package parcInfo.serveur;

import java.io.Serial;

import parcInfo.serveur.HoteListener;

public class ConnectionBuilder extends Thread {
    //thread qui tourne en boucle pour etablir la connection avec les clients
    Serveur serveur;

    // _ _ _ CONSTRUCTORS

    public ConnectionBuilder(Serveur serveur){
        this.setServeur(serveur);
    }

    // _ _ _ METHODS

    public void run() {    
        while(true){
            try {
                System.out.print("Attente d'une connexion au serveur");
                Hote h = this.serveur.getConnection();                  //obtiention d'un nouvel hote
                this.serveur.hotes.add(h);                              //ajout a la liste des hotes connectes
                HoteListener hl = new HoteListener(h);                  //instanciation du thread ecouteur
                hl.start();
                
            } catch (Exception e) {
                e.printStackTrace();
            } 

        }

    }

    //_ _ _ GET SET
    
    public Serveur getServeur() {
        return serveur;
    }

    public void setServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    
}
