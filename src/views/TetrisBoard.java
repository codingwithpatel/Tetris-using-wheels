package views;

import controllers.TetrisController;
import javafx.event.EventHandler;
import models.*;
import wheelsunh.users.*;
import wheelsunh.users.Frame;
import wheelsunh.users.Rectangle;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.Array;
import java.util.LinkedList;
import java.util.Random;

/**
 * TetrisBoard.java:
 * Class to model the tetris board
 *
 * @author Om Patel
 * @version 1.0 August 2, 2021
 *
 * @see java.awt.Color
 * @see java.awt.event.KeyListener
 * @see java.awt.event.KeyEvent
 */
public class TetrisBoard implements KeyListener
{
    /**
     * Constant to represent the width of the board
     */
    public static final int WIDTH = 10;

    /**
     * Constant to represnet the height of the board
     */
    public static final int HEIGHT = 24;

    /**
     * Controller object
     */
    private final TetrisController CONTROLLER;

    /**
     * Tetronimo object used to represent the current piece used
     */
    private Tetronimo tetronimo;

    /**
     * next tetronimo object to represent the next piece
     */
    private Tetronimo nextTetronimo;

    /**
     * This is the playing board
     */
    private Rectangle[][] playingField;

    /**
     * used to notify user which piece is going to come next
     */
    private Rectangle blockTracker;

    /**
     * Used to store the players score
     */
    private Rectangle scoreBoard;

    /**
     * used to check if game is over
     */
    private boolean isGameOver;

    /**
     * used to store the score so we can print it on screen
     */
    private TextBox scoreText;



    /**
     * Constructor to initialize the board
     *
     * @param frame The wheelsunh frame (so we can add this class as a key listener for the frame)
     */
    public TetrisBoard( Frame frame )
    {
        frame.addKeyListener( this );
        this.CONTROLLER = new TetrisController( this );

        scoreText = new TextBox(310, 260);

        this.buildBoard();

        this.run();
    }

    /**
     * Builds the playing field for tetris
     */
    private void buildBoard()
    {
        this.playingField = new Rectangle[ WIDTH ][ HEIGHT ];

        for ( int i = 0; i < TetrisBoard.WIDTH; i++ )
        {
            for ( int j = 0; j < TetrisBoard.HEIGHT; j++ )
            {
                this.playingField[ i ][ j ] = new Rectangle();
                this.playingField[ i ][ j ].setLocation( i * 20 + 40, j * 20 );
                this.playingField[ i ][ j ].setSize( Tetronimo.SIZE, Tetronimo.SIZE );
                this.playingField[ i ][ j ].setColor( Color.WHITE );
                this.playingField[ i ][ j ].setFrameColor( Color.BLACK );
            }
        }
    }

    /**
     * Starts gameplay and is responsible for keeping the game going
     */
    public void run()
    {
        //creates the next piece and score board
        createTrackers();

        // creates the first piece
        this.tetronimo = this.CONTROLLER.getNextTetromino();
        tetronimo.setLocation(40 + (5 * Tetronimo.SIZE), 0);

        //isGameOver is initially false
        isGameOver = false;

        /*
        this loop gets the next tetronimo and puts it in nextpiece block and gives the player half a second grace period to move the piece (panic moment)
        then moves the piece down and once it lands it locks it in place and checks if a row has filled up then updates board and sets nextTetronimo to current tetronimo
         */
        while(true) {
            this.nextTetronimo = this.CONTROLLER.getNextTetromino();
            nextTetronimo.setLocation(325, 105);
            Utilities.sleep(500);
            isGameOver =  this.CONTROLLER.checkIfGameIsOver(tetronimo);
            printScore();

            if(isGameOver) {
                break;
            }

            while(!this.CONTROLLER.tetronimoLanded(this.tetronimo))
            {
                this.tetronimo.setLocation( this.tetronimo.getXLocation(), this.tetronimo.getYLocation() + Tetronimo.SIZE );
                Utilities.sleep( 500 );
            }

            this.CONTROLLER.lockPiece(tetronimo);
            this.CONTROLLER.checkIfRowNeedsDeletion();
            updateBoardColor();
            tetronimo.setLocation(-500, -500);

            tetronimo = nextTetronimo;
            tetronimo.setLocation(40 + (5 * Tetronimo.SIZE), 0);
        }

    }

    /**
     * used to update the board once a piece has landed and color that box on the playing field
     */
    public void updateBoardColor() {
        for(int i = 0; i < this.CONTROLLER.collisionBoard.length; i++) {
            for(int j = 0; j < this.CONTROLLER.collisionBoard[i].length; j++) {
                this.playingField[i][j].setColor(this.CONTROLLER.collisionBoard[i][j] ? Color.BLUE : Color.WHITE);
                playingField[i][j].setFrameColor(Color.BLACK);
            }
        }
    }

    /**
     * Getter method for the array representing the playing field, not used yet but will be needed by the controller later
     *
     * @return The playing field
     */
    public Rectangle[][] getPlayingField()
    {
        return playingField;
    }

    /**
     * This method is not used in this program
     *
     * @param e The key event
     */
    @Override
    public void keyTyped( KeyEvent e )
    {
        //not in use
    }

    /**
     * Handles the key events by the user
     *
     * @param e The key event
     */
    @Override
    public void keyPressed( KeyEvent e )
    {
        int key = e.getKeyCode();

        if( this.tetronimo == null || isGameOver )
        {
            return;
        }

        switch( key )
        {
            case 38: //if player wants to rotate if you can
                this.tetronimo.rotate();
                if(!this.CONTROLLER.tetronimoCanMove(tetronimo, 0, 0)) {
                    this.tetronimo.rotate();
                    this.tetronimo.rotate();
                    this.tetronimo.rotate();
                }
                break;
            case 37: // if the left key is pressed move left if you can
                if( this.tetronimo.getXLocation() - Tetronimo.SIZE >= 40 )
                {
                    if(this.CONTROLLER.tetronimoCanMove(tetronimo, 0, -1)) {
                        this.tetronimo.shiftLeft();
                    }
                }
                break;
            case 39: // if right key is pressed move right if you can
                if( (this.tetronimo.getXLocation() + this.tetronimo.getWidth()) <
                        ((TetrisBoard.WIDTH * Tetronimo.SIZE) + 40))
                {
                    if(this.CONTROLLER.tetronimoCanMove(tetronimo, 0, 1)) {
                        this.tetronimo.shiftRight();
                    }
                }
                break;
            case 40: // is down arrow is held moves piece down quicker
                if(!this.CONTROLLER.tetronimoLanded( this.tetronimo )) {
                    this.tetronimo.setLocation( this.tetronimo.getXLocation(), this.tetronimo.getYLocation() + Tetronimo.SIZE );
                }
                break;
            case 32: //if space is pressed move the piece all the way down
                while (!this.CONTROLLER.tetronimoLanded( this.tetronimo )) {
                    this.tetronimo.setLocation( this.tetronimo.getXLocation(), this.tetronimo.getYLocation() + Tetronimo.SIZE );
                }
                break;
        }

    }

    /**
     * This method is not used in this program
     *
     * @param e The key event
     */
    @Override
    public void keyReleased( KeyEvent e )
    {
        //not in use
    }

    /**
     * creates the trackers
     */
    public void createTrackers() {

        //creates the next piece block
        blockTracker = new Rectangle();
        blockTracker.setLocation(300, 80);
        blockTracker.setColor(Color.BLACK);
        blockTracker.setSize(100, 125);

        //creates score board
        scoreBoard = new Rectangle();
        scoreBoard.setSize(100, 50);
        scoreBoard.setColor(Color.BLACK);
        scoreBoard.setLocation( 300, 250);
    }

    /**
     * prints the score of player
     */
    public void printScore() {
        //prints the score on top of score board
        int gameScore = this.CONTROLLER.gameScore;
        String totalGameScore = "Score: " + gameScore;

        scoreText.setText(totalGameScore);
        scoreText.setSize(80, 25);
    }
}