package it.polimi.ingsw.PSP19.Server.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.PSP19.Server.Controller.ServerController;
import it.polimi.ingsw.PSP19.Utils.MessageDeserializer;
import it.polimi.ingsw.PSP19.Server.Model.*;
import it.polimi.ingsw.PSP19.Server.Model.*;
import it.polimi.ingsw.PSP19.Utils.MessageSerializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ListenForChosenPawn extends ResponseHandler {
    private Socket client;
    private ObjectOutputStream output;
    private MessageDeserializer messageDeserializer;
    private ServerController serverController;
    private Model model;
    private Game game;


    public ListenForChosenPawn(Socket cl, ObjectOutputStream out) {
        super(cl, out);
        client = cl;
        output = out;
        messageDeserializer = new MessageDeserializer();
        serverController = new ServerController();
        model = Model.getModel();
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            MoveList moves;
            String gameID;
            Pawn pawn;
            Move move;
            int moveX,moveY;


            System.out.println("Received Choose Pawn Request");

            gameID = messageDeserializer.deserializeString(requestContent, "gameID");
            pawn = messageDeserializer.deserializeObject(requestContent, "pawn", Pawn.class);
            move = new Move(pawn);
            moveX = messageDeserializer.deserializeObject(requestContent,"x",Integer.class);
            moveY = messageDeserializer.deserializeObject(requestContent,"y",Integer.class);
            game = Model.getModel().searchID(gameID);
            game.getCurrentPlayer().setCurrentPawn(pawn);


            boolean ifMove = game.getCurrentPlayer().getDivinity() != Divinity.PROMETHEUS || game.getGameTurn().getDecidesToComeUp();
            move.setIfMove(ifMove);
            move.setX(moveX);
            move.setY(moveY);

            game.getGameTurn().startingTurn(game.getCurrentPlayer().getDivinity());
            game.setNextMoves(serverController.calculateNextMove(game.getNewGrid(), gameID, move, game.getGameTurn()));

            System.out.println(new Gson().toJson(game.getNextMoves()));

            if (game.getNextMoves().size() <= 0) {
                Move otherPawnMove = null;

                for (int x = 0; x < 5; x++) {
                    for (int y = 0; y < 5; y++) {
                        if (game.getNewGrid().getCells(x, y).getPawn() != null) {
                            Pawn currentPawn = game.getNewGrid().getCells(x, y).getPawn();
                            if (currentPawn.getId() != pawn.getId() && currentPawn.getOwner().getUsername().equals(pawn.getOwner().getUsername())) {
                                otherPawnMove = new Move(currentPawn);
                                otherPawnMove.setIfMove(true);
                                otherPawnMove.setX(x);
                                otherPawnMove.setY(y);
                            }
                        }
                    }
                }

                game.getGameTurn().startingTurn(game.getCurrentPlayer().getDivinity());

                moves = serverController.calculateNextMove(game.getNewGrid(), gameID, otherPawnMove, game.getGameTurn());
                if (moves.size() == 0) {
                    //game is over,no possible moves

                    if (game.getPlayers().size() == 2) {
                        Player winner = game.getPlayers().getRandomPlayer();

                        while (winner.getUsername().equals(game.getCurrentPlayer().getUsername())) {
                            winner = game.getPlayers().getRandomPlayer();
                        }

                        model.searchID(gameID).setWinner(winner);
                    } else { //deletes currentPlayer and starts a new turn
                        for (int x = 0; x < 5; x++) {
                            for (int y = 0; y < 5; y++) {
                                if (game.getNewGrid().getCells(x, y).getPawn() != null) {
                                    if (game.getNewGrid().getCells(x, y).getPawn().getOwner().getUsername().equals(game.getCurrentPlayer().getUsername())) {
                                        game.getNewGrid().getCells(x, y).setPawn(null);
                                    }
                                }
                            }
                        }

                        game.getPlayers().deletePlayer(game.getCurrentPlayer());
                        game.setCurrentPlayer(game.getPlayers().getPlayer(game.getCurrentPlayerIndex()));
                        game.setNTurns(game.getNTurns() + 1);

                        //reinizialites turn data
                        game.getGameTurn().startingTurn(game.getCurrentPlayer().getDivinity());
                    }


                    game.setnMoves(game.getnMoves() + 1);
                    String message = new MessageSerializer().serializeGame(game, "You Lost").toString();
                    output.writeObject(message);
                } else {
                    //this pawn doesn't have any possible moves but the other one does
                    game.setNextMoves(moves);

                    game.setnMoves(game.getnMoves() + 1);
                    String message = new MessageSerializer().serializeGame(game, "This pawn doesn't have any possible moves,choosing the other one").toString();
                    output.writeObject(message);
                }
            } else {

                game.setnMoves(game.getnMoves() + 1);
                String message = new MessageSerializer().serializeGame(game, "Received Chosen Pawn").toString();
                output.writeObject(message);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
            System.out.println("error while writing the response");
        }
    }
}
