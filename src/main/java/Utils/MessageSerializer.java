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

    public JsonElement serializeJoinGame(String username,boolean threePlayers) {
        JsonObject result = new JsonObject();

        result.add("header",new JsonPrimitive("JoinGame"));
        result.add("username", new JsonPrimitive(username));
        result.add("3players", new JsonPrimitive(threePlayers));

        return result;
    }

    public JsonElement serializeDivinities(ArrayList<Divinity> divinities) {
        JsonObject result = new JsonObject();

        result.add("header",new JsonPrimitive("SendDivinities"));
        result.add("divinities", new JsonPrimitive(divinities.toString()));

        return result;
    }

    public JsonElement serializeDivinity(Divinity divinity) {
        JsonObject result = new JsonObject();

        result.add("header",new JsonPrimitive("SendChosenDivinity"));
        result.add("divinity", new JsonPrimitive(divinity.toString()));

        return result;
    }

    public JsonElement serializeStartingPosition(Grid grid) {
        JsonObject result = new JsonObject();

        result.add("header",new JsonPrimitive("SendStartingPosition"));
        result.add("grid",new JsonPrimitive(grid.toString()));

        return result;
    }

    public JsonElement serializeChosenMove(Grid grid, Move move) {
        JsonObject result = new JsonObject();

        result.add("header",new JsonPrimitive("SendChosenMove"));
        result.add("grid",new JsonPrimitive(grid.toString()));
        result.add("move",new JsonPrimitive(move.toString()));

        return result;
    }





}