package OWL2DSL;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jprophet89 on 03/09/14.
 */
public class Tripleval {

    public ArrayList<Triplehandler> triplos=new ArrayList();


    public void AddHandler(Triplehandler novo){
        this.triplos.add(novo);
    }

    public boolean IsPresent(Triplehandler check){
        for(Triplehandler trip:triplos){
            if(trip.range.equals(check.range) && trip.domain.equals(check.domain) && trip.prop.equals(check.prop)){
                return true;
            }
        }
        return false;
    }

}
