import javax.swing.*;
import java.awt.image.BufferedImage;

public class GameWindow extends JFrame {

    public static final int WIDTH = 841; //3364 /4
    public static final int HEIGHT = 270; // 1080 /4

    private Board board;

    public GameWindow() {
        this.setTitle("Pixel Run");
        this.setSize(WIDTH,HEIGHT);
        this.setIconImage(ImagesLauncher.getImage("images/pixel_skok.png"));
        setResizable(false);
        this.setLocationRelativeTo(null); // Umieszczenie okna na środku
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        board = new Board();
        this.addKeyListener(board);
        this.add(board);
    }

    public void startGame() {
        setVisible(true);
        board.start();
    }
}
