package parcInfo.client;

import com.sun.management.OperatingSystemMXBean;
//import java.lang.management.OperatingSystemMXBean;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.server.LogStream;
import java.util.Scanner;
import java.util.Vector;
import parcInfo.data.*;

import annexes.Terminal;

import java.io.OutputStream;
import java.lang.management.ManagementFactory;

public class Client {
    Socket socket;
    String host = null;
    int port;
    String os;
    OperatingSystemMXBean bean;

    // _ _ _ CONSTRUCTORS _ _ _
    public Client() throws Exception{
        try {
            this.initPortHost();
            this.setOs();
            this.setBean((com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean());
            this.connecter(this.getHost(), this.getPort());
        } 
        catch (Exception e) {
            throw e;
        }       
    }

    // _ _ _ METHODS  _ _ _   
    //--- Fonctions d'initialisation

    public void connecter(String ip, int port){
        //obtenir la connexion d'un socketServer
        System.out.println("Attente d'une connexion");
        while(this.socket == null)
        {
            try 
            { 
                this.setSocket(new Socket(ip, port));  
                System.out.println("Connexion reussie"); 
                //this.socket   = new Socket("192.168.10.54" , 1212);                     //serveur dans ce pc local et le port pour communiquer   
            }
            catch(java.net.ConnectException e1){

            }
            catch (Exception e) 
            {
                //e.printStackTrace();
                System.err.println(e);
            }
        }
    }
    public void  initPortHost() throws Exception{
        int port = 0;
        String host;
        String path = "parcInfo/init.nato",
                donneeBrute;
        File source = new File(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
        try {
            donneeBrute = br.readLine();
            String[] donnees = donneeBrute.split(":");
            host = donnees[1].trim();

            donneeBrute = br.readLine();
            donnees = donneeBrute.split(":");
            port = Integer.valueOf(donnees[1].trim());
        }
        catch (Exception e) {
            throw e;
        }
        this.setPort(port);
        this.setHost(host);
    }
    
    // --- Donnes statiques ---
    public DonneesStatiques getDonneesStatiques(){
        //les donnes
        String os = this.os;
        String nomPc = "";
        String processeur = "";
	    String installedRam = "";

        try {
            processeur = this.getCpuName();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            nomPc = this.getNomPc();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            installedRam = String.valueOf(this.getInstalledRam());
        } 
        catch (Exception e3) {
            e3.printStackTrace();
        }

        return new DonneesStatiques(os, nomPc, processeur, installedRam);
    }
    public double getInstalledRam() throws Exception{
        Long ram = this.bean.getTotalMemorySize();
        return ram.doubleValue()/Double.valueOf(String.valueOf(1024*1024));                //obtenir une taille en Mo

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

    // --- Donnees dynamiques ---
    public DonneesDynamiques getDonneesDynamiques(){
        
        //Les donnes en question
        String availableRam = "";
        String cpuLoadPercentage = "";

        try {
            availableRam = String.valueOf(this.getAvailableRam());
        } catch (Exception e) {
            
        }
    
        try {
            cpuLoadPercentage = String.valueOf(this.getCpuLoadPercentage());
            
        } catch (Exception e) {
            
        }
        DonneesDynamiques dn = new DonneesDynamiques(availableRam, cpuLoadPercentage);
        return dn;
    }

    public double getCpuLoadPercentage() throws Exception{
        return this.bean.getCpuLoad()*100;   
    }
    
    public  double getAvailableRam() throws Exception{
        Long ramLibre = this.bean.getFreeMemorySize();
        return ramLibre.doubleValue()/Double.valueOf(String.valueOf(1024*1024));                //obtenir une taille en Mo
    }

    //Autres methodes
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
    public void traiterSocketException(SocketException se) throws Exception {
        //System.out.println(se.getMessage());
          if(se.getMessage().contains("Connection reset by peer")){
            System.out.println("Connexion au serveur interrompue:\t fermeture...");
            try{
                this.socket.getInputStream().close();
                //this.socket.getOutputStream().close();
                this.socket.close();
            }
            catch(Exception e){
                throw e;
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

    public String getHost() throws Exception{
        if(this.host == null) throw new Exception("Hote non initialise");
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() throws Exception{
        if(this.port == 0) throw new Exception("Port non initialise");
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public OperatingSystemMXBean getBean() {
        return bean;
    }

    public void setBean(OperatingSystemMXBean bean) {
        this.bean = bean;
    }

}