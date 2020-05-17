package it.polimi.ingsw.PSP19.Client.GUI;

import it.polimi.ingsw.PSP19.Client.Network.PeriodicUpdater;
import it.polimi.ingsw.PSP19.Client.Network.ServerAdapter;

import java.time.Duration;
import java.time.Instant;

public class ModelUpdaterThread implements Runnable {

    private final String codGame;
    private final ServerAdapter serverAdapter;
    private boolean modelCheck;
    private boolean loopCheck;


    public ModelUpdaterThread(String cGame, ServerAdapter sAdapter) {
        codGame = cGame;
        serverAdapter = sAdapter;
        loopCheck = true;
        modelCheck = false;
    }

    public void setModelCheck(boolean mCheck) {
        modelCheck = mCheck;
    }

    public void stopLoop() {
        loopCheck = false;
    }

    @Override
    public void run() {
        Instant lastTime = Instant.now();
        int updateRate = 1;

        while (loopCheck) {
            // periodically fetches the updated game data from Server
            if (modelCheck && Duration.between(lastTime, Instant.now()).getSeconds() > updateRate) {
                lastTime = Instant.now();
                PeriodicUpdater checkModelUpdate = new PeriodicUpdater(codGame, serverAdapter);
                Thread checkModelUpdateThread = new Thread(checkModelUpdate);
                //System.out.println("Checking for model updates");
                checkModelUpdateThread.start();
            }
        }
    }
}
