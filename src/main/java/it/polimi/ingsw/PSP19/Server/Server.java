package it.polimi.ingsw.PSP19.Server;

import it.polimi.ingsw.PSP19.Server.Network.ResponseContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public final static int SOCKET_PORT = 7777;

    public static void main(String[] args) {
        ServerSocket socket;
        try {
            socket = new ServerSocket(SOCKET_PORT);
            System.out.println("Server is listening on port: " + SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("Cannot open server socket");
            System.exit(1);
            return;
        }

        while (true) {
            try {
                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                ResponseContext clientHandler = new ResponseContext(client);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
}

