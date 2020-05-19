package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.Commands;
import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.Pane;
import javafx.scene.*;
import javafx.scene.paint.Paint;
import javafx.scene.media.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static java.lang.String.valueOf;

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
    CheckBox musicCheckBox;

    @FXML
    CheckBox effectsCheckBox;

    @FXML
    TextField anotherServerTextBox;

    @FXML
    ImageView connectButton;

    @FXML
    ImageView localServerBtn;

    public String getPageName() {
        return "Welcome";
    }

    public void localPressed(){
        localServerBtn.setImage(new Image("/Images/Login/localServer_pressed.png"));
        localServerKeepsPressed = true;
    }

    public void mouseEnteredLocal(){
        if(localServerKeepsPressed == true){
            localServerBtn.setImage(new Image("/Images/Login/localServer_pressed.png"));
        }
    }

    public void mouseExitedLocal(){
        if(localServerKeepsPressed == true && localServerIsSelected == false){
            localServerBtn.setImage(new Image("/Images/Login/localServer.png"));
        }
    }

    public void mouseEntered(){
        if(connectBtnIsSelected == true){
            connectButton.setImage(new Image("/Images/Login/Connect_Button_Pressed.png"));
        }
    }

    public void mouseExited(){
        if(connectBtnIsSelected == true){
            connectButton.setImage(new Image("/Images/Login/Connect_Button.png"));
        }
    }

    public void pressBtn(){
        connectBtnIsSelected = true;
        connectButton.setImage(new Image("/Images/Login/Connect_Button_Pressed.png"));
    }

    public void clickLocal(){
        if(localServerIsSelected == false) {
            localServerBtn.setImage(new Image("/Images/Login/localServer_pressed.png"));
            localServerIsSelected = true;
            if(!anotherServerTextBox.getText().equals("")) {
                anotherServerTextBox.setText("");
            }
        }
        else{
            localServerBtn.setImage(new Image("/Images/Login/localServer.png"));
            localServerIsSelected = false;
        }
    }

    public void anotherServer(){
        localServerBtn.setImage(new Image("/Images/Login/localServer.png"));
        localServerIsSelected = false;
    }

    public void playBtnClick() throws IOException, InterruptedException {
        connectButton.setImage(new Image("/Images/Login/Connect_Button_Pressed.png"));
        client.setThreePlayers(!twoPlayersButton.isSelected());
        String username = usernameTextBox.getText();
        boolean nPlayers = !twoPlayersButton.isSelected();
        if(username.length() > 12){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("INVALID USERNAME");
            alert.setHeaderText("Please insert a valid username.");
            alert.setContentText("You can't use a username with more than 12 characters.");

            alert.showAndWait();
            connectButton.setImage(new Image("/Images/Login/Connect_Button.png"));
        }
        else if(username.equals("")){
            //System.out.println("Insert a valid username");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("INVALID USERNAME");
            alert.setHeaderText("Please insert a valid username.");
            alert.setContentText("You can't leave the username space blank.");

            alert.showAndWait();
            connectButton.setImage(new Image("/Images/Login/Connect_Button.png"));
        }
        else if(localServerIsSelected == false && anotherServerTextBox.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("SELECT A SERVER");
            alert.setHeaderText("Please select local server or write an IP for another one.");
            alert.setContentText("You can't play without a server.");

            alert.showAndWait();
            connectButton.setImage(new Image("/Images/Login/Connect_Button.png"));
        }
        else{
            try {
                client.setPlayerUsername(username);
                if(localServerIsSelected == true) {
                    client.setServer("");
                }
                else{
                    try{ client.setServer(anotherServerTextBox.getText()); } catch (IllegalArgumentException | IOException IO){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("UNABLE TO ACCESS SERVER INSERTED");
                        alert.setHeaderText("Unluckily, it's impossible to reach the server.");
                        alert.setContentText("The server could be unreachable, or you could have failed to write it correctly.");
                    }
                }
                Thread.sleep(100);
                String message = messageSerializer.serializeJoinGame(username, nPlayers, null).toString();
                RequestHandler.getRequestHandler().updateRequest(Commands.JOIN_GAME, message);
            }catch(IOException | InterruptedException IO){
                //System.out.println("Not able to connect to local server");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("UNABLE TO ACCESS LOCAL SERVER");
                alert.setHeaderText("Unluckily, it looks like it's impossible to reach local server.");
                alert.setContentText("This error is really rare, you're lucky!");
            }
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        twoPlayersButton.setSelected(true);
        musicCheckBox.setSelected(true);
        effectsCheckBox.setSelected(true);
    }

}
