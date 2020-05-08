import javax.swing.*;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

public class Game {

    public static void main(String[] args) {
        File myObj = new File("topten.txt");

        JFrame jFrame;

        jFrame = new JFrame("Tetris");
        jFrame.setSize(Constants.WIDTH, Constants.HEIGHT);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        Board board=new Board();
        jFrame.add(board);
        jFrame.addKeyListener(board);

        jFrame.setVisible(true);
    }
}
