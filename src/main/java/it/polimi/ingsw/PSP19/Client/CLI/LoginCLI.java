package it.polimi.ingsw.PSP19.Client.CLI;

import it.polimi.ingsw.PSP19.Server.Model.PlayerList;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginCLI {
    private boolean twoOrThree;
    private int oldSize;
    private boolean lobby;

    /**
     * Print a welcome message for the user
     */
    public void printWelcome() {

        System.out.println("\n" +
                "\n" +
                " ___       __   _______   ___       ________  ________  _____ ______   _______           _________  ________         \n" +
                "|\\  \\     |\\  \\|\\  ___ \\ |\\  \\     |\\   ____\\|\\   __  \\|\\   _ \\  _   \\|\\  ___ \\         |\\___   ___\\\\   __  \\        \n" +
                "\\ \\  \\    \\ \\  \\ \\   __/|\\ \\  \\    \\ \\  \\___|\\ \\  \\|\\  \\ \\  \\\\\\__\\ \\  \\ \\   __/|        \\|___ \\  \\_\\ \\  \\|\\  \\       \n" +
                " \\ \\  \\  __\\ \\  \\ \\  \\_|/_\\ \\  \\    \\ \\  \\    \\ \\  \\\\\\  \\ \\  \\\\|__| \\  \\ \\  \\_|/__           \\ \\  \\ \\ \\  \\\\\\  \\      \n" +
                "  \\ \\  \\|\\__\\_\\  \\ \\  \\_|\\ \\ \\  \\____\\ \\  \\____\\ \\  \\\\\\  \\ \\  \\    \\ \\  \\ \\  \\_|\\ \\           \\ \\  \\ \\ \\  \\\\\\  \\     \n" +
                "   \\ \\____________\\ \\_______\\ \\_______\\ \\_______\\ \\_______\\ \\__\\    \\ \\__\\ \\_______\\           \\ \\__\\ \\ \\_______\\    \n" +
                "    \\|____________|\\|_______|\\|_______|\\|_______|\\|_______|\\|__|     \\|__|\\|_______|            \\|__|  \\|_______|    \n" +
                "                                                                                                                     ");

        System.out.println("            " + " ________  ________  ________   _________  ________  ________  ___  ________   ___     \n" +
                "            " + "|\\   ____\\|\\   __  \\|\\   ___  \\|\\___   ___\\\\   __  \\|\\   __  \\|\\  \\|\\   ___  \\|\\  \\    \n" +
                "            " + "\\ \\  \\___|\\ \\  \\|\\  \\ \\  \\\\ \\  \\|___ \\  \\_\\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\ \\  \\\\ \\  \\ \\  \\   \n" +
                "            " + " \\ \\_____  \\ \\   __  \\ \\  \\\\ \\  \\   \\ \\  \\ \\ \\  \\\\\\  \\ \\   _  _\\ \\  \\ \\  \\\\ \\  \\ \\  \\  \n" +
                "            " + "  \\|____|\\  \\ \\  \\ \\  \\ \\  \\\\ \\  \\   \\ \\  \\ \\ \\  \\\\\\  \\ \\  \\\\  \\\\ \\  \\ \\  \\\\ \\  \\ \\  \\ \n" +
                "            " + "    ____\\_\\  \\ \\__\\ \\__\\ \\__\\\\ \\__\\   \\ \\__\\ \\ \\_______\\ \\__\\\\ _\\\\ \\__\\ \\__\\\\ \\__\\ \\__\\\n" +
                "            " + "   |\\_________\\|__|\\|__|\\|__| \\|__|    \\|__|  \\|_______|\\|__|\\|__|\\|__|\\|__| \\|__|\\|__|\n" +
                "            " + "   \\|_________|                                                                        \n" +
                "\n");

    }

    /**
     * Read the Username of the player
     *
     * @return Chosen Username
     */
    public String readUsername() {
        String username;
        int max = 16;
        do {
            System.out.println("Chose your username (max " + max + " chars): ");
            Scanner input = new Scanner(System.in);
            username = input.next();
            if(username.length() > max){
                System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + "Username has more than " + max + " char");
            }
        } while (username.length() > max);
        return username;
    }

    /**
     * Read if the game will be a 2-players game or a 3-players game
     *
     * @return false if it is a 2-player game, true if it is a 3-player game
     * @throws IllegalArgumentException if the input value is not 2 or 3
     */
    public boolean readTwoOrThree() throws IllegalArgumentException {
        Scanner input = new Scanner(System.in);
        int val;
        System.out.println("Number of players: 2 or 3?");
        //System.out.println("(The choice will be considered valid only if you are the first player to connect)");
        try {
            val = input.nextInt();
            if ((val < 2) || (val > 3)) {
                throw new IllegalArgumentException();

            } else {
                if (val == 2) {
                    twoOrThree = false;
                    //players = 2;
                }
                if (val == 3) {
                    twoOrThree = true;
                    //players = 3;
                }
            }
        } catch (IllegalArgumentException | InputMismatchException e) {
            System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " Number of players must be 2 or 3\n");
            readTwoOrThree();
        }
        return twoOrThree;
    }

    /**
     * Draw the Lobby
     *
     * @param inGamePlayers list of players
     * @param gameId        value of gamId of which game the player connected
     */
    public void drawLobby(PlayerList inGamePlayers, String gameId) {

        if (!lobby) {
            oldSize = inGamePlayers.size();
            System.out.println(" ____________________________________________________");
            //System.out.println("| LOBBY                  ||   GameID: " + gameId + "    |");
            System.out.println(" " + (char) 27 + "[7m" + " LOBBY                  ||   GameID: " + gameId + "     " + (char) 27 + "[0m");
            System.out.println(" ----------------------------------------------------");

            System.out.println("   Players");
            lobby = true;

            for (int i = 0; i < inGamePlayers.size(); i++) {
                System.out.println(" + " + inGamePlayers.getPlayer(i).getUsername());
            }

        } else {
            if (oldSize < inGamePlayers.size()) {
                oldSize = inGamePlayers.size();
                System.out.println(" + " + inGamePlayers.getPlayer(oldSize - 1).getUsername());
            }
        }

    }

    /**
     * Constructor
     */
    public LoginCLI() {
    }
}
