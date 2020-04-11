package Client.Controller;

import Server.Controller.Turn;
import Server.Model.*;

public class ClientController {

    private boolean activeTurn;

    /**
     * sets the boolean activeTurn
     * @param activeTurn checks if the current player is the one playing
     */
    public void setActiveTurn(boolean activeTurn) {
        this.activeTurn = activeTurn;
    }

    /**
     *
     * @return a boolean which defines if the player is the one playing
     */
    public boolean getActiveTurn(){
        return activeTurn;
    }

    /**
     *
     * @param move is the move the current player has done
     * @param grid is the grid to be modified in the game
     * @return the Grid updated after the move is done
     */
    public Grid updateGridByMove(Move move, Grid grid, String gameID, Turn turn) throws IllegalArgumentException{
        Model model = Model.getModel();
        Game game = model.searchID(gameID);
        if(move.getIfMove() == true)
        {
            Pawn enemyPawn = grid.getCells(move.getX(),move.getY()).getPawn();

            if(enemyPawn != null && move.getToMove().getOwner().getDivinity().equals("APOLLO")) //APOLLO EFFECT
            {
                grid.getCells(move.getX(),move.getY()).setPawn(enemyPawn);
            }

            grid.getCells(move.getX(),move.getY()).setPawn(move.getToMove());
            if(grid.getCells(move.getX(),move.getY()).getTower().getLevel() == 3) {
                //TODO: WIN MESSAGE
            }

            if(grid.getCells(move.getX() + 1,move.getY()).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX() + 1,move.getY()).setPawn(null);
                if(move.getToMove().getOwner().getDivinity().equals("ATHENA")){
                    turn.setPawnMoved(true);
                }
            }

            else if(grid.getCells(move.getX() - 1,move.getY()).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX() - 1,move.getY()).setPawn(null);
                if(move.getToMove().getOwner().getDivinity().equals("ATHENA")){
                    turn.setPawnMoved(true);
                }
            }

            else if(grid.getCells(move.getX() + 1,move.getY() - 1).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX() + 1,move.getY() - 1).setPawn(null);
                if(move.getToMove().getOwner().getDivinity().equals("ATHENA")){
                    turn.setPawnMoved(true);
                }
            }

            else if(grid.getCells(move.getX() + 1,move.getY() + 1).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX() + 1,move.getY() + 1).setPawn(null);
                if(move.getToMove().getOwner().getDivinity().equals("ATHENA")){
                    turn.setPawnMoved(true);
                }
            }

            else if(grid.getCells(move.getX(),move.getY() + 1).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX(),move.getY() + 1).setPawn(null);
                if(move.getToMove().getOwner().getDivinity().equals("ATHENA")){
                    turn.setPawnMoved(true);
                }
            }

            else if(grid.getCells(move.getX(),move.getY() - 1).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX(),move.getY() - 1).setPawn(null);
                if(move.getToMove().getOwner().getDivinity().equals("ATHENA")){
                    turn.setPawnMoved(true);
                }
            }

            else if(grid.getCells(move.getX() - 1,move.getY() + 1).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX() - 1,move.getY() + 1).setPawn(null);
                if(move.getToMove().getOwner().getDivinity().equals("ATHENA")){
                    turn.setPawnMoved(true);
                }
            }

            else if(grid.getCells(move.getX() - 1,move.getY() - 1).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX() - 1,move.getY() - 1).setPawn(null);
                if(move.getToMove().getOwner().getDivinity().equals("ATHENA")){
                    turn.setPawnMoved(true);
                }
            }

            if(grid.getCells(move.getX(),move.getY()).getPawn().getId() == move.getToMove().getId()){
                if(move.getToMove().getOwner().getDivinity().equals("ATHENA")) {
                    turn.setPawnMoved(false);
                }
            }

            else
            {
                throw new IllegalArgumentException();       //DANGER: MAY NOT WORK
            }

        }

        if(!move.getIfMove()){
            if(grid.getCells(move.getX(),move.getY()).getTower().getLevel() == 0) {
                game.decreaseAvailableLevel1Buildings();
            }
            if(grid.getCells(move.getX(),move.getY()).getTower().getLevel() == 1){
                game.decreaseAvailableLevel2Buildings();
            }
            if(grid.getCells(move.getX(),move.getY()).getTower().getLevel() == 2){
                game.decreaseAvailableLevel3Buildings();
            }
            if(grid.getCells(move.getX(),move.getY()).getTower().getLevel() == 3){
                game.decreaseAvailableDomes();
            }
            grid.getCells(move.getX(),move.getY()).getTower().setLevel(grid.getCells(move.getX(),move.getY()).getTower().getLevel() + 1);
            if(grid.getCells(move.getX(),move.getY()).getTower().getLevel() == 4){
                grid.getCells(move.getX(),move.getY()).getTower().setIsDome(true);
            }
        }
        return grid;
    }
}
