package cz.bendik.explodingatoms.businesslogic;

import java.util.ArrayList;

public class Block {
    private gameColors gameColor;
    private final ArrayList<Block> neighbors;
    private int electronCount;
    private final int row;
    private final int col;

    public Block(gameColors color, ArrayList<Block> neighbors, int electronCount, int row, int col) {
        this.gameColor = color;
        this.neighbors = neighbors;
        this.electronCount = electronCount;
        this.row = row;
        this.col = col;
    }

    public gameColors getGameColor() {
        return gameColor;
    }

    public ArrayList<Block> getNeighbors() {
        return neighbors;
    }

    public int getElectronCount() {
        return electronCount;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setColor(gameColors gameColor) {
        this.gameColor = gameColor;
    }

    public void addNeighbor(Block block) {
        this.neighbors.add(block);
    }

    public void setElectronCount(int electronCount) {
        this.electronCount = electronCount;
    }
}
