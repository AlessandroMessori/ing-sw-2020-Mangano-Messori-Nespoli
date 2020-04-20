package Utils;

import Server.Model.*;
import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;


import static org.junit.Assert.*;

public class MessageDeserializerTest {

    static MessageSerializer messageSerializer;
    static MessageDeserializer messageDeserializer;
    static Gson gson;

    @Before
    public void setUp() throws Exception {
        messageSerializer = new MessageSerializer();
        messageDeserializer = new MessageDeserializer();
        gson = new Gson();
    }

    @After
    public void tearDown() throws Exception {
        messageSerializer = null;
        messageDeserializer = null;
        gson = null;
    }

    @Test
    public void deserializeStringTest() {
        String serializedMessage = messageSerializer.serializeJoinGame("Player1", false, null).toString();
        String username = messageDeserializer.deserializeString(serializedMessage, "username");
        assertEquals("Player1", username);
    }

    @Test
    public void deserializeBooleanTest() {
        String serializedMessage = messageSerializer.serializeJoinGame("Player1", false, null).toString();
        boolean threePlayers = messageDeserializer.deserializeBoolean(serializedMessage, "3players");
        assertFalse(threePlayers);
    }

    @Test
    public void deserializeSendDivinitiesTest() {
        ArrayList<Divinity> divinities = new ArrayList<Divinity>();
        divinities.add(Divinity.APOLLO);
        divinities.add(Divinity.ARTEMIS);
        String serializedMessage = messageSerializer.serializeDivinities(divinities, "SendDivinities", "").toString();
        ArrayList<Divinity> deserializedDivinities = messageDeserializer.deserializeObject(serializedMessage, "divinities", ArrayList.class);
        String header = messageDeserializer.deserializeString(serializedMessage, "header");
        assertEquals("SendDivinities", header);
        assertEquals("APOLLO", deserializedDivinities.get(0));
        assertEquals("ARTEMIS", deserializedDivinities.get(1));

    }

    @Test
    public void deserializeDivinityTest() {
        String serializedMessage = messageSerializer.serializeDivinity(Divinity.APOLLO, "username", "gameID").toString();
        Divinity div = messageDeserializer.deserializeObject(serializedMessage, "divinity", Divinity.class);
        assertEquals(Divinity.APOLLO, div);
    }

    @Test
    public void deserializeMoveTest() {
        Player testPlayer = new Player("Player1", Divinity.ATHENA, Colour.YELLOW);
        Pawn pawn = new Pawn(testPlayer);
        Cell cell = new Cell(new Tower(1, false), pawn);
        Game game = new Game(0, "", false, new Player("G1", Divinity.ATHENA, Colour.RED), new Grid(), new Grid(), new MoveList());
        Move move = new Move(pawn);
        move.setX(2);
        move.setY(2);
        String serializedMessage = messageSerializer.serializeChosenMove(game, move).toString();
        Move mv = messageDeserializer.deserializeObject(serializedMessage, "move", Move.class);
    }


    @Test
    public void test() {
        Game game = messageDeserializer.deserializeObject("{\"header\":\"SendGameUpdate\",\"game\":\"{\\\"nTurns\\\":0,\\\"availableLevel1Buildings\\\":22,\\\"availableLevel2Buildings\\\":18,\\\"availableLevel3Buildings\\\":14,\\\"availableDomes\\\":18,\\\"CodGame\\\":\\\"5nwfcdusj5\\\",\\\"threePlayers\\\":false,\\\"currentPlayer\\\":{\\\"username\\\":\\\"G1\\\",\\\"divinity\\\":\\\"APOLLO\\\"},\\\"players\\\":{\\\"players\\\":[{\\\"username\\\":\\\"G1\\\",\\\"divinity\\\":\\\"APOLLO\\\"},{\\\"username\\\":\\\"G2\\\",\\\"divinity\\\":\\\"ATHENA\\\"}]},\\\"inGameDivinities\\\":{\\\"divinities\\\":[]}}\"}\n", "game", Game.class);
    }

    @Test
    public void deserializeGridTest() {
        Player testPlayer = new Player("Player1", Divinity.ATHENA, Colour.YELLOW);
        Cell cell = new Cell(new Tower(1, false), new Pawn(testPlayer));
        Grid grid = new Grid();
        grid.setCells(cell, 2, 3);
        Gson gson = new Gson();
        String message = messageSerializer.serializeStartingPosition(grid, "TestHeader", "username", "gameID").toString();
        Grid deserializedGrid = messageDeserializer.deserializeObject(message, "grid", Grid.class);
        System.out.println(deserializedGrid);
    }

    @Test
    public void deserializeMovesTest() {
        Player testPlayer = new Player("Player1", Divinity.ATHENA, Colour.YELLOW);
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

        String message = messageSerializer.serializeNextMoves(grid, moves, "Player1").toString();
        MoveList deserializedMoves = messageDeserializer.deserializeObject(message, "moves", MoveList.class);
        System.out.println(deserializedMoves);
    }
}
