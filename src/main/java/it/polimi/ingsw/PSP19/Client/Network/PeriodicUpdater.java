package it.polimi.ingsw.PSP19.Client.Network;

import it.polimi.ingsw.PSP19.Utils.MessageSerializer;

public class PeriodicUpdater implements Runnable {

    private String gameID;
    private MessageSerializer messageSerializer;
    private ServerAdapter serverAdapter;

    public PeriodicUpdater(String gId, ServerAdapter s) {
        gameID = gId;
        messageSerializer = new MessageSerializer();
        serverAdapter = s;
    }

    // Add your task here
    public void run() {
        if (gameID != null) {
            String message = messageSerializer.serializeCheckModel(gameID).toString();
            serverAdapter.requestCheckModel(message);
        }
    }
}