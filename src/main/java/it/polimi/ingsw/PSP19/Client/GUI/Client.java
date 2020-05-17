package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;
import it.polimi.ingsw.PSP19.Client.Network.ServerObserver;
import it.polimi.ingsw.PSP19.Server.Model.*;
import it.polimi.ingsw.PSP19.Server.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;


public class Client extends Application implements ServerObserver {
    private RequestHandler requestHandler;
    private ServerAdapter serverAdapter;
    private Game game;
    private Page currentPage;
    private String playerUsername;
    private Colour chosenColor;
    private boolean alreadyChosenDivinity;
    private boolean alreadyChosenStartingPosition;
    private boolean alreadyChosenCanComeUp = false;
    private int lastMoveNumber = -1;
    private int lastMovedturn = 0;
    private Pawn chosenPawn = null;
    private Socket server = null;
    private Stage mainStage;
    double width = 1440;
    double height = 900;
    Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception {

        game = new Game(0, null, false, null, new Grid(), new Grid(), null);
        mainStage = primaryStage;

        root = setCurrentPage(new WelcomePage());

        Scene scene = new Scene(root, width, height);

        primaryStage.setTitle("SANTORINI");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // initializes the singleton used to handle  sending requests to server
        requestHandler = RequestHandler.getRequestHandler();
        requestHandler.setClient(this);

        currentPage.setGame(game);
        currentPage.setServerAdapter(serverAdapter);

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    /***
     * makes requests to the server based on the data of requestHandler
     */
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


    public void setServer(String ip) throws IOException {
        /* open a connection to the server */
        server = new Socket(ip, Server.SOCKET_PORT);

        /* Create the adapter that will allow communication with the server
         * in background, and start running its thread */
        serverAdapter = new ServerAdapter(server);
        serverAdapter.addObserver(this);
        Thread serverAdapterThread = new Thread(serverAdapter);
        serverAdapterThread.start();
    }

    /***
     * Sets the current Scene
     */
    public Parent setCurrentPage(Page page) throws IOException {
        currentPage = page;
        String currentPageName = currentPage.getPageName();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + currentPageName + "/" + currentPageName + ".fxml"));
        Parent root = loader.load();
        root.getStylesheets().add(getClass().getResource("/" + currentPageName + "/" + currentPageName + ".css").toExternalForm());

        currentPage = loader.getController();
        currentPage.setClient(this);
        currentPage.setGame(game);

        Platform.runLater(
                () -> {
                    mainStage.getScene().setRoot(root);
                }
        );

        return root;
    }


    public Colour getChosenColor() {
        return chosenColor;
    }

    public void setChosenColor(Colour cColor) {
        chosenColor = cColor;
    }

    public String getPlayerUsername() {
        return playerUsername;
    }

    public void setPlayerUsername(String uName) {
        playerUsername = uName;
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
        game.getPlayers().addPlayer(player);
        game.setCodGame(gameID);

        ModelUpdaterThread modelUpdater = new ModelUpdaterThread(gameID, serverAdapter); //starts a thread periodically checking for Model updates
        Thread modelUpdaterThread = new Thread(modelUpdater);

        modelUpdater.setModelCheck(true);
        modelUpdaterThread.start();

        try {
            setCurrentPage(new LobbyPage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyAll();
    }


    /**
     * function that gets called when a divinities signal is received from the server
     *
     * @param divinities the list of all divinities in the game
     */
    @Override
    public synchronized void receiveDivinities(String divinities) throws IOException {
        setCurrentPage(new WaitingDivinitiesChoicePage());
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
    public synchronized void receiveModelUpdate(Game g) throws IOException {
        System.out.println("Received Model Update");
        game = g;
        currentPage.setGame(g);
        int nPlayersInGame = game.getThreePlayers() ? 3 : 2;

        switch (currentPage.getPageName()) {

            case "WaitingDivinitiesChoice":
                if (game.getPlayers().size() == nPlayersInGame && game.getInGameDivinities().size() == nPlayersInGame) {
                    if (game.getCurrentPlayer().getUsername().equals(playerUsername)) {
                        setCurrentPage(new DivinitiesChoicePage());
                    } else {
                        setCurrentPage(new WaitingSingleDivinityChoicePage());
                    }
                }
                break;
            case "WaitingSingleDivinityChoice":
                if (game.getInGameDivinities().size() == 0) {
                    setCurrentPage(new GamePage());
                } else if (game.getCurrentPlayer().getUsername().equals(playerUsername)) {
                    setCurrentPage(new SingleDivinityChoicePage());
                }
                break;
        }
    }

}