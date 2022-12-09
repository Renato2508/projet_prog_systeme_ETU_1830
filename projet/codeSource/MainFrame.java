package parcInfo.graphique;

import javax.swing.JFrame;
import parcInfo.serveur.Serveur;
import parcInfo.graphique.Dashboard;

public class MainFrame extends JFrame{
    Serveur serveur;
    Dashboard dash; 

    // _ _ _ CONSTRUCTORS _ _ _ 
    public MainFrame(){}

    public MainFrame(Serveur serveur){
        this();
        this.setServeur(serveur);
        this.setDash(new Dashboard(this));
        this.add(this.dash);
        this.setSize(800,600);
        this.setVisible(true);
    }

    // _ _ _ GET SET _ _ _

    public Serveur getServeur() {
        return serveur;
    }

    public void setServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    public Dashboard getDash() {
        return dash;
    }

    public void setDash(Dashboard dash) {
        this.dash = dash;
    }
    

}
