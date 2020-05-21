package it.polimi.ingsw.PSP19.Client.GUI.Pages;

import it.polimi.ingsw.PSP19.Client.Commands;
import it.polimi.ingsw.PSP19.Client.Controller.ClientController;
import it.polimi.ingsw.PSP19.Client.GUI.RequestHandler;
import it.polimi.ingsw.PSP19.Server.Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
    private boolean atlasPower = false;
    int pawnCounter = 0;
    int chosenPawnID = -1;

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

    @FXML
    private ImageView twoPlayersPanel;

    @FXML
    private ImageView threePlayersPanel;

    @FXML
    private ImageView extraBtn;

    @FXML
    private ImageView yourTurnBanner;


    public String getPageName() {
        return "Game";
    }

    private ImageView getGameGridCell(int x, int y, boolean pawn) {
        int offset = pawn ? 50 : 0;
        return (ImageView) gameGrid.getChildren().get(offset + x * 5 + y);
    }

    private ImageView getActionGridImage(int x, int y) {
        return (ImageView) gameGrid.getChildren().get(25 + x * 5 + y);
    }

    private ImageView getGameGridButton(int x, int y) {
        return (ImageView) gameGrid.getChildren().get(75 + x * 5 + y);
    }

    public void setGame(Game g) throws IOException, InterruptedException {

        if (game == null || (!localChanges)) {

            //start of new turn
            if (game != null && game.getNTurns() != g.getNTurns()) {
                chosenPawnID = -1;
                alreadySelectedPawn = false;

                if (g.getCurrentPlayer().getUsername().equals(client.getPlayerUsername()) && g.getNTurns() > 0) {
                    yourTurnBanner.setImage(new Image("/Images/Game/your_turn.png"));
                    yourTurnBanner.setDisable(false);

                    try {
                        Thread.sleep(1500);
                        yourTurnBanner.setImage(null);
                        yourTurnBanner.setDisable(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }

            game = g;
        }

        if (game != null && g != null) {

            if (game.getThreePlayers()) {
                if (twoPlayersPanel != null) {
                    twoPlayersPanel.setImage(null);
                }

            } else {
                if (threePlayersPanel != null) {
                    threePlayersPanel.setImage(null);
                }
            }

            String actionTextContent = "POSITION";

            if (game.getWinner() != null) {
                client.setCurrentPage(new EndingPage(), null);
            }

            // boolean to decide whether it's the client's turn to move
            gridActive = g.getCurrentPlayer() != null && client != null && g.getCurrentPlayer().getUsername().equals(client.getPlayerUsername());

            turnText.setText("Turn " + game.getNTurns());

            //turnBanner.setImage(gridActive ? new Image("/Images/Game/turnBanner.png") : null);

            boolean alreadySetFirstOpponentImage = false;

            if (gridActive && game.getCurrentPlayer().getDivinity() == Divinity.ATLAS && game.getNextMoves() != null && game.getNextMoves().size() > 0 && !game.getNextMoves().getMove(0).getIfMove()) {
                String imagePath = atlasPower ? "_on" : "";
                extraBtn.setDisable(false);
                extraBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/gp_builddome" + imagePath + ".png"));

                extraBtn.setOnMouseClicked(e -> {
                    atlasPower = !atlasPower;
                    final String finalImagePath = atlasPower ? "_on" : "";
                    extraBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/gp_builddome" + finalImagePath + ".png"));
                });

            } else if (gridActive && game.getNextMoves() != null && game.getNextMoves().size() > 0 && game.getNextMoves().getMove(game.getNextMoves().size() - 1).getX() == 6) {
                extraBtn.setDisable(false);
                extraBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/skip.png"));

                extraBtn.setOnMouseClicked(e -> {
                    skipMove();
                });

                extraBtn.setOnMousePressed(e -> {
                    extraBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/skip_pressed.png"));
                });

                extraBtn.setOnMouseReleased(e -> {
                    extraBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/skip.png"));
                });

            } else {
                extraBtn.setDisable(true);
                extraBtn.setImage(null);
            }


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
                } else if (game.getNextMoves().size() == 0) {
                    client.setCurrentPage(new EndingPage(), null);
                }

            }

            actionText.setText(actionTextContent);

            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    int finalI = i;
                    int finalJ = j;
                    ImageView currentTowerImage = getGameGridCell(i, j, false);
                    ImageView currentPawnImage = getGameGridCell(i, j, true);
                    ImageView currentMoveImage = getActionGridImage(i, j);
                    ImageView currentButton = getGameGridButton(i, j);
                    Cell currentCell = game.getNewGrid().getCells(i, j);

                    if (startingPosition) {
                        currentButton.setOnMouseClicked(e -> {
                            onStartingPositionCellClick(currentCell, currentPawnImage, finalI, finalJ);
                        });
                    } else {
                        currentButton.setOnMouseClicked(e -> {
                            try {
                                onGameCellClick(currentCell, currentPawnImage, finalI, finalJ);
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        });
                    }

                    drawCell(currentCell, currentTowerImage, currentPawnImage, currentMoveImage, i, j);
                    if (!gridActive || !alreadySelectedPawn) {
                        cleanMoveImages();
                    }

                }
            }
        }

    }

    public void onStartingPositionCellClick(Cell currentCell, ImageView currentPawnImage, int finalI, int finalJ) {

        if (currentCell.getPawn() == null && gridActive) {
            Pawn newPawn = new Pawn(game.getPlayers().getPlayer(game.getPlayers().searchPlayerByUsername(client.getPlayerUsername())));
            newPawn.getOwner().setColour(client.getChosenColor());
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
                //coeff used to select special moves for a certain coordinate
                boolean isAtlasBuilding = (game.getNextMoves() != null && game.getNextMoves().size() > 0 && !game.getNextMoves().getMove(0).getIfMove() && game.getCurrentPlayer().getDivinity() == Divinity.ATLAS && atlasPower);
                int cx = isAtlasBuilding ? (-1 * finalI) - 1 : finalI;
                int cy = isAtlasBuilding ? (-1 * finalJ) - 1 : finalJ;

                for (int k = 0; k < game.getNextMoves().size(); k++) {
                    if (game.getNextMoves().getMove(k).getX() == cx && game.getNextMoves().getMove(k).getY() == cy) {
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
                            ImageView moveImage = getActionGridImage(i, j);

                            Cell cell = game.getNewGrid().getCells(i, j);
                            drawCell(cell, towerImage, pawnImage, moveImage, i, j);
                            moveImage.setImage(null);
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
                        try {
                            client.setCurrentPage(new EndingPage(), null);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } else {
                // Selecting the Pawn to use in this turn
                if (currentCell.getPawn() != null && currentCell.getPawn().getOwner().getUsername().equals(client.getPlayerUsername())) {
                    gridActive = false;
                    actionText.setText("LOADING");
                    game.getCurrentPlayer().setCurrentPawn(currentCell.getPawn());
                    alreadySelectedPawn = true;
                    chosenPawnID = currentCell.getPawn().getId();
                    String message = messageSerializer.serializeChosenPawn(game.getCodGame(), client.getPlayerUsername(), currentCell.getPawn()).toString();
                    RequestHandler.getRequestHandler().updateRequest(Commands.SEND_CHOSEN_PAWN, message);
                }
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void skipMove() {
        if (gridActive) {
            Move nextMove = game.getNextMoves().getMove(game.getNextMoves().size() - 1);
            gridActive = false;
            actionText.setText("LOADING");

            String message = messageSerializer.serializeChosenMove(game, nextMove).toString();
            RequestHandler.getRequestHandler().updateRequest(Commands.SEND_CHOSEN_MOVE, message);
        }
    }

    public void drawCell(Cell currentCell, ImageView currentTowerImage, ImageView currentPawnImage, ImageView currentMoveImage, int i, int j) {

        //draws Move
        if (currentMoveImage != null) {
            if (game.getNextMoves() != null && game.getNextMoves().size() > 0) {
                for (int k = 0; k < game.getNextMoves().size(); k++) {
                    if (game.getNextMoves().getMove(k).getX() == i && game.getNextMoves().getMove(k).getY() == j) {
                        boolean build = !game.getNextMoves().getMove(k).getIfMove();
                        currentMoveImage.setImage(new Image(getMoveImagePath(client.getChosenColor(), build)));
                    }
                }
            }
        }

        // draws Tower

        if (currentCell.getTower().getIsDome() && currentCell.getTower().getLevel() != 4) {
            currentTowerImage.setImage(new Image("/Images/Game/Buildings/Dome.png"));
        } else if (currentCell.getTower().getLevel() > 0) {
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

    public void cleanMoveImages() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                getActionGridImage(i, j).setImage(null);
            }
        }
    }

    public String getMoveImagePath(Colour colour, boolean build) {
        return build ? "/Images/Game/Action/build.png" : "/Images/Game/Action/move_" + colour.toString().toLowerCase() + ".png";
    }

    public String getBuildingImagePath(int level) {
        return (level < 1 || level > 4) ? null : "/Images/Game/Buildings/Building" + level + "levels.png";
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