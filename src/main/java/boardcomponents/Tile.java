package boardcomponents;

import java.awt.image.BufferedImage;

/**
 * A class that defines a Tile for the board.
 */
public class Tile {
    /**
     * Defines the images used for the tile.
     */
    public BufferedImage image;

    /**
     * Defines the collision outcome for the tile.
     */
    public boolean collision = false;
}
