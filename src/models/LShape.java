package models;

import java.awt.*;
/**
 * LShape.java:
 * Creates a LShaped tetronimo
 *
 * @author Om Patel
 * @version 1.0 August 2, 2021
 *
 * @see java.awt.Point
 */
public class LShape extends Tetronimo{

    /**
     * Constructor
     * creates a LShaped Piece
     */
    public LShape() {
        super.r1.setLocation(0, 0);
        super.r2.setLocation(0, Tetronimo.SIZE);
        super.r3.setLocation(0, Tetronimo.SIZE * 2);
        super.r4.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2);

        super.add(r1);
        super.add(r2);
        super.add(r3);
        super.add(r4);
    }

    /**
     * rotates the piece according to rotation
     */
    @Override
    public void rotate()
    {
        super.rotate();

        Point curLoc = super.getLocation();
        super.setLocation( 0, 0 );

        switch(super.curRotation % 4) {
            case 0: //after 3 clicks
                super.r1.setLocation(0, Tetronimo.SIZE);
                super.r2.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r3.setLocation(0, Tetronimo.SIZE * 2);
                super.r4.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE);
                break;
            case 1: //base
                super.r1.setLocation(0, 0);
                super.r2.setLocation(0, Tetronimo.SIZE);
                super.r3.setLocation(0, Tetronimo.SIZE * 2);
                super.r4.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2);
                break;
            case 2: //after 1 click
                super.r1.setLocation(0, Tetronimo.SIZE);
                super.r2.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r3.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE);
                super.r4.setLocation(Tetronimo.SIZE * 2, 0);
                break;
            case 3: // after 2 clicks
                super.r1.setLocation(0, 0);
                super.r2.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                super.r3.setLocation(Tetronimo.SIZE, 0);
                super.r4.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2);
                break;
        }


        super.setLocation( curLoc );
    }

    /**
     * Gets the height of the tetronimo based on the orientation
     *
     * @return The height of the tetronimo
     */
    @Override
    public int getHeight()
    {
        if( this.curRotation % 2 == 0 )
        {
            return Tetronimo.SIZE * 2;
        }
        else
        {
            return Tetronimo.SIZE * 3;
        }
    }

    /**
     * Gets the width of the tetronimo based on the orientation
     *
     * @return The width of the tetronimo
     */
    @Override
    public int getWidth()
    {
        if( this.curRotation % 2 == 0 )
        {
            return Tetronimo.SIZE * 3;
        }
        else
        {
            return Tetronimo.SIZE * 2;
        }
    }
}
