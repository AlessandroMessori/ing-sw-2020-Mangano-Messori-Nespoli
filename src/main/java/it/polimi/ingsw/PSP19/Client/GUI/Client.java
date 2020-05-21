package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.GUI.Pages.*;
import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;
import it.polimi.ingsw.PSP19.Client.Network.ServerObserver;
import it.polimi.ingsw.PSP19.Server.Model.*;
import it.polimi.ingsw.PSP19.Server.Server;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;


public class Client extends Application implements ServerObserver {
    private boolean hasMusic = true;
    private RequestHandler requestHandler;
    private ServerAdapter serverAdapter;
    private Game game;
    private Page currentPage;
    private String playerUsername;
    private Colour chosenColor;
    private boolean threePlayers;
    private boolean disconnected = false;
    private Socket server = null;
    private Stage mainStage;
    double width = 1440;
    double height = 900;
    Parent root;

    public boolean getHasMusic() {
        return hasMusic;
    }

    public void setHasMusic(boolean hasMusic) {
        this.hasMusic = hasMusic;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        game = new Game(0, null, false, null, new Grid(), new Grid(), null);
        mainStage = primaryStage;

        root = setCurrentPage(new WelcomePage(), "/Music/Menu/Atmospheric_fantasy_music_-_Ocean_Palace.mp3");

        Scene scene = new Scene(root, width, height);

        primaryStage.setOnCloseRequest(e -> {
            System.exit(0);
        });

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
    public Parent setCurrentPage(Page page, String musicPath) throws IOException, InterruptedException {

        Media media = null;                     //MUSIC
        // Create a Media Player
        MediaPlayer playermp3 = null;
        // Create a Media View
        MediaView mp3View = null;
        if (musicPath != null) {
            // Locate the media content in the CLASSPATH
            URL mediaUrl = getClass().getResource(musicPath);
            String mediaStringUrl = mediaUrl.toExternalForm();
            media = new Media(mediaStringUrl);
            playermp3 = new MediaPlayer(media);
            playermp3.setCycleCount(MediaPlayer.INDEFINITE);
            mp3View = new MediaView(playermp3);

            // if there is another song playing it gets stopped
            if (currentPage != null && currentPage.getMediaPlayer() != null) {
                currentPage.getMediaView().setDisable(true);
                currentPage.getMediaPlayer().stop();
            }
        } else {

            if (currentPage.getMediaView() != null) {
                if (hasMusic) {
                    playermp3 = currentPage.getMediaPlayer();
                    mp3View = currentPage.getMediaView();
                } else {
                    currentPage.getMediaView().setDisable(true);
                    currentPage.getMediaPlayer().stop();
                }
            }


        }

        currentPage = page;
        String currentPageName = currentPage.getPageName();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + currentPageName + "/" + currentPageName + ".fxml"));
        Parent root = loader.load();
        root.getStylesheets().add(getClass().getResource("/" + currentPageName + "/" + currentPageName + ".css").toExternalForm());

        Font.loadFont(
                getClass().getResource("/Images/Font/LillyBelle.ttf").toExternalForm(),
                10
        );

        currentPage = loader.getController();
        currentPage.setClient(this);
        currentPage.setGame(game);

        if (hasMusic) {
            currentPage.setMediaPlayer(playermp3);
            currentPage.setMediaView(mp3View);
        }
        Platform.runLater(
                () -> {
                    mainStage.getScene().setRoot(root);

                    if (currentPage.getMediaPlayer() != null) {
                        if (hasMusic == true) {
                            ((Pane) mainStage.getScene().getRoot()).getChildren().add(currentPage.getMediaView());
                            currentPage.getMediaPlayer().play();
                            currentPage.getMediaPlayer().setAutoPlay(true);
                        }
                    }
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

    public boolean getThreePlayers() {
        return threePlayers;
    }

    public void setThreePlayers(boolean threeP) {
        threePlayers = threeP;
    }

    /**
     * function that gets called when a username taken signal is received from the server
     *
     * @param response the server error response
     */
    @Override
    public synchronized void receiveUsernameTaken(String response) {

        Platform.runLater(
                () -> {
                    currentPage.showAlert();
                }
        );
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
            setCurrentPage(new LobbyPage(), null);
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

        if (game.getInGameDivinities().size() == 1) {
            try {
                setCurrentPage(new WaitingColorPage(), null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                setCurrentPage(new WaitingSingleDivinityChoicePage(), null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        notifyAll();
    }

    /**
     * function that gets called when a possible divinities signal is received from the server
     */
    @Override
    public synchronized void receivePossibleDivinities(String response) throws IOException {
        try {
            setCurrentPage(new WaitingDivinitiesChoicePage(), null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    @Override
    public void receiveStartingPosition(String position) {
        //notifyAll();
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
        //System.out.println("Received Model Update");
        game = g;

        if (game.getDisconnected() && !disconnected && !currentPage.getPageName().equals("Ending")) {
            disconnected = true;
            Platform.runLater(
                    () -> {
                        currentPage.showDisconnected();
                    }
            );
        }


        try {
            currentPage.setGame(g);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //int nPlayersInGame = getThreePlayers() ? 3 : 2;


        switch (currentPage.getPageName()) {

            case "WaitingDivinitiesChoice":
                if (game.getCurrentPlayer().getUsername().equals(playerUsername) && game.getInGameDivinities().size() == 0) {
                    System.out.println("Going to Divinities Page");
                    try {
                        setCurrentPage(new DivinitiesChoicePage(), null);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (game.getInGameDivinities().size() > 0) {
                    try {
                        setCurrentPage(new WaitingSingleDivinityChoicePage(), null);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case "DivinitiesChoice":
                if (game.getInGameDivinities().size() > 0) {
                    try {
                        setCurrentPage(new WaitingSingleDivinityChoicePage(), null);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "WaitingSingleDivinityChoice":
                if (game.getInGameDivinities().size() == 0) {
                    try {
                        setCurrentPage(new WaitingColorPage(), null);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (game.getCurrentPlayer().getUsername().equals(playerUsername)) {
                    try {
                        setCurrentPage(new SingleDivinityChoicePage(), null);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "WaitingColor":
                if (game.getAlreadyChosenColors().size() < game.getPlayers().size()) {
                    if (game.getCurrentPlayer().getUsername().equals(playerUsername)) {
                        try {
                            setCurrentPage(new ColorPage(), null);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
        }
    }


}