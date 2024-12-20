package item;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import entity.Player;
import game.GamePanel;

public class HealthPotion extends Item {
    private BufferedImage image;
    public HealthPotion(GamePanel game_panel, int x, int y) {
        super(game_panel, x, y);
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/game/items/health_potion.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void use() {
        Player player = (Player) game_panel.getEntities().get(0); // Assuming player is always the first entity
        player.health += 1;
        player.health = Math.min(player.health, 10);// Limit the player health to 10
        // Use an iterator to safely remove the specific instance from the player's inventory
        for (int i = 0; i < 5; i++) {
            if (player.inventory[i] == this) {
                player.inventory[i] = null;
                break;
            }
        }
    }
    @Override
    public void update(java.awt.Graphics2D g2d) {
        g2d.drawImage(image, x, y, game_panel.tile_size, game_panel.tile_size, null);
    }
    
}
