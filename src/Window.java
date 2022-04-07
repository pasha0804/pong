import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Window extends JFrame {
    private static final long serialVersionUID = 1L;
    private JButton Start;
    private JPanel panel;

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
    }

    public void addGameInstance(Game game) {
        add(game);
    }

    public void addListener(KeyListener listener) {
        this.addKeyListener(listener);
    }
}