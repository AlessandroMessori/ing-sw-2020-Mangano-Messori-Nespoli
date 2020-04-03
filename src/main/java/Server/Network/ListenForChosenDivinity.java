package Server.Network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ListenForChosenDivinity extends ResponseHandler {
    private Socket client;
    ObjectOutputStream output;

    public ListenForChosenDivinity(Socket cl,ObjectOutputStream out) {
        super(cl,out);
        client = cl;
        output = out;
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            output.writeObject("ListenForChosenDivinity Response Handler");
        }
        catch (ClassCastException e) {
            System.out.println("Error while writing the response");
        }
    }
}
