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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GamePage extends Page implements Initializable {

    private final ClientController clientController = new ClientController();
    private boolean startingPosition = true;
    private boolean alreadySelectedPawn = false;
    private boolean gridActive = true;
    private boolean localChanges = false;

    int pawnCounter = 0;

    @FXML
    private ImageView playerDivImage;

    @FXML
    private ImageView playerPowerImage;

    @FXML
    private Text playerUsernameText;

    @FXML
    private ImageView playerTurn;

    @FXML
    private ImageView opponentDivImage;

    @FXML
    private ImageView opponentPowerImage;

    @FXML
    private Text opponentUsernameText;

    @FXML
    private Text opponent1UsernameText;

    @FXML
    private Text opponent2UsernameText;

    @FXML
    private ImageView opponent1DivImage;

    @FXML
    private ImageView opponent2DivImage;

    @FXML
    private ImageView opponentTurn;

    @FXML
    private ImageView opponent1Turn;

    @FXML
    private ImageView opponent2Turn;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Text turnText;

    @FXML
    private Text actionText;


    public String getPageName() {
        return "Game";
    }

    private ImageView getGameGridCell(int x, int y, boolean pawn) {
        int offset = pawn ? 25 : 0;
        return (ImageView) gameGrid.getChildren().get(offset + x * 5 + y);
    }

    public void setGame(Game g) throws IOException {

        if (game == null || (!localChanges)) {

            if (game != null && game.getNTurns() != g.getNTurns()) {
                alreadySelectedPawn = false;
            }

            game = g;
        }

        if (game != null && g != null) {

            if (game.getCurrentPlayer().getColour() == null)
            {
                game.getCurrentPlayer().setColour(game.getPlayers().getPlayer(game.getPlayers().searchPlayerByUsername(game.getCurrentPlayer().getUsername())).getColour());
            }

            String actionTextContent = "POSITION";

            if (game.getWinner() != null) {
                client.setCurrentPage(new EndingPage(), null);
            }

            // boolean to decide whether it's the client's turn to move
            gridActive = g.getCurrentPlayer().getUsername().equals(client.getPlayerUsername());

            turnText.setText("Turn " + game.getNTurns());

            boolean alreadySetFirstOpponentImage = false;


            if (gridActive && game.getCurrentPlayer().getColour() != null) {
                playerTurn.setImage(new Image(getContourImagePath(game.getCurrentPlayer().getColour(), true)));
                opponentTurn.setImage(null);
                opponent1Turn.setImage(null);
                opponent2Turn.setImage(null);
            } else {
                playerTurn.setImage(null);
            }
            //Updates the divinity images
            for (int index = 0; index < game.getPlayers().size(); index++) {
                Player pl = game.getPlayers().getPlayer(index);
                String divName = pl.getDivinity().toString().substring(0, 1) + pl.getDivinity().toString().toLowerCase().substring(1);

                if (pl.getUsername().equals(client.getPlayerUsername())) {
                    //Sets The Player Image
                    playerDivImage.setImage(new Image("/Images/Game/Gods/Bigger/" + divName + ".png"));
                    playerUsernameText.setText(pl.getUsername());
                    //playerUsernameText.setTranslateX((225 - playerUsernameText.getWrappingWidth()) / 2 - 20);
                    playerPowerImage.setImage(new Image("/Images/Game/Gods/Bigger/Powers/" + divName + "_power.png"));
                } else {

                    if (game.getThreePlayers()) {
                        opponentDivImage.setImage(null);
                        opponentTurn.setImage(null);
                        opponentUsernameText.setText("");

                        if (alreadySetFirstOpponentImage) {
                            opponent2DivImage.setImage(new Image("/Images/Game/Gods/Smaller/" + divName + ".png"));
                            opponent2UsernameText.setText(pl.getUsername());

                            if (!gridActive && game.getCurrentPlayer().getUsername().equals(pl.getUsername()) && game.getCurrentPlayer().getColour() != null) {
                                opponent1Turn.setImage(null);
                                opponent2Turn.setImage(new Image(getContourImagePath(pl.getColour(), false)));
                            }


                        } else {
                            opponent1DivImage.setImage(new Image("/Images/Game/Gods/Smaller/" + divName + ".png"));
                            opponent1UsernameText.setText(pl.getUsername());

                            if (!gridActive && game.getCurrentPlayer().getUsername().equals(pl.getUsername()) && game.getCurrentPlayer().getColour() != null) {
                                opponent2Turn.setImage(null);
                                opponent1Turn.setImage(new Image(getContourImagePath(pl.getColour(), false)));
                            }
                            alreadySetFirstOpponentImage = true;
                        }

                    } else {

                        opponent1DivImage.setImage(null);
                        opponent2DivImage.setImage(null);
                        opponent1UsernameText.setText("");
                        opponent2UsernameText.setText("");

                        if (!gridActive && game.getCurrentPlayer().getColour() != null) {
                            opponent1Turn.setImage(null);
                            opponent2Turn.setImage(null);
                            opponentTurn.setImage(new Image(getContourImagePath(game.getCurrentPlayer().getColour(), true)));
                        }

                        // Sets Opponent Image for a 2 Players Game
                        opponentDivImage.setImage(new Image("/Images/Game/Gods/Bigger/" + divName + ".png"));
                        opponentUsernameText.setText(pl.getUsername());
                        //opponentUsernameText.setTranslateX((225 - opponentUsernameText.getWrappingWidth()) / 2 - 20);
                        opponentPowerImage.setImage(new Image("/Images/Game/Gods/Bigger/Powers/" + divName + "_power.png"));
                    }


                }

            }


            if (pawnCounter == 2) {

                if (!alreadySelectedPawn) {
                    actionTextContent = "SELECT";
                } else if (game.getNextMoves() != null && game.getNextMoves().size() > 0) {

                    for (int ind = 0; ind < game.getNextMoves().size(); ind++) {
                        Move mv = game.getNextMoves().getMove(ind);
                        System.out.print("(" + mv.getX() + "," + mv.getY() + ") ");
                    }
                    System.out.println("");

                    actionTextContent = game.getNextMoves().getMove(0).getIfMove() ? "MOVE" : "BUILD";
                }

            }

            actionText.setText(actionTextContent);

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
                            try {
                                onGameCellClick(currentCell, currentPawnImage, finalI, finalJ);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        });

                        currentTowerImage.setOnMouseClicked(e -> {
                            try {
                                onGameCellClick(currentCell, currentPawnImage, finalI, finalJ);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        });
                    }

                    drawCell(currentCell, currentTowerImage, currentPawnImage);

                }
            }
        }

    }

    public void onStartingPositionCellClick(Cell currentCell, ImageView currentPawnImage, int finalI, int finalJ) {

        if (currentCell.getPawn() == null && gridActive) {
            System.out.println(new Gson().toJson(game.getPlayers()));
            Pawn newPawn = new Pawn(game.getPlayers().getPlayer(game.getPlayers().searchPlayerByUsername(client.getPlayerUsername())));
            newPawn.setId(getNewPawnId());
            currentCell.setPawn(newPawn);
            game.getNewGrid().setCells(currentCell, finalI, finalJ);
            currentPawnImage.setImage(new Image(getPawnImagePath(client.getChosenColor(), newPawn.getId())));
            localChanges = true;
            pawnCounter++;

            if (pawnCounter == 2) {
                gridActive = false;
                actionText.setText("LOADING");
                startingPosition = false;
                System.out.println(client.getChosenColor());
                String message = messageSerializer.serializeStartingPosition(game.getNewGrid(), "SendStartingPosition", client.getPlayerUsername(), game.getCodGame(), client.getChosenColor()).toString();
                RequestHandler.getRequestHandler().updateRequest(Commands.SEND_STARTING_POSITION, message);
                localChanges = false;
            }
        }

    }

    public void onGameCellClick(Cell currentCell, ImageView currentPawnImage, int finalI, int finalJ) throws IOException {

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
                    //nextMove.setToMove(game.getCurrentPlayer().getCurrentPawn());
                    gridActive = false;
                    actionText.setText("LOADING");
                    game = clientController.updateGameByMove(nextMove, game);

                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            ImageView towerImage = getGameGridCell(i, j, false);
                            ImageView pawnImage = getGameGridCell(i, j, true);
                            Cell cell = game.getNewGrid().getCells(i, j);
                            drawCell(cell, towerImage, pawnImage);
                        }
                    }

                    if (game.getCurrentPlayer().getDivinity() == Divinity.DEMETER && game.getGameTurn().getNPossibleBuildings() == 1) {
                        Move cantBuildMove = new Move(game.getCurrentPlayer().getCurrentPawn());
                        cantBuildMove.setX(nextMove.getX());
                        cantBuildMove.setY(nextMove.getY());
                        cantBuildMove.setIfMove(false);
                        game.getGameTurn().setCantBuildOnThisBlock(cantBuildMove);

                        for (int x = 0; x < 5; x++) { //sending data for Demeter second building
                            for (int y = 0; y < 5; y++) {
                                if (game.getNewGrid().getCells(x, y).getPawn() != null) {
                                    if (game.getCurrentPlayer().getCurrentPawn().getId() == game.getNewGrid().getCells(x, y).getPawn().getId()) {
                                        nextMove.setX(x);
                                        nextMove.setY(y);
                                    }
                                }
                            }
                        }

                    }


                    String message = messageSerializer.serializeChosenMove(game, nextMove).toString();
                    RequestHandler.getRequestHandler().updateRequest(Commands.SEND_CHOSEN_MOVE, message);

                    if (game.getWinner() != null) {
                        client.setCurrentPage(new EndingPage(), null);
                    }
                }

            } else {
                // Selecting the Pawn to use in this turn
                if (currentCell.getPawn() != null && currentCell.getPawn().getOwner().getUsername().equals(client.getPlayerUsername())) {
                    gridActive = false;
                    actionText.setText("LOADING");
                    String message = messageSerializer.serializeChosenPawn(game.getCodGame(), client.getPlayerUsername(), currentCell.getPawn()).toString();
                    RequestHandler.getRequestHandler().updateRequest(Commands.SEND_CHOSEN_PAWN, message);
                    alreadySelectedPawn = true;
                }
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
        } else {
            currentTowerImage.setImage(null);
        }

        // draws Pawn
        if (currentCell.getPawn() != null) {

            Colour currentCellPawnColour;

            if (client.getPlayerUsername().equals(currentCell.getPawn().getOwner().getUsername())) {
                currentCellPawnColour = client.getChosenColor();
            } else {
                currentCellPawnColour = game.getPlayers().getPlayer(game.getPlayers().searchPlayerByUsername(currentCell.getPawn().getOwner().getUsername())).getColour();
            }

            currentPawnImage.setImage(new Image(getPawnImagePath(currentCellPawnColour, currentCell.getPawn().getId())));
        } else {
            currentPawnImage.setImage(null);
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

    public String getPawnImagePath(Colour colour, int pawnID) {

        String gender = (pawnID % 2 == 0) ? "Female" : "Male";

        switch (colour) {
            case RED:
                return "/Images/Game/Pawns/" + gender + "Builder_red.png";
            case BLUE:
                return "/Images/Game/Pawns/" + gender + "Builder_blu.png";
            case YELLOW:
                return "/Images/Game/Pawns/" + gender + "Builder_yellow.png";
            case WHITE:
                return "/Images/Game/Pawns/" + gender + "Builder_white.png";
            case PINK:
                return "/Images/Game/Pawns/" + gender + "Builder_purple.png";
            default:
                return null;
        }
    }

    public String getContourImagePath(Colour colour, boolean big) {
        String bigImage = big ? "" : "Small";
        String bigPath = big ? "Bigger" : "Smaller";

        return "/Images/Game/Gods/" + bigPath + "/currentPlayer" + bigImage + "_" + colour.toString().toLowerCase() + ".png";
    }

    public int getNewPawnId() {
        int randInt;
        boolean idNotValid;
        int max = (int) Math.pow(10, 5);
        int min = (int) Math.pow(10, 4);
        ArrayList<Integer> takenPawnId = new ArrayList<Integer>();

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (game.getNewGrid().getCells(x, y).getPawn() != null) {
                    takenPawnId.add(game.getNewGrid().getCells(x, y).getPawn().getId());
                }
            }
        }

        if (pawnCounter == 0) {
            //first pawn have an odd id
            do {
                randInt = (int) (Math.random() * (max - min + 1) + min);
                idNotValid = false;
                for (int idInExam : takenPawnId) {
                    if (idInExam == randInt) {
                        idNotValid = true;
                        break;
                    }
                }
            } while ((randInt % 2 != 1) && (!idNotValid));
        } else {
            //second pawn have an even id
            do {
                randInt = (int) (Math.random() * (max - min + 1) + min);
                idNotValid = false;
                for (int idInExam : takenPawnId) {
                    if (idInExam == randInt) {
                        idNotValid = true;
                        break;
                    }
                }
            } while ((randInt % 2 != 0) && (!idNotValid));
        }

        return randInt;
    }
}