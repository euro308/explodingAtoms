package cz.bendik.explodingatoms.presentation;

import cz.bendik.explodingatoms.businesslogic.*;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class ExplodingAtomsGUI {
    private final JFrame frame;
    private final Game game;
    private JButton[][] buttons;
    private ColorRatioBar ratioBar;
    private final int gridSize;

    public ExplodingAtomsGUI() {
        game = new Game();
        game.startNewGame();
        frame = new JFrame("ExplodingAtoms");
        gridSize = game.getGrid().getGridSize();
        initColorMap();
        createBoard();
    }

    private final Map<gameColors, Color> colorMap = new EnumMap<>(gameColors.class);

    private void initColorMap() {
        colorMap.put(gameColors.BLUE, new Color(137, 207, 240));
        colorMap.put(gameColors.RED, new Color(250, 160, 160));
        colorMap.put(gameColors.GRAY, new Color(200, 200, 200));
    }

    private static class ColorRatioBar extends JPanel {
        private int bluePercent = 0;
        private String text = "0% Modrý / 0% Červený";

        public ColorRatioBar() {
            setPreferredSize(new Dimension(600, 30));
        }

        public void setRatio(int blue, int red) {
            int total = blue + red;
            if (total == 0) {
                bluePercent = 0;
                text = "0% Modrý / 0% Červený";
            } else {
                bluePercent = (int) Math.round((blue * 100.0) / total);
                int redPercent = 100 - bluePercent;
                text = bluePercent + "% Modrý / " + redPercent + "% Červený";
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int width = getWidth();
            int height = getHeight();

            // Vypočítej šířky podle procent
            int blueWidth = (int) ((width * bluePercent) / 100.0);
            int redWidth = width - blueWidth;

            // Modrá část zleva
            g.setColor(new Color(137, 207, 240));
            g.fillRect(0, 0, blueWidth, height);

            // Červená část zprava
            g.setColor(new Color(250, 160, 160));
            g.fillRect(blueWidth, 0, redWidth, height);

            // Text uprostřed
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textX = (width - textWidth) / 2;
            int textY = (height + fm.getAscent()) / 2 - 2;
            g.drawString(text, textX, textY);
        }
    }

    private void createBoard() {
        frame.setLayout(new BorderLayout());

        // Hrací plocha uprostřed
        JPanel boardPanel = new JPanel(new GridLayout(gridSize, gridSize));
        buttons = new JButton[gridSize][gridSize];

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                JButton button = new JButton();

                final int currentRow = row;
                final int currentCol = col;

                button.addActionListener(event -> {
                    Block block = game.getGrid().getBlocks2D()[currentRow][currentCol];
                    game.gameRound(block);
                    updateBoard();
                });

                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);

        // Barevný bar dole
        ratioBar = new ColorRatioBar();
        frame.add(ratioBar, BorderLayout.SOUTH);  // <-- ZMĚŇ na SOUTH

        frame.setSize(600, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        updateBoard();
    }

    private void updateBoard() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Block block = game.getGrid().getBlocks2D()[row][col];

                int electronCount = block.getElectronCount();
                buttons[row][col].setText(electronCount > 0 ? String.valueOf(electronCount) : "");
                buttons[row][col].setBackground(colorMap.get(block.getGameColor()));
            }
        }

        // Spočítej poměr modrých a červených
        int blue = 0;
        int red = 0;

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Block block = game.getGrid().getBlocks2D()[row][col];
                if (block.getGameColor() == gameColors.BLUE) {
                    blue++;
                } else if (block.getGameColor() == gameColors.RED) {
                    red++;
                }
            }
        }

        ratioBar.setRatio(blue, red);  // <-- ZMĚŇ volání

        if (game.getIsFinished()) {
            JOptionPane.showMessageDialog(frame,
                    "Vyhrál " + (game.getWinner() == gameColors.BLUE ? "Modrý" : "Červený") + "!");
            resetBoard();
        }
    }

    private void resetBoard() {
        game.startNewGame();
        updateBoard();
    }
}
