package Server.Network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ListenForChosenMove extends ResponseHandler {
    private Socket client;
    private ObjectOutputStream output;

    public ListenForChosenMove(Socket cl, ObjectOutputStream out) {
        super(cl, out);
        client = cl;
        output = out;
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            output.writeObject("ListenForChosenMove Response Handler");
        } catch (ClassCastException e) {
            System.out.println("error while writing the response");
        }
    }
}
