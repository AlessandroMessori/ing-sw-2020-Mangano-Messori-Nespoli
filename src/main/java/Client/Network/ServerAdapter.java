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

    public synchronized void requestSendDivinities(String input) {
        nextCommand = Commands.SEND_DIVINITIES;
        requestContent = input;
        notifyAll();
    }

    public synchronized void requestSendDivinity(String input) {
        nextCommand = Commands.SEND_DIVINITY;
        requestContent = input;
        notifyAll();
    }

    public synchronized void requestSendStartingPosition(String input) {
        nextCommand = Commands.SEND_STARTING_POSITION;
        requestContent = input;
        notifyAll();
    }

    public synchronized void requestSendChosenPawn(String input) {
        nextCommand = Commands.SEND_CHOSEN_PAWN;
        requestContent = input;
        notifyAll();
    }

    public synchronized void requestSendChosenMove(String input) {
        nextCommand = Commands.SEND_CHOSEN_MOVE;
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
                    doSendDivinities();
                    break;
                case SEND_DIVINITY:
                    doSendDivinity();
                    break;
                case SEND_STARTING_POSITION:
                    doSendStartingPosition();
                    break;
                case SEND_CHOSEN_PAWN:
                    doSendChosenPawn();
                    break;
                case SEND_CHOSEN_MOVE:
                    doSendChosenMove();
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

        if (responseContent.equals("The username you selected was already taken,try again with a different username")) {
            List<ServerObserver> observersCpy;
            synchronized (observers) {
                observersCpy = new ArrayList<>(observers);
            }

            /* notify the observers that we got the string */
            for (ServerObserver observer : observersCpy) {
                observer.receiveUsernameTaken(responseContent);
            }
        } else {
            String username = messageDeserializer.deserializeString(responseContent, "username");
            boolean threePlayers = messageDeserializer.deserializeBoolean(responseContent, "3players");
            String gameID = messageDeserializer.deserializeString(responseContent, "gameID");

            /* copy the list of observers in case some observers changes it from inside
             * the notification method */
            List<ServerObserver> observersCpy;
            synchronized (observers) {
                observersCpy = new ArrayList<>(observers);
            }

            /* notify the observers that we got the string */
            for (ServerObserver observer : observersCpy) {
                observer.receiveNewPlayerConnected(new Player(username, null, null), gameID);
            }
        }
    }

    private synchronized void doSendDivinities() throws IOException, ClassNotFoundException {
        System.out.println("Sending Divinities Choice to Server");
        outputStm.writeObject(requestContent);
        String responseContent = (String) inputStm.readObject();

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer : observersCpy) {
            observer.receivePossibleDivinities(responseContent);
        }
    }

    private synchronized void doSendDivinity() throws IOException, ClassNotFoundException {
        System.out.println("Sending Divinity Choice to Server");
        outputStm.writeObject(requestContent);
        String responseContent = (String) inputStm.readObject();

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers t
        System.out.println(moves. "possible moves");hat we got the string */
        for (ServerObserver observer : observersCpy) {
            observer.receiveDivinities(responseContent);
        }
    }

    private synchronized void doSendStartingPosition() throws IOException, ClassNotFoundException {
        System.out.println("Sending Starting Position Choice to Server");
        outputStm.writeObject(requestContent);
        String responseContent = (String) inputStm.readObject();

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer : observersCpy) {
            observer.receiveDivinities(responseContent);
        }
    }

    private synchronized void doSendChosenPawn() throws IOException, ClassNotFoundException {
        System.out.println("Sending Chosen Pawn to Server");
        outputStm.writeObject(requestContent);
        String responseContent = (String) inputStm.readObject();

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer : observersCpy) {
            observer.receivePawn(responseContent);
        }
    }

    private synchronized void doSendChosenMove() throws IOException, ClassNotFoundException {
        System.out.println("Sending Chosen Move to Server");
        outputStm.writeObject(requestContent);
        String responseContent = (String) inputStm.readObject();

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer : observersCpy) {
            observer.receiveMoves(responseContent);
        }
    }

    private synchronized void receiveEndGame() throws IOException, ClassNotFoundException {
        System.out.println("Sending EndGame to Server");
        outputStm.writeObject(requestContent);
        String responseContent = (String) inputStm.readObject();

        /* copy the list of observers in case some observers changes it from inside
         * the notification method */
        List<ServerObserver> observersCpy;
        synchronized (observers) {
            observersCpy = new ArrayList<>(observers);
        }

        /* notify the observers that we got the string */
        for (ServerObserver observer : observersCpy) {
            observer.receiveEndGame(responseContent);
        }
    }

    private synchronized void doCheckModel() throws IOException, ClassNotFoundException {
        outputStm.writeObject(requestContent);
        Game game;
        String responseContent = (String) inputStm.readObject();
        try {
            game = messageDeserializer.deserializeObject(responseContent, "game", Game.class);
        } catch (Exception e) {
            if (responseContent == null) {
                System.out.println("null response content");
            } else if (responseContent.equals("")) {
                System.out.println("void response content");
            } else {
                System.out.println("responseContent:");
                System.out.println(responseContent);
            }

            e.printStackTrace();

            game = null;
        }


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

