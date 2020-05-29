package it.polimi.ingsw.PSP19.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import it.polimi.ingsw.PSP19.Server.Model.*;

import java.util.ArrayList;
import java.util.Objects;

public class MessageSerializer {

    private final Gson gson;
    // CLIENT Serializers

    /**
     * serializes in JSON a Join Game Message
     *
     * @param username     value of the username
     * @param threePlayers boolean who represents the choice of playing a game with 2 or 3 players
     * @param gameID       identifier of the game
     * @return the JSON serialized Join Game Message
     */
    public JsonElement serializeJoinGame(String username, boolean threePlayers, String gameID) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("JoinGame"));
        result.add("username", new JsonPrimitive(username));
        result.add("3players", new JsonPrimitive(threePlayers));

        result.add("gameID", new JsonPrimitive(Objects.requireNonNullElse(gameID, "NULL")));

        return result;
    }

    /**
     * serializes in JSON a Ping Message
     *
     * @param gameID identifier of the Game
     * @return the JSON serialized Ping Message
     */
    public JsonElement serializePing(String gameID) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("Ping"));
        result.add("gameID", new JsonPrimitive(gameID));

        return result;
    }

    /**
     * serializes in JSON a Check Model Message
     *
     * @param gameID identifier of the Game
     * @return the JSON serialized Check Model Message
     */
    public JsonElement serializeCheckModel(String gameID) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("CheckModel"));
        result.add("gameID", new JsonPrimitive(gameID));

        return result;
    }


    /**
     * serializes in JSON a send Divinities Message
     *
     * @param divinities the list of divinities to serialize
     * @param header     header of the message
     * @param gameID     identifier of the game
     * @return the JSON serialized send Divinities Message
     */
    //used by the client and the server
    public JsonElement serializeDivinities(ArrayList<Divinity> divinities, String header, String gameID) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive(header));
        result.add("divinities", new JsonPrimitive(gson.toJson(divinities)));
        result.add("gameID", new JsonPrimitive(gameID));

        return result;
    }

    /**
     * serializes in JSON a send Divinity Message
     *
     * @param divinity the divinity to serialize
     * @param gameID   identifier of the game
     * @param username identifier of the user sending the message
     * @return the JSON serialized send Divinity Message
     */
    public JsonElement serializeDivinity(Divinity divinity, String username, String gameID) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendChosenDivinity"));
        result.add("divinity", new JsonPrimitive(gson.toJson(divinity)));
        result.add("username", new JsonPrimitive(username));
        result.add("gameID", new JsonPrimitive(gameID));

        return result;
    }

    /**
     * serializes in JSON a send Starting Position Message
     *
     * @param grid     the grid to serialize
     * @param header   header of the message
     * @param username identifier of the user sending the message
     * @param gameID   identifier of the game
     * @param color    color of the player
     * @return the JSON serialized send Starting Position Message
     */
    //used by client and server
    public JsonElement serializeStartingPosition(Grid grid, String header, String username, String gameID, Colour color) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive(header));
        result.add("grid", new JsonPrimitive(gson.toJson(grid)));
        result.add("username", new JsonPrimitive(username));
        result.add("gameID", new JsonPrimitive(gameID));
        result.add("colour", new JsonPrimitive(gson.toJson(color)));

        return result;
    }


    /**
     * serializes in JSON a Decides Can Come Up Message
     *
     * @param canComeUp boolean who represents the choice of having the possibility of moving up this turn
     * @param gameID       identifier of the game
     * @return the JSON serialized Join Game Message
     */
    public JsonElement serializeDecideCanComeUp(boolean canComeUp, String gameID) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendCanComeUp"));
        result.add("canComeUp", new JsonPrimitive(canComeUp));

        result.add("gameID", new JsonPrimitive(Objects.requireNonNullElse(gameID, "NULL")));

        return result;
    }

    /**
     * serializes in JSON a send Chosen Pawn Message
     *
     * @param gameID   the id of the game
     * @param username the username of the pawn owner
     * @param pawn     the pawn to serialize
     * @return the JSON serialized send Chosen Pawn Message
     */
    public JsonElement serializeChosenPawn(String gameID, String username, Pawn pawn) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendChosenPawn"));
        result.add("gameID", new JsonPrimitive(gameID));
        result.add("username", new JsonPrimitive(username));
        result.add("pawn", new JsonPrimitive(gson.toJson(pawn)));

        return result;
    }

    /**
     * serializes in JSON a send Chosen Move Message
     *
     * @param game the game to serialize
     * @param move the move to serialize
     * @return the JSON serialized send Chosen Move Message
     */
    public JsonElement serializeChosenMove(Game game, Move move) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendChosenMove"));
        result.add("game", new JsonPrimitive(gson.toJson(game)));
        result.add("move", new JsonPrimitive(gson.toJson(move)));

        return result;
    }

    // SERVER only Serializers

    /**
     * serializes in JSON a send Next Moves Message
     *
     * @param grid            the grid to serialize
     * @param moves           the moves to serialize
     * @param currentPlayerID the id of the current Player
     * @return the JSON serialized send Next Moves Message
     */
    public JsonElement serializeNextMoves(Grid grid, MoveList moves, String currentPlayerID) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendChosenMove"));
        result.add("grid", new JsonPrimitive(gson.toJson(grid)));
        result.add("moves", new JsonPrimitive(gson.toJson(moves)));
        result.add("currentPlayerID", new JsonPrimitive(currentPlayerID));

        return result;
    }

    /**
     * serializes in JSON a send Game Message
     *
     * @param game the game to serialize
     * @return the JSON serialized send Game Message
     */
    public JsonElement serializeGame(Game game) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendGameUpdate"));
        result.add("game", new JsonPrimitive(gson.toJson(game)));

        return result;
    }


    /**
     * serializes in JSON a send Starting Position Message
     * constructor
     */
    public MessageSerializer() {
        gson = new Gson();
    }

}