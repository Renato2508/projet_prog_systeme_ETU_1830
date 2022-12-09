package parcInfo.graphique;

import java.awt.Color;
import java.awt.Container;
import java.util.Vector;

import javax.swing.SwingUtilities;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import parcInfo.graphique.LabelHost;
import parcInfo.serveur.Hote;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class HostList extends JPanel{
    Dashboard dash;
    Vector<Vector> data = new Vector();
    JTable table = new JTable();

    //_ _ _ CONSTRUCTORS
    public HostList(Dashboard dash){
        this.setDash(dash);
        Vector<String> cols = new Vector<String>();             //colonnes de la table d'affichage
        cols.add("OS");
        cols.add("CPU");
        cols.add("NOM");
        cols.add("ROM");
        cols.add("FREE ROM");
        cols.add("RAM");
        cols.add("FREE RAM");
        cols.add("CPU LOAD");
        
        this.table = new JTable(this.data, cols);                   //table d'affichage

        JScrollPane scroll = new JScrollPane(this.table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setColumnHeaderView(this.table.getTableHeader());
        this.add(scroll);
        



    }

    //METHODS

    public void update(Hote hote){
        //Container fen = SwingUtilities.getUnwrappedParent(this);
        //fen.setVisible(false);
        DefaultTableModel model = (DefaultTableModel)this.table.getModel();
        int indiceSuppress = this.dash.frame.serveur.getHotes().indexOf(hote);
        model.removeRow(indiceSuppress);
        model.addRow(hote.getData());
        int nbrLignes = model.getRowCount();
        model.moveRow(nbrLignes-1, nbrLignes-1, indiceSuppress);
        //fen.setVisible(true);
    }


    public void remove(Hote hote){
        //Container fen = SwingUtilities.getUnwrappedParent(this);
        //fen.setVisible(false);
        DefaultTableModel model = (DefaultTableModel)this.table.getModel();
        int indiceSuppress = this.dash.frame.serveur.getHotes().indexOf(hote);
        model.removeRow(indiceSuppress);
        //fen.setVisible(true);
    }    
    
    public void add(Hote hote){
        //Container fen = SwingUtilities.getUnwrappedParent(this);
        //fen.setVisible(false);
        DefaultTableModel model = (DefaultTableModel)this.table.getModel();
        model.addRow(hote.getData());
        //fen.setVisible(true);
    }

    
    public Vector<Vector> getData() {
        return data;
    }

    public void setData(Vector<Vector> data) {
        this.data = data;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public Dashboard getDash() {
        return dash;
    }

    public void setDash(Dashboard dash) {
        this.dash = dash;
    }
}
