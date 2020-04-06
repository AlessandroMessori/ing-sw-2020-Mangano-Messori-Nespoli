package Utils;

import Server.Model.Divinity;
import Server.Model.Grid;
import Server.Model.Move;
import Server.Model.MoveList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MessageDeserializer {

    private JsonParser jsonParser;
    private Gson gson;

    public String deserializeString(String message, String propName) {
        JsonObject obj = jsonParser.parse(message).getAsJsonObject();
        return obj.get(propName).getAsString();
    }

    public boolean deserializeBoolean(String message, String propName) {
        JsonObject obj = jsonParser.parse(message).getAsJsonObject();
        return obj.get(propName).getAsBoolean();
    }

    public <T> T deserializeObject(String message, String propName, Class<T> type) {
        JsonObject obj = jsonParser.parse(message).getAsJsonObject();
        return gson.fromJson(obj.get(propName).getAsString(), type);
    }

    public MessageDeserializer() {
        jsonParser = new JsonParser();
        gson = new Gson();
    }
}
