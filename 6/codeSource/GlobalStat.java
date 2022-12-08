package parcInfo.graphique;

import java.awt.Color;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import parcInfo.serveur.Serveur;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class GlobalStat extends JPanel{
    Dashboard dash;
    JLabel nbrHotes = new JLabel();
    JLabel nbrWin = new JLabel();
    JLabel nbrLin = new JLabel();
    
    
    //_ _ _ CONSTRUCTORS _ _ _
    public GlobalStat(Dashboard dash){
        this.setDash(dash);
        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        this.add(this.nbrHotes);
        this.add(this.nbrLin);
        this.add(this.nbrWin);
        Border border = new LineBorder(Color.BLACK);
        this.setBorder(border);
        this.maj();                     //premier chargement des donnees statistiques
    }

    // _ _ _  METHODS _ _ _
    public void maj(){
        //mise a jour des donnees de statistiques globales
        this.nbrHotes.setText("Connectes: "+this.dash.frame.serveur.getNbrHotes());
        this.nbrLin.setText("Sous Linux: "+this.dash.frame.serveur.getNbrLin());
        this.nbrWin.setText("Sous Windows: "+this.dash.frame.serveur.getNbrWin());   
    }
    //_ _ _GET SET _ _ _

    public Dashboard getDash() {
        return dash;
    }

    public void setDash(Dashboard dash) {
        this.dash = dash;
    }

    public JLabel getNbrHotes() {
        return nbrHotes;
    }

    public void setNbrHotes(JLabel nbrHotes) {
        this.nbrHotes = nbrHotes;
    }

    public JLabel getNbrWin() {
        return nbrWin;
    }

    public void setNbrWin(JLabel nbrWin) {
        this.nbrWin = nbrWin;
    }

    public JLabel getNbrLin() {
        return nbrLin;
    }

    public void setNbrLin(JLabel nbrLin) {
        this.nbrLin = nbrLin;
    }
       
}
