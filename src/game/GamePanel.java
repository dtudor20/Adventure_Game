package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.swing.JPanel;

import entity.Bottom;
import entity.BottomCornerLeft;
import entity.BottomCornerRight;
import entity.Entity;
import entity.Floor;
import entity.Left;
import entity.Player;
import entity.Right;
import entity.Skeleton;
import entity.Top;
import entity.TopCornerLeft;
import entity.TopCornerRight;
import entity.WoodenChest;

public class GamePanel extends JPanel {
    final int original_tile_size = 16;
    final int scale = 2;
    final public int tile_size = original_tile_size * scale;
    final int max_screen_col = 32;
    final int max_screen_row = 18;
    final int screen_width = tile_size * max_screen_col;
    final int screen_height = tile_size * max_screen_row;
    private ArrayList<Entity> entities;
    private boolean playerHit;
    private long hitTime;
    private static final long HIT_DISPLAY_DURATION = 200; // Duration to display the red tint in milliseconds
    public GamePanel() {
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true);
        entities = new ArrayList<>();
        entities.add(new Player(this));
        entities.get(0).movePosition(screen_width / 2 - 2 * tile_size, screen_height / 2 - 2 * tile_size); // setting the player position in the middle of the screen
        loadCaveMap();
    }
      private void loadCaveMap() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("game/CaveMap.txt")))) {
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
                            entities.add(1,new WoodenChest(this));
                            entities.add(new Floor(this));
                            entities.get(1).movePosition(x, y);
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                        case 's':
                            entities.add(1,new Skeleton(this));
                            entities.add(new Floor(this));
                            entities.get(1).movePosition(x, y);
                            entities.get(entities.size() - 1).movePosition(x, y);
                            break;
                    }
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = entities.size() - 1; i >= 0; i--) {
            entities.get(i).update(g2d);
        }
        if (playerHit && (System.currentTimeMillis() - hitTime) < HIT_DISPLAY_DURATION) {
            g2d.setColor(new Color(255, 0, 0, 50)); // Semi-transparent red
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
        g2d.dispose();
    }
}