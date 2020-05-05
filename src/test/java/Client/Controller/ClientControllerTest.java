package Client.Controller;

import it.polimi.ingsw.PSP19.Client.Controller.ClientController;
import it.polimi.ingsw.PSP19.Server.Model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ClientControllerTest {
    Grid grid;
    Grid newGrid;
    Move move;
    Game game;
    ClientController clientController;

    @Before
    public void setUp(){
        clientController = new ClientController();
        grid = new Grid();
        newGrid = new Grid();
    }

    @After
    public void tearDown(){
        clientController = null;
        grid = null;
        newGrid = null;
    }

    @Test
    public void updateGameByMoveTest() {
        Player player1 = new Player("player1", Divinity.ATLAS,Colour.BLUE);
        Player player2 = new Player("player2", Divinity.ATHENA,Colour.PINK);

        //PlayerList players = new PlayerList();
        //players.addPlayer(player1);
        //players.addPlayer(player2);
        Pawn pawn11 = new Pawn(player1);
        pawn11.setId(29);
        Pawn pawn12 = new Pawn(player1);
        pawn12.setId(40);
        Pawn pawn21 = new Pawn(player2);
        pawn21.setId(319);
        Pawn pawn22 = new Pawn(player2);
        pawn22.setId(398);



        MoveList moveList = new MoveList();
        move = new Move(pawn11);
        move.setX(1);
        move.setY(3);
        move.setIfMove(true);
        moveList.addMove(move);
        game = new Game(2,"dajen23rk2", false, player1, grid, grid, moveList);
        game.getNewGrid().getCells(1,2).setPawn(pawn11);
        game.getNewGrid().getCells(2,3).setPawn(pawn21);
        game.getNewGrid().getCells(3,4).setPawn(pawn12);
        game.getNewGrid().getCells(0,0).setPawn(pawn22);

        game.setPlayers(player1);
        game.setPlayers(player2);



        //Cell cell = new Cell((new Tower(0, false)), pawn11);
        //clientController.updateGameByMove(move,game);
        newGrid.getCells(1,2).setPawn(null);
        newGrid.getCells(1,3).setPawn(pawn11);
        newGrid.getCells(2,3).setPawn(pawn21);
        newGrid.getCells(3,4).setPawn(pawn12);
        newGrid.getCells(0,0).setPawn(pawn22);

        game = clientController.updateGameByMove(move,game);
        int i = 0;
        for( int x = 0; x < 5; x++){
            for(int y = 0; y < 5; y++){
                if((newGrid.getCells(x,y).getPawn() != null)&&(game.getNewGrid().getCells(x, y).getPawn() != null)) {
                    if (newGrid.getCells(x, y).getPawn().getId()== game.getNewGrid().getCells(x, y).getPawn().getId()) {
                        i++;
                    }
                }
            }
        }
        assertEquals(4,i);

        move = new Move(pawn12);
        move.setX(3);
        move.setY(3);
        move.setIfMove(false);
        game = clientController.updateGameByMove(move,game);
        assertEquals(1, game.getNewGrid().getCells(3,3).getTower().getLevel());

        move = new Move(pawn12);
        move.setX(3);
        move.setY(3);
        move.setIfMove(false);
        game = clientController.updateGameByMove(move,game);
        assertEquals(2, game.getNewGrid().getCells(3,3).getTower().getLevel());

        move = new Move(pawn12);
        move.setX(3);
        move.setY(3);
        move.setIfMove(false);
        game = clientController.updateGameByMove(move,game);
        assertEquals(3, game.getNewGrid().getCells(3,3).getTower().getLevel());

        move = new Move(pawn12);
        move.setX(3);
        move.setY(3);
        move.setIfMove(false);
        game = clientController.updateGameByMove(move,game);
        assertEquals(4, game.getNewGrid().getCells(3,3).getTower().getLevel());
        assert(game.getNewGrid().getCells(3,3).getTower().getIsDome());

        move = new Move(pawn12);
        move.setX(-5);
        move.setY(-3);
        move.setIfMove(false);
        game = clientController.updateGameByMove(move,game);
        assertEquals(1, game.getNewGrid().getCells(4,2).getTower().getLevel());
        assert(game.getNewGrid().getCells(4,2).getTower().getIsDome());

        game.setCurrentPlayer(player2);
        game.getNewGrid().getCells(2,3).setTower(new Tower(2,false));
        game.getNewGrid().getCells(3,3).setTower(new Tower(3,false));
        move = new Move(pawn21);
        move.setX(3);
        move.setY(3);
        move.setIfMove(true);
        game = clientController.updateGameByMove(move,game);
        assertSame(pawn21, game.getNewGrid().getCells(3,3).getPawn());
        assertSame(player2, game.getWinner());



        game.setCurrentPlayer(player1);
        game.getCurrentPlayer().setDivinity(Divinity.PAN);
        game.getNewGrid().getCells(2,4).setTower(new Tower(1,false));
        move = new Move(pawn12);
        move.setX(2);
        move.setY(4);
        move.setIfMove(true);
        game = clientController.updateGameByMove(move,game);
        move = new Move(pawn12);
        move.setX(2);
        move.setY(3);
        move.setIfMove(true);
        game = clientController.updateGameByMove(move,game);
        move = new Move(pawn12);
        move.setX(2);
        move.setY(2);
        move.setIfMove(true);
        game = clientController.updateGameByMove(move,game);

        assertSame(pawn12, game.getNewGrid().getCells(2,2).getPawn());
        assertSame(player1, game.getWinner());

        /* Apollo e Minotauro da finire
        game.setCurrentPlayer(player1);
        game.getCurrentPlayer().setDivinity(Divinity.MINOTAUR);
        game.setCurrentPlayer(player2);
        game.getCurrentPlayer().setDivinity(Divinity.APOLLO);
        Player player3 = new Player("player3", Divinity.DEMETER, Colour.RED);
        game.setPlayers(player3);
        Pawn pawn31 = new Pawn(player3);
        pawn31.setId(729);
        Pawn pawn32 = new Pawn(player3);
        pawn32.setId(740);
        grid = new Grid();
        game.setNewGrid(grid);
        game.getNewGrid().getCells(1,1).setPawn(pawn31);
        game.getNewGrid().getCells(3,1).setPawn(pawn32);
        game.getNewGrid().getCells(1,2).setPawn(pawn11);
        game.getNewGrid().getCells(2,3).setPawn(pawn21);
        game.getNewGrid().getCells(3,4).setPawn(pawn12);
        game.getNewGrid().getCells(0,0).setPawn(pawn22);

        game.setCurrentPlayer(player1);
        move = new Move(pawn11);
        move.setX(1);
        move.setY(1);
        move.setIfMove(true);
        game = clientController.updateGameByMove(move,game);
        assertSame(pawn31, game.getNewGrid().getCells(1,0).getPawn());

        game.setCurrentPlayer(player2);
        move = new Move(pawn22);
        move.setX(1);
        move.setY(0);
        move.setIfMove(true);
        game = clientController.updateGameByMove(move,game);

        assertSame(pawn31, game.getNewGrid().getCells(0,0).getPawn());
        assertSame(pawn22, game.getNewGrid().getCells(1,0).getPawn());
        assertSame(pawn11, game.getNewGrid().getCells(1,1).getPawn());
        */

    }
}