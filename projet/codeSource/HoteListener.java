package parcInfo.serveur;

import java.io.EOFException;
import java.net.SocketException;
import java.util.Vector;

public class HoteListener extends Thread {
    Hote hote;

    // _ _ _  CONSTRUCTORS

    public HoteListener(Hote hote){
        this.setHote(hote);
        this.hote.getServeur().calculNbrHotes();                                //mise a jour des statistiques globales apres instanciation de l'hote et son ecoueur
        this.hote.getServeur().getFrame().getDash().getStat().maj();            //mise a jour au niveau graphique 
    }

    // _ _ _ METHODS _ _ _

    public void run() {
        while(true){                                                    //ecoute de toutes les informations communiquees par UN client
            try{
                Object msg = this.hote.receiveObject();                         
                Vector<String> data = (Vector<String>)msg;
                if(data.get(0).equals("sta")){          //"STA" ET "DYN" sont les mots cle pour identifier une information 
                    data.remove(0);
                    this.hote.setSta(data);                             //actualisation des donnes statiques de l'hote
                    this.hote.getServeur().calculerNbrParOs();          //recomptage du nombvre d'hote par OS
                    
                    this.hote.getServeur().getFrame().getDash().getStat().maj();                   //mise a jour des donnees sur les stats generales
                    this.hote.getServeur().getFrame().getDash().getListe().add(this.hote);         //ajout de l'hote a l'ecran
                }

                else if(data.get(0).equals("dyn")){
                    data.remove(0);
                    this.hote.setDyn(data);
                    this.hote.getServeur().getFrame().getDash().getListe().update(this.hote);                           //actualisation des donnes dynamiques de l'hote a l'ecran
                }

            }
            catch(SocketException se){
                try
                {   
                    //gestion d'interuption de connexion
                    System.out.println("Presence d'une socketException");
                    this.hote.getServeur().getFrame().getDash().getListe().remove(this.hote); //retrait de l'hote au niveau affichage
                    this.hote.traiterSocketException(se);                       //si connection reset fermeture du thread
                    this.hote.getServeur().getFrame().getDash().getStat().maj(); //reactualisation des statistiques generales
                    break;
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }            
            catch(Exception e){
                //SocketException non levee si hote sous linux 

                System.out.println("Une erreur est survenue");              
                e.printStackTrace();
                try {
        
                    this.hote.getServeur().getFrame().getDash().getListe().remove(this.hote); //retrait visuel
                    this.hote.serveur.hotes.remove(this.hote);                                //RETRAIT DE LISTE
                    this.hote.socket.close();

                } 
                catch (Exception ev) {
                    ev.printStackTrace();
                }
                finally{
                    this.hote.serveur.statGlobales();                                       //reactualisation des statistiques globales
                    this.hote.getServeur().getFrame().getDash().getStat().maj();
                    break;
                }               
            }
    
            
        }
    }


    public Hote getHote() {
        return hote;
    }


    public void setHote(Hote hote) {
        this.hote = hote;
    }
}
