package it.polimi.ingsw.PSP19.Client.GUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePage extends Page implements Initializable {

    @FXML
    private GridPane gameGrid;

    public String getPageName() {
        return "Game";
    }

    @FXML
    private void printCell(MouseEvent e) {
        Node source = (Node) e.getSource();
        //System.out.println(e.get);
    }

    // 12

    private ImageView getGameGridCell(int x, int y) {
        return (ImageView) gameGrid.getChildren().get(x * 5 + y);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int finalI = i;
                int finalJ = j;

                getGameGridCell(i,j).setOnMouseClicked(e -> {
                    System.out.printf("Clicked at Cell %d,%d\n", finalI,finalJ);
                });

                getGameGridCell(i,j).setImage(new Image("/Images/button-play-normal.png"));

            }
        }
    }
}

