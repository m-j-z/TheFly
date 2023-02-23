package gui;

import character.Fly;
import character.Spider;
import nonmoving.NonMoving;
import nonmoving.Punishment;
import nonmoving.Reward;
import org.junit.jupiter.api.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class HandlerTest {

    static final Handler handler = new Handler();

    static Fly fly = new Fly(0, 0, null);
    static Spider spider = new Spider(100, 100, null, null);

    static Reward reward = new Reward(217, 771, 100, null);
    static Punishment punishment = new Punishment(345, 435, -100, null);

    static BufferedImage flyImg;
    static BufferedImage spiderImg;
    static BufferedImage rewardImg;

    static Game game;

    @BeforeAll
    public static void setUp() {

        URL flyURL = HandlerTest.class.getResource("/sprites/fly.png");
        assert flyURL != null;

        URL spiderURL = HandlerTest.class.getResource("/sprites/spider.png");
        assert spiderURL != null;

        URL rewardURL = HandlerTest.class.getResource("/sprites/butterfly.png");
        assert rewardURL != null;

        try {
            flyImg = ImageIO.read(flyURL);
            spiderImg = ImageIO.read(spiderURL);
            rewardImg = ImageIO.read(rewardURL);
            fly.setImage(flyImg);
            spider.setImage(spiderImg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        reward.required();

        handler.addCharacter(fly);
        handler.addCharacter(spider);

        handler.addNonMoving(reward);
        handler.addNonMoving(punishment);

        game = new Game();

    }

    @Test
    @DisplayName("Ensure method 'tick' properly returns value to indicate game win status")
    public void testWinCondition() {
        int numOfRequired = NonMoving.getRewardAmounts()[1];
        for(int i = 0; i != numOfRequired; i++) {
            reward.addCollected();
        }
        assertEquals(3, handler.tick());
        for(int i = 0; i != numOfRequired; i++) {
            reward.removeCollected();
        }
    }

    @Test
    @DisplayName("Ensure method 'tick' properly returns value to indicate game in progress status")
    public void testInProgressCondition() {
        assertEquals(1, handler.tick());
    }

    @Test
    @DisplayName("Ensure method 'tick' properly returns value to indicate game loss due to spider status")
    public void testSpiderLossCondition() {
        Spider spider = new Spider(0, 0, null, spiderImg);
        handler.addCharacter(spider);
        assertEquals(2, handler.tick());
        handler.removeCharacter(spider);
    }

    @Test
    @DisplayName("Ensure method 'tick' properly returns value to indicate game loss due to negative points status")
    public void testNegativeLossCondition() {
        Game.score = -1;
        assertEquals(2, handler.tick());
        Game.score = 0;
    }

    @Test
    @DisplayName("Ensure method 'addCharacter' properly adds and updates character list in Handler class")
    public void testAddCharacter() {
        Fly fly = new Fly(0, 0, null);
        int initialSize = handler.getCharacters().size();
        handler.addCharacter(fly);
        int finalSize = handler.getCharacters().size();
        assertEquals(initialSize + 1, finalSize);
    }

    @Test
    @DisplayName("Ensure meethod 'remove Character properly removes and updates character list in Handler class")
    public void testRemoveCharacter() {
        int initialSize = handler.getCharacters().size();
        handler.removeCharacter(spider);
        int finalSize = handler.getCharacters().size();
        assertEquals(initialSize - 1, finalSize);
    }

    @Test
    @DisplayName("Ensure method 'addNonMoving' properly adds and updates nonMoving list in Handler class")
    public void testAddNonMoving() {
        Reward newReward = new Reward(688, 457, 50, null);
        newReward.setAsBonus();
        int initialSize = handler.getNonMoving().size();
        handler.addNonMoving(newReward);
        int finalSize = handler.getNonMoving().size();
        assertEquals(initialSize + 1, finalSize);
    }

    @Test
    @DisplayName("Ensure method 'removeNonMoving' properly removes and updates nonMoving list in Handler class")
    public void testRemoveNonMoving() {
        int initialSize = handler.getNonMoving().size();
        handler.removeNonMoving(punishment);
        int finalSize = handler.getNonMoving().size();
        assertEquals(initialSize - 1, finalSize);
    }

    @Test
    @DisplayName("Ensure method 'getFly' properly returns a Fly character")
    public void testGetFly() {
        assertEquals(fly, handler.getFly());
    }

    @Test
    @DisplayName("Ensure method 'charWithSpider' properly checks if any spiders are colliding with the fly.")
    public void testFlySpiderCollision(){
        assertFalse(handler.charWithSpider());

        Spider spider = new Spider(0, 0, null, spiderImg);
        handler.addCharacter(spider);

        assertTrue(handler.charWithSpider());

        handler.removeCharacter(spider);
    }

    @Test
    @DisplayName("Ensure method 'charWithNonMoving' correctly updates the score if the fly is colliding with a NonMoving object.")
    public void testFlyNonMovingCollision(){
        int initialSize = handler.getNonMoving().size();
        Game.score = 0;
        Reward reward = new Reward(0, 0, 100, rewardImg);
        reward.required();
        handler.addNonMoving(reward);

        handler.charWithNonMoving();
        assertEquals(initialSize, handler.getNonMoving().size());
        assertEquals(100, Game.score);


        Reward reward2 = new Reward(1, 1, 150, rewardImg);
        reward2.required();
        handler.addNonMoving(reward2);

        handler.charWithNonMoving();
        assertEquals(initialSize, handler.getNonMoving().size());
        assertEquals(250, Game.score);


        Reward reward3 = new Reward(500, 500, 80, rewardImg);
        reward3.required();
        handler.addNonMoving(reward3);

        handler.charWithNonMoving();
        assertEquals(initialSize + 1, handler.getNonMoving().size());
        assertEquals(250, Game.score);
    }

    @Test
    @DisplayName("Ensure method 'updateBonus' properly affects bonus rewards only")
    public void testUpdateBonus(){
        int initialSize = handler.getNonMoving().size();
        Reward bonus = new Reward(0, 0, 100, rewardImg);
        bonus.setAsBonus();
        handler.addNonMoving(bonus);

        assertTrue(initialSize < handler.getNonMoving().size());

        initialSize = handler.getNonMoving().size();
        handler.updateBonus(5000);//bonus rewards default to 10 seconds so after 5 seconds, it should still be there
        Reward reward = new Reward(0, 0, 100, rewardImg);
        reward.required();
        handler.addNonMoving(reward);

        assertTrue(initialSize <= handler.getNonMoving().size());

        initialSize = handler.getNonMoving().size();
        handler.updateBonus(500000000);//this should remove the bonus reward but not the normal reward
        assertTrue(initialSize > handler.getNonMoving().size());

        handler.removeNonMoving(reward);
    }

}
