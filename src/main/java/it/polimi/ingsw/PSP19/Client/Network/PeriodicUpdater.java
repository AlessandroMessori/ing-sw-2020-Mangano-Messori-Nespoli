package it.polimi.ingsw.PSP19.Client.Network;

import it.polimi.ingsw.PSP19.Utils.MessageSerializer;

public class PeriodicUpdater implements Runnable {

    private String gameID;
    private MessageSerializer messageSerializer;
    private ServerAdapter serverAdapter;
    private boolean checkModel;

    public PeriodicUpdater(String gId, ServerAdapter s,boolean check) {
        gameID = gId;
        messageSerializer = new MessageSerializer();
        serverAdapter = s;
        checkModel = check;
    }

    // Add your task here
    public void run() {
        if (gameID != null) {
            if (checkModel) {
                String message = messageSerializer.serializeCheckModel(gameID).toString();
                serverAdapter.requestCheckModel(message);
            }
            else {
                String message = messageSerializer.serializePing(gameID).toString();
                serverAdapter.requestPing(message);
            }
        }
    }
}