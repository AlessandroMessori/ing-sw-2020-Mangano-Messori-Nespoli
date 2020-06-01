package it.polimi.ingsw.PSP19.Server.Network;

import it.polimi.ingsw.PSP19.Server.Controller.ServerController;
import it.polimi.ingsw.PSP19.Utils.MessageDeserializer;
import com.google.gson.Gson;
import it.polimi.ingsw.PSP19.Server.Model.Divinity;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Server.Model.Model;
import it.polimi.ingsw.PSP19.Server.Model.Move;
import it.polimi.ingsw.PSP19.Utils.MessageSerializer;

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


            if ((chosenMove.getX() == 6 && chosenMove.getY() == 6 && (game.getCurrentPlayer().getDivinity() == Divinity.DEMETER || game.getCurrentPlayer().getDivinity() == Divinity.HEPHAESTUS))
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

                if (game.getGameTurn().getNPossibleBuildings() == 1 && game.getCurrentPlayer().getDivinity() == Divinity.HEPHAESTUS) {
                    game.getGameTurn().setLastPlacedBlock(chosenMove);
                    game.getGameTurn().setNPossibleBuildings(game.getGameTurn().getNPossibleBuildings() - 1);
                }

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


            //calculates and sets the next possible moves for the player
            System.out.println(new Gson().toJson(chosenMove));

            game.setNextMoves(serverController.calculateNextMove(game.getNewGrid(), game.getCodGame(), chosenMove, game.getGameTurn()));

            System.out.println(game.getNextMoves());

            if (game.getWinner() != null) {
                // Deletes Game After 5 Seconds
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                Model.getModel().delGame(game);
                            }
                        },
                        30000
                );
            }


            game.setnMoves(game.getnMoves() + 1);
            String message = new MessageSerializer().serializeGame(game, "Received Move").toString();
            output.writeObject(message);
        } catch (ClassCastException e) {
            System.out.println("error while writing the response");
        }
    }
}