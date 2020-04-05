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
        assertEquals("{\"header\":\"JoinGame\",\"username\":\"Player1\",\"3players\":false}",
                messageSerializer.serializeJoinGame("Player1", false).toString());
    }

    @Test
    public void serializeSendDivinitiesTest() {
        ArrayList<Divinity> divinities = new ArrayList<Divinity>();
        divinities.add(Divinity.APOLLO);
        divinities.add(Divinity.ARTEMIS);
        assertEquals("{\"header\":\"SendDivinities\",\"divinities\":\"[APOLLO, ARTEMIS]\"}",
                messageSerializer.serializeDivinities(divinities).toString());
    }

    @Test
    public void serializeSendDivinityTest() {
        assertEquals("{\"header\":\"SendChosenDivinity\",\"divinity\":\"APOLLO\"}",
                messageSerializer.serializeDivinity(Divinity.APOLLO).toString());
    }

    @Test
    public void serializeStartingPositionTest() {
        Player testPlayer = new Player("Player1", Divinity.ATHENA, Colour.BLACK);
        Cell cell = new Cell(new Tower(1, false), new Pawn(testPlayer));
        Grid grid = new Grid();
        grid.setCells(cell, 2, 3);
        System.out.println(messageSerializer.serializeStartingPosition(grid));
    }

    @Test
    public void serializeChosenMoveTest() {
        Player testPlayer = new Player("Player1", Divinity.ATHENA, Colour.BLACK);
        Pawn pawn = new Pawn(testPlayer);
        Cell cell = new Cell(new Tower(1, false), pawn);
        Grid grid = new Grid();
        grid.setCells(cell, 2, 3);
        Move move = new Move(pawn);
        move.setX(2);
        move.setY(2);
        System.out.println(messageSerializer.serializeChosenMove(grid, move));
    }
}
