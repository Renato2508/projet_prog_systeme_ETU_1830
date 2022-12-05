package parcInfo.serveur;

public class HoteListener extends Thread {
    Hote hote;

    // _ _ _  CONSTRUCTORS

    public HoteListener(Hote hote){
        this.setHote(hote);
    }

    // _ _ _ METHODS _ _ _

    public void run() {
        while(true){
            try{
                Object msg = this.hote.receiveObject();
                System.out.println("Message recu:\t"+msg);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
        }
    }


    public Hote getHote() {
        return hote;
    }


    public void setHote(Hote hote) {
        this.hote = hote;
    }
}
