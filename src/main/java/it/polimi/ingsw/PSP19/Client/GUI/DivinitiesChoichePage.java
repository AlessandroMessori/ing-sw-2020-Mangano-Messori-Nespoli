package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Server.Model.Grid;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;



public class DivinitiesChoichePage extends Page implements Initializable {

    private URL layoutURL;

    @FXML
    private ImageView goBtn;

    @FXML
    private ImageView div1;

    @FXML
    private ImageView div2;

    @FXML
    private ImageView div3;

    @FXML
    private ImageView div4;

    @FXML
    private ImageView div5;

    @FXML
    private ImageView div6;

    @FXML
    private ImageView div7;

    @FXML
    private ImageView div8;

    @FXML
    private ImageView div9;

    public DivinitiesChoichePage(){

    }

    public String getPageName() {
        return "DivinitiesChoice";
    }

    public void setGame(Game g) {
        game = g;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new Game(0, null, false, null, new Grid(), new Grid(), null);


        ImageView[] divButtonsArray = {div1, div2, div3, div4, div5, div6, div7, div8, div9};

        for (int i = 0; i < 9; i++) {
            int finalI = i;

            divButtonsArray[i].setOnMousePressed(e -> {
                divButtonsArray[finalI].setImage(new Image("/Images/DivChoice" + getDivinityStringByIndex(finalI) + "_chosen.png"));
            });

            divButtonsArray[i].setOnMouseReleased(e -> {
                divButtonsArray[finalI].setImage(new Image("/Images/DivChoice/" + getDivinityStringByIndex(finalI) + ".png"));
            });


        }
    }

    private String getDivinityStringByIndex(int i) {
        switch (i) {
            case 0:
                return "Apollo";
            case 1:
                return "Artemis";
            case 2:
                return "Athena";
            case 3:
                return "Atlas";
            case 4:
                return "Demeter";
            case 5:
                return "Hephaestus";
            case 6:
                return "Minotaur";
            case 8:
                return "Pan";
            case 9:
                return "Prometheus";
            default:
                return null;
        }
    }

}
