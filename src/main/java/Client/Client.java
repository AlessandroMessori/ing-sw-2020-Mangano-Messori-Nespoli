package Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import Client.Network.ServerObserver;
import Client.Network.ServerAdapter;
import Server.Server;
import Server.Model.Game;

public class Client implements Runnable, ServerObserver
{
    /* auxiliary variable used for implementing the consumer-producer pattern*/
    private String response = null;
    private Game gameState;


    public static void main( String[] args )
    {
        /* Instantiate a new Client which will also receive events from
         * the server by implementing the ServerObserver interface */
        Client client = new Client();
        client.run();
    }


    @Override
    public void run()
    {
        /*
         * WARNING: this method executes IN THE CONTEXT OF THE MAIN THREAD
         */

        Scanner scanner = new Scanner(System.in);

        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

        /* open a connection to the server */
        Socket server;
        try {
            server = new Socket(ip, Server.SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");

        /* Create the adapter that will allow communication with the server
         * in background, and start running its thread */
        ServerAdapter serverAdapter = new ServerAdapter(server);
        serverAdapter.addObserver(this);
        Thread serverAdapterThread = new Thread(serverAdapter);
        serverAdapterThread.start();

        String str = scanner.nextLine();
        while (!"".equals(str)) {

            synchronized (this) {
                /* reset the variable that contains the next string to be consumed
                 * from the server */
                response = null;

                serverAdapter.requestConversion(str);

                // here we put the code to execute while we wait for the server response

                /* we have the response, print it */
                System.out.println(response);
            }

            str = scanner.nextLine();
        }

        serverAdapter.stop();
    }


    @Override
    public synchronized void didReceiveConvertedString(String oldStr, String newStr)
    {
        /*
         * WARNING: this method executes IN THE CONTEXT OF `serverAdapterThread`
         * because it is called from inside the `run` method of ServerAdapter!
         */

        /* Save the string and notify the main thread */
        response = newStr;
        notifyAll();
    }
}
