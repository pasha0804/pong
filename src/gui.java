import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gui extends JFrame {
    private JButton startButton;
    private JPanel mainPanel;
    private JButton scrbrd;

    public gui() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        setLocation(0, 0);
        setSize(500, 500);
        setLocationRelativeTo(null);
        toFront();
        requestFocus();
        setAlwaysOnTop(true);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton) {
                    new addPlayerName(getSize(), getLocation());
                }
            }
        });
        scrbrd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == scrbrd) {
                    new Scoreboard(getSize(), getLocation());
                }
            }
        });
    }
}
