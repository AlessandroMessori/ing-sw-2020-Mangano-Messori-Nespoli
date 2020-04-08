package Server.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    static Game game;

    @Before
    public void setUp() throws Exception {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game = new Game(1, "01", false, p1, g1, g2, null);
        game.setPlayers(p1);
        game.setPlayers(p2);
    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }

    @Test
    public void setNTurnsTest() {
        game.setNTurns(10);
        assertTrue(game.getNTurns() == 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNTurnsExceptionTest() {
        game.setNTurns(-10);
    }

    @Test
    public void getNTurnsTest() {
        assertTrue(game.getNTurns() == 1);
    }

    @Test
    public void getCodGameTest() {
        assertEquals("01", game.getCodGame());
    }

    @Test
    public void setCodGame() {
        game.setCodGame("02");
        assertEquals("02", game.getCodGame());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCodGameExceptionTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Model model = Model.getModel();
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        model.addGame(new Game(1, "01", true, p1, g1, g2, null));
        game.setCodGame("01");
    }

    @Test
    public void getThreePlayersTest() {
        assertTrue(game.getThreePlayers() == false);
    }

    @Test
    public void setThreePlayersTest() {
        game.setThreePlayers(true);
        assertTrue(game.getThreePlayers() == true);
    }

    @Test
    public void getCurrentPlayerTest() {
        assertSame("Player1", game.getCurrentPlayer().getUsername());
    }

    @Test
    public void setCurrentPlayerTest() {
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);

        game.setCurrentPlayer(game.getPlayers().getPlayer(1));
        assertSame("Player2", game.getCurrentPlayer().getUsername());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setCurrentPlayerExceptionTest() {
        Player p3 = new Player("Player3", Divinity.ATLAS, Colour.BLUE);

        game.setCurrentPlayer(p3);
    }

    @Test
    public void getPlayersTest() {
        PlayerList players;
        players = game.getPlayers();
        assertSame("Player1", players.getPlayer(0).getUsername());
        assertSame("Player2", players.getPlayer(1).getUsername());

    }

    @Test
    public void setPlayersTest() {
        Player p3 = new Player("Player3", Divinity.HEPHAESTUS, Colour.PINK);
        game.setThreePlayers(true);
        game.setPlayers(p3);
        assertSame("Player3", game.getPlayers().getPlayer(2).getUsername());

    }

    @Test
    public void getOldGridTest() {
        Grid g1 = new Grid();
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Cell c1 = new Cell(new Tower(2, false), new Pawn(p1));

        g1 = game.getOldGrid();
        g1.setCells(c1, 3, 4);
        game.setOldGrid(g1);
        assertTrue(game.getOldGrid().getCells(3, 4).getPawn().getOwner().getUsername().equals("Player1"));
        assertFalse(game.getOldGrid().getCells(3, 4).getTower().getIsDome());
        assertSame(2, game.getOldGrid().getCells(3, 4).getTower().getLevel());
    }

    @Test
    public void setOldGridTest() {
        Grid g1 = new Grid();
        Grid g2 = new Grid();
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Cell c1 = new Cell(new Tower(2, false), new Pawn(p1));
        Cell c2 = new Cell(new Tower(1, true), new Pawn(p2));

        g1 = game.getOldGrid();
        g1.setCells(c1, 3, 4);
        game.setOldGrid(g1);
        assertTrue(game.getOldGrid().getCells(3, 4).getPawn().getOwner().getUsername().equals("Player1"));
        assertFalse(game.getOldGrid().getCells(3, 4).getTower().getIsDome());
        assertSame(2, game.getOldGrid().getCells(3, 4).getTower().getLevel());

        g2.setCells(c2, 1, 2);
        game.setOldGrid(g2);
        assertTrue(game.getOldGrid().getCells(1, 2).getPawn().getOwner().getUsername().equals("Player2"));
        assertTrue(game.getOldGrid().getCells(1, 2).getTower().getIsDome());
        assertSame(1, game.getOldGrid().getCells(1, 2).getTower().getLevel());
    }

    @Test
    public void getNewGridTest() {
        Grid g1 = new Grid();
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Cell c1 = new Cell(new Tower(2, false), new Pawn(p1));

        g1 = game.getNewGrid();
        g1.setCells(c1, 3, 4);
        game.setNewGrid(g1);
        assertTrue(game.getNewGrid().getCells(3, 4).getPawn().getOwner().getUsername().equals("Player1"));
        assertFalse(game.getNewGrid().getCells(3, 4).getTower().getIsDome());
        assertSame(2, game.getNewGrid().getCells(3, 4).getTower().getLevel());
    }

    @Test
    public void setNewGridTest() {
        Grid g1 = new Grid();
        Grid g2 = new Grid();
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Cell c1 = new Cell(new Tower(2, false), new Pawn(p1));
        Cell c2 = new Cell(new Tower(1, true), new Pawn(p2));

        g1 = game.getNewGrid();
        g1.setCells(c1, 3, 4);
        game.setNewGrid(g1);
        assertTrue(game.getNewGrid().getCells(3, 4).getPawn().getOwner().getUsername().equals("Player1"));
        assertFalse(game.getNewGrid().getCells(3, 4).getTower().getIsDome());
        assertSame(2, game.getNewGrid().getCells(3, 4).getTower().getLevel());

        g2.setCells(c2, 1, 2);
        game.setNewGrid(g2);
        assertTrue(game.getNewGrid().getCells(1, 2).getPawn().getOwner().getUsername().equals("Player2"));
        assertTrue(game.getNewGrid().getCells(1, 2).getTower().getIsDome());
        assertSame(1, game.getNewGrid().getCells(1, 2).getTower().getLevel());


    }

    @Test
    public void getNextMovesTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        MoveList m1;

        m1 = new MoveList();
        m1.addMove(new Move(new Pawn(p1)));
        game.setNextMoves(m1);
        assertTrue(game.getNextMoves().getMove(0).getToMove().getOwner().getUsername().equals("Player1"));

    }

    @Test
    public void setNextMovesTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p3 = new Player("Player3", Divinity.HEPHAESTUS, Colour.PINK);
        MoveList m1, m2;

        m1 = new MoveList();
        m1.addMove(new Move(new Pawn(p1)));
        game.setNextMoves(m1);
        assertTrue(game.getNextMoves().getMove(0).getToMove().getOwner().getUsername().equals("Player1"));

        m2 = new MoveList();
        m2.addMove(new Move(new Pawn(p3)));
        game.setNextMoves(m2);
        assertSame("Player3", game.getNextMoves().getMove(0).getToMove().getOwner().getUsername());
    }

    @Test
    public void getAvailableLevel1BuildingsTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game = new Game(1, "01", false, p1, g1, g2, null);
        game.setPlayers(p1);
        game.setPlayers(p2);
        assertEquals(22, game.getAvailableLevel1Buildings());
    }

    @Test
    public void decreaseAvailableLevel1BuildingsTest() {
        int buildingsLeft = game.getAvailableLevel1Buildings();
        game.decreaseAvailableLevel1Buildings();
        assertEquals(buildingsLeft - 1, game.getAvailableLevel1Buildings());
    }

    @Test
    public void getAvailableLevel2BuildingsTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game = new Game(1, "01", false, p1, g1, g2, null);
        game.setPlayers(p1);
        game.setPlayers(p2);
        assertEquals(18, game.getAvailableLevel2Buildings());
    }

    @Test
    public void decreaseAvailableLevel2BuildingsTest() {
        int buildingsLeft = game.getAvailableLevel2Buildings();
        game.decreaseAvailableLevel2Buildings();
        assertEquals(buildingsLeft - 1, game.getAvailableLevel2Buildings());
    }

    @Test
    public void getAvailableLevel3BuildingsTest() {
        Player p1 = new Player("Player1", Divinity.ATHENA, Colour.RED);
        Player p2 = new Player("Player2", Divinity.ATLAS, Colour.BLUE);
        Grid g1 = new Grid();
        Grid g2 = new Grid();

        game = new Game(1, "01", false, p1, g1, g2, null);
        game.setPlayers(p1);
        game.setPlayers(p2);
        game = new Game(1, "01", false, p1, g1, g2, null);
        assertEquals(14, game.getAvailableLevel3Buildings());
    }

    @Test
    public void decreaseAvailableLevel3BuildingsTest() {
        int buildingsLeft = game.getAvailableLevel3Buildings();
        game.decreaseAvailableLevel3Buildings();
        assertEquals(buildingsLeft - 1, game.getAvailableLevel3Buildings());
    }

}
