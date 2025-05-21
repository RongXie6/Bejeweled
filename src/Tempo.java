import javax.swing.*;
import java.awt.*;

public class Tempo extends JPanel implements Runnable {
    private int progress = 0;

    public int getProgress() {
        return progress;
    }

    public Tempo() {
        this.setSize(800, 30);
        Thread thread = new Thread(this);
        thread.start();
    }
    //simulare il tempo
    @Override
    public void run() {
        while (progress < 800) {
            progress++;
            repaint();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       // JOptionPane.showMessageDialog(this, "The end");
    }
    //disegna il rettangolo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, 800, 30);

        g.setColor(new Color(48, 67, 191));
        g.fillRect(0, 0, progress , 30);
    }
}
