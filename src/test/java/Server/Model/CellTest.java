package Server.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {

    static Cell cell;
    static Tower tower;
    static Pawn pawn;
    static Player player;

    @Before
    public void setUp() {
        cell = new Cell(new Tower(0,false), null);
    }

    @After
    public void tearDown() {
        cell = null;
    }

    @Test
    public void getTowerTest() {
        tower = new Tower(1,false);
        cell = new Cell(tower,null);
        assertSame(cell.getTower(),tower);
    }

    @Test
    public void setTowerTest() {
        tower = new Tower(1,false);
        cell = new Cell(new Tower(0,false), null);
        cell.setTower(tower);
        assertSame(cell.getTower(), tower);
    }

    @Test
    public void getPawnTest() {
        player = new Player("Player1", Divinity.ATHENA, Colour.YELLOW);
        pawn = new Pawn(player);
        cell = new Cell(new Tower(0,false), pawn);
        assertSame(cell.getPawn(), pawn);
    }

    @Test
    public void setPawnTest() {
        player = new Player("Player1", Divinity.ATHENA, Colour.YELLOW);
        pawn = new Pawn(player);
        cell = new Cell(new Tower(0,false), null);
        cell.setPawn(pawn);
        assertSame(cell.getPawn(), pawn);
    }
}