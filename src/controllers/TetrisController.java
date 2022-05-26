package controllers;

import models.*;
import views.TetrisBoard;

import java.awt.*;
import java.util.Random;

/**
 * TetrisController.java:
 * Class to hold all of the game logic for tetris
 *
 * @author Om Patel
 * @version 1.0 August 2, 2021
 */
public class TetrisController
{
    /**
     * creates a TetrisBoard object
     */
    private final TetrisBoard TETRIS_BOARD;

    /**
     * 2D boolean Array to check collisions
     */
    public boolean[][] collisionBoard;

    /**
     * used to choose a piece randomly
     */
    public static Random randomNumberChooser = new Random();

    /**
     * used to hold player score
     */
    public int gameScore;

    /**
     * random number chosen from 0-4 so 5 peices it can choose from
     */
    public static final int tetrominoNumber = 7;

    /**
     * Constructor to take in a tetris board so the controller and the board can communciate
     *
     * @param tetrisBoard A tetris board instance
     */
    public TetrisController( TetrisBoard tetrisBoard )
    {
        gameScore = 0; //score 0 at start
        collisionBoard = new boolean[TetrisBoard.WIDTH][TetrisBoard.HEIGHT]; //collision array is big as the game board
        this.TETRIS_BOARD = tetrisBoard;


        for(int i = 0; i < collisionBoard.length; i++) { //set collision array to false initially
            for(int j = 0; j < collisionBoard[i].length; j++) {
                collisionBoard[i][j] = false;
            }
        }
    }

    /**
     * Randomly chooses the next tetronimo and returns it
     *
     * @return The next tetronimo to be played
     */
    public Tetronimo getNextTetromino()
    {
        Tetronimo tetronimo;

        int blockKey = randomNumberChooser.nextInt(tetrominoNumber);
        switch (blockKey) {
            case 0: //if random number chosen is 0, piece up is a StraightShape
                tetronimo = new StraightShape();
                return tetronimo;
            case 1://if random number chosen is 1, piece up is a OShape
                tetronimo = new OShape();
                return tetronimo;
            case 2: //if random number chosen is 2, piece up is a TShape
                tetronimo = new TShape();
                return tetronimo;
            case 3://if random number chosen is 3, piece up is a LShape
                tetronimo = new LShape();
                return tetronimo;
            case 5:
                tetronimo = new JShape();
                return tetronimo;
            case 6:
                tetronimo = new ZShape();
                return  tetronimo;
            case 4://if random number chosen is 4 or default, piece up is a SShape
            default:
                tetronimo = new SShape();
                return tetronimo;
        }

    }

    /**
     * Method to determine if the tetronimo has landed
     *
     * @param tetronimo The tetronimo to evaluate
     * @return True if the tetronimo has landed (on the bottom of the board or another tetronimo), false if it has not
     */
    public boolean tetronimoLanded( Tetronimo tetronimo )
    {
        // if bottom of board is reached return true
        int nextY = tetronimo.getYLocation() + tetronimo.getHeight() + Tetronimo.SIZE;
        if(nextY > 480) {
            return true;
        }

        // or return if a tetronimo can move
        return !tetronimoCanMove(tetronimo, 1, 0);
    }

    /**
     * when a piece lands, set collision board to true for those objects
     * @param piece current piece thats up
     */
    public void lockPiece(Tetronimo piece) {
        Point[] blocks = piece.getRectangleLocations();

        // uses point array to set all blocks of a piece to true
        for(int i = 0; i < blocks.length; i++) {
            int row = turnYAxisToRow(blocks[i].y);
            int col = turnXAxisToCol(blocks[i].x);

            collisionBoard[col][row] = true;
        }
    }

    /**
     * turns given y value to row
     * @param yValue y value of given block
     * @return row of block
     */
    public int turnYAxisToRow(int yValue) {
        int row = yValue / 20; // calculates the row
        return row;
    }

    /**
     * turns given x value to col
     * @param xValue x value of given block
     * @return col of block
     */
    public int turnXAxisToCol(int xValue) {
        int col = (xValue - 40) / 20; // calculates the col
        return col;
    }

    /**
     * checks if the piece can move all the way down or has collided with a piece
     * @param tetronimo current piece
     * @param r // which row to check
     * @param c // which col to check
     * @return true or false if it can move
     */
    public boolean tetronimoCanMove(Tetronimo tetronimo, int r, int c) {
        Point[] blocks = tetronimo.getRectangleLocations();

        for(int i = 0; i < blocks.length; i++) {
            int row = turnYAxisToRow(blocks[i].y) + r;
            int col = turnXAxisToCol(blocks[i].x) + c;

            if(col > 9) { //checks out of bounds of field
                return false;
            } else if(row > 23) {
                return false;
            }

            if(collisionBoard[col][row]) { //if a peice is already there we cant move
                return false;
            }
        }
        return true;
    }

    /**
     * deletes the row that is filled
     * @param row which row is filled
     */
    public void deleteRow(int row) {
        for(int rowCounter = row; rowCounter > 0; rowCounter--) {
            for(int col = 0; col < TetrisBoard.WIDTH; col++) {
                collisionBoard[col][rowCounter] = collisionBoard[col][rowCounter - 1];
            }
        }
        for(int col = 0; col < TetrisBoard.WIDTH; col++) {
            collisionBoard[col][0] = false;
        }
    }

    /**
     * used to see if row has been filled and deletes it
     */
    public void checkIfRowNeedsDeletion() {
        int rowsRemovedCounter = 0;
        /*
        checks if row is filled and sets it to true or false
         */
        for(int row = TetrisBoard.HEIGHT - 1; row >= 0; row--) {
            boolean rowIsFilled = true;
            for(int col = 0; col < TetrisBoard.WIDTH; col++) {
                rowIsFilled = rowIsFilled && collisionBoard[col][row];
            }
            if(rowIsFilled) { // if true, delete the row, and increase the gameScore by 100
                deleteRow(row);
                rowsRemovedCounter++;
                gameScore = gameScore + 100;
                row++;
            }
        }
        if(rowsRemovedCounter == 4) { //if 4 rows are deleted at the same time add 400 points more
            gameScore = gameScore + 400;
        }
    }

    /**
     * checks if the game is over
     * @param currentPiece piece currently in use
     * @return true or false if it can
     */
    public boolean checkIfGameIsOver(Tetronimo currentPiece) {

        return (!tetronimoCanMove(currentPiece, 0 ,0));

    }

}
