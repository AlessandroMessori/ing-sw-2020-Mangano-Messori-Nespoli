package it.polimi.ingsw.PSP19.Client.GUI.Pages;

import it.polimi.ingsw.PSP19.Client.GUI.Pages.Page;
import it.polimi.ingsw.PSP19.Server.Model.Colour;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class WaitingColorPage extends Page implements Initializable {

    private URL layoutURL;

    @FXML
    private ImageView pawnTaken1;

    @FXML
    private ImageView pawnTaken2;

    @FXML
    private ImageView pawnTaken3;

    @FXML
    private ImageView pawnTaken4;

    @FXML
    private ImageView pawnTaken5;

    @FXML
    private ImageView chooseColorBtn1;

    @FXML
    private ImageView chooseColorBtn2;

    @FXML
    private ImageView chooseColorBtn3;

    @FXML
    private ImageView chooseColorBtn4;

    @FXML
    private ImageView chooseColorBtn5;

    @FXML
    private ImageView waiting;

    public WaitingColorPage() {
    }

    public String getPageName() {
        return "WaitingColor";
    }

    public void setGame(Game g) {
        game = g;

        ImageView[] chooseButtonsArray = {chooseColorBtn1, chooseColorBtn2, chooseColorBtn3, chooseColorBtn4, chooseColorBtn5};
        ImageView[] pawnTakenArray = {pawnTaken1, pawnTaken2, pawnTaken3, pawnTaken4, pawnTaken5};

        for (int i = 0; i < 5; i++) {
            Colour currentColor = getColorByIndex(i);

            if (game.getAlreadyChosenColors().contains(currentColor)) {
                pawnTakenArray[i].setImage(new Image("/Images/ColorChoice/already_taken.png"));
            }

            chooseButtonsArray[i].setDisable(true);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }

    private Colour getColorByIndex(int i) {
        switch (i) {
            case 0:
                return Colour.BLUE;
            case 1:
                return Colour.RED;
            case 2:
                return Colour.WHITE;
            case 3:
                return Colour.YELLOW;
            case 4:
                return Colour.PINK;
            default:
                return null;
        }
    }
}

