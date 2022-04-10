import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class addPlayerName extends JFrame {

    private JPanel mainPanel;                                                                        //--------------------------------
    private JLabel textLabel;                                                                        // Deklaration aller JFrame elemente
    private JTextField textField1;                                                                   //--------------------------------
    private JButton gameStartButton;                                                                 //--------------------------------
    private final JFrame frame;                                                                      //--------------------------------

    addPlayerName(Dimension scale, Point location) {

        frame = new JFrame();

        frame.setSize(scale);                                                                         // setzt Grösse des Fensters zur selben grösse wie vorheriges Fens
        frame.setLocation(location);                                                                  // setzt Ort des Fensters zur selben grösse wie vorheriges Fenster

        mainPanel.setBackground(Color.black);

        frame.setVisible(true);
        frame.add(mainPanel);
        frame.setTitle("Pong - by Alperen Y. & Connor F. (Page: Add Player)");

        textLabel.setVisible(true);                                                                  //-------------------------------
        textField1.setVisible(true);                                                                 // Macht Elemente sichtbar
        gameStartButton.setVisible(true);                                                            //-------------------------------
        textField1.setDocument(new EingeschraenkteLaenge(3));                                 // gibt textField1 ein Zeichenlimit

        frame.setResizable(false);                                                                   //---------------------------------------
        frame.toFront();                                                                             // schiebt das Fenster in den Vordergrund
        frame.requestFocus();                                                                        //---------------------------------------
        frame.setAlwaysOnTop(true);                                                                  //---------------------------------------

        ImageIcon image = new ImageIcon(getClass().getResource("images/icon.png"));              // erstellt Icon oben links am Fenster
        frame.setIconImage(image.getImage());

        gameStartButton.addActionListener(e -> {
            if (textField1.getText().length() <= 2 && e.getSource() == gameStartButton) {         //--------------------------------------------------
                gameStartButton.setEnabled(false);                                                // überprüft, ob der eingegebene Text genug lang ist
                System.out.println("NAME MUST BE EXACTLY 3 CHARACTERS!!!");                       //-------------------------------------------------
            }                                                                                     //-------------------------------------------------
            else {
                gameStartButton.setEnabled(true);
            }

            if (e.getSource() == gameStartButton && textField1.getText().length() == 3) {
                gameStartButton.setEnabled(true);

                String name = textField1.getText();                                           //setzt name zum eingegebenen Text

                frame.dispose();                                                               //schliesst Fenster
                Window window = new Window("Pong - by Alperen Y. & Connor F. (Main Game)", Game.GAME_WIDTH, Game.GAME_HEIGHT);    //startet Spiel
                Game game = new Game(name);

                window.addGameInstance(game);
                window.addListener(game);
                game.start();
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                String text = textField1.getText().trim();
                textField1.setText(text);
                super.keyPressed(e);
                char c = e.getKeyChar();

                // prüft, ob der eingegebene Text nur Buchstaben enthält
                textField1.setEditable(Character.isLetter(c) || Character.isISOControl(c));
            }
        });
    }
}