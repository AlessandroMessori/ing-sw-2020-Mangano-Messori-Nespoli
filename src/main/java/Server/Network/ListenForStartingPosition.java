package Server.Network;

import Server.Model.Game;
import Server.Model.Grid;
import Server.Model.Model;
import Server.Model.Player;
import Utils.CastingHelper;
import Utils.MessageDeserializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ListenForStartingPosition extends ResponseHandler {
    private Socket client;
    private ObjectOutputStream output;
    private MessageDeserializer messageDeserializer;
    private Model model;
    private Game game;


    public ListenForStartingPosition(Socket cl, ObjectOutputStream out) {
        super(cl, out);
        client = cl;
        output = out;
        messageDeserializer = new MessageDeserializer();
        model = Model.getModel();
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            Grid grid;
            String gameID;

            System.out.println("Received Send Starting Position Request");

            grid = messageDeserializer.deserializeObject(requestContent, "grid", Grid.class);
            gameID = messageDeserializer.deserializeString(requestContent, "gameID");
            game = Model.getModel().searchID(gameID);

            // add updated grid to model
            game.setOldGrid(grid);
            game.setNewGrid(grid);

            //select new current player

            output.writeObject("Received Starting Position");

        } catch (ClassCastException e) {
            System.out.println("error while writing the response");
        }
    }
}
