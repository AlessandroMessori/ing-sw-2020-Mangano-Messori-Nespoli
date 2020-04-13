package Server.Network;

import Server.Controller.ServerController;
import Server.Model.Divinity;
import Server.Model.Game;
import Server.Model.Model;
import Server.Model.Player;
import Utils.MessageDeserializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ListenForChosenDivinity extends ResponseHandler {
    private Socket client;
    ObjectOutputStream output;
    private MessageDeserializer messageDeserializer;
    private Model model;
    private Game game;
    private ServerController serverController;


    public ListenForChosenDivinity(Socket cl, ObjectOutputStream out) {
        super(cl, out);
        client = cl;
        output = out;
        messageDeserializer = new MessageDeserializer();
        model = Model.getModel();
        serverController = new ServerController();
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            Divinity chosenDivinity = messageDeserializer.deserializeObject(requestContent, "divinity", Divinity.class);
            String username = messageDeserializer.deserializeString(requestContent, "username");
            String gameID = messageDeserializer.deserializeString(requestContent, "gameID");

            game = Model.getModel().searchID(gameID);
            game.getInGameDivinities().deleteDivinity(chosenDivinity);
            // Add Divinity to Player
            serverController.setSpecificPlayerDiv(gameID, username, chosenDivinity);

            Player randomPlayer = game.getPlayers().getRandomPlayer();

            if (game.getInGameDivinities().size() > 0) { //sets the player to choose the divinity
                while (randomPlayer.getDivinity() != null) {
                    randomPlayer = game.getPlayers().getRandomPlayer();
                }


                game.setCurrentPlayer(randomPlayer);
            }
            output.writeObject("Received Divinity");
        } catch (ClassCastException e) {
            System.out.println("Error while writing the response");
        }
    }
}
