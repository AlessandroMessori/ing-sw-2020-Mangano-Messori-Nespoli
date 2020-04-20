package Client.CLI;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Server.Model.*;

public class CLI {
    private Grid gameGrid;
    private boolean twoOrThree;
    private ArrayList<String> chosenDivinities;
    private ArrayList<String> inGameDivinities;
    private ArrayList<String> inGameColors;
    private int players;
    private int oldSize;
    private boolean lobby;

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
                "\n");

    }

    /**
     * Read the Username of the player
     * @return Chosen Username
     */
    public String readUsername() {
        String username;
        do {
        System.out.println("Chose your username (max 16 chars): ");
        Scanner input = new Scanner(System.in);
        username = input.next();
        } while (username.length() > 16);
        return username;
    }

    /**
     * Read if the game will be a 2-players game or a 3-players game
     * @return false if it is a 2-player game, true if it is a 3-player game
     * @throws IllegalArgumentException if the input value is not 2 or 3
     */
    public boolean readTwoOrThree() throws IllegalArgumentException{
        Scanner input = new Scanner(System.in);
        int val;
        System.out.println("Number of players: 2 or 3?");
        try{
            val = input.nextInt();
            if((val<2)||(val>3)){
                throw new IllegalArgumentException();

            } else {
                if(val == 2){
                    twoOrThree = false;
                    players = 2;
                }
                if(val == 3){
                    twoOrThree = true;
                    players = 3;
                }
            }
        }
        catch(IllegalArgumentException | InputMismatchException e){
            System.out.println("*** ERROR ***\n number must be 2 or 3\n");
            readTwoOrThree();
        }
        return twoOrThree;
    }

    /**
     * Draw the Lobby
     * @param inGamePlayers list of players
     * @param gameId value of gamId of which game the player connected
     */
    public void drawLobby(PlayerList inGamePlayers, String gameId){

        if(!lobby) {
            oldSize = 1;
            System.out.println(" ____________________________________________________");
            //System.out.println("| LOBBY\t\t\t\t\t\t||\tGameID: " + gameId + "\t |");
            System.out.println(" " + (char) 27 + "[7m"+" LOBBY\t\t\t\t\t\t||\tGameID: " + gameId + "\t " + (char) 27 +"[0m");
            System.out.println(" ----------------------------------------------------");

            System.out.println("\tPlayers");
            //System.out.println((char) 27 + "[1m\tPlayers" + (char) 27 + "[0m");
            lobby = true;
            System.out.println(" + " + inGamePlayers.getPlayer(0).getUsername());
        } else {
            if(oldSize < inGamePlayers.size()) {
                oldSize = inGamePlayers.size();
                System.out.println(" + " + inGamePlayers.getPlayer(oldSize-1).getUsername());
            }
        }

    }

    /**
     * Print the list of possible colors for the player and let him chose one
     * @return color chosen
     */
    public Colour choseColor(){

        Colour[] colors = Colour.values();
        Colour color = null;
        Scanner input = new Scanner(System.in);
        int val;
        StringColor[] colorShowed = StringColor.values();
        String word;
        boolean alreadyIn = false;
        boolean chosen;
        boolean playerChoice = false;

        System.out.println("\nIn-game Colors to choose from (if marked, it has already been chosen): ");
        for(int i = 0; i < colors.length; i++){

            chosen = false;
            for(String inGameColor : inGameColors){
                if (inGameColor.equals(colors[i].toString())) {
                    chosen = true;
                }
            }
            if(!chosen){
                System.out.println((i + 1) + " " + colorShowed[i] + colors[i].toString() + StringColor.RESET);
            }else{
                System.out.println((char) 27 + "[9m" + (i + 1) + " "  + colors[i].toString() + (char) 27 + "[0m");
            }
        }

        while(!playerChoice) {
            System.out.println("\n(indicate the numbers corresponding to the chosen color)\nChosen color: ");

            word = input.next();
            if (word.matches("^-?\\d+$")) {
                val = Integer.parseInt(word);
                if ((val > 0) && (val < (colors.length + 1))) {
                    for (String col : inGameColors) {
                        alreadyIn = colors[val - 1].toString().equals(col);
                    }
                    if (!alreadyIn) {
                        inGameColors.add(colors[val - 1].toString());
                        color = colors[val - 1];
                        System.out.println("+++ Chosen " + colorShowed[val-1] + colors[val - 1].toString() + StringColor.RESET + " +++");
                        playerChoice = true;
                    } else {
                        System.out.println("Color: " + val + " " + colorShowed[val-1] + colors[val - 1].toString() + StringColor.RESET +" is already chosen.");
                    }
                } else {
                    System.out.println("\"" + val + "\"" + " is not a valid input, input must be a number between 1 and 9. Retry ");
                }
            } else {
                System.out.println("\"" + word + "\"" + " is not a valid input, input must be a number between 1 and 9. Retry");
            }
        }
        return color;

    }

    /**
     * Print the list of all divinities for the initial choice
     */
    public void printListDivinities(){
        Divinity[] divinities = Divinity.values();
        System.out.println("\nChose " + players + " divinities for the game:");
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
        String word;
        String reset;
        boolean alreadyIn = false;
        System.out.println("\n(indicate the numbers corresponding to the chosen divinities)\nChosen divinities: ");
        while(chosenDivinities.size() < players){
            word = input.next();
            if (word.matches("^-?\\d+$")) {
                val = Integer.parseInt(word);
                if ((val > 0) && (val < 10)) {
                    for (String dv : chosenDivinities) {
                        alreadyIn = divinities[val - 1].toString().equals(dv);
                    }
                    if (!alreadyIn) {
                        chosenDivinities.add(divinities[val - 1].toString());
                        System.out.println("+++ Added " + divinities[val-1].toString() + " +++");
                    } else {
                        System.out.println("Divinity: " + val + " " + divinities[val-1].toString() + " is already chosen.");
                    }
                } else {
                    System.out.println("\"" + val + "\""+ " is not a valid input, input must be a number between 1 and 9. Retry ");
                }
            } else {
                System.out.println("\"" + word + "\"" + " is not a valid input, input must be a number between 1 and 9. Retry");
            }
        }
        System.out.println("\nChosen divinities");
        System.out.println(chosenDivinities);
        System.out.println("\nConfirm the selection?\n y: yes        n: no");
        reset = input.next();
        while(!(reset.equals("y")) && !(reset.equals("n"))){
            System.out.println("\nChosen divinities");
            System.out.println(chosenDivinities);
            System.out.println("Confirm the selection?\n y: yes        n: no");
            reset = input.next();
        }
        if(reset.equals("n")){
            resetDivinities();
            printListDivinities();
            readDivinitiesChoice();
        }

        return chosenDivinities;
    }

    /**
     * reset the chosen divinities for the game
     */
    private void resetDivinities(){
        chosenDivinities = new ArrayList<>();
    }

    /**
     * Print the 2 or 3 divinities from which the player has to choose
     */
    public void printPossibleDivinities(){
        boolean chosen;
        System.out.println("\nIn-game divinities to choose from (if marked, it has already been chosen): ");
        for (int i = 0; i < chosenDivinities.size(); i++) {
            chosen = false;
            for (String inGameDivinity : inGameDivinities) {

                if (chosenDivinities.get(i).equals(inGameDivinity)) {
                    chosen = true;
                }
            }
            if(!chosen){
                System.out.println((i + 1) + " " + chosenDivinities.get(i));
            }else{
                System.out.println((char) 27 + "[9m" + (i + 1) + " " + chosenDivinities.get(i) + (char) 27 + "[0m");
            }
        }
    }

    /**
     * Read the divinity chose from the player
     * @return name of the divinity chose from the player
     */
    public String readChosenDivinity(){
        String plDivinity = null;
        Scanner input = new Scanner(System.in);
        String word;
        boolean chosen = false;
        boolean in = false;
        int val;
        int diff = chosenDivinities.size() - inGameDivinities.size();

        while(!chosen){
            if(diff != 1) {
                System.out.println("\n(indicate the number corresponding to the chosen divinity)\nChosen divinity: ");
                word = input.next();
                if (word.matches("^-?\\d+$")) {
                    val = Integer.parseInt(word);
                    if((val > 0) && (val < chosenDivinities.size()+1)) {
                        for (String inGameDivinity : inGameDivinities) {
                            in = false;
                            if (chosenDivinities.get(val-1).equals(inGameDivinity)) {
                                in = true;
                            }
                        }
                        if(!in){
                            plDivinity = chosenDivinities.get(val - 1);
                            chosen = true;
                        }else{
                            System.out.println("Divinity: " + val + " " + chosenDivinities.get(val-1) + " is already chosen.");
                        }

                    }else{
                        System.out.println("\"" + val + "\"" + " is not a valid input, input must be a number between 1 and " + chosenDivinities.size() + ". Retry");
                    }
                } else {
                    System.out.println("\"" + word + "\"" + " is not a valid input, input must be a number between 1 and " + chosenDivinities.size() + ". Retry");
                }
            } else {
                for (int j = 0; j < chosenDivinities.size(); j++) {
                    for (int t = 0; t < inGameDivinities.size(); t++) {
                        in = false;
                        if (inGameDivinities.get(t).equals(chosenDivinities.get(j))) {
                            in = true;
                        }
                    }
                    if(!in){
                        plDivinity = chosenDivinities.get(j);
                        chosen = true;
                    }
                }
                System.out.println("\nRemaining divinity: " + plDivinity);
            }
        }
        inGameDivinities.add(plDivinity);
        return plDivinity;
    }

    /**
     * Print the grid of the game
     * @param grid grid to print
     */
    public void drawGrid(Grid grid){
        StringBuilder rowOne = new StringBuilder();
        StringBuilder rowTwo = new StringBuilder();
        String top = "\t _______________________________________";
        String mid = "__\t|_______|_______|_______|_______|_______|"; //"––\t|–––––––|–––––––|–––––––|–––––––|–––––––|";
        String bot = "__\t|\t\t|\t\t|\t\t|\t\t|\t\t|" + "\n" +
                "\t ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾"; //"\t ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾";

        System.out.println("\\ Y |   1   |   2   |   3   |   4   |   5   |");
        System.out.println("X \\" + top);

        for(int x = 0; x < 5; x++){

            for(int y = 0; y < 5; y++) {
                if(y == 0) {
                    rowOne.append("\t|");
                    rowTwo.append( (x+1) + "\t|");
                }else{
                    rowOne.append("|");
                    rowTwo.append("|");
                }
                if(grid.getCells(x,y).getPawn() != null){
                    if(grid.getCells(x,y).getPawn().getOwner().getColour() == Colour.BLUE) {
                        rowOne.append(StringColor.ANSI_BLUE + " ♙ \t" + StringColor.RESET);
                    }
                    if(grid.getCells(x,y).getPawn().getOwner().getColour() == Colour.RED) {
                        rowOne.append(StringColor.ANSI_RED + " ♙ \t" + StringColor.RESET);
                    }
                    if(grid.getCells(x,y).getPawn().getOwner().getColour() == Colour.GREEN) {
                        rowOne.append(StringColor.ANSI_GREEN + " ♙ \t" + StringColor.RESET);
                    }
                    if(grid.getCells(x,y).getPawn().getOwner().getColour() == Colour.YELLOW) {
                        rowOne.append(StringColor.ANSI_YELLOW + " ♙ \t" + StringColor.RESET);
                    }
                    if(grid.getCells(x,y).getPawn().getOwner().getColour() == Colour.WHITE) {
                        rowOne.append(StringColor.ANSI_WHITE + " ♙ \t" + StringColor.RESET);
                    }
                    if(grid.getCells(x,y).getPawn().getOwner().getColour() == Colour.PINK) {
                        rowOne.append(StringColor.ANSI_PINK + " ♙ \t" + StringColor.RESET);
                    }
                }else{
                    rowOne.append("\t\t");
                }
                if(grid.getCells(x,y).getTower().getLevel() != 0){
                    int lvl = grid.getCells(x,y).getTower().getLevel();
                    if(lvl == 4){
                        rowTwo.append("\tX");
                    }else {
                        rowTwo.append("\tT" + lvl + "\t");
                    }
                }else{
                    rowTwo.append("\t\t");
                }
                if(y == 4) {
                    rowOne.append("|");
                    rowTwo.append("|");
                }

            }
            System.out.println(rowOne);
            System.out.println(rowTwo);
            if(x != 4) {
                System.out.println(mid);
            }
            rowOne.delete(0, rowOne.length());
            rowTwo.delete(0, rowTwo.length());

        }

    System.out.println(bot);

    }

    /**
     * Read the starting position for player's pawns
     * @param choosingPlayer player that has to chose the starting positions
     * @return an updated grid of the game with the player's pawns
     */
    public Grid readStartingPosition(Player choosingPlayer){
        Scanner input = new Scanner(System.in);
        StringColor color = null;
        boolean positionChosen;
        boolean validPosition;
        Pawn newPawn;
        int valx = 0;
        int valy = 0;

        System.out.println("Chose the starting position for your workers");
        System.out.println("Write coordinates X,Y \n");

        if(choosingPlayer.getColour() == Colour.BLUE) color = StringColor.ANSI_BLUE;
        if(choosingPlayer.getColour() == Colour.RED) color = StringColor.ANSI_RED;
        if(choosingPlayer.getColour() == Colour.GREEN) color = StringColor.ANSI_GREEN;
        if(choosingPlayer.getColour() == Colour.YELLOW) color = StringColor.ANSI_YELLOW;
        if(choosingPlayer.getColour() == Colour.WHITE) color = StringColor.ANSI_WHITE;
        if(choosingPlayer.getColour() == Colour.PINK) color = StringColor.ANSI_PINK;

        for(int j = 0; j < 2; j++) {
            positionChosen = false;
            validPosition = false;
            drawGrid(gameGrid);
            while (!positionChosen) {

                System.out.println("\nPosition of "+ color +"Worker " + (j + 1) + StringColor.RESET);
                System.out.println("X Y");
                String row = input.nextLine();
                String[] words = row.split(" ");
                if(words.length < 2){
                    System.out.println("You must give TWO values in input");
                }else {

                    if ((words[0].matches("^-?\\d+$")) && (Integer.parseInt(words[0]) > 0) && (Integer.parseInt(words[0]) < 6)) {
                        valx = Integer.parseInt(words[0]);
                    } else {
                        System.out.println("The value must be a number between 1 and 5 ");
                    }
                    if ((words[1].matches("^-?\\d+$")) && (Integer.parseInt(words[1]) > 0) && (Integer.parseInt(words[1]) < 6)) {
                        valy = Integer.parseInt(words[1]);
                    } else {
                        System.out.println("The value must be a number between 1 and 5 ");
                    }
                    if ((valx > 0) && (valy > 0)) {
                        if (gameGrid.getCells(valx - 1, valy - 1).getPawn() == null) {
                            validPosition = true;
                        } else {
                            System.out.println("(" + valx + "," + valy + ") is not a valid position\n The position is already occupied");
                        }
                    }
                    if (validPosition) {
                        positionChosen = true;
                    }
                }
            }

            newPawn = new Pawn(choosingPlayer);
            gameGrid.getCells(valx-1,valy-1).setPawn(newPawn);


        }

        return gameGrid;
    }

    public void printListMoves(MoveList possibleMoves){

    }

    public void /*Move*/ readChosenMove(){

    }

    public void drawResults(){

    }

    public void setChosenDivinities(ArrayList<String> chosenDivinities) {
        this.chosenDivinities = chosenDivinities;
    }

    /**
     * Constructor
     */
    public CLI(){
        gameGrid = new Grid();
        chosenDivinities = new ArrayList<String>();
        inGameDivinities = new ArrayList<String>();
        inGameColors = new ArrayList<String>();
        lobby = false;
    }

/*
    public static void main(String[] args) {



        CLI cli = new CLI();
        for(int i = 0; i < 3; i++) cli.choseColor();

        Player p = new Player("dad",Divinity.ATHENA,Colour.YELLOW);

        cli.gameGrid.getCells(2,3).setTower(new Tower(2,false));
        cli.drawGrid(cli.readStartingPosition(p));

        cli.printWelcome();
        cli.readUsername();
        cli.readTwoOrThree();
        cli.printListDivinities();
        cli.readDivinitiesChoice();

        for(int i = 0; i < cli.players; i++) {
            cli.printPossibleDivinities();
            System.out.println(cli.readChosenDivinity());
        }


    }
*/

}
