package Server.Model;

public class Player {
    private String username;
    private Divinity divinity;
    private Colour colour;
    private Pawn currentPawn;

    /**
     * @return value of Username;
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return value of Divinity;
     */
    public Divinity getDivinity() {
        return divinity;
    }


    /**
     * @return value of Colour;
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * @return value of Pawn;
     */
    public Pawn getCurrentPawn() {
        return currentPawn;
    }

    /**
     * sets value of username
     *
     * @param uName value of Username;
     */
    public void setUsername(String uName) {
        username = uName;
    }

    /**
     * sets value of divinity
     *
     * @param div value of Divinity;
     */
    public void setDivinity(Divinity div) {
        divinity = div;
    }


    /**
     * sets value of colour
     *
     * @param col value of Colour;
     */
    public void setColour(Colour col) {
        colour = col;
    }

    /**
     * sets value of currentPawn
     *
     * @param p value of currentPawn;
     */
    public void setCurrentPawn(Pawn p) {
        currentPawn = p;
    }
    
    @Override
    public String toString() {
        return getUsername();
    }

    /**
     * constructor
     *
     * @param uName value of Username;
     * @param div   value of Divinity;
     * @param col   value of Colour;
     */
    public Player(String uName, Divinity div, Colour col) {
        username = uName;
        divinity = div;
        colour = col;
    }


}
