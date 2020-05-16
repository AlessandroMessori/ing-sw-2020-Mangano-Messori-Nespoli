package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.Commands;
import it.polimi.ingsw.PSP19.Client.Controller.ClientController;
import it.polimi.ingsw.PSP19.Server.Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePage extends Page implements Initializable {

    private final ClientController clientController = new ClientController();
    private boolean startingPosition = true;
    private boolean alreadySelectedPawn = false;
    private boolean gridActive = true;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Text turnText;

    public String getPageName() {
        return "Game";
    }

    private ImageView getGameGridCell(int x, int y, boolean pawn) {
        int offset = pawn ? 25 : 0;
        return (ImageView) gameGrid.getChildren().get(offset + x * 5 + y);
    }

    public void setGame(Game g) {
        game = g;

        // boolean to decide whether it's the client's turn to move
        gridActive = g.getCurrentPlayer().getUsername().equals(client.getPlayerUsername());
    }

    public void onStartingPositionCellClick(Cell currentCell, ImageView currentPawnImage) {

        if (currentCell.getPawn() == null && gridActive) {
            gridActive = false;
            currentPawnImage.setImage(new Image(getPawnImagePath()));
            currentCell.setPawn(new Pawn(game.getCurrentPlayer()));
            String message = messageSerializer.serializeStartingPosition(game.getNewGrid(), "SendStartingPosition", client.getPlayerUsername(), game.getCodGame(), client.getChosenColor()).toString();
            RequestHandler.getRequestHandler().updateRequest(Commands.SEND_STARTING_POSITION, message);
        }

    }

    public void onGameCellClick(Cell currentCell, ImageView currentPawnImage, int finalI, int finalJ) {

        if (gridActive) {
            if (alreadySelectedPawn) {
                int selectedMove = -1;

                for (int k = 0; k < game.getNextMoves().size(); k++) {
                    if (game.getNextMoves().getMove(k).getX() == finalI && game.getNextMoves().getMove(k).getY() == finalJ) {
                        selectedMove = k;
                    }
                }

                if (selectedMove >= 0) {
                    Move nextMove = game.getNextMoves().getMove(selectedMove);
                    gridActive = false;
                    game = clientController.updateGameByMove(nextMove, game);
                    //TODO update grid graphics
                    String message = messageSerializer.serializeChosenMove(game, nextMove).toString();
                    RequestHandler.getRequestHandler().updateRequest(Commands.SEND_CHOSEN_MOVE, message);
                }

            }

        } else {
            // Selecting the Pawn to use in this turn
            if (currentCell.getPawn() != null && currentCell.getPawn().getOwner().getUsername().equals(game.getCurrentPlayer().getUsername())) {
                gridActive = false;
                String message = messageSerializer.serializeChosenPawn(game.getCodGame(), client.getPlayerUsername(), currentCell.getPawn()).toString();
                RequestHandler.getRequestHandler().updateRequest(Commands.SEND_CHOSEN_PAWN, message);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Player g1, g2;

        g1 = new Player("G1", Divinity.ARTEMIS, Colour.RED);
        g2 = new Player("G2", Divinity.DEMETER, Colour.BLUE);

        //turnText.setFont(Font.loadFont("/Images/Font/LillyBelle.ttf",120.0));

        game = new Game(0, null, false, null, new Grid(), new Grid(), null);
        game.getPlayers().addPlayer(g1);
        game.getPlayers().addPlayer(g2);
        /*game.getNewGrid().getCells(0, 3).getTower().setLevel(1);
        game.getNewGrid().getCells(0, 2).getTower().setLevel(2);
        game.getNewGrid().getCells(0, 1).getTower().setLevel(3);
        game.getNewGrid().getCells(0, 0).getTower().setLevel(4);
        game.getNewGrid().getCells(2, 1).getTower().setLevel(1);
        game.getNewGrid().getCells(1, 2).getTower().setLevel(2);
        game.getNewGrid().getCells(2, 1).getTower().setLevel(3);
        game.getNewGrid().getCells(4, 0).getTower().setLevel(4);
        game.getNewGrid().getCells(3, 4).setPawn(new Pawn(g1));
        game.getNewGrid().getCells(2, 1).setPawn(new Pawn(g1));*/

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                int finalI = i;
                int finalJ = j;
                ImageView currentTowerImage = getGameGridCell(i, j, false);
                ImageView currentPawnImage = getGameGridCell(i, j, true);
                Cell currentCell = game.getNewGrid().getCells(i, j);
                game.setCurrentPlayer(g1);

                if (startingPosition) {
                    currentPawnImage.setOnMouseClicked(e -> {
                        onStartingPositionCellClick(currentCell, currentPawnImage);
                    });

                    currentTowerImage.setOnMouseClicked(e -> {
                        onStartingPositionCellClick(currentCell, currentPawnImage);
                    });
                } else {
                    currentPawnImage.setOnMouseClicked(e -> {
                        onGameCellClick(currentCell, currentPawnImage, finalI, finalJ);
                    });

                    currentTowerImage.setOnMouseClicked(e -> {
                        onGameCellClick(currentCell, currentPawnImage, finalI, finalJ);
                    });
                }

                drawCell(currentCell, currentTowerImage, currentPawnImage);

            }
        }
    }


    public void drawCell(Cell currentCell, ImageView currentTowerImage, ImageView currentPawnImage) {
        // draws Tower
        if (currentCell.getTower().getLevel() > 0) {
            currentTowerImage.setImage(new Image(getBuildingImagePath(currentCell.getTower().getLevel())));
        }

        // draws Pawn
        if (currentCell.getPawn() != null) {
            currentPawnImage.setImage(new Image(getPawnImagePath()));
        }
    }

    public String getBuildingImagePath(int level) {
        switch (level) {
            case 1:
                return "/Images/Game/Buildings/Building1levels.png";
            case 2:
                return "/Images/Game/Buildings/Building2levels.png";
            case 3:
                return "/Images/Game/Buildings/Building3levels.png";
            case 4:
                return "/Images/Game/Buildings/Building4levels.png";
            default:
                return null;
        }
    }

    public String getPawnImagePath() {

        if (client == null) {
            return "/Images/Game/Pawns/MaleBuilder_blu.png";
        }

        switch (client.getChosenColor()) {
            case RED:
                return "/Images/Game/Pawns/MaleBuilder_red.png";
            case BLUE:
                return "/Images/Game/Pawns/MaleBuilder_blu.png";
            case YELLOW:
                return "/Images/Game/Pawns/MaleBuilder_yellow.png";
            case WHITE:
                return "/Images/Game/Pawns/MaleBuilder_white.png";
            case PINK:
                return "/Images/Game/Pawns/MaleBuilder_purple.png";
            default:
                return null;
        }
    }
}