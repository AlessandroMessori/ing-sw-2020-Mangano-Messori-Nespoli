package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Server.Model.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LobbyPage extends Page implements Initializable {

    @FXML
    private ListView<String> lobbyList;

    @FXML
    private Text codGameText;

    private ObservableList<String> lobbyItems = FXCollections.observableArrayList();

    private URL layoutURL;

    public LobbyPage() {
    }

    public String getPageName() {
        return "Lobby";
    }

    public void setGame(Game g) {
        game = g;

        lobbyItems = FXCollections.observableArrayList();

        // add the players from the model to the lobby list
        for (int i = 0; i < game.getPlayers().size(); i++) {
            lobbyItems.add(game.getPlayers().getPlayer(i).getUsername());
        }

        lobbyList.setItems(lobbyItems);

        codGameText.setText("GAME ID:" + game.getCodGame());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}

