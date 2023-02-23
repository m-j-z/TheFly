package nonmoving;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A class that defines the non-moving, manipulate objects of the game.
 */
public class NonMoving {
    /**
     * Defines the current x coordinate.
     */
    protected final int x;

    /**
     * Defines the current y coordinate.
     */
    protected final int y;
    private final int value;
    private boolean required;

    /**
     * Defines the type of reward, bonus or required.
     */
    public boolean bonus;

    /**
     * Defines the image to be rendered.
     */
    protected BufferedImage img;
    private static int numOfRequired = 0;
    private static int numOfCollectedRequired = 0;
    private static int numOfBonusCollected = 0;
    private static int numOfBonus = 0;
    private final int[] imgSize;

    /**
     * Defines the remaining lifetime of the bonus reward.
     */
    protected double lifetime;

    /**
     * Constructor for a NonMoving object.
     * @param x         the initial x coordinate
     * @param y         the initial y coordinate
     * @param value     the point value of the NonMoving object
     * @param img       the BufferedImage to render for NonMoving object
     * @see BufferedImage
     */
    public NonMoving(int x, int y, int value, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.required = false;
        this.img = img;
        if (this.img != null){
            imgSize = new int[]{ img.getWidth(), img.getHeight() };
        } else{
            imgSize = new int[]{0,0};
        }
        this.bonus = false; //default value
        this.lifetime = 0.0;
    }

    /**
     * Sets the Reward as a required Reward.
     */
    public void required() {
        this.required = true;
        numOfRequired++;
    }

    /**
     * Returns the point value of the NonMoving object.
     * @return int
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns whether it is required to be collected.
     * @return boolean
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Increases the number of collected required or bonus rewards.
     */
    public void addCollected() {
        if (this.required) {
            numOfCollectedRequired++;
        } else if (this.bonus){
            numOfBonusCollected++;
        }
    }

    /**
     * Decreases the number of collected required or bonus rewards
     */
    public void removeCollected() {
        if (this.required) {
            numOfCollectedRequired--;
        } else if (this.bonus) {
            numOfBonusCollected--;
        }
    }

    /**
     * Checks whether the win condition is satisfied.
     * @return boolean
     */
    public boolean tick() {
        return numOfCollectedRequired >= numOfRequired;
    }

    /**
     * Renders the NonMoving object.
     * @param graphics a Graphics object
     * @see Graphics
     */
    public void render(Graphics graphics) {
        graphics.drawImage(img, x, y, null);
    }

    /**
     * Returns the current location of the NonMoving object.
     * @return int[]
     */
    public int[] getPos() {
        return new int[] {x, y};
    }

    /**
     * Returns the size of the NonMoving object.
     * @return int[]
     */
    public int[] getSize() {
        return imgSize;
    }

    /**
     * Sets the reward to be a bonus reward.
     */
    public void setAsBonus() {
        numOfBonus++;
        this.bonus = true;
        this.lifetime = 10;
    }

    /**
     * Returns the number of collected required rewards and the total number of required rewards.
     * @return  int[]
     */
    public static int[] getRewardAmounts() {
        return new int[]{numOfCollectedRequired, numOfRequired};
    }

    /**
     * Returns the number of collected bonus rewards and the total number of collected rewards.
     * @return int[] - int[0] is the number of collected bonus rewards, int[1] is the total number of bonus rewards
     */
    public static int[] getBonusAmounts() {
        return new int[]{numOfBonusCollected, numOfBonus};
    }

    /**
     * Decreases the lifetime of a bonus reward by the specified ms.
     * @param ms    a long that specifies the time to decrease in milliseconds
     * @return  boolean
     */
    public boolean decreaseLifetime(long ms){
        return false;
    }

}
