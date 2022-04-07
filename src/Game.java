import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Game extends JPanel implements Runnable, KeyListener {

    private static final long serialVersionUID = 1L;
    public static final int GAME_WIDTH = 1600;
    public static final int GAME_HEIGHT = 900;

    private String[] instructions = { "'A' for One Player (Solo against ", "'S' for Two Player", "'D' for No Player",
            "WASD for Player One Movement", "Arrow Keys for Player Two Movement", "SPACE to Start/Reset ball",
            "ESC to quit", "******Only Two Player is working right now******"};

    private boolean menu = true;
    private boolean game = false;
    private boolean onePlayer = true, twoPlayer = false, noPlayer = false;

    private Thread t;
    private boolean running = false;

    public static int scoreOne;
    public static int scoreTwo;
    public static String scoreOneString;
    public static String scoreTwoString;

    protected Player playerOne;
    protected Player playerTwo;
    protected Ball ball;
    protected static int ballSpeed;
    public static boolean startDirection;

    // BOOLEANS THAT WILL BE USED FOR SMOOTHER MOVEMENT
    private boolean right = false, left = false, up = false, down = false, space = false;
    private boolean rightTwo = false, leftTwo = false, upTwo = false, downTwo = false;

    public Game() {
        playerOne = new Player(0, 360, 15, 200, ID.PLAYER_ONE);
        playerTwo = new Player(GAME_WIDTH - 21, 360, 15, 200, ID.PLAYER_TWO);
        ball = new Ball(0, 0, 50, 50, ID.BALL);
    }

    // INITIALIZES NEW THREAD AND STARTS IT (MAKES IT CALL RUN METHOD)
    public void start() {
        if (t == null) {
            t = new Thread(this);
        }
        t.start();
        running = true;
    }

    // MAIN GAME LOOP (NEEDS REVISING)
    public void run() {
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

    //UPDATES ALL OBJECT POSITIONS AND USED TO DECIDE GAME STATE
    public void updateLogic() {
        if (menu) {
            if (left) {
                onePlayer = true;
                menu = false;
                game = true;
            }
            if (down) {
                onePlayer = false;
                twoPlayer = true;
                menu = false;
                game = true;
            }
            if (right) {
                onePlayer = false;
                noPlayer = true;
                menu = false;
                game = true;
            }
        } else if (game) {
            // X DIRECTION MOVEMENT PLAYER ONE
            if (right)
                playerOne.xSpeed = 3;
            else if (left)
                playerOne.xSpeed = -3;
            else
                playerOne.xSpeed = 0;

            // Y DIRECTION MOVEMENT PLAYER ONE
            if (down)
                playerOne.ySpeed = 3;
            else if (up)
                playerOne.ySpeed = -3;
            else
                playerOne.ySpeed = 0;

            // X DIRECTION MOVEMENT PLAYER TWO
            if (rightTwo)
                playerTwo.xSpeed = 3;
            else if (leftTwo)
                playerTwo.xSpeed = -3;
            else
                playerTwo.xSpeed = 0;

            // Y DIRECTION MOVEMENT PLAYER TWO
            if (downTwo)
                playerTwo.ySpeed = 1;

            if (upTwo)
                playerTwo.ySpeed = -1;

            // if (ball.isColliding(playerOne))
            //     playerTwo.ySpeed = 1;
            // if (ball.isColliding(playerTwo))
            //     playerTwo.ySpeed = 1;

            /* if (playerTwo.ySpeed + playerTwo.HEIGHT >= GAME_HEIGHT)
                playerTwo.ySpeed = GAME_HEIGHT - playerTwo.HEIGHT; */

            if (ball.isColliding(playerOne)) {
                ball.x = 15;
                ball.xSpeed--;
                ball.xSpeed = -ball.xSpeed;
            }
            if (ball.isColliding(playerTwo)) {
                ball.x = GAME_WIDTH - 21 - ball.WIDTH;
                ball.xSpeed++;
                ball.xSpeed = -ball.xSpeed;
            }

            // UPDATE OBJECT LOGIC
            playerOne.update();
            playerTwo.update();
            ball.update();
            scoreOneString = "Player One: " + scoreOne;
            scoreTwoString = "Player Two: " + scoreTwo;
        }
    }

    public void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

    // METHOD OF JPANEL TO BE USED TO SHOW OBJECTS
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.setColor(Color.WHITE);
        if (menu) {
            for (int i = 0; i < instructions.length; i++) {
                drawCenteredString(instructions[i], Game.GAME_WIDTH, Game.GAME_HEIGHT + (i * 45) - 150, g);
            }
        } else if (game) {

            g.drawString(scoreOneString, (Game.GAME_WIDTH / 4) - (scoreOneString.length() * 8 / 2),
                    Game.GAME_HEIGHT - 40);
            g.drawString(scoreTwoString, (Game.GAME_WIDTH / 4 * 3) - (scoreTwoString.length() * 8 / 2),
                    Game.GAME_HEIGHT - 40);
            playerOne.render(g);
            playerTwo.render(g);
            ball.render(g);
        }
    }

    public void waitForStart() {
        while (!space) {

        }
        if (startDirection) {
            ball.xSpeed = 1;
            ball.ySpeed = -1;
        } else {
            ball.xSpeed = -1;
            ball.ySpeed = 1;
        }
    }

    // KEYBOARD INPUT EVENTS TRIGGERED BY OS
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }

        if (onePlayer) {
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                right = true;
            }

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                left = true;
            }

            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                down = true;
            }

            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                up = true;
            }
        } else if (twoPlayer) {
            if (key == KeyEvent.VK_D) {
                right = true;
            }

            if (key == KeyEvent.VK_A) {
                left = true;
            }

            if (key == KeyEvent.VK_S) {
                down = true;
            }

            if (key == KeyEvent.VK_W) {
                up = true;
            }

            if (key == KeyEvent.VK_RIGHT) {
                rightTwo = true;
            }

            if (key == KeyEvent.VK_LEFT) {
                leftTwo = true;
            }

            if (!upTwo)
                downTwo = true;

            if (!downTwo)
                upTwo = true;

            if (playerTwo.ySpeed == 0 && ball.ySpeed >= 0 || ball.ySpeed <= -1)
                upTwo = false;

            if (playerTwo.ySpeed == 0 || !downTwo)
                upTwo = true;

            if (playerTwo.ySpeed == 0 || !upTwo)
                downTwo = true;

            // if (!downTwo)
            //     upTwo = true;
//
            // if (playerTwo.ySpeed == 0 && ball.ySpeed >= 0 || ball.ySpeed <= -1)
            //     upTwo = false;
//
            // if (!upTwo) {
            //     downTwo = true;
            // }
//
            // if (playerTwo.ySpeed == 0 || !downTwo) {
            //     upTwo = true;
            // }
        }

        if (key == KeyEvent.VK_SPACE) {
            space = true;
            ball.reset();
            waitForStart();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (onePlayer) {
            if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                right = false;
            }

            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                left = false;
            }

            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                down = false;
            }

            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                up = false;
            }
        } else if (twoPlayer) {
            if (key == KeyEvent.VK_D) {
                right = false;
            }

            if (key == KeyEvent.VK_A) {
                left = false;
            }

            if (key == KeyEvent.VK_S) {
                down = false;
            }

            if (key == KeyEvent.VK_W) {
                up = false;
            }

            if (key == KeyEvent.VK_RIGHT) {
                rightTwo = false;
            }

            if (key == KeyEvent.VK_LEFT) {
                leftTwo = false;
            }

            // if (key == KeyEvent.VK_DOWN) {
            //     downTwo = false;
            // }
            // if (key == KeyEvent.VK_UP) {
            //     upTwo = false;
            // }
        }
    }

    // UNUSED METHOD REQUIRED BY KEYLISTENER INTERFACE
    @Override
    public void keyTyped(KeyEvent e) {
    }

    // STARTING POINT
    public static void main(String[] args) {
        new gui();
        JFrame frame = new gui();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setBackground(Color.black);
    }
}