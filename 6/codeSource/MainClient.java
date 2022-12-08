package parcInfo.client;

import java.net.SocketException;

import javax.sound.sampled.SourceDataLine;

import parcInfo.client.Client;
import parcInfo.data.*;

public class MainClient {
    public static void main(String[] args){
        Client client = new Client();    
        String ip = "localhost";
        int port = 1212;
        client.connecter(ip, port);
        //System.out.println("Le socket de ce client:\t"+client.getSocket());
            
        try{
            client.sendObject(client.getDonneesStatiques().toVector()); //envoi unique des donnees statiques
            while(true){
            //envoi periodique des donnees dynamques avec intervalles
                try{
                    DonneesDynamiques dn = client.getDonneesDynamiques();
                    System.out.println("DN:"+dn.toVector());
                    
                    client.sendObject(dn.toVector());
                    Thread.sleep(5000);       
                }
                catch(SocketException se1){
                    try{
                        client.traiterSocketException(se1);         //traitement: fermer le socket et les canaux de communication
                        break;
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }                
                }
                catch(Exception e){
                    e.printStackTrace();
                } 
            
            }
        }
        catch(SocketException se2){
            try{
                client.traiterSocketException(se2);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }            }
        catch(Exception e1){
            e1.printStackTrace();
        }
    }
}
