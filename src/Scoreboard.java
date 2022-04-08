import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Scoreboard implements ActionListener{
    private JButton mainback;
    DefaultListModel Score_Model = new DefaultListModel();
    private JLabel title;
    private JPanel panel;
    private JList Scores;
    private JWindow window;

    FontMetrics font;

    Scoreboard(Dimension scale, Point location){
        window = new JWindow();
        window.setVisible(true);
        window.setSize(scale);
        window.setLocation(location);
        panel.setBackground(Color.black);
        title.setForeground(Color.white);
        Scores.setBackground(Color.black);
        Scores.setForeground(Color.white);

        window.toFront();
        window.requestFocus();
        window.setAlwaysOnTop(true);

        /*title.setFont(new Font("Monaco", Font.PLAIN, 40));
        font = title.getFontMetrics();
        title.drawString("Start", (SCREEN_WIDTH / 2) - (font.stringWidth("Start") / 2), 100);*/

        window.add(panel);
        mainback.addActionListener(this);
        content();
    }

    public void content(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pong", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT * from scoreboard Order By Score DESC Limit 10");
            int numb = 1;
            while (resultset.next()) {
                int scr = resultset.getInt("Score");
                String nam = resultset.getString("player");
                String comb = "" + numb + ". " + scr + " " + nam + "";
                Score_Model.addElement(comb);
                numb++;
            }
            Scores.setModel(Score_Model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* if (e.getSource() == mainback){
            window.dispose();

            Main.main(state.setstate(0));

        } */

        if(e.getSource() == mainback) {
            window.dispose();

        }
    }
}