package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.Controller.ClientController;
import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;
import it.polimi.ingsw.PSP19.Client.Network.ServerObserver;
import it.polimi.ingsw.PSP19.Server.Model.*;
import it.polimi.ingsw.PSP19.Server.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;


public class Client extends Application implements ServerObserver {
    private RequestHandler requestHandler;
    private ServerAdapter serverAdapter;
    private String response = null;
    private Game game;
    private Page currentPage;
    private ClientController clientController;
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
    double width = 1500;
    double height = 900;
    Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        currentPage = new WelcomePage();
        String currentPageName = currentPage.getPageName();
        root = FXMLLoader.load(getClass().getResource("/" + currentPageName + "/" + currentPageName + ".fxml"));
        root.getStylesheets().add(getClass().getResource("/" + currentPageName + "/" + currentPageName + ".css").toExternalForm());

        requestHandler = RequestHandler.getRequestHandler();
        requestHandler.setClient(this);

        /* open a connection to the server */
        Socket server;
        try {
            server = new Socket("192.168.1.33", Server.SOCKET_PORT);
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

        Scene scene = new Scene(root, width, height);

        primaryStage.setScene(scene);
        primaryStage.setTitle("SANTORINI");
        primaryStage.show();

        game = new Game(0, null, false, null, new Grid(), new Grid(), null);

        currentPage.setGame(null);
        currentPage.setServerAdapter(serverAdapter);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void makeRequest() {
        String reqContent = requestHandler.getCurrentRequest();
        switch (requestHandler.getCurrentCommand()) {
            case JOIN_GAME:
                serverAdapter.requestJoinGame(reqContent);
                break;
            case SEND_DIVINITIES:
                serverAdapter.requestSendDivinities(reqContent);
                break;
            case SEND_DIVINITY:
                serverAdapter.requestSendDivinity(reqContent);
                break;
            case SEND_STARTING_POSITION:
                serverAdapter.requestSendStartingPosition(reqContent);
                break;
            case SEND_DECIDES_TO_COME_UP:
                serverAdapter.requestSendDecidesToComeUp(reqContent);
                break;
            case SEND_CHOSEN_PAWN:
                serverAdapter.requestSendChosenPawn(reqContent);
                break;
            case SEND_CHOSEN_MOVE:
                serverAdapter.requestSendChosenMove(reqContent);
                break;
            case CHECK_MODEL:
                serverAdapter.requestCheckModel(reqContent);
                break;
        }
    }

    /**
     * function that gets called when a username taken signal is received from the server
     *
     * @param response the server error response
     */
    @Override
    public synchronized void receiveUsernameTaken(String response) {
        System.out.println(response);
    }

    /**
     * function that gets called when a new player signal is received from the server
     *
     * @param player the player who joined the game
     */
    @Override
    public synchronized void receiveNewPlayerConnected(Player player, String gameID) {
        System.out.println("Received Response From Server,Going to Lobby Page");
        //game.getPlayers().addPlayer(player);
        //game.setCodGame(gameID);
        checkModel = true;
        //currentPage = Pages.LOBBY;
        notifyAll();
    }


    /**
     * function that gets called when a divinities signal is received from the server
     *
     * @param divinities the list of all divinities in the game
     */
    @Override
    public synchronized void receiveDivinities(String divinities) {
        notifyAll();
    }

    /**
     * function that gets called when a possible divinities signal is received from the server
     */
    @Override
    public synchronized void receivePossibleDivinities(String response) {
        notifyAll();
    }

    /**
     * function that gets called when an pawn signal is received from the server
     */
    public synchronized void receivePawn(String pawn) {
        if (pawn.equals("You Lost")) {
            System.out.println("You don't have any possible move!");
            //currentPage = Pages.LOADINGMOVE;
        } else if (pawn.equals("This pawn doesn't have any possible moves,choosing the other one")) {
            System.out.println(pawn + "\n");
        }
        notifyAll();
    }

    /**
     * function that gets called when an canComeUp signal is received from the server
     */
    public synchronized void receiveCanComeUp(String canComeUp) {
        //currentPage = Pages.GAME;
        notifyAll();
    }

    /**
     * function that gets called when an new move signal is received from the server
     */
    public synchronized void receiveMoves(String moves) {
        notifyAll();
    }

    /**
     * function that gets called when an end game signal is received from the server
     */
    @Override
    public synchronized void receiveEndGame(String endGame) {
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
        currentPage.setGame(g);
    }

}
