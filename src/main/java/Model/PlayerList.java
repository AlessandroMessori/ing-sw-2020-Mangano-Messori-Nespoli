package Model;

import java.util.ArrayList;

public class PlayerList {

    private ArrayList<Player> players;

    /**
     * @return size;
     */
    public int getLen() {
        return players.size();
    }

    /**
     * adds a player if not already present
     *
     * @param player the player to add;
     */
    public void addPlayer(Player player) {

        for (Player pl : players) {
            if (player.getUserName().equals(pl.getUserName())) {
                return;
            }
        }

        players.add(player);
    }

    /**
     * deletes a player if present
     *
     * @param player the player to add;
     */
    public void deletePlayer(Player player) {

        for (Player pl : players) {
            if (player.getUserName().equals(pl.getUserName())) {
                players.remove(pl);
            }
        }
    }

    /**
     * constructor
     */
    public void PlayerList() {

        players = new ArrayList<Player>();
    }


}
