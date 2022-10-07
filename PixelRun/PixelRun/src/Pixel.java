import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Pixel {
    private double x;
    private double y;
    private double speedX;
    private double speedY;

    private static final int RUN = 0;
    private static final int JUMP = 1;
    private static final int FALL = 2;
    private static final int DEATH = 3;

    public static final float GRAVITY = 0.6f; //0.8f
    public static final int GROUND_Y_PIXEL = 165;

    private int pixelIsDoing = RUN;
    private BufferedImage pixel;
    private BufferedImage pixelJump;

    private Rectangle area;

    ///////////////////////// SCORE ////////////////////////

    public int highScore = 0;
    public int actualScore = 0;

    public int getHighScore() {
        return highScore;
    }

    public int getActualScore() {
        return actualScore;
    }

    ////////////////////////////////////////////////////////

    public Pixel() {
        x = 80;
        y = GROUND_Y_PIXEL;
        pixel = ImagesLauncher.getImage("images/pixel2.png");
        pixelJump = ImagesLauncher.getImage("images/pixeljump.png");
        try {
            Scanner scanner = new Scanner(new FileReader("HIGHSCORE.txt"));
            highScore = scanner.nextInt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Rectangle getBound() {
        area = new Rectangle();
        area.x = (int) x ;
        area.y = (int) y + 2;
        area.width = (int) (pixel.getWidth() * 0.5);
        area.height = (int) (pixel.getHeight() * 0.5);

        return area;
    }

    public void paint(Graphics g) {
        if(pixelIsDoing == RUN) {
            g.drawImage(pixel, (int) x, (int) y, null);
        } else if(pixelIsDoing == JUMP || pixelIsDoing == FALL) {
            g.drawImage(pixelJump, (int) x, (int) y, null);      
        }
    }

    public void update() {
        if (y >= GROUND_Y_PIXEL) {
            y = GROUND_Y_PIXEL;
            pixelIsDoing = RUN; // powrót po skoku do pozycji RUN tekstura
        } else {
            speedY += GRAVITY;
            y += speedY;
        }
    }

    public void jump() {
        if (y >= GROUND_Y_PIXEL) {
            speedY = -13.0f;
            y += speedY;
            pixelIsDoing = JUMP;
        }
    }

    public void death(boolean isDeath) {
        if(isDeath) {
            pixelIsDoing = DEATH;
        } else {
            pixelIsDoing = RUN;
        }
        // HIGH SCORE //
        try {
            FileWriter fw = new FileWriter("HIGHSCORE.txt");
            fw.write(String.valueOf(highScore));
            fw.close();
            System.out.println("NEW HIGH SCORE SAVED");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scoreUp() {
        actualScore += 100;
        // NEW HIGH SCORE
        if(actualScore > highScore) {
            highScore = actualScore;
        }
        // Tempo UP ^
        if(actualScore >= 300 && actualScore % 300 == 0) {
            speedX += 1.0;
        }
    }

    public void resetPixel() {
        y = GROUND_Y_PIXEL;
        actualScore = 0;
    }

    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

}
