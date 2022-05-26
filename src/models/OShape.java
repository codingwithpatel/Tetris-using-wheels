package models;

import java.awt.*;

/**
 * OShape.java:
 * Creates a square tetronimo
 *
 * @author Om Patel
 * @version 1.0 August 2, 2021
 *
 * @see java.awt.Point
 */
public class OShape extends Tetronimo{
    /**
     * creates a square piece
     */
    public OShape() {
        super.r1.setLocation(0,0);
        super.r2.setLocation(0, Tetronimo.SIZE);
        super.r3.setLocation(Tetronimo.SIZE, 0);
        super.r4.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);

        super.add(r1);
        super.add(r2);
        super.add(r3);
        super.add(r4);
    }

    /**
     * rotates the square (Not really needed since square has same appearance when rotated)
     */
    @Override
    public void rotate()
    {
        super.rotate();
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
            return Tetronimo.SIZE * 2;
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
            return Tetronimo.SIZE * 2;
        }
        else
        {
            return Tetronimo.SIZE * 2;
        }
    }
}
