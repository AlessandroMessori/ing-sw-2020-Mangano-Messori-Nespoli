package it.polimi.ingsw.PSP19.Server.Network;

import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Server.Model.Model;
import it.polimi.ingsw.PSP19.Utils.MessageDeserializer;
import it.polimi.ingsw.PSP19.Utils.MessageSerializer;

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
            Game game;

            System.out.println("Received Choose Decides To Come Up Request");

            gameID = messageDeserializer.deserializeString(requestContent, "gameID");
            canComeUp = messageDeserializer.deserializeBoolean(requestContent, "canComeUp");
            game = model.searchID(gameID);

            game.getGameTurn().setDecidesToComeUp(canComeUp);
            System.out.println("canComeUp: " + model.searchID(gameID).getGameTurn().getDecidesToComeUp());

            game.setnMoves(game.getnMoves() + 1);
            String message = new MessageSerializer().serializeGame(game, "Received Can Come Up").toString();
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error while writing the response");
        }
    }
}
