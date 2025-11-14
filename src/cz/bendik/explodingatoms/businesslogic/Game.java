package cz.bendik.explodingatoms.businesslogic;

public class Game {
    private final Grid grid = new Grid();
    private Colors currentPlayer;
    private boolean isFinished = false;
    private Colors winner;
    private int turn;

    public void startNewGame() {
        isFinished = false;
        winner = null;
        currentPlayer = Colors.BLUE; // Začne vždy modrý
        turn = 0;
        grid.prepareGame();
    }

    public void gameRound(Block block) {
        if(isFinished) return;

        if(block.getColor() == Colors.GRAY || block.getColor() == currentPlayer) {
            grid.addElectron(block, currentPlayer);
            currentPlayer = (currentPlayer == Colors.BLUE) ? Colors.RED : Colors.BLUE; // Otočit hráče
            checkGameState();
            turn++;
        }
    }

    public void checkGameState() {
        int blue = 0;
        int red = 0;

        for(Block block : grid.getBlocks()) {
            if(block.getColor() == Colors.BLUE) {
                blue++;
            }
            else if(block.getColor() == Colors.RED) {
                red++;
            }
        }

        if(turn > 2 ) { // Oba hráči zahráli aspoň jednou
            if (blue > 0 && red == 0) {
                isFinished = true;
                winner = Colors.BLUE;
            } else if (red > 0 && blue == 0) {
                isFinished = true;
                winner = Colors.RED;
            }
        }
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public Grid getGrid() {
        return grid;
    }

    public Colors getWinner() {
        return winner;
    }
}
