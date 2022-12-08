package affichage;
import files.*;
import javax.swing.*;
import java.util.Vector;
import calcul.*;
import listener.*;
import java.io.*;
import java.sql.Date;

import rendu.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.xml.namespace.QName;
public class Enregistrements extends JFrame{
    Formulaire formulaire;
    Object[] content;
    JTextField searchbar=new JTextField();
    JPanel details=new JPanel();
    JTextField date1=new JTextField();
    JTextField date2=new JTextField();
    int comparaison;
    public Formulaire getFormulaire(){
        return this.formulaire;
    }
        public void addMoyenne() throws Exception{
        Calculs c=new Calculs();
        String usedField=(String) this.content[0].getClass().getMethod("getFieldToUse").invoke(this.content[0]);
        double moyenne=getMoyenne(this.content,usedField);
        JLabel moy=new JLabel("Moyenne des "+usedField+"s: "+moyenne);
        this.details.add(moy);
    }
    public void addSomme() throws Exception{
        
        String usedField=(String) this.content[0].getClass().getMethod("getFieldToUse").invoke(this.content[0]);
        double somme=getSomme(content,usedField);
        JLabel sum=new JLabel("Somme des "+usedField+"s: "+somme);
        this.details.add(sum);
    }
    public void addMin() throws Exception{
        Calculs c=new Calculs();

        String usedField=(String) this.content[0].getClass().getMethod("getFieldToUse").invoke(this.content[0]);
        int minIndex=this.getMinMaxIndex(content,usedField,0,1); 
        double minValue=Double.valueOf( c.getGetter(usedField,content[minIndex]));      
        JLabel min=new JLabel("Valeur minimum des "+usedField+"s: "+minValue);
        this.details.add(min);
    }
    public void addMax() throws Exception{
        Calculs c=new Calculs();
        String usedField=(String) this.content[0].getClass().getMethod("getFieldToUse").invoke(this.content[0]);
        int maxIndex=this.getMinMaxIndex(content,usedField,0,-1); 
        double maxValue=Double.valueOf( c.getGetter(usedField,content[maxIndex]));
        JLabel max=new JLabel("Valeur maximum des "+usedField+"s: "+maxValue);
        this.details.add(max);
    }
    public void getTables(JPanel pan) throws Exception{
            String[] labels=new String[3];
            labels[0]="Triage decroissant";
            labels[1]="Enregistrements";
            labels[2]="Triage croissant";
            
            // for(int i=-1;i<2;i=i+2){
            //     JTable tableau=new JTable(getRowData(i),getColumns());
            //     pan.add(tableau);
            //     pan.add(new JLabel(labels[i+1]));
            //     JScrollPane scrollpane=new JScrollPane(tableau);
            //     tableau.setRowHeight(20);
            //     pan.add(scrollpane);
            // }
            

    // }
    public Object createObjets(String[] arguments) throws Exception{
        String baseName=this.formulaire.getFile().getBaseName();
        Object objet = null;  
        Class classe=Class.forName(baseName.toLowerCase()+"."+baseName);
        objet=classe.newInstance();
        for(int iattr=0;iattr<classe.getDeclaredFields().length;iattr++){
            for(int imethod=0;imethod<classe.getDeclaredMethods().length;imethod++){
                if(classe.getDeclaredMethods()[imethod].getName().compareToIgnoreCase("set"+classe.getDeclaredFields()[iattr].getName())==0){
                    classe.getDeclaredMethods()[imethod].invoke(objet,arguments[iattr]);
                }
            }
        }
        return objet;
    }
    public double getSomme(Object [] lo, String attribut) throws Exception{
        double somme=0;
        double ajout=0;
        Calculs c=new Calculs();

        for(int ilo=0;ilo<lo.length;ilo++){

            String a=c.getGetter(attribut,lo[ilo]);
            ajout=Double.valueOf(a);
            somme=somme+ajout; 
        }
        return somme;
    }
    public double getMoyenne(Object [] lo, String attribut) throws Exception{
        double somme=getSomme(lo,attribut);
        return somme/this.formulaire.getFile().getEnregistrement();
    }

    public int Compare(double d1, double d2){
        if(d1>d2){
            return 1;
        }
        return -1;
    }
    //compare = 1 si d1 > d2 ==>min
    //compare = -1 si d1<d2 ==>max 
    public int getMinMaxIndex(Object[] lo, String attribut,int begin,int compare) throws Exception{
        int imin=begin;
        Calculs c=new Calculs();

        double minValue=Double.valueOf(c.getGetter(attribut, lo[begin]));
        for(int i=begin;i<lo.length;i++){
            double temp=Double.valueOf(c.getGetter(attribut,lo[i]));
            if(Compare(minValue,temp)==compare){
                imin=i;
                minValue=temp;
            }
        }
        return imin;
    }
    public void sort(String attribut,int compare) throws Exception{
        String baseName=this.formulaire.getFile().getBaseName();
        Object temp = null;  
        Class classe=Class.forName(baseName.toLowerCase()+"."+baseName);
        temp=classe.newInstance();
        for(int i=0;i<content.length;i++){
            int imin=getMinMaxIndex(content,attribut,i,compare);
            temp=content[imin];
            content[imin]=content[i];
            content[i]=temp;
        }
    }
    public String[][] getRowData(int compare) throws Exception{
        Calculs c=new Calculs();
        int numrows=this.formulaire.getFile().getEnregistrement();
        int numCol=this.content[0].getClass().getDeclaredFields().length;
        String usedField=(String) this.content[0].getClass().getMethod("getFieldToUse").invoke(this.content[0]);
        if(compare!=0){
            sort(usedField,compare);
        }

        String[][] rowdata=new String[numrows][numCol];

        for(int i=0;i<numrows;i++){
            for(int j=0;j<numCol;j++){
                rowdata[i][j]=c.getGetter(content[i].getClass().getDeclaredFields()[j].getName(),content[i]);
            }
        }
        return rowdata;
    }

    public Object[] getColumns(){
        
        int numCol=this.content[0].getClass().getDeclaredFields().length;
        Object[] o=new Object[numCol];
        for(int i=0;i<numCol;i++){
            o[i]=new Object();
            o[i]=this.content[0].getClass().getDeclaredFields()[i].getName();
        }
        return o;
    }

    public Object[] makeObjects() throws Exception{
            Vector v=this.formulaire.getFile().readAll();
            int len=this.formulaire.getFile().getEnregistrement();
            Object[] content=new Object[len];
            for(int i=0;i<len;i++){
                content[i]=createObjets( ((String[]) v.get(i)));
            }
            return content;
    }
public Enregistrements(Formulaire f,int comparaison) throws Exception{
        
        this.formulaire=f;
        this.setSize(1200,800);

        Vector v=this.formulaire.getFile().readAll();
        int len=this.formulaire.getFile().getEnregistrement();
        this.content=new Object[len];

        this.content=makeObjects();
        
        JPanel right=new JPanel();

        JPanel pan=new JPanel();
        pan.setLayout(new BoxLayout(pan,BoxLayout.X_AXIS));
        this.add(pan);

        
        JPanel table_container=new JPanel();
        table_container.setLayout(new BoxLayout(table_container,BoxLayout.X_AXIS));
        DefaultTableModel model = new DefaultTableModel(getRowData(comparaison),getColumns());

        JTable table=new JTable();
        table.setModel(model);

        table_container.add(table);

        JPanel linksmodif=new JPanel();
        linksmodif.setLayout(new BoxLayout(linksmodif,BoxLayout.Y_AXIS));

        JPanel linksdelete=new JPanel();
        linksdelete.setLayout(new BoxLayout(linksdelete,BoxLayout.Y_AXIS));

        table_container.add(linksmodif);
        table_container.add(linksdelete);


        
        if(comparaison==0){
            for(int i=0;i<len;i++){

                JLabel modifier=new JLabel("Modifier");
                modifier.addMouseListener(new Modify(i, this));
                linksmodif.add(modifier);
    
                JLabel supprimer=new JLabel("Supprimer");
                supprimer.addMouseListener(new Delete(i,this));
                linksdelete.add(supprimer);
            }    
        }
        
        JScrollPane scroll=new JScrollPane(table_container);

        pan.add(scroll);
        pan.add(right);
        JButton sum=new JButton("Somme");
        sum.addActionListener(new Fonctionnalites(this,0));
        JButton max=new JButton("Maximum");
        max.addActionListener(new Fonctionnalites(this,1));
        JButton min=new JButton("Minimum");
        min.addActionListener(new Fonctionnalites(this,2));
        JButton retour=new JButton("Retour");
        retour.addActionListener(new Retour(this));
        
        JPanel boutons=new JPanel();
        boutons.setLayout(new BoxLayout(boutons,BoxLayout.Y_AXIS));


        this.setFocusable(true);
        this.requestFocus();
        right.setLayout(new BoxLayout(right, BoxLayout.X_AXIS));
        JPanel search=new JPanel();
        search.setLayout(new FlowLayout());
        right.add(search);
        this.searchbar.setPreferredSize(new Dimension(100,20));
        search.add(searchbar);
        JButton rechercher=new JButton("Rechercher");
        search.add(rechercher);
        rechercher.addActionListener(new Search(this,0));

        JPanel searchdate=new JPanel();
        searchdate.setLayout(new FlowLayout());
        searchdate.add(new JLabel("Rechercher une date entre :"));
        right.add(searchdate);
        date1.setPreferredSize(new Dimension(100,20));
        searchdate.add(date1);
        searchdate.add(new JLabel("et "));
        date2.setPreferredSize(new Dimension(100,20));
        searchdate.add(date2);

        JButton rechercherdate=new JButton("Rechercher la date");
        searchdate.add(rechercherdate);
        rechercherdate.addActionListener(new Search(this, 1));

        String name=this.content[0].getClass().getSimpleName();
        JLabel labelHead = new JLabel(name);
        labelHead.setFont(new Font("Arial",Font.TRUETYPE_FONT,20));

        JButton croissant=new JButton("Tri Croissant");
        croissant.addActionListener(new Fonctionnalites(this, 3));
        JButton decroissant=new JButton("Tri Decroissant");
        decroissant.addActionListener(new Fonctionnalites(this, 4));

        boutons.add(sum);
        boutons.add(min);
        boutons.add(max);
        boutons.add(croissant);
        boutons.add(decroissant);
        right.add(details);
        // this.addSomme();
        // this.addMin();
        // this.addMax();
        // this.addMoyenne();
        this.details.setLayout(new BoxLayout(details,BoxLayout.Y_AXIS));
        details.add(boutons);
        boutons.add(retour);
    }
    public String getText(){
        return searchbar.getText();
    }
    public Object[] search(Object[] lo,String recherche) throws Exception{
        Vector result=new Vector();
        Calculs c=new Calculs();
        for(int iobject=0;iobject<lo.length;iobject++){
            for(int ifield=0;ifield<lo[0].getClass().getDeclaredFields().length;ifield++){
                String a=c.getGetter(lo[0].getClass().getDeclaredFields()[ifield].getName(),lo[iobject]);
                if(a.toLowerCase().contains(recherche.toLowerCase())){
                    result.add(lo[iobject]);
                }
            }
        }
        return result.toArray();
    }
    public Object[] getContent(){
        return this.content;
    }
    public String getDate1(){
        return this.date1.getText();
    }
    public String getDate2(){
        return this.date2.getText();
    }
    public Object[] searchDate(Object [] lo, String date1, String date2 ) throws Exception{
        Vector result=new Vector();
        Calculs c=new Calculs();
        Date d1=Date.valueOf(date1);
        Date d2=Date.valueOf(date2);
        for(int iobject=0;iobject<lo.length;iobject++){
            for(int ifield=0;ifield<lo[0].getClass().getDeclaredFields().length;ifield++){
                if(lo[0].getClass().getDeclaredFields()[ifield].getType().getSimpleName()==new Date(2000,1,1).getClass().getSimpleName()){
                    String a=c.getGetter(lo[0].getClass().getDeclaredFields()[ifield].getName(),lo[iobject]);
                    Date d=Date.valueOf(a);
                    if((d.compareTo(d1)>=0)&&(d.compareTo(d2)<=0)){
                        result.add(lo[iobject]);
                    }
                }
            }   
        }
        return result.toArray();
    }
}