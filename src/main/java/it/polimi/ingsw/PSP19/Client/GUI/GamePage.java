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

        Player g1, g2;

        g1 = new Player("G1", Divinity.ARTEMIS, Colour.RED);
        g2 = new Player("G2", Divinity.DEMETER, Colour.BLUE);

        game = new Game(0, null, false, null, new Grid(), new Grid(), null);
        game.getPlayers().addPlayer(g1);
        game.getPlayers().addPlayer(g2);
        game.getNewGrid().getCells(0, 3).getTower().setLevel(1);
        game.getNewGrid().getCells(2, 1).getTower().setLevel(1);
        game.getNewGrid().getCells(3, 4).setPawn(new Pawn(g1));
        game.getNewGrid().getCells(2, 1).setPawn(new Pawn(g1));

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int finalI = i;
                int finalJ = j;
                ImageView currentTowerImage = getGameGridCell(i, j, false);
                ImageView currentPawnImage = getGameGridCell(i, j, true);
                Cell currentCell = game.getNewGrid().getCells(i, j);
                game.setCurrentPlayer(g1);

                currentPawnImage.setOnMouseClicked(e -> {
                    System.out.printf("Clicked at Cell %d,%d\n", finalI, finalJ);

                    if  (currentCell.getPawn() != null) {
                        System.out.println(currentCell.getPawn().getOwner().getUsername());
                        System.out.println(game.getCurrentPlayer());
                    }

                    if (alreadyChosenPawn) {
                        System.out.println("Already Chosen Pawn");
                    } else {
                        // Selecting The Pawn for this Turn
                        if (currentCell.getPawn() != null && currentCell.getPawn().getOwner().getUsername().equals(game.getCurrentPlayer().getUsername())) {
                            alreadyChosenPawn = true;
                            game.getCurrentPlayer().setCurrentPawn(currentCell.getPawn());
                            System.out.println("Selecting Pawn");
                        }
                    }

                });

                // draws Tower
                if (currentCell.getTower().getLevel() > 0) {
                    currentTowerImage.setImage(new Image("/Images/button-play-normal.png"));
                }

                // draws Pawn
                if (currentCell.getPawn() != null) {
                    currentPawnImage.setImage(new Image("/Images/logo.png"));
                }

            }        }
    }
}

