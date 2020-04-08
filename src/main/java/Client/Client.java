package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

import Client.Network.ServerObserver;
import Client.Network.ServerAdapter;
import Server.Model.*;
import Server.Server;
import Server.Model.Game;
import Client.CLI.CLI;
import Utils.MessageSerializer;
import com.google.gson.JsonElement;

public class Client implements Runnable, ServerObserver {
    /* auxiliary variable used for implementing the consumer-producer pattern*/
    private String response = null;
    private Game game;
    private Pages currentPage;
    private ArrayList<Divinity> divinities;
    private CLI cli;
    private MessageSerializer messageSerializer;


    public static void main(String[] args) {
        /* Instantiate a new Client which will also receive events from
         * the server by implementing the ServerObserver interface */
        Client client = new Client();
        client.run();
    }


    @Override
    public void run() {
        /*
         * WARNING: this method executes IN THE CONTEXT OF THE MAIN THREAD
         */
        Scanner scanner = new Scanner(System.in);
        boolean loopCheck = true;
        currentPage = Pages.WELCOME;
        game = new Game(0, "", false, null, null, null, null);
        divinities = new ArrayList<>();
        cli = new CLI();
        messageSerializer = new MessageSerializer();
        Timer timer = new Timer();

        /*
          get initial data from user (IP Address,Username,Type of Game)
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

        while (loopCheck) {

            if (game.getCodGame() != null) {
                String message = messageSerializer.serializeCheckModel(game.getCodGame()).toString();
                serverAdapter.requestCheckModel(message);
            }


            switch (currentPage) {
                case WELCOME:
                    String userName = cli.readUsername();
                    boolean nPlayers = cli.readTwoOrThree();
                    String message = messageSerializer.serializeJoinGame(userName, nPlayers).toString();
                    currentPage = Pages.LOADING;

                    serverAdapter.requestJoinGame(message);
                    System.out.println("Loading data from server...");
                    break;
                case LOBBY:
                    System.out.println("Lobby Page");
                    break;
                case DIVINITIESCHOICE:
                    System.out.println("Divinities Choice Page");
                    break;
                case DIVINITYCHOICE:
                    System.out.println("Divinity Choice Page");
                    break;
                case STARTINGPOSITIONCHOICE:
                    System.out.println("Starting Positions Choice Page");
                    break;
                case GAME:
                    System.out.println("Game Page");
                    break;
                case ENDGAME:
                    loopCheck = false;
                    break;
                case LOADING:
                    loopCheck = true;
                    break;
                default:
                    System.out.println(response);
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
    public synchronized void receiveNewPlayerConnected(Player player) {
        currentPage = Pages.LOBBY;
        System.out.println("Received Response From Server");
        game.getPlayers().addPlayer(player);
        notifyAll();
    }

    /**
     * function that gets called when a divinities signal is received from the server
     *
     * @param divinities the list of all divinities in the game
     */
    @Override
    public synchronized void receiveDivinities(ArrayList<Divinity> divinities) {
        currentPage = Pages.DIVINITIESCHOICE;
        this.divinities = divinities;
        notifyAll();
    }

    /**
     * function that gets called when a possible divinities signal is received from the server
     *
     * @param divinities the list of possible divinities for the player
     */
    @Override
    public synchronized void receivePossibleDivinities(ArrayList<Divinity> divinities) {
        currentPage = Pages.DIVINITYCHOICE;
        this.divinities = divinities;
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
        //currentPage = Pages.ENDGAME;
        game = g;
        notifyAll();
    }

}
