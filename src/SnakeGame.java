
import javax.swing.*;

public class SnakeGame  extends JFrame{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Jogo da cobrinha");
        Jogo game = new Jogo();
        frame.add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}