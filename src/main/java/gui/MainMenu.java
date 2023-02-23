package gui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


/**
 * A class that handles the GUI aspect of the game.
 */
public class MainMenu {

    private final InstructionMenu instructionMenu;
    private static JFrame frame;
    private static String title;
    private Game game;

    // changes font type of the whole menu
    private final String fontType = "Serif";

    private JLabel scoreLabel;
    private JLabel timeLabel;
    private JLabel rewardLabel;

    private JPanel mainPanel;


    /**
     * Constructor for a MainMenu object.
     * @param frame a JFrame object
     * @param title a String object that defines the title of the game.
     * @param game a Game object
     * @see Game
     */
    public MainMenu(JFrame frame, String title, Game game) {
        MainMenu.frame = frame;
        MainMenu.title = title;
        this.game = game;
        this.instructionMenu = new InstructionMenu(frame, this);
        createMainMenu();
    }

    /**
     * Adds the title label to the main menu.
     * @param panel JPanel to add title to
     */
    private void createTitleLabel(JPanel panel) {
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font(fontType, Font.PLAIN, 64));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);
    }

    /**
     * Adds the necessary buttons to the main menu.
     * @param panel JPanel to add the buttons to
     * @see JPanel
     */
    private void createButtons(JPanel panel) {
        Font btnFont = new Font(fontType, Font.PLAIN, 24);

        JButton startBtn = new JButton("START");
        JButton instructionBtn = new JButton("INSTRUCTIONS");
        JButton quitBtn = new JButton("QUIT");

        startBtn.setFont(btnFont);
        instructionBtn.setFont(btnFont);
        quitBtn.setFont(btnFont);

        modifyButton(startBtn);
        modifyButton(instructionBtn);
        modifyButton(quitBtn);

        startBtn.addActionListener(e -> {
            if(e.getSource() == startBtn) {
                frame.getContentPane().removeAll();
                frame.repaint();

                //frame.add(game); moved to the function below
                addGameDisplay(createGameDisplay());

                frame.setVisible(true);
                game.start();
            }
        });

        instructionBtn.addActionListener(e -> {
            if(e.getSource() == instructionBtn) {
                frame.getContentPane().removeAll();
                frame.repaint();
                instructionMenu.createInstructionsMenu();
            }
        });

        quitBtn.addActionListener(e -> {
            if(e.getSource() == quitBtn) {
                frame.dispose();
            }
        });

        panel.add(startBtn);
        panel.add(instructionBtn);
        panel.add(quitBtn);
    }

    /**
     * Adds the high score label to the main menu.
     * @param panel JPanel to add label to
     * @see JPanel
     */
    private void createHighScoreLabel(JPanel panel) {
        JLabel highScoreLabel = new JLabel("HIGH SCORE: " + Game.highscore, SwingConstants.CENTER);
        highScoreLabel.setFont(new Font(fontType, Font.PLAIN, 32));
        highScoreLabel.setForeground(Color.WHITE);
        panel.add(highScoreLabel);
    }

    /**
     * Creates the main menu on the frame specified by the frame argument.
     */
    void createMainMenu() {

        // set main JPanel
        mainPanel = new JPanel(new GridLayout(3, 1));
        mainPanel.setBackground(Color.BLACK);
        frame.add(mainPanel);

        // create and add panels to main JPanel
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        JPanel highScorePanel = new JPanel(new GridBagLayout());

        JPanel leftBtnPanel = new JPanel();
        JPanel midBtnPanel = new JPanel(new GridLayout(3, 1));
        JPanel rightBtnPanel = new JPanel();

        buttonPanel.add(leftBtnPanel);
        buttonPanel.add(midBtnPanel);
        buttonPanel.add(rightBtnPanel);

        leftBtnPanel.setOpaque(false);
        midBtnPanel.setOpaque(false);
        rightBtnPanel.setOpaque(false);

        titlePanel.setOpaque(false);
        buttonPanel.setOpaque(false);
        highScorePanel.setOpaque(false);

        mainPanel.add(titlePanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(highScorePanel);

        createTitleLabel(titlePanel);
        createButtons(midBtnPanel);
        createHighScoreLabel(highScorePanel);

        frame.setVisible(true);
    }

    void drawMainMenu(){
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.add(this.mainPanel);
        frame.setVisible(true);
    }

    /**
     * Creates the result screen as specified by the state variable
     * @param state     an int representing the state of the game
     * @param highscore an int that indicates the current high score
     * @param time      a long that indicates the current play time in milliseconds
     * @param required  an array of 2 ints that are the required rewards collected and the total required rewards
     * @param bonus     an array of 2 ints that are the bonus rewards collected and the total bonus rewards
     * @return JPanel a panel with all the information on the result screen.
     */
    public JPanel createResultScreen(int state, int highscore, long time, int[] required, int[] bonus) {
        return new ResultScreen(this, this.frame, state,  highscore,  time,  required, bonus);

    }



    /**
     * Clears all objects currently on the screen and displays a new result screen.
     * @param resultScreen the JPanel to be displayed
     */
    public void addResultScreen(JPanel resultScreen){
        frame.getContentPane().removeAll();
        frame.repaint();
        frame.add(resultScreen);
        frame.setVisible(true);
    }

    /**
     * Modifies the JButton by removing focusable, changing the text colour to be white, make it transparent and adds a green border.
     * @param btn the JButton to be modified
     * @see JButton
     */
    void modifyButton(JButton btn) {
        // TODO possibly implement text highlighting when button is hovered over
        btn.setFocusable(false);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorder(new LineBorder(Color.GREEN));
    }
    /**
     * Creates the header at the top of the screen which displays the player's score, time, and rewards collected.
     * @return a JPanel to display the game on
     */
    public JPanel createGameDisplay() {
        SwingFactory factory = new SwingFactory(new Font("Serif", Font.PLAIN, 28));
        Color purple = new Color(77, 30, 121);
        Color lightBlue = new Color(0, 207, 255);

        JPanel ingamePanel = new JPanel(new GridLayout(1, 3));

        ingamePanel.setBackground(purple);
        ingamePanel.setSize(new Dimension(Game.WIDTH, Game.HEADER_HEIGHT));
        ingamePanel.setMinimumSize(new Dimension(Game.WIDTH, Game.HEADER_HEIGHT));
        ingamePanel.setMaximumSize(new Dimension(Game.WIDTH, Game.HEADER_HEIGHT));
        ingamePanel.setPreferredSize(new Dimension(Game.WIDTH, Game.HEADER_HEIGHT));

        scoreLabel = factory.createLabel("Score: 0", lightBlue);
        ingamePanel.add(scoreLabel);

        timeLabel = factory.createLabel("Time: 0", lightBlue);
        ingamePanel.add(timeLabel);

        rewardLabel = factory.createLabel("Rewards: 0", lightBlue);
        ingamePanel.add(rewardLabel);

        ingamePanel.add(new GamePanel());

        return ingamePanel;
    }

    /**
     * Adds an existing display to the game.
     * @param newFrame the JPanel to be added
     */
    public void addGameDisplay(JPanel newFrame){
        frame.add(newFrame);
        frame.add(game);
    }

    /**
     * Gets the current label whose text contains the score.
     * @return Jlabel
     */
    public JLabel getScoreLabel(){return scoreLabel;}

    /**
     * Gets the current label whose text contains the time left in "minutes:seconds.milliseconds" format.
     * @return Jlabel
     */
    public JLabel getTimeLabel(){return timeLabel;}

    /**
     * Gets the current label whose text contains the rewards collected and total rewards.
     * @return JLabel
     */
    public JLabel getRewardLabel(){return rewardLabel;}

    /**
     * Sets the score as specified by the score parameter.
     * @param score an int that indicates the current score
     */
    public void setScoreLabel(int score){
        scoreLabel.setText("Score: "+ score);
        if (score < 0){
            scoreLabel.setForeground(new Color(240, 80, 49));
        } else{
            scoreLabel.setForeground(new Color(214, 92, 211));
        }
    }

    /**
     * Sets the time as specified by the time parameter.
     * @param time a long that indicates the current play time
     */
    public void setTimeLabel(long time){
        timeLabel.setText("Time: "+timeToString(time));
    }

    /**
     * Sets the number of collected required reward and number of total required reward to be displayed.
     * @param collected an int that indicates the number of collected required rewards
     * @param required  an int that indicates the total number of required rewards
     */
    public void setRewardLabel(int collected, int required){
        if (collected < 0){
            collected = 0;
        }
        if (required < 1){
            required = 1;
        }
        if (collected > required){
            collected = required;
        }
        String collectedString = Integer.toString(collected);
        String requiredString = Integer.toString(required);
        rewardLabel.setText("Rewards: "+collectedString+" / "+requiredString);
        double rewardFraction = (double) collected / required;

        int labelR = 255;
        int labelG = 255;
        int labelB = 0;

        /*
        set the label to a color representing the player's progress towards completion
        red = no rewards collected
        yellow = half rewards collected
        green = all rewards collected
        */
        if (rewardFraction < 0.5){
            labelG = (int) (2 * rewardFraction * 255);
        } else{
            labelR = (int) (2 * (1 - rewardFraction) * 255);
        }
        rewardLabel.setForeground(new Color(labelR, labelG, labelB));
    }

    /**
     * Converts a time expressed as milliseconds to a "minutes:seconds.milliseconds" format to display to the user
     * @param time the current time in the game in milliseconds
     * @return String
     */
    public String timeToString(long time){
        if (time < 0){
            return "00:00.000";
        }
        long ms = (time % 1000);
        long sec = ((time / 1000) % 60);
        long min = ((time / 1000) / 60);
        String msString = Long.toString(ms);
        String secondsString = Long.toString(sec);
        String minutesString = Long.toString(min);

        //make sure the output string always has the same number of digits
        if (secondsString.length()==1){
            secondsString = "0"+secondsString;
        } if (minutesString.length()==1){
            minutesString = "0"+minutesString;
        } if (msString.length()==1){
            msString = "00"+msString;
        } else if (msString.length()==2){
            msString = "0"+msString;
        }

        return (minutesString+":"+secondsString+"."+msString);
    }

}
