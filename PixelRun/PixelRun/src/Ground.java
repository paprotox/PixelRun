import java.awt.*;
import java.awt.image.BufferedImage;

public class Ground {
    private BufferedImage ground;

    public Ground() {
        this.ground = ImagesLauncher.getImage("images/ground2.png");
    }

    public void paint(Graphics g) {
        g.drawImage(ground,0,GameWindow.HEIGHT - 55,null);
    }

}
