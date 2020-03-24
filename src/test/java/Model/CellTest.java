package Model;

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
        cell = new Cell(Type.EMPTY, null,null);
    }

    @After
    public void tearDown() {
        cell = null;
    }

    @Test
    public void getTypeTest() {
        assertSame(cell.getType(), Type.EMPTY);
    }

    @Test
    public void setTypeTest() {
        cell.setType(Type.TOWER);
        assertSame(cell.getType(), Type.TOWER);
    }

    @Test
    public void getTowerTest() {
        tower = new Tower(1,false);
        cell = new Cell(Type.TOWER, tower,null);
        assertSame(cell.getTower(),tower);
    }

    @Test
    public void setTowerTest() {
        tower = new Tower(1,false);
        cell = new Cell(Type.EMPTY, null,null);
        cell.setType(Type.TOWER);
        cell.setTower(tower);
        assertSame(cell.getTower(), tower);
    }

    @Test
    public void getPawnTest() {
        player = new Player("Player1", Divinity.ATHENA, Colour.BLACK);
        pawn = new Pawn(player);
        cell = new Cell(Type.PAWN,null, pawn);
        assertSame(cell.getPawn(), pawn);
    }

    @Test
    public void setPawnTest() {
        player = new Player("Player1", Divinity.ATHENA, Colour.BLACK);
        pawn = new Pawn(player);
        cell = new Cell(Type.EMPTY,null, null);
        cell.setType(Type.PAWN);
        cell.setPawn(pawn);
        assertSame(cell.getPawn(), pawn);
    }
}