package cz.bendik.explodingatoms.businesslogic;

import java.util.ArrayList;

public class Grid {
    private final ArrayList<Block> blocks = new ArrayList<>();
    private final int gridSize = 8; // 8 krát 8 je standardní plocha

    public void prepareGame() {
        blocks.clear(); // Vyčistí pole v případě další hry (hráč spustí hru znovu)

        for (int i = 0; i < (gridSize * gridSize); i++) { // Naplnit pole prázdnými bloky
            blocks.add(new Block(Colors.GRAY, new ArrayList<>(), 0, i));
        }

        for(Block block : blocks) { // Nastavit sousedy
            int pos = block.getPosition();
            int row =  pos / gridSize;
            int col =  pos % gridSize;

            if(row > 0) { // Nahoru
                block.addNeighbor(blocks.get(pos - gridSize));
            }
            if(row < gridSize - 1) { // Dolů
                block.addNeighbor(blocks.get(pos + gridSize));
            }
            if(col > 0) { // Doleva
                block.addNeighbor(blocks.get(pos - 1));
            }
            if(col < gridSize - 1) {
                block.addNeighbor(blocks.get(pos + 1));
            }
        }
    }

    public void addElectron(Block block, Colors playerColors) {
        if(block.getElectronCount() == 0) { // Pokud je pole prázdné
            block.setColor(playerColors);
        }

        block.setElectronCount(block.getElectronCount() + 1);

        if((block.getElectronCount()) >= block.getNeighbors().size()) { // Výbuch
            explode(block, playerColors);
        }
    }

    public void explode(Block block, Colors playerColors) {
        block.setElectronCount(0);

        for(Block neighbor : block.getNeighbors()) {
            neighbor.setColor(playerColors); // Změní barvu i v případě, že byl atom protihráčův
            addElectron(neighbor, playerColors);
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public int getGridSize() {
        return gridSize;
    }
}
