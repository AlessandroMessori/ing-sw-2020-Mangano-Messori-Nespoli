package Server.Network;

import Server.Controller.ServerController;
import Server.Model.*;
import Utils.MessageDeserializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ListenForChosenMove extends ResponseHandler {
    private Socket client;
    ObjectOutputStream output;
    private MessageDeserializer messageDeserializer;
    private Model model;
    private ServerController serverController;

    public ListenForChosenMove(Socket cl, ObjectOutputStream out) {
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
            Move chosenMove = messageDeserializer.deserializeObject(requestContent, "move", Move.class);
            Game gameToCopy = messageDeserializer.deserializeObject(requestContent, "game", Game.class);

            Game game = model.searchID(gameToCopy.getCodGame());

            System.out.println("Received Send Chosen Move Request");

            //updates the game in Model with the data sent from the Client
            game.copyGame(gameToCopy);

            //updates the current player
            Player randomPlayer = game.getPlayers().getRandomPlayer();

            while (randomPlayer.getUsername().equals(game.getCurrentPlayer().getUsername())) {
                randomPlayer = game.getPlayers().getRandomPlayer();
            }

            game.setCurrentPlayer(randomPlayer);
            game.setNTurns(game.getNTurns() + 1);

            //calculates and sets the next possible moves for the player
            game.setNextMoves(serverController.calculateNextMove(game.getNewGrid(), game.getCurrentPlayer(), game.getCodGame(), chosenMove, game.getGameTurn()));


            output.writeObject("Received Move");
        } catch (ClassCastException e) {
            System.out.println("error while writing the response");
        }
    }
}
