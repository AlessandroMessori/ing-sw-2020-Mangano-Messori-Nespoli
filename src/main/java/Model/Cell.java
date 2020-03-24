package Model;

public class Cell {
    private Type type;
    private Tower tower;
    private Pawn pawn;

    /**
     *
     * @return value of Type
     */
    public Type getType() {
        return type;
    }

    /**
     * set value of Type
     * @param type value of Type
     */
    public void setType(Type type) {
        this.type = type;
        if(this.type == Type.EMPTY){
            this.tower = null;
            this.pawn = null;
        }
    }

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
        if(this.getType() == Type.TOWER){
            this.tower = tower;
        }
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
        if(this.getType() == Type.PAWN) {
            this.pawn = pawn;
        }
    }

    /**
     * Constructor
     */
    public Cell(Type type, Tower tower, Pawn pawn) {
        this.type = type;
        if(this.type == Type.EMPTY){
            this.tower = null;
            this.pawn = null;
        }else if(this.type == Type.TOWER){
            this.tower = tower;
            this.pawn = null;
        }else if(this.type == Type.PAWN){
            this.tower = null;
            this.pawn = pawn;
        }
    }
}
