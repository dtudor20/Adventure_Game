package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import game.GamePanel;

public class Skeleton extends Monster {
    BufferedImage image_left, image_right, attack_left,attack_right;
    private long lastShotTime;
    private long shotCooldown = 2000; // 2 seconds cooldown between shots
    BufferedImage currentImage;
    public Skeleton(GamePanel game_panel) {
        super(game_panel);
        health = 5;
        damage = 1;
        lastShotTime = 0;

        try {
            image_left = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/monster/skeleton_left.png"));
            image_right = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/monster/skeleton_right.png"));
            attack_left = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/monster/skeleton_left_attacking.png"));
            attack_right = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/monster/skeleton_right_attacking.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Graphics2D g2d) {
        if (health <= 0) {
            game_panel.getEntities().remove(this);
            return;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShotTime >= shotCooldown) {
            shootEnergyBall();
            lastShotTime = currentTime;
        }
        else if(currentTime - lastShotTime < shotCooldown/1.2){
            currentImage= (x > game_panel.getEntities().get(0).x) ? image_left : image_right;
        }
        else{
            currentImage= (x > game_panel.getEntities().get(0).x) ? attack_left : attack_right;
        }

        draw(g2d);
    }
    private void shootEnergyBall() {
        Player player = (Player) game_panel.getEntities().get(0); // Assuming player is always the first entity
    int playerX = player.x + game_panel.tile_size / 2; // Center of the player
    int playerY = player.y + game_panel.tile_size / 2; // Center of the player

    // Calculate the direction vector from the skeleton to the player
    double directionX = playerX - (x + game_panel.tile_size / 2);
    double directionY = playerY - (y + game_panel.tile_size / 2);
    double length = Math.sqrt(directionX * directionX + directionY * directionY);
    
        // Avoid division by zero
        if (length != 0) {
            directionX /= length;
            directionY /= length;
        } else {
            directionX = 0;
            directionY = 0;
        }
    
        EnergyBall energyBall = new EnergyBall(game_panel, x+game_panel.tile_size/2, y+game_panel.tile_size/2, directionX, directionY, damage);
        game_panel.getEntities().add(1,energyBall);
    }
    

    private void draw(Graphics2D g2d) {
        g2d.drawImage(currentImage, x, y, game_panel.tile_size, game_panel.tile_size, null);
    }
}