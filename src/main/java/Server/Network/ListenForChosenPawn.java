package Server.Network;

import Server.Controller.ServerController;
import Server.Model.*;
import Utils.CastingHelper;
import Utils.MessageDeserializer;

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
            ArrayList<String> receivedDivinitiesStr;
            ArrayList<Divinity> receivedDivinities;
            String gameID;
            Pawn pawn;
            Move move;


            System.out.println("Received Choose Pawn Request");

            receivedDivinitiesStr = messageDeserializer.deserializeObject(requestContent, "divinities", ArrayList.class);
            receivedDivinities = CastingHelper.convertDivinityList(receivedDivinitiesStr);
            gameID = messageDeserializer.deserializeString(requestContent, "gameID");
            pawn = messageDeserializer.deserializeObject(requestContent, "pawn", Pawn.class);
            move = new Move(pawn);
            game = Model.getModel().searchID(gameID);
            game.getCurrentPlayer().setCurrentPawn(pawn);
            game.setnMoves(game.getnMoves() + 1);

            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    if (game.getNewGrid().getCells(x, y).getPawn() != null && game.getNewGrid().getCells(x, y).getPawn().getId() == pawn.getId()) {
                        move.setIfMove(true);
                        move.setX(x);
                        move.setY(y);
                    }
                }
            }

            game.setNextMoves(serverController.calculateNextMove(game.getNewGrid(), game.getCurrentPlayer(), gameID, move, game.getGameTurn()));

            output.writeObject("Received Chosen Pawn");
        } catch (ClassCastException e) {
            e.printStackTrace();
            System.out.println("error while writing the response");
        }
    }
}
