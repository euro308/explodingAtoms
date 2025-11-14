package cz.bendik.explodingatoms.businesslogic;

import java.util.ArrayList;

public class Block {
    private Colors colors;
    private final ArrayList<Block> neighbors;
    private int electronCount;
    private final int position;

    public Block(Colors colors, ArrayList<Block> neighbors, int electronCount, int position) {
        this.colors = colors;
        this.neighbors = neighbors;
        this.electronCount = electronCount;
        this.position = position;
    }

    public Colors getColor() {
        return colors;
    }

    public ArrayList<Block> getNeighbors() {
        return neighbors;
    }

    public int getElectronCount() {
        return electronCount;
    }

    public int getPosition() {
        return position;
    }

    public void setColor(Colors colors) {
        this.colors = colors;
    }

    public void addNeighbor(Block block) {
        this.neighbors.add(block);
    }

    public void setElectronCount(int electronCount) {
        this.electronCount = electronCount;
    }
}
