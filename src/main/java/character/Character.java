package character;

import gui.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * An abstract to define the Character class.
 */
public abstract class Character {


    private int x;
    private int y;
    private final boolean isEnemy;
    private BufferedImage img;

    /**
     * Constructor
     * @param x         initial x position of the Character
     * @param y         initial y position of the Character
     * @param isEnemy   if the Character is an enemy
     * @param img       the sprite of the Character
     */
    public Character(int x, int y, boolean isEnemy, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.isEnemy = isEnemy;
        this.img = img;
    }

    /**
     * Moves the Character by x and y units.
     * @param x the amount to move horizontally
     * @param y the amount to move vertically
     */
    public void move(int x, int y) {
        if(this.x + x < 0 || this.x + x >= Game.realWidth - img.getWidth()) {
            return;
        }
        if(this.y + y < 0 || this.y + y >= Game.realHeight - img.getHeight()){
            return;
        }
        this.x += x;
        this.y += y;
    }

    /**
     * Detects whether this character collides with another character
     * @param other the other character that it is checking with
     * @return boolean, returns false if a collision is not detected
     */
    public boolean movingCollision(Character other){
        return collisionDetection(getPos(), getSize(), other.getPos(), other.getSize());
    }

    /**
     * Detects whether this character collides with a NonMovingObject
     * @param other the other object that it is checking with
     * @return boolean, returns false if a collision is not detected
     */
    public boolean nonMovingCollision(nonmoving.NonMoving other){
        return collisionDetection(getPos(), getSize(), other.getPos(), other.getSize());
    }

    /**
     * Detects whether two objects collide only using their coordinates and sizes.
     * The two methods above call this method with the coordinates of their respective objects.
     * @return boolean, returns false if a collision is not detected
     */
    private boolean collisionDetection(int[] thisPos, int[] thisSize, int[] otherPos, int[] otherSize){
        //left, right, top, bottom
        if (thisPos.length != 2 ||
            thisSize.length != 2 ||
            otherPos.length != 2 ||
            otherSize.length != 2){
            return false;
        }
        if (thisSize[0] < 0 || thisSize[1] < 0){
            return false;
        }
        if (otherSize[0] < 0 || otherSize[1] < 0){
            return false;
        }

        int[] thisRect = new int[]{
                thisPos[0],thisPos[0]+thisSize[0],thisPos[1],thisPos[1]+thisSize[1]};
        int[] otherRect = new int[]{
                otherPos[0],otherPos[0]+otherSize[0],otherPos[1],otherPos[1]+otherSize[1]};


        /*  There is a collision if:
            - This rectangle's left side is less than the other one's right side
            - This rectangle's right side is greater than the other one's left side
            - This rectangle's top side is less than the other one's bottom side
            - This rectangle's bottom side is greater than the other one's top side
        */
        return (thisRect[0] < otherRect[1] && thisRect[1] > otherRect[0]
         && thisRect[2] < otherRect[3] && thisRect[3] > otherRect[2]);
    }

    /**
     * Returns the position of the Character.
     * @return int[] - int[0] is the x coordinate, int[1] is the y coordinate
     */
    public int[] getPos() {
        return new int[]{x, y};
    }

    /**
     * Returns the size of the Character.
     * @return int[] - int[0] is the width, int[1] is the height
     */
    public int[] getSize() {
        return new int[]{img.getWidth(), img.getHeight()};
    }

    /**
     * Renders the graphic to the JFrame
     * @param graphics a Graphics object
     * @see Graphics
     */
    public void render(Graphics graphics) {
        graphics.drawImage(img, x, y, null);
    }

    /**
     * Returns whether the Character is a Spider or not
     * @return boolean, true if it's a Spider, false otherwise
     */
    public boolean isSpider() {
        return isEnemy;
    }

    /**
     * Sets the image of the Character object
     * @param img a BufferedImage object
     */
    public void setImage(BufferedImage img) {
        this.img = img;
    }

    /**
     * Method to update the actions of a Character object.
     */
    public abstract void tick();

}