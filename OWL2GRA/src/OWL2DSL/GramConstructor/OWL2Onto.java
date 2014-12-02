package OWL2DSL.GramConstructor;

import OWL2DSL.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;

/**
 * Created by jprophet89 on 05/03/14.
 */
public class OWL2Onto {

    public void Ontofilecreator(Ontology onto,String name){
        String file="Ontology{\n";

        //Conecpts
        file+="\tConcepts[\n";
        for(Iterator i=onto.concepts.iterator();i.hasNext();){
            Concepts temp=(Concepts) i.next();
            file+="\t\t{"+temp.name;
            if(!temp.atribute.isEmpty()){
                file+=",Attributes[";
                for(Iterator j=temp.atribute.iterator();j.hasNext();){
                    Attributes atemp=(Attributes)j.next();
                    file+="{"+atemp.name.toLowerCase()+" "+atemp.type.toLowerCase()+"}";
                }
                file+="]";
            }
            file+="}\n";
        }
        file+="\t]\n";

        //Hierarchies
        file+="\tHierarchies[\n";
        for(Iterator i=onto.hierarcy.iterator();i.hasNext();){
            Hierarchies htemp=(Hierarchies) i.next();
            file+="\t\t{"+htemp.class_name.name+" > "+htemp.subclass_name.name+"}\n";
        }
        file+="\t]\n";
        //Relations
        file+="\tRelations[\n";
        for(Iterator i=onto.tripledomain.iterator();i.hasNext();){
            TripleDomain trtemp=(TripleDomain)i.next();
            file+="\t\t{"+trtemp.relation.substring(trtemp.relation.indexOf("#")+1).toLowerCase()+"}\n";
        }
        file+="\t]\n";

        //Links
        file+="\tLinks[\n";
        for(Iterator i=onto.tripledomain.iterator();i.hasNext();){
            TripleDomain trtemp=(TripleDomain)i.next();
            for(Iterator j=onto.triplerange.iterator();j.hasNext();){
                TripleRange rantemp=(TripleRange)j.next();
                if(rantemp.relation.equals(trtemp.relation)){
                    file+="\t\t{"+trtemp.domain.substring(trtemp.domain.indexOf("#")+1).toLowerCase()+" "+trtemp.relation.substring(trtemp.relation.indexOf("#")+1).toLowerCase()+" "+rantemp.range.substring(rantemp.range.indexOf("#")+1).toLowerCase()+"}\n";
                }
            }
        }

        file+="\t]\n";
        file+='}';
        PrintStream out = null;
        try {
            try {
                out = new PrintStream(new FileOutputStream(name+".onto"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.print(file);
        }
        finally {
            if (out != null) out.close();
        }
    }
}
