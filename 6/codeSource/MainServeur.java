package parcInfo.serveur;

import parcInfo.graphique.MainFrame;

public class MainServeur {
    public static void main(String[] args){
        Serveur serveur = null;
        try{
            serveur = new Serveur(1212);
            ConnectionBuilder cb = new ConnectionBuilder(serveur);
            cb.start();
            
            //while(true){
            //    System.out.println("PRINCIPAL HOTES:\t\t"+serveur.getNbrHotes());
            //    System.out.println("PRINCIPAL LIN:\t\t"+serveur.getNbrLin());
            //    System.out.println("PRINCIPAL WIN:\t\t"+serveur.getNbrWin());
            //}
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
