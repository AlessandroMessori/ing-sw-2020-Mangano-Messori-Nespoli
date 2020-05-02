package it.polimi.ingsw.PSP19.Server.Model;

import java.util.ArrayList;

public class DivinityList {
    private ArrayList<Divinity> divinities;


    /**
     * returns the divinity list size
     *
     * @return size;
     */
    public int size() {
        return divinities.size();
    }

    /**
     * adds a new divinity to the list,throws exception if move is already present
     *
     * @param divinity the move to add;
     */
    public void addDivinity(Divinity divinity) throws IllegalArgumentException {

        for (Divinity dv : divinities) {
            if (dv.toString().equals(divinity.toString())) {
                throw new IllegalArgumentException();
            }
        }

        divinities.add(divinity);
    }

    /**
     * returns divinity at position i,throws IndexOutOfBoundsException if is greater than list size
     *
     * @param i index of the divinity
     * @return divinity at position i
     */
    public Divinity getDivinity(int i) throws IllegalArgumentException {
        if (i > size() - 1) {
            throw new IllegalArgumentException();
        }

        return divinities.get(i);
    }

    /**
     * deletes a divinity,throws exception if the divinity isn't in the list
     *
     * @param divinity the divinity to delete;
     */
    public void deleteDivinity(Divinity divinity) throws IllegalArgumentException {

        Divinity divinityToDelete;
        divinityToDelete = null;

        for (Divinity dv : divinities) {
            if (dv == divinity) {
                divinityToDelete = dv;
            }
        }

        if (divinityToDelete == null) {
            throw new IllegalArgumentException();
        } else {
            divinities.remove(divinityToDelete);
        }

    }

    /**
     * constructor
     */
    public DivinityList() {
        divinities = new ArrayList<Divinity>();
    }


    @Override
    public String toString() {

        StringBuilder divinitiesString = new StringBuilder();

        for (int i = 0; i < size(); i++) {
            divinitiesString.append(getDivinity(i).toString() + ",");
        }

        return "[" + divinitiesString + "]";
    }
}

