package models;

import java.awt.*;

/**
 * SShaped.java:
 * Creates a SShaped tetronimo
 *
 * @author Om Patel
 * @version 1.0 August 2, 2021
 *
 * @see java.awt.Point
 */
public class SShape extends Tetronimo{

    /**
     * Constructor
     * Creates the SShaped piece
     */
    public SShape() {
        super.r1.setLocation(0,0);
        super.r2.setLocation(0, Tetronimo.SIZE);
        super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
        super.r4.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2);

        super.add(r1);
        super.add(r2);
        super.add(r3);
        super.add(r4);
    }

    /**
     * Rotates the piece according to current rotation
     */
    @Override
    public void rotate()
    {
        super.rotate();

        Point curLoc = super.getLocation();
        super.setLocation( 0, 0 );

        if( super.curRotation % 2 == 0 ) // first click
        {
            super.r1.setLocation( 0, Tetronimo.SIZE);
            super.r2.setLocation( Tetronimo.SIZE, Tetronimo.SIZE);
            super.r3.setLocation( Tetronimo.SIZE, 0 );
            super.r4.setLocation( Tetronimo.SIZE * 2, 0 );
        }
        else // base
        {
            super.r1.setLocation(0,0);
            super.r2.setLocation(0, Tetronimo.SIZE);
            super.r3.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
            super.r4.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2);
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
