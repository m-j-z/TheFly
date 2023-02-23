package gui;

import boardcomponents.TileManager;

import javax.swing.*;
import java.awt.*;

/**
 * A class that helps with the rendering of the board.
 */
public class GamePanel extends JPanel {
    /**
     * Defines the tile size.
     */
    public final int tileSize = 32;

    /**
     * Defines the width of the board in terms of tiles.
     */
    public final int maxScreenCol = 30;

    /**
     * Defines the height of the board in terms of tiles.
     */
    public final int maxScreenRow = 20;

    /**
     * Defines the TileManager that handles the creation of the board.
     */
    public TileManager tileM = new TileManager(this);

    /**
     * Constructor for a GamePanel object.
     */
    public GamePanel() {
        int screenWidth = tileSize * maxScreenCol;
        int screenHeight = tileSize * maxScreenRow;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    /**
     * Paints the component using a Graphics object.
     * @param g a Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);

        g2.dispose();
    }

    /**
     * Returns the TileManager object.
     * @return A TileManager object
     */
    public TileManager getTileM() {
        return this.tileM;
    }

}

