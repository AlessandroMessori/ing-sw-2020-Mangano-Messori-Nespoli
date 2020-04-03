package Server.Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ResponseContext implements Runnable {

    private ResponseHandler responseHandler;
    private String requestContent;
    private Socket client;


    public ResponseContext(Socket cl) {
        client = cl;
    }

    @Override
    public void run() {
        try {
            handleResponse();
        } catch (IOException e) {
            System.out.println("Client " + client.getInetAddress() + " connection dropped");
        }
    }

    private void handleResponse() throws IOException {

        System.out.println("started connection with " + client.getInetAddress());
        ObjectInputStream input = new ObjectInputStream(client.getInputStream());
        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());


        //loop to handle all the requests of the connected client
        try {
            while (true) {
                Object next = input.readObject();
                requestContent = (String) next;


                //TODO divide requestContent into Header and Body

                switch (requestContent) {
                    case "JoinGame":
                        responseHandler = new ListenForPlayer(client, output);
                        break;
                    case "SendDivinities":
                        responseHandler = new ListenForDivinities(client, output);
                        break;
                    case "SendChosenDivinity":
                        responseHandler = new ListenForChosenDivinity(client, output);
                        break;
                    case "SendStartingPosition":
                        responseHandler = new ListenForStartingPosition(client, output);
                        break;
                    case "SendChosenMove":
                        responseHandler = new ListenForChosenMove(client, output);
                        break;
                    default:
                        responseHandler = new ResponseHandler(client, output);
                        break;
                }

                System.out.println("Before Handle Response");
                responseHandler.handleResponse(requestContent);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("Invalid stream from client");
        }

        client.close();

    }

}