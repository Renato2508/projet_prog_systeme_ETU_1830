package parcInfo.client;

import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import annexes.Terminal;

import java.io.OutputStream;

public class Client {
    Socket socket;
    String os;

    // _ _ _ CONSTRUCTORS _ _ _
    public Client(){
        this.setOs();
    }

    // _ _ _ METHODS  _ _ _ 

    public String getAvailableMasStorage() throws Exception{
        String res = null;                     // le resultat final
        BufferedReader reader = null;   //le buffered reader qui lira les donnees
        String cmd = this.os == "Linux" ? "df -h --total" : "wmic volume get freespace";
        String tempo = null;            //chaine temporaire lors des parcours de reader
        try{
            reader = Terminal.executer(cmd);                //execution de la commande de terminal
            if(this.os.equals("Windows")){
                for(int i = 1; i<4; i++){
                    tempo = reader.readLine();
                }
                res = tempo;
            }
            else if(this.os.equals("Linux")){
                while((tempo = reader.readLine()).contains("total") == false){
                }
                
                Vector<String> liste = new Vector<String>();
                Scanner scan=new Scanner(tempo);                     //en fait la ligne de resultat obtenue contient plusieurs valeurs
                while(scan.hasNext())
                    liste.add(scan.next());
                res = liste.get(3);            
            }
        }
        catch(Exception e){
            throw e;
        }
        return res;
    }


    public String getTotalMasStorage() throws Exception{
        String res = null;                     // le resultat final
        BufferedReader reader = null;   //le buffered reader qui lira les donnees
        String cmd = this.os == "Linux" ? "df -h --total" : "wmic volume get capacity";
        String tempo = null;            //chaine temporaire lors des parcours de reader
        try{
            reader = Terminal.executer(cmd);                //execution de la commande de terminal
            if(this.os.equals("Windows")){
                for(int i = 1; i<4; i++){
                    tempo = reader.readLine();
                }
                res = tempo;
            }
            else if(this.os.equals("Linux")){
                while((tempo = reader.readLine()).contains("total") == false){
                }
                
                Vector<String> liste = new Vector<String>();
                Scanner scan=new Scanner(tempo);                     //en fait la ligne de resultat obtenue contient plusieurs valeurs
                while(scan.hasNext())
                    liste.add(scan.next());
                res = liste.get(1);            
            }
        }
        catch(Exception e){
            throw e;
        }
        return res;   
    }
    
    public String getAvailableRam() throws Exception{
        String res = null;                     // le resultat final
        BufferedReader reader = null;   //le buffered reader qui lira les donnees
        String cmd = this.os == "Linux" ? "cat /proc/meminfo" : "systeminfo";
        String tempo = null;            //chaine temporaire lors des parcours de reader
        try{
            reader = Terminal.executer(cmd);                //execution de la commande de terminal
            if(this.os.equals("Windows")){
                while(true){
                    tempo = reader.readLine();
                    String deComparaison = tempo.toLowerCase();
                    String ecritEng = "available physical memory";     //pour les os en ang
                    String ecritFr = "moire physique disponible:";
                    if(deComparaison.contains(ecritEng) || deComparaison.contains(ecritFr)) break;
                }
                res = tempo;
            }
            else if(this.os.equals("Linux")){
                while((tempo = reader.readLine()).contains("memavail") == false){
                }
                res = tempo;
            }
        }
        catch(Exception e){
            throw e;
        }
        return res;
    }

    public String getInstalledRam() throws Exception{
        String res = null;                     // le resultat final
        BufferedReader reader = null;   //le buffered reader qui lira les donnees
        String cmd = this.os == "Linux" ? "cat /proc/meminfo" : "wmic memorychip get capacity";
        String tempo = null;            //chaine temporaire lors des parcours de reader
        try{
            reader = Terminal.executer(cmd);                //execution de la commande de terminal
            if(this.os.equals("Windows")){
                for(int i = 1; i<4; i++){
                    tempo = reader.readLine();
                }
                res = tempo;
            }
            else if(this.os.equals("Linux")){
                while((tempo = reader.readLine()).contains("memetotal") == false){
                }
                res = tempo;
            }
        }
        catch(Exception e){
            throw e;
        }
        return res;
    }

    public String getCpuName() throws Exception{
        String res = null;                     // le resultat final
        BufferedReader reader = null;   //le buffered reader qui lira les donnees
        String cmd = this.os == "Linux" ? "cat /proc/cpuinfo" : "wmic cpu get name";
        String tempo = null;            //chaine temporaire lors des parcours de reader
        try{
            reader = Terminal.executer(cmd);                //execution de la commande de terminal
            if(this.os.equals("Windows")){
                for(int i = 1; i<4; i++){
                    tempo = reader.readLine();
                }
                res = tempo;
            }
            else if(this.os.equals("Linux")){
                while((tempo = reader.readLine()).contains("model name") == false){
                }
                res = tempo;
            }
        }
        catch(Exception e){
            throw e;
        }
        return res; 
    }
    
    public String getNomPc() throws Exception{          //obtenu a partir de l'execution sur terminal d'une commande
        
        String res;                     // le resultat final
        String cmd = "hostname";
        BufferedReader reader = null;   //le buffered reader qui lira les donnees
        try{
            reader = Terminal.executer(cmd);
            res = reader.readLine();
        }
        catch(Exception e){
            throw e;
        }
        
        return res;                           //le resultat est obternu immediatement a la premiere ligne
    }

    public  void sendObject(Object sortie) throws Exception
    {   
        try{
            OutputStream fluxSortie = this.socket.getOutputStream();
            ObjectOutputStream objetSortie = new ObjectOutputStream(fluxSortie);
            objetSortie.writeObject(sortie);
            objetSortie.flush();    
        }
        catch(Exception e)
        {
            throw e;
        }
    }

    public void connecter(String ip, int port){
        //obtenir la connexion d'un socketServer
        while(this.socket == null)
        {
            try 
            {
                this.setSocket(new Socket(ip, port));   
                //this.socket   = new Socket("192.168.10.54" , 1212);                     //serveur dans ce pc local et le port pour communiquer   
            }
            catch (Exception e) 
            {
                //e.printStackTrace();
                System.err.println(e);
            }
        }
    }

    //  _ _ _ SET GET _ _ _ 

    public void setOs() {
        String os = System.getProperty("os.name");
        if(os.contains("Windows")) this.os = "Windows";
        else if(os.contains("Linux")) this.os = "Linux";
        
    }

    public String getOs() {
        return os;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public Socket getSocket() {
        return socket;
    }

}