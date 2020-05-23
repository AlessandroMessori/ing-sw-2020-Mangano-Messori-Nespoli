package it.polimi.ingsw.PSP19.Client.CLI;

import it.polimi.ingsw.PSP19.Server.Model.Colour;

import java.util.ArrayList;
import java.util.Scanner;

public class ColorChoiceCLI {

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

    public ColorChoiceCLI() {
    }
}
