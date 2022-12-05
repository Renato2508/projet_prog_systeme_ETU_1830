package parcInfo.serveur;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import parcInfo.serveur.*;

public class Serveur {
    ServerSocket serveur;
    Vector<Hote> hotes = new Vector<Hote>();


    // _ _ _ CONSTRUCTORS
    
    public Serveur(){}

    public Serveur(int port) throws Exception{
        try{
            this.setServeur(new ServerSocket(port));
        }
        catch(Exception e){
            throw e;
        }
    }

    // _ _ _ METHODS 
    public Hote getConnection()throws Exception{
        try{
            System.out.print("Attente d'une connexion au serveur");     //fonction pour permettre aux sockets clientes de se connecter
            Socket socketCourant = this.serveur.accept();
            System.out.println("Nouveau socket connecte:\t"+socketCourant);
            Hote nouveau = new Hote(socketCourant, this);
            return nouveau;

        }
        catch(Exception e)
        {
            throw e;
        }
    }


    // _ _ _ SET GET  _ _ _

    public ServerSocket getServeur() {
        return serveur;
    }

    public void setServeur(ServerSocket serveur) {
        this.serveur = serveur;
    }

    public Vector<Hote> getHotes() {
        return hotes;
    }

    public void setHotes(Vector<Hote> hotes) {
        this.hotes = hotes;
    }

    
    
}
