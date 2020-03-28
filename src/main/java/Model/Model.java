package Model;

import java.util.ArrayList;

public class Model {
    static private Model Instance = null;
    static private ArrayList<Game> games;
    static private String path;

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
    public void addGame(Game g) throws IllegalArgumentException
    {
        for(Game g1 : games)
        {
            if(g1.getCodGame().equals(g.getCodGame()))
            {
                throw new IllegalArgumentException();
            }
        }
        games.add(g);

    }

    /**
     *
     * @param CodG game ID I want to verify if already tied to a game
     * @return the game with CodG as ID, else null
     */
    public Game searchID(String CodG)
    {
        for(Game g1 : games)
        {
            if(g1.getCodGame().equals(CodG))
            {
                return(g1);
            }
        }
        final Game o = null;
        return o;
    }

    /**
     *
     * deletes a game from the list
     *
     * @param g game to delete from the list
     */
    public void delGame(Game g) throws IllegalArgumentException {
        Game GameToDelete;
        GameToDelete = null;

        for(Game g1 : games)
        {
            if(g1.getCodGame().equals(g.getCodGame()))
            {
                GameToDelete = g;
            }
        }

        if(GameToDelete == null)
        {
            throw new IllegalArgumentException();
        }
        else
        {
            games.remove(GameToDelete);
        }
    }

    /**
     *
     * Since Model is SINGLETON, a reset method is required for testing
     */
     public void resetModel(){
         final String DefPath = "Bruh";
         Instance = new Model(DefPath);
    }

    /**
     *
     * loads a game left unfinished
     *
     * @param g game to load
     */
    public void loadGame(Game g) {  }       //TO IMPLEMENT IN THE FUTURE

    /**
     * SINGLETON
     * private constructor
     * @param p the path of the file where to save game IDs
     */
    private Model (String p) {
        games = new ArrayList<Game>();
        path = p;
    }

    /**
     * SINGLETON
     * public constructor
     * @return the instance of the Model
     */
    static public Model getModel() {
        if(Instance == null)
        {
            final String DefPath = "Bruh";      //INSERT HERE THE FILE PATH WHERE TO SAVE GAME IDs
            Instance = new Model(DefPath);
        }

        return Instance;
    }
}
