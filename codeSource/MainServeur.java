package parcInfo.serveur;

public class MainServeur {
    public static void main(String[] args){
        Serveur serveur = null;
        try{
            serveur = new Serveur(1212);
            ConnectionBuilder cb = new ConnectionBuilder(serveur);
            cb.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
