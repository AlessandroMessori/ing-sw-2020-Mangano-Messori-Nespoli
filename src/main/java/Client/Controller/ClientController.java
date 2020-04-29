package Client.Controller;

import Server.Model.*;

public class ClientController {

   /* /**
     * ATHENA EFFECT
     *
     * @param grid grid where to see if Pawn was moved
     * @param move where the pawn is now
     * @return a boolean true if the pawn was moved, false if not
     */
    /*public boolean checkIfMoved(Grid grid, Move move){
        int count = 0;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if (0 <= (move.getX() + i) && (move.getX() + i) <= 4 && 0 <= (move.getY() + j) && (move.getY() + j) <= 4) {
                    if (grid.getCells(move.getX() + i, move.getY() + j).getPawn() != null) {
                        if (grid.getCells(move.getX() + i, move.getY() + j).getPawn().getId() == move.getToMove().getId()) {
                            count++;
                        }
                    }
                }
            }
        }
        if(count == 1){
            return false;
        }
        return true;

    }*/

    /**
     *
     * @param move is the move the current player has done
     * @param game is the game to be modified
     * @return the game updated after the move is done
     */
    public Game updateGameByMove(Move move, Game game) throws IllegalArgumentException{
        int startingLevel = 0;
        int endingLevel = 0;
        game.setOldGrid(game.getNewGrid());
        if(move.getIfMove() == true)
        {
            game.getNewGrid().getCells(move.getX(),move.getY()).setPawn(move.getToMove());

            if(game.getCurrentPlayer().getDivinity() == Divinity.ATHENA || game.getCurrentPlayer().getDivinity() == Divinity.PAN){
                endingLevel = game.getNewGrid().getCells(move.getX(),move.getY()).getTower().getLevel();
                /*game.getGameTurn().setPawnMoved(checkIfMoved(game.getNewGrid(),move));*/
            }

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

                                if(game.getCurrentPlayer().getDivinity() == Divinity.ATHENA){
                                    startingLevel = game.getNewGrid().getCells(move.getX() + i,move.getY() + j).getTower().getLevel();
                                    if((endingLevel - startingLevel) > 0){
                                        game.getGameTurn().setPawnMoved(true);
                                    }
                                }
                                if(game.getCurrentPlayer().getDivinity() == Divinity.PAN){
                                    startingLevel = game.getNewGrid().getCells(move.getX() + i,move.getY() + j).getTower().getLevel();
                                    if((startingLevel - endingLevel) >= 2){
                                        game.setWinner(game.getCurrentPlayer());
                                    }
                                }

                                if(game.getGameTurn().getEnemyPawn1() != null && move.getToMove().getOwner().getDivinity() == Divinity.APOLLO) //APOLLO EFFECT
                                {
                                    for(int k = 0; k <= 4; k++){
                                        for(int l = 0; l <= 4; l++){
                                            if(game.getNewGrid().getCells(k,l).getPawn() != null){
                                                if(game.getGameTurn().getEnemyPawn1() != null) {
                                                    if (game.getNewGrid().getCells(k, l).getPawn().getId() == game.getGameTurn().getEnemyPawn1().getId()) {
                                                        game.getGameTurn().setEnemyPawn1(null);
                                                    }
                                                }
                                                if(game.getGameTurn().getEnemyPawn2() != null){
                                                    if(game.getNewGrid().getCells(k,l).getPawn().getId() == game.getGameTurn().getEnemyPawn2().getId()){
                                                        game.getGameTurn().setEnemyPawn2(null);
                                                    }
                                                }
                                                if(game.getGameTurn().getEnemyPawn3() != null){
                                                    if(game.getNewGrid().getCells(k,l).getPawn().getId() == game.getGameTurn().getEnemyPawn3().getId()){
                                                        game.getGameTurn().setEnemyPawn3(null);
                                                    }
                                                }
                                                if(game.getGameTurn().getEnemyPawn4() != null){
                                                    if(game.getNewGrid().getCells(k,l).getPawn().getId() == game.getGameTurn().getEnemyPawn4().getId()){
                                                        game.getGameTurn().setEnemyPawn4(null);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (game.getGameTurn().getEnemyPawn1() != null) {
                                        game.getNewGrid().getCells(move.getX() + i,move.getY() + j).setPawn(game.getGameTurn().getEnemyPawn1());
                                    }
                                    else if(game.getGameTurn().getEnemyPawn2() != null){
                                        game.getNewGrid().getCells(move.getX() + i,move.getY() + j).setPawn(game.getGameTurn().getEnemyPawn2());
                                    }
                                    else if(game.getGameTurn().getEnemyPawn3() != null){
                                        game.getNewGrid().getCells(move.getX() + i,move.getY() + j).setPawn(game.getGameTurn().getEnemyPawn3());
                                    }
                                    else if(game.getGameTurn().getEnemyPawn4() != null){
                                        game.getNewGrid().getCells(move.getX() + i,move.getY() + j).setPawn(game.getGameTurn().getEnemyPawn4());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if(!move.getIfMove()){

            if(move.getX() < 0 && move.getY() < 0){
                int defX = 0, defY = 0;
                game.decreaseAvailableDomes();

                for(int i = -1; i >= -5; i--){
                    if(move.getX() == i){
                        defX = Math.abs(i) - 1;
                    }
                }

                for(int i = -1; i >= -5; i--){
                    if(move.getY() == i){
                        defY = Math.abs(i) - 1;
                    }
                }
                game.getNewGrid().getCells(defX,defY).getTower().setLevel(game.getNewGrid().getCells(defX,defY).getTower().getLevel() + 1);
                game.getNewGrid().getCells(defX,defY).getTower().setIsDome(true);
                return game;
            }

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
