package Model;

public class Player {
    private String username;
    private Divinity divinity;
    private Colour colour;

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
