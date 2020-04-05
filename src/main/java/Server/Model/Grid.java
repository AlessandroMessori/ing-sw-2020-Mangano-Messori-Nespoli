package Server.Model;

public class Grid {
    private Cell[][] cells;

    /**
     * @param x value of row
     * @param y value of column
     * @return value of Cells[x][y]
     */
    public Cell getCells(int x, int y) {
        return cells[x][y];
    }

    /**
     * set value of Cells[x][y]
     *
     * @param cell value of Cell
     * @param x    value of row
     * @param y    value of column
     */
    public void setCells(Cell cell, int x, int y) {
        cells[x][y] = cell;
    }

    /**
     * constructor
     */
    public Grid() {
        cells = new Cell[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                cells[i][j] = new Cell(new Tower(0, false), null);
            }
        }
    }

    @Override
    public String toString() {

        StringBuilder cellString = new StringBuilder();
        String doubleQuotes = Character.toString((char) 34);

        for (int i = 0; i < 5; i++) {
            cellString.append("[");
            for (int j = 0; j < 5; j++) {
                Cell currentCell = cells[i][j];

                cellString.append("{");

                if (currentCell.getPawn() != null) {
                    cellString.append(doubleQuotes + "pawnId" + doubleQuotes + ":" + currentCell.getPawn().getId() + ",");
                }

                cellString.append(doubleQuotes + "towerLevel" + doubleQuotes + ":" + currentCell.getTower().getLevel() + ",");
                cellString.append(doubleQuotes + "isDome" + doubleQuotes + ":" + currentCell.getTower().getIsDome() + ",");

                cellString.append("},");

            }
            cellString.append("]");
        }


        return "{" + doubleQuotes + "cells" + doubleQuotes + ": [" + cellString + "]}";
    }
}
