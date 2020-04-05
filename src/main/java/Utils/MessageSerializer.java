package Utils;

import Server.Model.Divinity;
import Server.Model.Grid;
import Server.Model.Move;
import Server.Model.MoveList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.ArrayList;

public class MessageSerializer {

    // CLIENT Serializers

    public JsonElement serializeJoinGame(String username, boolean threePlayers) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("JoinGame"));
        result.add("username", new JsonPrimitive(username));
        result.add("3players", new JsonPrimitive(threePlayers));

        return result;
    }

    //used by the client and the server
    public JsonElement serializeDivinities(ArrayList<Divinity> divinities, String header) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive(header));
        result.add("divinities", new JsonPrimitive(divinities.toString()));

        return result;
    }

    public JsonElement serializeDivinity(Divinity divinity) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendChosenDivinity"));
        result.add("divinity", new JsonPrimitive(divinity.toString()));

        return result;
    }

    //used by client and server
    public JsonElement serializeStartingPosition(Grid grid, String header) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive(header));
        result.add("grid", new JsonPrimitive(grid.toString()));

        return result;
    }

    public JsonElement serializeChosenMove(Grid grid, Move move) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendChosenMove"));
        result.add("grid", new JsonPrimitive(grid.toString()));
        result.add("move", new JsonPrimitive(move.toString()));

        return result;
    }

    // SERVER only Serializers

    public JsonElement serializeNextMoves(Grid grid, MoveList moves, String currentPlayerID) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendChosenMove"));
        result.add("grid", new JsonPrimitive(grid.toString()));
        result.add("moves", new JsonPrimitive(moves.toString()));
        result.add("currentPlayerID", new JsonPrimitive(currentPlayerID));

        return result;
    }

}