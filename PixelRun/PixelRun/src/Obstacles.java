import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Obstacles {
    private Pixel pixel;
    private Cactus cactus;
    private List<Cactus> cactusList;


    public Obstacles(Pixel pixel) {
        this.pixel = pixel;
        cactusList = new ArrayList<>();
        cactusList.add(new Cactus(pixel,1000));
    }

    public void update() {
        for(Cactus e : cactusList) {
            e.update();
        }

        ///////// wyrysowanie kolejnego cactusa, gdy poprzedni zniknie z ekranu ////////
        cactus = cactusList.get(0);
        if(cactus.isOutOfScreen()) {
            pixel.scoreUp();
            cactusList.clear();
            cactusList.add(new Cactus(pixel,1000));
        }

    }
    ///// RESET //////
    public void resetObstacles() {
        cactusList.clear();
        cactusList.add(new Cactus(pixel,1000));
    }

    //////////////////

    // WYRYSOWANIE CACTUSÓW //////
    public void paint(Graphics g) {
        for(Cactus e : cactusList) {
            e.paint(g);
        }
    }
    //////////////////////////////
    public boolean isCollision() {
        for(Cactus cactus : cactusList) {
            if(pixel.getBound().intersects(cactus.getBound())) {
                return true;
            }
        }
        return false;
    }



}
