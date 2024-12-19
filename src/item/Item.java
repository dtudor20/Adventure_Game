package item;

import java.awt.Graphics2D;

import entity.Entity;
import entity.Player;
import game.GamePanel;

public class Item extends Entity{
    public boolean isConsumable;
    public boolean isWeapon;
    public int i,j;
    Item(GamePanel game_panel, int x, int y) {
        super(game_panel);
        isConsumable = false;
        isWeapon = false;
        this.x = x;
        this.y = y;
        // Place i and j at the player's position
        Player player = (Player) game_panel.getEntities().get(0); // Assuming player is always the first entity
        this.i = player.x;
        this.j = player.y;
    }
    public void movePosition(int x, int y)
    {
        this.x += x;
        this.y += y;
    }
    public void update(Graphics2D g2d)
    {
        g2d.drawImage(null, x, y, game_panel.tile_size, game_panel.tile_size, null);
    }
    public void pickUp() {
        Player player = (Player) game_panel.getEntities().get(0); // Assuming player is always the first entity
        int inventorySize = player.getInventory().size();
        if (inventorySize < 5) {
            this.x = inventorySize * game_panel.tile_size;
            this.y = 0;
            player.getInventory().add(this);
            game_panel.getEntities().remove(this);
            System.out.println("Item picked up and added to inventory.");
        } else {
            System.out.println("Inventory is full. Item not picked up.");
        }
    }
    public void use() {
        System.out.println("Item used.");
    }
}
