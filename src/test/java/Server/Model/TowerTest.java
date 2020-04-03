package Server.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TowerTest {

    static Tower tower;

    @Before
    public void setUp(){
        tower = new Tower(1,false);
    }

    @After
    public void tearDown(){
        tower = null;
    }

    @Test
    public void getLevelTest() {
        tower = new Tower(1,false);
        assertEquals(tower.getLevel(), 1);
    }

    @Test
    public void setLevelTest() {
        tower.setLevel(2);
        assertEquals(tower.getLevel(), 2);

    }

    @Test
    public void getIsDomeTest() {
        tower = new Tower(1,false);
        assertFalse(tower.getIsDome());
    }

    @Test
    public void setIsDomeTest() {
        tower.setIsDome(true);
        assertTrue(tower.getIsDome());
    }
}