package character;

import gui.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static gui.Game.fly;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KeyInputTest {

    static Game game;

    @BeforeAll
    public static void setUp() {
        game = new Game();
    }

    @Test
    @DisplayName("Ensure correct handling of 'W' key input.")
    public void testWKeyInput() {
        int[] initialPos = fly.getPos();

        KeyEvent keyEvent = new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');
        game.getKeyListeners()[0].keyPressed(keyEvent);

        int[] finalPos = fly.getPos();
        assertEquals(initialPos[1] - 4, finalPos[1]);
    }

    @Test
    @DisplayName("Ensure correct handling of 'S' key inputs.")
    public void testSKeyInput() {

        int[] initialPos = fly.getPos();

        KeyEvent keyEvent = new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
        game.getKeyListeners()[0].keyPressed(keyEvent);

        int[] finalPos = fly.getPos();
        assertEquals(initialPos[1] + 4, finalPos[1]);
    }

    @Test
    @DisplayName("Ensure correct handling of 'A' key input.")
    public void testAKeyInput() {

        int[] initialPos = fly.getPos();

        KeyEvent keyEvent = new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
        game.getKeyListeners()[0].keyPressed(keyEvent);

        int[] finalPos = fly.getPos();
        assertEquals(initialPos[0] - 4, finalPos[0]);
    }

    @Test
    @DisplayName("Ensure correct handling of 'D' key input.")
    public void testDKeyInput() {

        int[] initialPos = fly.getPos();

        KeyEvent keyEvent = new KeyEvent(game, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');
        game.getKeyListeners()[0].keyPressed(keyEvent);

        int[] finalPos = fly.getPos();
        assertEquals(initialPos[0] + 4, finalPos[0]);
    }

}
