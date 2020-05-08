package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;
import it.polimi.ingsw.PSP19.Server.Model.Game;
import it.polimi.ingsw.PSP19.Utils.MessageSerializer;

public abstract class Page {
    protected Game game;

    protected ServerAdapter serverAdapter;

    protected Client client;

    protected final MessageSerializer messageSerializer = new MessageSerializer();

    public abstract String getPageName();

    public void setGame(Game g) {
        game = g;
    }

    public void setServerAdapter(ServerAdapter sAdapter) {
        serverAdapter = sAdapter;
    }

    public void setClient(Client cl) {
        client = cl;
    }

}
