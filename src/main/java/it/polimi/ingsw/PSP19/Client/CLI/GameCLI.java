package it.polimi.ingsw.PSP19.Client.CLI;

import it.polimi.ingsw.PSP19.Server.Model.*;
import it.polimi.ingsw.PSP19.Utils.GuiHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class GameCLI {

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
        String bot = "__  |_______|_______|_______|_______|_______|"; //+ "\n" +
                     //"     ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾";

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
                    color = colorString(grid.getCells(x, y).getPawn().getOwner());
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
            color = colorString(inGamePlayers.getPlayer(i));
            list.append("   " + color + inGamePlayers.getPlayer(i).getUsername() + StringColor.RESET + "  Divinity: " + inGamePlayers.getPlayer(i).getDivinity().toString() + "\n");
        }
        System.out.println(list);
    }

    /**
     * Extract the color of the player to print with
     *
     * @param pl player you need to extract the color
     * @return the color of the player
     */
    public StringColor colorString(Player pl) {
        StringColor color = null;
            if (pl.getColour() == Colour.BLUE) color = StringColor.ANSI_BLUE;
            if (pl.getColour() == Colour.RED) color = StringColor.ANSI_RED;
            //if (pl.getColour() == Colour.GREEN) color = StringColor.ANSI_GREEN;
            if (pl.getColour() == Colour.YELLOW) color = StringColor.ANSI_YELLOW;
            if (pl.getColour() == Colour.WHITE) color = StringColor.ANSI_WHITE;
            if (pl.getColour() == Colour.PINK) color = StringColor.ANSI_PINK;
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
        //boolean idNotValid;
        Pawn newPawn;
        int valx;
        int valy;
        int max = (int) Math.pow(10, 5);
        int min = (int) Math.pow(10, 4);
        //int randInt;
        ArrayList<Integer> takenPawnId = new ArrayList<Integer>();

        System.out.println("\nChose the starting position for your workers");
        System.out.println("Write coordinates X,Y \n");
        color = colorString(choosingPlayer);
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
            int randomInt = GuiHelper.getRandInt(max, min, takenPawnId, j);
            newPawn.setId(randomInt);
            gameGrid.getCells(valx - 1, valy - 1).setPawn(newPawn);

            System.out.println("\n");

        }

        return gameGrid;
    }

    /**
     * To activate the effect of Prometheus the player have to chose if he want to move up by one level or not
     *
     * @return the valuer of goUp: false if the player don't want to move up, true if the player want to move up in this turn
     */
    public boolean wantToGoUp (){
        boolean goUp;
        Scanner input = new Scanner(System.in);
        String decision;

        do {
            System.out.println("Due to you divinity power you can BUILD before and after your move BUT you will not be able to MOVE UP " +
                    "\n You want to MOVE UP in this turn? (you will be able to build only one time after your move)" +
                    "\n           y: yes           n: no");
            decision = input.next();
        } while (!(decision.equals("y")) && !(decision.equals("n")));

        goUp = decision.equals("y");

        return goUp;
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
        StringColor color = colorString(currentPlayer);
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
     * Higlight the cells where is possible to act
     *
     * @param grid Grid of the game
     * @param possibleMoves list of possible action
     */
    public void drawPossibleMovesOnGrid(Grid grid, MoveList possibleMoves) {
        StringBuilder rowOne = new StringBuilder();
        StringBuilder rowTwo = new StringBuilder();
        StringBuilder top = new StringBuilder();
        StringBuilder mid = new StringBuilder();
        StringBuilder bot = new StringBuilder();
        StringColor color = StringColor.ANSI_GREEN;
        StringColor colorPawn;
        int[][] posCoord = new int[5][5];
        for(int i = 0; i < possibleMoves.size(); i++) {
            for(int x = 0; x < 5; x++){
                for(int y = 0; y < 5; y++){

                    if ((possibleMoves.getMove(i).getX() == x) && (possibleMoves.getMove(i).getY() == y)) {
                        posCoord[x][y] = 1;
                    }
                }
            }
        }

        System.out.println("\\ Y |   1   |   2   |   3   |   4   |   5   |");
        top.append("X \\");
        bot.append("__  ");
        for (int x = 0; x < 5; x++) {

            top.append("  ");

            rowOne.append("    ");
            rowTwo.append((x + 1) + "   ");
            mid.append("__  ");

            for (int y = 0; y < 5; y++) {

                if (x == 0) {
                    if (posCoord[x][y] == 1) {
                        top.append(color + "_______" + StringColor.RESET);
                        rowOne.append(color + "|" + StringColor.RESET);
                        rowTwo.append(color + "|" + StringColor.RESET);
                    } else {

                        top.append("_______");

                        if (y > 0) {
                            if (posCoord[x][y - 1] == 1) {
                                rowOne.append(color + "|" + StringColor.RESET);
                                rowTwo.append(color + "|" + StringColor.RESET);
                            } else {
                                rowOne.append("|");
                                rowTwo.append("|");
                            }
                        } else {
                            rowOne.append("|");
                            rowTwo.append("|");
                        }

                    }

                } else {
                    if (posCoord[x - 1][y] == 1) {
                        mid.append(color + "|" + StringColor.RESET);
                    } else if (y > 0) {
                        if (posCoord[x - 1][y - 1] == 1) {
                            mid.append(color + "|" + StringColor.RESET);
                        } else {
                            mid.append("|");
                        }
                    } else {
                        mid.append("|");
                    }

                    if (posCoord[x][y] == 1) {
                        mid.append(color + "_______" + StringColor.RESET);
                        rowOne.append(color + "|" + StringColor.RESET);
                        rowTwo.append(color + "|" + StringColor.RESET);
                    } else {
                        if (posCoord[x - 1][y] == 1) {
                            mid.append(color + "_______" + StringColor.RESET);
                        } else {
                            mid.append("_______");
                        }
                        if (y > 0) {
                            if (posCoord[x][y - 1] == 1) {
                                rowOne.append(color + "|" + StringColor.RESET);
                                rowTwo.append(color + "|" + StringColor.RESET);
                            } else {
                                rowOne.append("|");
                                rowTwo.append("|");
                            }
                        } else {
                            rowOne.append("|");
                            rowTwo.append("|");
                        }

                    }
                }


                if (grid.getCells(x, y).getPawn() != null) {
                    colorPawn = colorString(grid.getCells(x, y).getPawn().getOwner());
                    rowOne.append(colorPawn + " W");
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
                    if ((lvl == 4) || (grid.getCells(x, y).getTower().getIsDome())) {
                        rowTwo.append("    X  ");
                    } else {
                        rowTwo.append("    T" + lvl + " ");
                    }
                } else {
                    rowTwo.append("       ");
                }

                if (y == 4) {
                    if (x != 0) {
                        if (posCoord[x - 1][y] == 1) {
                            mid.append(color + "|" + StringColor.RESET);
                        } else {

                            mid.append("|");

                        }
                    }
                    if (posCoord[x][y] == 1) {
                        rowOne.append(color + "|" + StringColor.RESET);
                        rowTwo.append(color + "|" + StringColor.RESET);
                    } else {
                        rowOne.append("|");
                        rowTwo.append("|");
                    }
                }else{
                    top.append("_");
                }

                if (x == 4) {
                    if (posCoord[x][y] == 1) {
                        bot.append(color + "|" + StringColor.RESET);
                    } else if (y > 0) {
                        if (posCoord[x][y - 1] == 1) {
                            bot.append(color + "|" + StringColor.RESET);
                        } else {
                            bot.append("|");
                        }
                    } else {
                        bot.append("|");
                    }

                    if (posCoord[x][y] == 1) {
                        bot.append(color + "_______" + StringColor.RESET);

                    } else {
                        bot.append("_______");
                    }

                    if (y == 4) {
                        if (posCoord[x][y] == 1) {
                            bot.append(color + "|" + StringColor.RESET);
                        } else {
                            /*if (posCoord[x - 1][y - 1] == 1) {
                                bot.append(color + "|" + StringColor.RESET);
                            } else {*/
                            bot.append("|");
                            //}
                        }
                    }
                }

            }


            if(x == 0) System.out.println(top);
            else System.out.println(mid);

            System.out.println(rowOne);
            System.out.println(rowTwo);

            top.delete(0, top.length());
            rowOne.delete(0, rowOne.length());
            rowTwo.delete(0, rowTwo.length());
            mid.delete(0, mid.length());


        }
        System.out.println(bot);
        bot.delete(0, bot.length());

    }

    /**
     * Chose the move that the player wants to do
     *
     * @param possibleAction list of possible action from which the player has to chose
     * @return the action that the player choose
     */
    public Move choseMove(MoveList possibleAction, Grid movesGrid) { //move ifmove dice se è mossa di movimento (true) o costruzione (false)
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

        drawPossibleMovesOnGrid(movesGrid, possibleAction);
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
     * Constructor
     */
    public GameCLI() {
    }
 
}