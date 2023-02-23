package nonmoving;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class NonMovingTest {

    static BufferedImage img;

    static Reward requiredReward;
    static Reward bonusReward;
    static Punishment punishment;

    @BeforeAll
    public static void setUp() {
        URL url = NonMovingTest.class.getResource("/sprites/friendly_fly.png");
        assert url != null;
        try {
            img = ImageIO.read(url);
        } catch (Exception e) {
            System.out.println("Could not load image.");
        }

        requiredReward = new Reward(0, 0, 100, img);
        requiredReward.required();
        bonusReward = new Reward(0, 0, 50, null);
        bonusReward.setAsBonus();
        punishment = new Punishment(0, 0, -100, null);
    }

    @Test
    @DisplayName("Ensure method 'getValue' returns the correct value of object.")
    public void testGetValue() {
        assertEquals(100, requiredReward.getValue());
        assertEquals(50, bonusReward.getValue());
        assertEquals(-100, punishment.getValue());
    }

    @Test
    @DisplayName("Ensure method 'isRequired' returns the correct boolean.")
    public void testIsRequired() {
        assertTrue(requiredReward.isRequired());
        assertFalse(bonusReward.isRequired());
        assertFalse(punishment.isRequired());
    }

    @Test
    @DisplayName("Ensure method 'addCollected' correctly increments the number of required collected.")
    public void testAddCollected() {
        int[] initialRewardAmounts = NonMoving.getRewardAmounts();
        requiredReward.addCollected();
        int[] finalRewardAmounts = NonMoving.getRewardAmounts();

        assertEquals(initialRewardAmounts[1], finalRewardAmounts[1]);
        assertEquals(initialRewardAmounts[0] + 1, finalRewardAmounts[0]);
    }

    @Test
    @DisplayName("Ensure method 'removeCollected' correctly decrements the number of required collected.")
    public void testRemoveCollected() {
        requiredReward.addCollected();
        requiredReward.addCollected();
        requiredReward.addCollected();

        int[] initialRewardAmounts = NonMoving.getRewardAmounts();
        requiredReward.removeCollected();
        int[] finalRewardAmounts = NonMoving.getRewardAmounts();

        assertEquals(initialRewardAmounts[1], finalRewardAmounts[1]);
        assertEquals(initialRewardAmounts[0] - 1, finalRewardAmounts[0]);
    }

    @Test
    @DisplayName("Ensure method 'getPos' returns correct position of NonMoving")
    public void testGetPos() {
        int[] positions = requiredReward.getPos();

        assertEquals(0, positions[0]);
        assertEquals(0, positions[1]);
    }

    @Test
    @DisplayName("Ensure method 'getSize' returns correct size of NonMoving")
    public void testGetSize() {
        int[] size = requiredReward.getSize();
        assertEquals(img.getWidth(), size[0]);
        assertEquals(img.getHeight(), size[1]);
    }

    @Test
    @DisplayName("Ensure method 'setAsBonus' correctly sets the object as a bonus reward.")
    public void testSetAsBonus() {
        int[] initialBonusAmounts = NonMoving.getBonusAmounts();
        bonusReward.setAsBonus();
        int[] finalBonusAmounts = NonMoving.getBonusAmounts();

        assertTrue(bonusReward.bonus);
        assertEquals(initialBonusAmounts[0], finalBonusAmounts[0]);
        assertEquals(initialBonusAmounts[1] + 1, finalBonusAmounts[1]);
    }

    @Test
    @DisplayName("Ensure creation of additional required rewards correctly updates the number of require rewards")
    public void testAddRequiredReward() {
        int[] initialRequired = NonMoving.getRewardAmounts();
        Reward newReward1 = new Reward(0, 0, 100, img);
        newReward1.required();
        Reward newReward2 = new Reward(0, 0, 100, img);
        newReward2.required();
        Reward newReward3 = new Reward(0, 0, 100, img);
        newReward3.required();
        Reward optionalReward = new Reward(0, 0, 100, null);
        optionalReward.setAsBonus();

        int[] finalRequired = NonMoving.getRewardAmounts();

        assertEquals(initialRequired[1] + 3, finalRequired[1]);
    }

}
