package Server.Model;

import it.polimi.ingsw.PSP19.Server.Model.Colour;
import it.polimi.ingsw.PSP19.Server.Model.Divinity;
import it.polimi.ingsw.PSP19.Server.Model.Pawn;
import it.polimi.ingsw.PSP19.Server.Model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    static Player player;

    @Before
    public void setUp() throws Exception {
        player = new Player("Player1", Divinity.ATHENA, Colour.RED);
    }

    @After
    public void tearDown() throws Exception {
        player = null;
    }

    @Test
    public void getUsernameTest() {
        player = new Player("Player2", Divinity.ATHENA, Colour.RED);
        assertTrue("Player2".equals(player.getUsername()));
    }

    @Test
    public void getDivinityTest() {
        player = new Player("Player1", Divinity.MINOTAUR, Colour.RED);
        assertTrue(player.getDivinity() == Divinity.MINOTAUR);
    }

    @Test
    public void getColourTest() {
        player = new Player("Player1", Divinity.ATHENA, Colour.GREEN);
        assertTrue(player.getColour() == Colour.GREEN);
    }

    @Test
    public void setUsernameTest() {
        player.setUsername("Player2");
        assertEquals("Player2", player.getUsername());
    }

    @Test
    public void setDivinityTest() {
        player.setDivinity(Divinity.ATLAS);
        assertSame(player.getDivinity(), Divinity.ATLAS);
    }

    @Test
    public void setColourTest() {
        player.setColour(Colour.BLUE);
        assertSame(player.getColour(), Colour.BLUE);
    }

    @Test
    public void setCurrentPawnTest() {
        player.setCurrentPawn(new Pawn(player));
        assertEquals(player.getUsername(),player.getCurrentPawn().getOwner().getUsername());
    }
}