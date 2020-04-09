package Server.Model;

import java.util.ArrayList;

public class Game {
    private int nTurns;
    private int availableLevel1Buildings;
    private int availableLevel2Buildings;
    private int availableLevel3Buildings;
    private int availableDomes;
    private String CodGame;
    private boolean threePlayers;
    private Player currentPlayer;
    private PlayerList players;
    private Grid oldGrid;
    private Grid newGrid;
    private MoveList nextMoves;
    private DivinityList inGameDivinities;


    /**
     * @return number of turns played
     */
    public int getNTurns() {
        return nTurns;
    }

    /**
     * sets the number of turns
     *
     * @param t updated number of turns played
     */
    public void setNTurns(int t) throws IllegalArgumentException {
        if (t >= 0) {
            nTurns = t;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return available level 1 buildings
     */
    public int getAvailableLevel1Buildings() {
        return availableLevel1Buildings;
    }

    /**
     * decreases by 1 the AvailableLevel1Buildings
     */
    public void decreaseAvailableLevel1Buildings() {
        availableLevel1Buildings--;
    }

    /**
     * @return available level 2 buildings
     */
    public int getAvailableLevel2Buildings() {
        return availableLevel2Buildings;
    }

    /**
     * decreases by 1 the AvailableLevel2Buildings
     */
    public void decreaseAvailableLevel2Buildings() {
        availableLevel2Buildings--;
    }

    /**
     * @return available level 3 buildings
     */
    public int getAvailableLevel3Buildings() {
        return availableLevel3Buildings;
    }

    /**
     * decreases by 1 the AvailableLevel3Buildings
     */
    public void decreaseAvailableLevel3Buildings() {
        availableLevel3Buildings--;
    }

    /**
     *
     * @return available domes
     */
    public int getAvailableDomes(){
        return availableDomes;
    }

    /**
     * decreases by 1 the AvailableDomes
     */
    public void decreaseAvailableDomes() { availableDomes--; }


    /**
     * sets the number of turns
     *
     * @param t updated number of turns played
     */
    public void decreaseMaxLevel1Builings(int t) throws IllegalArgumentException {

    }

    /**
     * @return the game ID
     */
    public String getCodGame() {
        return CodGame;
    }

    /**
     * sets the ID of the game
     *
     * @param c the ID of the game
     */
    public void setCodGame(String c) throws IllegalArgumentException {
        Model model = Model.getModel();
        if (model.searchID(c) == null) {
            CodGame = c;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * @return a value that defines the number of players (FALSE = 2, TRUE = 3)
     */
    public boolean getThreePlayers() {
        return threePlayers;
    }

    /**
     * defines if the game is played by 2 (FALSE) or 3 (TRUE) people
     *
     * @param y boolean that indicates the number of players
     */
    public void setThreePlayers(boolean y) {
        threePlayers = y;
    }

    /**
     * @return the player who's currently playing
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * updates the player who is currently playing
     *
     * @param p the player who is currently playing
     */
    public void setCurrentPlayer(Player p) throws IllegalArgumentException {
        for (int i = 0; i < players.size(); i++)     //POSSIBLE ERROR: BEFORE I PUT "PLAYERS.SIZE() - 1", NOW CHANGED
        {
            Player p1 = players.getPlayer(i);
            if (p1.equals(p)) {
                currentPlayer = p;
                return;
            }
        }
        throw new IllegalArgumentException();
    }

    /**
     * @return the list of players of the game
     */
    public PlayerList getPlayers() {
        return players;
    }

    /**
     * sets the players of the game
     *
     * @param p player to add in the game
     */
    public void setPlayers(Player p) {
        players.addPlayer(p);
    }

    /**
     * @return the old grid, if the move coming from the view is not accepted
     */
    public Grid getOldGrid() {
        return oldGrid;
    }

    /**
     * sets (saves) the old grid
     *
     * @param g is the old grid
     */
    public void setOldGrid(Grid g) {
        oldGrid = g;
    }

    /**
     * @return the new grid, modified with the current player's move
     */
    public Grid getNewGrid() {
        return newGrid;
    }

    /**
     * sets the new grid, modified after the current player's move is accepted
     *
     * @param g is the new grid
     */
    public void setNewGrid(Grid g) {
        newGrid = g;
    }

    /**
     * gives to the Controller the possible moves
     *
     * @return the possible moves
     */
    public MoveList getNextMoves() {
        return nextMoves;
    }

    /**
     * sets the moves the payer will be able to do
     *
     * @param m is the moves the player will be able to do
     */
    public void setNextMoves(MoveList m) {
        nextMoves = m;
    }

    /**
     * @return the list of divinities of the game
     */
    public DivinityList getInGameDivinities() {
        return inGameDivinities;
    }

    /**
     * constructor
     *
     * @param nTur    indicates the number of turn passed + 1
     * @param CodG    the game ID which is unique
     * @param threePl boolean which indicates if the game has three players (true)
     * @param currPl  indicates the current player of the 2/3 that are playing the game
     * @param oldG    the old grid, before the move
     * @param newG    the new grid made after the move was set
     * @param move    the possible moves
     */
    public Game(int nTur, String CodG, boolean threePl, Player currPl, Grid oldG, Grid newG, MoveList move) {
        nTurns = nTur;
        CodGame = CodG;
        threePlayers = threePl;
        currentPlayer = currPl;
        players = new PlayerList();       // NOT SURE
        oldGrid = oldG;
        newGrid = newG;
        nextMoves = move;
        availableLevel1Buildings = 22;
        availableLevel2Buildings = 18;
        availableLevel3Buildings = 14;
        availableDomes = 18;
        inGameDivinities = new DivinityList();
    }

}
