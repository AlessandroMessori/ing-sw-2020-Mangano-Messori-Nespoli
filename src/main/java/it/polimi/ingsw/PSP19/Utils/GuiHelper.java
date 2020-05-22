package it.polimi.ingsw.PSP19.Utils;

import it.polimi.ingsw.PSP19.Server.Model.Colour;
import it.polimi.ingsw.PSP19.Server.Model.Game;

import java.util.ArrayList;

public class GuiHelper {

    /**
     * gets the image path of a move image
     *
     * @param colour the color of the player making the move
     * @param build the type of move
     *
     * @return the move image path
     */
    public static String getMoveImagePath(Colour colour, boolean build) {
        return build ? "/Images/Game/Action/build.png" : "/Images/Game/Action/move_" + colour.toString().toLowerCase() + ".png";
    }

    /**
     * gets the image path of a building
     *
     * @param level the level of the building
     *
     * @return the building image path
     */
    public static String getBuildingImagePath(int level) {
        return (level < 1 || level > 4) ? null : "/Images/Game/Buildings/Building" + level + "levels.png";
    }

    /**
     * gets the image path of a pawn image
     *
     * @param colour the color of the pawn making
     * @param pawnID the if of the pawn,if odd returns a Male pawn,if even returns a Female pawn
     *
     * @return the pawn image path
     */
    public static String getPawnImagePath(Colour colour, int pawnID) {
        String gender = (pawnID % 2 == 0) ? "Female" : "Male";

        switch (colour) {
            case RED:
                return "/Images/Game/Pawns/" + gender + "Builder_red.png";
            case BLUE:
                return "/Images/Game/Pawns/" + gender + "Builder_blu.png";
            case YELLOW:
                return "/Images/Game/Pawns/" + gender + "Builder_yellow.png";
            case WHITE:
                return "/Images/Game/Pawns/" + gender + "Builder_white.png";
            case PINK:
                return "/Images/Game/Pawns/" + gender + "Builder_purple.png";
            default:
                return null;
        }
    }

    /**
     * gets the image path of a contour image
     *
     * @param colour the color of the contour image
     * @param big dimension of the image
     *
     * @return the contour image path
     */
    public static String getContourImagePath(Colour colour, boolean big) {
        String bigImage = big ? "" : "Small";
        String bigPath = big ? "Bigger" : "Smaller";

        return "/Images/Game/Gods/" + bigPath + "/currentPlayer" + bigImage + "_" + colour.toString().toLowerCase() + ".png";
    }

    /**
     * gets the image path of a move image
     *
     * @param game the game the pawn belongs to
     * @param pawnCounter number of pawns already in the game before this function call
     *
     * @return the new pawn id
     */
    public static int getNewPawnId(Game game,int pawnCounter) {
        int randInt;
        int max = (int) Math.pow(10, 5);
        int min = (int) Math.pow(10, 4);
        ArrayList<Integer> takenPawnId = new ArrayList<>();

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                if (game.getNewGrid().getCells(x, y).getPawn() != null) {
                    takenPawnId.add(game.getNewGrid().getCells(x, y).getPawn().getId());
                }
            }
        }

        randInt = getRandInt(max, min, takenPawnId, pawnCounter);

        return randInt;
    }

    public static int getRandInt(int max, int min, ArrayList<Integer> takenPawnId, int pawnCounter) {
        int randInt;
        boolean idNotValid;
        if (pawnCounter == 0) {
            //first pawn have an odd id
            do {
                randInt = (int) (Math.random() * (max - min + 1) + min);
                idNotValid = false;
                for (int idInExam : takenPawnId) {
                    if (idInExam == randInt) {
                        idNotValid = true;
                        break;
                    }
                }
            } while ((randInt % 2 != 1) && (!idNotValid));
        } else {
            //second pawn have an even id
            do {
                randInt = (int) (Math.random() * (max - min + 1) + min);
                idNotValid = false;
                for (int idInExam : takenPawnId) {
                    if (idInExam == randInt) {
                        idNotValid = true;
                        break;
                    }
                }
            } while ((randInt % 2 != 0) && (!idNotValid));
        }
        return randInt;
    }


    /**
     * gets a specified percentage of a value
     *
     * @param value the value to get the percentage from
     * @param percentage the percentage to get
     *
     * @return the percentage of the value
     */
    public static double getPercentage(double value, double percentage) {
        return value / 100 * percentage;
    }

    /**
     * gets the coordinates for centering a node in a window
     *
     * @param width the total window width
     * @param nodeWidth width of the node to center
     *
     * @return centered node coordinates
     */
    public static double getCenteredX(double width, double nodeWidth) {
        return width / 2 - nodeWidth / 2;
    }
}
