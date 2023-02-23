package nonmoving;

import java.awt.image.BufferedImage;

/**
 * A class that defines the object a player must collect.
 */
public class Reward extends NonMoving{

    /**
     * Constructor of a Reward object
     * @param x         the initial x coordinate
     * @param y         the initial y coordinate
     * @param value     the point value of the Reward object
     * @param img       a BufferedImage that defines the image to render for the object
     * @see BufferedImage
     */
    public Reward(int x, int y, int value, BufferedImage img) {
        super(x, y, value, img);
    }

    /**
     * If this object is a bonus reward, decreases this object's lifetime
     * and returns whether its lifetime has become 0 or negative.
     * @param ms milliseconds per tick of the game
     * @return boolean whether this object has a 0 or negative lifetime
     */
    public boolean decreaseLifetime(long ms){
        if (bonus) {
            lifetime -= (ms / 1000.0);
            return lifetime <= 0.0;
        }
        return false;
    }
}
