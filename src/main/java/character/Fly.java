package character;

import java.awt.image.BufferedImage;

/**
 * A Player controlled Fly object.
 */
public class Fly extends Character{
    /**
     * Constructor
     * @param x         initial x position of the Character
     * @param y         initial y position of the Character
     * @param img       the sprite of the Character
     */
    public Fly(int x, int y, BufferedImage img) { super(x, y, false, img); }

    /**
     * Unused tick method for Fly object.
     */
    @Override
    public void tick() {}

}