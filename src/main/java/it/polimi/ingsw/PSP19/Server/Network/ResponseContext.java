package it.polimi.ingsw.PSP19.Server.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Server.Model.Model;
import it.polimi.ingsw.PSP19.Utils.MessageDeserializer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ResponseContext implements Runnable {

    private ResponseHandler responseHandler;
    private String requestContent, requestHeader, gameID;
    private Socket client;
    private MessageDeserializer messageDeserializer;
    private static Map<String, Long> connectionsStamps;


    public ResponseContext(Socket cl) {
        client = cl;
        gameID = null;
        messageDeserializer = new MessageDeserializer();
        if (ResponseContext.connectionsStamps == null) {
            ResponseContext.connectionsStamps = new HashMap<>();
        }
    }

    @Override
    public void run() {
        try {
            handleResponse();
        } catch (Exception e) {
            System.out.println("Client " + client.getInetAddress() + " connection dropped");
            if (gameID != null) {
                Game disconnectedGame = Model.getModel().searchID(gameID);
                disconnectedGame.setDisconnected();
                //Deletes Game from Model after 5 seconds
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    Model.getModel().delGame(disconnectedGame);
                                }
                                catch (Exception e) {

                                }
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

                if (requestHeader.equals("Ping")) {
                    long delay = 20000;
                    gameID = messageDeserializer.deserializeString(requestContent, "gameID");
                    long localTime = (new Date()).getTime();
                    ResponseContext.connectionsStamps.put(client.getRemoteSocketAddress().toString(), localTime);

                    new java.util.Timer().schedule(
                            new java.util.TimerTask() {
                                @Override
                                public void run() {
                                   
                                    //System.out.println(new Gson().toJson(ResponseContext.connectionsStamps));
                                    //if in the last 5 seconds there hasn't been any check model request from the client,the connection is considered lost
                                    if (localTime == ResponseContext.connectionsStamps.get(client.getRemoteSocketAddress().toString())) {
                                        Game disconnectedGame = Model.getModel().searchID(gameID);
                                        disconnectedGame.setDisconnected();
                                    }
                                }
                            },
                            delay
                    );

                    output.writeObject("Received Ping");

                } else {

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

            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("Invalid stream from client");
        }

        client.close();

    }

}
