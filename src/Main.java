import javax.swing.*;
import java.awt.*;


//Gioco: Gemme
/*Il progetto è organizzato in seguenti classi:
1.Main presenta le funzioni principali per iniziallizare e avviare il gioco
2.StartScreen la finistra dello start del gioco
3.Gemme gestisce l'interfaccia grafica del gioco, visualizzando una griglia 8x8 di bottoni, aggiornando il punteggio e gestendo le azioni del giocatore.
4.GemmeGame contiene la logica del gioco, inclusa la gestione del punteggio e delle azioni di gioco. Gestisce la selezione delle gemme, il loro scambio, e la verifica di combinazioni.
5.Map rappresenta la griglia di gioco 8x8. Ogni cella della griglia contiene un bottone con un'icona (una gemma). Gestisce la logica delle combinazioni di gemme,
 controlla se ci sono combinazioni valide orizzontali o verticali, rimuove le gemme abbinate e fa cadere nuove gemme per riempire gli spazi vuoti.
6.Tempo simulare il tempo
7.GameOver visualizza la finestra di "Game Over", mostrando il punteggio finale e due pulsanti: uno per riavviare il gioco e uno per uscire dal gioco.
*/




public class Main {
    //funzione per avviare il gioco
    public static void restartGame() {
        JFrame f = new JFrame();
        f.setSize(950, 950);
        f.setLocation(0, 0);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setResizable(false);

        // toppanel contiene tempo,punteggi
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBounds(50, 0, 800, 50);
        topPanel.setBackground(Color.darkGray);
        //imposstare il font e size del testo e il loro background
        JLabel punteggioLabel = new JLabel("Punteggio: 0");
        punteggioLabel.setFont(new Font("Arial", Font.BOLD, 30));
        punteggioLabel.setOpaque(false);
        punteggioLabel.setForeground(new Color(4, 21, 124));
        punteggioLabel.setBounds(0, 10, 150, 30);
        topPanel.add(punteggioLabel);

        //il tempo
        Tempo t = new Tempo();
        t.setBounds(50, 60, 800, 30);

        //ogetto gemme che contiene il parte principale del gioco con una matrice 8x8 +le regole impostate
        Gemme g = new Gemme();
        g.setLocation(50, 100);
        g.setSize(800, 800);


        //Funzione asincrona per aggiornare i punteggi
        Timer timer = new Timer(100, e -> {
            punteggioLabel.setText("Punteggio: " + g.getPunteggio());

            if (t.getProgress() >= 800&&!g.isLoose()) {
                g.loose();
                new GameOverScreen(g.getPunteggio()).setVisible(true);
                f.dispose();

            }
        });
        timer.start();

        f.add(topPanel);
        f.add(t);
        f.add(g);

        f.setVisible(true);
    }

    public static void main(String[] args) {
        StartScreen startScreen = new StartScreen();
        startScreen.setVisible(true);
    }
}
