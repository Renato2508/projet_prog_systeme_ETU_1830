package parcInfo.serveur;

import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

import annexe.OnVectors;

import java.io.InputStream;
import java.io.ObjectInputStream;

public class Hote {
    Serveur serveur;
    Socket socket;
    Vector<String> dyn;          //pour les donnes dynamiques
    Vector<String> sta;          //pour les donnes statiques
    
    //  _ _ _ Constructors

    public Hote(Socket socket, Serveur serveur){
        this.setSocket(socket);
        this.setServeur(serveur);
    }
    

    // _ _ _  METHODS  _ _ _

    public Vector getData(){
        Vector res = OnVectors.cat(this.sta, this.dyn);
        return res;
    }
    public void traiterSocketException(SocketException se) throws Exception {
        System.out.println(se.getMessage());
          if(se.getMessage().contains("Connection reset")){
            System.out.println("Connexion avec un des hotes interrompue:\t retrait...");
            try{
                this.socket.getInputStream().close();                   //fermeture des flux de donnees et du socket
                //this.socket.getOutputStream().close();
                this.socket.close();
                this.serveur.hotes.remove(this);                 //retrait de l'hote pour des chiffres justes
                this.serveur.statGlobales();
                this.serveur.afficherStatGlobales();
                //System.out.println("Nombres d'hotes restants:\t"+this.serveur.getNbrHotes());
            }
            catch(Exception e){
                throw e;
            }            
          }
    }
    
    public Object receiveObject() throws Exception
    {       
        Object res = null;
        try{
            InputStream fluxEntree = this.socket.getInputStream();
            ObjectInputStream objetEntree = new ObjectInputStream(fluxEntree);
            res = objetEntree.readObject();    
        }
        catch(Exception d)
        {
            throw d;            
        }
        return res;
    }

    // _ _ _ SET GET _ _ _

    public Vector<String> getDyn() {
        return dyn;
    }

    public void setDyn(Vector<String> dyn) {
        this.dyn = dyn;
    }

    public Vector<String> getSta() {
        return sta;
    }

    public void setSta(Vector<String> sta) {
        this.sta = sta;
    }

    public void setServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    public Serveur getServeur() {
        return serveur;
    }
    
    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    

    // _ _ _GET SET

    
}
