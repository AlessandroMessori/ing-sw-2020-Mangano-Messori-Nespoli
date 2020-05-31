package it.polimi.ingsw.PSP19.Server.Network;

import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Server.Model.Model;
import it.polimi.ingsw.PSP19.Utils.MessageDeserializer;
import it.polimi.ingsw.PSP19.Utils.MessageSerializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ListenForModelCheck extends ResponseHandler {
    private Socket client;
    private ObjectOutputStream output;
    private MessageSerializer messageSerializer;
    private MessageDeserializer messageDeserializer;
    private Model model;
    private Game game;

    public ListenForModelCheck(Socket cl, ObjectOutputStream out) {
        super(cl, out);
        client = cl;
        output = out;
        messageSerializer = new MessageSerializer();
        messageDeserializer = new MessageDeserializer();
        model = Model.getModel();
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            String response, gameID;
            gameID = messageDeserializer.deserializeString(requestContent, "gameID");
            game = model.searchID(gameID);
            response = messageSerializer.serializeGame(game,"SendGameUpdate").toString();
            output.writeObject(response);
        } catch (ClassCastException e) {
            System.out.println("error while writing the response");
        }
    }
}
