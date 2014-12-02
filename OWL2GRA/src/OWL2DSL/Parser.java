package OWL2DSL;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.stringtemplate.v4.compiler.STParser;
import org.w3c.dom.ranges.Range;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jprophet89 on 01/07/14.
 */
public class Parser {

    public ArrayList OntologyParser(String path_to_ontology) throws OWLOntologyCreationException {
        Ontology onto=new Ontology();
        ArrayList results=new ArrayList();
        String output="";
        /*
        * Load the ontology from the file
        * */
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File ontology_file = new File(path_to_ontology);
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ontology_file);
        OWLDataFactory df= OWLManager.getOWLDataFactory();
        output+="\nNo problems loading the ontology";

        //Parsing Class from the ontology
        for(Iterator<OWLClass> i=ontology.getClassesInSignature().iterator();i.hasNext();){
            OWLClass axi=i.next();
            onto.addConcept(axi.toStringID().substring(axi.toStringID().indexOf("#")+1));
        }
        output+="\nNo problems parsing the ontology classes";
        //Parsing SubClass from the ontology
        for(Iterator<OWLClass> i=ontology.getClassesInSignature().iterator();i.hasNext();){
            OWLClass axi=i.next();
            if(!axi.getSubClasses(ontology).isEmpty()){
                for(OWLClassExpression classExpression:axi.getSubClasses(ontology)){
                    onto.addHierarchy(axi.toStringID().substring(axi.toStringID().indexOf("#")+1),classExpression.toString().substring(classExpression.toString().indexOf("#")+1,classExpression.toString().length()-1));
                }
            }
        }
        output+="\nNo problems parsing the ontology hierarchies";
        //Parsing DataProperties from the Ontology
        for(OWLDataProperty dataproperty:ontology.getDataPropertiesInSignature()){
            String type=dataproperty.getRanges(ontology).toString().replaceAll(ontology.getOntologyID().getOntologyIRI().toString(),"");
            type=type.replaceAll("<","");
            type=type.replaceAll(">","");
            type=type.substring(1,type.length()-1);
            for(OWLClassExpression classExpression:dataproperty.getDomains(ontology)){
                onto.addAttribute(classExpression.toString().substring(classExpression.toString().indexOf("#")+1,classExpression.toString().length()-1),dataproperty.toStringID().substring(dataproperty.toStringID().indexOf("#")+1),type);
            }
        }
        output+="\nNo problems parsing the ontology dataproperties";

        //Parsing ObjectProperties from the Ontology
        /***
         * Process all the properties with one or none cardinalities
         *
         *
         */
        Tripleval validator=new Tripleval();
        int j=0;
        for(OWLObjectProperty objectProperty:ontology.getObjectPropertiesInSignature()){
            for(OWLObjectPropertyRangeAxiom propertyRangeAxiom:ontology.getObjectPropertyRangeAxioms(objectProperty.getNamedProperty())){
                //Domain
                String domain=propertyRangeAxiom.getAnnotations().toString().substring(propertyRangeAxiom.getAnnotations().toString().indexOf("#") + 1);
                domain=domain.substring(0,domain.indexOf(">"));
                //Property
                String property=propertyRangeAxiom.getProperty().toString().substring(propertyRangeAxiom.getProperty().toString().indexOf("#")+1);
                property=property.substring(0,property.indexOf(">"));
                //Range and Cardinality
                int minimum=-1,maximum=-1;
                String range="";
                if(!propertyRangeAxiom.getNestedClassExpressions().toString().contains("ObjectUnionOf") && !propertyRangeAxiom.getNestedClassExpressions().toString().contains("ObjectMinCardinality") && !propertyRangeAxiom.getNestedClassExpressions().toString().contains("ObjectMaxCardinality")){
                    range=propertyRangeAxiom.getNestedClassExpressions().toString().substring(propertyRangeAxiom.getNestedClassExpressions().toString().indexOf("#")+1,propertyRangeAxiom.getNestedClassExpressions().toString().indexOf(">"));
                }else{
                    if(propertyRangeAxiom.getNestedClassExpressions().toString().contains("ObjectUnionOf")){
                        for(OWLClassExpression classExpression:propertyRangeAxiom.getNestedClassExpressions()){
                            if(classExpression.toString().contains("ObjectMinCardinality") && !classExpression.toString().contains("ObjectUnionOf")){
                                minimum=Integer.parseInt(classExpression.toString().substring(classExpression.toString().indexOf("(")+1,classExpression.toString().indexOf("<")-1));
                                if(range.equals("")){
                                    range=classExpression.toString().substring(classExpression.toString().indexOf("#")+1);
                                    range=range.substring(range.toString().indexOf("#")+1);
                                    range=range.substring(0,range.toString().indexOf(">"));
                                }
                            }
                            if(classExpression.toString().contains("ObjectMaxCardinality")&& !classExpression.toString().contains("ObjectUnionOf")){
                                maximum=Integer.parseInt(classExpression.toString().substring(classExpression.toString().indexOf("(")+1,classExpression.toString().indexOf("<")-1));
                                if(range.equals("")){
                                    range=classExpression.toString().substring(classExpression.toString().indexOf("#")+1);
                                    range=range.substring(range.toString().indexOf("#")+1);
                                    range=range.substring(0,range.toString().indexOf(">"));
                                }
                            }
                        }
                    }else{
                        for(OWLClassExpression classExpression:propertyRangeAxiom.getNestedClassExpressions()){
                            if(classExpression.toString().contains("ObjectMinCardinality")){
                                minimum=Integer.parseInt(classExpression.toString().substring(classExpression.toString().indexOf("(")+1,classExpression.toString().indexOf("<")-1));
                                if(range.equals("")){
                                    range=classExpression.toString().substring(classExpression.toString().indexOf("#")+1);
                                    range=range.substring(range.toString().indexOf("#")+1);
                                    range=range.substring(0,range.toString().indexOf(">"));
                                }
                            }
                            if(classExpression.toString().contains("ObjectMaxCardinality")){
                                maximum=Integer.parseInt(classExpression.toString().substring(classExpression.toString().indexOf("(")+1,classExpression.toString().indexOf("<")-1));
                                if(range.equals("")){
                                    range=classExpression.toString().substring(classExpression.toString().indexOf("#")+1);
                                    range=range.substring(range.toString().indexOf("#")+1);
                                    range=range.substring(0,range.toString().indexOf(">"));
                                }
                            }
                        }
                    }
                }
                if(maximum<minimum && maximum>0){
                    output+="\n+-------------------WARNING---------------\n";
                    output+="| Maximum lower than minimum\n";
                    output+="| on:\n";
                    output+="| "+domain+"->"+property+"->"+range+"\n";
                    output+="+------------------------------------------\n";
                }
                onto.addPropertyRange(range,property,maximum,minimum,domain);
            }
        }
        results.add(onto);
        results.add(output);
        return results;
    }

}
