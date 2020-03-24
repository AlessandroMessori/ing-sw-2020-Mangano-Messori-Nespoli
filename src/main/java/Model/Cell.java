package Model;

public class Cell {
    private Tower tower;
    private Pawn pawn;

    /**
     *
     * @return value of Tower
     */
    public Tower getTower() {
        return tower;
    }

    /**
     * set value of Tower
     * @param tower value of Tower
     */
    public void setTower(Tower tower) {
        this.tower = tower;
    }

    /**
     *
     * @return value of Pawn
     */
    public Pawn getPawn() {
        return pawn;
    }

    /**
     * set value of Pawn
     * @param pawn value of Pawn
     */
    public void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    /**
     * Constructor
     */
    public Cell(Tower tower, Pawn pawn) {
        this.tower = tower;
        this.pawn = pawn;
    }
}
