package cz.bendik.explodingatoms.presentation;

import cz.bendik.explodingatoms.businesslogic.*;

import javax.swing.*;
import java.awt.*;

public class ExplodingAtomsGUI {
    private final JFrame frame;
    private final Game game;
    private JButton[][] buttons;
    private final int gridSize;

    public ExplodingAtomsGUI() {
        game = new Game();
        game.startNewGame();
        frame = new JFrame("ExplodingAtoms");
        gridSize = game.getGrid().getGridSize();
        createBoard();
    }

    private void createBoard() {
        frame.setLayout(new GridLayout(gridSize, gridSize));

        buttons = new JButton[gridSize][gridSize];
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                int position = row * gridSize + col;
                JButton button = new JButton();

                button.addActionListener(event -> {
                    Block block = game.getGrid().getBlocks().get(position);
                    game.gameRound(block);
                    updateBoard();
                });

                buttons[row][col] = button;
                frame.add(button);
            }
        }

        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        updateBoard();
    }

    private void updateBoard() {
        for (int i = 0; i < (gridSize * gridSize); i++) {
            Block block = game.getGrid().getBlocks().get(i);
            int row = i / gridSize;
            int col = i % gridSize;

            int electronCount = block.getElectronCount();
            buttons[row][col].setText(electronCount > 0 ? String.valueOf(electronCount) : "");
            buttons[row][col].setBackground(block.getColor() == Colors.BLUE ? new java.awt.Color(137, 207, 240) : (block.getColor() == Colors.RED ? new java.awt.Color(250, 160, 160) : new java.awt.Color(200, 200, 200)));
        }

        // Zobraz aktuálního hráče a stav hry
        if (game.getIsFinished()) {
            JOptionPane.showMessageDialog(frame,
                    "Vyhrál " + (game.getWinner() == Colors.BLUE ? "Modrý" : "Červený") + "!");
            resetBoard();
        }
    }

    private void resetBoard() {
        game.startNewGame();
        updateBoard();
    }
}
