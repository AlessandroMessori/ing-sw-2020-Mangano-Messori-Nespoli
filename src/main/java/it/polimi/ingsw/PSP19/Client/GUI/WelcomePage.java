package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.Commands;
import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomePage extends Page implements Initializable {

    public Pane welcomePageContainer;

    @FXML
    private TextField nickField;

    @FXML
    private TextField ipField;

    @FXML
    private CheckBox nPlayersCheck;

    public String getPageName() {
        return "Welcome";
    }

    public void playBtnClick() {
        String username = nickField.getText();
        boolean nPlayers = nPlayersCheck.isSelected();

        String message = messageSerializer.serializeJoinGame(username, nPlayers, null).toString();

        RequestHandler.getRequestHandler().updateRequest(Commands.JOIN_GAME, message);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
