package parcInfo.graphique;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import parcInfo.graphique.GlobalStat;
import parcInfo.graphique.HostList;
import parcInfo.graphique.MainFrame;

import javax.swing.JScrollPane;


public class Dashboard extends JPanel{
    MainFrame frame;
    GlobalStat stat; 
    HostList liste; 

    // _ _ _ CONSTRUCTORS _ _ _
    public Dashboard(MainFrame frame){
        this.setFrame(frame);
        this.setStat(new GlobalStat(this));
        this.setListe(new HostList(this));
        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        this.setLayout(layout);
        this.add(this.stat);
        this.add(this.liste);
        //this.add(this.liste);
    }

    public MainFrame getFrame() {
        return frame;
    }

    public void setFrame(MainFrame frame) {
        this.frame = frame;
    }

    public GlobalStat getStat() {
        return stat;
    }

    public void setStat(GlobalStat stat) {
        this.stat = stat;
    }

    public HostList getListe() {
        return liste;
    }

    public void setListe(HostList liste) {
        this.liste = liste;
    }
    // _ _ _ GET SET _ _ _

    

    
}
