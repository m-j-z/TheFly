package gui;

import boardcomponents.TileManager;
import character.Fly;
import character.KeyInput;
import character.Spider;
import nonmoving.NonMoving;
import nonmoving.Punishment;
import nonmoving.Reward;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Creates and initializes the game.
 */
public class Game extends Canvas implements Runnable {

    // window dimensions
    /**
     * Width of the window.
     */
    public static final int WIDTH = 1024;

    /**
     * Height of the window.
     */
    public static final int HEIGHT = WIDTH / 12 * 9;

    /**
     * Header height.
     */
    public static final int HEADER_HEIGHT = 40;

    /**
     * Real width of the window.
     */
    public static int realWidth = 0;

    /**
     * Real height of the window
     */
    public static int realHeight = 0;

    /**
     * Defines a GamePanel object.
     * @see GamePanel
     */
    GamePanel gp;

    /**
     * Defines a Thread object.
     * @see Thread
     */
    private Thread thread;

    /**
     * Defines a Handler object.
     */
    private final Handler handler;

    /*
        state = -1 - error
        state = 0  - game not started
        state = 1  - game in progress
        state = 2  - game loss
        state = 3  - game win
     */

    /**
     * Defines the current status of the game loop.
     */
    private boolean isRunning = false;

    /**
     * Current Players' score
     */
    public static int score = 0;

    /**
     * Current high score as read.
     */
    public static int highscore = 0;

    /**
     * Defines the current play time.
     */
    private long time = 0;

    //bonus reward spawn intervals are randomized between a min. and max. time
    /**
     * Defines the minimum lifetime of a new bonus reward.
     */
    private final long minBonusTime = 5000; //bonus rewards will appear no more frequently than every 5 seconds

    /**
     * Defines the maximum lifetime of a new bonus reward.
     */
    private final long maxBonusTime = 10000; //bonus rewards will appear at least every 10 seconds

    //limit the number of available bonus rewards to prevent stalling the game to infinitely increase the score
    /**
     * Defines the limit to the number of bonus rewards available.
     */
    private int bonusRewardsAvailable = 20;

    //first bonus reward will always appear at the minimum time
    /**
     * Sets the first bonus reward to always appear for the minimum lifetime possible.
     */
    private long nextBonus = minBonusTime;

    /**
     * Defines a randomizer to determine the lifetime of a bonus reward.
     */
    private final Random bonusTimeRandomizer = new Random();

    /**
     * Defines a randomizer to determine the location of the bonus reward.
     */
    private final Random bonusLocationRandomizer = new Random();

    /**
     * A Window object.
     */
    private final Window win;

    /**
     * Defines the Fly image.
     */
    private BufferedImage flyImg;

    /**
     * Defines the Spider image.
     */
    private BufferedImage spiderImg;

    /**
     * Defines the required reward image.
     */
    private BufferedImage requiredImg;

    /**
     * Defines the punishment image.
     */
    private BufferedImage punishmentImg;

    /**
     * Defines the bonus reward image.
     */
    private BufferedImage optional1Img;

    /**
     * Defines the bonus reward image.
     */
    private BufferedImage optional2Img;

    /**
     * A static Fly object
     */
    public static Fly fly;

    /**
     * A TileManager object used to get valid positions for bonus rewards.
     */
    private TileManager tileM;

    /**
     * MainMenu object
     */
    MainMenu menu;


    /**
     * Creates a new game and initializes the board.
     */
    public Game() {
        //read the high score from the file
        //this must be done before creating the window because the window needs to know the high score
        try {
            File file = new File("highscore.txt");
            if (file.createNewFile()) {
                FileWriter f = new FileWriter("highscore.txt");
                f.write("0");
                f.close();
            }
            Scanner text = new Scanner(file);
            if (text.hasNextLine()) {
                String fileContent = text.nextLine();
                int scoreInFile = 0;
                try{
                    scoreInFile = Integer.parseInt(fileContent);
                } catch (NumberFormatException e){
                    e.printStackTrace();
                }
                highscore = scoreInFile;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        win = new Window(WIDTH, HEIGHT, "IN A STICKY SITUATION!", this);
        menu = win.getMenu();

        // get dimensions for bound checking
        Dimension dimensions = win.getDim();
        realWidth = dimensions.width;
        realHeight = dimensions.height;

        handler = new Handler();
        initializeBoard(handler);

        this.addKeyListener(new KeyInput(handler, gp));
    }

    /**
     * Reads an image from the given file path and returns it.
     * @param filePath file path to read the image from.
     * @return BufferedImage
     */
    private BufferedImage readURL(String filePath){
        URL imageURL = getClass().getResource(filePath);
        assert imageURL != null;
        BufferedImage image = null;
        try{
            image = ImageIO.read(imageURL);
        } catch (IOException e){
            e.printStackTrace();
        }
        assert image != null;
        return image;
    }

    /**
     * Initializes the images used in the game.
     */
    private void getImageResources() {
        flyImg = readURL("/sprites/fly.png");
        spiderImg = readURL("/sprites/spider.png");
        requiredImg = readURL("/sprites/friendly_fly.png");
        punishmentImg = readURL("/sprites/punishment.png");
        optional1Img = readURL("/sprites/butterfly.png");
        optional2Img = readURL("/sprites/beetle.png");
    }

    /**
     * Initializes board
     * @param handler Class that will handle render and tick updates
     */
    private void initializeBoard(Handler handler) {
        try {

            gp = new GamePanel();

            getImageResources();

            int[][] spiderPath1 = {{0, 1}, {-1, 0}, {-1, 0}, {-1, 0}, {0, -1}};
            int[][] spiderPath2 = {{-1, 0}, {-1, 0}, {-1, 0}};
            int[][] spiderPath3 = {{-1, 0}, {0, 1}, {-1, 0}, {0, -1}, {0, -1}, {0, 1}, {0, 1}};
            int[][] spiderPath4 = {{-1, 0}, {-1, 0}, {0, -1}, {0, -1}, {0, -1}, {1, 0}, {1, 0}, {1, 0}, {0, 1}, {0, 1}, {0, 1}, {-1, 0}};
            int[][] spiderPath5 = {{-1, 0}, {-1, 0}, {-1, 0}, {-1, 0}, {-1, 0}, {-1, 0}};
            int[][] spiderPath6 = {{0, 1}, {0, 1}, {0, 1}, {0, 1}, {0, 1}, {0, 1}, {0, 1}};
            int[][] spiderPath7 = {{1, 0}, {1, 0}, {1, 0}, {1, 0}};

            if(flyImg != null && spiderImg != null && requiredImg != null && optional1Img != null && optional2Img != null && punishmentImg != null) {
                fly = new Fly(50, 75, flyImg);
                handler.addCharacter(fly);

                handler.addCharacter(new Spider(275, 300, spiderPath1, spiderImg));
                handler.addCharacter(new Spider(158, 542, spiderPath2, spiderImg));
                handler.addCharacter(new Spider(372, 78, spiderPath3, spiderImg));
                handler.addCharacter(new Spider(482, 374, spiderPath4, spiderImg));
                handler.addCharacter(new Spider(794, 510, spiderPath5, spiderImg));
                handler.addCharacter(new Spider(788, 260, spiderPath6, spiderImg));
                handler.addCharacter(new Spider(770, 194, spiderPath7, spiderImg));

                handler.addNonMoving(new Reward(34, 482, 100, requiredImg));
                handler.addNonMoving(new Reward(482, 334, 100, requiredImg));
                handler.addNonMoving(new Reward(830, 510, 100, requiredImg));
                handler.addNonMoving(new Reward(866, 574, 100, requiredImg));
                handler.addNonMoving(new Reward(830, 126, 100, requiredImg));
                handler.addNonMoving(new Reward(382, 75, 100, requiredImg));

                ArrayList<NonMoving> nonMovings = handler.getNonMoving();
                for (NonMoving nonMoving : nonMovings) {
                    nonMoving.required();
                }

                handler.addNonMoving(new Punishment(66, 482, -100, punishmentImg));
                handler.addNonMoving(new Punishment(354, 190, -100, punishmentImg));
                handler.addNonMoving(new Punishment(606, 222, -100, punishmentImg));
                handler.addNonMoving(new Punishment(606, 262, -100, punishmentImg));
                handler.addNonMoving(new Punishment(606, 302, -100, punishmentImg));
                handler.addNonMoving(new Punishment(354, 222, -100, punishmentImg));
                handler.addNonMoving(new Punishment(894, 322, -100, punishmentImg));
                handler.addNonMoving(new Punishment(866, 394, -100, punishmentImg));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts the game by creating a thread and allows the main loop to start.
     */
    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    /**
     * Waits for thread to finish and stops the main loop.
     */
    public synchronized void stop() {
        try {
            thread.join();
            isRunning = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initiates the main loop of the game that will initialize then update the board on every tick.
     */
    public void run() {
        long startTime = System.nanoTime();             // get current time in nanoseconds
        double numOfTicks = 32.0;
        double ns = 1000000000 / numOfTicks;            // nanoseconds per tick
        double delta = 0;                               // numbers of ticks passed

        requestFocusInWindow();

        while(isRunning) {
            long currTime = System.nanoTime();

            delta += (currTime - startTime) / ns;
            startTime = currTime;

            while(delta >= 1) {
                long msElapsed = (long) (1000.0/(delta*numOfTicks));
                time += msElapsed;

                updateBonus(msElapsed);
                tick();
                delta--;
            }

            if(isRunning) {
                updateBoard();
                updateHeader();
                collectNonMoving();
            }
        }
    }

    /**
     * Runs the handler.tick() function.
     */
    private void tick() {
        int state = handler.tick();
        if (state != 1) {
            //MainMenu results = new MainMenu();r

            int[] rewardAmounts = NonMoving.getRewardAmounts();
            int[] bonusAmounts = NonMoving.getBonusAmounts();

            menu.addResultScreen(menu.createResultScreen(state, highscore, time, rewardAmounts, bonusAmounts));

            stop();
        }
    }

    /**
     * Runs the handler.render() function for updating the board.
     */
    private void updateBoard() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bs.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        tileM = gp.getTileM();
        tileM.draw((Graphics2D) graphics);

        handler.render(graphics);

        graphics.dispose();
        bs.show();
    }

    /**
     * Checks whether the Fly collides with any NonMovingObject.
     * If it does, the method adds its value to the score and removes the object.
     */
    private void collectNonMoving(){
        handler.charWithNonMoving();
    }

    /**
     * Updates the header to display time and score.
     */
    public void updateHeader(){
        win.setScoreLabel(score);
        win.setTimeLabel(time);
        int[] rewardAmounts = NonMoving.getRewardAmounts();
        win.setRewardLabel(rewardAmounts[0], rewardAmounts[1]);
    }

    /**
     * First updates the lifetime on all existing bonus rewards
     * then attempts to add a new bonus reward, if enough time has
     * passed since the last bonus reward was added and all the
     * available bonus rewards have not been used up yet.
     * @param msElapsed Milliseconds per tick of the game
     */
    public void updateBonus(long msElapsed){
        handler.updateBonus(msElapsed);

        if (bonusRewardsAvailable > 0) {
            nextBonus -= msElapsed;
            if (nextBonus <= 0) {
                //the time until the next bonus reward is randomized between minBonusTime and maxBonusTime
                long nextRewardTime = minBonusTime + bonusTimeRandomizer.nextInt((int) (maxBonusTime - minBonusTime));
                nextBonus += nextRewardTime;

                //Randomly select an image for the new bonus reward
                int bonusRewardImageChoice = bonusTimeRandomizer.nextInt(2);
                BufferedImage newBonusImage = optional1Img;
                if (bonusRewardImageChoice == 1){
                    newBonusImage = optional2Img;
                }

                //Find all the valid positions that a bonus can appear in
                ArrayList<ArrayList<Integer>> bonusPositions = new ArrayList<>();
                int[][] validPositions = tileM.mapTileNum;
                for (int i=0;i<validPositions.length;i++){
                    for (int j=0;j<validPositions[i].length;j++){
                        if (validPositions[i][j] == 0) {
                            ArrayList<Integer> thisPosition = new ArrayList<>();
                            thisPosition.add(i);
                            thisPosition.add(j);
                            bonusPositions.add(thisPosition);
                        }
                    }
                }

                //Randomly select a position to add the new bonus reward in
                int tileSize = tileM.getTileSize();
                ArrayList<Integer> position = bonusPositions.get(bonusLocationRandomizer.nextInt(bonusPositions.size()));
                int positionX = position.get(0) * tileSize;
                int positionY = position.get(1) * tileSize;


                Reward bonus = new Reward(positionX, positionY, 50, newBonusImage);
                bonus.setAsBonus();
                handler.addNonMoving(bonus);
                bonusRewardsAvailable--;
            }
        }
    }

    /**
     * Main method.
     * @param args arguments passed on the command line
     */
    public static void main(String[] args) {
        new Game();
    }

}
