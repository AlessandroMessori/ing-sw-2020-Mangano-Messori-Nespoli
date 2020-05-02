package it.polimi.ingsw.PSP19.Server.Model;

import java.util.ArrayList;

public class PlayerList {

    private ArrayList<Player> players;

    /**
     * @return size;
     */
    public int size() {
        return players.size();
    }

    /**
     * adds a new player to the list,throws exception if player is already present
     *
     * @param player the player to add;
     */
    public void addPlayer(Player player) throws IllegalArgumentException {

        for (Player pl : players) {
            if (player.getUsername().equals(pl.getUsername()) ||
                    (player.getDivinity() == pl.getDivinity() && pl.getDivinity() != null) ||
                    player.getColour() == pl.getColour() && pl.getColour() != null) {
                throw new IllegalArgumentException();
            }
        }

        players.add(player);
    }

    /**
     * returns player at position i,throws IndexOutOfBoundsException if is greater than list size
     *
     * @param i index of the player
     * @return player at position i
     */
    public Player getPlayer(int i) throws IllegalArgumentException {
        if (i > size() - 1) {
            throw new IllegalArgumentException();
        }

        return players.get(i);
    }

    /**
     * returns random player from the list
     *
     * @return random player
     */
    public Player getRandomPlayer() {
        int randomNumber = (int) (Math.random() * size());

        return players.get(randomNumber);
    }

    /**
     * searches for a player in the list
     *
     * @param uName the username of the player to search
     * @return index of the found player in the list,returns -1 if the player isn't found
     */
    public int searchPlayerByUsername(String uName) {
        int i = 0;
        for (Player pl : players) {
            if (pl.getUsername().equals(uName)) {
                return i;
            }
            i++;
        }

        return -1;
    }

    /**
     * deletes a player,throws exception if the player isn't in the list
     *
     * @param player the player to delete;
     */
    public void deletePlayer(Player player) throws IllegalArgumentException {

        Player playerToDelete;
        playerToDelete = null;

        for (Player pl : players) {
            if (player.getUsername().equals(pl.getUsername())) {
                playerToDelete = pl;
            }
        }

        if (playerToDelete == null) {
            throw new IllegalArgumentException();
        } else {
            players.remove(playerToDelete);
        }

    }

    @Override
    public String toString() {
        return players.toString();
    }

    /**
     * constructor
     */
    public PlayerList() {
        players = new ArrayList<>();
    }


}
