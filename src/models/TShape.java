package models;

import javax.smartcardio.TerminalFactory;
import java.awt.*;

/**
 * TShape.java:
 * Creates a TShape tetronimo
 *
 * @author Om Patel
 * @version 1.0 August 2, 2021
 *
 * @see java.awt.Point
 */
public class TShape extends Tetronimo{

    /**
     * Constructor
     * creates the TShaped piece
     */
    public TShape() {
        super.r1.setLocation(0, 0);
        super.r2.setLocation(Tetronimo.SIZE, 0);
        super.r3.setLocation(Tetronimo.SIZE * 2, 0);
        super.r4.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);

        super.add(r1);
        super.add(r2);
        super.add(r3);
        super.add(r4);
    }

    /**
     * rotates the TShaped piece according to current rotation
     */
    @Override
    public void rotate()
    {
        super.rotate();

        Point curLoc = super.getLocation();
        super.setLocation( 0, 0 );


        switch(super.curRotation % 4) {
            case 0: // 3 clicks
                super.r1.setLocation( 0, Tetronimo.SIZE);
                super.r2.setLocation( Tetronimo.SIZE, 0);
                super.r3.setLocation( Tetronimo.SIZE, Tetronimo.SIZE );
                super.r4.setLocation( Tetronimo.SIZE, Tetronimo.SIZE * 2 );
                break;
            case 1: // base
                super.r1.setLocation(0, Tetronimo.SIZE);
                super.r2.setLocation(Tetronimo.SIZE, Tetronimo.SIZE * 2);
                super.r3.setLocation(Tetronimo.SIZE * 2, Tetronimo.SIZE);
                super.r4.setLocation(Tetronimo.SIZE, Tetronimo.SIZE);
                break;
            case 2: // one click
                super.r1.setLocation( 0, 0);
                super.r2.setLocation( 0, Tetronimo.SIZE * 2);
                super.r3.setLocation( 0, Tetronimo.SIZE );
                super.r4.setLocation( Tetronimo.SIZE, Tetronimo.SIZE);
                break;
            case 3: // two click
                super.r1.setLocation( 0, Tetronimo.SIZE);
                super.r2.setLocation( Tetronimo.SIZE, 0);
                super.r3.setLocation( Tetronimo.SIZE, Tetronimo.SIZE );
                super.r4.setLocation( Tetronimo.SIZE * 2, Tetronimo.SIZE);
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
            return Tetronimo.SIZE * 3;
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
            return Tetronimo.SIZE * 3;
        }
    }
}
