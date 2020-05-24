package it.polimi.ingsw.PSP19.Client.Controller;

import it.polimi.ingsw.PSP19.Server.Model.Divinity;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Server.Model.Move;

public class ClientController {

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
            }

            for(int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if(0 <= move.getX() + i && move.getX() + i <= 4 && 0 <= move.getY() + j && move.getY() + j <= 4) {
                        if (game.getNewGrid().getCells(move.getX() + i, move.getY() + j).getPawn() != null) {
                            if (game.getNewGrid().getCells(move.getX() + i, move.getY() + j).getPawn().getId() == (move.getToMove().getId())) {
                                if(i == 0 && j == 0){
                                    continue;
                                }

                                startingLevel = game.getNewGrid().getCells(move.getX() + i,move.getY() + j).getTower().getLevel();

                                game.getNewGrid().getCells(move.getX() + i, move.getY() + j).setPawn(null);

                                if(game.getNewGrid().getCells(move.getX(),move.getY()).getTower().getLevel() == 3 && startingLevel != 3) {
                                    game.setWinner(game.getCurrentPlayer());            //WINNING MOVE
                                }

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

                                if(game.getGameTurn().getEnemyPawn1() != null && (move.getToMove().getOwner().getDivinity() == Divinity.APOLLO || move.getToMove().getOwner().getDivinity() == Divinity.MINOTAUR)) //APOLLO EFFECT
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
                                        if(game.getCurrentPlayer().getDivinity() == Divinity.APOLLO) {
                                            game.getNewGrid().getCells(move.getX() + i, move.getY() + j).setPawn(game.getGameTurn().getEnemyPawn1());
                                        }
                                        if(game.getCurrentPlayer().getDivinity() == Divinity.MINOTAUR) {
                                            game.getNewGrid().getCells(move.getX() - i, move.getY() - j).setPawn(game.getGameTurn().getEnemyPawn1());
                                        }
                                    }
                                    else if(game.getGameTurn().getEnemyPawn2() != null){
                                        if(game.getCurrentPlayer().getDivinity() == Divinity.APOLLO) {
                                            game.getNewGrid().getCells(move.getX() + i, move.getY() + j).setPawn(game.getGameTurn().getEnemyPawn2());
                                        }
                                        if(game.getCurrentPlayer().getDivinity() == Divinity.MINOTAUR) {
                                            game.getNewGrid().getCells(move.getX() - i, move.getY() - j).setPawn(game.getGameTurn().getEnemyPawn2());
                                        }
                                    }
                                    else if(game.getGameTurn().getEnemyPawn3() != null){
                                        if(game.getCurrentPlayer().getDivinity() == Divinity.APOLLO) {
                                            game.getNewGrid().getCells(move.getX() + i, move.getY() + j).setPawn(game.getGameTurn().getEnemyPawn3());
                                        }
                                        if(game.getCurrentPlayer().getDivinity() == Divinity.MINOTAUR) {
                                            game.getNewGrid().getCells(move.getX() - i, move.getY() - j).setPawn(game.getGameTurn().getEnemyPawn3());
                                        }
                                    }
                                    else if(game.getGameTurn().getEnemyPawn4() != null){
                                        if(game.getCurrentPlayer().getDivinity() == Divinity.APOLLO) {
                                            game.getNewGrid().getCells(move.getX() + i, move.getY() + j).setPawn(game.getGameTurn().getEnemyPawn4());
                                        }
                                        if(game.getCurrentPlayer().getDivinity() == Divinity.MINOTAUR) {
                                            game.getNewGrid().getCells(move.getX() - i, move.getY() - j).setPawn(game.getGameTurn().getEnemyPawn4());
                                        }
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
