package it.polimi.ingsw.PSP19.Client.GUI;

import com.google.gson.Gson;
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
    private boolean localChanges = false;

    int pawnCounter = 0;

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

        if (game == null || (!localChanges)) {
            game = g;
        }

        if (game != null) {

            // boolean to decide whether it's the client's turn to move
            gridActive = g.getCurrentPlayer().getUsername().equals(client.getPlayerUsername());
            turnText.setText("Turn " + game.getNTurns());


            game.getPlayers().getPlayer(game.getPlayers().searchPlayerByUsername(client.getPlayerUsername())).getDivinity();


            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    int finalI = i;
                    int finalJ = j;
                    ImageView currentTowerImage = getGameGridCell(i, j, false);
                    ImageView currentPawnImage = getGameGridCell(i, j, true);
                    Cell currentCell = game.getNewGrid().getCells(i, j);

                    if (startingPosition) {
                        currentPawnImage.setOnMouseClicked(e -> {
                            onStartingPositionCellClick(currentCell, currentPawnImage, finalI, finalJ);
                        });

                        currentTowerImage.setOnMouseClicked(e -> {
                            onStartingPositionCellClick(currentCell, currentPawnImage, finalI, finalJ);
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

    }

    public void onStartingPositionCellClick(Cell currentCell, ImageView currentPawnImage, int finalI, int finalJ) {

        if (currentCell.getPawn() == null && gridActive) {
            pawnCounter++;
            currentPawnImage.setImage(new Image(getPawnImagePath(client.getChosenColor())));
            currentCell.setPawn(new Pawn(game.getCurrentPlayer()));
            game.getNewGrid().setCells(currentCell, finalI, finalJ);
            localChanges = true;

            if (pawnCounter == 2) {
                gridActive = false;
                System.out.println(client.getChosenColor());
                String message = messageSerializer.serializeStartingPosition(game.getNewGrid(), "SendStartingPosition", client.getPlayerUsername(), game.getCodGame(), client.getChosenColor()).toString();
                RequestHandler.getRequestHandler().updateRequest(Commands.SEND_STARTING_POSITION, message);
                localChanges = false;
            }
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

    }


    public void drawCell(Cell currentCell, ImageView currentTowerImage, ImageView currentPawnImage) {
        // draws Tower
        if (currentCell.getTower().getLevel() > 0) {
            currentTowerImage.setImage(new Image(getBuildingImagePath(currentCell.getTower().getLevel())));
        }

        // draws Pawn
        if (currentCell.getPawn() != null) {

            Colour currentCellPawnColour;

            if (client.getPlayerUsername().equals(currentCell.getPawn().getOwner().getUsername())) {
                currentCellPawnColour = client.getChosenColor();
            } else {
                currentCellPawnColour = game.getPlayers().getPlayer(game.getPlayers().searchPlayerByUsername(currentCell.getPawn().getOwner().getUsername())).getColour();
            }

            currentPawnImage.setImage(new Image(getPawnImagePath(currentCellPawnColour)));
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

    public String getPawnImagePath(Colour colour) {

        switch (colour) {
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