package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import Client.Network.ServerObserver;
import Client.Network.ServerAdapter;
import Server.Model.*;
import Server.Server;
import Server.Model.Game;

public class Client implements Runnable, ServerObserver {
    /* auxiliary variable used for implementing the consumer-producer pattern*/
    private String response = null;
    private Game game;
    private Pages currentPage;
    private ArrayList<Divinity> divinities;


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
        divinities = new ArrayList<Divinity>();

        /*
          get initial data from user (IP Address,Username,Type of Game)
         */
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
        System.out.println("Connected");

        /* Create the adapter that will allow communication with the server
         * in background, and start running its thread */
        ServerAdapter serverAdapter = new ServerAdapter(server);
        serverAdapter.addObserver(this);
        Thread serverAdapterThread = new Thread(serverAdapter);
        serverAdapterThread.start();

        while (loopCheck) {
            switch (currentPage) {
                case WELCOME:
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
                default:
                    System.out.println(response);
                    break;
            }
        }


        serverAdapter.stop();
    }


    @Override
    public synchronized void didReceiveConvertedString(String oldStr, String newStr) {
        /* Save the string and notify the main thread */
        response = newStr;
        notifyAll();
    }

    @Override
    public synchronized void receiveNewPlayerConnected(Player player) {
        currentPage = Pages.LOBBY;
        game.getPlayers().addPlayer(player);
        notifyAll();
    }

    @Override
    public synchronized void receiveDivinities(ArrayList<Divinity> divinities) {
        currentPage = Pages.DIVINITIESCHOICE;
        this.divinities = divinities;
        notifyAll();
    }

    @Override
    public synchronized void receivePossibleDivinities(ArrayList<Divinity> divinities) {
        currentPage = Pages.DIVINITYCHOICE;
        this.divinities = divinities;
        notifyAll();
    }


    public synchronized void receiveMoves(MoveList moves, Grid grid) {
        currentPage = Pages.GAME;
        game.setOldGrid(grid);
        game.setNewGrid(grid);
        notifyAll();
    }

    @Override
    public synchronized void receiveEndGame(Grid grid) {
        currentPage = Pages.ENDGAME;
        game.setOldGrid(grid);
        game.setNewGrid(grid);
        notifyAll();
    }

}
