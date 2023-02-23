package gui;

import javax.swing.*;
import java.awt.*;

/**
 * A class that creates the window of the game.
 */
public class Window extends Canvas {

    /**
     * Defines the frame created by the Window class.
     */
    private final JFrame frame;

    /**
     * Defines a MainMenu object.
     */
    private final MainMenu menu;
    //need to make this a field so the game can change labels on this window whenever the score changes

    /**
     * Creates a JFrame for all methods to be run on.
     * @param width  width dimension of the JFrame
     * @param height height dimension of the JFrame
     * @param title  title of the JFrame
     * @param game   the game to be ran on the JFrame
     * @see JFrame
     */
    public Window(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);
        this.frame = frame;

        frame.setPreferredSize(new Dimension(width, height));
        frame.setMaximumSize(new Dimension(width, height));
        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        menu = new MainMenu(frame, title, game);
    }

    /**
     * Returns the MainMenu object.
     * @return MainMenu object
     */
    public MainMenu getMenu(){
        return this.menu;
    }

    /**
     * Returns the JFrame width and height.
     * @return Dimension
     */
    public Dimension getDim() {
        frame.pack();
        return frame.getContentPane().getSize();
    }

    /**
     * Updates score display.
     * @param score an int that indicates the current score
     */
    public void setScoreLabel(int score){
        menu.setScoreLabel(score);
    }

    /**
     * Updates time display.
     * @param time  a long that indicates the current play time
     */
    public void setTimeLabel(long time){
        menu.setTimeLabel(time);
    }

    /**
     * Updates reward display.
     * @param collected an int that indicates the number of collected required rewards
     * @param required  an int that indicates the total number of required rewards
     */
    public void setRewardLabel(int collected, int required){
        menu.setRewardLabel(collected, required);
    }

}
