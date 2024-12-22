package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import game.GamePanel;

public class EnergyBall extends Monster {
    private BufferedImage image;
    private int speed;
    private double directionX;
    private double directionY;
    private int damage;
    public EnergyBall(GamePanel game_panel, int x, int y, double directionX, double directionY, int damage) {
        super(game_panel);
        this.x = x;
        this.y = y;
        this.directionX = directionX;
        this.directionY = directionY;
        speed = 5; // Set the speed of the energy ball
        this.damage=damage;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/monster/energy2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Graphics2D g2d) {
        x += directionX * speed;
        y += directionY * speed;

        // Check for collision with the player
        Player player = (Player) game_panel.getEntities().get(0); // Assuming player is always the first entity
        Rectangle energyBallRect = new Rectangle(x, y, game_panel.tile_size/2, game_panel.tile_size/2);
        Rectangle playerRect = new Rectangle(player.x, player.y, game_panel.tile_size, game_panel.tile_size);
        //g2d.draw(energyBallRect);
        //g2d.draw(playerRect);
        if (energyBallRect.intersects(playerRect)) {
            player.health -= damage; // Apply damage to the player
            System.out.println("Player hit! Remaining health: " + player.health);
            game_panel.setPlayerHit(true); // Set the player hit flag
            game_panel.getEntities().remove(this); // Remove the energy ball after hitting the player
        }

        draw(g2d);
        if (x < 0 || x > game_panel.getWidth() || y < 0 || y > game_panel.getHeight()) {
            game_panel.getEntities().remove(this);
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, x, y, game_panel.tile_size/2, game_panel.tile_size/2, null);
    }
}