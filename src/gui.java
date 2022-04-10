import javax.swing.*;
import java.awt.*;

public class gui extends JFrame {
    private JButton startButton;                                                            //----------------------------------
    private JPanel mainPanel;                                                               //deklariert alle JFrame Elemente
    private JButton scrbrd;                                                                 //----------------------------------

    public gui() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        setLocation(0, 0);                                                              //legt Ort des Fensters fest
        setSize(500, 500);                                                       //setzt Grösse fest
        setLocationRelativeTo(null);                                                       //Macht Fenster in die Mitte des Bildschirms
        mainPanel.setBackground(Color.black);
        toFront();                                                                         //---------------------------------------
        requestFocus();                                                                    // schiebt das Fenster in den Vordergrund
        setAlwaysOnTop(true);                                                              //---------------------------------------
        ImageIcon image = new ImageIcon(getClass().getResource("images/icon.png"));   // erstellt Icon oben links am Fenster
        setIconImage(image.getImage());

                                                                                            //---------------------------------------------------------------------
                                                                                            //---------------------------------------------------------------------
        startButton.addActionListener(e -> {                                                //falls der Button "Scoreboard" gedrückt wird, wird Scoreboard geöffnet
            if (e.getSource() == startButton) {                                             //---------------------------------------------------------------------
                new addPlayerName(getSize(), getLocation());                                //---------------------------------------------------------------------
            }
        });
                                                                                            //---------------------------------------------------------------------
                                                                                            // ---------------------------------------------------------------------
        scrbrd.addActionListener(e -> {                                                     //falls der Button "Scoreboard" gedrückt wird, wird Scoreboard geöffnet
            if (e.getSource() == scrbrd) {                                                  //---------------------------------------------------------------------
                new Scoreboard(getSize(), getLocation());                                   //---------------------------------------------------------------------
            }
        });
    }
}
