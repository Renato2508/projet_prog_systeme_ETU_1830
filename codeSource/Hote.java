package parcInfo.serveur;

import java.net.Socket;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class Hote {
    Serveur serveur;
    Socket socket;
    int nbrLin,
        nbrWin,
        nbrHer,
        nbrUEFI,
        nbrHotes;
    
    //  _ _ _ Constructors

    public Hote(Socket socket, Serveur serveur){
        this.setSocket(socket);
        this.setServeur(serveur);
    }
    

    // _ _ _  METHODS  _ _ _

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

    public int getNbrLin() {
        return nbrLin;
    }

    public void setNbrLin(int nbrLin) {
        this.nbrLin = nbrLin;
    }

    public int getNbrWin() {
        return nbrWin;
    }

    public void setNbrWin(int nbrWin) {
        this.nbrWin = nbrWin;
    }

    public int getNbrHer() {
        return nbrHer;
    }

    public void setNbrHer(int nbrHer) {
        this.nbrHer = nbrHer;
    }

    public int getNbrUEFI() {
        return nbrUEFI;
    }

    public void setNbrUEFI(int nbrUEFI) {
        this.nbrUEFI = nbrUEFI;
    }

    public int getNbrHotes() {
        return nbrHotes;
    }

    public void setNbrHotes(int nbrHotes) {
        this.nbrHotes = nbrHotes;
    }

    // _ _ _GET SET

    
}
