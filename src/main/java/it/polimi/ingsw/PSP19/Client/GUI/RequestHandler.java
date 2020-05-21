package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.Commands;

public class RequestHandler {

    static private RequestHandler Instance = null;

    private Commands currentCommand;

    private String currentRequest;

    private Client client;

    private RequestHandler() {
    }

    /**
     * returns the current type of request to make
     *
     * @return the type of request
     */
    public Commands getCurrentCommand() {
        return currentCommand;
    }

    /**
     * returns the content of the current request
     *
     * @return the content of current request
     */
    public String getCurrentRequest() {
        return currentRequest;
    }


    /**
     * makes a request to the server with the configuration taken from the parameters
     *
     * @param comm the type of request
     * @param reqContent the content of the request to make
     */
    public void updateRequest(Commands comm, String reqContent) {
        currentCommand = comm;
        currentRequest = reqContent;
        client.makeRequest();
    }

    /**
     * sets the reference to the client making the requests
     *
     * @param cl the client making the requests
     */
    public void setClient(Client cl) {
        client = cl;
    }

    /**
     * static method used to make this object a singleton
     *
     * @return the RequestHandler singleton instance
     */
    public static RequestHandler getRequestHandler() {
        if (Instance == null) {
            Instance = new RequestHandler();
        }
        return Instance;
    }

}
