package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.GUI.Pages.*;
import it.polimi.ingsw.PSP19.Client.Network.PingService;
import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;
import it.polimi.ingsw.PSP19.Client.Network.ServerObserver;
import it.polimi.ingsw.PSP19.Server.Model.*;
import it.polimi.ingsw.PSP19.Server.Server;
import it.polimi.ingsw.PSP19.Utils.MessageDeserializer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
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
    double scaleFactor = 0;
    ModelUpdaterThread modelUpdater;
    Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //adapts size to smaller screens
        if (primaryScreenBounds.getWidth() < width || primaryScreenBounds.getHeight() < height) {
            scaleFactor = 0.7;
        }

        game = new Game(0, null, false, null, new Grid(), new Grid(), null);
        mainStage = primaryStage;

        root = setCurrentPage(new WelcomePage(), "/Music/Menu/Atmospheric_fantasy_music_-_Ocean_Palace.mp3");

        Scene scene;
        if (scaleFactor != 0) {
            scene = new Scene(root, width * scaleFactor, height * scaleFactor);
        } else {
            scene = new Scene(root, width, height);
        }
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
     * RequestHandler observer: makes requests to the server based on the data of requestHandler
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


    /***
     * Sets the server the client will be connecting to
     *
     * @throws IOException error when connecting to the server
     * @param ip ip address of the server
     *
     */
    public void setServer(String ip) throws IOException {
        /* open a connection to the server */
        try {
            server = new Socket(ip, Server.SOCKET_PORT);

            /* Create the adapter that will allow communication with the server
             * in background, and start running its thread */
            serverAdapter = new ServerAdapter(server);
            serverAdapter.addObserver(this);
            Thread serverAdapterThread = new Thread(serverAdapter);
            serverAdapterThread.start();
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }

    }

    /***
     * Sets the current Scene
     *
     * @exception IOException,InterruptedException error when setting the scene
     *
     * @param page the page the set
     * @param musicPath path of the soundtrack of the page
     *
     * @return Parent node of the set page
     */
    public Parent setCurrentPage(Page page, String musicPath) throws IOException, InterruptedException {

        /*Media media = null;                     //MUSIC
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


        }*/

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
        currentPage.setModelUpdaterThread(modelUpdater);
        currentPage.setGame(game);

        /*if (hasMusic) {
            currentPage.setMediaPlayer(playermp3);
            currentPage.setMediaView(mp3View);
        }*/
        Platform.runLater(
                () -> {
                    mainStage.getScene().setRoot(root);
                    mainStage.setResizable(true);
                    if (scaleFactor != 0) {
                        mainStage.setWidth(width * scaleFactor);
                        mainStage.setHeight(height * scaleFactor);
                        mainStage.setResizable(false);
                        Scale scale = new Scale(scaleFactor, scaleFactor);
                        scale.setPivotX(0);
                        scale.setPivotY(0);
                        root.getTransforms().setAll(scale);
                    }
                    /*if (currentPage.getMediaPlayer() != null) {
                        if (hasMusic == true) {
                            ((Pane) mainStage.getScene().getRoot()).getChildren().add(currentPage.getMediaView());
                            currentPage.getMediaPlayer().play();
                            currentPage.getMediaPlayer().setAutoPlay(true);
                        }
                    }*/
                }
        );

        return root;
    }


    /**
     * returns the value of the property hasMusic
     *
     * @return value of hasMusic
     */
    public boolean getHasMusic() {
        return hasMusic;
    }

    /**
     * sets the value of the property hasMusic
     *
     * @param hasMusic value of hasMusic
     */
    public void setHasMusic(boolean hasMusic) {
        this.hasMusic = hasMusic;
    }


    /**
     * returns the value of the property chosenColor
     *
     * @return value of chosenColor
     */
    public Colour getChosenColor() {
        return chosenColor;
    }

    /**
     * sets the value of the property chosenColor
     *
     * @param cColor value of chosenColor
     */
    public void setChosenColor(Colour cColor) {
        chosenColor = cColor;
    }

    /**
     * returns the value of the property playerUsername
     *
     * @return value of playerUsername
     */
    public String getPlayerUsername() {
        return playerUsername;
    }

    /**
     * sets the value of the property playerUsername
     *
     * @param uName value of playerUsername
     */
    public void setPlayerUsername(String uName) {
        playerUsername = uName;
    }

    /**
     * returns the value of the property threePlayers
     *
     * @return value of threePlayers
     */
    public boolean getThreePlayers() {
        return threePlayers;
    }

    /**
     * sets the value of the property threePlayers
     *
     * @param threeP value of threePlayers
     */
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

         modelUpdater = new ModelUpdaterThread(gameID, serverAdapter); //starts a thread periodically checking for Model updates
        Thread modelUpdaterThread = new Thread(modelUpdater);

        modelUpdater.setModelCheck(true);
        modelUpdaterThread.start();

        PingService pingService = new PingService(gameID, serverAdapter); //starts a thread pinging the server
        Thread pingServiceThread = new Thread(pingService);
        pingServiceThread.start();

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
        Game game = (new MessageDeserializer()).deserializeObject(pawn, "game", Game.class);

        try {
            currentPage.setGame(game);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (pawn.equals("You Lost")) {
            System.out.println("You don't have any possible move!");
            //currentPage = Pages.LOADINGMOVE;
        } else if (pawn.equals("This pawn doesn't have any possible moves,choosing the other one")) {
            System.out.println(pawn + "\n");
        }
        notifyAll();
    }

    @Override
    public synchronized void receiveStartingPosition(String position) {
        notifyAll();
    }

    /**
     * function that gets called when an canComeUp signal is received from the server
     */
    public synchronized void receiveCanComeUp(String canComeUp) {
        Game game = (new MessageDeserializer()).deserializeObject(canComeUp, "game", Game.class);
        System.out.println("Received Can Come Up Response!");
        try {
            currentPage.setGame(game);
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyAll();
    }

    /**
     * function that gets called when an new move signal is received from the server
     */
    public synchronized void receiveMoves(String moves) {
        Game game = (new MessageDeserializer()).deserializeObject(moves, "game", Game.class);
        System.out.println("Received Moves Response!");
        try {
            currentPage.setGame(game);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        if (game.getWinner() != null) {
            game = g;
            try {
                setCurrentPage(new EndingPage(), null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        try {
            currentPage.setGame(g);

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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void receivePing(String ping) {
        notifyAll();
    }


}