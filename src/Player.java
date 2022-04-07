import java.awt.Color;
import java.awt.Graphics;

public class Player extends Entity {

    public Player(int x, int y, int width, int height, ID id) {
        super(x, y, width, height, id);
    }

    public void update() {

        if (this.id == ID.PLAYER_ONE) {
            if (this.x < 0) {
                this.x = 0;
            }
            if (this.x + this.WIDTH > this.WIDTH) {
                this.x = 0;
            }
            if (this.y < 0) {
                this.y = 0;
            }
            if (this.y + this.HEIGHT + 30 > Game.GAME_HEIGHT) {
                this.y = Game.GAME_HEIGHT - this.HEIGHT - 30;
            }

            this.x += this.xSpeed;
            this.y += this.ySpeed;
        }

        if (this.id == ID.PLAYER_TWO) {

            if (this.y < 0) {
                this.y = 0;
            }

            if (this.y + this.HEIGHT + 30 > Game.GAME_HEIGHT) {
                this.y = Game.GAME_HEIGHT - this.HEIGHT - 30;
            }

            this.x = Game.GAME_WIDTH - 21;
            this.y += this.ySpeed;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, this.WIDTH, this.HEIGHT);
    }

}