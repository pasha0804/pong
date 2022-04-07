import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addPlayerName extends JFrame {
    private JPanel mainPanel;
    private JLabel textLabel;
    private JTextField textField1;
    private JButton gameStartButton;
    private JFrame frame;

    addPlayerName(Dimension scale, Point location) {

        frame = new JFrame();

        frame.setSize(scale);
        frame.setLocation(location);

        frame.setVisible(true);
        frame.add(mainPanel);

        textLabel.setVisible(true);
        textField1.setVisible(true);
        gameStartButton.setVisible(true);

        frame.toFront();
        frame.requestFocus();
        frame.setAlwaysOnTop(true);

        gameStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == gameStartButton) {
                    frame.dispose();
                    Window window = new Window("Pong", Game.GAME_WIDTH, Game.GAME_HEIGHT);
                    Game game = new Game();

                    window.addGameInstance(game);
                    window.addListener(game);
                    game.start();
                }
            }
        });
    }
}
