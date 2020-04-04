package Client.Controller;

import Server.Model.Grid;
import Server.Model.Move;
import Server.Model.Cell;
import Server.Model.Tower;

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
    public Grid updateGridByMove(Move move, Grid grid) throws IllegalArgumentException{
        if(move.getIfMove() == true)
        {
            grid.getCells(move.getX(),move.getY()).setPawn(move.getToMove());
            if(grid.getCells(move.getX(),move.getY()).getTower().getLevel() == 3) {
                //TODO: WIN MESSAGE
            }

            if(grid.getCells(move.getX() + 1,move.getY()).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX() + 1,move.getY()).setPawn(null);
            }

            else if(grid.getCells(move.getX() - 1,move.getY()).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX() - 1,move.getY()).setPawn(null);
            }

            else if(grid.getCells(move.getX() + 1,move.getY() - 1).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX() + 1,move.getY() - 1).setPawn(null);
            }

            else if(grid.getCells(move.getX() + 1,move.getY() + 1).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX() + 1,move.getY() + 1).setPawn(null);
            }

            else if(grid.getCells(move.getX(),move.getY() + 1).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX(),move.getY() + 1).setPawn(null);
            }

            else if(grid.getCells(move.getX(),move.getY() - 1).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX(),move.getY() - 1).setPawn(null);
            }

            else if(grid.getCells(move.getX() - 1,move.getY() + 1).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX() - 1,move.getY() + 1).setPawn(null);
            }

            else if(grid.getCells(move.getX() - 1,move.getY() - 1).getPawn().getId() == move.getToMove().getId()){
                grid.getCells(move.getX() - 1,move.getY() - 1).setPawn(null);
            }

            else
            {
                throw new IllegalArgumentException();       //DANGER: MAY NOT WORK
            }

        }

        if(!move.getIfMove()){
            grid.getCells(move.getX(),move.getY()).getTower().setLevel(grid.getCells(move.getX(),move.getY()).getTower().getLevel() + 1);
            if(grid.getCells(move.getX(),move.getY()).getTower().getLevel() == 4){
                grid.getCells(move.getX(),move.getY()).getTower().setIsDome(true);
            }
        }
        return grid;
    }
}
