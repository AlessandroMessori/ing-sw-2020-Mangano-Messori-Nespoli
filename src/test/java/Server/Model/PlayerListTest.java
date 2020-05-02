package Server.Model;

import it.polimi.ingsw.PSP19.Server.Model.Colour;
import it.polimi.ingsw.PSP19.Server.Model.Divinity;
import it.polimi.ingsw.PSP19.Server.Model.Player;
import it.polimi.ingsw.PSP19.Server.Model.PlayerList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerListTest {

    static PlayerList playerList;

    @Before
    public void setUp() throws Exception {
        playerList = new PlayerList();
    }

    @After
    public void tearDown() throws Exception {
        playerList = null;
    }

    @Test
    public void getLenTest() {
        playerList = new PlayerList();

        playerList.addPlayer(new Player("Player1", Divinity.ATHENA, Colour.RED));
        playerList.addPlayer(new Player("Player2", Divinity.ATLAS, Colour.BLUE));

        assertTrue(playerList.size() == 2);
    }

    @Test
    public void getPlayerTest() {
        playerList = new PlayerList();

        playerList.addPlayer(new Player("Player1", Divinity.ATHENA, Colour.RED));

        Player player = playerList.getPlayer(0);

        assertEquals("Player1", player.getUsername());
        assertEquals(player.getDivinity(), Divinity.ATHENA);
        assertEquals(player.getColour(), Colour.RED);
    }

    @Test
    public void getRandomPlayerTest() {
        playerList = new PlayerList();

        playerList.addPlayer(new Player("Player1", Divinity.ATHENA, Colour.RED));
        playerList.addPlayer(new Player("Player2", Divinity.PROMETHEUS, Colour.YELLOW));
        playerList.addPlayer(new Player("Player3", Divinity.ATLAS, Colour.BLUE));

        for (int i = 0; i < 10; i++) {
            System.out.println(playerList.getRandomPlayer());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPlayerExceptionTest() {
        playerList = new PlayerList();

        playerList.addPlayer(new Player("Player1", Divinity.ATHENA, Colour.RED));

        Player player = playerList.getPlayer(1);
    }

    @Test
    public void addPlayerTest() {

        playerList = new PlayerList();

        playerList.addPlayer(new Player("Player1", Divinity.ATHENA, Colour.RED));
        playerList.addPlayer(new Player("Player2", Divinity.ATLAS, Colour.BLUE));

        Player returnedPlayer1, returnedPlayer2;
        returnedPlayer1 = playerList.getPlayer(0);
        returnedPlayer2 = playerList.getPlayer(1);

        assertEquals("Player1", returnedPlayer1.getUsername());
        assertEquals(returnedPlayer1.getDivinity(), Divinity.ATHENA);
        assertEquals(returnedPlayer1.getColour(), Colour.RED);

        assertEquals("Player2", returnedPlayer2.getUsername());
        assertEquals(returnedPlayer2.getDivinity(), Divinity.ATLAS);
        assertEquals(returnedPlayer2.getColour(), Colour.BLUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPlayerExceptionTest() {

        playerList = new PlayerList();

        playerList.addPlayer(new Player("Player1", Divinity.ATHENA, Colour.RED));
        playerList.addPlayer(new Player("Player2", Divinity.ATLAS, Colour.BLUE));


        playerList.addPlayer(new Player("Player2", Divinity.ATHENA, Colour.YELLOW));
    }

    @Test
    public void deletePlayerTest() {
        playerList = new PlayerList();

        playerList.addPlayer(new Player("Player1", Divinity.ATHENA, Colour.RED));
        playerList.addPlayer(new Player("Player2", Divinity.ATLAS, Colour.BLUE));

        Player toDeletePlayer = playerList.getPlayer(0);
        playerList.deletePlayer(toDeletePlayer);

        assertEquals(1, playerList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deletePlayerExceptionTest() {
        playerList = new PlayerList();

        playerList.addPlayer(new Player("Player1", Divinity.ATHENA, Colour.RED));
        playerList.addPlayer(new Player("Player2", Divinity.ATLAS, Colour.BLUE));

        Player toDeletePlayer = new Player("Player3", Divinity.APOLLO, Colour.GREEN);
        playerList.deletePlayer(toDeletePlayer);
    }

    @Test
    public void searchPlayerTest() {
        playerList = new PlayerList();

        playerList.addPlayer(new Player("Player1", Divinity.ATHENA, Colour.RED));
        playerList.addPlayer(new Player("Player2", Divinity.ATLAS, Colour.BLUE));

        Player toSearchPlayer1 = playerList.getPlayer(1);
        Player toSearchPlayer2 = new Player("Player3", Divinity.HEPHAESTUS, Colour.YELLOW);


        assertEquals(1, playerList.searchPlayerByUsername(toSearchPlayer1.getUsername()));
        assertEquals(-1, playerList.searchPlayerByUsername(toSearchPlayer2.getUsername()));
    }

    @Test
    public void playerListToStringTest() {
        playerList = new PlayerList();

        playerList.addPlayer(new Player("Player1", Divinity.ATHENA, Colour.RED));
        playerList.addPlayer(new Player("Player2", Divinity.ATLAS, Colour.BLUE));

        assertEquals("[Player1, Player2]",playerList.toString());
    }

}