package Utils;

import Server.Model.Divinity;
import Server.Model.Grid;
import Server.Model.Move;
import Server.Model.MoveList;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;

public class MessageSerializer {

    private Gson gson;
    // CLIENT Serializers

    /**
     * serializes in JSON a Join Game Message
     *
     * @param username     value of the username
     * @param threePlayers boolean who represents the choice of playing a game with 2 or 3 players
     * @return the JSON serialized Join Game Message
     */
    public JsonElement serializeJoinGame(String username, boolean threePlayers) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("JoinGame"));
        result.add("username", new JsonPrimitive(username));
        result.add("3players", new JsonPrimitive(threePlayers));

        return result;
    }

    /**
     * serializes in JSON a send Divinities Message
     *
     * @param divinities the list of divinities to serialize
     * @param header     header of the message
     * @return the JSON serialized send Divinities Message
     */
    //used by the client and the server
    public JsonElement serializeDivinities(ArrayList<Divinity> divinities, String header) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive(header));
        result.add("divinities", new JsonPrimitive(gson.toJson(divinities)));

        return result;
    }

    /**
     * serializes in JSON a send Divinity Message
     *
     * @param divinity the divinity to serialize
     * @return the JSON serialized send Divinity Message
     */
    public JsonElement serializeDivinity(Divinity divinity) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendChosenDivinity"));
        result.add("divinity", new JsonPrimitive(gson.toJson(divinity)));

        return result;
    }

    /**
     * serializes in JSON a send Starting Position Message
     *
     * @param grid   the grid to serialize
     * @param header header of the message
     * @return the JSON serialized send Starting Position Message
     */
    //used by client and server
    public JsonElement serializeStartingPosition(Grid grid, String header) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive(header));
        result.add("grid", new JsonPrimitive(gson.toJson(grid)));

        return result;
    }

    /**
     * serializes in JSON a send Chosen Move Message
     *
     * @param grid the grid to serialize
     * @param move the move to serialize
     * @return the JSON serialized send Chosen Move Message
     */
    public JsonElement serializeChosenMove(Grid grid, Move move) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendChosenMove"));
        result.add("grid", new JsonPrimitive(gson.toJson(grid)));
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
     * serializes in JSON a send Starting Position Message
     * constructor
     */
    public MessageSerializer() {
        gson = new Gson();
    }

}