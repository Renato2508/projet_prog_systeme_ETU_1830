package parcInfo.client;

import java.net.SocketException;

import javax.sound.sampled.SourceDataLine;

import parcInfo.client.Client;
import parcInfo.data.*;

public class MainClient {
    public static void main(String[] args){
        Client client = null;            
        try{
            client = new Client(); 
            client.sendObject(client.getDonneesStatiques().toVector()); //envoi unique des donnees statiques
            while(true){
            //envoi periodique des donnees dynamques avec intervalles
                try{
                    DonneesDynamiques dn = client.getDonneesDynamiques();
                    
                    client.sendObject(dn.toVector());
                    Thread.sleep(3000);       
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
