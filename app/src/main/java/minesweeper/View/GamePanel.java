package minesweeper.View;

import minesweeper.Model.Block;
import minesweeper.Model.MineField;
import minesweeper.Controller.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements Observer{
    protected JPanel gamePanel;
    protected JPanel modeLabelPanel;
    protected JLabel modeLabel;
    protected JPanel containerPanel;
    protected JPanel labelsPanel;
    protected JPanel timeScoreLabelPanel;
    protected JLabel timeScoreLabel;
    JLabel timeLabel;

    public GamePanel() {
        containerPanel = new JPanel(new BorderLayout()); 
        labelsPanel = new JPanel(new BorderLayout()); // Panel for labels
        modeLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        timeScoreLabelPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        modeLabel = new JLabel();
        timeScoreLabel = new JLabel("Time: 00:00:00");
    }

    public JPanel createPanel(String mode, MineField game) {
        modeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        modeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        modeLabel.setText("Mines: " + game.getMines());
        modeLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        modeLabelPanel.add(modeLabel); // Add modeLabel to modeLabelPanel
        timeScoreLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        timeScoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        timeScoreLabelPanel.add(timeScoreLabel);
        
        labelsPanel.add(modeLabelPanel, BorderLayout.CENTER); // Add modeLabelPanel to the left
        labelsPanel.add(timeScoreLabelPanel, BorderLayout.EAST); // Add highScoreLabelPanel to the right
        containerPanel.add(labelsPanel, BorderLayout.NORTH); // Add labelsPanel to the top

        if (mode.equals("easy")) {
            gamePanel = new JPanel(new GridLayout(5, 5, 2, 2)); // Change layout to GridLayout
        } else if (mode.equals("normal")) {
            gamePanel = new JPanel(new GridLayout(7, 7, 2, 2)); // Change layout to GridLayout
        } else {
            gamePanel = new JPanel(new GridLayout(11, 11, 2, 2)); // Change layout to GridLayout
            gamePanel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 40));
        }

        for (int i = 0; i < game.getRowSize(); i++) {
            for (int j = 0; j < game.getColumnSize(); j++) {
                Block block = new Block(i, j);

                if (game.blocksArray[i][j] == null) {
                    game.blocksArray[i][j] = block;
                } else {
                    block = game.blocksArray[i][j];
                }
        
                block.setFocusable(false);
                if (!mode.equals("hard")) {
                    block.setFont(new Font("Arial Unicode MS", Font.PLAIN, 40)); 
                } else {
                    block.setFont(new Font("Arial Unicode MS", Font.PLAIN, 15));
                }
                block.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (game.gameOver == true) {
                            return;
                        }
                        Block block = (Block) e.getSource();
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            
                            if (block.getText().equals("")) {
                                if (game.minesList.contains(block)) {
                                    game.revealMines();
                                    // modeLabel.setText("Game Over!, Try again");
                                    modeLabel.setForeground(Color.RED);
                                } else {
                                    game.checkMines(block.rows, block.columns);
                                }
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            if (block.getText().equals("") && block.isEnabled()) {
                                game.totalMinesCount -= 1;
                                modeLabel.setText("Mines: " + game.totalMinesCount);
                                block.setText("🚩");
                            } else if (block.getText().equals("🚩")) {
                                game.totalMinesCount += 1;
                                modeLabel.setText("Mines: " + game.totalMinesCount);
                                block.setText("");
                            }
                        }
                    }
                });

                gamePanel.add(block); 
            }
        }

        containerPanel.add(gamePanel, BorderLayout.CENTER); 
        return containerPanel;
    }

    public void update(String result) {
        modeLabel.setText(result);
    }

}