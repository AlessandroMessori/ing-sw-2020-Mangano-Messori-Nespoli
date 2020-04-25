package Client.CLI;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Server.Model.*;

public class CLI {
    private boolean twoOrThree;
    private ArrayList<String> chosenDivinities;
    private int players;
    private int oldSize;
    private boolean lobby;

    /**
     * sets player value
     * @param n player value
     */
    public void setPlayers(int n){
        players = n;
    }

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
        try {
            val = input.nextInt();
            if ((val < 2) || (val > 3)) {
                throw new IllegalArgumentException();

            } else {
                if (val == 2) {
                    twoOrThree = false;
                    players = 2;
                }
                if (val == 3) {
                    twoOrThree = true;
                    players = 3;
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
     * Print the list of possible colors for the player and let him chose one
     *
     * @param inGameColors already chosen colors
     * @return color chosen
     */
    public Colour choseColor(ArrayList<String> inGameColors) {

        //print colors

        Colour[] colors = Colour.values();
        StringColor[] colorShowed = StringColor.values();
        boolean chosen;

        System.out.println("\nIn-game Colors to choose from (if marked, it has already been chosen): ");
        for (int i = 0; i < colors.length; i++) {

            chosen = false;
            for (String inGameColor : inGameColors) {
                if (colors[i].toString().equals(inGameColor)) {
                    chosen = true;
                }
            }
            if (!chosen) {
                System.out.println("   " + (i + 1) + ". " + colorShowed[i] + colors[i].toString() + StringColor.RESET);
            } else {
                System.out.println("  " + (char) 27 + "[9m" + " " + (i + 1) + ". " + colors[i].toString() + " " + (char) 27 + "[0m");
            }
        }

        //choice of color

        Colour color = null;
        Scanner input = new Scanner(System.in);
        int val;

        String word;
        boolean alreadyIn;
        boolean playerChoice = false;
        while (!playerChoice) {
            System.out.println("\n(indicate the numbers corresponding to the chosen color)\nChosen color: ");

            word = input.next();
            if (word.matches("^-?\\d+$")) {
                val = Integer.parseInt(word);
                if ((val > 0) && (val < (colors.length + 1))) {
                    alreadyIn = false;
                    for (String col : inGameColors) {

                        if (colors[val - 1].toString().equals(col)) {
                            alreadyIn = true;
                        }
                    }
                    if (!alreadyIn) {
                        color = colors[val - 1];
                        System.out.println(" You chose " + colorShowed[val - 1] + color.toString() + StringColor.RESET);
                        playerChoice = true;
                    } else {
                        System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " Color: " + val + ". " + colorShowed[val - 1] + colors[val - 1].toString() + StringColor.RESET + " is already chosen.");
                    }
                } else {
                    System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " \"" + val + "\"" + " is not a valid input, input must be a number between 1 and 9. Retry ");
                }
            } else {
                System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " \"" + word + "\"" + " is not a valid input, input must be a number between 1 and 9. Retry");
            }
        }
        inGameColors.add(color.toString());
        return color;
    }

    /**
     * Print the list of all divinities for the initial choice
     */
    public void printListDivinities() {
        Divinity[] divinities = Divinity.values();
        System.out.println("\nChose " + players + " divinities for the game:");
        for (int i = 0; i < divinities.length; i++) {
            System.out.println("   " + (i + 1) + ". " + divinities[i]);
        }
    }

    /**
     * Read the 2 or 3, based on how many players has the game, chosen divinities
     *
     * @return Arraylist with the chosen divinities
     */
    public ArrayList<String> readDivinitiesChoice() {
        Scanner input = new Scanner(System.in);
        Divinity[] divinities = Divinity.values();
        int val;
        String word;
        String reset;
        boolean alreadyIn = false;
        System.out.println("\n(indicate the numbers corresponding to the chosen divinities)\nChosen divinities: ");
        while (chosenDivinities.size() < players) {
            word = input.next();
            if (word.matches("^-?\\d+$")) {
                val = Integer.parseInt(word);
                if ((val > 0) && (val < 10)) {
                    for (String dv : chosenDivinities) {
                        alreadyIn = divinities[val - 1].toString().equals(dv);
                    }
                    if (!alreadyIn) {
                        chosenDivinities.add(divinities[val - 1].toString());
                        System.out.println(" You chose: " + val + ". " + divinities[val - 1].toString());
                    } else {
                        System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " Divinity: " + val + ". " + divinities[val - 1].toString() + " is already chosen.");
                    }
                } else {
                    System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " \"" + val + "\"" + " is not a valid input, input must be a number between 1 and 9. Retry ");
                }
            } else {
                System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " \"" + word + "\"" + " is not a valid input, input must be a number between 1 and 9. Retry");
            }
        }

        do {
            System.out.println("\nChosen divinities");
            System.out.println(chosenDivinities);
            System.out.println("Confirm the selection?\n y: yes        n: no");
            reset = input.next();
        } while (!(reset.equals("y")) && !(reset.equals("n")));

        if (reset.equals("n")) {
            resetDivinities();
            printListDivinities();
            readDivinitiesChoice();
        }

        return chosenDivinities;
    }

    /**
     * reset the chosen divinities for the game
     */
    private void resetDivinities() {
        chosenDivinities = new ArrayList<>();
    }

    /**
     * Print the 2 or 3 divinities from which the player has to choose
     *
     * @param playableDivinities ArrayList of playable divinities for the current game
     * @param inGameDivinities   ArrayList of divinities you can chose from, not already chosen
     */
    public void printPossibleDivinities(ArrayList<String> playableDivinities, ArrayList<String> inGameDivinities) {
        boolean chosen;
        System.out.println("\nIn-game divinities to choose from (if marked, it has already been chosen): ");
        for (int i = 0; i < playableDivinities.size(); i++) {
            chosen = false;
            for (String inGameDivinity : inGameDivinities) {

                if (playableDivinities.get(i).equals(inGameDivinity)) {
                    chosen = true;
                    break;
                }
            }
            if (!chosen) {
                System.out.println("  " + (char) 27 + "[9m" + " " + (i + 1) + ". " + playableDivinities.get(i) + " " + (char) 27 + "[0m");
            } else {
                System.out.println("   " + (i + 1) + ". " + playableDivinities.get(i));
            }
        }
    }

    /**
     * Read the divinity chose from the player
     *
     * @param playableDivinities ArrayList of playable divinities for the current game
     * @param inGameDivinities   ArrayList of divinities you can chose from, not already chosen
     * @return name of the divinity chose from the player
     */
    public String readChosenDivinity(ArrayList<String> playableDivinities, ArrayList<String> inGameDivinities) {
        String plDivinity = null;
        Scanner input = new Scanner(System.in);
        String word;
        boolean chosen = false;
        boolean in;
        int val;
        int diff = inGameDivinities.size();

        while (!chosen) {
            if (diff != 1) {
                System.out.println("\n(indicate the number corresponding to the chosen divinity)\nChosen divinity: ");
                word = input.next();
                if (word.matches("^-?\\d+$")) {
                    val = Integer.parseInt(word);
                    if ((val > 0) && (val < playableDivinities.size() + 1)) {
                        in = false;
                        for (String inGameDivinity : inGameDivinities) {

                            if (playableDivinities.get(val - 1).equals(inGameDivinity)) {
                                in = true;
                                break;
                            }
                        }
                        if (!in) {
                            System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " Divinity: " + val + ". " + playableDivinities.get(val - 1) + " is already chosen.");
                        } else {
                            plDivinity = playableDivinities.get(val - 1);
                            System.out.println(" You chose: " + plDivinity);
                            chosen = true;
                        }

                    } else {
                        System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " \"" + val + "\"" + " is not a valid input, input must be a number between 1 and " + playableDivinities.size() + ". Retry");
                    }
                } else {
                    System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + "\"" + word + "\"" + " is not a valid input, input must be a number between 1 and " + playableDivinities.size() + ". Retry");
                }
            } else {
                plDivinity = inGameDivinities.get(0);
                chosen = true;
                System.out.println("\nRemaining divinity: " + plDivinity);
            }
        }
        //inGameDivinities.add(plDivinity);
        return plDivinity;
    }

    /**
     * Print the grid of the game
     *
     * @param grid grid to print
     */
    public void drawGrid(Grid grid) {
        StringBuilder rowOne = new StringBuilder();
        StringBuilder rowTwo = new StringBuilder();
        StringColor color;
        String top = "  _______________________________________";
        String mid = "__  |_______|_______|_______|_______|_______|";
        String bot = "__  |       |       |       |       |       |" + "\n" +
                "     ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾";

        System.out.println("\\ Y |   1   |   2   |   3   |   4   |   5   |");
        System.out.println("X \\" + top);

        for (int x = 0; x < 5; x++) {

            for (int y = 0; y < 5; y++) {
                if (y == 0) {
                    rowOne.append("    |");
                    rowTwo.append((x + 1) + "   |");
                } else {
                    rowOne.append("|");
                    rowTwo.append("|");
                }
                if (grid.getCells(x, y).getPawn() != null) {
                    color = colorString(grid.getCells(x, y).getPawn().getOwner(), false);
                    rowOne.append(color + " W");
                    if (grid.getCells(x, y).getPawn().getId() % 2 == 1) {
                        rowOne.append("1");
                    } else {
                        rowOne.append("2");
                    }
                    rowOne.append("    " + StringColor.RESET);
                } else {
                    rowOne.append("       ");
                }
                if (grid.getCells(x, y).getTower().getLevel() != 0) {
                    int lvl = grid.getCells(x, y).getTower().getLevel();
                    if ((lvl == 4)||(grid.getCells(x, y).getTower().getIsDome())) {
                        rowTwo.append("    X  ");
                    } else {
                        rowTwo.append("    T" + lvl + " ");
                    }
                } else {
                    rowTwo.append("       ");
                }
                if (y == 4) {
                    rowOne.append("|");
                    rowTwo.append("|");
                }

            }
            System.out.println(rowOne);
            System.out.println(rowTwo);
            if (x != 4) {
                System.out.println(mid);
            }
            rowOne.delete(0, rowOne.length());
            rowTwo.delete(0, rowTwo.length());

        }

        System.out.println(bot);

    }

    /**
     * Print the list of players in game
     *
     * @param inGamePlayers list of players in game
     */
    public void drawPlayers(PlayerList inGamePlayers) {
        StringBuilder list = new StringBuilder();
        StringColor color;
        list.append("\nPlayers:\n");
        for (int i = 0; i < inGamePlayers.size(); i++) {
            color = colorString(inGamePlayers.getPlayer(i), false);
            list.append("   " + color + inGamePlayers.getPlayer(i).getUsername() + StringColor.RESET + "  Divinity: " + inGamePlayers.getPlayer(i).getDivinity().toString() + "\n");
        }
        System.out.println(list);
    }

    /**
     * Extract the color of the player to print with
     *
     * @param pl player you need to extract the color
     * @param background false if you need only the color for chars, true if you want background color
     * @return the color of the player
     */
    private StringColor colorString(Player pl, boolean background) {
        StringColor color = null;
        if(!background) {
            if (pl.getColour() == Colour.BLUE) color = StringColor.ANSI_BLUE;
            if (pl.getColour() == Colour.RED) color = StringColor.ANSI_RED;
            if (pl.getColour() == Colour.GREEN) color = StringColor.ANSI_GREEN;
            if (pl.getColour() == Colour.YELLOW) color = StringColor.ANSI_YELLOW;
            if (pl.getColour() == Colour.WHITE) color = StringColor.ANSI_WHITE;
            if (pl.getColour() == Colour.PINK) color = StringColor.ANSI_PINK;
        }else{
            if (pl.getColour() == Colour.BLUE) color = StringColor.BACKGROUND_BLUE;
            if (pl.getColour() == Colour.RED) color = StringColor.BACKGROUND_RED;
            if (pl.getColour() == Colour.GREEN) color = StringColor.BACKGROUND_GREEN;
            if (pl.getColour() == Colour.YELLOW) color = StringColor.BACKGROUND_YELLOW;
            if (pl.getColour() == Colour.WHITE) color = StringColor.BACKGROUND_WHITE;
            if (pl.getColour() == Colour.PINK) color = StringColor.BACKGROUND_PINK;
        }
        return color;
    }

    /**
     * Read the starting position for player's pawns
     *
     * @param choosingPlayer player that has to chose the starting positions
     * @param gameGrid       grid of the game
     * @return an updated grid of the game with the player's pawns
     */
    public Grid readStartingPosition(Player choosingPlayer, Grid gameGrid) {
        Scanner input = new Scanner(System.in);
        StringColor color;
        boolean positionChosen;
        boolean validPosition;
        boolean idNotValid;
        Pawn newPawn;
        int valx;
        int valy;
        int max = (int) Math.pow(10, 5);
        int min = (int) Math.pow(10, 4);
        int randInt;
        ArrayList<Integer> takenPawnId = new ArrayList<Integer>();

        System.out.println("Chose the starting position for your workers");
        System.out.println("Write coordinates X,Y \n");
        color = colorString(choosingPlayer, false);
        for (int j = 0; j < 2; j++) {
            positionChosen = false;
            validPosition = false;
            valx = 0;
            valy = 0;
            drawGrid(gameGrid);
            while (!positionChosen) {
 
                System.out.println("\nPosition of " + color + "Worker " + (j + 1) + StringColor.RESET);
                System.out.println("X Y");
                String row = input.nextLine();
                String[] words = row.split(" ");
                if (words.length < 2) {
                    System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " You must give TWO values in input");
                } else {

                    if ((words[0].matches("^-?\\d+$")) && (Integer.parseInt(words[0]) > 0) && (Integer.parseInt(words[0]) < 6)) {
                        valx = Integer.parseInt(words[0]);
                        if ((words[1].matches("^-?\\d+$")) && (Integer.parseInt(words[1]) > 0) && (Integer.parseInt(words[1]) < 6)) {
                            valy = Integer.parseInt(words[1]);

                            if (gameGrid.getCells(valx - 1, valy - 1).getPawn() == null) {
                                validPosition = true;
                            } else {
                                System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " (" + valx + "," + valy + ") is not a valid position\n The position is already occupied");
                            }

                        } else {
                            System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " Input must be numbers between 1 and 5 ");
                        }
                    } else {
                        System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " Input must be numbers between 1 and 5 ");
                    }
                    if (validPosition) {
                        positionChosen = true;
                    }
                }
            }

            for (int x = 0; x < 5; x++) {
                for (int y = 0; y < 5; y++) {
                    if (gameGrid.getCells(x, y).getPawn() != null) {
                        takenPawnId.add(gameGrid.getCells(x,y).getPawn().getId());
                    }
                }
            }

            newPawn = new Pawn(choosingPlayer);
            if (j == 0) {
                //first pawn have an odd id
                do {
                    randInt = (int) (Math.random() * (max - min + 1) + min);
                    idNotValid = false;
                    for(int idInExam : takenPawnId){
                        if(idInExam == randInt){
                            idNotValid = true;
                            break;
                        }
                    }
                } while ((randInt % 2 != 1)&&(!idNotValid));
            } else {
                //second pawn have an even id
                do {
                    randInt = (int) (Math.random() * (max - min + 1) + min);
                    idNotValid = false;
                    for(int idInExam : takenPawnId){
                        if(idInExam == randInt){
                            idNotValid = true;
                            break;
                        }
                    }
                } while ((randInt % 2 != 0)&&(!idNotValid));
            }
            newPawn.setId(randInt);
            gameGrid.getCells(valx - 1, valy - 1).setPawn(newPawn);


        }

        return gameGrid;
    }

    /**
     * Chose the pawn to move
     *
     * @param currentPlayer player that have to chose
     * @param gameGrid      grid of the game
     * @return the Pawn that the player want to move
     */
    public Pawn choseToMove(Player currentPlayer, Grid gameGrid) {
        Pawn pawnToMove = null;
        int val;
        String word;
        Scanner input = new Scanner(System.in);
        Pawn[] playerPawns = new Pawn[2];
        StringColor color = colorString(currentPlayer, false);
        int[] coordinates = new int[4];
        boolean chosen = false;

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (gameGrid.getCells(x, y).getPawn() != null) {
                    if (gameGrid.getCells(x, y).getPawn().getOwner().getUsername().equals(currentPlayer.getUsername())) {
                        if (gameGrid.getCells(x, y).getPawn().getId() % 2 == 1) {
                            playerPawns[0] = gameGrid.getCells(x, y).getPawn();
                            coordinates[0] = x + 1;
                            coordinates[1] = y + 1;
                        } else {
                            playerPawns[1] = gameGrid.getCells(x, y).getPawn();
                            coordinates[2] = x + 1;
                            coordinates[3] = y + 1;
                        }
                    }
                }
            }
        }

        //print
        for (int i = 0; i < playerPawns.length; i++) {
            System.out.println((i + 1) + ". " + color + "Worker" + (i + 1) + StringColor.RESET + " at (" + coordinates[(i * 2)] + "," + coordinates[((i * 2) + 1)] + ")");
        }

        System.out.println("Which worker do you want to use?");

        //choice
        while (!chosen) {
            word = input.next();
            if (word.matches("^-?\\d+$")) {
                val = Integer.parseInt(word);
                if ((val > 0) && (val < playerPawns.length + 1)) {
                    pawnToMove = playerPawns[val - 1];
                    System.out.println("\nYou chose: " + val + ". " + color + " Worker" + val + StringColor.RESET + " at (" + coordinates[((val - 1) * 2)] + "," + coordinates[((val - 1) * 2) + 1] + ")");
                    chosen = true;
                } else {
                    System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " \"" + val + "\"" + " is not a valid input, input must be 1 or 2. Retry ");
                }
            } else {
                System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " \"" + word + "\"" + " is not a valid input, input must be 1 or 2. Retry");
            }
        }

        return pawnToMove;
    }

    /**
     * Chose the move that the player wants to do
     *
     * @param possibleAction list of possible action from which the player has to chose
     * @return the action that the player choose
     */
    public Move choseMove(MoveList possibleAction) { //move ifmove dice se è mossa di movimento (true) o costruzione (false)
        Move chosenMove = null;
        //Move jumpAction;
        Scanner input = new Scanner(System.in);
        String word;
        String action;
        StringBuilder list = new StringBuilder();
        StringBuilder[] row;
        int val;
        int line;
        int nRow;
        boolean chosen = false;

        if (possibleAction.getMove(0).getIfMove()) {
            action = "Move";
        } else {
            action = "Build";
        }

        /*
        if ((possibleAction.size() == 27)){
            nRow = 9;
        }else if ((possibleAction.size() == 26)){
            nRow = 8;
        }else if ((possibleAction.size() == 28) || (possibleAction.size() == 21) || (possibleAction.size() == 14) || (possibleAction.size() == 13)){
            nRow = 7;
        }else if ((possibleAction.size() == 30) || (possibleAction.size() == 29) || (possibleAction.size() == 24)  || (possibleAction.size() == 23)  || (possibleAction.size() == 22) || (possibleAction.size() == 18)  || (possibleAction.size() == 17)){
            nRow = 6;
        }else if ((possibleAction.size() == 25)  || (possibleAction.size() == 20)  || (possibleAction.size() == 19) || (possibleAction.size() == 15) || (possibleAction.size() == 10)) {
            nRow = 5;
        } else if ((possibleAction.size() == 16) || (possibleAction.size() == 12) || (possibleAction.size() == 11) || (possibleAction.size() == 8) || (possibleAction.size() == 7)) {
            nRow = 4; //1+3=4%4=0 2+3=5%4=1 3+3=6%4=2 4+3=7%4=3
        }else if ((possibleAction.size() == 9) || (possibleAction.size() == 6) || (possibleAction.size() == 5)) {
            nRow = 3;
        } else if (possibleAction.size() == 4) {
            nRow = 2;
        } else {    //3 2 e 1 rispettive row 1 col
            nRow = possibleAction.size(); //1+2=3%3=0 2+2=4%3=1 3+2=5%3=2| 1+1=2%2=0 2+1=3%2=1 | 1+0=1%1=0
        }
         */
        //print
        if ((possibleAction.size() > 16)){
            nRow = 6;
        }else if ((possibleAction.size() == 15) || (possibleAction.size() == 14) || (possibleAction.size() == 13) || (possibleAction.size() == 10)) {
            nRow = 5;
        } else if ((possibleAction.size() == 16) || (possibleAction.size() == 12) || (possibleAction.size() == 11) || (possibleAction.size() == 8) || (possibleAction.size() == 7)) {
            nRow = 4; //1+3=4%4=0 2+3=5%4=1 3+3=6%4=2 4+3=7%4=3
        }else if ((possibleAction.size() == 9) || (possibleAction.size() == 6) || (possibleAction.size() == 5)) {
            nRow = 3;
        } else if (possibleAction.size() == 4) {
            nRow = 2;
        } else {    //3 2 e 1 rispettive row 1 col
            nRow = possibleAction.size(); //1+2=3%3=0 2+2=4%3=1 3+2=5%3=2| 1+1=2%2=0 2+1=3%2=1 | 1+0=1%1=0
        }

        row = new StringBuilder[nRow];
        for (int i = 0; i < nRow; i++) {
            row[i] = new StringBuilder();
        }
        for (int i = 1; i < possibleAction.size() + 1; i++) {
            line = (i + nRow - 1) % nRow;
            if(possibleAction.getMove(i-1).getX() > 5){
                //jumpAction = possibleAction.getMove(i-1);
                row[(i + nRow) % nRow].append("   " + i + ". SKIP" + action.toUpperCase() + "                          ");
            }else {
                if (possibleAction.getMove(i - 1).getX() < 0) {
                    row[line].append("   " + i + ". " + action + " a Dome to (x: " + (-possibleAction.getMove(i - 1).getX()) + ", y: " + (-possibleAction.getMove(i - 1).getY()) + ")    ");
                } else {
                    row[line].append("   " + i + ". " + action + " to (x: " + (possibleAction.getMove(i - 1).getX() + 1) + ", y: " + (possibleAction.getMove(i - 1).getY() + 1) + ")           ");
                }
            }
        }

        for(int i = 0; i < nRow; i++){
            list.append(row[i] + "\n");
        }

        System.out.println(list);

        //choice
        while (!chosen) {

            System.out.println("(indicate the number corresponding to the chosen action)\nChosen Action: ");
            word = input.next();
            if (word.matches("^-?\\d+$")) {
                val = Integer.parseInt(word);
                if ((val > 0) && (val < possibleAction.size() + 1)) {
                    chosenMove = possibleAction.getMove(val - 1);
                    if(chosenMove.getX() < 0){
                        System.out.println(" Your choice: " + val + ". " + action + " a Dome to (x: " + (-chosenMove.getX()) + ", y: " + (-chosenMove.getY()) + ")");
                    }else if (chosenMove.getX() > 5){
                        System.out.println(" Your choice: Skip " + action + " a second time");
                    } else {
                        System.out.println(" Your choice: " + val + ". " + action + " to (x: " + (chosenMove.getX() + 1) + ", y: " + (chosenMove.getY() + 1) + ")");
                    }
                    chosen = true;
                } else {
                    System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " \"" + val + "\"" + " is not a valid input, input must be a number between 1 and " + possibleAction.size() + ". Retry");
                }
            } else {
                System.out.println(StringColor.ANSI_RED + " ⚠ " + StringColor.RESET + " \"" + word + "\"" + " is not a valid input, input must be a number between 1 and " + possibleAction.size() + ". Retry");
            }
        }

        return chosenMove;
    }

    /**
     * Draw the results of the game
     *
     * @param currentPlayer player that is playing
     * @param winnerPlayer winner player
     */
    public void drawResults(Player currentPlayer, Player winnerPlayer) {
        /*

          /‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\
         /                                                                                  \
        |      /██     /██ /██████  /██   /██       /██      /██  /██████  /██   /██         |
        |      \  ██ /██/| ██  \ ██| ██  | ██      | ██ /███| ██| ██  \ ██| ████| ██         |
        |       \  ████/ | ██  | ██| ██  | ██      | ██/██ ██ ██| ██  | ██| ██ ██ ██         |
        |        \  ██/  | ██  | ██| ██  | ██      | ████_  ████| ██  | ██| ██  ████         |
        |         | ██   |  ██████/|  ██████/      | ██/   \  ██|  ██████/| ██ \  ██         |
        |         |__/    \______/  \______/       |__/     \__/ \______/ |__/  \__/         |
         \                                                                                  /
          \________________________________________________________________________________/

            |‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|
            |          ╦ ╦╔═╗╦ ╦  ╦  ╔═╗╔═╗╔╦╗          |
            |          ╚╦╝║ ║║ ║  ║  ║ ║╚═╗ ║           |
            |           ╩ ╚═╝╚═╝  ╩═╝╚═╝╚═╝ ╩           |
            |___________________________________________|
         */
        StringColor color = colorString(currentPlayer, false);
        StringBuilder results = new StringBuilder();
        if(currentPlayer.getUsername().equals(winnerPlayer.getUsername())){
            results.append(" \n" +
                    color + "  /‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\\" + StringColor.RESET + "\n" +
                    color + " /                                                                                  \\" + StringColor.RESET + "\n" +
                    color + "|      /██     /██ /██████  /██   /██       /██      /██  /██████  /██   /██         |" + StringColor.RESET + "\n" +
                    color + "|      \\  ██ /██/| ██  \\ ██| ██  | ██      | ██ /███| ██| ██  \\ ██| ████| ██         |" + StringColor.RESET + "\n" +
                    color + "|       \\  ████/ | ██  | ██| ██  | ██      | ██/██ ██ ██| ██  | ██| ██ ██ ██         |" + StringColor.RESET + "\n" +
                    color + "|        \\  ██/  | ██  | ██| ██  | ██      | ████_  ████| ██  | ██| ██  ████         |" + StringColor.RESET + "\n" +
                    color + "|         | ██   |  ██████/|  ██████/      | ██/   \\  ██|  ██████/| ██ \\  ██         |" + StringColor.RESET + "\n" +
                    color + "|         |__/    \\______/  \\______/       |__/     \\__/ \\______/ |__/  \\__/         |" + StringColor.RESET + "\n" +
                    color + " \\                                                                                  /" + StringColor.RESET + "\n" +
                    color + "  \\________________________________________________________________________________/" + StringColor.RESET);

        } else {
            results.append("\n" +
                    color + " |‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|" + StringColor.RESET + "\n" +
                    color + " |          ╦ ╦╔═╗╦ ╦  ╦  ╔═╗╔═╗╔╦╗          |" + StringColor.RESET + "\n" +
                    color + " |          ╚╦╝║ ║║ ║  ║  ║ ║╚═╗ ║           |" + StringColor.RESET + "\n" +
                    color + " |           ╩ ╚═╝╚═╝  ╩═╝╚═╝╚═╝ ╩           |" + StringColor.RESET + "\n" +
                    color + " |___________________________________________|" + StringColor.RESET + "\n\n");

            color = colorString(winnerPlayer, true);
            results.append((color + " " + StringColor.RESET).repeat(Math.max(0, winnerPlayer.getUsername().length() + 30)));
            results.append("\n");
            results.append(color + "   " + StringColor.RESET);
            results.append(" ".repeat(Math.max(0, winnerPlayer.getUsername().length() + 24)));
            results.append(color + "   " + StringColor.RESET);
            results.append("\n");
            results.append(color + "   " + StringColor.RESET);
            color = colorString(winnerPlayer, false);
            results.append(color + "     " + winnerPlayer.getUsername() + StringColor.RESET + " IS THE WINNER     ");
            color = colorString(winnerPlayer, true);
            results.append(color + "   " + StringColor.RESET);
            results.append("\n");
            results.append(color + "   " + StringColor.RESET);
            results.append(" ".repeat(Math.max(0, winnerPlayer.getUsername().length() + 24)));
            results.append(color + "   " + StringColor.RESET);
            results.append("\n");
            results.append((color + " " + StringColor.RESET).repeat(Math.max(0, winnerPlayer.getUsername().length() + 30)));
        }
        System.out.println(results);
    }

    /**
     * Set ArrayList<String> chosenDivinities
     *
     * @param chosenDivinities list of chosen divinities
     */
    public void setChosenDivinities(ArrayList<String> chosenDivinities) {
        this.chosenDivinities = chosenDivinities;
    }

    /**
     * Constructor
     */
    public CLI() {
        chosenDivinities = new ArrayList<String>();
        lobby = false;
    }

}