package boardcomponents;

import gui.GamePanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TileManagerTest {

    GamePanel gp;
    public Tile[] tile = new Tile[10];
    public int[][] mapTileNum;
    TileManager tileManager;

    @BeforeEach
    void setup() {
        this.gp = new GamePanel();
        assertNotNull(gp);
        tileManager = new TileManager(gp);

        tile = new Tile[10];
        assertNotNull(tile);
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        assertNotNull(mapTileNum);
    }


    @Test
    @DisplayName("Ensure tile images are read correctly")
    public void testGetTileImage() {
        try {
            tile[0] = new Tile();
            assertNotNull(tile[0]);
            InputStream tile0 = getClass().getResourceAsStream("/map/web.png");
            assertNotNull(tile0);
            tile[0].image = ImageIO.read(tile0);

            tile[1] = new Tile();
            assertNotNull(tile[1]);
            InputStream tile1 = getClass().getResourceAsStream("/map/wall.png");
            assertNotNull(tile1);
            tile[1].image = ImageIO.read(tile1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Ensure map loads")
    public void testLoadMap() {
        InputStream input = getClass().getResourceAsStream("/map/mapt1.txt");
        assertNotNull(input);


    }




}
