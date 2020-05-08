package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.Commands;

public class RequestHandler {

    static private RequestHandler Instance = null;

    private Commands currentCommand;

    private String currentRequest;

    private Client client;

    private RequestHandler() {
    }

    public Commands getCurrentCommand() {
        return currentCommand;
    }

    public String getCurrentRequest() {
        return currentRequest;
    }

    public void updateRequest(Commands comm, String reqContent) {
        currentCommand = comm;
        currentRequest = reqContent;
        client.makeRequest();
    }

    public void setClient(Client cl) {
        client = cl;
    }

    public static RequestHandler getRequestHandler() {
        if (Instance == null) {
            Instance = new RequestHandler();
        }
        return Instance;
    }

}
