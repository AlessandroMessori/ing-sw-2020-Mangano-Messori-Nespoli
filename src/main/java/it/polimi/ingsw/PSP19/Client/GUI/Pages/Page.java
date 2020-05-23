package it.polimi.ingsw.PSP19.Client.GUI.Pages;

import it.polimi.ingsw.PSP19.Client.GUI.Client;
import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Utils.MessageSerializer;
import javafx.scene.control.Alert;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.IOException;

public abstract class Page {
    protected Game game;

    protected ServerAdapter serverAdapter;

    protected Client client;

    protected final MessageSerializer messageSerializer = new MessageSerializer();

    protected MediaView mediaView = null;

    protected MediaPlayer mediaPlayer = null;

    /**
     * returns the string identifier of the page
     *
     * @return the identifier of the page
     */
    public abstract String getPageName();

    /**
     * sets the internal state of the page
     *
     * @exception  IOException,InterruptedException error when setting the game
     * @param g the game to set
     */
    public void setGame(Game g) throws IOException, InterruptedException {
        game = g;
    }

    /**
     * sets the server adapter use to communicate with the server
     *
     * @param sAdapter the ServerAdapter to set
     */
    public void setServerAdapter(ServerAdapter sAdapter) {
        serverAdapter = sAdapter;
    }

    /**
     * sets the reference to the container in which the page is located
     *
     * @param cl the reference to the client
     */
    public void setClient(Client cl) {
        client = cl;
    }

    /**
     * shows an alert,to use when an username already taken event is received
     */
    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("USERNAME ALREADY TAKEN");
        alert.setHeaderText("Change your username");
        alert.setContentText("A player with the same username is already present in the lobby");

        alert.showAndWait();
    }

    /**
     * shows an alert,to use when an user disconnected event is received
     */
    public void showDisconnected() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("DISCONNECTION");
        alert.setHeaderText("A player disconnected himself from the game");
        alert.setContentText("GAME OVER!");

        alert.setOnCloseRequest(e -> {
            System.exit(0);
        });

        alert.showAndWait();
    }

    /**
     * returns the media player of the page
     *
     * @return the media player
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * returns the media component of the page
     *
     * @return the media view
     */
    public MediaView getMediaView() {
        return mediaView;
    }

    /**
     * sets the media player of the page
     *
     * @param m the media player to set
     */
    public void setMediaPlayer(MediaPlayer m) {
        mediaPlayer = m;
    }


    /**
     * sets the media view of the page
     *
     * @param mv the media view to set
     */
    public void setMediaView(MediaView mv) {
        mediaView = mv;
    }


}
