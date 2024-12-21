package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import game.GamePanel;

public class DoorRight extends Entity {
    BufferedImage door_right;
    public DoorRight(GamePanel game_panel) {
        super(game_panel);
        x = 0;
        y = 0;
        try {
            door_right = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/game/tileset/door_right.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Graphics2D g2d) {
        this.draw(g2d);
    }

    private void draw(Graphics2D g2d) {
        g2d.drawImage(door_right, x, y, game_panel.tile_size, game_panel.tile_size, null);
    }
    
}
