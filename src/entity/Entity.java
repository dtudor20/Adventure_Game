package entity;

import game.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;

public class Entity {
    public int x,y; // position
    public int speed;
    public boolean isInteractable;
    public GamePanel game_panel;
    public void movePosition(int x, int y)
    {
        this.x += x;
        this.y += y;
    }
    public Entity(GamePanel game_panel)
    {
        this.game_panel=game_panel;
        x = 50;
        y = 50;
        speed = 4;
        isInteractable = false;
    }

    public void update(Graphics2D g2d)
    {
        this.draw(g2d);
    }

    private void draw(Graphics2D g2d)
    {
        g2d.setColor(Color.red);
        g2d.fillRect(x, y, game_panel.tile_size, game_panel.tile_size);
    }
    
}
