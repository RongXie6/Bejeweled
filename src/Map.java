import javax.swing.*;
import java.util.Random;

public class Map {
    private JButton[][] caselle = new JButton[8][8];  // Griglia di caselle
    private int punteggio = 0;  // Punteggio del giocatore
    private int n_trovato = 0;  // Contatore per il numero di combinazioni trovate
    private boolean isRunning = true;  // Flag per controllare se il gioco è in esecuzione

    // Costruttore della classe Map, inizializza la mappa
    public Map() {
        initializeMap();
    }

    // Restituisce la griglia delle caselle
    public JButton[][] getCaselle() {
        return caselle;
    }

    // Inizializza la mappa con immagini casuali senza combinazioni immediate
    private void initializeMap() {
        Random rand = new Random();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int n = rand.nextInt(6) + 1;  // Genera un numero casuale tra 1 e 6
                caselle[i][j] = new JButton(new ImageIcon("src/Images/image" + n + ".png"));
            }
        }
        // Controlla e stacca eventuali combinazioni immediate
        controllaEStacca();
    }

    // Controlla se ci sono combinazioni valide nella mappa
    public int controlla() {
        int n = 0;

        // Orizzontale: controlla tutte le combinazioni orizzontali
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 6; j++) {
                if (controllaMatch(i, j, 0, 1)) { // Orizzontale
                    n++;
                }
            }
        }

        // Verticale: controlla tutte le combinazioni verticali
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                if (controllaMatch(i, j, 1, 0)) { // Verticale
                    n++;
                }
            }
        }

        return n;
    }

    // Verifica se ci sono combinazioni possibili nello stato attuale della mappa
    public boolean verifica() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (caselle[i][j].getIcon() != null) {
                    // Controllo orizzontale
                    if (j < 7 && caselle[i][j + 1].getIcon() != null) {
                        n_trovato += scambia(i, j, i, j + 1);
                        n_trovato += scambia(i, j + 1, i, j);
                        if (n_trovato > 0) {
                            n_trovato = 0;
                            return true;  // Se trovata una combinazione ritorna true
                        }
                    }
                    // Controllo verticale
                    if (i < 7 && caselle[i + 1][j].getIcon() != null) {
                        n_trovato += scambia(i, j, i + 1, j);
                        n_trovato += scambia(i + 1, j, i, j);
                        if (n_trovato > 0) {
                            n_trovato = 0;
                            return true;  // Se trovata una combinazione ritorna true
                        }
                    }
                }
            }
        }
        return false;
    }

    // Scambia due caselle e chiama la funzione di controllo
    public int scambia(int r1, int c1, int r2, int c2) {
        String imgName1 = ((ImageIcon) caselle[r1][c1].getIcon()).getDescription();
        String imgName2 = ((ImageIcon) caselle[r2][c2].getIcon()).getDescription();
        caselle[r1][c1].setIcon(new ImageIcon(imgName2));
        caselle[r2][c2].setIcon(new ImageIcon(imgName1));
        return controlla();  // Ritorna il numero di combinazioni trovate
    }

    // Controlla e stacca tutte le combinazioni trovate nella mappa
    public synchronized void controllaEStacca() {
        Thread thread = new Thread(() -> {
            boolean trovato = true;
            while (trovato) {
                trovato = false;

                // Orizzontale: stacca tutte le combinazioni orizzontali
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 6; j++) {
                        if (controllaMatch(i, j, 0, 1)) {
                            staccaMatch(i, j, 0, 1);
                            trovato = true;
                        }
                    }
                }

                // Verticale: stacca tutte le combinazioni verticali
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 8; j++) {
                        if (controllaMatch(i, j, 1, 0)) {
                            staccaMatch(i, j, 1, 0);
                            trovato = true;
                        }
                    }
                }

                // Se il gioco non è in fase di caricamento, attendi 200 ms prima di far cadere i diamanti
                if (!isRunning) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Fai cadere i diamanti
                lasciaCadere();

                // Rigenera le immagini mancanti
                rigeneraImmagini();
            }
            isRunning = false;
        });

        // Avvia il thread e attendi che finisca prima di azzerare il punteggio
        thread.start();
        if (isRunning) {
            System.out.println("Sta caricando la mappa...");
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            punteggio = 0;  // Reset punteggio
        }
    }

    // Controlla se ci sono combinazioni di almeno 3 gemme uguali orizzontali o verticali
    private boolean controllaMatch(int r, int c, int dr, int dc) {
        if (caselle[r][c].getIcon() == null) return false;

        String imgName = ((ImageIcon) caselle[r][c].getIcon()).getDescription();
        int count = 1;

        // Controllo nella direzione data (orizzontale o verticale)
        for (int k = 1; k < 8; k++) {
            int nr = r + k * dr;
            int nc = c + k * dc;
            if (nr >= 8 || nc >= 8 || caselle[nr][nc].getIcon() == null) {
                break;
            }
            if (!((ImageIcon) caselle[nr][nc].getIcon()).getDescription().equals(imgName)) {
                break;
            }
            count++;
        }

        // Ulteriore controllo in direzione opposta (verticale o orizzontale)
        if (dr != 0) {
            for (int k = 1; k < 8; k++) {
                int nr = r - k * dr;
                int nc = c - k * dc;

                if (nr < 0 || nr >= 8 || nc < 0 || nc >= 8 || caselle[nr][nc].getIcon() == null) {
                    break;
                }
                if (!((ImageIcon) caselle[nr][nc].getIcon()).getDescription().equals(imgName)) {
                    break;
                }
                count++;
            }
        }
        return count >= 3;  // Ritorna true se c'è almeno una combinazione di 3
    }

    // Rimuove le gemme abbinate
    private void staccaMatch(int r, int c, int dr, int dc) {
        String imgName = ((ImageIcon) caselle[r][c].getIcon()).getDescription();
        int count = 0;
        int tempc = 0;
        int tempr = 0;

        // Conta il numero di gemme abbinate
        for (int k = 0; k < 8; k++) {
            int nr = r + k * dr;
            int nc = c + k * dc;
            if (nr >= 8 || nc >= 8 || caselle[nr][nc].getIcon() == null ||
                    !((ImageIcon) caselle[nr][nc].getIcon()).getDescription().equals(imgName)) {
                break;
            }
            if (dr == 0) {
                if (controllaMatch(nr, nc, 1, 0)) {
                    tempr = nr;
                    tempc = nc;
                }
            }
            count++;
        }

        // Se ci sono almeno 3 gemme abbinate, rimuovile
        if (count >= 3) {
            try {
                Thread.sleep(200);  // Attendi 200ms
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int k = 0; k < count; k++) {
                punteggio += 1;
                int nr = r + k * dr;
                int nc = c + k * dc;
                if (nr != tempr || nc != tempc) {
                    caselle[nr][nc].setIcon(null);  // Rimuove l'immagine dalla casella
                }
            }
        }
    }

    // Fai cadere i diamanti vuoti
    private void lasciaCadere() {
        for (int c = 0; c < 8; c++) {
            for (int r = 7; r >= 1; r--) {
                if (caselle[r][c].getIcon() == null) {
                    for (int i = r; i > 0; i--) {
                        caselle[i][c].setIcon(caselle[i - 1][c].getIcon());
                    }
                    caselle[0][c].setIcon(null);
                }
            }
        }
    }

    // Rigenera le immagini per le caselle vuote
    private void rigeneraImmagini() {
        Random rand = new Random();
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (caselle[r][c].getIcon() == null) {
                    int n;
                    do {
                        n = rand.nextInt(6) + 1;
                        caselle[r][c].setIcon(new ImageIcon("src/Images/image" + n + ".png"));
                    } while (controllaMatch(r, c, 1, 0) || controllaMatch(r, c, 0, 1));
                }
            }
        }
    }

    // Metodi get
    public int getPunteggio() {
        return punteggio;
    }

    public int getN_trovato() {
        return n_trovato;
    }

    public void setN_trovato(int n_trovato) {
        this.n_trovato = n_trovato;
    }
}
