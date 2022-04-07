import javax.swing.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Scoreboard implements ActionListener{
    private JButton mainback;
    private JLabel title;
    private JPanel panel;
    private JTable table1;
    private JWindow window;

    FontMetrics font;

    Scoreboard(Dimension scale, Point location){
        window = new JWindow();
        // frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.add(panel);
        window.toFront();
        window.requestFocus();
        window.setAlwaysOnTop(true);
        title.setForeground(Color.white);
        window.setSize(scale);
        window.setLocation(location);

        /*title.setFont(new Font("Monaco", Font.PLAIN, 40));
        font = title.getFontMetrics();
        title.drawString("Start", (SCREEN_WIDTH / 2) - (font.stringWidth("Start") / 2), 100);*/

        mainback.addActionListener(this);
        content();
    }

    public void content(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pong", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery("SELECT * from scoreboard Order By score DESC Limit 10");
            ArrayList<Integer> pla = new ArrayList<>();
            ArrayList<String> play = new ArrayList<>();
            ArrayList<Integer> score = new ArrayList<>();
            int platz = 1;
            while (resultset.next()) {
                int scr = resultset.getInt("score");
                String nam = resultset.getString("player");
                pla.add(platz);
                score.add(scr);
                play.add(nam);
                platz++;
            }
            Object[] cols = {"Platz", "Name", "Score"};

            Integer[] play_arr = new Integer[pla.size()];
            play_arr = pla.toArray(play_arr);

            String[] player_arr = new String[play.size()];
            player_arr = play.toArray(player_arr);

            Integer[] score_arr = new Integer[score.size()];
            score_arr = score.toArray(score_arr);

            Object[][] data = {play_arr, player_arr, score_arr};

            table1.setModel(new DefaultTableModel(data, cols));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /* if (e.getSource() == mainback){
            frame.dispose();

            Main.main(state.setstate(0));

        } */

        if(e.getSource() == mainback) {
            window.dispose();
        }
    }
}
