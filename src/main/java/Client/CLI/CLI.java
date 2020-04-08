package Client.CLI;

import java.nio.channels.FileLockInterruptionException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Server.Model.Divinity;
import Server.Model.Grid;
import Server.Model.Move;

import javax.print.DocFlavor;

public class CLI {
    private Grid gameGrid;
    private boolean twoOrThree;
    private ArrayList<String> chosenDivinities;

    /**
     * Print a welcome message for the user
     */
    public void printWelcome(){

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

        System.out.println("            "+" ________  ________  ________   _________  ________  ________  ___  ________   ___     \n" +
                "            "+"|\\   ____\\|\\   __  \\|\\   ___  \\|\\___   ___\\\\   __  \\|\\   __  \\|\\  \\|\\   ___  \\|\\  \\    \n" +
                "            "+"\\ \\  \\___|\\ \\  \\|\\  \\ \\  \\\\ \\  \\|___ \\  \\_\\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\ \\  \\\\ \\  \\ \\  \\   \n" +
                "            "+" \\ \\_____  \\ \\   __  \\ \\  \\\\ \\  \\   \\ \\  \\ \\ \\  \\\\\\  \\ \\   _  _\\ \\  \\ \\  \\\\ \\  \\ \\  \\  \n" +
                "            "+ "  \\|____|\\  \\ \\  \\ \\  \\ \\  \\\\ \\  \\   \\ \\  \\ \\ \\  \\\\\\  \\ \\  \\\\  \\\\ \\  \\ \\  \\\\ \\  \\ \\  \\ \n" +
                "            "+ "    ____\\_\\  \\ \\__\\ \\__\\ \\__\\\\ \\__\\   \\ \\__\\ \\ \\_______\\ \\__\\\\ _\\\\ \\__\\ \\__\\\\ \\__\\ \\__\\\n" +
                "            "+ "   |\\_________\\|__|\\|__|\\|__| \\|__|    \\|__|  \\|_______|\\|__|\\|__|\\|__|\\|__| \\|__|\\|__|\n" +
                "            "+ "   \\|_________|                                                                        \n" +
                "            "+ "                                                                                       \n" +
                "            "+ "                                                                                       \n" +
                "\n");

    }

    /**
     * Read the Username of the player
     * @return Chosen Username
     */
    public String readUsername(){
        String username;
        System.out.println("Scegli il tuo Username: ");
        Scanner input = new Scanner(System.in);
        username = input.next();
        return username;
    }
    /*
    public String readIPAddress(){
        Scanner input = new Scanner(System.in);
        return input.next();
    }
    */

    /**
     * Read if the game will be a 2-players game or a 3-players game
     * @return false if it is a 2-player game, true if it is a 3-player game
     * @throws IllegalArgumentException if the input value is not 2 or 3
     */
    public boolean readTwoOrThree() throws IllegalArgumentException{
        Scanner input = new Scanner(System.in);
        int val;
        System.out.println("Numero di giocatori: 2 o 3?");
        try{
            val = input.nextInt();
            if((val<2)||(val>3)){
                throw new IllegalArgumentException();
            } else {
                if(val == 2){
                    twoOrThree = false;
                }
                if(val == 3){
                    twoOrThree = true;
                }
            }
        }
        catch(IllegalArgumentException | InputMismatchException e){
            System.out.println("*** ERRORE ***\n Il numero di giocatori deve essere 2 o 3\n");
            readTwoOrThree();
        }
        return twoOrThree;
    }

    public void drawLobby(){

    }

    /**
     * Print the list of all divinities for the initial choice
     */
    public void printListDivinities(){
        Divinity[] divinities = Divinity.values();
        int players;
        if(!twoOrThree) {
            players = 2;
        }else{
            players = 3;
        }
        System.out.println("\nScegli " + players + " divinità per la partita:");
        for(int i = 0; i < divinities.length; i++) {
            System.out.println(i+1 + " " + divinities[i]);
        }
    }

    /**
     * Read the 2 or 3, based on how many players has the game, chosen divinities
     * @return arraylist with the chosen divinities
     */
    public ArrayList<String> readDivinitiesChoice(){
        Scanner input = new Scanner(System.in);
        Divinity[] divinities = Divinity.values();
        int val;
        int players;
        int i = 0;
        boolean alreadyIn = false;
        if(!twoOrThree) {
            players = 2;
        }else{
            players = 3;
        }
        System.out.println("\n(indicare i numeri corrispondenti alle divinità scelte)\nDivinità scelte: ");
        try {
            while(chosenDivinities.size() < players){

                val = input.nextInt();
                if(val > 9) {
                    throw new IllegalArgumentException();
                }

                for(String dv: chosenDivinities) {                                  //doppio 1 1 3 salva 1 e 3, non aggiunge 1 due volte
                    alreadyIn = divinities[val - 1].toString().equals(dv);          // però se faccio 1 g 3 rileva errore ma salva solo 1 e quindi devo rimettere 2 valori
                }                                                                   //stessa cosa se metto 1 30 3
                if(!alreadyIn){
                    chosenDivinities.add(divinities[val - 1].toString());
                } else {
                    System.out.println("+++");
                }

            }

        } catch (IllegalArgumentException | InputMismatchException e) {
            System.out.println("***");
            readDivinitiesChoice();
        }
        return chosenDivinities;
    }

    /**
     * Print the 2 or 3 divinities from which the player has to choose
     */
    public void printPossibleDivinities(){
        if(chosenDivinities.size() != 1) {
            System.out.println("\nDivinità in gioco tra cui scegliere: ");
            for (int i = 0; i < chosenDivinities.size(); i++) {
                System.out.println(i + 1 + " " + chosenDivinities.get(i));
            }
        } else {
            System.out.println("\nDivinità rimasta: ");
            System.out.println(1 + " " + chosenDivinities.get(0));
        }
    }

    /**
     * Read the divinity chose from the player
     * @return name of the divinity chose from the player
     */
    public String readChosenDivinity(){
        String chosenDivinity;
        Scanner input = new Scanner(System.in);
        if(chosenDivinities.size() != 1) {
            System.out.println("\n(indicare il numero corrispondente della divinità scelta)\nDivinità scelta: ");
            int val = input.nextInt();
            chosenDivinity = chosenDivinities.get(val - 1);
        } else {
            chosenDivinity = chosenDivinities.get(0);
        }
        chosenDivinities.remove(chosenDivinity);
        return chosenDivinity;
    }

    public void drawGrid(Grid grid){

    }

    public void /*Grid*/ readStartingPosition(){

    }

    public void printListMoves(){

    }

    public void /*Move*/ readChosenMove(){

    }

    public void drawResults(){

    }

    public CLI(){
        gameGrid = new Grid();
        chosenDivinities = new ArrayList<String>();
    }
/*
    public static void main(String[] args) {
        CLI cli = new CLI();
        ArrayList<String> divinità;
        boolean threePlayers;
        int players;
        cli.printWelcome();
        System.out.println(cli.readUsername());
        threePlayers = cli.readTwoOrThree();
        System.out.println(threePlayers);
        cli.printListDivinities();
        divinità = cli.readDivinitiesChoice();

        if(!threePlayers) {
            players = 2;
        }else{
            players = 3;
        }
        for(int i = 0; i < players; i++) {
            cli.printPossibleDivinities();
            System.out.println(cli.readChosenDivinity());
        }
    }

*/
}
