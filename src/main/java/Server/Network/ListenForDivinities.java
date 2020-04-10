package Server.Network;

import Server.Model.Divinity;
import Server.Model.Game;
import Server.Model.Model;
import Server.Model.Player;
import Utils.MessageDeserializer;
import Utils.MessageSerializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ListenForDivinities extends ResponseHandler {
    private Socket client;
    private ObjectOutputStream output;
    private MessageDeserializer messageDeserializer;
    private Model model;
    private Game game;


    public ListenForDivinities(Socket cl, ObjectOutputStream out) {
        super(cl, out);
        client = cl;
        output = out;
        messageDeserializer = new MessageDeserializer();
        model = Model.getModel();
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            ArrayList<Divinity> receivedDivinities;
            String gameID;

            receivedDivinities = messageDeserializer.deserializeObject(requestContent, "divinities", ArrayList.class);
            gameID = messageDeserializer.deserializeString(requestContent, "gameID");
            game = Model.getModel().searchID(gameID);

            //saves the inGameDivinities to model
            for (Divinity dv : receivedDivinities) {
                game.getInGameDivinities().addDivinity(dv);
            }

            Player randomPlayer = game.getPlayers().getRandomPlayer();

            while (!randomPlayer.getUsername().equals(game.getCurrentPlayer().getUsername())) {
                randomPlayer = game.getPlayers().getRandomPlayer();
            }

            game.setCurrentPlayer(randomPlayer);

            output.writeObject("Received Divinities");
        } catch (ClassCastException e) {
            System.out.println("error while writing the response");
        }
    }
}
