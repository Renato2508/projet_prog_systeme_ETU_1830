package annexes;

import java.io.*;

public class Terminal{

    public static BufferedReader executer(String cmd) throws Exception{
        try{
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(pr.getInputStream()));        //le resultat final qu'on lirea individuellement selon les cas
            return reader;
        }
        catch(Exception e){
            throw e;
        }
        
    }
}
