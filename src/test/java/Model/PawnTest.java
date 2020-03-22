package Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PawnTest {

    static Pawn pawn;
    static Player player;

    @Before
    public void setUp()
    {
        player = new Player("Player1", Divinity.ATHENA, Colour.RED);
        pawn = new Pawn(player);
    }

    @After
    public void tearDown() {
        player = null;
        pawn = null;
    }

    @Test
    public void getOwnerTest() {
        player = new Player("Player1", Divinity.ATHENA, Colour.RED);
        pawn = new Pawn(player);
        assertEquals("Player1", pawn.getOwner().getUsername());
    }

    @Test
    public void setOwnerTest() {
        player = new Player("Player2", Divinity.ATHENA, Colour.RED);
        pawn.setOwner(player);
        assertSame(pawn.getOwner(), player);

    }
}