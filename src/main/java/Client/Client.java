package Client;

import java.io.IOException;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

import Client.Network.PeriodicUpdater;
import Client.Network.ServerObserver;
import Client.Network.ServerAdapter;
import Server.Model.*;
import Server.Server;
import Server.Model.Game;
import Client.CLI.CLI;
import Utils.CastingHelper;
import Utils.MessageSerializer;

public class Client implements Runnable, ServerObserver {
    private String response = null;
    private Game game;
    private Pages currentPage;
    private CLI cli;
    private MessageSerializer messageSerializer;
    private String playerUsername;
    private boolean checkModel;
    private boolean alreadyChosenDivinity;
    private boolean alreadyChosenStartingPosition;


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
                    cli.printPossibleDivinities();
                    String div = cli.readChosenDivinity();
                    message = messageSerializer.serializeDivinity(CastingHelper.convertDivinity(div), playerUsername, game.getCodGame()).toString();
                    alreadyChosenDivinity = true;
                    currentPage = Pages.LOADINGDIVINITY;

                    serverAdapter.requestSendDivinity(message);
                    break;
                case STARTINGPOSITIONCHOICE:
                    System.out.println("Selecting a random starting position");
                    setsDefaultStartingPosition();
                    message = messageSerializer.serializeStartingPosition(game.getNewGrid(), "SendStartingPosition", playerUsername, game.getCodGame()).toString();
                    alreadyChosenStartingPosition = true;
                    currentPage = Pages.LOADINGSTARTINGPOSITION;

                    serverAdapter.requestSendStartingPosition(message);
                    break;
                case GAME:
                    System.out.println("Game Page");
                    break;
                case ENDGAME:
                    loopCheck = false;
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
        System.out.println(divinities);
        notifyAll();
    }

    /**
     * function that gets called when a possible divinities signal is received from the server
     */
    @Override
    public synchronized void receivePossibleDivinities(String response) {
        System.out.println(response);
        notifyAll();
    }

    /**
     * function that gets called when an new move signal is received from the server
     *
     * @param moves the list of possible moves for the player
     * @param grid  updated game grid
     */
    public synchronized void receiveMoves(MoveList moves, Grid grid) {
        currentPage = Pages.GAME;
        game.setOldGrid(grid);
        game.setNewGrid(grid);
        notifyAll();
    }

    /**
     * function that gets called when an end game signal is received from the server
     *
     * @param grid final value of the game grid
     */
    @Override
    public synchronized void receiveEndGame(Grid grid) {
        currentPage = Pages.ENDGAME;
        game.setOldGrid(grid);
        game.setNewGrid(grid);
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
            }
        }

        notifyAll();

    }

    /*Temporary utility function,selects a new staring position with the first free cell*/
    void setsDefaultStartingPosition() {

        int x = 0;
        int y = 0;
        int pawnCounter = 0;

        boolean loopCheck = true;

        while (loopCheck && x < 5) {
            y = 0;
            while (loopCheck && y < 5) {

                if (game.getNewGrid().getCells(x, y).getPawn() == null) {
                    Player currentPlayer = game.getPlayers().getPlayer(game.getPlayers().searchPlayerByUsername(playerUsername));
                    game.getNewGrid().getCells(x, y).setPawn(new Pawn(currentPlayer));
                    pawnCounter++;
                    System.out.println("Adding new pawn at position: " + x + "," + y);
                }

                if (pawnCounter >= 2) {
                    loopCheck = false;
                }

                y++;
            }
            x++;
        }


    }

}




