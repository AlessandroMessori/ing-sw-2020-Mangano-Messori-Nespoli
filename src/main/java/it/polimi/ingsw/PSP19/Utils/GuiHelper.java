package it.polimi.ingsw.PSP19.Utils;

public class GuiHelper {

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
