import java.awt.Color;
import java.awt.Graphics;

public class Ball extends Entity {

    public Ball(int x, int y, int width, int height, ID id) {
        super(x, y, width, height, id);
        this.x = Game.GAME_WIDTH / 2 - (this.WIDTH / 2);
        this.y = Game.GAME_HEIGHT / 2 - (this.HEIGHT / 2);
    }

    public boolean isColliding(Player player) {
        // if(this.y >= player.y) {
        //     player.y++;
        // }
        // if(this.ySpeed >= player.ySpeed) {
        //     player.ySpeed++;
        // }
        if (player.id == ID.PLAYER_ONE)
            return (this.x < player.WIDTH && ((this.y >= player.y && this.y < player.y + player.HEIGHT)
                    || (this.y + this.HEIGHT >= player.y && this.y + this.HEIGHT < player.y + player.HEIGHT)));
        else
            return (this.x + this.WIDTH > player.x && ((this.y >= player.y && this.y < player.y + player.HEIGHT)
                    || (this.y + this.HEIGHT >= player.y && this.y + this.HEIGHT < player.y + player.HEIGHT)));
    }

    public void reset() {
        this.x = Game.GAME_WIDTH / 2 - (this.WIDTH / 2);
        this.y = Game.GAME_HEIGHT / 2 - (this.HEIGHT / 2);
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    @Override
    public void update() {
        if ((this.x + this.WIDTH) > Game.GAME_WIDTH) {
            Game.scoreOne++;
            Game.startDirection = true;
            reset();
        }
        if (this.x < 0) {
            Game.scoreTwo++;
            Game.startDirection = false;
            reset();
        }
        if (this.y + this.HEIGHT + 30 > Game.GAME_HEIGHT) {
            this.ySpeed = -this.ySpeed;
        }
        if (this.y < 0) {
            this.ySpeed = -this.ySpeed;
        }
        this.x += this.xSpeed;
        this.y += this.ySpeed;

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(this.x, this.y, this.WIDTH, this.HEIGHT);
    }
}