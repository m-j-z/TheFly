package boardcomponents;


import gui.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A class the creates and renders the board.
 */
public class TileManager {

    private final GamePanel gp;

    /**
     * Defines the tiles of the game.
     */
    public Tile[] tile;

    /**
     * Defines the type of tile at a specified x and y index.
     */
    public int[][] mapTileNum;

    /**
     * Constructor for a TileManager object
     * @param gp    A GamePanel object
     * @see GamePanel
     */
    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap();
    }

    /**
     * Returns the size of the tiles.
     * @return size of tiles as an int
     */
    public int getTileSize(){
        return gp.tileSize;
    }

    /**
     * Reads the tile images and sets the Tile object appropriately.
     */
    public void getTileImage() {
        try {
            tile[0] = new Tile();
            InputStream tile0 = getClass().getResourceAsStream("/map/web.png");
            assert tile0 != null;
            tile[0].image = ImageIO.read(tile0);

            tile[1] = new Tile();
            InputStream tile1 = getClass().getResourceAsStream("/map/wall.png");
            assert tile1 != null;
            tile[1].image = ImageIO.read(tile1);
            tile[1].collision = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the board layout from a text file.
     */
    public void loadMap() {
        try {
            InputStream input = getClass().getResourceAsStream("/map/mapt1.txt");
            assert input != null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            int col = 0;
            int row = 0;

            while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = reader.readLine();

                while(col < gp.maxScreenCol) {
                    String[] numbers = line.split( " ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the map.
     * @param g2 a Graphics2D object
     * @see Graphics2D
     */
    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {

            int tileNum = mapTileNum[col][row]; // index
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}