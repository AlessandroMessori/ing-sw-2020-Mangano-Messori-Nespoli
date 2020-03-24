package Model;

import java.util.ArrayList;

public class Model {
    private ArrayList<Game> games;
    private String path;

    /**
     *
     * @return number of games played left before finished
     */
    public int getLen() {  return(games.size()); }

    /**
     *
     * adds a game to the list of games
     *
     * @param g game to add
     */
    public void addGame(Game g) { games.add(g); }         //MISSES EXCEPTION HANDLING

    /**
     *
     * deletes a game from the list
     *
     * @param g game to delete from the list
     */
    public void delGame(Game g) { games.remove(g); }      //MISSES EXCEPTION HANDLING

    /**
     *
     * loads a game left unfinished
     *
     * @param g game to load
     */
    public void loadGame(Game g) {  }       //non so come farlo

    /**
     * constructor
     * @param p
     */
    public void Model(String p) {
        games = new ArrayList<Game>();
        path = p;
    }
}
