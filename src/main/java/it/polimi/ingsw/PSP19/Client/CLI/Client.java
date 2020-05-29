package it.polimi.ingsw.PSP19.Client.CLI;

import java.io.IOException;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import it.polimi.ingsw.PSP19.Client.Network.PeriodicUpdater;
import it.polimi.ingsw.PSP19.Client.Network.PingService;
import it.polimi.ingsw.PSP19.Client.Network.ServerObserver;
import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;
import it.polimi.ingsw.PSP19.Client.Pages;
import it.polimi.ingsw.PSP19.Server.Model.*;
import it.polimi.ingsw.PSP19.Server.Server;
import it.polimi.ingsw.PSP19.Client.Controller.ClientController;
import it.polimi.ingsw.PSP19.Utils.CastingHelper;
import it.polimi.ingsw.PSP19.Utils.MessageSerializer;
import com.google.gson.Gson;

public class Client implements Runnable, ServerObserver {
    private Game game;
    private Pages currentPage;
    private LoginCLI loginCli;
    private DivinitiesChoicesCLI divinChoiceCli;
    private ColorChoiceCLI colorChoiceCli;
    private GameCLI gameCli;
    private EndCLI endCli;
    private ClientController clientController;
    private MessageSerializer messageSerializer;
    private ServerAdapter serverAdapter;
    private String playerUsername;
    private Colour chosenColor;
    private boolean checkModel;
    private boolean alreadyChosenDivinity;
    private boolean alreadyChosenStartingPosition;
    private boolean alreadyChosenCanComeUp = false;
    boolean loopCheck = true;
    private int lastMoveNumber = -1;
    private int lastMovedturn = 0;
    private Pawn chosenPawn = null;
    ArrayList<String> chosenDivinities;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }


    @Override
    public void run() {
        /*
         * WARNING: this method executes IN THE CONTEXT OF THE MAIN THREAD
         */
        Scanner scanner = new Scanner(System.in); //local variables

        int updateRate = 1;
        Instant lastTime;

        currentPage = Pages.WELCOME; //class properties
        game = new Game(0, null, false, null, new Grid(), new Grid(), null);
        loginCli = new LoginCLI();
        divinChoiceCli = new DivinitiesChoicesCLI();
        colorChoiceCli = new ColorChoiceCLI();
        gameCli = new GameCLI();
        endCli = new EndCLI();
        clientController = new ClientController();
        messageSerializer = new MessageSerializer();
        checkModel = false;
        alreadyChosenDivinity = false;

        /*
          get IP Address from user
         */
        loginCli.printWelcome();
        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

        /* open a connection to the server */
        Socket server;
        try {
            server = new Socket(ip, Server.SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected to the server");

        /* Create the adapter that will allow communication with the server
         * in background, and start running its thread */
        serverAdapter = new ServerAdapter(server);
        serverAdapter.addObserver(this);
        Thread serverAdapterThread = new Thread(serverAdapter);
        serverAdapterThread.start();
        lastTime = Instant.now();

        while (loopCheck) {

            System.out.print("");
            // periodically fetches the updated game data from Server
            if (checkModel && Duration.between(lastTime, Instant.now()).getSeconds() > updateRate) {
                lastTime = Instant.now();
                PeriodicUpdater checkModelUpdate = new PeriodicUpdater(game.getCodGame(), serverAdapter, true);
                Thread checkModelUpdateThread = new Thread(checkModelUpdate);
                checkModelUpdateThread.start();
            }

            switch (currentPage) {
                case WELCOME:
                    welcome(serverAdapter);
                    break;
                case DIVINITIESCHOICE:
                    divinitiesChoice(serverAdapter);
                    break;
                case DIVINITYCHOICE:
                    divinityChoice(serverAdapter);
                    break;
                case STARTINGPOSITIONCHOICE:
                    startingPositionChoice(serverAdapter);
                    break;
                case GAME:
                    moveChoice(serverAdapter);
                    break;
                case ENDGAME:
                    endGame();
                    break;
                default:
                    break;
            }
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        serverAdapter.stop();
    }

    /**
     * handles the welcome phase
     *
     * @param serverAdapter the object used to communicate with the server
     */
    public void welcome(ServerAdapter serverAdapter) {
        String message;
        playerUsername = loginCli.readUsername();
        boolean nPlayers = loginCli.readTwoOrThree();
        message = messageSerializer.serializeJoinGame(playerUsername, nPlayers, null).toString();
        currentPage = Pages.LOADINGWELCOMEDATA;

        serverAdapter.requestJoinGame(message);
        System.out.println("Loading data from server...");
    }

    /**
     * handles the divinities choice phase
     *
     * @param serverAdapter the object used to communicate with the server
     */
    public void divinitiesChoice(ServerAdapter serverAdapter) {
        String message;
        divinChoiceCli.printListDivinities(game.getPlayers().size());
        chosenDivinities = divinChoiceCli.readDivinitiesChoice(game.getPlayers().size());
        divinChoiceCli.setChosenDivinities(chosenDivinities);
        message = messageSerializer.serializeDivinities(CastingHelper.convertDivinityList(chosenDivinities), "SendDivinities", game.getCodGame()).toString();
        currentPage = Pages.LOADINGDIVINITY;

        serverAdapter.requestSendDivinities(message);
    }

    /**
     * handles the divinity choice phase
     *
     * @param serverAdapter the object used to communicate with the server
     */
    public void divinityChoice(ServerAdapter serverAdapter) {
        String message;
        System.out.println("Choose Your Divinity");
        divinChoiceCli.setChosenDivinities(CastingHelper.convertDivinityListToString(game.getInGameDivinities()));
        divinChoiceCli.printPossibleDivinities(CastingHelper.convertDivinityListToString(game.getPossibleDivinities()), CastingHelper.convertDivinityListToString(game.getInGameDivinities()));
        String div = divinChoiceCli.readChosenDivinity(CastingHelper.convertDivinityListToString(game.getPossibleDivinities()), CastingHelper.convertDivinityListToString(game.getInGameDivinities()));
        message = messageSerializer.serializeDivinity(CastingHelper.convertDivinity(div), playerUsername, game.getCodGame()).toString();
        alreadyChosenDivinity = true;
        currentPage = Pages.LOADINGDIVINITY;

        serverAdapter.requestSendDivinity(message);
    }

    /**
     * handles the starting position choice phase
     *
     * @param serverAdapter the object used to communicate with the server
     */
    public void startingPositionChoice(ServerAdapter serverAdapter) {
        String message;
        chosenColor = colorChoiceCli.choseColor(convertColors(game.getAlreadyChosenColors()));

        game.getCurrentPlayer().setColour(chosenColor);
        game.setNewGrid(gameCli.readStartingPosition(game.getCurrentPlayer(), game.getNewGrid()));
        gameCli.drawGrid(game.getNewGrid());
        game.setOldGrid(game.getNewGrid());
        message = messageSerializer.serializeStartingPosition(game.getNewGrid(), "SendStartingPosition", playerUsername, game.getCodGame(), chosenColor).toString();
        alreadyChosenStartingPosition = true;
        currentPage = Pages.LOADINGSTARTINGPOSITION;

        serverAdapter.requestSendStartingPosition(message);
    }

    /**
     * handles the move choice phase
     *
     * @param serverAdapter the object used to communicate with the server
     */
    public void moveChoice(ServerAdapter serverAdapter) {
        String message;
        if (game.getCurrentPlayer().getDivinity() == Divinity.PROMETHEUS && !alreadyChosenCanComeUp) {
            boolean canComeUp = gameCli.wantToGoUp();
            message = messageSerializer.serializeDecideCanComeUp(canComeUp, game.getCodGame()).toString();
            serverAdapter.requestSendDecidesToComeUp(message);
            alreadyChosenCanComeUp = true;
            currentPage = Pages.LOADINGCANCOMEUP;
        } else if (lastMovedturn < game.getNTurns()) { // Choosing the Pawn to use
            System.out.println("Turn: " + game.getNTurns());
            gameCli.drawPlayers(game.getPlayers());
            gameCli.drawGrid(game.getNewGrid());
            chosenPawn = gameCli.choseToMove(game.getCurrentPlayer(), game.getNewGrid());

            message = messageSerializer.serializeChosenPawn(game.getCodGame(), playerUsername, chosenPawn).toString();
            lastMovedturn = game.getNTurns();
            lastMoveNumber = game.getnMoves();
            currentPage = Pages.LOADINGMOVE;

            serverAdapter.requestSendChosenPawn(message);
        } else { // Making Moves
            //gameCli.drawGrid(game.getNewGrid());
            Move chosenMove;
            boolean endTurn = false;

            if (game.getNextMoves().size() > 0) {
                chosenMove = (game.getNextMoves().size() == 1 && game.getNextMoves().getMove(0).getX() == 6) ? game.getNextMoves().getMove(0) : gameCli.choseMove(game.getNextMoves(), game.getNewGrid());
                String moveText = chosenMove.getIfMove() ? "Moved to" : "Built in";
                System.out.println(moveText + " coordinates (" + (chosenMove.getX() + 1) + "," + (chosenMove.getY() + 1) + ")");
                endTurn = chosenMove.getX() == 6 && chosenMove.getY() == 6;
                game = endTurn ? game : clientController.updateGameByMove(chosenMove, game);
                gameCli.drawGrid(game.getNewGrid());

                if (game.getCurrentPlayer().getDivinity() == Divinity.DEMETER && game.getGameTurn().getNPossibleBuildings() == 1) {
                    Move cantBuildMove = new Move(chosenPawn);
                    cantBuildMove.setX(chosenMove.getX());
                    cantBuildMove.setY(chosenMove.getY());
                    cantBuildMove.setIfMove(false);
                    game.getGameTurn().setCantBuildOnThisBlock(cantBuildMove);

                    for (int x = 0; x < 5; x++) { //sending data for Demeter second building
                        for (int y = 0; y < 5; y++) {
                            if (game.getNewGrid().getCells(x, y).getPawn() != null) {
                                if (chosenPawn.getId() == game.getNewGrid().getCells(x, y).getPawn().getId()) {
                                    chosenMove.setX(x);
                                    chosenMove.setY(y);
                                }
                            }
                        }
                    }

                }

                message = messageSerializer.serializeChosenMove(game, chosenMove).toString();
                lastMoveNumber = game.getnMoves();
                currentPage = (game.getWinner() != null) ? Pages.ENDGAME : Pages.LOADINGMOVE;

                serverAdapter.requestSendChosenMove(message);
            } else {
                System.out.println("There are no possible moves!");
                loopCheck = false;
            }
        }
    }

    /**
     * handles the end game phase
     */
    public void endGame() {
        System.out.println("GAME OVER!");
        loopCheck = false;
        endCli.drawResults(new Player(playerUsername, null, chosenColor), game.getWinner());
    }

    /**
     * function that gets called when a username taken signal is received from the server
     *
     * @param response the server error response
     */
    @Override
    public synchronized void receiveUsernameTaken(String response) {
        System.out.println(response);
        currentPage = Pages.WELCOME;
    }

    /**
     * function that gets called when a new player signal is received from the server
     *
     * @param player the player who joined the game
     */
    @Override
    public synchronized void receiveNewPlayerConnected(Player player, String gameID) {
        System.out.println("Received Response From Server,Going to Lobby Page");
        game.getPlayers().addPlayer(player);
        game.setCodGame(gameID);
        checkModel = true;

        PingService pingService = new PingService(gameID, serverAdapter); //starts a thread pinging the server
        Thread pingServiceThread = new Thread(pingService);
        pingServiceThread.start();

        currentPage = Pages.LOBBY;
        notifyAll();
    }

    /**
     * function that gets called when a divinities signal is received from the server
     *
     * @param divinities the list of all divinities in the game
     */
    @Override
    public synchronized void receiveDivinities(String divinities) {
        System.out.println("");
        notifyAll();
    }

    /**
     * function that gets called when a possible divinities signal is received from the server
     */
    @Override
    public synchronized void receivePossibleDivinities(String response) {
        System.out.println("");
        notifyAll();
    }

    /**
     * function that gets called when a starting position signal is received from the server
     */
    @Override
    public synchronized void receiveStartingPosition(String position) {
        System.out.println("");
        notifyAll();
    }

    /**
     * function that gets called when an pawn signal is received from the server
     */
    public synchronized void receivePawn(String pawn) {
        if (pawn.equals("You Lost")) {
            System.out.println("You don't have any possible move!");
            currentPage = Pages.LOADINGMOVE;
        } else if (pawn.equals("This pawn doesn't have any possible moves,choosing the other one")) {
            System.out.println(pawn + "\n");
        } else {
            System.out.println("");
        }
        notifyAll();
    }

    /**
     * function that gets called when an canComeUp signal is received from the server
     */
    public synchronized void receiveCanComeUp(String canComeUp) {
        System.out.println("");
        currentPage = Pages.GAME;
        notifyAll();
    }

    /**
     * function that gets called when an new move signal is received from the server
     */
    public synchronized void receiveMoves(String moves) {
        System.out.println("");
        notifyAll();
    }

    /**
     * function that gets called when an end game signal is received from the server
     */
    @Override
    public synchronized void receiveEndGame(String endGame) {
        System.out.println("");
        notifyAll();
    }

    /**
     * function that gets called when an update model signal is received from the server
     *
     * @param g update value of game
     */
    @Override
    public synchronized void receiveModelUpdate(Game g) {
        boolean gridChanged = !(new Gson().toJson(game.getNewGrid()).equals(new Gson().toJson(g.getNewGrid())));
        game = g;

        if (game != null) {
            int nPlayers = game.getThreePlayers() ? 3 : 2;

            if (game.getDisconnected()) {
                System.out.println("A Player Disconnected,Game Over");
                loopCheck = false;
            } else {

                switch (currentPage) {
                    case LOBBY:
                        loginCli.drawLobby(game.getPlayers(), game.getCodGame());

                        // check if we have enough players to start the game
                        if (game.getPlayers().size() == nPlayers) {
                            if (game.getCurrentPlayer().getUsername().equals(playerUsername)) {
                                System.out.println("Going To Divinities Choice Page");
                                currentPage = Pages.DIVINITIESCHOICE;
                            } else {
                                System.out.println("\nWaiting for another player to choose the in game divinities");
                                currentPage = Pages.LOADINGDIVINITIES;
                            }
                        }
                        break;
                    case LOADINGDIVINITIES:
                        int nPlayersInGame = game.getThreePlayers() ? 3 : 2;
                        if (game.getPlayers().size() == nPlayersInGame && game.getInGameDivinities().size() == nPlayersInGame) {
                            if (game.getCurrentPlayer().getUsername().equals(playerUsername)) {
                                System.out.println("Going to Divinity Choice Page");
                                currentPage = Pages.DIVINITYCHOICE;
                            } else {
                                System.out.println("\nWaiting for other players to chose their divinities");
                                currentPage = Pages.LOADINGDIVINITY;
                            }
                        }
                        break;
                    case LOADINGDIVINITY:
                        if (!alreadyChosenDivinity && game.getCurrentPlayer().getUsername().equals(playerUsername)) {
                            System.out.println("Going to Divinity Choice Page");
                            currentPage = Pages.DIVINITYCHOICE;
                        } else if (alreadyChosenDivinity && game.getInGameDivinities().size() == 0) {
                            System.out.println("Going to Starting Position Choice Page");
                            currentPage = Pages.LOADINGSTARTINGPOSITION;
                        }
                        break;
                    case LOADINGSTARTINGPOSITION:
                        if (gridChanged) {
                            gameCli.drawGrid(game.getNewGrid());
                        }

                        if (!alreadyChosenStartingPosition && game.getCurrentPlayer().getUsername().equals(playerUsername)) {
                            System.out.println("\nChoose your Starting Position");
                            currentPage = Pages.STARTINGPOSITIONCHOICE;
                        } else if (alreadyChosenStartingPosition && game.getNTurns() > 0) {
                            System.out.println("\nGoing to Game Page");
                            currentPage = Pages.LOADINGMOVE;
                        }

                        break;
                    case LOADINGMOVE:
                        if (game.getWinner() != null) {
                            currentPage = Pages.ENDGAME;
                        } else if (lastMoveNumber < game.getnMoves() && game.getCurrentPlayer().getUsername().equals(playerUsername)) {
                            if (lastMovedturn < game.getNTurns()) {
                                alreadyChosenCanComeUp = false;
                            }
                            currentPage = Pages.GAME;
                        } else {
                            if (gridChanged) {
                                gameCli.drawGrid(game.getNewGrid());
                            }
                            currentPage = Pages.LOADINGMOVE;
                        }

                        break;
                }
            }

        }

        notifyAll();

    }

    @Override
    public synchronized void receivePing(String ping) {
        notifyAll();
    }

    /**
     * converts an ArrayList of Colors to an ArrayList of Strings
     *
     * @param colors the ArrayList to Convert
     * @return the ArrayList of converted strings
     */
    ArrayList<String> convertColors(ArrayList<Colour> colors) {
        ArrayList<String> strColors = new ArrayList<String>();
        for (Colour cl : colors) {
            strColors.add(cl.toString());
        }
        return strColors;
    }
}



