package Utils;

import Server.Model.*;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MessageSerializerTest {
    MessageSerializer messageSerializer;

    @Before
    public void setUp() throws Exception {
        messageSerializer = new MessageSerializer();
    }

    @After
    public void tearDown() throws Exception {
        messageSerializer = null;
    }

    @Test
    public void serializeJoinGameTest() {
        assertEquals("{\"header\":\"JoinGame\",\"username\":\"Player1\",\"3players\":false,\"gameID\":\"NULL\"}",
                messageSerializer.serializeJoinGame("Player1", false, null).toString());
    }

    @Test
    public void serializeSendDivinitiesTest() {
        ArrayList<Divinity> divinities = new ArrayList<Divinity>();
        divinities.add(Divinity.APOLLO);
        divinities.add(Divinity.ARTEMIS);
        assertEquals("{\"header\":\"TestHeader\",\"divinities\":\"[\\\"APOLLO\\\",\\\"ARTEMIS\\\"]\",\"gameID\":\"\"}",
                messageSerializer.serializeDivinities(divinities, "TestHeader", "").toString());
    }

    @Test
    public void serializeSendDivinityTest() {
        assertEquals("{\"header\":\"SendChosenDivinity\",\"divinity\":\"\\\"APOLLO\\\"\",\"username\":\"username\",\"gameID\":\"gameID\"}",
                messageSerializer.serializeDivinity(Divinity.APOLLO, "username", "gameID").toString());
    }

    @Test
    public void serializeStartingPositionTest() {
        Player testPlayer = new Player("Player1", Divinity.ATHENA, Colour.BLACK);
        Cell cell = new Cell(new Tower(1, false), new Pawn(testPlayer));
        Grid grid = new Grid();
        grid.setCells(cell, 2, 3);
        Gson gson = new Gson();
        System.out.println(messageSerializer.serializeStartingPosition(grid, "TestHeader", "username", "gameID"));
    }

    @Test
    public void serializeChosenMoveTest() {
        Player testPlayer = new Player("Player1", Divinity.ATHENA, Colour.BLACK);
        Pawn pawn = new Pawn(testPlayer);
        Cell cell = new Cell(new Tower(1, false), pawn);
        Game game = new Game(0, "", false, new Player("G1", Divinity.ATHENA, Colour.RED), new Grid(), new Grid(), new MoveList());
        Move move = new Move(pawn);
        move.setX(2);
        move.setY(2);
        System.out.println(messageSerializer.serializeChosenMove(game, move));
    }

    @Test
    public void serializeNextMovesTest() {
        Player testPlayer = new Player("Player1", Divinity.ATHENA, Colour.BLACK);
        Pawn pawn = new Pawn(testPlayer);
        Cell cell = new Cell(new Tower(1, false), pawn);
        MoveList moves = new MoveList();
        Move move1 = new Move(pawn);
        Move move2 = new Move(pawn);
        Grid grid = new Grid();
        grid.setCells(cell, 2, 3);
        move1.setX(2);
        move1.setY(2);
        move2.setX(2);
        move2.setY(0);
        moves.addMove(move1);
        moves.addMove(move2);

        System.out.println(messageSerializer.serializeNextMoves(grid, moves, "Player1"));
    }
}
