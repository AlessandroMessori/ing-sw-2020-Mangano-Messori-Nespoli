package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Utils.MessageSerializer;
import javafx.scene.control.Alert;
import javafx.scene.media.Media;
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

    public abstract String getPageName();

    public void setGame(Game g) throws IOException {
        game = g;
    }

    public void setServerAdapter(ServerAdapter sAdapter) {
        serverAdapter = sAdapter;
    }

    public void setClient(Client cl) {
        client = cl;
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("USERNAME ALREADY TAKEN");
        alert.setHeaderText("Change your username");
        alert.setContentText("A player with the same username is already present in the lobby");

        alert.showAndWait();
    }

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

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public MediaView getMediaView() {
        return mediaView;
    }

    public void setMediaPlayer(MediaPlayer m) {
        mediaPlayer = m;
    }

    public void setMediaView(MediaView mv) {
        mediaView = mv;
    }


}
