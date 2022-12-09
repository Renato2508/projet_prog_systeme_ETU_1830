package annexes;

import parcInfo.client.Client;

public class MainTest {
    public static void  main(String[] args) {
        Client client = new Client();

        try {
            System.out.println("NOM: \t"+client.getNomPc());
            System.out.println("CPU: \t"+client.getCpuName());
            System.out.println("Installed memory: \t"+client.getInstalledRam());
            System.out.println("Available memory: \t"+client.getAvailableRam());
            System.out.println("Mass storage: \t"+client.getTotalMasStorage());
            System.out.println("Available Mass storage: \t"+client.getAvailableMasStorage());
               
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
