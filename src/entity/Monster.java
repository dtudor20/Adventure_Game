package entity;

import java.awt.Graphics2D;
import java.awt.Point;
import game.GamePanel;

public class Monster extends Entity {
    public int health;
    public int damage;
    public int cooldown;
    public long cooldown_time;

    public Monster(GamePanel game_panel) {
        super(game_panel);
        x = 0;
        y = 0;
        health = 10;
        damage = 1;
        cooldown = 1000;
    }

    private boolean isOccupied(int newX, int newY) {
        int tileX = newX / game_panel.tile_size;
        int tileY = newY / game_panel.tile_size;
        // Check if the new position is occupied
        if (game_panel.entityPositions.contains(new Point(tileX, tileY))) {
            return true;
        }

        // Check if the vampire is near the player and damage the player if they are
        // close
        Player player = (Player) game_panel.getEntities().get(0);
        if (this instanceof Vampire) {
            int distanceX = Math.abs(player.x - this.x);
            int distanceY = Math.abs(player.y - this.y);
            int attackRange = game_panel.tile_size / 2; // Adjust the range as needed

            if (distanceX <= attackRange && distanceY <= attackRange
                    && cooldown_time + cooldown < System.currentTimeMillis()) {
                player.health -= this.damage;
                System.out.println("Player damaged by vampire! Health: " + player.health);
                cooldown_time = System.currentTimeMillis();
            }
        }

        return false;
    }

    public void move() {
        int newX = x;
        int newY = y;

        // Determine new X position
        if (x > game_panel.getEntities().get(0).x) {
            newX -= speed;
        } else {
            newX += speed;
        }

        // Check for occupancy before updating X position
        if (!isOccupied(newX, y)) {
            x = newX;
        } else {
            // Move away from the player
            if (x > newX) {
                x += speed;
            } else {
                x -= speed;
            }
        }
        // Determine new Y position
        if (y > game_panel.getEntities().get(0).y) {
            newY -= speed;
        } else {
            newY += speed;
        }

        // Check for occupancy before updating Y position
        if (!isOccupied(x, newY)) {
            y = newY;
        } else {
            // Move away from the player
            if (y >newY) {
                y += speed;
            } else {
                y -= speed;
            }
        }

    }

    @Override
    public void update(Graphics2D g2d) {
        draw(g2d);
    }

    private void draw(Graphics2D g2d) {
        // Draw monster
    }

}