import javax.swing.*;
import java.awt.*;

public class GameOverScreen extends JFrame {
    private int score;

    public GameOverScreen(int score) {
        this.score = score;
        setTitle("Game Over");
        setSize(620, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Creazione del pannello principale con sfondo personalizzato
        JPanel panel = new JPanel() {
            private Image background = new ImageIcon("src/Images/background.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new BorderLayout());
        add(panel);

        // Etichetta "Game Over"
        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 80));
        gameOverLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        gameOverLabel.setForeground(Color.WHITE);
        panel.add(gameOverLabel, BorderLayout.NORTH);

        // Etichetta per il punteggio
        JLabel scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 40));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));
        panel.add(scoreLabel, BorderLayout.CENTER);

        // Pannello per i bottoni
        JPanel buttonPanel = new JPanel();

        // Bottone "Retry"
        JButton retryButton = new JButton("Retry");
        retryButton.setFont(new Font("Arial", Font.BOLD, 30));
        retryButton.setFocusPainted(false);
        retryButton.setBackground(new Color(48, 67, 191));
        retryButton.setForeground(Color.white);
        retryButton.addActionListener(e -> {
            dispose(); // Chiude la finestra attuale
            Main.restartGame(); // Ritorna al gioco
        });
        buttonPanel.setOpaque(false);
        buttonPanel.add(retryButton);

        // Bottone "Exit"
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 30));
        exitButton.setFocusPainted(false);
        exitButton.setBackground(new Color(48, 67, 191));
        exitButton.setForeground(Color.white);
        exitButton.addActionListener(e -> System.exit(0)); // Esce dal programma
        buttonPanel.add(exitButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        panel.add(buttonPanel, BorderLayout.SOUTH);
    }
}
