package it.polimi.ingsw.PSP19.Client.CLI;

import it.polimi.ingsw.PSP19.Server.Model.Divinity;

import java.util.ArrayList;
import java.util.Scanner;

public class DivinitiesChoicesCLI {
    private ArrayList<String> chosenDivinities;


    /**
     * Print the list of all divinities for the initial choice
     */
    public void printListDivinities(int players) {
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
    public ArrayList<String> readDivinitiesChoice(int players) {
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
            printListDivinities(players);
            readDivinitiesChoice(players);
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

    public void setChosenDivinities(ArrayList<String> chosenDivinities) {
        this.chosenDivinities = chosenDivinities;
    }

    public DivinitiesChoicesCLI() {
        this.chosenDivinities = new ArrayList<String>();
    }
}
