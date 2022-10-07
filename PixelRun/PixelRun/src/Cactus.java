import java.awt.*;
import java.awt.image.BufferedImage;

public class Cactus {
    private double x;
    private BufferedImage cactus;
    public static final int GROUND_Y_CACTUS = 165;

    private Pixel pixel;

    private Rectangle area;

    public Cactus(Pixel pixel, double x) {
        this.pixel = pixel;
        this.x = x;
        cactus = ImagesLauncher.getImage("images/cactus.png");
    }

    public void paint(Graphics g) {
        g.drawImage(cactus,(int) x, GROUND_Y_CACTUS,null);
    }

    public void update() {
        x -= pixel.getSpeedX();
    }

    public boolean isOutOfScreen() {
        if(x < -cactus.getWidth()) {
            return true;
        } else {
            return false;
        }
    }

    public Rectangle getBound() {
        area = new Rectangle();
        area.x = (int) (x + 8);
        area.y = GROUND_Y_CACTUS - 20;
        area.width = (int) (cactus.getWidth() * 0.5);
        area.height = (int) (cactus.getHeight() * 0.5);

        return area;
    }
}
