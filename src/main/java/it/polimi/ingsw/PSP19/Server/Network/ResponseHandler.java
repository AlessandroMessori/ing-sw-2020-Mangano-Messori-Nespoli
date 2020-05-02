package it.polimi.ingsw.PSP19.Server.Network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ResponseHandler {
    private Socket client;
    private ObjectOutputStream output;



    public ResponseHandler(Socket client,ObjectOutputStream output) {
        this.client = client;
        this.output = output;
    }


    public void handleResponse(String requestContent) throws IOException {
        try {
            this.output.writeObject("Basic Response Handler");
        }
        catch (ClassCastException e) {
            System.out.println("error while writing the response");
        }
    }
}
