package gui;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

/**
 * An extension of JPanel that creates the result screen.
 */
public class ResultScreen extends JPanel{

    /**
     * MainMenu object
     */
    MainMenu menu;

    /**
     * Frame object
     */
    Frame frame;

    /**
     * Constructor for a ResultScreen object
     * @param menu      MainMenu object
     * @param frame     Frame object
     * @param state     the state of the game
     * @param highscore the current high score
     * @param time      the current time
     * @param required  the number of collected required rewards and total number of required rewards
     * @param bonus     the number of collected bonus rewards and total number of bonus rewards
     */
    public ResultScreen(MainMenu menu, Frame frame, int state, int highscore, long time, int[] required, int[] bonus) {

        super(new GridLayout(2, 1));

        this.menu = menu;
        this.frame = frame;

        SwingFactory factory = new SwingFactory(new Font("Serif", Font.PLAIN, 32));

        Color black = new Color(2, 0, 1);
        Color white = new Color(255, 255, 255);
        Color lightBlue = new Color(0, 207, 255);
        Color red = new Color(240, 80, 49);
        Color green = new Color(0, 218, 72);
        Color pink = new Color(214, 92, 211);
        Color lightPink = new Color(255, 3, 204);
        Color yellow = new Color(255, 255, 0);

        int score = Game.score;

        if (required.length != 2) {
            required = new int[]{0, 1};
        }
        if (bonus.length != 2) {
            bonus = new int[]{0, 1};
        }


       // JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        this.setBackground(Color.BLACK);

        JLabel results = new JLabel("");
        results.setFont(new Font("Serif", Font.PLAIN, 60));

        if (state == 2) {
            results.setText("YOU LOSE!");
            results.setForeground(red);
        } else if (state == 3) {
            results.setText("YOU WIN!");
            results.setForeground(green);
        } else {
            results.setText("GAME RETURNED STATE " + state + ".");
        }
        results.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel resultPanel = new JPanel(new GridLayout(9, 1));
        resultPanel.setBackground(Color.BLACK);

        JLabel resultMessage = factory.createLabel("", white);
        JLabel scoreResult = factory.createLabel("", white);
        JLabel bonusResult = factory.createLabel("", lightBlue);
        JLabel timeResult = factory.createLabel("", lightBlue);
        JLabel highScoreResult = factory.createLabel("", black);


        resultPanel.add(resultMessage);
        if (state == 2) {
            resultMessage.setForeground(red);
            if (score < 0) {
                resultMessage.setText("Your score dropped below 0!");
            } else {
                resultMessage.setText("The spider caught you!");
            }
        } else if (state == 3) {
            resultMessage.setForeground(green);
            resultMessage.setText("All flies rescued, well done!");
        } else {
            resultMessage.setText("Something went wrong.");
        }


        //add empty labels between labels to create newlines between the text
        resultPanel.add(new JLabel());
        resultPanel.add(new JLabel());


        if (state == 2 || state == 3) {
            //show the player's score if they win or lose
            resultPanel.add(scoreResult);
            scoreResult.setText("Score: " + score);

            if (score < 0) {
                scoreResult.setForeground(red);
            } else {
                scoreResult.setForeground(pink);
            }
        } else {
            resultPanel.add(new JLabel());
        }

        if (state == 3) {
            //show the player's time taken only if they win
            resultPanel.add(bonusResult);
            resultPanel.add(timeResult);
            resultPanel.add(new JLabel());
            resultPanel.add(highScoreResult);

            //if they win, show the bonus rewards collected (because they had to have collected all required)
            bonusResult.setText("Bonus Rewards Collected: " + bonus[0] + " / " + bonus[1]);
            timeResult.setText("Time Taken: " + menu.timeToString(time));

            if (score > highscore) {
                highScoreResult.setText("NEW HIGH SCORE!");
                highScoreResult.setForeground(lightPink);
                scoreResult.setForeground(yellow);

                //write the new high score to the file
                //score is only written on wins
                try {
                    FileWriter file = new FileWriter("highscore.txt");
                    file.write(Integer.toString(score));
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                highScoreResult.setText("High Score: " + highscore);
                highScoreResult.setForeground(lightBlue);
            }

        } else {
            resultPanel.add(bonusResult);
            resultPanel.add(new JLabel());
            resultPanel.add(new JLabel());
            resultPanel.add(new JLabel());

            //if they lose, show the required rewards they collected (shows how close they were to winning)
            bonusResult.setText("Rewards Collected: " + required[0] + " / " + required[1]);
        }
        resultPanel.add(new JLabel());

        this.add(results);
        this.add(resultPanel);

        frame.add(this);
        frame.setVisible(true);
    }



}
