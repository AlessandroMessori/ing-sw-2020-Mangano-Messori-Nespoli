package Server.Network;

import Server.Model.Divinity;
import Server.Model.Game;
import Server.Model.Model;
import Utils.MessageDeserializer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ListenForChosenDivinity extends ResponseHandler {
    private Socket client;
    ObjectOutputStream output;
    private MessageDeserializer messageDeserializer;
    private Model model;
    private Game game;


    public ListenForChosenDivinity(Socket cl, ObjectOutputStream out) {
        super(cl, out);
        client = cl;
        output = out;
        messageDeserializer = new MessageDeserializer();
        model = Model.getModel();
    }

    @Override
    public void handleResponse(String requestContent) throws IOException {
        try {
            output.writeObject("ListenForChosenDivinity Response Handler");
            Divinity chosenDivinity = messageDeserializer.deserializeObject(requestContent, "divinity", Divinity.class);
            String username = messageDeserializer.deserializeString(requestContent, "username");
        } catch (ClassCastException e) {
            System.out.println("Error while writing the response");
        }
    }
}
