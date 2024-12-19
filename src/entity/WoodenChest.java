package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import game.GamePanel;
import item.CommonSword;
import item.HealthPotion;

public class WoodenChest extends Entity{
    BufferedImage image;
    public boolean isOpened, dropped;
    public WoodenChest(GamePanel game_panel){
        super(game_panel);
        isInteractable = true;
        x=0;
        y=0;
        isOpened = false;
        dropped= false;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/game/tileset/wooden_chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(Graphics2D g2d)
    {
        if(isOpened && !dropped)
        {
            game_panel.getEntities().add(1,new CommonSword(game_panel, x+10, y+20)); // Access entities array from GamePanel
            game_panel.getEntities().add(1,new HealthPotion(game_panel, x+20, y+20)); // Access entities array from GamePanel
            dropped = true;
            try {
                image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/game/tileset/wooden_chest_opened.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.draw(g2d);
    }
    private void draw(Graphics2D g2d)
    {
        g2d.drawImage(image, x, y, game_panel.tile_size, game_panel.tile_size, null);
    }
}
