import java.awt.Graphics;

public abstract class Entity {
    protected int x;
    protected int y;
    protected final int WIDTH;
    protected final int HEIGHT;
    protected double xSpeed;
    protected double ySpeed;
    protected ID id;

    public Entity(int x, int y, int width, int height, ID id) {                       //definiert Koordinaten und Dimensionen
        this.x = x;
        this.y = y;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.id = id;
    }

    public String toString() {
        return "\nCoordinates: (" + this.x + ", " + this.y + ") \nxSpeed = " + this.xSpeed + "\nySpeed = " + this.ySpeed
                + "\nID = " + this.id;
    }

    public void update() {
    }

    public void render(Graphics g) {
    }
}