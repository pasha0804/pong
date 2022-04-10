import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Scoreboard implements ActionListener{
    private JButton mainback;                                                                      //--------------------------------
    DefaultListModel<Object> Score_Model = new DefaultListModel<>();                               //Deklaration aller JFrame elemente
    private JLabel title;                                                                          //--------------------------------
    private JPanel panel;                                                                          //--------------------------------
    private JList<Object> Scores;                                                                  //--------------------------------
    private final JWindow window;                                                                  //--------------------------------


    Scoreboard(Dimension scale, Point location){

        window = new JWindow();                                                                     //erstellt JWindow
        window.setVisible(true);                                                                    //
        window.setSize(scale);                                                                      //setzt Grösse des Fensters zur selben grösse wie vorheriges Fenster
        window.setLocation(location);                                                               //setzt Ort des Fensters zur selben grösse wie vorheriges Fenster
        panel.setBackground(Color.black);                                                           //Hintergrundfarbe
        title.setForeground(Color.white);                                                           //Vordergrundfarbe
        Scores.setBackground(Color.black);                                                          //Hintergrundfarbe
        Scores.setForeground(Color.white);                                                          //Vordergrundfarbe

        window.toFront();                                                                           //---------------------------------------
        window.requestFocus();                                                                      //schiebt das Fenster in den Vordergrund
        window.setAlwaysOnTop(true);                                                                //---------------------------------------

        String obj = "Rank                     " + "Player                     " + "Score";         //---------------------
        Score_Model.addElement(obj);                                                                //erste Zeile des JList


        window.add(panel);                                                                          // fügt panel zumFenster hinzu
        mainback.addActionListener(this);                                                          // Actionlistener des "Main Menu" Knopf
        content();                                                                                  // führt content aus
    }

    public void content(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pong", "root", "");        // MySQL Verbindung
            Statement statement = connection.createStatement();
            String sql = "SELECT * from scoreboard Order By Score DESC Limit 10";
            ResultSet resultset = statement.executeQuery(sql);                                                                        // MySQL statement
            int numb = 1;
            while (resultset.next()) {                                                                                                // While Loop
                int scr = resultset.getInt("Score");                                                                         // fügt jedes Score der Spieler in der Datenbank zur JList
                String nam = resultset.getString("player");                                                                  // fügt Jeder Spielername in der Datenbank zur JList
                String comb = "" + numb + ".                              " + nam + "                             " + scr + "";       // deklariert "comb"
                Score_Model.addElement(comb);                                                                                         // fügt jeden Spieler und seine Punktzahl zur JList hinzu
                numb++;                                                                                                               //
            }
            Scores.setModel(Score_Model);
        } catch (SQLException e) {                                                                                                    //---------------
            e.printStackTrace();                                                                                                      // Exception catch
        }                                                                                                                             //---------------
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == mainback) {
            window.dispose();

        }
    }
}