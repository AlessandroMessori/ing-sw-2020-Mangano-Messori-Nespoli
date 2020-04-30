package Server.Controller;

import Server.Model.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class ServerControllerTest {

    ServerController serverController;
    Model model;

    @Before
    public void setUp() {
        serverController = new ServerController();
        model = Model.getModel();
    }

    @After
    public void tearDown() {
        serverController = null;
        model.resetModel();
    }

    @Test
    public void randomStringTest() {
        String str = ServerController.randomString(10);
        String st2 = ServerController.randomString(10);
        assertSame(10, str.length());
        assertFalse(str.equals(st2));
    }

    @Test
    public void addPlayerToModelTest() {
        serverController.addPlayerToModel(new Player("G1", Divinity.ATHENA, Colour.RED), true);
        serverController.addPlayerToModel(new Player("G2", Divinity.ATLAS, Colour.BLUE), true);
        serverController.addPlayerToModel(new Player("G3", Divinity.ARTEMIS, Colour.GREEN), true);
        serverController.addPlayerToModel(new Player("G4", Divinity.ATHENA, Colour.RED), false);
        serverController.addPlayerToModel(new Player("G5", Divinity.PAN, Colour.PINK), false);

        assertSame(2,model.getGames().size());
        assertTrue(model.getGames().get(0).getThreePlayers());
        assertFalse(model.getGames().get(1).getThreePlayers());

        assertEquals("G2",model.getGames().get(0).getPlayers().getPlayer(1).getUsername());
        assertEquals("G4",model.getGames().get(1).getPlayers().getPlayer(0).getUsername());
    }

    @Test
    public void setSpecificPlayerDivTest() {

        serverController.addPlayerToModel(new Player("G1",null, Colour.RED), false);
        serverController.addPlayerToModel(new Player("G2", null, Colour.PINK), false);

        String gameID = model.getGames().get(0).getCodGame();

        serverController.setSpecificPlayerDiv(gameID,"G1",Divinity.ATHENA);
        serverController.setSpecificPlayerDiv(gameID,"G2",Divinity.PAN);

        assertSame(Divinity.ATHENA,model.getGames().get(0).getPlayers().getPlayer(0).getDivinity());
        assertSame(Divinity.PAN,model.getGames().get(0).getPlayers().getPlayer(1).getDivinity());

    }

    @Test(expected = IllegalArgumentException.class)
    public void setSpecificPlayerDivTestException() {

        serverController.addPlayerToModel(new Player("G1",null, Colour.RED), false);
        serverController.addPlayerToModel(new Player("G2", null, Colour.PINK), false);

        String gameID = model.getGames().get(0).getCodGame();

        serverController.setSpecificPlayerDiv(gameID,"G1",Divinity.ATHENA);
        serverController.setSpecificPlayerDiv(gameID,"G1",Divinity.ATLAS);

    }

    @Test
    public void getPlayersThatAlreadyPlacedTest() {

        PlayerList pList;
        Player p1,p2;
        p1 = new Player("G1",Divinity.ATLAS, Colour.RED);
        p2 = new Player("G2", Divinity.APOLLO, Colour.PINK);

        serverController.addPlayerToModel(p1, false);
        serverController.addPlayerToModel(p2, false);

        Game game = model.getGames().get(0);

        pList = serverController.getPlayersThatAlreadyPlaced(game.getNewGrid());

        assertSame(0,pList.size());

        Pawn pawn1,pawn2,pawn3,pawn4;

        pawn1 = new Pawn(p1);
        pawn2 = new Pawn(p1);

        game.getNewGrid().setCells(new Cell(null,pawn1),0,0);
        game.getNewGrid().setCells(new Cell(null,pawn2),0,1);

        pList = serverController.getPlayersThatAlreadyPlaced(game.getNewGrid());

        assertSame(1,pList.size());
        assertEquals("G1",pList.getPlayer(0).getUsername());

        pawn3 = new Pawn(p2);
        pawn4 = new Pawn(p2);

        game.getNewGrid().setCells(new Cell(null,pawn3),0,2);
        game.getNewGrid().setCells(new Cell(null,pawn4),0,3);

        pList = serverController.getPlayersThatAlreadyPlaced(game.getNewGrid());

        assertSame(2,pList.size());
        assertEquals("G2",pList.getPlayer(1).getUsername());


    }

}
