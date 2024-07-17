package minesweeper.Model;

import javax.swing.*;

public class Block extends JButton{
    public int rows;
    public int columns;
    public Block(int r, int c) {
        this.rows = r;
        this.columns = c;
    }
}
