import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gemme extends JPanel {
    private JButton[][] caselle; // Matrice di bottoni per la mappa
    private GemmeGame game; // Oggetto gioco principale
    private int punteggio = 0; // Punteggio del gioco
    private Image backgroundImage; // Immagine di sfondo per la schermata
    Timer timer = new Timer(100, (e) -> { // Timer per aggiornare il punteggio ogni 100 ms
        punteggio = game.getPunteggio(); // Ottieni punteggio dal gioco
    });

    public Gemme() {
        // Carica l'immagine di sfondo
        backgroundImage = new ImageIcon("src/Images/GameScreen_bitmaps_0.png").getImage(); // Modifica con il percorso corretto dell'immagine

        // Inizializza l'oggetto di gioco
        game = new GemmeGame();
        caselle = game.getMap().getCaselle(); // Ottieni le caselle dal gioco

        // Imposta il layout del JPanel per permettere la visualizzazione dello sfondo
        setLayout(new BorderLayout());

        // Crea il pannello con la griglia dei bottoni (8x8)
        JPanel buttonPanel = new JPanel(new GridLayout(8, 8, 5, 5));
        buttonPanel.setOpaque(false); // Rende il pannello trasparente per non coprire lo sfondo
        buttonPanel.setPreferredSize(new Dimension(720, 720)); // Imposta la dimensione fissa per il pannello
        buttonPanel.setMaximumSize(new Dimension(720, 720)); // Impedisce al pannello di espandersi

        // Pannello centrale per allineare il pannello dei bottoni
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setOpaque(false); // Rende il pannello centrale trasparente
        centerPanel.setBorder(BorderFactory.createEmptyBorder(55, 0, 0, 0)); // Aggiunge un margine in alto
        centerPanel.add(buttonPanel);

        // Aggiunge i bottoni alla griglia
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = caselle[i][j]; // Ottieni il bottone per la posizione (i, j)

                // Rende il bottone più piccolo
                button.setPreferredSize(new Dimension(30, 30)); // Imposta la dimensione del bottone
                button.setBorderPainted(false); // Rimuove il bordo del bottone
                button.setContentAreaFilled(false); // Rende il bottone trasparente
                button.setOpaque(false);
                button.setFocusable(false); // Impedisce al bottone di acquisire il focus

                // Aggiungi l'azione per quando il bottone viene cliccato
                int finalI = i;
                int finalJ = j;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        game.clicca(finalI, finalJ); // Chiamata al metodo di gioco per gestire il click
                    }
                });

                buttonPanel.add(button); // Aggiungi il bottone al pannello
            }
        }

        // Aggiungi il pannello centrale alla finestra principale
        add(centerPanel, BorderLayout.CENTER);
        timer.start(); // Avvia il timer per aggiornare il punteggio
    }

    // Metodo per disegnare lo sfondo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Chiamata al metodo di base per il disegno
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Disegna lo sfondo
        }
    }

    // Verifica se il gioco è stato perso
    public boolean isLoose() {
        return game.isLoose(); // Restituisce lo stato di "perso" dal gioco
    }

    // Ottieni il punteggio attuale del gioco
    public int getPunteggio() {
        return punteggio;
    }

    // Imposta lo stato del gioco a "perso"
    public void loose() {
        game.setLoose(true); // Imposta lo stato di "perso" nel gioco
    }
}
