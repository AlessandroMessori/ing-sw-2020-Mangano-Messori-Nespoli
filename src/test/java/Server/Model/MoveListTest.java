package Server.Model;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveListTest {

    static MoveList moveList;
    static Player player;
    static Pawn pawn;

    @Before
    public void setUp() throws Exception {
        moveList = new MoveList();
        player = new Player("Pl1", Divinity.ATHENA, Colour.RED);
        pawn = new Pawn(player);
    }

    @After
    public void tearDown() throws Exception {
        moveList = null;
    }

    @Test
    public void getLenTest() {
        moveList = new MoveList();
        Move move1, move2;
        move1 = new Move(pawn);
        move2 = new Move(pawn);

        move1.setX(1);
        move1.setY(2);
        move2.setX(3);
        move2.setY(4);

        moveList.addMove(move1);
        moveList.addMove(move2);

        assertTrue(moveList.size() == 2);
    }

    @Test
    public void getMoveTest() {
        moveList = new MoveList();
        Move move = new Move(pawn);
        move.setX(2);
        move.setY(2);

        moveList.addMove(move);


        Move returnedMove = moveList.getMove(0);

        assertEquals(returnedMove.getX(), 2);
        assertEquals(returnedMove.getY(), 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getMoveExceptionTest() {
        moveList = new MoveList();

        moveList.addMove(new Move(pawn));

        Move Move = moveList.getMove(1);
    }

    @Test
    public void addMoveTest() {

        moveList = new MoveList();
        Move move1, move2;
        move1 = new Move(pawn);
        move2 = new Move(pawn);

        move1.setX(1);
        move1.setY(2);
        move2.setX(3);
        move2.setY(4);

        moveList.addMove(move1);
        moveList.addMove(move2);

        Move returnedMove1, returnedMove2;
        returnedMove1 = moveList.getMove(0);
        returnedMove2 = moveList.getMove(1);

        assertEquals(returnedMove1.getX(), 1);
        assertEquals(returnedMove1.getY(), 2);
        assertEquals(returnedMove2.getX(), 3);
        assertEquals(returnedMove2.getY(), 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addMoveExceptionTest() {

        moveList = new MoveList();
        Move move1, move2, move3;
        move1 = new Move(pawn);
        move2 = new Move(pawn);
        move3 = new Move(pawn);

        move1.setX(1);
        move1.setY(2);
        move2.setX(3);
        move2.setY(4);
        move3.setX(1);
        move3.setY(2);

        moveList.addMove(move1);
        moveList.addMove(move2);


        moveList.addMove(move3); //wrong add
    }

    @Test
    public void deleteMoveTest() {
        moveList = new MoveList();
        Move move1, move2, move3;
        move1 = new Move(pawn);
        move2 = new Move(pawn);
        move3 = new Move(pawn);

        move1.setX(1);
        move1.setY(2);
        move2.setX(3);
        move2.setY(4);
        move3.setX(1);
        move3.setY(2);

        moveList.addMove(move1);
        moveList.addMove(move2);

        Move toDeleteMove = moveList.getMove(0);
        moveList.deleteMove(toDeleteMove);

        assertEquals(1, moveList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteMoveExceptionTest() {
        moveList = new MoveList();
        Move move1, move2, move3;
        move1 = new Move(pawn);
        move2 = new Move(pawn);
        move3 = new Move(pawn);

        move1.setX(1);
        move1.setY(2);
        move2.setX(3);
        move2.setY(4);
        move3.setX(1);
        move3.setY(3);

        moveList.addMove(move1);
        moveList.addMove(move2);
        moveList.deleteMove(move3);
    }

}
