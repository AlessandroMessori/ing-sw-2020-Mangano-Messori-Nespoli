package Server.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveTest {

    static Move move;
    static Pawn pawn;

    @Before
    public void setUp(){
        pawn = new Pawn(new Player("Player1", Divinity.ATHENA, Colour.YELLOW));
        move = new Move(pawn);
    }

    @After
    public void tearDown(){
        pawn = null;
        move = null;
    }

    @Test
    public void getToMoveTest() {
        pawn = new Pawn(new Player("Player2", Divinity.DEMETER, Colour.RED));
        move = new Move(pawn);
        assertSame(move.getToMove(),pawn);
    }

    @Test
    public void setToMoveTest() {
        pawn = new Pawn(new Player("Player2", Divinity.DEMETER, Colour.RED));
        move.setToMove(pawn);
        assertSame(move.getToMove(),pawn);
    }

    @Test
    public void getXTest() {
        move.setX(3);
        assertSame(move.getX(),3);
    }

    @Test
    public void setXTest() {
        move.setX(3);
        assertSame(move.getX(),3);
    }

    @Test
    public void getYTest() {
        move.setY(3);
        assertSame(move.getY(),3);
    }

    @Test
    public void setYTest() {
        move.setY(3);
        assertSame(move.getY(),3);
    }

    @Test
    public void getIfMoveTest() {
        move.setIfMove(true);
        assertTrue(move.getIfMove());
    }

    @Test
    public void setIfMoveTest() {
        move.setIfMove(false);
        assertFalse(move.getIfMove());
    }
}