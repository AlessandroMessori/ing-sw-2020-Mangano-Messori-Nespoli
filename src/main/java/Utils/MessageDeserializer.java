package Utils;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MessageDeserializer {

    private JsonParser jsonParser;
    private Gson gson;

    /**
     * extracts the value of a string from a specific field of a JSON string
     *
     * @param message the JSON String from which to extract the value
     * @param propName the field name of the value to deserialize
     *
     * @return deserialized string
     */
    public String deserializeString(String message, String propName) {
        JsonObject obj = jsonParser.parse(message).getAsJsonObject();
        return obj.get(propName).getAsString();
    }

    /**
     * extracts the value of a boolean from a specific field of a JSON string
     *
     * @param message the JSON String from which to extract the value
     * @param propName the field name of the value to deserialize
     *
     * @return deserialized boolean
     */
    public boolean deserializeBoolean(String message, String propName) {
        JsonObject obj = jsonParser.parse(message).getAsJsonObject();
        return obj.get(propName).getAsBoolean();
    }

    /**
     * extracts the value of an Object from a specific field of a JSON string
     *
     * @param message the JSON String from which to extract the value
     * @param propName the field name of the Object to deserialize
     * @param type the Object to deserialize
     * @param <T> the type of the Object to deserialize
     *
     * @return deserialized Object
     */
    public <T> T deserializeObject(String message, String propName, Class<T> type) {
        JsonObject obj = jsonParser.parse(message).getAsJsonObject();
        return gson.fromJson(obj.get(propName).getAsString(), type);
    }

    /**
     * constructor
     */
    public MessageDeserializer() {
        jsonParser = new JsonParser();
        gson = new Gson();
    }
}
