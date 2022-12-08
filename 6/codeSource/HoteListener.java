package parcInfo.serveur;

import java.net.SocketException;
import java.util.Vector;

public class HoteListener extends Thread {
    Hote hote;

    // _ _ _  CONSTRUCTORS

    public HoteListener(Hote hote){
        this.setHote(hote);
        this.hote.getServeur().calculNbrHotes();
        this.hote.getServeur().getFrame().getDash().getStat().maj();
    }

    // _ _ _ METHODS _ _ _

    public void run() {
        while(true){                                                    //ecoute de toutes les informations communiquees par le client
            try{
                Object msg = this.hote.receiveObject();                         
                Vector<String> data = (Vector<String>)msg;
                if(data.get(0).equals("sta")){
                    data.remove(0);
                    this.hote.setSta(data);                             //actualisation des donnes statiques de l'hote
                    this.hote.getServeur().calculerNbrParOs();
                    this.hote.getServeur().afficherStatGlobales();
                    
                    this.hote.getServeur().getFrame().getDash().getStat().maj();                        //mise a jour des donnees sur les stats generales
                    this.hote.getServeur().getFrame().getDash().getListe().add(this.hote);              //ajout de l'hote a l'ecran

                    //System.out.println("sta:\t"+this.hote.getSta());
                }

                else if(data.get(0).equals("dyn")){
                    data.remove(0);
                    this.hote.setDyn(data);
                    System.out.println("DN:"+this.hote.getDyn()+"TAILLE:"+this.hote.getDyn().size());
                    //System.out.println(this.hote.getData());
                    this.hote.getServeur().getFrame().getDash().getListe().update(this.hote);                           //actualisation des donnes dynamiques de l'hote
                    //System.out.println("dyn:\t"+this.hote.getDyn());
                }

            }
            catch(SocketException se){
                try
                {   
                    this.hote.getServeur().getFrame().getDash().getListe().remove(this.hote);
                    this.hote.traiterSocketException(se);                       //affiche l'exception, si connection reset fermeture du thread
                    this.hote.getServeur().getFrame().getDash().getStat().maj();
                    break;
                    //Thread.interrupt();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            catch(Exception e){
                e.printStackTrace();
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
