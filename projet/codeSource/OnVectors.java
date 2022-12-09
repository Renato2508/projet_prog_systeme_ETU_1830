package annexe;

import java.util.Vector;

public class OnVectors
{ 
    public static Vector cat(Vector v1, Vector v2)
    {   
        Vector res = new Vector();
        //System.out.println("V1:"+v1);
        //System.out.println("V2:"+v2);
        
        if(v1 == null)   res = v2;
        else if(v2 == null)   res = v1;
        else if (v1 != null && v2 != null ){
            for(Object o: v1)
            {   
                if(o != null)
                res.add(o);
            }
            for(Object o2: v2){
                if(o2 != null)
                res.add(o2);
            }
        }
        //System.out.println("CAT:"+res);
        return res;
    }
}
