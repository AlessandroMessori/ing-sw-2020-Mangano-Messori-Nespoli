package Model;

public class Player {
    private String username;
    private Divinity divinity;
    private Colour colour;

    /**
     *
     * @return value of Username;
     */
    public String getUserName() {
        return username;
    }

    /**
     *
     * @return value of Divinity;
     */
    public Divinity getDivinity() {
        return divinity;
    }


    /**
     *
     * @return value of Colour;
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * sets value of username
     * @param uName value of Username;
     */
    public String setUserName(String uName) {
        return username = uName;
    }

    /**
     * sets value of divinity
     * @param div value of Divinity;
     */
    public Divinity getDivinity(Divinity div) {
        return divinity = div;
    }


    /**
     * sets value of colour
     * @param col value of Colour;
     */
    public Colour getColour(Colour col) {
        return colour = col;
    }

    /**
     * constructor
     * @param uName value of Username;
     * @param div value of Divinity;
     * @param col value of Colour;
     */
    public Player(String uName,Divinity div,Colour col) {
        username = uName;
        divinity = div;
        colour = col;
    }


}
