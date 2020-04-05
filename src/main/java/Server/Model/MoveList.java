package Server.Model;

import java.util.ArrayList;

public class MoveList {
    private ArrayList<Move> moves;


    /**
     * returns the moves list size
     *
     * @return size;
     */
    public int size() {
        return moves.size();
    }

    /**
     * adds a new move to the list,throws exception if move is already present
     *
     * @param move the move to add;
     */
    public void addMove(Move move) throws IllegalArgumentException {

        for (Move mv : moves) {
            if (move.getX() == mv.getX() && move.getY() == mv.getY()) {
                throw new IllegalArgumentException();
            }
        }

        moves.add(move);
    }

    /**
     * returns move at position i,throws IndexOutOfBoundsException if is greater than list size
     *
     * @param i index of the move
     * @return player at position i
     */
    public Move getMove(int i) throws IllegalArgumentException {
        if (i > size() - 1) {
            throw new IllegalArgumentException();
        }

        return moves.get(i);
    }

    /**
     * deletes a move,throws exception if the move isn't in the list
     *
     * @param move the move to delete;
     */
    public void deleteMove(Move move) throws IllegalArgumentException {

        Move moveToDelete;
        moveToDelete = null;

        for (Move mv : moves) {
            if (mv.getX() == move.getX() && mv.getY() == move.getY()) {
                moveToDelete = mv;
            }
        }

        if (moveToDelete == null) {
            throw new IllegalArgumentException();
        } else {
            moves.remove(moveToDelete);
        }

    }

    /**
     * constructor
     */
    public MoveList() {
        moves = new ArrayList<Move>();
    }


    @Override
    public String toString() {

        StringBuilder movesString = new StringBuilder();

        for (int i = 0; i < size(); i++) {
            movesString.append(getMove(i).toString() + ",");
        }

        return "[" + movesString + "]";
    }
}
