package it.polimi.ingsw.PSP19.Client.GUI.Pages;

import it.polimi.ingsw.PSP19.Client.GUI.Pages.Page;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class EndingPage extends Page implements Initializable {

    public Pane endingPageContainer;

    public String getPageName() {
        return "Ending";
    }

    public void setGame(Game g) {
        game = g;
        if (game.getWinner().getUsername().equals(client.getPlayerUsername())) {
            endingPageContainer.getStyleClass().add("winnerBackground");
        } else {
            endingPageContainer.getStyleClass().add("loserBackground");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
