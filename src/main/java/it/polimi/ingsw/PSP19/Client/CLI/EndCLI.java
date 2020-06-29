package it.polimi.ingsw.PSP19.Client.CLI;

import it.polimi.ingsw.PSP19.Server.Model.Colour;
import it.polimi.ingsw.PSP19.Server.Model.Player;

public class EndCLI {

    /**
     * Draw the results of the game
     *
     * @param currentPlayer player that is playing
     * @param winnerPlayer winner player
     */
    public void drawResults(Player currentPlayer, Player winnerPlayer) {
        /*

          /‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\
         /                                                                                  \
        |      /██     /██ /██████  /██   /██       /██      /██  /██████  /██   /██         |
        |      \  ██ /██/| ██  \ ██| ██  | ██      | ██ /███| ██| ██  \ ██| ████| ██         |
        |       \  ████/ | ██  | ██| ██  | ██      | ██/██ ██ ██| ██  | ██| ██ ██ ██         |
        |        \  ██/  | ██  | ██| ██  | ██      | ████_  ████| ██  | ██| ██  ████         |
        |         | ██   |  ██████/|  ██████/      | ██/   \  ██|  ██████/| ██ \  ██         |
        |         |__/    \______/  \______/       |__/     \__/ \______/ |__/  \__/         |
         \                                                                                  /
          \________________________________________________________________________________/

            |‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾|
            |          ╦ ╦╔═╗╦ ╦  ╦  ╔═╗╔═╗╔╦╗          |
            |          ╚╦╝║ ║║ ║  ║  ║ ║╚═╗ ║           |
            |           ╩ ╚═╝╚═╝  ╩═╝╚═╝╚═╝ ╩           |
            |___________________________________________|

         ____________________________________________________
        |    __   _______ _   _   _     _____ _____ _____    |
        |    \ \ / /  _  | | | | | |   |  _  /  ___|_   _|   |
        |     \ V /| | | | | | | | |   | | | \ `--.  | |     |
        |      \ / | | | | | | | | |   | | | |`--. \ | |     |
        |      | | \ \_/ / |_| | | |___\ \_/ /\__/ / | |     |
        |      \_/  \___/ \___/  \_____/\___/\____/  \_/     |
        |____________________________________________________|




         */
        StringColor color = colorString(currentPlayer, false);
        StringBuilder results = new StringBuilder();
        if(currentPlayer.getUsername().equals(winnerPlayer.getUsername())){
            results.append(" \n" +
                    color + "   ________________________________________________________________________________" + StringColor.RESET + "\n" +
                    color + "  /                                                                                \\" + StringColor.RESET + "\n" +
                    color + " /                                                                                  \\" + StringColor.RESET + "\n" +
                    color + "|      /##     /## /######  /##   /##       /##      /##  /######  /##   /##         |" + StringColor.RESET + "\n" +
                    color + "|      \\  ## /##/| ##  \\ ##| ##  | ##      | ## /###| ##| ##  \\ ##| ####| ##         |" + StringColor.RESET + "\n" +
                    color + "|       \\  ####/ | ##  | ##| ##  | ##      | ##/## ## ##| ##  | ##| ## ## ##         |" + StringColor.RESET + "\n" +
                    color + "|        \\  ##/  | ##  | ##| ##  | ##      | ####_  ####| ##  | ##| ##  ####         |" + StringColor.RESET + "\n" +
                    color + "|         | ##   |  ######/|  ######/      | ##/   \\  ##|  ######/| ## \\  ##         |" + StringColor.RESET + "\n" +
                    color + "|         |__/    \\______/  \\______/       |__/     \\__/ \\______/ |__/  \\__/         |" + StringColor.RESET + "\n" +
                    color + " \\                                                                                  /" + StringColor.RESET + "\n" +
                    color + "  \\________________________________________________________________________________/" + StringColor.RESET);

        } else {/*
            results.append("\n" +
                    color + "  ___________________________________________" + StringColor.RESET + "\n" +
                    color + " |                                           |" + StringColor.RESET + "\n" +
                    color + " |          ╦ ╦╔═╗╦ ╦  ╦  ╔═╗╔═╗╔╦╗          |" + StringColor.RESET + "\n" +
                    color + " |          ╚╦╝║ ║║ ║  ║  ║ ║╚═╗ ║           |" + StringColor.RESET + "\n" +
                    color + " |           ╩ ╚═╝╚═╝  ╩═╝╚═╝╚═╝ ╩           |" + StringColor.RESET + "\n" +
                    color + " |___________________________________________|" + StringColor.RESET + "\n\n");*/

            results.append("\n" +
                    color + " ____________________________________________________" + StringColor.RESET + "\n" +
                    color + "|    __   _______ _   _   _     _____ _____ _____    |" + StringColor.RESET + "\n" +
                    color + "|    \\ \\ / /  _  | | | | | |   |  _  /  ___|_   _|   |" + StringColor.RESET + "\n" +
                    color + "|     \\ V /| | | | | | | | |   | | | \\ `--.  | |     |" + StringColor.RESET + "\n" +
                    color + "|      \\ / | | | | | | | | |   | | | |`--. \\ | |     |" + StringColor.RESET + "\n" +
                    color + "|      | | \\ \\_/ / |_| | | |___\\ \\_/ /\\__/ / | |     |" + StringColor.RESET + "\n" +
                    color + "|      \\_/  \\___/ \\___/  \\_____/\\___/\\____/  \\_/     |" + StringColor.RESET + "\n" +
                    color + "|____________________________________________________|" + StringColor.RESET + "\n\n");

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
        return;
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
            //if (pl.getColour() == Colour.GREEN) color = StringColor.ANSI_GREEN;
            if (pl.getColour() == Colour.YELLOW) color = StringColor.ANSI_YELLOW;
            if (pl.getColour() == Colour.WHITE) color = StringColor.ANSI_WHITE;
            if (pl.getColour() == Colour.PINK) color = StringColor.ANSI_PINK;
        }else{
            if (pl.getColour() == Colour.BLUE) color = StringColor.BACKGROUND_BLUE;
            if (pl.getColour() == Colour.RED) color = StringColor.BACKGROUND_RED;
            //if (pl.getColour() == Colour.GREEN) color = StringColor.BACKGROUND_GREEN;
            if (pl.getColour() == Colour.YELLOW) color = StringColor.BACKGROUND_YELLOW;
            if (pl.getColour() == Colour.WHITE) color = StringColor.BACKGROUND_WHITE;
            if (pl.getColour() == Colour.PINK) color = StringColor.BACKGROUND_PINK;
        }
        return color;
    }

    /**
     * Constructor
     */
    public EndCLI(){
    }
}
