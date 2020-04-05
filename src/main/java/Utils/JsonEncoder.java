package Utils;


import com.google.gson.Gson;

public class JsonEncoder {

    private Gson gson;

    public void XMLEncoder() {
        gson = new Gson();

    }

    public String encodeHeader(String header) {
        return gson.toJson(header);
    }

    public String encodeBody(String body) {
        return gson.toJson(body);
    }


}
