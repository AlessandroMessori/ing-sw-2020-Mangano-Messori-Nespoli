package Server.Controller;

import Server.Model.*;

import java.util.ArrayList;
import java.util.Random;

public class ServerController {


    private ArrayList<Divinity> inGameDivinities;
    private Divinity currDivinity;

    public boolean getCanSwap() {
        return canSwap;
    }

    public void setCanSwap(boolean canSwap) {
        this.canSwap = canSwap;
    }

    private boolean canSwap = false;                 //Apollo

    public int getNPossibleMoves() {
        return nPossibleMoves;
    }

    public void setNPossibleMoves(int nPossibleMoves) {
        this.nPossibleMoves = nPossibleMoves;
    }

    private int nPossibleMoves = 1;                 //Artemis

    public int getNMovesMade() {
        return nMovesMade;
    }

    public void setNMovesMade(int nMovesMade) {
        this.nMovesMade = nMovesMade;
    }

    private int nMovesMade = 0;

    public boolean getPawnMoved() {
        return pawnMoved;
    }

    public void setPawnMoved(boolean pawnMoved) {
        this.pawnMoved = pawnMoved;
    }

    private boolean pawnMoved = false;

    public boolean getCanBuildDomes() {
        return canBuildDomes;
    }

    public void setCanBuildDomes(boolean canBuildDomes) {
        this.canBuildDomes = canBuildDomes;
    }

    private boolean canBuildDomes = false;           //Atlas

    public int getNPossibleBuildings() {
        return nPossibleBuildings;
    }

    public void setNPossibleBuildings(int nPossibleBuildings) {
        this.nPossibleBuildings = nPossibleBuildings;
    }

    private int nPossibleBuildings = 1;             //Demeter

    public int getNMadeBuildings() {
        return nMadeBuildings;
    }

    public void setNMadeBuildings(int nMadeBuildings) {
        this.nMadeBuildings = nMadeBuildings;
    }

    private int nMadeBuildings = 0;

    public boolean getCanBuildOnLastBlock() {
        return canBuildOnLastBlock;
    }

    public void setCanBuildOnLastBlock(boolean canBuildOnLastBlock) {
        this.canBuildOnLastBlock = canBuildOnLastBlock;
    }

    private boolean canBuildOnLastBlock = false;    //Hephaestus

    public boolean getCanMoveAndSwap() {
        return canMoveAndSwap;
    }

    public void setCanMoveAndSwap(boolean canMoveAndSwap) {
        this.canMoveAndSwap = canMoveAndSwap;
    }

    private boolean canMoveAndSwap = false;         //Minotaur

    public boolean getVictoryAfterDescent() {
        return victoryAfterDescent;
    }

    public void setVictoryAfterDescent(boolean victoryAfterDescent) {
        this.victoryAfterDescent = victoryAfterDescent;
    }

    private boolean victoryAfterDescent = false;    //Pan

    public boolean getCanComeUp() {
        return canComeUp;
    }

    public void setCanComeUp(boolean canComeUp) {
        this.canComeUp = canComeUp;
    }

    private boolean canComeUp;

    public boolean getCanBuildBeforeMove() {
        return canBuildBeforeMove;
    }

    public void setCanBuildBeforeMove(boolean canBuildBeforeMove) {
        this.canBuildBeforeMove = canBuildBeforeMove;
    }

    private boolean canBuildBeforeMove = false;     //Prometheus, true if canComeUp = false



    /**
     *
     * @param length length of wanted random string
     * @return a random string
     */
    public static String randomString (int length) {
        Random rnd = new Random();
        char[] arr = new char[length];

        for (int i=0; i<length; i++) {
            int n = rnd.nextInt (36);
            arr[i] = (char) (n < 10 ? '0'+n : 'a'+n-10);
        }

        return new String (arr);
    }


    /**
     *
     * function called when the turn is starting to set variables
     */
    public void startingTurn() throws IllegalArgumentException{
        switch(currDivinity) {
            case APOLLO:
                setCanSwap(true);
                break;
            case ARTEMIS:
                setNPossibleMoves(2);
                break;
            case ATHENA:
                setPawnMoved(true);     //TODO: TRUE IF PAWNS WERE MOVED IN THE PREVIOUS TURN
                break;
            case ATLAS:
                setCanBuildDomes(true);
                break;
            case DEMETER:
                setNPossibleBuildings(2);
                break;
            case HEPHAESTUS:
                setCanBuildOnLastBlock(true);
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
     * adds the player to the game
     * @param p player to add
     * @param if3 boolean which checks if the player wants to play in a 2 or a 3 players game
     */
    public void addPlayerToModel(Player p, boolean if3){        //TODO SYNCHRONIZED
        String x;
        Model model = Model.getModel();

        if(model.getGames().size() == 0){       //starting case
            model.addGame(new Game(0,x = randomString(10),if3,null,null,null,null));
            model.searchID(x).getPlayers().addPlayer(p);
            return;
        }
        for(Game g : model.getGames()){
            if(!g.getThreePlayers() && !if3){           //if 2 players
                if(g.getPlayers().size() < 2){
                    g.getPlayers().addPlayer(p);
                }
            }
            else if(g.getThreePlayers() && if3){       //if 3 players
                if(g.getPlayers().size() < 3){
                    g.getPlayers().addPlayer(p);
                }
            }
            else {
                x = randomString(10);
                while(model.searchID(x) != null)
                {
                    x = randomString(10);
                }
                model.addGame(new Game(0,x,if3,null,null,null,null));
                model.searchID(x).getPlayers().addPlayer(p);
            }
        }
    }

    /**
     *
     * @param move move to add to the grid in the Model
     * @param g grid to be updated
     * @return
     */
    public Grid updateGridByMoveDef(Move move, Grid g){
        return g;
    }

    /**
     *
     * @param divinity the divinity to add to the inGameDivinities list
     */
    public void setInGameDivinities(Divinity divinity){
        inGameDivinities.add(divinity);
    }

    /**
     *
     * @return the list of Divinities
     */
    public ArrayList<Divinity> getInGameDivinities(){
        return inGameDivinities;
    }

    /**
     *
     * deletes a divinity from inGameDivinities
     * @param div the divinity to remove
     */
    public void deleteDivinity(Divinity div){
        inGameDivinities.remove(div);
    }

    /**
     *
     * @param grid grid on which to calculate the next possible move(s), based on the player's divinity
     * @return the possible MoveList
     */
    public MoveList calculateNextMove(Grid grid){
        MoveList movelist = null;


        return movelist;
    }

    /**
     *
     * @param p player to add the divinity
     * @param div divinity to add to the player
     */
    public void addDivinityToPlayer(Player p, Divinity div){
        p.setDivinity(div);
    }

    /**
     *
     * @param grid
     * @param gameID
     */
    public void updateModelGrid(Grid grid, String gameID){

    }
}
