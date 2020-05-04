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
        Player player1 = new Player("player1", Divinity.ARTEMIS,Colour.BLUE);
        Player player2 = new Player("player2", Divinity.ATHENA,Colour.PINK);
        Pawn pawn11 = new Pawn(player1);
        pawn11.setId(29);
        Pawn pawn12 = new Pawn(player1);
        pawn12.setId(40);
        Pawn pawn21 = new Pawn(player2);
        pawn21.setId(319);
        Pawn pawn22 = new Pawn(player2);
        pawn22.setId(398);



        MoveList moveList = new MoveList();
        game = new Game(2,"dajen23rk2", false, player1, grid, grid, moveList);
        game.getNewGrid().getCells(1,2).setPawn(pawn11);
        game.getNewGrid().getCells(2,3).setPawn(pawn21);
        game.getNewGrid().getCells(3,4).setPawn(pawn12);
        game.getNewGrid().getCells(0,0).setPawn(pawn22);
        move = new Move(pawn11);
        move.setX(1);
        move.setY(3);
        move.setIfMove(true);
        moveList.addMove(move);

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


    }
}