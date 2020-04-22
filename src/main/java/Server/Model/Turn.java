package Server.Model;

import Server.Model.*;
import Server.Model.Divinity;

public class Turn {

    /**
     *
     * @return the divinity of the player who is currently playing
     */
    public Divinity getCurrDivinity() {
        return currDivinity;
    }

    private Divinity currDivinity;

    /**
     * APOLLO EFFECT
     *
     * @param p pawn to swap with current player's one
     */
    public void setEnemyPawn(Pawn p){enemyPawn = p;}

    /**
     * APOLLO EFFECT
     *
     * @return the Pawn to swap with the current player's one
     */
    public Pawn getEnemyPawn() {return enemyPawn;}

    private Pawn enemyPawn = null;

    /**
     * APOLLO EFFECT
     *
     * @return a boolean which indicates if the Pawn can be swapped
     */
    public boolean getCanSwap() {
        return canSwap;
    }

    /**
     * APOLLO EFFECT
     *
     * @param canSwap  a boolean which indicates if the Pawn can be swapped
     */
    public void setCanSwap(boolean canSwap) {
        this.canSwap = canSwap;
    }

    private boolean canSwap = false;                 //Apollo

    /**
     * CONDITION FOR
     * ARTEMIS EFFECT
     *
     * @return the move where the pawn can't move since it's its starting place
     */
    public Move getCantMoveBackHere() {return cantMoveBackHere;}

    /**
     * CONDITION FOR
     * ARTEMIS EFFECT
     *
     * @param move the starting point for the pawn
     */
    public void setCantMoveBackHere(Move move){ cantMoveBackHere = move;}

    private Move cantMoveBackHere = new Move(null);

    /**
     * ARTEMIS EFFECT
     *
     * @return the number of possible moves in this turn (default: 1)
     */
    public int getNPossibleMoves() {
        return nPossibleMoves;
    }

    /**
     * ARTEMIS EFFECT
     *
     * @param nPossibleMoves the number of possible moves in this turn (default: 1)
     */
    public void setNPossibleMoves(int nPossibleMoves) {
        this.nPossibleMoves = nPossibleMoves;
    }

    private int nPossibleMoves = 1;                 //Artemis

    /**
     *
     * @return the number of moves already made
     */
    public int getNMovesMade() {
        return nMovesMade;
    }

    /**
     *
     * @param nMovesMade the number of moves already made
     */
    public void setNMovesMade(int nMovesMade) {
        this.nMovesMade = nMovesMade;
    }

    private int nMovesMade = 0;

    /**
     * CONDITION FOR
     * ATHENA EFFECT
     *
     * @return if a pawn was moved in the previous same player's turn
     */
    public boolean getPawnMoved() {
        return pawnMoved;
    }

    /**
     * CONDITION FOR
     * ATHENA EFFECT
     *
     * @param pawnMoved if a pawn was moved in the previous same player's turn
     */
    public void setPawnMoved(boolean pawnMoved) {
        this.pawnMoved = pawnMoved;
    }

    private boolean pawnMoved = false;

    /**
     * ATLAS EFFECT
     *
     * @return a boolean which indicates if the player can build domes wherever he want
     */
    public boolean getCanBuildDomes() {
        return canBuildDomes;
    }

    /**
     * ATLAS EFFECT
     *
     * @param canBuildDomes a boolean which indicates if the player can build domes wherever he want
     */
    public void setCanBuildDomes(boolean canBuildDomes) {
        this.canBuildDomes = canBuildDomes;
    }

    private boolean canBuildDomes = false;           //Atlas

    /**
     * CONDITION FOR
     * DEMETER EFFECT
     *
     * @param move building move where the player built and can't build again
     */
    public void setCantBuildOnThisBlock(Move move){cantBuildOnThisBlock = move;}

    /**
     * CONDITION FOR
     * DEMETER EFFECT
     *
     * @return the last building move made, where the player can't build again
     */
    public Move getCantBuildOnThisBlock(){return cantBuildOnThisBlock;}

    private Move cantBuildOnThisBlock = new Move(null);

    /**
     * DEMETER EFFECT
     *
     * @return the number of possible buildings to be created in this turn
     */
    public int getNPossibleBuildings() {
        return nPossibleBuildings;
    }

    /**
     * DEMETER EFFECT
     *
     * @param nPossibleBuildings the number of possible buildings to be created in this turn
     */
    public void setNPossibleBuildings(int nPossibleBuildings) {
        this.nPossibleBuildings = nPossibleBuildings;
    }

    private int nPossibleBuildings = 1;             //Demeter

    /**
     *
     * @return the number of buildings already made
     */
    public int getNMadeBuildings() {
        return nMadeBuildings;
    }

    /**
     *
     * @param nMadeBuildings the number of buildings already made
     */
    public void setNMadeBuildings(int nMadeBuildings) {
        this.nMadeBuildings = nMadeBuildings;
    }

    private int nMadeBuildings = 0;

    /**
     * HEPHAESTUS EFFECT
     *
     * @return a boolean which indicates if the player can build on the last placed first level block
     */
    public boolean getCanBuildOnLastBlock() {
        return canBuildOnLastPlacedBlock;
    }

    /**
     * HEPHAESTUS EFFECT
     *
     * @param canBuildOnLastBlock a boolean which indicates if the player can build on the last placed first level block
     */
    public void setCanBuildOnLastPlacedBlock(boolean canBuildOnLastBlock) {
        this.canBuildOnLastPlacedBlock = canBuildOnLastBlock;
    }

    private boolean canBuildOnLastPlacedBlock = false;    //Hephaestus

    /**
     * MINOTAUR EFFECT
     *
     * @return a boolean which indicates if the player can move in an opponent's pawn-occupied cell
     */
    public boolean getCanMoveAndSwap() {
        return canMoveAndSwap;
    }

    /**
     * MINOTAUR EFFECT
     *
     * @param canMoveAndSwap a boolean which indicates if the player can move in an opponent's pawn-occupied cell
     */
    public void setCanMoveAndSwap(boolean canMoveAndSwap) {
        this.canMoveAndSwap = canMoveAndSwap;
    }

    private boolean canMoveAndSwap = false;         //Minotaur

    /**
     * PAN EFFECT
     *
     * @return if the player can win even after a descent of 2 or more levels
     */
    public boolean getVictoryAfterDescent() {
        return victoryAfterDescent;
    }

    /**
     * PAN EFFECT
     *
     * @param victoryAfterDescent if the player can win even after a descent of 2 or more levels
     */
    public void setVictoryAfterDescent(boolean victoryAfterDescent) {
        this.victoryAfterDescent = victoryAfterDescent;
    }

    private boolean victoryAfterDescent = false;    //Pan

    /**
     * CONDITION FOR
     * PROMETHEUS EFFECT
     *
     * @return if the player can come up a level
     */
    public boolean getCanComeUp() {
        return canComeUp;
    }

    /**
     * CONDITION FOR
     * PROMETHEUS EFFECT
     *
     * @param canComeUp if the player can come up a level
     */
    public void setCanComeUp(boolean canComeUp) {
        this.canComeUp = canComeUp;
    }

    private boolean canComeUp;

    /**
     * PROMETHEUS EFFECT
     *
     * @return if the player can build before and after having moved a pawn
     */
    public boolean getCanBuildBeforeMove() {
        return canBuildBeforeMove;
    }

    /**
     * PROMETHEUS EFFECT
     *
     * @param canBuildBeforeMove if the player can build before and after having moved a pawn
     */
    public void setCanBuildBeforeMove(boolean canBuildBeforeMove) {
        this.canBuildBeforeMove = canBuildBeforeMove;
    }

    private boolean canBuildBeforeMove = false;     //Prometheus, true if canComeUp = false

    /**
     *
     * function called when the turn is starting to set variables
     */
    public void startingTurn(Divinity div) throws IllegalArgumentException{
        enemyPawn = null;
        currDivinity = div;
        setNPossibleMoves(1);
        setNMovesMade(0);
        setNPossibleBuildings(1);
        setNMadeBuildings(0);
        switch(currDivinity) {
            case APOLLO:
                setCanSwap(true);
                break;
            case ARTEMIS:
                setNPossibleMoves(2);
                Move moveM = new Move(null);
                moveM.setIfMove(true);
                moveM.setX(-1);
                moveM.setY(-1);
                setCantMoveBackHere(moveM);
                break;
            case ATHENA:
                setPawnMoved(false);     //TODO: TRUE IF PAWNS WERE MOVED IN THE PREVIOUS TURN
                break;
            case ATLAS:
                setCanBuildDomes(true);
                break;
            case DEMETER:
                setNPossibleBuildings(2);
                Move moveB = new Move(null);
                moveB.setIfMove(false);
                moveB.setX(-1);
                moveB.setY(-1);
                setCantBuildOnThisBlock(moveB);
                break;
            case HEPHAESTUS:
                setCanBuildOnLastPlacedBlock(true);
                break;
            case MINOTAUR:
                setCanMoveAndSwap(true);
                break;
            case PAN:
                setVictoryAfterDescent(true);
                break;
            case PROMETHEUS:
                if(!getCanComeUp())
                {
                    setCanBuildBeforeMove(true);
                }
                break;
            default:
                throw new IllegalArgumentException();

        }

    }

    /**
     *
     * @param grid
     * @param move
     */
    public void canItComeUp(Grid grid, Move move){      //useful for Prometheus
        if(grid.getCells(move.getX() + 1,move.getY()).getTower().getLevel() == grid.getCells(move.getX(),move.getY()).getTower().getLevel() + 1)
        {
            if(!grid.getCells(move.getX() + 1,move.getY()).getTower().getIsDome()) {
                setCanComeUp(true);
            }
        }
        else if(grid.getCells(move.getX() - 1,move.getY()).getTower().getLevel() == grid.getCells(move.getX(),move.getY()).getTower().getLevel() + 1)
        {
            if(!grid.getCells(move.getX() - 1,move.getY()).getTower().getIsDome()) {
                setCanComeUp(true);
            }
        }
        else if(grid.getCells(move.getX() + 1,move.getY() + 1).getTower().getLevel() == grid.getCells(move.getX(),move.getY()).getTower().getLevel() + 1)
        {
            if(!grid.getCells(move.getX() + 1,move.getY() + 1).getTower().getIsDome()) {
                setCanComeUp(true);
            }
        }
        else if(grid.getCells(move.getX() + 1,move.getY() - 1).getTower().getLevel() == grid.getCells(move.getX(),move.getY()).getTower().getLevel() + 1)
        {
            if(!grid.getCells(move.getX() + 1,move.getY() - 1).getTower().getIsDome()) {
                setCanComeUp(true);
            }
        }
        else if(grid.getCells(move.getX() - 1,move.getY() + 1).getTower().getLevel() == grid.getCells(move.getX(),move.getY()).getTower().getLevel() + 1)
        {
            if(!grid.getCells(move.getX() - 1,move.getY() + 1).getTower().getIsDome()) {
                setCanComeUp(true);
            }
        }
        else if(grid.getCells(move.getX() - 1,move.getY() - 1).getTower().getLevel() == grid.getCells(move.getX(),move.getY()).getTower().getLevel() + 1)
        {
            if(!grid.getCells(move.getX() - 1,move.getY() - 1).getTower().getIsDome()) {
                setCanComeUp(true);
            }
        }
        else if(grid.getCells(move.getX(),move.getY() + 1).getTower().getLevel() == grid.getCells(move.getX(),move.getY()).getTower().getLevel() + 1)
        {
            if(!grid.getCells(move.getX(),move.getY() + 1).getTower().getIsDome()) {
                setCanComeUp(true);
            }
        }
        else if(grid.getCells(move.getX(),move.getY() - 1).getTower().getLevel() == grid.getCells(move.getX(),move.getY()).getTower().getLevel() + 1)
        {
            if(!grid.getCells(move.getX(),move.getY() - 1).getTower().getIsDome()) {
                setCanComeUp(true);
            }
        }
    }

    /**
     * PAN EFFECT
     *
     * @result true if the game can be won by the player with Pan as a divinity
     */
    public boolean checkIfWin(){
        if(!getCurrDivinity().equals("PAN")) {return false;}

        return true;
    }

    /**
     * CONSTRUCTOR
     * @param div indicates the Divinity owned by the player in this Turn
     */
    public Turn(Divinity div){
        currDivinity = div;
    }



}
