package Client.CLI;

import java.util.ArrayList;
import java.util.Scanner;

import Server.Model.Divinity;
import Server.Model.Grid;
import Server.Model.Move;

public class CLI {
    private Grid gameGrid;
    private boolean twoOrThree;
    private ArrayList<String> chosenDivinities;

    /**
     * Print a welcome message for the user
     */
    public void printWelcome(){
        System.out.println("WELCOME TO SANTORINI THE GAME");

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
        chosenDivinities = new ArrayList<>();
        int val;
        int players;
        if(!twoOrThree) {
            players = 2;
        }else{
            players = 3;
        }
        System.out.println("\n(indicare i numeri corrispondenti alle divinità scelte)\nDivinità scelte: ");
        for(int i = 0; i < players; i++) {
            val = input.nextInt();
            chosenDivinities.add(divinities[val - 1].toString());
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
/*
    public static void main(String[] args) {
        CLI cli = new CLI();
        boolean threePlayers;
        int players;
        cli.printWelcome();
        System.out.println(cli.readUsername());
        threePlayers = cli.readTwoOrThree();
        System.out.println(threePlayers);
        cli.printListDivinities();
        cli.readDivinitiesChoice();
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
