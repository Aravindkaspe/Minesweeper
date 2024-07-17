package minesweeper.View;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

import minesweeper.Model.*;
import minesweeper.Controller.*;



public class GameGUI {
    JFrame frame;
    GamePanel gameModePanel;
    
    public void run() {
        JFrame gameFrame = new JFrame("Minesweeper");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
    
            
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 30));
        titleLabel.setText("Minesweeper");
            
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        titlePanel.add(titleLabel);
        titlePanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        JButton easyMode = new JButton("Easy");
        JButton normalMode = new JButton("Normal");
        JButton hardMode = new JButton("Hard");

        
        menuPanel.add(Box.createVerticalStrut(20)); 
        easyMode.setAlignmentX(Component.CENTER_ALIGNMENT); 
        menuPanel.add(easyMode);
        menuPanel.add(Box.createVerticalStrut(10)); 
        normalMode.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(normalMode);
        menuPanel.add(Box.createVerticalStrut(10)); 
        hardMode.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(hardMode);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.add(titlePanel);
        containerPanel.add(menuPanel);
        containerPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        
        gameFrame.getContentPane().add(containerPanel);

        gameFrame.setPreferredSize(new Dimension(600, 800));
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);


        easyMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                gameModePanel = new GamePanel();
                MineField game = new MineField(5,5,3);
                game.addObserver(gameModePanel);
                JPanel gamePanel = gameModePanel.createPanel("easy", game);
                replacePanel(containerPanel, gamePanel);
            }
        });

        normalMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameModePanel = new GamePanel();
                MineField game = new MineField(7,7,7);
                game.addObserver(gameModePanel);
                JPanel gamePanel = gameModePanel.createPanel("normal", game);
                replacePanel(containerPanel, gamePanel);
            }
        });

        hardMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameModePanel = new GamePanel();
                MineField game = new MineField(11,11,20);
                Observer observer = new GamePanel(); 
                game.addObserver(observer);
                JPanel gamePanel = gameModePanel.createPanel("hard", game);
                replacePanel(containerPanel, gamePanel);
            }
        });
    }

    private static void replacePanel(JPanel containerPanel, JPanel newPanel) {
        containerPanel.removeAll();
        containerPanel.add(newPanel);
        containerPanel.revalidate();
        containerPanel.repaint();
    }

}
