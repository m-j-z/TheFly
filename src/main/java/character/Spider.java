package character;

import java.awt.image.BufferedImage;

/**
 * A class that acts as the enemy of the player.
 */
public class Spider extends Character {

    private final int[][] patrolPath;
    private int patrolStep = 0;
    private boolean reverseDirection = false;
    private int steps = 0;

    /**
     * Spider object constructor
     * @param x         the initial x co-ordinate
     * @param y         the initial y co-ordinate
     * @param path      the path of which the spider will follow to conclude at its goal location
     * @param img       a BufferedImage for sprite of the object
     */
    public Spider(int x, int y, int[][] path, BufferedImage img) {
        super(x, y, true, img);
        patrolPath = path;
    }

    /**
     * Moves the spider along a set path.
     */
    public void patrol() {
        if(patrolPath == null) {
            return;
        }
        int spiderVelocity = 1;
        if(reverseDirection) {
            move(patrolPath[patrolStep][0] * spiderVelocity * -1, patrolPath[patrolStep][1] * spiderVelocity * -1);
        } else {
            move(patrolPath[patrolStep][0] * spiderVelocity, patrolPath[patrolStep][1] * spiderVelocity);
        }

        if(steps >= 31) {
            if(reverseDirection) {
                patrolStep--;
            } else {
                patrolStep++;
            }
            steps = 0;
        } else {
            steps++;
        }

        if(patrolStep > patrolPath.length - 1) {
            reverseDirection = true;
            patrolStep = patrolPath.length - 1;
        } else if(patrolStep < 0) {
            reverseDirection = false;
            patrolStep = 0;
        }
    }

    /**
     * Used to call patrol for tick updates.
     */
    @Override
    public void tick() {
        patrol();
    }
}