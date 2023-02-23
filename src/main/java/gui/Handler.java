package gui;

import character.Character;
import nonmoving.NonMoving;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class that handles the tick and render updates of the game.
 */
public class Handler {

    private final ArrayList<Character> characters;
    private final ArrayList<NonMoving> nonMoving;

    /**
     * Constructor for a Handler object.
     */
    public Handler() {
        characters = new ArrayList<>();
        nonMoving = new ArrayList<>();
    }

    /**
     * Loops through the Character list and updates the characters by calling the tick() method for the respective class.
     * @return returns the game state as an integer
     */
    public int tick() {
        for (Character tmp : characters) {
            tmp.tick();
        }

        //Win condition: All required rewards have been collected
        NonMoving tmp = new NonMoving(0, 0,0, null);
        if (tmp.tick()) {
            return 3;
        }

        //First lose condition: The Fly collides with a Spider
        if (charWithSpider()){
            return 2;
        }

        //Second lose condition: The score drops below 0 as a result of Punishments
        if (Game.score < 0){
            return 2;
        }

        return 1;
    }

    /**
     * Loops through the Character list and renders the graphics for the character.
     * @param graphics a Graphics class object
     * @see Graphics
     */
    public void render(Graphics graphics) {
        for (Character tmp : characters) {
            tmp.render(graphics);
        }
        for (NonMoving tmp : nonMoving) {
            tmp.render(graphics);
        }
    }

    /**
     * Loops through the Character list and returns the Fly object, if found.
     * @return A Fly object
     */
    public Character getFly() {
        for (Character tmp : characters) {
            if(!tmp.isSpider()) {
                return tmp;
            }
        }
        return null;
    }

    /**
     * Loops through the Character list and checks whether the Fly
     * is colliding with any Spider.
     * @return boolean whether the Fly is colliding with at least one Spider.
     */
    public boolean charWithSpider(){
        Character fly = getFly();
        if (fly != null){
            for (Character tmp : characters) {
                if(tmp.isSpider()) {
                    boolean result = fly.movingCollision(tmp);

                    //once at least one Spider is found to collide with the fly, return true
                    if (result){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Loops through the Character list and checks whether the Fly
     * is colliding with any NonMovingObject.
     */
    public void charWithNonMoving(){
        Character fly = getFly();
        if (fly != null){
            for (NonMoving tmp : nonMoving) {
                boolean result = fly.nonMovingCollision(tmp);

                //once at least one NonMovingObject is found to collide with the fly, return its value
                if (result){
                    Game.score += tmp.getValue();
                    removeNonMoving(tmp);
                    tmp.addCollected();
                    break;
                }
            }
        }
    }

    /**
     * Decreases the current lifetime of all bonus rewards.
     * @param ms a long that defines time in milliseconds
     */
    public void updateBonus(long ms){
        boolean expired;

        /* Iterate through the list of NonMoving objects
        and decrease their lifetimes. If any of their lifetimes
        are less than 0, remove them. Iterate backwards to avoid
        index conflicts when removing elements.
        */
        for (int i = nonMoving.size() - 1 ; i >= 0 ; i--) {
            NonMoving tmp = nonMoving.get(i);
            expired = tmp.decreaseLifetime(ms);
            if (expired){
                removeNonMoving(tmp);
            }
        }
    }


    /**
     * Adds a character to the Characters list
     * @param character a Character object
     */
    public void addCharacter(Character character) {
        if(character == null) {
            return;
        }
        this.characters.add(character);
    }

    // possibly not needed
    /**
     * Removes a character from the Characters list
     * @param character a Character object
     */
    public void removeCharacter(Character character) {
        if(character == null) {
            return;
        }
        this.characters.remove(character);
    }

    /**
     * Adds a reward to the NonMoving list
     * @param object a NonMoving object
     */
    public void addNonMoving(NonMoving object) {
        if(object == null) {
            return;
        }
        this.nonMoving.add(object);
    }

    /**
     * Removes a reward from the NonMoving list
     * @param object a NonMoving object
     */
    public void removeNonMoving(NonMoving object) {
        if(object == null) {
            return;
        }
        this.nonMoving.remove(object);
    }

    /**
     * Returns the list of all Character objects handled by the Handler.
     * @return ArrayList of Character objects
     * @see ArrayList
     */
    public ArrayList<Character> getCharacters() {
        return characters;
    }

    /**
     * Returns the list of all NonMoving objects handled by the Handler.
     * @return  ArrayList of NonMoving objects
     * @see ArrayList
     */
    public ArrayList<NonMoving> getNonMoving() {
        return nonMoving;
    }

}
