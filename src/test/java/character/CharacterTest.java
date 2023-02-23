package character;

import gui.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {

    static BufferedImage img;
    static BufferedImage spiderImg;

    @BeforeAll
    public static void setUp() {
        URL url = CharacterTest.class.getResource("/sprites/fly.png");
        URL spiderURL = CharacterTest.class.getResource("/sprites/spider.png");
        assert url != null;
        assert spiderURL != null;
        try {
            img = ImageIO.read(url);
            spiderImg = ImageIO.read(spiderURL);
        } catch (Exception e) {
            System.out.println("Could not load image.");
        }
    }

    @Test
    @DisplayName("Ensure correct handling of negative x co-ordinates.")
    public void testNegativeXCoordinates() {
        Fly fly = new Fly(0, 0, img);
        fly.move(-1, 0);
        int[] positions = fly.getPos();
        assertEquals(0, positions[0]);
    }

    @Test
    @DisplayName("Ensure correct handling of negative y co-ordinates.")
    public void testNegativeYCoordinates() {
        Fly fly = new Fly(0, 0, img);
        fly.move(0, -1);
        int[] positions = fly.getPos();
        assertEquals(0, positions[1]);
    }

    @Test
    @DisplayName("Ensure correct handling of x co-coordinates outside of window.")
    public void testXCoordinateGreaterThanWindow() {
        Fly fly = new Fly(Game.realWidth - img.getWidth(), Game.realHeight - img.getHeight(), img);
        fly.move(1, 0);
        int[] positions = fly.getPos();
        assertEquals(Game.realWidth - img.getWidth(), positions[0]);
    }

    @Test
    @DisplayName("Ensure correct handling of y co-coordinates outside of window.")
    public void testYCoordinateGreaterThanWindow() {
        Fly fly = new Fly(Game.realWidth - img.getWidth(), Game.realHeight - img.getHeight(), img);
        fly.move(0, 1);
        int[] positions = fly.getPos();
        assertEquals(Game.realHeight - img.getHeight(), positions[1]);
    }

    @Test
    @DisplayName("Ensure method 'getPos' returns correct position of Character")
    public void testGetPos() {
        Fly fly = new Fly(150, 250, img);
        int[] positions = fly.getPos();

        assertEquals(150, positions[0]);
        assertEquals(250, positions[1]);
    }

    @Test
    @DisplayName("Ensure method 'getSize' returns correct size of Character")
    public void testGetSize() {
        Fly fly = new Fly(150, 250, img);

        int[] size = fly.getSize();
        assertEquals(img.getWidth(), size[0]);
        assertEquals(img.getHeight(), size[1]);
    }

    @Test
    @DisplayName("Ensure method 'isSpider' returns correct boolean.")
    public void testIsSpider() {
        Fly fly = new Fly(0, 0, img);
        Spider spider = new Spider(0, 0, null, null);

        assertFalse(fly.isSpider());
        assertTrue(spider.isSpider());
    }

    @Test
    @DisplayName("Ensure method collisionDetection correctly detects collisions from all sides.")
    public void testCollisionDetection() {
        //test with a fly and a spider
        new Game();
        Fly fly = new Fly(300, 300, img);
        Spider spider = new Spider(300, 300, null, spiderImg);

        int[] flySize = fly.getSize();
        int[] spiderSize = spider.getSize();

        //thisRect[0] < otherRect[1] && thisRect[1] > otherRect[0] && thisRect[2] < otherRect[3] && thisRect[3] > otherRect[2]);
        //the on points for each of these are false
        //x1 is fly left < spider right
        //x2 is fly right > spider left
        //x3 is fly top < spider bottom
        //x4 is fly bottom > spider top

        fly.move(0, 1);//set x3 and x4 to values that are in-points

        //x1 changes from on to off, all others are in
        fly.move(-1 * flySize[0], 0);
        assertFalse(fly.movingCollision(spider));
        fly.move(1, 0);
        assertTrue(fly.movingCollision(spider));

        //x2 changes from on to off, all others are in
        fly.move(flySize[0] - 1 + spiderSize[0], 0);
        assertFalse(fly.movingCollision(spider));
        fly.move(-1, 0);
        assertTrue(fly.movingCollision(spider));

        //x3 changes from on to off, all others are in
        fly.move(0, -1 * flySize[1] - 1);
        assertFalse(fly.movingCollision(spider));
        fly.move(0, 1);
        assertTrue(fly.movingCollision(spider));

        //x4 changes from on to off, all others are in
        fly.move(0, flySize[1] - 1 + spiderSize[1]);
        assertFalse(fly.movingCollision(spider));
        fly.move(0, -1);
        assertTrue(fly.movingCollision(spider));
    }

}
