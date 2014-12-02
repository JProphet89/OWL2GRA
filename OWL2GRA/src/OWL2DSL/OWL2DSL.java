package OWL2DSL;

import OWL2DSL.GramConstructor.GramarConstructor;
import antlr.Grammar;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jprophet89 on 01/07/14.
 */
public class OWL2DSL {

    public String RunModule(String path_to_ontology,String name_for_grammar) throws OWLOntologyCreationException, IOException {
        String output="Starting the Module";
        output+="Start Parsing\n";
        Parser parser=new Parser();
        if(!new File(path_to_ontology).exists()){
            output+="\nOntology is 404!";
            return output;
        }
        ArrayList<Object> parsingresults=parser.OntologyParser(path_to_ontology);
        Ontology onto=(Ontology)parsingresults.get(0);
        output+=(String)parsingresults.get(1);
        if(onto!=null){
            GramarConstructor grammarconstruction=new GramarConstructor();
            grammarconstruction.buildgrammartree(onto,name_for_grammar,path_to_ontology);
            output+="\nThe Grammar has been successfully generated";
        }
        output+="\nEnd of processing";
        return output;
    }

}
