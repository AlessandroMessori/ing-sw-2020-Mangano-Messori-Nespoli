package it.polimi.ingsw.PSP19.Client.GUI.Pages;

import it.polimi.ingsw.PSP19.Client.GUI.Pages.Page;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class WaitingDivinitiesChoicePage extends Page implements Initializable {

    private URL layoutURL;

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

    @FXML
    private ImageView waiting;

    public WaitingDivinitiesChoicePage(){
    }

    public String getPageName() {
        return "WaitingDivinitiesChoice";
    }

    public void setGame(Game g) {
        game = g;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        ImageView[] buttonsArray = {div1, div2, div3, div4, div5, div6, div7, div8, div9, waiting};

        for(ImageView button : buttonsArray) button.setDisable(true);

    }


}
