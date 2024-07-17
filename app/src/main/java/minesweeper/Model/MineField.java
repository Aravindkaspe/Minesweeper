package minesweeper.Model;

import minesweeper.Controller.Observer;

import java.util.Random;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.net.URL;

public class MineField {
    protected int rowSize;
    protected int columnSize;
    public int totalMinesCount;
    public ArrayList<Block> minesList = new ArrayList<>();
    public Block[][] blocksArray;
    protected Random random = new Random();
    protected int blocksOpened;
    public boolean gameOver = false;
    protected Observer myObserver;

    public MineField(int rSize, int cSize, int totalMinesCount) {
        this.rowSize = rSize;
        this.columnSize = cSize;
        this.totalMinesCount = totalMinesCount;
        this.blocksArray = new Block[rowSize][columnSize];
        this.placeMines(totalMinesCount);
        this.blocksOpened = 0;
    }

    public int getRowSize() {
        return rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }


    public void placeMines(int totalMinesCount) {
        int minesPlaced = 0;
        while (minesPlaced < totalMinesCount) {
            int r = random.nextInt(rowSize);
            int c = random.nextInt(columnSize);
            if (blocksArray[r][c] == null) {
                blocksArray[r][c] = new Block(r, c);
                minesList.add(blocksArray[r][c]);
                minesPlaced++;
            }
        }
    }

    public void checkMines(int r, int c) {

        if (r < 0 || r >= rowSize || c < 0 || c>= columnSize) {
            return;
        }

        Block block = blocksArray[r][c];
        if (!block.isEnabled()) {
            return;
        }
        block.setEnabled(false);
        blocksOpened += 1;

        int minesCount = 0;
        
        minesCount += countMines(r-1, c-1);
        minesCount += countMines(r-1, c);
        minesCount += countMines(r-1, c+1);
        minesCount += countMines(r, c-1);
        minesCount += countMines(r, c+1);
        minesCount += countMines(r+1, c-1);
        minesCount += countMines(r+1, c);
        minesCount += countMines(r+1, c+1);

        if (minesCount > 0) {
            block.setText(String.valueOf(minesCount));
        } else {
            block.setText("");

            checkMines(r-1, c-1);
            checkMines(r-1, c);
            checkMines(r-1, c+1);
            checkMines(r, c-1);
            checkMines(r, c+1);
            checkMines(r, c);
            checkMines(r+1, c-1);
            checkMines(r+1, c);
            checkMines(r+1, c+1);
        }
    }

    public int countMines(int r, int c) {
        
        if (r < 0 || r >= rowSize || c < 0 || c>= columnSize) {
            return 0;
        } if (minesList.contains(blocksArray[r][c])) {
            return 1;
        }
        return 0;
    }

    public void revealMines() {
        URL imageUrl = getClass().getResource("/bomb.jpg");
        ImageIcon bombIcon = new ImageIcon(imageUrl);

        int iconWidth = 50;
        int iconHeight = 50;
        Image image = bombIcon.getImage().getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        ImageIcon newBombIcon = new ImageIcon(image);

        for (int i = 0; i < minesList.size(); i++) {
            Block block = minesList.get(i);
            block.setIcon(newBombIcon);
            block.setBackground(Color.RED);
        }
        myObserver.update("Game Over!, Try Again.");
        gameOver = true;
    }

    public void addObserver(Observer observer) {
        this.myObserver = observer;
    }

    public int getMines() {
        return this.totalMinesCount;
    }
}
