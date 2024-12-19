package entity;

import java.awt.Graphics2D;
import game.GamePanel;

public class Monster extends Entity {
    public int health;
    public int damage;
    public Monster(GamePanel game_panel) {
        super(game_panel);
        x=0;
        y=0;
        health=10;
        damage=1;
    }
    @Override
    public void update(Graphics2D g2d) {
        draw(g2d);
    }
    private void draw(Graphics2D g2d) {
        // Draw monster
    }
    
}
