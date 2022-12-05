package parcInfo.client;

import parcInfo.client.Client;

public class MainClient {
    public static void main(String[] args){
        try{
            Client client = new Client();
            String ip = "localhost";
            int port = 1212;
            client.connecter(ip, port);
            //System.out.println("Le socket de ce client:\t"+client.getSocket());
            while(true){
                client.sendObject("Bonjour");
            }
            //client.getSocket().close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
