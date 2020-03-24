package Model;

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
        assertTrue("Player2".equals(player.getUsername()));
    }

    @Test
    public void setDivinityTest() {
        player.setDivinity(Divinity.ATLAS);
        assertTrue(player.getDivinity() == Divinity.ATLAS);
    }

    @Test
    public void setColourTest() {
        player.setColour(Colour.BLUE);
        assertTrue(player.getColour() == Colour.BLUE);
    }
}