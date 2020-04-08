package Client.Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Client.Commands;
import Server.Model.Game;
import Server.Model.Player;
import Utils.MessageDeserializer;


public class ServerAdapter implements Runnable {
    private Commands nextCommand;
    private String requestContent;
    MessageDeserializer messageDeserializer = new MessageDeserializer();

    private Socket server;
    private ObjectOutputStream outputStm;
    private ObjectInputStream inputStm;

    private List<ServerObserver> observers = new ArrayList<>();


    public ServerAdapter(Socket server) {
        this.server = server;
    }


    public void addObserver(ServerObserver observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }


    public void removeObserver(ServerObserver observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }


    public synchronized void stop() {
        nextCommand = Commands.STOP;
        notifyAll();
    }


    public synchronized void requestJoinGame(String input) {
        nextCommand = Commands.JOIN_GAME;
        requestContent = input;
        notifyAll();
    }

    public synchronized void requestCheckModel(String input) {
        nextCommand = Commands.CHECK_MODEL;
        requestContent = input;
        notifyAll();
    }


    @Override
    public void run() {
        try {
            outputStm = new ObjectOutputStream(server.getOutputStream());
            inputStm = new ObjectInputStream(server.getInputStream());
            handleServerConnection();
        } catch (IOException e) {
            System.out.println("server has died");
        } catch (ClassCastException | ClassNotFoundException e) {
            System.out.println("protocol violation");
        }

        try {
            server.close();
        } catch (IOException e) {
        }
    }


    private synchronized void handleServerConnection() throws IOException, ClassNotFoundException {
        /* wait for commands */
        while (true) {
            nextCommand = null;
            try {
                wait();
            } catch (InterruptedException e) {
            }

            if (nextCommand == null)
                continue;

            switch (nextCommand) {
                case JOIN_GAME:
                    doJoinGame();
                    break;
                case SEND_DIVINITIES:
                    //sendDivinities();
                    break;
                case SEND_DIVINITY:
                    //sendDivinity();
                    break;
                case SEND_STARTING_POSITION:
                    //sendStartingPosition();
                    break;
                case SEND_CHOSEN_MOVE:
                    //sendChosenMove();
                    break;
                case CHECK_MODEL:
                    doCheckModel();
                    break;
                case STOP:
                    return;
            }
        }
    }


    private synchronized void doJoinGame() throws IOException, ClassNotFoundException {
        outputStm.writeObject(requestContent);
        String responseContent = (String) inputStm.readObject();
        String username = messageDeserializer.deserializeString(responseContent, "username");
        boolean threePlayers = messageDeserializer.deserializeBoolean(responseContent, "3players");

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer : observersCpy) {
            observer.receiveNewPlayerConnected(new Player(username, null, null));
        }
    }

    private synchronized void doCheckModel() throws IOException, ClassNotFoundException {
        outputStm.writeObject(requestContent);
        String responseContent = (String) inputStm.readObject();
        Game game = messageDeserializer.deserializeObject(responseContent, "game", Game.class);

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer : observersCpy) {
            observer.receiveModelUpdate(game);
        }
    }
}

