package Client;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import Client.Network.PeriodicUpdater;
import Client.Network.ServerObserver;
import Client.Network.ServerAdapter;
import Server.Model.*;
import Server.Server;
import Server.Model.Game;
import Client.CLI.CLI;
import Client.Controller.ClientController;
import Utils.CastingHelper;
import Utils.MessageSerializer;
import com.google.gson.Gson;

public class Client implements Runnable, ServerObserver {
    private String response = null;
    private Game game;
    private Pages currentPage;
    private CLI cli;
    private ClientController clientController;
    private MessageSerializer messageSerializer;
    private String playerUsername;
    private boolean checkModel;
    private boolean alreadyChosenDivinity;
    private boolean alreadyChosenStartingPosition;
    private int lastMoveNumber = -1;
    private int lastMovedturn = 0;

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
        boolean loopCheck = true;
        int updateRate = 1;
        String message;
        Instant lastTime;
        ArrayList<String> chosenDivinities;

        currentPage = Pages.WELCOME; //class properties
        game = new Game(0, null, false, null, new Grid(), new Grid(), null);
        cli = new CLI();
        clientController = new ClientController();
        messageSerializer = new MessageSerializer();
        checkModel = false;
        alreadyChosenDivinity = false;

        /*
          get IP Address from user
         */
        cli.printWelcome();
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
        ServerAdapter serverAdapter = new ServerAdapter(server);
        serverAdapter.addObserver(this);
        Thread serverAdapterThread = new Thread(serverAdapter);
        serverAdapterThread.start();
        lastTime = Instant.now();

        while (loopCheck) {

            System.out.print("");
            // periodically fetches the updated game data from Server
            if (checkModel && Duration.between(lastTime, Instant.now()).getSeconds() > updateRate) {
                lastTime = Instant.now();
                PeriodicUpdater checkModelUpdate = new PeriodicUpdater(game.getCodGame(), serverAdapter);
                Thread checkModelUpdateThread = new Thread(checkModelUpdate);
                checkModelUpdateThread.start();
            }

            switch (currentPage) {
                case WELCOME: //active states,the application gets data from the interaction with the user
                    playerUsername = cli.readUsername();
                    boolean nPlayers = cli.readTwoOrThree();
                    message = messageSerializer.serializeJoinGame(playerUsername, nPlayers, null).toString();
                    currentPage = Pages.LOADINGWELCOMEDATA;

                    serverAdapter.requestJoinGame(message);
                    System.out.println("Loading data from server...");
                    break;
                case DIVINITIESCHOICE:
                    cli.printListDivinities();
                    chosenDivinities = cli.readDivinitiesChoice();
                    cli.setChosenDivinities(chosenDivinities);
                    message = messageSerializer.serializeDivinities(CastingHelper.convertDivinityList(chosenDivinities), "SendDivinities", game.getCodGame()).toString();
                    currentPage = Pages.LOADINGDIVINITY;

                    serverAdapter.requestSendDivinities(message);
                    break;
                case DIVINITYCHOICE:
                    System.out.println("Choose Your Divinity");
                    cli.setChosenDivinities(CastingHelper.convertDivinityListToString(game.getInGameDivinities()));
                    cli.printPossibleDivinities(CastingHelper.convertDivinityListToString(game.getPossibleDivinities()), CastingHelper.convertDivinityListToString(game.getInGameDivinities()));
                    String div = cli.readChosenDivinity(CastingHelper.convertDivinityListToString(game.getPossibleDivinities()), CastingHelper.convertDivinityListToString(game.getInGameDivinities()));
                    message = messageSerializer.serializeDivinity(CastingHelper.convertDivinity(div), playerUsername, game.getCodGame()).toString();
                    alreadyChosenDivinity = true;
                    currentPage = Pages.LOADINGDIVINITY;

                    serverAdapter.requestSendDivinity(message);
                    break;
                case STARTINGPOSITIONCHOICE:
                    Colour chosenColor = cli.choseColor(convertColors(game.getAlreadyChosenColors()));
                    game.getCurrentPlayer().setColour(chosenColor);
                    game.setNewGrid(cli.readStartingPosition(game.getCurrentPlayer(), game.getNewGrid()));
                    cli.drawGrid(game.getNewGrid());
                    game.setOldGrid(game.getNewGrid());
                    message = messageSerializer.serializeStartingPosition(game.getNewGrid(), "SendStartingPosition", playerUsername, game.getCodGame(), chosenColor).toString();
                    alreadyChosenStartingPosition = true;
                    currentPage = Pages.LOADINGSTARTINGPOSITION;

                    serverAdapter.requestSendStartingPosition(message);
                    break;
                case GAME:
                    Pawn chosenPawn;
                    if (lastMovedturn < game.getNTurns()) { // Choosing the Pawn to use
                        System.out.println("Turn: " + game.getNTurns());
                        cli.drawPlayers(game.getPlayers());
                        cli.drawGrid(game.getNewGrid());
                        chosenPawn = cli.choseToMove(game.getCurrentPlayer(), game.getNewGrid());

                        message = messageSerializer.serializeChosenPawn(game.getCodGame(), playerUsername, chosenPawn).toString();
                        lastMovedturn = game.getNTurns();
                        lastMoveNumber = game.getnMoves();
                        currentPage = Pages.LOADINGMOVE;

                        serverAdapter.requestSendChosenPawn(message);
                    } else { // Making Moves
                        cli.drawGrid(game.getNewGrid());
                        Move chosenMove;

                        if (game.getNextMoves().size() > 0) {
                            chosenMove = cli.choseMove(game.getNextMoves());
                            String moveText = chosenMove.getIfMove() ? "Moved to" : "Built in";
                            System.out.println(moveText + " coordinates (" + (chosenMove.getX() + 1) + "," + (chosenMove.getY() + 1) + ")");
                            game = clientController.updateGameByMove(chosenMove, game);
                            cli.drawGrid(game.getNewGrid());

                            if (game.getCurrentPlayer().getDivinity() == Divinity.DEMETER && game.getGameTurn().getNPossibleBuildings() == 1) {
                                game.getGameTurn().setCantBuildOnThisBlock(chosenMove);

                                for (int x = 0; x < 5; x++) { //sending data
                                    for (int y = 0; y < 5; y++) {
                                        if (game.getNewGrid().getCells(x, y).getPawn() != null) {
                                            if (chosenMove.getToMove().getId() == game.getNewGrid().getCells(x, y).getPawn().getId()) {
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

                    break;
                case ENDGAME:
                    System.out.println("GAME OVER!");
                    loopCheck = false;
                    cli.drawResults(game.getPlayers().getPlayer(game.getPlayers().searchPlayerByUsername(playerUsername)), game.getWinner());
                    break;
                case LOBBY: //passive states: the user can't do anything,the application is idle until an update from the server is received
                    currentPage = Pages.LOBBY;
                    break;
                case LOADINGWELCOMEDATA:
                    currentPage = Pages.LOADINGWELCOMEDATA;
                    break;
                case LOADINGDIVINITY:
                    currentPage = Pages.LOADINGDIVINITY;
                    break;
                case LOADINGDIVINITIES:
                    currentPage = Pages.LOADINGDIVINITIES;
                    break;
                case LOADINGSTARTINGPOSITION:
                    currentPage = Pages.LOADINGSTARTINGPOSITION;
                    break;
                case LOADINGMOVE:
                    currentPage = Pages.LOADINGMOVE;
                    break;
                default:
                    //System.out.println(response);
                    break;
            }
        }


        serverAdapter.stop();
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
     * function that gets called when an pawn signal is received from the server
     */
    public synchronized void receivePawn(String pawn) {
        System.out.println("");
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
        game = g;

        if (game != null) {
            int nPlayers = game.getThreePlayers() ? 3 : 2;

            switch (currentPage) {
                case LOBBY:
                    cli.drawLobby(game.getPlayers(), game.getCodGame());

                    // check if we have enough players to start the game
                    if (game.getPlayers().size() == nPlayers) {
                        if (game.getCurrentPlayer().getUsername().equals(playerUsername)) {
                            System.out.println("Going To Divinities Choice Page");
                            currentPage = Pages.DIVINITIESCHOICE;
                        } else {
                            System.out.println("Waiting for another player to choose the in game divinities");
                            currentPage = Pages.LOADINGDIVINITIES;
                        }
                    }
                    break;
                case LOADINGDIVINITIES:
                    if (game.getPlayers().size() == nPlayers && game.getInGameDivinities().size() == nPlayers) {
                        if (game.getCurrentPlayer().getUsername().equals(playerUsername)) {
                            System.out.println("Going to Divinity Choice Page");
                            currentPage = Pages.DIVINITYCHOICE;
                        } else {
                            System.out.println("Waiting for other players to chose their divinities");
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
                        currentPage = Pages.GAME;
                    } else {
                        currentPage = Pages.LOADINGMOVE;
                    }

                    break;
            }
        }

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




