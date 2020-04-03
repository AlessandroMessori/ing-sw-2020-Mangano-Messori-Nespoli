package Server.Model;

public class Pawn {
    private Player owner;
    private int id;

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
     *
     * @return value of Id
     */
    public int getId() {
        return id;
    }

    /**
     * set value of Id
     * @param id value of Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Constructor
     *
     * @param owner value of Owner
     */
    public Pawn(Player owner) {
        this.owner = owner;
        id = 0;
    }
}
