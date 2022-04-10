// imports
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serial;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

public class Game extends JPanel implements Runnable, KeyListener { // vererbt JPanel, implementiert Runnable und Keylistener
                                                                    // Runnable ist eine Schnittstelle, die von einer Klasse implementiert werden muss, deren Instanzen von einem Thread ausgeführt werden sollen
                                                                    // Keylistener kann eingabe von der tastatur abrufen

    @Serial
    private static final long serialVersionUID = 1L; // ermöglicht es, Objekte abzurufen/zu speichern
    public static final int GAME_WIDTH = 1600; // breite des Spiels
    public static final int GAME_HEIGHT = 900; // höhe des Spiels

    private final String[] instructions = { "Press now 'S' to start the game", // Tutorial des Spiels
            "Press W to go up", "Press S to go down", "Press space to start/reset the ball",
            "ESC to quit the game"};

    private boolean menu = true; // setzt "menu" zu true: Menu wird angezeigt am Anfang
    private boolean game = false; // setzt "game" zu false: Spiel fängt noch nicht an; erst, wenn es richtig vom Menu aus gestartet wird
    private boolean startGame = true; // startGame (allgemeiner Bool) wird zu true gesetzt

    private Thread t; // timer (t.sleep, ...)
    private boolean running = false; // setzt running auf false, wird für den Konstruktor run() benötigt

    public static int scorePlayer; // setzt einen static int scorePlayer - score-anzeige vom spieler
    public static int scoreBot; // setzt einen static int scoreBot - score-anzeige vom bot
    public static String scorePlayerString = "Player One: 0"; // im spiel steht "Player (One oder Name): 0"
    public static String scoreBotString = "Bot: 0"; // im spiel steht "Bot: 0"

    public String name; // spielname

    protected Player player; // Ruft player aus klasse Player hervor
    protected Player bot; // Ruft bot aus klasse Player hervor
    protected Ball ball; // Ruft ball aus klasse Ball hervor
    public static boolean startDirection; // boolean für den Startpunkt

    //booleans, welche für reibungslose Bewegung benutzt werden
    private boolean up = false;
    private boolean down = false;
    private boolean upBot = false;
    private boolean downBot = false;

    // sql verbindung connection, statement und resultset werden auf null gesetzt um später (z.B. zeile 55) eine verbindung zur datenbank zu erstellen
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public Game(String name) { // konstruktor vom Game mit String name für spielname
        try { // try/catch für verbindung mit mysql server
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pong", "root", "");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        player = new Player(0, 360, 15, 200, ID.PLAYER); // ruft player aus klasse Player hervor und gibt denn ort und breite/höhe an
        bot = new Player(GAME_WIDTH - 21, 360, 15, 200, ID.BOT); // ruft bot aus klasse Bot hervor und gibt denn ort und breite/höhe an
        ball = new Ball(0, 0, 50, 50, ID.BALL); // ruft ball aus klasse Ball hervor und gibt denn ort und breite/höhe an
        this.name = name;
    }

    public void start() { //initialisiert neuer thread und startet diesen
        if (t == null) {
            t = new Thread(this);
        }
        t.start();
        running = true;
    }

    public void run() { //Haupt-spiel loop
        while (running) {
            updateLogic();
            repaint();

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateLogic() { //aktualisiert alle object-positionen und wird benötigt um den "Game State" tu entscheiden
        if (menu) {
            if (down) {
                startGame = true;
                menu = false;
                game = true;
            }
        }

        //Y-Richtung Spieler Bewegung
        else if (game) {
            if (down) {
                player.ySpeed = 3;
            } else if (up) {
                player.ySpeed = -3;
            } else {
                player.ySpeed = 0;
            }

            if (downBot && !upBot) { //Y-Richtung Bot Bewegung
                bot.ySpeed = 1;
            } else if (upBot && !downBot) {
                bot.ySpeed = -1;
            } else {
                bot.ySpeed = 0;
            }

            if (ball.isColliding(player)) { //Kollisionen des Balles gegen den Spieler
                ball.x = 15;
                ball.xSpeed--;
                ball.xSpeed = -ball.xSpeed;
                upBot = false;
                downBot = true;
                ball.bob = 1;
            }

            if (ball.isColliding(bot)) { //Kollisionen des Balles gegen den Bot
                ball.x = GAME_WIDTH - 21 - ball.WIDTH;
                ball.xSpeed++;
                ball.xSpeed = -ball.xSpeed;
            }

            if (ball.bob == 0) { // macht, dass bot nach oben geht wenn ball.y niedriger als 0 ist (siehe Ball.java zeile 49, 54)
                upBot = true;
                downBot = false;
                ball.bob = 1;
            }

            if (bot.y + this.HEIGHT + 30 > Game.GAME_HEIGHT) { //setzt Obergrenze vom Bot fest
                bot.y = Game.GAME_HEIGHT - this.HEIGHT - 30;
                upBot = true;
                downBot = false;
            }

            if (bot.y < 0) { // wenn bot.y kleiner als 0 ist wird bot.y 0 und upbot false und downbot true, also der bot bewegt sich nach oben
                bot.y = 0;
                upBot = false;
                downBot = true;
            }

            player.update(); // objekt logik aktualisieren
            bot.update();
            ball.update();
            scorePlayerString = "Player " + this.name + ": " + scorePlayer;
            scoreBotString = "Bot: " + scoreBot;

            if (scorePlayer == 6 && scorePlayer > scoreBot) { // Win-Condition
                try {
                    resultSet = statement.executeQuery("SELECT player from scoreboard"); // testen ob name schon in der Datenbank erhalten ist

                    ArrayList<String> namearr = new ArrayList<>();
                    boolean checkname = false;
                    while (resultSet.next()) {
                        namearr.add(resultSet.getString("player"));
                    }

                    if (namearr.contains(this.name)) {
                        checkname = true;
                    }

                    if (checkname) {
                        statement.executeUpdate("UPDATE Scoreboard SET score = score + " + scorePlayer + " WHERE player = '" + this.name + "'"); // Datensatz überschreiben
                    } else {
                        statement.executeUpdate("INSERT INTO Scoreboard VALUES(null, '" + name + "', " + scorePlayer + ")"); // Datensatz einfügen

                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                System.out.println("You have won! Restart the application to look at your updated stats in the scoreboard.");
                System.exit(0);
            }

            if (scoreBot == 6 && scoreBot > scorePlayer) { // Lose-Condition

                if (scorePlayer == 0) {
                    System.out.println("You have lost! Restart the application to look at your updated stats in the scoreboard.");
                    System.exit(0);

                }
                try {
                    statement.executeUpdate("INSERT INTO Scoreboard VALUES(null, '" + name + "', " + scorePlayer + ")"); // Datensatz einfügen
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                System.out.println("You have lost! Restart the application to look at your updated stats in the scoreboard.");
                System.exit(0);
            }
        }
    }

    public void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

    @Override // Die Annotation @Override teilt dem Java-Compiler mit, dass wir eine Methode der Oberklasse überschreiben wollen
    public void paintComponent(Graphics g) { //Methode von JPanel, benutzt um objekte anzuzeigen
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.setColor(Color.WHITE);
        if (menu) {
            for (int i = 0; i < instructions.length; i++) {
                drawCenteredString(instructions[i], Game.GAME_WIDTH, Game.GAME_HEIGHT + (i * 45) - 150, g);         //Anleitung wird ausgeschrieben
            }
        } else if (game) {
            g.drawString(scorePlayerString, (Game.GAME_WIDTH / 4) - (scorePlayerString.length() * 8 / 2),
                    Game.GAME_HEIGHT - 40);
            g.drawString(scoreBotString, (Game.GAME_WIDTH / 4 * 3) - (scoreBotString.length() * 8 / 2),
                    Game.GAME_HEIGHT - 40);
            player.render(g);
            bot.render(g);
            ball.render(g);
        }
    }

    public void waitForStart() { // restartet das spiel, aber nur ball wird ihre location zurücksetzen, bot und spieler bleiben am gleichen platz/bewegen sich weiter
        if (startDirection) {
            ball.xSpeed = 1;
            ball.ySpeed = -1;
        } else {
            ball.xSpeed = -1;
            ball.ySpeed = 1;
        }
    }

    //Keyboard inputs ausgelöst vom OS
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }

        if (startGame) {

            if (key == KeyEvent.VK_S) {
                down = true;
            }
            if (key == KeyEvent.VK_W) {
                up = true;
            }
        }

        if (key == KeyEvent.VK_SPACE) {
            ball.reset();
            waitForStart();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { // falls taste nicht mehr gedrückt wird
        int key = e.getKeyCode();

        if (startGame) {

            if (key == KeyEvent.VK_S) {
                down = false;
            }

            if (key == KeyEvent.VK_W) {
                up = false;
            }
        }
    }

    //unbenutzte Methode erfordert vom Keylistener Interface
    @Override
    public void keyTyped(KeyEvent e) {
    }

    // Anfangspunkt
    public static void main(String[] args) {                                //Start der Applikation
        new gui();                                                          //Main menu Start
        JFrame frame = new gui();                                           //Neuer frame im gui
        frame.setTitle("Pong - by Alperen Y. & Connor F. (Page: Main Menu)");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setBackground(Color.black);
    }
}