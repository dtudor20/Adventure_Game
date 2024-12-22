package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.FontMetrics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import entity.Bottom;
import entity.BottomCornerLeft;
import entity.BottomCornerRight;
import entity.Candle;
import entity.Door;
import entity.DoorLeft;
import entity.DoorRight;
import entity.Entity;
import entity.Floor;
import entity.Ladder;
import entity.Left;
import entity.Monster;
import entity.Player;
import entity.Right;
import entity.Skeleton;
import entity.Skull;
import entity.Top;
import entity.TopCornerLeft;
import entity.TopCornerRight;
import entity.Vampire;
import entity.WoodenChest;
import item.CommonSword;
import item.HealthPotion;
import item.RareSword;
import item.SilverKey;

public class GamePanel extends JPanel {
    final int original_tile_size = 16;
    final int scale = 2;
    final public int tile_size = original_tile_size * scale;
    final int max_screen_col = 32;
    final int max_screen_row = 18;
    final int screen_width = tile_size * max_screen_col;
    final int screen_height = tile_size * max_screen_row;
    public Set<Point> entityPositions = new HashSet<>();
    private ArrayList<Entity> entities;
    private boolean playerHit;
    private long hitTime;
    private int chestCount = 0;
    public int level=1,old_level=1;
    public int monster_count=0;
    private static final long LEVEL_DISPLAY_DURATION = 4000; // Duration to display the level in milliseconds
    private long levelDisplayTime;
    private static final long HIT_DISPLAY_DURATION = 200; // Duration to display the red tint in milliseconds
    public GamePanel() {
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.decode("#25131A"));
        this.setDoubleBuffered(true);
        entities = new ArrayList<>();
        entities.add(new Player(this));
        entities.get(0).movePosition(screen_width / 2 - 2 * tile_size, screen_height / 2 - 2 * tile_size); // setting the player position in the middle of the screen
        loadCaveMap(level);
    }
      private void loadCaveMap(int level) {
        monster_count=0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("game/CaveMap"+level+".txt")))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    char ch = line.charAt(col);
                    int x = col * tile_size;
                    int y = row * tile_size;
                    switch (ch) {
                        case '0':
                            break;
                        case '1':
                            entities.add(new TopCornerLeft(this));
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case '2':
                            entities.add(new Top(this));
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case '3':
                            entities.add(new TopCornerRight(this));
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case '4':
                            entities.add(new Left(this));
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case '5':
                            entities.add(new Floor(this));
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case '6':
                            entities.add(new Right(this));
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case '7':
                            entities.add(new BottomCornerLeft(this));
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case '8':
                            entities.add(new Bottom(this));
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case '9':
                            entities.add(new BottomCornerRight(this));
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case 'c':
                            chestCount++;
                            if(chestCount==1||chestCount==4||chestCount==5)
                                entities.add(new WoodenChest(this, HealthPotion.class));
                            else if(chestCount==2)
                                entities.add(new WoodenChest(this, CommonSword.class));
                            else if(chestCount==3||chestCount==7 ||chestCount==8)
                                entities.add(new WoodenChest(this, SilverKey.class));
                            else if(chestCount==6)
                                entities.add(new WoodenChest(this, RareSword.class));

                            
                            entities.add(new Floor(this));
                            entities.get(entities.size()-2).movePosition(x, y);
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case 's':
                            entities.add(1,new Skeleton(this));
                            entities.add(new Floor(this));
                            entities.get(1).movePosition(x, y);
                            entities.get(entities.size() - 1).movePosition(x, y);
                            monster_count++;
                            break;
                        case 'v':
                            entities.add(1,new Vampire(this));
                            entities.add(new Floor(this));
                            entities.get(1).movePosition(x, y);
                            entities.get(entities.size() - 1).movePosition(x, y);
                            monster_count++;
                            break;
                        case '[':
                            entities.add(new DoorLeft(this));
                            entities.add(new Floor(this));
                            entities.get(entities.size()-2).movePosition(x, y);
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case ']':
                            entities.add(new DoorRight(this));
                            entities.add(new Floor(this));
                            entities.get(entities.size()-2).movePosition(x, y);
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case 'l':
                            entities.add(new Ladder(this));
                            entities.get(entities.size()-1).movePosition(x, y);
                            break;
                        case ',':
                            entities.add(new Candle(this));
                            entities.add(new Floor(this));
                            entities.get(entities.size()-2).movePosition(x, y);
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case '|':
                            entities.add(new Door(this));
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case '.':
                            entities.add(new Skull(this));
                            entities.add(new Floor(this));
                            entities.get(entities.size()-2).movePosition(x, y);
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                    }
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        levelDisplayTime = System.currentTimeMillis();
    }
    
    public ArrayList<Entity> getEntities() {
        return entities;
    }
    public void setPlayerHit(boolean hit) {
        playerHit = hit;
        hitTime = System.currentTimeMillis();
    }

    public void update() {
        repaint(); // Request a repaint to call paintComponent
    }
    public int  cameraX=0, cameraY=0;
    @Override
    protected void paintComponent(Graphics g) {
        if (old_level != level) {
            // Preserve the player entity
            Player player = (Player) entities.get(0);
            entities.clear();
            entities.add(player); // Re-add the player entity

            loadCaveMap(level);
            old_level = level;
        }

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = entities.size() - 1; i >= 1; i--) {
            entities.get(i).movePosition(cameraX, cameraY);
            entities.get(i).update(g2d);

            // monster collision detection
            if (!(entities.get(i) instanceof Monster)&& !(entities.get(i) instanceof Floor)) {
                int entityRight = entities.get(i).x + tile_size;
                int entityBottom = entities.get(i).y + tile_size;
                for (int xPos = entities.get(i).x; xPos < entityRight; xPos += tile_size) {
                    for (int yPos = entities.get(i).y; yPos < entityBottom; yPos += tile_size) {
                        int entityTileX = xPos / tile_size;
                        int entityTileY = yPos / tile_size;
                        entityPositions.add(new Point(entityTileX, entityTileY));
                    }
                }
                if (entities.get(i) instanceof Door && monster_count == 0) {
                    int doorX = entities.get(i).x;
                    int doorY = entities.get(i).y;
                    entities.add(new Floor(this));
                    entities.get(entities.size() - 1).movePosition(doorX, doorY);
                    entities.remove(i);
                }
            }
                
        }
        entities.get(0).update(g2d);
        if (playerHit && (System.currentTimeMillis() - hitTime) < HIT_DISPLAY_DURATION) {
            g2d.setColor(new Color(255, 0, 0, 50)); // Semi-transparent red
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        if (System.currentTimeMillis() - levelDisplayTime < LEVEL_DISPLAY_DURATION) {
            drawLevel(g2d);
        }
        g2d.dispose();
    }
    private void drawLevel(Graphics2D g2d) {
        String levelText = "Level " + level;
        Font font = new Font("Tahoma", Font.BOLD, 24);
        g2d.setFont(font);
        FontMetrics metrics = g2d.getFontMetrics(font);
        int x = (screen_width - metrics.stringWidth(levelText)) / 2;
        int y = ((screen_height - metrics.getHeight()) / 2) + metrics.getAscent()-screen_height/3;
        g2d.setColor(Color.WHITE);
        g2d.drawString(levelText, x, y);
        if(level==1)
        {
            String instructions = "Use wasd keys to move, e to interact, g to drop and space to use!";
            font = new Font("Tahoma", Font.BOLD, 12);
            g2d.setFont(font);
            metrics = g2d.getFontMetrics(font);
            x = (screen_width - metrics.stringWidth(instructions)) / 2;
            y = ((screen_height - metrics.getHeight()) / 2) + metrics.getAscent()-screen_height/3+30;
            g2d.setColor(Color.WHITE);
            g2d.drawString(instructions, x, y);
        }
    }
}