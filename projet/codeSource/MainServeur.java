package parcInfo.serveur;

import parcInfo.graphique.MainFrame;

public class MainServeur {
    public static void main(String[] args){
        Serveur serveur = null;
        try{
            serveur = new Serveur();
            ConnectionBuilder cb = new ConnectionBuilder(serveur);
            cb.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
