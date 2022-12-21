package parcInfo.serveur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import parcInfo.serveur.*;
import parcInfo.graphique.MainFrame;

public class Serveur {
    ServerSocket serveur;
    int port;
    MainFrame frame = new MainFrame(this);
    Vector<Hote> hotes = new Vector<Hote>();
    int nbrHotes = 0;
    int nbrLin = 0;
    int nbrWin = 0;


    // _ _ _ CONSTRUCTORS

    public Serveur(int port) throws Exception{
        try{
            this.setServeur(new ServerSocket(port));
        }
        catch(Exception e){
            throw e;
        }
    }    
    public Serveur() throws Exception{
        try {
            this.getPortNumber();
            this.setServeur(new ServerSocket(this.getPort()));
        } 
        catch (Exception e) {
            throw e;
        }       
    }  

    // _ _ _ METHODS _ _ _
    // --- Initialisation
    public void  getPortNumber() throws Exception{
        int res = 0;
        String path = "parcInfo/init.nato",
                donneeBrute;
        File source = new File(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
        br.readLine();
        try {
            donneeBrute = br.readLine();
            String[] donnees = donneeBrute.split(":");
            res = Integer.valueOf(donnees[1].trim());
        }
        catch (Exception e) {
            throw e;
        }
        this.setPort(res);
    }

    // --- Connection --- 
    public Hote getConnection()throws Exception{
        try{
        //fonction pour permettre aux sockets clientes de se connecter
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

    //---Statisiques
    public void statGlobales(){
        this.calculerNbrParOs();
        this.calculNbrHotes();
    }  
    public void calculerNbrParOs(){
        //comptage par os
        int nbrWin = 0;
        int nbrLin = 0;
        for(Hote hote: this.hotes){
            String os = hote.getSta().get(0);            
            if(os.equals("Windows"))
                nbrWin++;
            else if(os.equals("Linux"))
                nbrLin++;                
        }
        this.setNbrLin(nbrLin);
        this.setNbrWin(nbrWin);
    }
    public void calculNbrHotes() {
        this.nbrHotes = this.hotes.size();
    }
    

    public void afficherStatGlobales(){
        System.out.println("Hotes connectes:\t"+this.nbrHotes);
        System.out.println("Sous linux:\t"+this.nbrLin);
        System.out.println("Sous windows:\t"+this.nbrWin);
    }

   
    
    // _ _ _ SET GET  _ _ _

    public int getNbrHotes() {
        return nbrHotes;
    }

    public void setNbrHotes() {
        this.nbrHotes++;
    }

    public void setNbrLin(int nbr) {
        this.nbrLin = nbr;
    }

    public int getNbrLin() {
        return nbrLin;
    }

    public void setNbrWin(int nbr) {
        this.nbrWin = nbr;
    }

    public int getNbrWin() {
        return nbrWin;
    }

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

    public MainFrame getFrame() {
        return frame;
    }

    public void setFrame(MainFrame frame) {
        this.frame = frame;
    }

    public void setNbrHotes(int nbrHotes) {
        this.nbrHotes = nbrHotes;
    }

    public int getPort() throws Exception{
        if(this.port == 0) throw new Exception("Port non initialise");
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }  
    
}
