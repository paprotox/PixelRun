import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Board extends JPanel implements Runnable, KeyListener {
    private static final int START_GAME = 0;
    private static final int IS_RUNNING = 1;
    private static final int STOP_GAME = 2;

    private int gameStatus = START_GAME;
    private boolean iskeyPressed;

    private Pixel pixel;
    private Ground ground;
    private Obstacles obstacles;

    private int FPS = 60;

    public Board() {
        pixel = new Pixel();
        pixel.setSpeedX(6.2);
        ground = new Ground();
        obstacles = new Obstacles(pixel);
    }

    public void start() { //this gdzie targetem jest klasa Board ktora implementuje Runnable, i nasz thread odpala metode run
        Thread thread = new Thread(this);
        thread.start();
    }

    public void gameUpdate() {
        if(gameStatus == IS_RUNNING) {
            pixel.update();
            obstacles.update();
            if(obstacles.isCollision()) {
                gameStatus = STOP_GAME;
                pixel.death(true);
            }
        }
    }


    /* GAME LOOP */
    @Override
    public void run() {

        double drawInterval = 1000000000/FPS; //// 1sec/30FPS 0,03333 sec
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (true) {
             gameUpdate();
             repaint();

            try {
                double remainingTIme = nextDrawTime - System.nanoTime(); // time before next draw time
                remainingTIme = remainingTIme/1000000; //thread in milis

                if(remainingTIme < 0) {
                    remainingTIme = 0;
                }

                Thread.sleep((long) remainingTIme);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void paint(Graphics g) {
        ////BACKGROUND////
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        switch (gameStatus) {
            case START_GAME:
                pixel.paint(g);
                ground.paint(g);

                // SCORE TABLE
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN,18));
                g.drawString("PRESS SPACE TO START GAME", 300, 130);
                g.drawString("SCORE  " + pixel.getActualScore(), 480, 30);
                g.drawString("HIGH SCORE  " + pixel.getHighScore(), 640, 30);
                break;

            case IS_RUNNING:

            case STOP_GAME:
                pixel.paint(g);
                ground.paint(g);
                obstacles.paint(g);

                // SCORE TABLE
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN,18));
                g.drawString("SCORE  " + pixel.getActualScore(), 480, 30);
                g.drawString("HIGH SCORE  " + pixel.getHighScore(), 640, 30);

                if(gameStatus == STOP_GAME) {
                    g.drawString("PRESS SPACE TO CONTINUE", 300, 130);
                    g.drawString("GAME OVER", 340, 70);
                }
                break;
        }

    }

    /////////////////////////////////////////////////////////////////
    //USER
    /////////////////////////////////////////////////////////////////

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!iskeyPressed) {
            iskeyPressed = true;
            switch (gameStatus) {
                case START_GAME:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        gameStatus = IS_RUNNING;
                    }
                    break;

                case IS_RUNNING:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        pixel.jump();
                    }
                    break;

                case STOP_GAME:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        gameStatus = IS_RUNNING;
                        resetGame();
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)   {
        iskeyPressed = false;  // false - gdyż inaczej Pixel nie skoczy kolejny raz
    }

    public void resetGame() {
        obstacles.resetObstacles();
        pixel.death(false);
        pixel.resetPixel();
    }
}
