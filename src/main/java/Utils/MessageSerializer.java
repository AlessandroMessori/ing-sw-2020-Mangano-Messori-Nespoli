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
        result.add("divinities", new JsonPrimitive(gson.toJson(divinities)));

        return result;
    }

    public JsonElement serializeDivinity(Divinity divinity) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendChosenDivinity"));
        result.add("divinity", new JsonPrimitive(gson.toJson(divinity)));

        return result;
    }

    //used by client and server
    public JsonElement serializeStartingPosition(Grid grid, String header) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive(header));
        result.add("grid", new JsonPrimitive(gson.toJson(grid)));

        return result;
    }

    public JsonElement serializeChosenMove(Grid grid, Move move) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendChosenMove"));
        result.add("grid", new JsonPrimitive(gson.toJson(grid)));
        result.add("move", new JsonPrimitive(gson.toJson(move)));

        return result;
    }

    // SERVER only Serializers

    public JsonElement serializeNextMoves(Grid grid, MoveList moves, String currentPlayerID) {
        JsonObject result = new JsonObject();

        result.add("header", new JsonPrimitive("SendChosenMove"));
        result.add("grid", new JsonPrimitive(gson.toJson(grid)));
        result.add("moves", new JsonPrimitive(gson.toJson(moves)));
        result.add("currentPlayerID", new JsonPrimitive(currentPlayerID));

        return result;
    }

    public MessageSerializer() {
        gson = new Gson();
    }

}