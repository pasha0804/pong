import java.awt.Color;
import java.awt.Graphics;

public class Ball extends Entity {

    public Ball(int x, int y, int width, int height, ID id) {
        super(x, y, width, height, id);
        this.x = Game.GAME_WIDTH / 2 - (this.WIDTH / 2);
        this.y = Game.GAME_HEIGHT / 2 - (this.HEIGHT / 2);
    }

    // Kollisionen
    public boolean isColliding(Player player) {
        if (player.id == ID.PLAYER) {
            return (this.x < player.WIDTH && ((this.y >= player.y && this.y < player.y + player.HEIGHT)
                    || (this.y + this.HEIGHT >= player.y && this.y + this.HEIGHT < player.y + player.HEIGHT)));
        }
        else {
            return (this.x + this.WIDTH > player.x && ((this.y >= player.y && this.y < player.y + player.HEIGHT)
                    || (this.y + this.HEIGHT >= player.y && this.y + this.HEIGHT < player.y + player.HEIGHT)));
        }
    }

    // zurücksetzen des Balles
    public void reset() {
        this.x = Game.GAME_WIDTH / 2 - (this.WIDTH / 2);
        this.y = Game.GAME_HEIGHT / 2 - (this.HEIGHT / 2);
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    public int bob = 1;

    @Override
    public void update() {      // Scores updaten
        if ((this.x + this.WIDTH) > Game.GAME_WIDTH) {
            Game.scorePlayer++;
            Game.startDirection = true;
            reset();
        }
        if (this.x < 0) {
            Game.scoreBot++;
            Game.startDirection = false;
            reset();
        }

        if (this.y + this.HEIGHT + 30 > Game.GAME_HEIGHT) {
            this.ySpeed = -this.ySpeed;
            bob = 0;
        }

        if (this.y < 0) {
            this.ySpeed = -this.ySpeed;
            bob = 0;
        }
        this.x += this.xSpeed;
        this.y += this.ySpeed;

    }

    @Override
    public void render(Graphics g) {        // rendert den Ball
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, this.WIDTH, this.HEIGHT);
    }
}