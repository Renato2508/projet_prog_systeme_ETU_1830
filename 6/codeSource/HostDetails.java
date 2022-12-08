package parcInfo.graphique;

import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

import parcInfo.serveur.Hote;

public class HostDetails extends JFrame {
    Hote hote;

    //_ _ _ CONSTRUCTORS _ _ _
    public HostDetails(Hote hote){
        this.setHote(hote);
        JPanel pane = new JPanel();
        Vector<String> sta = this.hote.getSta(),
                        dyn = this.hote.getDyn();
        BoxLayout layout = new BoxLayout(pane, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
        this.add(new JLabel("Nom: "+sta.get(3)));
        this.add(new JLabel("OS:"+sta.get(1)));
        this.add(new JLabel("CPU:"+sta.get(2)));
        this.add(new JLabel("Stockage"+sta.get(4)));
        this.add(new JLabel("Stockage disponible:"+sta.get(5)));
        this.add(new JLabel("Memoire:"+sta.get(6)));
        this.add(new JLabel("Memoire disponible:"+dyn.get(2)));
        this.add(new JLabel("Taux d'utilisation CPU:"+dyn.get(3)));
    }

    //_ _ _ GET SET _ _ _

    public Hote getHote() {
        return hote;
    }

    public void setHote(Hote hote) {
        this.hote = hote;
    }
}
