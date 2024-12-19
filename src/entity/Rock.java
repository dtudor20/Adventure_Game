package entity;

import game.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rock extends Entity {
    private BufferedImage rock;

    public Rock(GamePanel game_panel) {
        super(game_panel);
        try {
            rock = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/game/rock.png"));
        } catch (IOException e) {
            e.printStackTrace();}
    }
    @Override
    public void update(Graphics2D g2d)
    {
        this.draw(g2d);
    }
    private void draw(Graphics2D g2d)
    {
        g2d.drawImage(rock, x, y, game_panel.tile_size, game_panel.tile_size, null);
    }
    
}
