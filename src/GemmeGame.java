import javax.swing.*;

public class GemmeGame {
    private Map map;
    private int punteggio;
    private boolean loose;
    private JButton p1 = null;
    private int r1 = -1;
    private int c1 = -1;
    private boolean gif = false;
    private int n_trovato = 0;
    Timer timer=new Timer(100,(e) -> {
        punteggio= map.getPunteggio();
    });


    public void setLoose(boolean loose) {
        this.loose = loose;
    }

    public GemmeGame() {
        map = new Map();
        punteggio = 0;
        loose = false;
        timer.start();
    }

    public Map getMap() {
        return map;
    }


    public void clicca(int r, int c) {
        if(!loose){
            if (p1 == null) {
                // Seleziona prima gemma, cambia in formato gif

                if(map.getCaselle()[r][c]!=null){
                    p1 = map.getCaselle()[r][c];
                    r1 = r;
                    c1 = c;
                    String imgName = ((ImageIcon) p1.getIcon()).getDescription();
                    String gifName = imgName.replace(".png", ".gif");
                    p1.setIcon(new ImageIcon(gifName));
                    gif = true;
                }

            } else {
                // i diamanti scelti devono essere adiancenti
                if ((Math.abs(r1 - r) <= 1 && c1 == c) || (Math.abs(c1 - c) <= 1 && r1 == r)) {

                    JButton p2 = map.getCaselle()[r][c];

                    // Scambia le immagini
                    String imgName1 = ((ImageIcon) p1.getIcon()).getDescription().replace(".gif", ".png");
                    String imgName2 = ((ImageIcon) p2.getIcon()).getDescription().replace(".gif", ".png");
                    p1.setIcon(new ImageIcon(imgName2));
                    p2.setIcon(new ImageIcon(imgName1));


                    n_trovato=map.controlla();

                    //se le operazioni sono inutili sbagliati tornano in dietro

                    if(p1.getIcon() != null && p2.getIcon() != null){
                        // Secondo click, cambia le immagini e controlla

                        //se seglie due volte se stesso come se sta annulando azione
                        if(p1==p2){
                            String imgName = ((ImageIcon) p1.getIcon()).getDescription();
                            String gifName = imgName.replace(".gif", ".png");
                            p1.setIcon(new ImageIcon(gifName));

                            //non puo essere una combinazione inutile
                        } else if (n_trovato==0) {
                            System.out.println("!");
                            String imgName3 = ((ImageIcon) p1.getIcon()).getDescription().replace(".gif", ".png");
                            String imgName4 = ((ImageIcon) p2.getIcon()).getDescription().replace(".gif", ".png");

                            p1.setIcon(new ImageIcon(imgName4));
                            p2.setIcon(new ImageIcon(imgName3));
                        } else{

                            //se tutto soddista segue le regole del gioco chiama funzione controllaestacca
                            map.controllaEStacca();

                        }
                    }


                    // Reset
                    p1 = null;
                    r1 = -1;
                    c1 = -1;
                    gif = false;
                    n_trovato = 0;


                    //verifica se ci sono altri possibili combinazioni

                    if (!map.verifica()){
                        loose=true;
                    }


                }else {
                    String imgName = ((ImageIcon) p1.getIcon()).getDescription();
                    String gifName = imgName.replace(".gif", ".png");
                    p1.setIcon(new ImageIcon(gifName));
                    p1=null;

                }


            }


        }

    }


    public boolean isLoose() {
        return loose;
    }

    public int getPunteggio() {
        return map.getPunteggio();

    }
}