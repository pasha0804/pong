import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class dbconnect {
    private String player;
    private int score;

    dbconnect(String player, int score) {
        this.player = player;
        this.score = score;

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/3306", "root", "");
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO scoreboard(player, score) VALUES('" + player + "', " + score + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}