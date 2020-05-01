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

            //PROMETHEUS Power
            System.out.println("Condition");
            System.out.println(game.getGameTurn().getDecidesToComeUp());
            if (game.getCurrentPlayer().getDivinity() == Divinity.PROMETHEUS && !game.getGameTurn().getDecidesToComeUp()) {

                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 5; y++) {
                        if (game.getNewGrid().getCells(x, y).getPawn() != null) {
                            if (game.getNewGrid().getCells(x, y).getPawn().getId() == game.getCurrentPlayer().getCurrentPawn().getId()) {
                                chosenMove.setX(x);
                                chosenMove.setY(y);
                                chosenMove.setToMove(game.getCurrentPlayer().getCurrentPawn());
                            }
                        }
                    }
                }

                chosenMove.setIfMove(game.getGameTurn().getNPossibleMoves() > 0);


                System.out.println("Chosen Move With Prometheus");
                System.out.println(new Gson().toJson(chosenMove));

            } else if (game.getGameTurn().getNPossibleMoves() == 0 && game.getGameTurn().getNPossibleBuildings() > 0) {
                chosenMove.setIfMove(false);

                if (chosenMove.getX() == 6 && chosenMove.getY() == 6 && game.getCurrentPlayer().getDivinity() == Divinity.ARTEMIS) {

                    for (int x = 0; x < 5; x++) {
                        for (int y = 0; y < 5; y++) {
                            if (game.getNewGrid().getCells(x, y).getPawn() != null) {
                                if (game.getNewGrid().getCells(x, y).getPawn().getId() == chosenMove.getToMove().getId()) {
                                    chosenMove.setX(x);
                                    chosenMove.setY(y);
                                }
                            }
                        }
                    }

                }

            }

            if ((chosenMove.getX() == 6 && chosenMove.getY() == 6 && game.getCurrentPlayer().getDivinity() == Divinity.DEMETER)
                    || (game.getGameTurn().getNPossibleMoves() == 0 && game.getGameTurn().getNPossibleBuildings() == 0)) {
                // current turn is over,starting new turn
                chosenMove.setIfMove(true);

                //selects a new player to play
                game.increaseCurrentPlayerIndex();
                game.setCurrentPlayer(game.getPlayers().getPlayer(game.getCurrentPlayerIndex()));
                game.setNTurns(game.getNTurns() + 1);

                //reinizialites turn data
                game.getGameTurn().startingTurn(game.getCurrentPlayer().getDivinity());
            }

            //calculates and sets the next possible moves for the player
            System.out.println(new Gson().toJson(chosenMove));

            game.setNextMoves(serverController.calculateNextMove(game.getNewGrid(), game.getCodGame(), chosenMove, game.getGameTurn()));

            output.writeObject("Received Move");
        } catch (ClassCastException e) {
            System.out.println("error while writing the response");
        }
    }
}