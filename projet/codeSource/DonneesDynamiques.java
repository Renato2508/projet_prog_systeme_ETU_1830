package parcInfo.data;

import java.util.Vector;

public class DonneesDynamiques {
    String availableRam;
    String cpuLoadPercentage;

    //  _ _ _  CONSTRUCTORS _ _ _ 

    public DonneesDynamiques(String availableRam, String cpuLoadPercentage){
        this.setAvailableRam(availableRam);
        this.setCpuLoadPercentage(cpuLoadPercentage);
    }

    //_ _ _METHODS 
    public Vector<String> toVector(){
        //en vue de serialiser et envoyer
        Vector<String> res = new Vector<String>();
        res.add("dyn");
        res.add(this.getOs());
        res.add(this.getAvailableRam());
        res.add(this.getCpuLoadPercentage());
        return res;
    }

    // _ _ _ GET SET _ _ _ 
    
    public String getAvailableRam() {
        return availableRam;
    }
    public void setAvailableRam(String availableRam) {
        this.availableRam = availableRam;
    }
    public String getCpuLoadPercentage() {
        return cpuLoadPercentage;
    }
    public void setCpuLoadPercentage(String cpuLoadPercentage) {
        this.cpuLoadPercentage = cpuLoadPercentage;
    }

    
}
