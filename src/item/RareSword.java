package item;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import entity.Entity;
import entity.Monster;
import entity.Player;
import game.GamePanel;

public class RareSword extends CommonSword {
    BufferedImage image, flipped_image, mirrored_image;
    public RareSword(GamePanel game_panel, int x, int y) {
        super(game_panel,x, y);
        range = 20;
        damage = 2;
        cooldown = 0.5f;
        attack_time = 0.2f;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/game/items/rare_sword.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        flipped_image = createFlipped(image);
        mirrored_image = createMirrored(image);
        
    }
    @Override
    public void update(Graphics2D g2d) {
        Player player = (Player) game_panel.getEntities().get(0); // Assuming player is always the first entity
        int playerX = player.x;
        int playerY = player.y;

        if (isAttacking) {
            // Calculate the rotation angle
            long elapsedTime = System.currentTimeMillis() - attackStartTime - 100;
            float progress = (float) elapsedTime / (attack_time * 1000);
            double angle = Math.PI / 4 * progress; // Rotate from 0 to 45 degrees

            // Save the original transform
            java.awt.geom.AffineTransform originalTransform = g2d.getTransform();

            // Define the attack area based on the player's direction and sword range
            int attackX = playerX;
            int attackY = playerY;
            int attackWidth = game_panel.tile_size / 2;
            int attackHeight = game_panel.tile_size / 2;

            switch (direction) {
                case "front":
                    attackY += game_panel.tile_size;
                    attackHeight += range;
                    g2d.rotate(angle, playerX + game_panel.tile_size / 2, playerY + game_panel.tile_size / 2);
                    g2d.drawImage(flipped_image, playerX, playerY + game_panel.tile_size, game_panel.tile_size,
                            game_panel.tile_size, null);
                    break;
                case "back":
                    attackY -= range;
                    attackHeight += range;
                    g2d.rotate(-angle, playerX + game_panel.tile_size / 2, playerY + game_panel.tile_size / 2);
                    g2d.drawImage(image, playerX, playerY - game_panel.tile_size, game_panel.tile_size,
                            game_panel.tile_size, null);
                    break;
                case "left":
                    attackX -= range;
                    attackWidth += range;
                    g2d.rotate(-angle, playerX + game_panel.tile_size / 2, playerY + game_panel.tile_size / 2);
                    g2d.drawImage(mirrored_image, playerX - game_panel.tile_size, playerY, game_panel.tile_size,
                            game_panel.tile_size, null);
                    break;
                case "right":
                    attackX += game_panel.tile_size;
                    attackWidth += range;
                    g2d.rotate(angle, playerX + game_panel.tile_size / 2, playerY + game_panel.tile_size / 2);
                    g2d.drawImage(image, playerX + game_panel.tile_size, playerY, game_panel.tile_size,
                            game_panel.tile_size, null);
                    break;
            }

            // Check for collisions with monsters using sword range
            java.awt.Rectangle attackArea = new java.awt.Rectangle(attackX, attackY, attackWidth, attackHeight);
            for (Entity entity : game_panel.getEntities()) {
                if (entity instanceof Monster) {
                    Monster monster = (Monster) entity;
                    //g2d.draw(attackArea);
                    java.awt.Rectangle monsterArea = new java.awt.Rectangle(monster.x,
                            monster.y , game_panel.tile_size, game_panel.tile_size);
                    //g2d.draw(monsterArea);
                    if (attackArea.intersects(monsterArea) && !hitMonsters.contains(monster)) {
                        monster.health -= damage; // Apply damage to the monster
                        hitMonsters.add(monster); // Track the monster as hit
                        System.out.println("Monster hit! Remaining health: " + monster.health);
                    }
                }
            }

            // Restore the original transform
            g2d.setTransform(originalTransform);

            // Check if the attack duration has passed
            if (elapsedTime > attack_time * 1000) {
                isAttacking = false;
            }
        } else {
            // Draw the normal image
            g2d.drawImage(image, x, y, game_panel.tile_size, game_panel.tile_size, null);
            if (lifetime + 60000 < System.currentTimeMillis()) {
                game_panel.getEntities().remove(this);
            }
        }
    }
}