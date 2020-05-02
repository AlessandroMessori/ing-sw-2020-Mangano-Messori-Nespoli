package it.polimi.ingsw.PSP19.Server.Network;

import it.polimi.ingsw.PSP19.Server.Model.Model;
import it.polimi.ingsw.PSP19.Utils.MessageDeserializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ListenForDecidesToComeUp extends ResponseHandler {
    private final Socket client;
    private final ObjectOutputStream output;
    private final MessageDeserializer messageDeserializer;
    private final Model model;


    public ListenForDecidesToComeUp(Socket cl, ObjectOutputStream out) {
        super(cl, out);
        client = cl;
        output = out;
        messageDeserializer = new MessageDeserializer();
        model = Model.getModel();
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            String gameID;
            boolean canComeUp;

            System.out.println("Received Choose Decides To Come Up Request");

            gameID = messageDeserializer.deserializeString(requestContent, "gameID");
            canComeUp = messageDeserializer.deserializeBoolean(requestContent, "canComeUp");

            model.searchID(gameID).getGameTurn().setDecidesToComeUp(canComeUp);
            System.out.println("canComeUp: " + model.searchID(gameID).getGameTurn().getDecidesToComeUp());

            output.writeObject("Received Can Come Up");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error while writing the response");
        }
    }
}
