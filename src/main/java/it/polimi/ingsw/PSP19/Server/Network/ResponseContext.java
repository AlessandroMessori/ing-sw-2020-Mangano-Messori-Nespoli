package it.polimi.ingsw.PSP19.Server.Network;

import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Server.Model.Model;
import it.polimi.ingsw.PSP19.Utils.MessageDeserializer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ResponseContext implements Runnable {

    private ResponseHandler responseHandler;
    private String requestContent, requestHeader, gameID;
    private Socket client;
    private MessageDeserializer messageDeserializer;


    public ResponseContext(Socket cl) {
        client = cl;
        gameID = null;
        messageDeserializer = new MessageDeserializer();
    }

    @Override
    public void run() {
        try {
            handleResponse();
        } catch (IOException e) {
            System.out.println("Client " + client.getInetAddress() + " connection dropped");
            if (gameID != null) {
                Game disconnectedGame = Model.getModel().searchID(gameID);
                disconnectedGame.setDisconnected();
                //Deletes Game from Model after 5 seconds
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Model.getModel().delGame(disconnectedGame);
                            }
                        },
                        30000
                );
            }
        }
    }

    private void handleResponse() throws IOException {


        //loop to handle all the requests of the connected client
        try {
            System.out.println("started connection with " + client.getInetAddress());
            ObjectInputStream input = new ObjectInputStream(client.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            while (true) {
                Object next = input.readObject();
                requestContent = (String) next;
                requestHeader = messageDeserializer.deserializeString(requestContent, "header");

                if (requestHeader.equals("CheckModel")) {
                    gameID = messageDeserializer.deserializeString(requestContent, "gameID");
                }

                System.out.println(Model.getModel().getGames().toString());

                switch (requestHeader) {
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
                    case "SendCanComeUp":
                        responseHandler = new ListenForDecidesToComeUp(client, output);
                        break;
                    case "SendChosenPawn":
                        responseHandler = new ListenForChosenPawn(client, output);
                        break;
                    case "SendChosenMove":
                        responseHandler = new ListenForChosenMove(client, output);
                        break;
                    case "CheckModel":
                        responseHandler = new ListenForModelCheck(client, output);
                        break;
                    default:
                        responseHandler = new ResponseHandler(client, output);
                        break;
                }

                responseHandler.handleResponse(requestContent);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("Invalid stream from client");
        }

        client.close();

    }

}