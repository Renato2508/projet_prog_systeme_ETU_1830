package parcInfo.graphique;

import parcInfo.serveur.Hote;
import javax.swing.JLabel;

public class LabelHost extends JLabel{
    Hote hote;

    public LabelHost(Hote hote){
        this.setHote(hote);
        String texte = this.hote.getSta().get(2);
        System.out.println("Construction d'un jlabel... texte:"+texte);
        this.setText(texte);
    }

    //_ _ _GET SET _ _ _

    public Hote getHote() {
        return hote;
    }

    public void setHote(Hote hote) {
        this.hote = hote;
    }
    
}
