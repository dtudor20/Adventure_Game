import game.GamePanel;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Adventure");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel game_panel = new GamePanel();
        window.add(game_panel);
        
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        while (true) {
            game_panel.update();
            try {
                Thread.sleep(1000 / 60); // Sleep for approximately 16 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}