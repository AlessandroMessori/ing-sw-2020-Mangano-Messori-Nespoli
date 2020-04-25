package Server.Controller;

import Server.Model.*;

import java.util.ArrayList;
import java.util.Random;

public class ServerController {


    private ArrayList<Divinity> inGameDivinities;


    /**
     * @param length length of wanted random string
     * @return a random string
     */
    public static String randomString(int length) {
        Random rnd = new Random();
        char[] arr = new char[length];

        for (int i = 0; i < length; i++) {
            int n = rnd.nextInt(36);
            arr[i] = (char) (n < 10 ? '0' + n : 'a' + n - 10);
        }

        return new String(arr);
    }


    /**
     * adds the player to the game
     *
     * @param p   player to add
     * @param if3 boolean which checks if the player wants to play in a 2 or a 3 players game
     * @return the Game to which the player is added
     */
    public Game addPlayerToModel(Player p, boolean if3) {        //TODO SYNCHRONIZED
        String x;
        Model model = Model.getModel();

        if (model.getGames().size() == 0) {       //starting case
            model.addGame(new Game(0, x = randomString(10), if3, null, new Grid(), new Grid(), null));
            model.searchID(x).getPlayers().addPlayer(p);
            return (model.searchID(x));
        }
        for (Game g : model.getGames()) {
            if (!g.getThreePlayers() && !if3) {           //if 2 players
                if (g.getPlayers().size() < 2) {
                    g.getPlayers().addPlayer(p);
                    return g;
                }
            } else if (g.getThreePlayers() && if3) {       //if 3 players
                if (g.getPlayers().size() < 3) {
                    g.getPlayers().addPlayer(p);
                    return g;
                }
            } else {
                x = randomString(10);
                while (model.searchID(x) != null) {
                    x = randomString(10);
                }
                model.addGame(new Game(0, x, if3, null, null, null, null));
                model.searchID(x).getPlayers().addPlayer(p);
                return model.searchID(x);
            }
        }
        return null;
    }

    /**
     * @param divinity the divinity to add to the inGameDivinities list
     */
    public void setInGameDivinities(Divinity divinity) {
        inGameDivinities.add(divinity);
    }

    /**
     * @return the list of Divinities
     */
    public ArrayList<Divinity> getInGameDivinities() {
        return inGameDivinities;
    }

    /**
     * deletes a divinity from inGameDivinities
     *
     * @param div the divinity to remove
     */
    public void deleteDivinity(Divinity div) {
        inGameDivinities.remove(div);
    }

    /**
     * @param grid grid on which to calculate the next possible move(s), based on the player's divinity
     * @return the possible MoveList
     */
    public MoveList calculateNextMove(Grid grid, Player p, String gameID, Move move, Turn turn) {
        MoveList movelist = new MoveList();
        Model model = Model.getModel();
        Game game = model.searchID(gameID);

        if (game.getCurrentPlayer().getDivinity() == Divinity.ARTEMIS && game.getGameTurn().getNMovesMade() == 0) {
            game.getGameTurn().setCantMoveBackHere(move);
        }

        if (move.getIfMove()) {              //IF MOVEMENT
            movelist = new MoveList();
            /*turn.canItComeUp(grid, move); //TODO: PUT OUTSIDE THIS FUNCTION, WHEN CALCULATENEXTMOVE() IS CALLED
            if (!turn.getCanComeUp()) {
                if (turn.getCanBuildBeforeMove()) {
                    move.setIfMove(false);
                    calculateNextMove(grid,p,gameID,move,turn);
                    move.setIfMove(true);
                    //TODO: BUILD BEFORE MOVE
                }
            }*/

            if (turn.getNPossibleMoves() > 0) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (0 <= (move.getX() + i) && (move.getX() + i) <= 4 && 0 <= (move.getY() + j) && (move.getY() + j) <= 4) {
                            if (grid.getCells(move.getX() + i, move.getY() + j).getTower().getLevel() <= grid.getCells(move.getX(), move.getY()).getTower().getLevel() + 1) {
                                if (!grid.getCells(move.getX() + i, move.getY() + j).getTower().getIsDome()) {
                                    if ((grid.getCells(move.getX() + i, move.getY() + j).getPawn() == null) || ((game.getCurrentPlayer().getDivinity() == Divinity.APOLLO) && !game.getCurrentPlayer().getUsername().equals(grid.getCells(move.getX() + i, move.getY() + j).getPawn().getOwner().getUsername()))) {
                                        /*if (game.getCurrentPlayer().getDivinity() != Divinity.ARTEMIS || (game.getGameTurn().getNMovesMade() == 0) || ((move.getX() + i != game.getGameTurn().getCantMoveBackHere().getX()) && move.getY() + j != game.getGameTurn().getCantMoveBackHere().getY())) {*/
                                            if (game.getCurrentPlayer().getDivinity() == Divinity.ATHENA || !turn.getPawnMoved() || grid.getCells(move.getX() + i, move.getY() + j).getTower().getLevel() <= grid.getCells(move.getX(), move.getY()).getTower().getLevel()) {
                                                if(i == 0 && j == 0){
                                                    continue;
                                                }
                                                if(game.getGameTurn().getNMovesMade() == 1 && game.getCurrentPlayer().getDivinity() == Divinity.ARTEMIS && ((move.getX() + i == game.getGameTurn().getCantMoveBackHere().getX()) && move.getY() + j == game.getGameTurn().getCantMoveBackHere().getY())){
                                                    continue;
                                                }
                                                Move possMove = new Move(move.getToMove());

                                                if(game.getGameTurn().getEnemyPawn1() == null) {        //TODO: ADD ENEMYPLAYER3 FOR APOLLO
                                                    if (game.getCurrentPlayer().getDivinity() == Divinity.APOLLO && grid.getCells(move.getX() + i, move.getY() + j).getPawn() != null) {
                                                        if(!grid.getCells(move.getX() + i, move.getY() + j).getPawn().getOwner().getUsername().equals(game.getCurrentPlayer().getUsername()))
                                                        {
                                                            game.getGameTurn().setEnemyPawn1(grid.getCells(move.getX() + i, move.getY() + j).getPawn());
                                                        }
                                                    }
                                                }

                                                if(game.getGameTurn().getEnemyPawn1() != null){
                                                    if (game.getCurrentPlayer().getDivinity() == Divinity.APOLLO && grid.getCells(move.getX() + i, move.getY() + j).getPawn() != null) {
                                                        if(!grid.getCells(move.getX() + i, move.getY() + j).getPawn().getOwner().getUsername().equals(game.getCurrentPlayer().getUsername()))
                                                        {
                                                            game.getGameTurn().setEnemyPawn2(grid.getCells(move.getX() + i, move.getY() + j).getPawn());
                                                        }
                                                    }
                                                }

                                                possMove.setIfMove(true);
                                                possMove.setX(move.getX() + i);
                                                possMove.setY(move.getY() + j);
                                                movelist.addMove(possMove);
                                            }
                                        //}
                                    }
                                }
                            }
                        }
                    }
                }

                if(game.getCurrentPlayer().getDivinity() == Divinity.ARTEMIS && game.getGameTurn().getNMovesMade() == 1){
                    Move possMove = new Move(move.getToMove());

                    possMove.setIfMove(true);
                    possMove.setX(6);
                    possMove.setY(6);
                    movelist.addMove(possMove);
                }

                turn.setNPossibleMoves(turn.getNPossibleMoves() - 1);
                turn.setNMovesMade(turn.getNMovesMade() + 1);
            }
        } else if (!move.getIfMove()) {             //BUILDING MOVE //TODO: ADD IF(...&& NMADEBUILDINGS == 0)
            movelist = new MoveList();
            if (game.getCurrentPlayer().getDivinity() == Divinity.ATLAS) {        //ATLAS EFFECT
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (0 <= (move.getX() + i) && (move.getX() + i) <= 4 && 0 <= (move.getY() + j) && (move.getY() + j) <= 4) {
                            if (grid.getCells(move.getX() + i, move.getY() + j).getPawn() == null && !grid.getCells(move.getX() + i,move.getY() + j).getTower().getIsDome()) {
                                if (game.getAvailableDomes() > 0) {
                                    Move possMove = new Move(null);

                                    possMove.setIfMove(false);
                                    possMove.setX(-(move.getX() + i) - 1);
                                    possMove.setY(-(move.getY() + j) - 1);
                                    movelist.addMove(possMove);
                                }
                            }
                        }
                    }
                }
            }
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (0 <= move.getX() + i && move.getX() + i <= 4 && 0 <= move.getY() + j && move.getY() + j <= 4) {
                        if (grid.getCells(move.getX() + i, move.getY() + j).getPawn() == null && !grid.getCells(move.getX() + i, move.getY() + j).getTower().getIsDome()) {
                            /*if (game.getCurrentPlayer().getDivinity() != Divinity.DEMETER || ((turn.getCantBuildOnThisBlock().getX() != move.getX() + i) && (turn.getCantBuildOnThisBlock().getY() != move.getY() + j))) {*/
                                if(i == 0 && j == 0){
                                    continue;               //TO NOT BUILD UNDER THE PAWN; COULD NOT WORK
                                }

                                if(game.getGameTurn().getNMadeBuildings() == 1 && game.getCurrentPlayer().getDivinity() == Divinity.DEMETER && ((turn.getCantBuildOnThisBlock().getX() == move.getX() + i) && (turn.getCantBuildOnThisBlock().getY() == move.getY() + j))){
                                    continue;
                                }

                                if (grid.getCells(move.getX() + i, move.getY() + j).getTower().getLevel() == 0) {
                                    if (game.getAvailableLevel1Buildings() > 0) {
                                        Move possMove = new Move(null);

                                        possMove.setIfMove(false);
                                        possMove.setX(move.getX() + i);
                                        possMove.setY(move.getY() + j);

                                        movelist.addMove(possMove);

                                    }
                                }
                                if (grid.getCells(move.getX() + i, move.getY() + j).getTower().getLevel() == 1) {
                                    if (game.getAvailableLevel2Buildings() > 0) {
                                        Move possMove = new Move(null);

                                        possMove.setIfMove(false);
                                        possMove.setX(move.getX() + i);
                                        possMove.setY(move.getY() + j);

                                        movelist.addMove(possMove);

                                    }
                                }

                                if (grid.getCells(move.getX() + i, move.getY() + j).getTower().getLevel() == 2) {
                                    if (game.getAvailableLevel3Buildings() > 0) {
                                        Move possMove = new Move(null);

                                        possMove.setIfMove(false);
                                        possMove.setX(move.getX() + i);
                                        possMove.setY(move.getY() + j);

                                        movelist.addMove(possMove);

                                    }
                                }

                                if (grid.getCells(move.getX() + i, move.getY() + j).getTower().getLevel() == 3) {
                                    if (game.getAvailableDomes() > 0) {
                                        Move possMove = new Move(null);

                                        possMove.setIfMove(false);
                                        possMove.setX(move.getX() + i);
                                        possMove.setY(move.getY() + j);

                                        movelist.addMove(possMove);

                                    }
                                //}
                            }
                        }
                    }

                }
            }
            if(game.getCurrentPlayer().getDivinity() == Divinity.DEMETER && game.getGameTurn().getNMadeBuildings() == 1){
                Move possMove = new Move(move.getToMove());

                possMove.setIfMove(false);
                possMove.setX(6);
                possMove.setY(6);
                movelist.addMove(possMove);
            }

            turn.setNMadeBuildings(turn.getNMadeBuildings() + 1);
            turn.setNPossibleBuildings(turn.getNPossibleBuildings() - 1);
        }

        //ALTERNATIVE FOR DEMETER

        /*else if(move.getIfMove() == false && game.getCurrentPlayer().getDivinity() == Divinity.DEMETER && turn.getNMadeBuildings() == 1){
            Move moveP = new Move(move.getToMove());
            for(int i = 0; i <= 4; i++){
                for(int j = 0; j <= 4; j++){
                    if(grid.getCells(i,j).getPawn().getId() == move.getToMove().getId()){
                        moveP.setX(i);
                        moveP.setY(j);
                    }
                }
            }
            for(int i = 0; i <= 1; i++){
                for(int j = 0; j <= 1; j++){
                    if (0 <= moveP.getX() + i && moveP.getX() + i <= 4 && 0 <= moveP.getY() + j && moveP.getY() + j <= 4){
                        if(grid.getCells(moveP.getX() + i, moveP.getY() + j).getPawn() == null){
                            if(!grid.getCells(moveP.getX() + i,moveP.getY() + j).getTower().getIsDome()){
                                if(moveP.getX() + i == move.getX() && moveP.getY() + j == move.getY()){
                                    continue;
                                }
                                //TODO BUILDING MOVE
                            }
                        }
                    }
                }
            }
        }*/

        return movelist;
    }

    /**
     * @param p   player to add the divinity
     * @param div divinity to add to the player
     */
    public void addDivinityToPlayer(Player p, Divinity div) {
        p.setDivinity(div);
        deleteDivinity(div);
    }

    /**
     * @param grid   new grid after the move
     * @param gameID the ID of the current game
     */
    public void updateModelGrid(Grid grid, String gameID) {
        Model model = Model.getModel();
        Game game = model.searchID(gameID);
        game.setOldGrid(game.getNewGrid());
        game.setNewGrid(grid);          //TODO: SEND THE NEW GRID TO OTHER PLAYERS?
    }

    /**
     * @param gameID   game ID of the game where to set the player's divinity
     * @param username of the player
     * @param div      divinity to assign to the player
     */
    public void setSpecificPlayerDiv(String gameID, String username, Divinity div) throws IllegalArgumentException {
        Model model = Model.getModel();
        Game game = model.searchID(gameID);
        for (int i = 0; i < game.getPlayers().size(); i++) {
            if (game.getPlayers().getPlayer(i).getUsername().equals(username)) {
                if (game.getPlayers().getPlayer(i).getDivinity() == null) {
                    game.getPlayers().getPlayer(i).setDivinity(div);
                    return;
                } else {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    /**
     *
     * @param grid the grid where to see the pawns
     * @return a PlayerList of players that already placed pawns
     */
    public PlayerList getPlayersThatAlreadyPlaced(Grid grid) {
        PlayerList playerList = new PlayerList();
        boolean alreadyContained = false;
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                if (grid.getCells(i, j).getPawn() != null) {
                    if (playerList.size() == 0) {
                        playerList.addPlayer(grid.getCells(i, j).getPawn().getOwner());
                    } else {
                        alreadyContained = false;
                        for (int k = 0; k <= playerList.size() - 1; k++) {
                            if (playerList.getPlayer(k).getUsername().equals(grid.getCells(i, j).getPawn().getOwner().getUsername())) {
                                alreadyContained = true;
                            }
                        }
                        if (!alreadyContained) {
                            playerList.addPlayer(grid.getCells(i, j).getPawn().getOwner());
                        }
                    }
                }
            }
        }
        return playerList;
    }
}
