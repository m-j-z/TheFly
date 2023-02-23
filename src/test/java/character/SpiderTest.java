package character;

import gui.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpiderTest {

    static BufferedImage img;

    @BeforeAll
    public static void setUp() {
        new Game();
        URL url = SpiderTest.class.getResource("/sprites/spider.png");
        assert url != null;
        try {
            img = ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("Could not load image.");
        }
    }

    @Test
    @DisplayName("Ensure method 'patrol' is handled correctly for y co-ordinates.")
    public void testYPatrol() {
        int[][] spiderPath = {{0, 1}};
        Spider spider = new Spider(275, 300, spiderPath, img);

        spider.patrol();
        int[] finalPos = spider.getPos();
        assertEquals(301, finalPos[1]);
    }

    @Test
    @DisplayName("Ensure method 'patrol' is handled correctly for x co-ordinates.")
    public void testXPatrol() {
        int[][] spiderPath = {{1, 0}};
        Spider spider = new Spider(275, 300, spiderPath, img);

        spider.patrol();
        int[] finalPos = spider.getPos();
        assertEquals(276, finalPos[0]);
    }

    @Test
    @DisplayName("Ensure method 'patrol' handles path of length 0")
    public void testZeroPath() {
        Spider spider = new Spider(275, 300, null, img);

        spider.patrol();
        int[] finalPos = spider.getPos();
        assertEquals(275, finalPos[0]);
        assertEquals(300, finalPos[1]);
    }

    @Test
    @DisplayName("Ensure method 'patrol' handles path of length 1")
    public void testOnePath() {
        int[][] spiderPath = {{1, 0}};
        Spider spider = new Spider(275, 300, spiderPath, img);

        int[] initialPos, finalPos;

        initialPos = spider.getPos();
        for(int i = 0; i < 32; i++) {
            spider.patrol();
        }
        finalPos = spider.getPos();

        assertEquals(initialPos[0] + 32, finalPos[0]);
        assertEquals(initialPos[1], finalPos[1]);

        initialPos = spider.getPos();
        for(int i = 0; i < 32; i++) {
            spider.patrol();
        }
        finalPos = spider.getPos();

        assertEquals(initialPos[0] - 32, finalPos[0]);
        assertEquals(initialPos[1], finalPos[1]);

    }

    @Test
    @DisplayName("Ensure method 'patrol' handles path of length > 1")
    public void testGreaterOnePath() {
        int[][] spiderPath = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Spider spider = new Spider(275, 300, spiderPath, img);

        int[] initialPos, finalPos;

        initialPos = spider.getPos();
        for(int i = 0; i < 32; i++) {
            spider.patrol();
        }
        finalPos = spider.getPos();

        assertEquals(initialPos[0] + 32, finalPos[0]);
        assertEquals(initialPos[1], finalPos[1]);

        initialPos = spider.getPos();
        for(int i = 0; i < 32; i++) {
            spider.patrol();
        }
        finalPos = spider.getPos();

        assertEquals(initialPos[0] - 32, finalPos[0]);
        assertEquals(initialPos[1], finalPos[1]);

        initialPos = spider.getPos();
        for(int i = 0; i < 32; i++) {
            spider.patrol();
        }
        finalPos = spider.getPos();

        assertEquals(initialPos[0], finalPos[0]);
        assertEquals(initialPos[1] + 32, finalPos[1]);

        initialPos = spider.getPos();
        for(int i = 0; i < 32; i++) {
            spider.patrol();
        }
        finalPos = spider.getPos();

        assertEquals(initialPos[0], finalPos[0]);
        assertEquals(initialPos[1] - 32, finalPos[1]);

        initialPos = spider.getPos();
        for(int i = 0; i < 32; i++) {
            spider.patrol();
        }
        finalPos = spider.getPos();

        assertEquals(initialPos[0], finalPos[0]);
        assertEquals(initialPos[1] + 32, finalPos[1]);

        initialPos = spider.getPos();
        for(int i = 0; i < 32; i++) {
            spider.patrol();
        }
        finalPos = spider.getPos();

        assertEquals(initialPos[0], finalPos[0]);
        assertEquals(initialPos[1] - 32, finalPos[1]);

        initialPos = spider.getPos();
        for(int i = 0; i < 32; i++) {
            spider.patrol();
        }
        finalPos = spider.getPos();

        assertEquals(initialPos[0] + 32, finalPos[0]);
        assertEquals(initialPos[1], finalPos[1]);

        initialPos = spider.getPos();
        for(int i = 0; i < 32; i++) {
            spider.patrol();
        }
        finalPos = spider.getPos();

        assertEquals(initialPos[0] - 32, finalPos[0]);
        assertEquals(initialPos[1], finalPos[1]);
    }

}
