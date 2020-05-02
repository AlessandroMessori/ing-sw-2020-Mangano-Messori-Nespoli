package it.polimi.ingsw.PSP19.Server.Model;
//Apollo OK
//Artemis OK
//Athena
//Atlas
//Demeter
public class Move {
    private Pawn toMove;
    private int x;
    private int y;
    private boolean ifMove;

    /**
     * @return pawn to move
     */
    public Pawn getToMove() {
        return toMove;
    }

    /**
     * set value of Pawn
     *
     * @param toMove value of pawn to move
     */
    public void setToMove(Pawn toMove) {
        this.toMove = toMove;
    }

    /**
     * @return value of x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * set value of x coordinate
     *
     * @param x value of x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return value of y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * set value of y coordinate
     *
     * @param y value of y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return value of IfMove
     */
    public boolean getIfMove() {
        return ifMove;
    }

    /**
     * set value of ifMove
     *
     * @param ifMove sett value o IfMove
     */
    public void setIfMove(boolean ifMove) {
        this.ifMove = ifMove;
    }

    /**
     * constructor
     *
     * @param pawn pawn to move
     */
    public Move(Pawn pawn) {
        this.toMove = pawn;
    }

    @Override
    public String toString() {
        StringBuilder moveString = new StringBuilder();

        moveString.append("\"pawnID\":" + getToMove().getId() + ",\n");
        moveString.append("\"x\":" + getX() + ",\n");
        moveString.append("\"y\":" + getX() + ",\n");
        moveString.append("\"ifMove\":" + getIfMove() + ",\n");


        return "{" + moveString + "}";
    }
}
