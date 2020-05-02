package Server.Model;

import it.polimi.ingsw.PSP19.Server.Model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GridTest {

    static Grid grid;
    static Cell cell;

    @Before
    public void setUp() {
        grid = new Grid();
    }

    @After
    public void tearDown() {
        grid = null;
    }

    @Test
    public void getCellsTest() {
        grid = new Grid();
        assertNotNull(grid.getCells(2, 3));
    }

    @Test
    public void setCellsTest() {
        cell = new Cell(new Tower(1, false), null);
        grid = new Grid();
        grid.setCells(cell, 2, 3);
        assertSame(grid.getCells(2, 3), cell);
    }

    @Test
    public void printGridTest() {
        Player testPlayer = new Player("Player1", Divinity.ATHENA, Colour.YELLOW);
        cell = new Cell(new Tower(1, false), new Pawn(testPlayer));
        grid = new Grid();
        grid.setCells(cell, 2, 3);
        System.out.println(grid.toString());
    }
}