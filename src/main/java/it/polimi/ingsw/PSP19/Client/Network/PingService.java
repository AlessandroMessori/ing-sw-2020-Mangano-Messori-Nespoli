package it.polimi.ingsw.PSP19.Client.Network;

import it.polimi.ingsw.PSP19.Utils.MessageSerializer;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

public class PingService implements Runnable {

    private final String codGame;
    private final ServerAdapter serverAdapter;
    private final MessageSerializer messageSerializer;


    public PingService(String cGame, ServerAdapter sAdapter) {
        codGame = cGame;
        serverAdapter = sAdapter;
        messageSerializer = new MessageSerializer();
    }


    /**
     * periodically makes request to the server checking the model status
     */
    @Override
    public void run() {
        int updateRate = 5;

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                String message = messageSerializer.serializePing(codGame).toString();
                serverAdapter.requestPing(message);
            }
        }, 0, updateRate * 1000);
    }

}


