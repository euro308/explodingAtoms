package cz.bendik.explodingatoms.businesslogic;

public class Game {
    private final Grid grid = new Grid();
    private gameColors currentPlayer;
    private boolean isFinished = false;
    private gameColors winner;
    private int turn;

    public void startNewGame() {
        isFinished = false;
        winner = null;
        currentPlayer = gameColors.BLUE; // Začne vždy modrý
        turn = 0;
        grid.prepareGame();
    }

    public void gameRound(Block block) {
        if(isFinished) return;

        if(block.getGameColor() == gameColors.GRAY || block.getGameColor() == currentPlayer) {
            grid.addElectron(block, currentPlayer);
            currentPlayer = (currentPlayer == gameColors.BLUE) ? gameColors.RED : gameColors.BLUE; // Otočit hráče
            checkGameState();
            turn++;
        }
    }

    public void checkGameState() {
        int blue = 0;
        int red = 0;

        for(int row = 0; row < grid.getGridSize(); row++) {
            for(int col = 0; col < grid.getGridSize(); col++) {
                Block block = grid.getBlocks2D()[row][col];

                if(block.getGameColor() == gameColors.BLUE) {
                    blue++;
                }
                else if(block.getGameColor() == gameColors.RED) {
                    red++;
                }
            }

        }

        if(turn > 2 ) { // Oba hráči zahráli aspoň jednou
            if (blue > 0 && red == 0) {
                isFinished = true;
                winner = gameColors.BLUE;
            } else if (red > 0 && blue == 0) {
                isFinished = true;
                winner = gameColors.RED;
            }
        }
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public Grid getGrid() {
        return grid;
    }

    public gameColors getWinner() {
        return winner;
    }
}
