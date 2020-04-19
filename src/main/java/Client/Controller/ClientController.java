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
     *
     * @param move is the move the current player has done
     * @param game is the game to be modified
     * @return the game updated after the move is done
     */
    public Game updateGameByMove(Move move, Game game) throws IllegalArgumentException{
        if(move.getIfMove() == true)
        {
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
                    if(game.getNewGrid().getCells(move.getX() + i, move.getY() + j).getPawn() != null) {
                        if (game.getNewGrid().getCells(move.getX() + i, move.getY() + j).getPawn().getId() == move.getToMove().getId()) {
                            game.getNewGrid().getCells(move.getX() + i, move.getY() + j).setPawn(null);
                            if (move.getToMove().getOwner().getDivinity() == Divinity.ATHENA) {
                                game.getGameTurn().setPawnMoved(true);
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

            if(game.getNewGrid().getCells(move.getX(),move.getY()).getPawn() != null) {
                if (game.getNewGrid().getCells(move.getX(), move.getY()).getPawn().getId() == move.getToMove().getId()) {
                    if (move.getToMove().getOwner().getDivinity() == Divinity.ATHENA) {
                        game.getGameTurn().setPawnMoved(false);
                    }
                }
            }

            else
            {
                throw new IllegalArgumentException();       //DANGER: MAY NOT WORK
            }

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
