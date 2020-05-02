package it.polimi.ingsw.PSP19.Server.Network;

import it.polimi.ingsw.PSP19.Server.Model.Divinity;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Server.Model.Model;
import it.polimi.ingsw.PSP19.Utils.CastingHelper;
import it.polimi.ingsw.PSP19.Utils.MessageDeserializer;

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
            ArrayList<String> receivedDivinitiesStr;
            ArrayList<Divinity> receivedDivinities;
            String gameID;

            System.out.println("Received Send Divinities Request");

            receivedDivinitiesStr = messageDeserializer.deserializeObject(requestContent, "divinities", ArrayList.class);
            receivedDivinities = CastingHelper.convertDivinityList(receivedDivinitiesStr);
            gameID = messageDeserializer.deserializeString(requestContent, "gameID");
            game = Model.getModel().searchID(gameID);

            //saves the inGameDivinities to model
            for (int i = 0; i < receivedDivinities.size(); i++) {
                game.getInGameDivinities().addDivinity(receivedDivinities.get(i));
                game.getPossibleDivinities().addDivinity(receivedDivinities.get(i));
            }

            System.out.print("In Game Divinities:[ ");
            for (int i = 0; i < game.getInGameDivinities().size(); i++) {
                System.out.print(game.getInGameDivinities().getDivinity(i).toString() + " ");
            }
            System.out.println("]");


            game.increaseCurrentPlayerIndex();
            game.setCurrentPlayer(game.getPlayers().getPlayer(game.getCurrentPlayerIndex()));

            output.writeObject("Received Divinities");
        } catch (ClassCastException e) {
            e.printStackTrace();
            System.out.println("error while writing the response");
        }
    }
}
