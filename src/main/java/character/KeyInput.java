package character;

import gui.GamePanel;
import gui.Handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A class that handles the Player input.
 */
public class KeyInput extends KeyAdapter {

    private final Handler handler;
    private final GamePanel gamePanel;

    /**
     * Constructor for KeyInput
     * @param handler   a Handler object
     * @param gamePanel a GamePanel object
     * @see Handler
     * @see GamePanel
     */
    public KeyInput(Handler handler, GamePanel gamePanel) {
        this.handler = handler;
        this.gamePanel = gamePanel;
    }

    /**
     * Gets the key code from a KeyEvent and moves the Character as specified by the key event.
     * @param event Gets a KeyEvent input
     * @see KeyEvent
     */
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        Character fly = handler.getFly();
        int[] position = fly.getPos();

        if(key == KeyEvent.VK_W) {
            position[1] -= 4;
            if (checkTile(position, Direction.NORTH)) {
                fly.move(0, -4);
            }
        }

        if(key == KeyEvent.VK_S) {
            position[1] += 4;
            if (checkTile(position, Direction.SOUTH)) {
                fly.move(0, 4);
            }
        }

        if(key == KeyEvent.VK_A) {
            position[0] -= 4;
            if (checkTile(position, Direction.WEST)) {
                fly.move(-4, 0);
            }
        }

        if(key == KeyEvent.VK_D) {
            position[0] += 4;
            if (checkTile(position, Direction.EAST)) {
                fly.move(4, 0);
            }
        }

    }

    private boolean checkTile(int[] position, Direction direction) {
        int charLeftWorldX = position[0] + 20;
        int charRightWorldX = position[0] + 20;
        int charTopWorldY = position[1] + 20;
        int charBottomWorldY = position[1] + 20;

        int charLeftCol = charLeftWorldX/gamePanel.tileSize;
        int charRightCol = charRightWorldX/gamePanel.tileSize;
        int charTopRow = charTopWorldY/gamePanel.tileSize;
        int charBottomRow = charBottomWorldY/gamePanel.tileSize;

        int tile1, tile2;

        switch (direction) {
            case NORTH -> {
                charTopRow = (charTopWorldY - 4) / gamePanel.tileSize;
                tile1 = gamePanel.tileM.mapTileNum[charLeftCol][charTopRow];
                tile2 = gamePanel.tileM.mapTileNum[charRightCol][charTopRow];
                if (gamePanel.tileM.tile[tile1].collision || gamePanel.tileM.tile[tile2].collision) {
                    return false;
                }
            }
            case SOUTH -> {
                charBottomRow = (charBottomWorldY + 4) / gamePanel.tileSize;
                tile1 = gamePanel.tileM.mapTileNum[charLeftCol][charBottomRow];
                tile2 = gamePanel.tileM.mapTileNum[charRightCol][charBottomRow];
                if (gamePanel.tileM.tile[tile1].collision || gamePanel.tileM.tile[tile2].collision) {
                    return false;
                }
            }
            case WEST -> {
                charLeftCol = (charLeftWorldX - 4) / gamePanel.tileSize;
                tile1 = gamePanel.tileM.mapTileNum[charLeftCol][charTopRow];
                tile2 = gamePanel.tileM.mapTileNum[charLeftCol][charBottomRow];
                if (gamePanel.tileM.tile[tile1].collision || gamePanel.tileM.tile[tile2].collision) {
                    return false;
                }
            }
            case EAST -> {
                charRightCol = (charRightWorldX + 4) / gamePanel.tileSize;
                tile1 = gamePanel.tileM.mapTileNum[charRightCol][charTopRow];
                tile2 = gamePanel.tileM.mapTileNum[charRightCol][charBottomRow];
                if (gamePanel.tileM.tile[tile1].collision || gamePanel.tileM.tile[tile2].collision) {
                    return false;
                }
            }
        }

        return true;
    }

}
