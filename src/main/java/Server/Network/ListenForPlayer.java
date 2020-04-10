package Server.Network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Server.Model.Game;
import Server.Model.Model;
import Server.Model.Player;
import Utils.MessageSerializer;
import Utils.MessageDeserializer;
import Server.Controller.ServerController;


public class ListenForPlayer extends ResponseHandler {
    private Socket client;
    private ObjectOutputStream output;
    private MessageSerializer messageSerializer;
    private MessageDeserializer messageDeserializer;
    private ServerController controller;

    public ListenForPlayer(Socket cl, ObjectOutputStream out) {
        super(cl, out);
        client = cl;
        output = out;
        controller = new ServerController();
        messageSerializer = new MessageSerializer();
        messageDeserializer = new MessageDeserializer();
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            String username = messageDeserializer.deserializeString(requestContent, "username");
            boolean nPlayers = messageDeserializer.deserializeBoolean(requestContent, "3players");

            Model model = Model.getModel();

            //System.out.println("Number of Games before Controller call:" + model.getGames().size());

            Player player = new Player(username, null, null);
            controller.addPlayerToModel(player, nPlayers);
            Game game = Model.getModel().searchGameByUsername(username);
            String gameID = game.getCodGame();
            String response = messageSerializer.serializeJoinGame(username, nPlayers, gameID).toString();
            int numberOfPlayers = nPlayers ? 3 : 2;

            //if the Lobby is full,sets a random player to decide the inGameDivinities
            if (game.getPlayers().size() == numberOfPlayers) {
                game.setCurrentPlayer(game.getPlayers().getRandomPlayer());
            }

            /*System.out.println("Number of Games:" + model.getGames().size());
            for (Game gm : model.getGames()) {
                System.out.print("Game " + gm.getCodGame() + ":Players : [");
                for (int i = 0; i < gm.getPlayers().size(); i++) {
                    System.out.print(gm.getPlayers().getPlayer(i).getUsername() + ",");
                }
                System.out.println("]");
            }*/


            output.writeObject(response);
        } catch (ClassCastException e) {
            System.out.println("error while writing the response");
        }
    }
}
