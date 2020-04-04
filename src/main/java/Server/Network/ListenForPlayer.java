package Server.Network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ListenForPlayer extends ResponseHandler {
    private Socket client;
    private ObjectOutputStream output;

    public ListenForPlayer(Socket cl,ObjectOutputStream out) {
        super(cl,out);
        client = cl;
        output = out;
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            //Player player = decodeXML(requestContent);
            //boolean nPlayers = decodeXML(requestContent);
            //Controller.addPlayerToModel(player,nPlayers);
            output.writeObject("ListenForPlayer Response Handler");
        }
        catch (ClassCastException e) {
            System.out.println("error while writing the response");
        }
    }
}
