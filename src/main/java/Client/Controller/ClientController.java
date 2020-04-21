package Client.Controller;

import Server.Model.*;

public class ClientController {

    /*private boolean activeTurn;

    /**
     * sets the boolean activeTurn
     * @param activeTurn checks if the current player is the one playing
     */
    /*public void setActiveTurn(boolean activeTurn) {
        this.activeTurn = activeTurn;
    }

    /**
     *
     * @return a boolean which defines if the player is the one playing
     */
    /*public boolean getActiveTurn(){
        return activeTurn;
    }*/

    /**
     * ATHENA EFFECT
     *
     * @param grid grid where to see if Pawn was moved
     * @param move where the pawn is now
     * @return a boolean true if the pawn was moved, false if not
     */
    public boolean checkIfMoved(Grid grid, Move move){
        int count = 0;
        for(int i = -1; i <= 4; i++){
            for(int j = -1; j <= 4; j++){
                if(grid.getCells(move.getX() + i,move.getY() + j).getPawn().getId() == move.getToMove().getId()){
                    count++;
                }
            }
        }
        if(count == 1){
            return false;
        }
        return true;

    }

    /**
     *
     * @param move is the move the current player has done
     * @param game is the game to be modified
     * @return the game updated after the move is done
     */
    public Game updateGameByMove(Move move, Game game) throws IllegalArgumentException{
        game.setOldGrid(game.getNewGrid());
        if(move.getIfMove() == true)
        {
            if(game.getCurrentPlayer().getDivinity() == Divinity.ATHENA){
                game.getGameTurn().setPawnMoved(checkIfMoved(game.getNewGrid(),move));
            }

            Pawn enemyPawn = game.getNewGrid().getCells(move.getX(),move.getY()).getPawn();

            if(enemyPawn != null && move.getToMove().getOwner().getDivinity() == Divinity.APOLLO) //APOLLO EFFECT
            {
                game.getNewGrid().getCells(move.getX(),move.getY()).setPawn(enemyPawn);
            }

            game.getNewGrid().getCells(move.getX(),move.getY()).setPawn(move.getToMove());
            if(game.getNewGrid().getCells(move.getX(),move.getY()).getTower().getLevel() == 3) {
                game.setWinner(game.getCurrentPlayer());            //WINNING MOVE
            }

            for(int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if(0 <= move.getX() + i && move.getX() + i <= 4 && 0 <= move.getY() + j && move.getY() + j <= 4) {
                        if (game.getNewGrid().getCells(move.getX() + i, move.getY() + j).getPawn() != null) {
                            if (game.getNewGrid().getCells(move.getX() + i, move.getY() + j).getPawn().getId() == (move.getToMove().getId())) {
                                if(i == 0 && j == 0){
                                    continue;
                                }
                                game.getNewGrid().getCells(move.getX() + i, move.getY() + j).setPawn(null);
                                /*if (move.getToMove().getOwner().getDivinity() == Divinity.ATHENA) {
                                    if(i == 0 && j == 0) {
                                        game.getGameTurn().setPawnMoved(false);
                                    }
                                    else {
                                        game.getGameTurn().setPawnMoved(true);
                                    }
                                }*/
                            }
                        }
                    }
                }
            }

            /*else if(game.getNewGrid().getCells(move.getX() - 1,move.getY()).getPawn().getId() == move.getToMove().getId()){
                game.getNewGrid().getCells(move.getX() - 1,move.getY()).setPawn(null);
                if(move.getToMove().getOwner().getDivinity() == Divinity.ATHENA){
                    game.getGameTurn().setPawnMoved(true);
                }
            }

            else if(game.getNewGrid().getCells(move.getX() + 1,move.getY() - 1).getPawn().getId() == move.getToMove().getId()){
                game.getNewGrid().getCells(move.getX() + 1,move.getY() - 1).setPawn(null);
                if(move.getToMove().getOwner().getDivinity() == Divinity.ATHENA){
                    game.getGameTurn().setPawnMoved(true);
                }
            }

            else if(game.getNewGrid().getCells(move.getX() + 1,move.getY() + 1).getPawn().getId() == move.getToMove().getId()){
                game.getNewGrid().getCells(move.getX() + 1,move.getY() + 1).setPawn(null);
                if(move.getToMove().getOwner().getDivinity() == Divinity.ATHENA){
                    game.getGameTurn().setPawnMoved(true);
                }
            }

            else if(game.getNewGrid().getCells(move.getX(),move.getY() + 1).getPawn().getId() == move.getToMove().getId()){
                game.getNewGrid().getCells(move.getX(),move.getY() + 1).setPawn(null);
                if(move.getToMove().getOwner().getDivinity() == Divinity.ATHENA){
                    game.getGameTurn().setPawnMoved(true);
                }
            }

            else if(game.getNewGrid().getCells(move.getX(),move.getY() - 1).getPawn().getId() == move.getToMove().getId()){
                game.getNewGrid().getCells(move.getX(),move.getY() - 1).setPawn(null);
                if(move.getToMove().getOwner().getDivinity() == Divinity.ATHENA){
                    game.getGameTurn().setPawnMoved(true);
                }
            }

            else if(game.getNewGrid().getCells(move.getX() - 1,move.getY() + 1).getPawn().getId() == move.getToMove().getId()){
                game.getNewGrid().getCells(move.getX() - 1,move.getY() + 1).setPawn(null);
                if(move.getToMove().getOwner().getDivinity() == Divinity.ATHENA){
                    game.getGameTurn().setPawnMoved(true);
                }
            }

            else if(game.getNewGrid().getCells(move.getX() - 1,move.getY() - 1).getPawn().getId() == move.getToMove().getId()){
                game.getNewGrid().getCells(move.getX() - 1,move.getY() - 1).setPawn(null);
                if(move.getToMove().getOwner().getDivinity() == Divinity.ATHENA){
                    game.getGameTurn().setPawnMoved(true);
                }
            }

             */

            /*if(game.getNewGrid().getCells(move.getX(),move.getY()).getPawn() != null) {
                if (game.getNewGrid().getCells(move.getX(), move.getY()).getPawn().getId() == move.getToMove().getId()) {
                    if (move.getToMove().getOwner().getDivinity() == Divinity.ATHENA) {
                        game.getGameTurn().setPawnMoved(false);
                    }
                }
            }*/

            /*else
            {
                throw new IllegalArgumentException();       //DANGER: MAY NOT WORK
            }*/

        }

        if(!move.getIfMove()){
            if(game.getNewGrid().getCells(move.getX(),move.getY()).getTower().getLevel() == 0) {
                game.decreaseAvailableLevel1Buildings();
            }
            if(game.getNewGrid().getCells(move.getX(),move.getY()).getTower().getLevel() == 1){
                game.decreaseAvailableLevel2Buildings();
            }
            if(game.getNewGrid().getCells(move.getX(),move.getY()).getTower().getLevel() == 2){
                game.decreaseAvailableLevel3Buildings();
            }
            if(game.getNewGrid().getCells(move.getX(),move.getY()).getTower().getLevel() == 3){
                game.decreaseAvailableDomes();
            }
            game.getNewGrid().getCells(move.getX(),move.getY()).getTower().setLevel(game.getNewGrid().getCells(move.getX(),move.getY()).getTower().getLevel() + 1);
            if(game.getNewGrid().getCells(move.getX(),move.getY()).getTower().getLevel() == 4){
                game.getNewGrid().getCells(move.getX(),move.getY()).getTower().setIsDome(true);
            }
        }
        return game;
    }
}
