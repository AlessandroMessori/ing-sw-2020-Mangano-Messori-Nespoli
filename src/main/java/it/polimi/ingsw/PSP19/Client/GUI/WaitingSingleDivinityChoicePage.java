package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.CLI.StringColor;
import it.polimi.ingsw.PSP19.Client.Commands;
import it.polimi.ingsw.PSP19.Server.Model.Grid;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Utils.CastingHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
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
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        int players;
        boolean chosen;
        ImageView[] divButtonsArray;

        //-- put in comment this to try the page
        //ArrayList<String> possibleDivinities = CastingHelper.convertDivinityListToString(game.getPossibleDivinities());
        //ArrayList<String> inGameDivinities = CastingHelper.convertDivinityListToString(game.getInGameDivinities());


        //-- to try the page de-comment this

        game = new Game(0, null, false, null, new Grid(), new Grid(), null);

        ArrayList<String> possibleDivinities = new ArrayList<>(); //add divinities, num of divinities ==players
        possibleDivinities.add("Apollo");
        possibleDivinities.add("Minotaur");
        //possibleDivinities.add("Pan");
        ArrayList<String> inGameDivinities = new ArrayList<>();
        //inGameDivinities.add("Minotaur");


        if (game.getThreePlayers()) {
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
            if (!chosen) {
                divButtonsArray[i].setImage(new Image("/Images/DivChoice/" + possibleDivinities.get(i) + ".png"));
            } else {
                divButtonsArray[i].setImage(new Image("/Images/DivChoice/" + possibleDivinities.get(i) + "_chosen.png"));
            }
            divButtonsArray[i].setDisable(true);

        }



    }
}