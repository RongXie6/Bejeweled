import javax.swing.*;
import java.awt.*;

public class StartScreen extends JFrame {
    public StartScreen() {
        setTitle("Gemme - StartScreen");
        setSize(620, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Pannello principale con immagine di sfondo
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon("src/Images/BackgroundHQ.png");
                g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null);

        // Etichetta con il titolo
        JLabel titleLabel = new JLabel(new ImageIcon("src/Images/title.png"));
        titleLabel.setBounds(5, 50, 600, 150); // Posizione e dimensione del titolo
        panel.add(titleLabel);

        // Pannello per i bottoni
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(220, 600, 180, 50); // Posizione del pannello

        // Bottone "Start"
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setFocusPainted(false);
        startButton.setBackground(new Color(48, 67, 191)); // Colore blu per il bottone
        startButton.setForeground(Color.WHITE);
        startButton.addActionListener(e -> {
            dispose(); // Chiude la schermata di avvio
            Main.restartGame(); // Avvia il gioco
        });

        // Aggiungi il bottone al pannello
        buttonPanel.add(startButton);

        // Aggiungi il pannello al pannello principale
        panel.add(buttonPanel);

        // Aggiungi il pannello principale alla finestra
        add(panel);
    }
}
