package it.polimi.ingsw.PSP19.Client.GUI.Pages;

import it.polimi.ingsw.PSP19.Client.GUI.Pages.Page;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Utils.CastingHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class WaitingSingleDivinityChoicePage extends Page implements Initializable {

    private URL layoutURL;

    @FXML
    private ImageView div31;

    @FXML
    private ImageView div32;

    @FXML
    private ImageView div33;

    @FXML
    private ImageView div21;

    @FXML
    private ImageView div22;

    @FXML
    private ImageView waiting;

    public WaitingSingleDivinityChoicePage() {
    }

    public String getPageName() {
        return "WaitingSingleDivinityChoice";
    }

    public void setGame(Game g) {
        game = g;

        int players;
        boolean chosen;
        ImageView[] divButtonsArray;

        //-- put in comment this to try the page
        ArrayList<String> possibleDivinities = CastingHelper.convertDivinityListToString(game.getPossibleDivinities());
        ArrayList<String> inGameDivinities = CastingHelper.convertDivinityListToString(game.getInGameDivinities());

        if (client.getThreePlayers()) {
            players = 3;
            divButtonsArray = new ImageView[]{div31, div32, div33};
            div21.setDisable(true);
            div22.setDisable(true);

        } else {
            players = 2;
            divButtonsArray = new ImageView[]{div21, div22};
            div31.setDisable(true);
            div32.setDisable(true);
            div33.setDisable(true);
        }



        for (int i = 0; i < players; i++) {
            chosen = false;

            for (String inGameDivinity : inGameDivinities) {
                if (possibleDivinities.get(i).equals(inGameDivinity)) {
                    chosen = true;
                    break;
                }
            }

            String divName = possibleDivinities.get(i).substring(0, 1) + possibleDivinities.get(i).toLowerCase().substring(1);

            if (!chosen) {
                divButtonsArray[i].setImage(new Image("/Images/DivChoice/" + divName + ".png"));
            } else {
                divButtonsArray[i].setImage(new Image("/Images/DivChoice/" + divName + "_chosen.png"));
            }
            divButtonsArray[i].setDisable(true);

        }

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}