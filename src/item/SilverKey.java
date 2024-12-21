package item;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import entity.DoorRight;
import entity.Entity;
import entity.Player;

import java.io.IOException;
import java.util.Iterator;

import game.GamePanel;

public class SilverKey extends Item{

    BufferedImage image;
    public SilverKey(GamePanel game_panel, int x, int y) {
        super(game_panel, x, y);
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/game/items/silver_key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void use() {
        Player player = (Player) game_panel.getEntities().get(0);
        Iterator<Entity> iterator = game_panel.getEntities().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity instanceof DoorRight) {
                int distanceX = Math.abs(player.x - entity.x);
                int distanceY = Math.abs(player.y - entity.y);
                int interactionRange = game_panel.tile_size; // Adjust the range as needed

                if (distanceX <= interactionRange && distanceY <= interactionRange) {
                    iterator.remove();
                    for (int i = 0; i < player.inventory.length; i++) {
                        if (player.inventory[i] == this) {
                            player.inventory[i] = null;
                            break;
                        }
                    }
                    System.out.println("Right door removed using SilverKey!");
                    break;
                }
            }
        }
    }
    
    @Override
    public void update(Graphics2D g2d) {
        g2d.drawImage(image, x, y, game_panel.tile_size, game_panel.tile_size, null);
    }
}
