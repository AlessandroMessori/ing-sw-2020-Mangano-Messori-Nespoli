package Server.Controller;

import Server.Model.*;

import java.util.ArrayList;
import java.util.Random;

public class ServerController {


    private ArrayList<Divinity> inGameDivinities;


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
    public MoveList calculateNextMove(Grid grid, Player p){
        MoveList movelist = null;

        Turn turn = new Turn(p.getDivinity());
        turn.startingTurn();



        return movelist;
    }

    /**
     *
     * @param p player to add the divinity
     * @param div divinity to add to the player
     */
    public void addDivinityToPlayer(Player p, Divinity div){
        p.setDivinity(div);
        deleteDivinity(div);
    }

    /**
     *
     * @param grid new grid after the move
     * @param gameID the ID of the current game
     */
    public void updateModelGrid(Grid grid, String gameID){
        Model model = Model.getModel();
        Game game = model.searchID(gameID);
        game.setOldGrid(game.getNewGrid());
        game.setNewGrid(grid);          //TODO: SEND THE NEW GRID TO OTHER PLAYERS?
    }
}
