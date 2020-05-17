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

public class EndingPage extends Page implements Initializable {

    public Pane endingPageContainer;

    public String getPageName(){return "Ending";}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(game.getWinner().getUsername().equals(client.getPlayerUsername())){
            endingPageContainer.getStyleClass().add("winnerBackground");
        }
        else{
            endingPageContainer.getStyleClass().add("loserBackground");
        }
    }
}
