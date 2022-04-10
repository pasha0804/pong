import java.awt.*;
import java.awt.event.KeyListener;
import java.io.Serial;
import javax.swing.*;

// Fenster vom Pong-Game
public class Window extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    public Window(String name, int width, int height) {
        setTitle(name);
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setAlwaysOnTop(true);
        ImageIcon image = new ImageIcon(getClass().getResource("images/icon.png"));
        setIconImage(image.getImage());
    }

    // Spiel-Instanz hinzufügen
    public void addGameInstance(Game game) {
        add(game);
    }

    public void addListener(KeyListener listener) {
        this.addKeyListener(listener);
    }
}