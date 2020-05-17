package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.Commands;
import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePage extends Page implements Initializable {

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

    public String getPageName() {
        return "Welcome";
    }

    public void playBtnClick() throws IOException {
        String username = usernameTextBox.getText();
        boolean nPlayers = !twoPlayersButton.isSelected();
        if(username.equals("")){
            System.out.println("Insert a valid username");
        }
        else{
            try {
                client.setPlayerUsername(username);
                client.setServer("");
                Thread.sleep(100);
                String message = messageSerializer.serializeJoinGame(username, nPlayers, null).toString();
                RequestHandler.getRequestHandler().updateRequest(Commands.JOIN_GAME, message);
            }catch(IOException | InterruptedException IO){
                System.out.println("Not able to connect to local server");
            }
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        twoPlayersButton.setSelected(true);
    }

}
