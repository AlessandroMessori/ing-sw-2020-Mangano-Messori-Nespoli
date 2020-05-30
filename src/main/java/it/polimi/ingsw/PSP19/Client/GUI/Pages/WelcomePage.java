package it.polimi.ingsw.PSP19.Client.GUI.Pages;

import it.polimi.ingsw.PSP19.Client.Commands;
import it.polimi.ingsw.PSP19.Client.GUI.Pages.Page;
import it.polimi.ingsw.PSP19.Client.GUI.RequestHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePage extends Page implements Initializable {

    private boolean connectBtnIsSelected = true;

    private boolean localServerIsSelected = false;

    private boolean localServerKeepsPressed = true;

    public Pane welcomePageContainer;

    @FXML
    TextField usernameTextBox;

    @FXML
    RadioButton twoPlayersButton;

    @FXML
    TextField anotherServerTextBox;

    @FXML
    ImageView connectButton;

    @FXML
    ImageView localServerBtn;

    public String getPageName() {
        return "Welcome";
    }

    public void localPressed() {
        localServerBtn.setImage(new Image("/Images/Login/localServer_pressed.png"));
        localServerKeepsPressed = true;
    }

    public void mouseEnteredLocal() {
        if (localServerKeepsPressed == true) {
            localServerBtn.setImage(new Image("/Images/Login/localServer_pressed.png"));
        }
    }

    public void mouseExitedLocal() {
        if (localServerKeepsPressed == true && localServerIsSelected == false) {
            localServerBtn.setImage(new Image("/Images/Login/localServer.png"));
        }
    }

    public void mouseEntered() {
        if (connectBtnIsSelected == true) {
            connectButton.setImage(new Image("/Images/Login/Connect_Button_Pressed.png"));
        }
    }

    public void mouseExited() {
        if (connectBtnIsSelected == true) {
            connectButton.setImage(new Image("/Images/Login/Connect_Button.png"));
        }
    }

    public void pressBtn() {
        connectBtnIsSelected = true;
        connectButton.setImage(new Image("/Images/Login/Connect_Button_Pressed.png"));
    }

    public void clickLocal() {
        if (localServerIsSelected == false) {
            localServerBtn.setImage(new Image("/Images/Login/localServer_pressed.png"));
            localServerIsSelected = true;
            if (!anotherServerTextBox.getText().equals("")) {
                anotherServerTextBox.setText("");
            }
        } else {
            localServerBtn.setImage(new Image("/Images/Login/localServer.png"));
            localServerIsSelected = false;
        }
    }


    public void anotherServer() {
        localServerBtn.setImage(new Image("/Images/Login/localServer.png"));
        localServerIsSelected = false;
    }

    public void playBtnClick() throws IOException, InterruptedException {
        connectButton.setImage(new Image("/Images/Login/Connect_Button_Pressed.png"));
        /*if (!musicCheckBox.isSelected()) {
            client.setHasMusic(false);
        }*/
        client.setThreePlayers(!twoPlayersButton.isSelected());
        String username = usernameTextBox.getText();
        boolean nPlayers = !twoPlayersButton.isSelected();
        if (username.length() > 12) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("INVALID USERNAME");
            alert.setHeaderText("Please insert a valid username.");
            alert.setContentText("You can't use a username with more than 12 characters.");

            alert.showAndWait();
            connectButton.setImage(new Image("/Images/Login/Connect_Button.png"));
        } else if (username.equals("")) {
            //System.out.println("Insert a valid username");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("INVALID USERNAME");
            alert.setHeaderText("Please insert a valid username.");
            alert.setContentText("You can't leave the username space blank.");

            alert.showAndWait();
            connectButton.setImage(new Image("/Images/Login/Connect_Button.png"));
        } else if (localServerIsSelected == false && anotherServerTextBox.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("SELECT A SERVER");
            alert.setHeaderText("Please select local server or write an IP for another one.");
            alert.setContentText("You can't play without a server.");

            alert.showAndWait();
            connectButton.setImage(new Image("/Images/Login/Connect_Button.png"));
        } else {
            client.setPlayerUsername(username);
            String serverIp = localServerIsSelected ? "" : anotherServerTextBox.getText();

            try {
                client.setServer(serverIp);
                Thread.sleep(300);
                String message = messageSerializer.serializeJoinGame(username, nPlayers, null).toString();
                RequestHandler.getRequestHandler().updateRequest(Commands.JOIN_GAME, message);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("UNABLE TO ACCESS SERVER INSERTED");
                alert.setHeaderText("Unluckily, it's impossible to reach the server.");
                alert.setContentText("The server could be unreachable, or you could have failed to write it correctly.");
                alert.showAndWait();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        twoPlayersButton.setSelected(true);
        /*musicCheckBox.setSelected(true);
        effectsCheckBox.setSelected(true);*/
    }

}
