package Server.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ModelTest {

    Model model = Model.getModel();

    @Before
    public void setUp(){
        Model model = Model.getModel();
        model.resetModel();
    }

    @After
    public void tearDown(){
        model = null;
    }

    @Test
    public void getLenTest(){
        Game game1,game2;
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game1 = new Game(1, "01", false, p1, g1, g2,null);
        game2 = new Game(1,"02",false,p1,g1,g2,null);
        model.addGame(game1);
        model.addGame(game2);
        assertTrue(model.getLen() == 2);

    }
    @Test
    public void addGameTest(){
        Game game;
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game = new Game(1, "01", false, p1, g1, g2,null);
        model.addGame(game);
        assertTrue(model.searchID("01").equals(game));

    }

    @Test(expected = IllegalArgumentException.class)
    public void addGameExceptionTest(){
        Game game1,game2;
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game1 = new Game(1, "01", false, p1, g1, g2,null);
        game2 = new Game(1,"01",false,p1,g1,g2,null);
        model.addGame(game1);
        model.addGame(game2);

    }

    @Test
    public void delGameTest(){
        Game game1,game2;
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game1 = new Game(1, "01", false, p1, g1, g2,null);
        game2 = new Game(1, "02", false, p1, g1, g2,null);
        model.addGame(game1);
        model.addGame(game2);
        model.delGame(game2);
        assertNull(model.searchID("02"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void delGameExceptionTest(){
        Game game1,game2;
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game1 = new Game(1, "01", false, p1, g1, g2,null);
        game2 = new Game(1, "02", false, p1, g1, g2,null);
        model.addGame(game1);
        model.delGame(game2);

    }

    @Test
    public void searchIDTest(){
        Game game;
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game = new Game(1, "01", false, p1, g1, g2,null);
        model.addGame(game);
        assertTrue(model.searchID("01").getCodGame().equals("01"));

    }

    @Test
    public void loadGameTest(){     //TO IMPLEMENT IN THE FUTURE
        Game game;
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game = new Game(1, "01", false, p1, g1, g2,null);
        model.loadGame(game);
                                    //TO IMPLEMENT IN THE FUTURE
    }

}
