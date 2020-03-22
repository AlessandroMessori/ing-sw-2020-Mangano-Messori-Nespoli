package Model;

public class Pawn {
    private Player owner;

    /**
     *
     * @return value of Owner
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * set value of Owner
     * @param owner value of Owner
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }

    /**
     * Constructor
     *
     * @param owner value of Owner
     */
    public Pawn(Player owner) {
        this.owner = owner;
    }
}
