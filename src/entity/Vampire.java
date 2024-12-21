package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.GamePanel;
public class Vampire extends Monster{
    BufferedImage image_left, image_right, currentImage;
    public Vampire(GamePanel game_panel) {
        super(game_panel);
        health = 5;
        damage = 1;
        speed = 2;
        try {
            image_left = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/monster/vampire_left.png"));
            image_right = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/monster/vampire_right.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(Graphics2D g2d) {
        move();
        currentImage= (x > game_panel.getEntities().get(0).x) ? image_left : image_right;
        draw(g2d);
    }
    public void draw(Graphics2D g2d) {
        g2d.drawImage(currentImage, x, y, game_panel.tile_size, game_panel.tile_size, null);
    }
}
