package it.polimi.ingsw.PSP19.Client.GUI.Pages;

import it.polimi.ingsw.PSP19.Client.Commands;
import it.polimi.ingsw.PSP19.Client.Controller.ClientController;
import it.polimi.ingsw.PSP19.Client.GUI.RequestHandler;
import it.polimi.ingsw.PSP19.Server.Model.*;
import it.polimi.ingsw.PSP19.Utils.GuiHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GamePage extends Page implements Initializable {

    private final ClientController clientController = new ClientController();
    private boolean startingPosition = true;
    private boolean alreadySelectedPawn = false;
    private boolean alreadySelectedCanComeUp = true;
    private boolean loadingNewGrid = false;
    private boolean gridActive = true;
    private boolean localChanges = false;
    private boolean powerSwitch = false;
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
    private ImageView confirmBtn;

    @FXML
    private ImageView yourTurnBanner;


    public String getPageName() {
        return "Game";
    }

    public void setGame(Game g) throws IOException, InterruptedException {


        System.out.println(g.getnMoves());
        gridActive = g.getCurrentPlayer() != null && client != null && g.getCurrentPlayer().getUsername().equals(client.getPlayerUsername());


        if (game == null || startingPosition || !gridActive || (game != null && game.getnMoves() < g.getnMoves())) {

            if (game != null && (!localChanges && game.getNTurns() < g.getNTurns())) {
                // start of a new turn,updating the GUI
                showNewTurnBanner(g);
                modelUpdaterThread.setModelCheck(true);
            }

            //data updates
            if (turnText != null) {
                // boolean to decide whether it's the client's turn to move
                if (gridActive && ((game != null && game.getnMoves() < g.getnMoves()))) {
                    modelUpdaterThread.setModelCheck(false);
                }

                if (game != null) {
                    System.out.println(game.getnMoves() + " " + g.getnMoves());
                }

                if (game == null || (!(startingPosition && pawnCounter < 2) && game.getnMoves() == 0) || (!localChanges) || (game.getnMoves() < g.getnMoves()) || (game.getNTurns() < g.getNTurns())) {
                    System.out.println("Updating Game!");


                    game = g;

                    //updates the GUI based on the opponent moves
                    opponentMoveGUIUpdate();

                    //defines the behaviour of the Extra Button
                    setExtraBtnAction();

                    //defines the behaviour of the Grid
                    setGridActions();


                }

            }
        }


    }


    /**
     * Returns the ImageView of the specified Cell
     *
     * @param x    x coordinate of the ImageView
     * @param y    y coordinate of the ImageView
     * @param pawn if true returns Pawn ImageView,if false returns Tower ImageView
     * @return ImageView  of the cell at coordinates x,y
     */
    private ImageView getGameGridCell(int x, int y, boolean pawn) {
        int offset = pawn ? 50 : 0;
        return (ImageView) gameGrid.getChildren().get(offset + x * 5 + y);
    }

    /**
     * Returns the Action ImageView of the specified Cell
     *
     * @param x x coordinate of the ImageView
     * @param y y coordinate of the ImageView
     * @return ImageView of the Action of the cell at coordinates x,y
     */
    private ImageView getActionGridImage(int x, int y) {
        return (ImageView) gameGrid.getChildren().get(25 + x * 5 + y);
    }

    /**
     * Returns the Button of the specified Cell
     *
     * @param x x coordinate of the Button
     * @param y y coordinate of the Button
     * @return Button of the cell at coordinates x,y
     */
    private ImageView getGameGridButton(int x, int y) {
        return (ImageView) gameGrid.getChildren().get(75 + x * 5 + y);
    }

    /**
     * Updates the GUI when a new turn starts
     */
    private void showNewTurnBanner(Game g) {
        //start of new turn

        chosenPawnID = -1;
        alreadySelectedPawn = false;

        if (g.getCurrentPlayer().getUsername().equals(client.getPlayerUsername()) && g.getNTurns() > 0) {
            yourTurnBanner.setImage(new Image("/Images/Game/your_turn.png"));
            yourTurnBanner.setDisable(false);

            alreadySelectedCanComeUp = !(g.getCurrentPlayer().getDivinity() == Divinity.PROMETHEUS);

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {

                            yourTurnBanner.setImage(null);
                            yourTurnBanner.setDisable(true);
                        }
                    },
                    1500
            );

            System.out.println("Starting Turn");

        }

        //System.out.println(new Gson().toJson(game));
    }


    /**
     * Updates the GUI when the opponent makes a new move
     */
    private void opponentMoveGUIUpdate() throws IOException, InterruptedException {

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

        if (turnText != null) {
            turnText.setText("Turn " + game.getNTurns());
        }

        boolean alreadySetFirstOpponentImage = false;

        if (gridActive && game.getCurrentPlayer().getColour() != null) {
            playerTurn.setImage(new Image(GuiHelper.getContourImagePath(game.getCurrentPlayer().getColour(), true)));
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
                        setDivImages(pl, divName, opponent2DivImage, opponent2UsernameText, opponent1Turn, opponent2Turn);


                    } else {
                        setDivImages(pl, divName, opponent1DivImage, opponent1UsernameText, opponent2Turn, opponent1Turn);
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
                        opponentTurn.setImage(new Image(GuiHelper.getContourImagePath(game.getCurrentPlayer().getColour(), true)));
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
            actionTextContent = getCurrentActionText();
        }

        actionText.setText(actionTextContent);

    }

    /**
     * Sets the event handler for the extra button  in this turn
     */
    private void setExtraBtnAction() {

        if (!startingPosition && game.getCurrentPlayer().getDivinity() == Divinity.PROMETHEUS && !alreadySelectedCanComeUp) {
            String imagePath = powerSwitch ? "_on" : "";
            extraBtn.setDisable(false);
            extraBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/gp_buildbeforemove" + imagePath + ".png"));
            confirmBtn.setDisable(false);
            confirmBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/confirm.png"));

            extraBtn.setOnMouseClicked(e -> {
                powerSwitch = !powerSwitch;
                final String finalImagePath = powerSwitch ? "_on" : "";
                extraBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/gp_buildbeforemove" + finalImagePath + ".png"));
            });

            confirmBtn.setOnMouseClicked(e -> {
                gridActive = false;
                actionText.setText("LOADING");
                alreadySelectedCanComeUp = true;
                String message = messageSerializer.serializeDecideCanComeUp(!powerSwitch, game.getCodGame()).toString();
                RequestHandler.getRequestHandler().updateRequest(Commands.SEND_DECIDES_TO_COME_UP, message);
                localChanges = false;

            });

            confirmBtn.setOnMousePressed(e -> confirmBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/confirm_pressed.png")));

            confirmBtn.setOnMouseReleased(e -> confirmBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/confirm.png")));
        } else if (gridActive && game.getCurrentPlayer().getDivinity() == Divinity.ATLAS && game.getNextMoves() != null && game.getNextMoves().size() > 0 && !game.getNextMoves().getMove(0).getIfMove()) {
            String imagePath = powerSwitch ? "_on" : "";
            extraBtn.setDisable(false);
            extraBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/gp_builddome" + imagePath + ".png"));

            extraBtn.setOnMouseClicked(e -> {
                powerSwitch = !powerSwitch;
                final String finalImagePath = powerSwitch ? "_on" : "";
                extraBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/gp_builddome" + finalImagePath + ".png"));
            });

        } else if (gridActive && game.getNextMoves() != null && game.getNextMoves().size() > 0 && game.getNextMoves().getMove(game.getNextMoves().size() - 1).getX() == 6) {
            extraBtn.setDisable(false);
            extraBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/skip.png"));

            extraBtn.setOnMouseClicked(e -> skipMove());

            extraBtn.setOnMousePressed(e -> extraBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/skip_pressed.png")));

            extraBtn.setOnMouseReleased(e -> extraBtn.setImage(new Image("/Images/Game/Gods/Bigger/Powers/skip.png")));

        } else {
            extraBtn.setDisable(true);
            extraBtn.setImage(null);
            confirmBtn.setDisable(true);
            confirmBtn.setImage(null);
        }

    }

    /**
     * Sets the event handler for the game grid in this turn
     */
    private void setGridActions() {
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
                    currentButton.setOnMouseClicked(e -> onStartingPositionCellClick(currentCell, currentPawnImage, finalI, finalJ));
                } else {
                    currentButton.setOnMouseClicked(e -> {
                        try {
                            onGameCellClick(currentCell, finalI, finalJ);
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

    /**
     * Sets the Images of the Divinities ImageViews
     *
     * @param pl                    player the divinity ImageView belongs to
     * @param divName               divinity name
     * @param opponent1DivImage     ImageView of the divinity of the first opponent in 3 player games
     * @param opponent1Turn         ImageView of the turn selector of the first opponent in 3 player games
     * @param opponent1UsernameText Text of the first opponent in 3 player games
     * @param opponent2Turn         ImageView of the turn selector of the second opponent in 3 player games
     */
    private void setDivImages(Player pl, String divName, ImageView opponent1DivImage, Text opponent1UsernameText, ImageView opponent2Turn, ImageView opponent1Turn) {
        opponent1DivImage.setImage(new Image("/Images/Game/Gods/Smaller/" + divName + ".png"));
        opponent1UsernameText.setText(pl.getUsername());

        if (!gridActive && game.getCurrentPlayer().getUsername().equals(pl.getUsername()) && game.getCurrentPlayer().getColour() != null) {
            opponent2Turn.setImage(null);
            opponent1Turn.setImage(new Image(GuiHelper.getContourImagePath(pl.getColour(), false)));
        }
    }

    /**
     * Event handler for a click on a cell of the grid during the starting position phase
     *
     * @param currentCell      the model cell being clicked
     * @param currentPawnImage the pawn image of the cell being clicked
     * @param finalI           x coordinate of the cell being clicked
     * @param finalJ           y coordinate of the cell being clicked
     */
    private void onStartingPositionCellClick(Cell currentCell, ImageView currentPawnImage, int finalI, int finalJ) {

        if (currentCell.getPawn() == null && gridActive && pawnCounter < 2) {
            Pawn newPawn = new Pawn(game.getPlayers().getPlayer(game.getPlayers().searchPlayerByUsername(client.getPlayerUsername())));
            newPawn.getOwner().setColour(client.getChosenColor());
            newPawn.setId(GuiHelper.getNewPawnId(game, pawnCounter));
            currentCell.setPawn(newPawn);
            game.getNewGrid().setCells(currentCell, finalI, finalJ);
            currentPawnImage.setImage(new Image(GuiHelper.getPawnImagePath(client.getChosenColor(), newPawn.getId())));
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

    /**
     * Event handler for a click on a cell of the grid during the game
     *
     * @param currentCell the model cell being clicked
     * @param finalI      x coordinate of the cell being clicked
     * @param finalJ      y coordinate of the cell being clicked
     */
    private void onGameCellClick(Cell currentCell, int finalI, int finalJ) throws IOException {

        if (gridActive && alreadySelectedCanComeUp && !loadingNewGrid && game.getWinner() == null) {
            if (alreadySelectedPawn) {
                int selectedMove = -1;
                //coeff used to select special moves for a certain coordinate
                boolean isAtlasBuilding = (game.getNextMoves() != null && game.getNextMoves().size() > 0 && !game.getNextMoves().getMove(0).getIfMove() && game.getCurrentPlayer().getDivinity() == Divinity.ATLAS && powerSwitch);
                int cx = isAtlasBuilding ? (-1 * finalI) - 1 : finalI;
                int cy = isAtlasBuilding ? (-1 * finalJ) - 1 : finalJ;

                for (int k = 0; k < Objects.requireNonNull(game.getNextMoves()).size(); k++) {
                    if (game.getNextMoves().getMove(k).getX() == cx && game.getNextMoves().getMove(k).getY() == cy) {
                        selectedMove = k;
                    }
                }

                if (selectedMove >= 0) {
                    Move nextMove = game.getNextMoves().getMove(selectedMove);
                    //nextMove.setToMove(game.getCurrentPlayer().getCurrentPawn());
                    gridActive = false;
                    actionText.setText("LOADING");
                    loadingNewGrid = true;
                    game = clientController.updateGameByMove(nextMove, game);
                    loadingNewGrid = false;

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


                    localChanges = false;

                    if (game.getWinner() != null) {
                        gridActive = false;
                    }

                    String message = messageSerializer.serializeChosenMove(game, nextMove).toString();
                    RequestHandler.getRequestHandler().updateRequest(Commands.SEND_CHOSEN_MOVE, message);
                }

            } else {
                // Selecting the Pawn to use in this turn
                if (currentCell.getPawn() != null && currentCell.getPawn().getOwner().getUsername().equals(client.getPlayerUsername())) {
                    gridActive = false;
                    actionText.setText("LOADING");
                    game.getCurrentPlayer().setCurrentPawn(currentCell.getPawn());
                    alreadySelectedPawn = true;
                    chosenPawnID = currentCell.getPawn().getId();
                    String message = messageSerializer.serializeChosenPawn(game.getCodGame(), client.getPlayerUsername(), currentCell.getPawn(), finalI, finalJ).toString();
                    RequestHandler.getRequestHandler().updateRequest(Commands.SEND_CHOSEN_PAWN, message);
                    localChanges = false;

                }
            }

        }
    }

    /**
     * Skip Button Click Event
     */
    private void skipMove() {
        if (gridActive) {
            Move nextMove = game.getNextMoves().getMove(game.getNextMoves().size() - 1);
            gridActive = false;
            actionText.setText("LOADING");


            String message = messageSerializer.serializeChosenMove(game, nextMove).toString();
            RequestHandler.getRequestHandler().updateRequest(Commands.SEND_CHOSEN_MOVE, message);
            localChanges = false;

        }
    }

    /**
     * Calculates the text of the action Text
     *
     * @return text of the current move
     */
    private String getCurrentActionText() throws IOException, InterruptedException {
        if (game.getCurrentPlayer().getDivinity() == Divinity.PROMETHEUS && !alreadySelectedCanComeUp) {
            return "DECIDE";
        } else if (!alreadySelectedPawn) {
            return "SELECT";
        } else if (game.getNextMoves() != null && game.getNextMoves().size() > 0) {
            return game.getNextMoves().getMove(0).getIfMove() ? "MOVE" : "BUILD";
        } else {
            if (game.getNextMoves() != null && game.getNextMoves().size() == 0) {
                client.setCurrentPage(new EndingPage(), null);
            }
            return null;
        }
    }

    /**
     * Draw a single Cell of the game board
     *
     * @param currentCell       the model cell
     * @param currentTowerImage ImageView of the tower of the cell
     * @param currentPawnImage  ImageView of the pawn of the cell
     * @param currentMoveImage  ImageView of the move of the cell
     * @param i                 x coordinate of the cell
     * @param j                 y coordinate of the cell
     */
    private void drawCell(Cell currentCell, ImageView currentTowerImage, ImageView currentPawnImage, ImageView currentMoveImage, int i, int j) {

        //draws Move
        if (currentMoveImage != null && game.getWinner() == null) {
            if (game.getNextMoves() != null && game.getNextMoves().size() > 0) {
                for (int k = 0; k < game.getNextMoves().size(); k++) {
                    if (game.getNextMoves().getMove(k).getX() == i && game.getNextMoves().getMove(k).getY() == j) {
                        boolean build = !game.getNextMoves().getMove(k).getIfMove();
                        currentMoveImage.setImage(new Image(GuiHelper.getMoveImagePath(client.getChosenColor(), build)));
                    }
                }
            }
        }

        // draws Tower

        if (currentCell.getTower().getIsDome() || currentCell.getTower().getLevel() > 0) {
            currentTowerImage.setImage(new Image(GuiHelper.getBuildingImagePath(currentCell.getTower().getLevel(), currentCell.getTower().getIsDome())));
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

            currentPawnImage.setImage(new Image(GuiHelper.getPawnImagePath(currentCellPawnColour, currentCell.getPawn().getId())));
        } else {
            currentPawnImage.setImage(null);
        }
    }

    /**
     * Sets all the images of the moves to null
     */
    private void cleanMoveImages() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                getActionGridImage(i, j).setImage(null);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}