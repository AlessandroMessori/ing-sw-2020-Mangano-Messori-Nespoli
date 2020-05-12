package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Server.Model.*;
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

    private boolean alreadyChosenPawn = false;

    public String getPageName() {
        return "Game";
    }

    private ImageView getGameGridCell(int x, int y, boolean pawn) {
        int offset = pawn ? 25 : 0;
        return (ImageView) gameGrid.getChildren().get(offset + x * 5 + y);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        game = new Game(0, null, false, null, new Grid(), new Grid(), null);
        game.getNewGrid().getCells(0, 3).getTower().setLevel(1);
        game.getNewGrid().getCells(2, 1).getTower().setLevel(1);
        game.getNewGrid().getCells(3,4).setPawn(new Pawn(new Player("G1",Divinity.ARTEMIS,Colour.RED)));
        game.getNewGrid().getCells(2,1).setPawn(new Pawn(new Player("G1",Divinity.ARTEMIS,Colour.RED)));

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int finalI = i;
                int finalJ = j;
                ImageView currentTowerImage = getGameGridCell(i, j,false);
                ImageView currentPawnImage = getGameGridCell(i,j,true);
                Cell currentCell = game.getNewGrid().getCells(i, j);

                currentTowerImage.setOnMouseClicked(e -> {
                    System.out.printf("Clicked at Cell %d,%d\n", finalI, finalJ);

                    /*if (alreadyChosenPawn) {

                    } else {
                        if (currentCell.getPawn().getId() == game.getCurrentPlayer().getCurrentPawn().getId()) {

                        }
                    }*/

                });

                // draws Tower
                if (currentCell.getTower().getLevel() > 0) {
                    currentTowerImage.setImage(new Image("/Images/button-play-normal.png"));
                }

                // draws Pawn
                if (currentCell.getPawn() != null) {
                    currentPawnImage.setImage(new Image("/Images/logo.png"));
                }

            }
        }
    }
}

