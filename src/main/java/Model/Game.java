package Model;

import java.util.*;

public class Game {
    private int nTurns;
    private String CodGame;
    private boolean threePlayers;
    private Player currentPlayer;
    private PlayerList players;
    private Grid oldGrid;
    private Grid newGrid;

    /**
     *
     * @return number of turns played
     */
    public int getNTurns() { return nTurns; }

    /**
     * sets the number of turns
     * @param t updated number of turns played
     */
    public void setNTurns(int t) { nTurns = t; }

    /**
     *
     * @return the game ID
     */
    public String getCodGame() { return CodGame; }

    /**
     * sets the ID of the game
     * @param c the ID of the game
     */
    public void setCodGame(String c) { CodGame = c; }

    /**
     * defines if the game is played by 2 (FALSE) or 3 (TRUE) people
     * @param y boolean that indicates the number of players
     */
    public void setThreePlayers(boolean y) { threePlayers = y; }

    /**
     *
     * @return the player who's currently playing
     */
    public Player getCurrentPlayer() { return currentPlayer; }

    /**
     * updates the player who is currently playing
     * @param p the player who is currently playing
     */
    public void setCurrentPlayer(Player p) { currentPlayer = p; }

    /**
     *
     * @return the list of players of the game
     */
    public PlayerList getPlayers() { return players; }

    /**
     * sets the players of the game
     * @param p player to add in the game
     */
    public void setPlayers(Player p) { players.addPlayer(p); }      //MISSES EXCEPTION HANDLING

    /**
     *
     * @return the old grid, if the move coming from the view is not accepted
     */
    public Grid getOldGrid() { return oldGrid; }

    /**
     * sets (saves) the old grid
     * @param g is the old grid
     */
    public void setOldGrid(Grid g) { oldGrid = g; }

    /**
     *
     * @return the new grid, modified with the current player's move
     */
    public Grid getNewGrid() { return newGrid; }

    /**
     * sets the new grid, modified after the current player's move is accepted
     * @param g is the new grid
     */
    public Grid setNewGrid(Grid g) { newGrid = g; }

    /**
     * constructor
     */
    public void Game(int nTur, String CodG, boolean threePl, Player currPl, Grid oldG, Grid newG)
    {
        nTurns = nTur;
        CodGame = CodG;
        threePlayers = threePl;
        currentPlayer = currPl;
        players = new PlayerList();       // non sono sicuro
        oldGrid = oldG;
        newGrid = newG;
    }

}
