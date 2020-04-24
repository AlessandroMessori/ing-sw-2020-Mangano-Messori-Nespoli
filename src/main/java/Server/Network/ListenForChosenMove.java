package Server.Network;

import Server.Controller.ServerController;
import Server.Model.*;
import Utils.MessageDeserializer;
import com.google.gson.Gson;

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
            game.setnMoves(game.getnMoves() + 1);

            if (game.getGameTurn().getNPossibleMoves() == 0 && game.getGameTurn().getNPossibleBuildings() > 0) {
                chosenMove.setIfMove(false);
            }

            if ((chosenMove.getX() == 6 && chosenMove.getY() == 6) && (game.getGameTurn().getNPossibleMoves() == 0 && game.getGameTurn().getNPossibleBuildings() == 0)) {
                // current turn is over,starting new turn
                chosenMove.setIfMove(true);
                //selects a new player to play
                Player randomPlayer = game.getPlayers().getRandomPlayer();

                while (randomPlayer.getUsername().equals(game.getCurrentPlayer().getUsername())) {
                    randomPlayer = game.getPlayers().getRandomPlayer();
                }

                game.setCurrentPlayer(randomPlayer);
                game.setNTurns(game.getNTurns() + 1);

                //reinizialites turn data
                game.getGameTurn().startingTurn(game.getCurrentPlayer().getDivinity());
            }
            //calculates and sets the next possible moves for the player
            System.out.println(new Gson().toJson(chosenMove));


            game.setNextMoves(serverController.calculateNextMove(game.getNewGrid(), game.getCurrentPlayer(), game.getCodGame(), chosenMove, game.getGameTurn()));


            output.writeObject("Received Move");
        } catch (ClassCastException e) {
            System.out.println("error while writing the response");
        }
    }
}