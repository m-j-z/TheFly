package nonmoving;

import java.awt.image.BufferedImage;

/**
 * A class that defines the objects that the Player must avoid.
 */
public class Punishment extends NonMoving{

    /**
     * Constructor of a Punishment object
     * @param x         the initial x coordinate
     * @param y         the initial y coordinate
     * @param value     point value to decrease
     * @param img       a BufferedImage object that defines the rendered image of the object.
     * @see BufferedImage
     */
    public Punishment(int x, int y, int value, BufferedImage img) {
        super(x, y, value, img);
    }
}
