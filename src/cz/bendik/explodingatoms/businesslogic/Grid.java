package cz.bendik.explodingatoms.businesslogic;

import java.util.ArrayList;

public class Grid {
    private final int gridSize = 8; // 8 krát 8 je standardní plocha
    private final Block[][] blocks = new Block[gridSize][gridSize];

    public void prepareGame() {
        for (int row = 0; row < gridSize; row++) { // Naplnit pole prázdnými bloky
            for (int col = 0; col < gridSize; col++) {
                blocks[row][col] = new Block(gameColors.GRAY, new ArrayList<>(), 0, row, col);
            }
        }

        for (int row = 0; row < gridSize; row++) { // Nastavit sousedy
            for (int col = 0; col < gridSize; col++) {
                Block block = blocks[row][col];

                if (row > 0)               block.addNeighbor(blocks[row - 1][col]);
                if (row < gridSize - 1)    block.addNeighbor(blocks[row + 1][col]);
                if (col > 0)               block.addNeighbor(blocks[row][col - 1]);
                if (col < gridSize - 1)    block.addNeighbor(blocks[row][col + 1]);
            }
        }
    }

    public void addElectron(Block block, gameColors playerGameColors) {
        if (block.getElectronCount() == 0) { // Pokud je pole prázdné
            block.setColor(playerGameColors);
        }

        block.setElectronCount(block.getElectronCount() + 1);

        if ((block.getElectronCount()) >= block.getNeighbors().size()) { // Výbuch
            explode(block, playerGameColors);
        }
    }

    public void explode(Block block, gameColors playerGameColors) {
        block.setElectronCount(0);
        block.setColor(gameColors.GRAY);

        for (Block neighbor : block.getNeighbors()) {
            neighbor.setColor(playerGameColors); // Změní barvu i v případě, že byl atom protihráčův
            addElectron(neighbor, playerGameColors);
        }
    }

    public Block[][] getBlocks2D() {
        return blocks;
    }

    public int getGridSize() {
        return gridSize;
    }
}
