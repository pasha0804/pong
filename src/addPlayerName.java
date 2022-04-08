import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class addPlayerName extends JFrame {
    private JPanel mainPanel;
    private JLabel textLabel;
    private JTextField textField1;
    private JButton gameStartButton;
    private JComboBox difficulty;
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
        textField1.setDocument(new LengthRestrictedDocument(3));

        frame.setResizable(false);
        frame.toFront();
        frame.requestFocus();
        frame.setAlwaysOnTop(true);

        gameStartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().length() <= 2 && e.getSource() == gameStartButton) {
                    gameStartButton.setEnabled(false);
                    System.out.println("NAME MUST BE EXACTLY 3 CHARACTERS!!!");
                    // frame.dispose();
                }
                else {
                    gameStartButton.setEnabled(true);
                }

                if (e.getSource() == gameStartButton && textField1.getText().length() == 3) {
                        gameStartButton.setEnabled(true);

                        String name = textField1.getText();


                        frame.dispose();
                        Window window = new Window("Pong", Game.GAME_WIDTH, Game.GAME_HEIGHT);
                        Game game = new Game(name);

                        window.addGameInstance(game);
                        window.addListener(game);
                        game.start();
                }
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                String text = textField1.getText().trim();
                textField1.setText(text);
                super.keyPressed(e);
                char c = e.getKeyChar();

                if(Character.isLetter(c) || Character.isISOControl(c)) {
                    textField1.setEditable(true);
                }
                else {
                    textField1.setEditable(false);
                }
            }
        });

        // textField1.getDocument().addDocumentListener(new DocumentListener() {
        //     public void changedUpdate(DocumentEvent e) {
        //         changed();
        //     }
        //     public void removeUpdate(DocumentEvent e) {
        //         changed();
        //     }
        //     public void insertUpdate(DocumentEvent e) {
        //         changed();
        //     }

        //     public void changed() {
        //         if (textLabel.getText().equals("")){
        //             gameStartButton.setEnabled(false);
        //         }
        //         else {
        //             gameStartButton.setEnabled(true);
        //         }
        //     }
        // });
    }
}
