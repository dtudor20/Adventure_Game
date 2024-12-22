package entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.GamePanel;
public class Knight extends Monster{
    BufferedImage image_left, image_right, currentImage;
    public Knight(GamePanel game_panel) {
        super(game_panel);
        health = 20;
        damage = 2;
        speed = 1;
        try {
            image_left = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/monster/knight_left.png"));
            image_right = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/monster/knight_right.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(Graphics2D g2d) {
        if (health <= 0) {
            game_panel.getEntities().remove(this);
            game_panel.monster_count--;
            return;
        }
        move();
        currentImage= (x > game_panel.getEntities().get(0).x) ? image_left : image_right;
        draw(g2d);
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(currentImage, x, y, game_panel.tile_size, game_panel.tile_size, null);
    }
}
