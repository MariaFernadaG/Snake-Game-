import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Jogo extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (WIDTH * HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
    private int[] x = new int[GAME_UNITS];
    private int[] y = new int[GAME_UNITS];
    private int Corpocobra = 6;
    private int applesE;
    private int appleX;
    private int appleY;
    private boolean running = false;
    private char direction = 'R';
    private Timer timer;

    public Jogo() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        Começar();
    }

    public void Começar() {
        CriarMaca();
        running = true;
        timer = new Timer(105, this);
        timer.start();
    }

    public void CriarMaca() {
        appleX = new Random().nextInt((int) (WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = new Random().nextInt((int) (HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Desenhe(g);
    }

    public void Desenhe(Graphics g) {
        if (running) {
            //maçã
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //cobra
            for (int i = 0; i < Corpocobra; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(new Color(45, 180, 0));
                }
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }

            //pontuação
            g.setColor(Color.white);
            g.setFont(new Font("Fonte", Font.BOLD, 40));
            g.drawString("Pontuação: " + applesE, 10, 30);
        } else {
            gameOver(g);
        }
    }

    public void Movimento() {
        for (int i = Corpocobra; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] -= UNIT_SIZE;
                break;
            case 'D':
                y[0] += UNIT_SIZE;
                break;
            case 'L':
                x[0] -= UNIT_SIZE;
                break;
            case 'R':
                x[0] += UNIT_SIZE;
                break;
        }
    }

    public void MacasComidas() {
        if (x[0] == appleX && y[0] == appleY) {
            Corpocobra++;
            applesE++;
            CriarMaca();
        }
    }

    public void VerificarColisoes() {
        //colisões com as bordas
        if (x[0] >= WIDTH || x[0] < 0 || y[0] >= HEIGHT || y[0] < 0) {
            running = false;
        }

        //colisões com o corpo da cobra
        for (int i = 1; i < Corpocobra; i++) {
            if (x[i] == x[0] && y[i] == y[0]) {
                running = false;
            }
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Fonte", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Fim de jogo", (WIDTH - metrics.stringWidth("Fim de jogo")) / 2, HEIGHT / 2);
        g.setColor(Color.white);
        g.setFont(new Font("Fonte", Font.BOLD, 40));
        g.drawString("Pontuação: " + applesE, 350, 450);
    }
   

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            Movimento();
            MacasComidas();
            VerificarColisoes();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
