package Onto2OWL;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import uk.ac.manchester.cs.owl.owlapi.OWLClassExpressionImpl;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by jprophet89 on 29/06/14.
 */
public class Onto2OWL {

    public String RunModule(String ontofilepath,String name_for_ontology) throws org.antlr.runtime.RecognitionException, IOException, OWLOntologyCreationException {
        String output="";
        File ontofile=new File(ontofilepath);
        if(!ontofile.exists()){
            output+="The OntoDL File was not found please try again."+"\n";
            return output;
        }
        output+="Starting the Onto2OWL Module::"+"\n";
        /*
        * Run the OntoDL grammar to extract the information and return the Ontologies
        * */

        OntoDLLexer lex = new OntoDLLexer(new ANTLRFileStream(ontofile.getAbsolutePath(), "UTF8"));
        CommonTokenStream tokens = new CommonTokenStream(lex);
        OntoDLParser g = new OntoDLParser(tokens,null);
        Ontologies ontodl_result=g.ontology();
        output+="OntoDL grammar finnish the pre-processing"+"\n";
        /*
        * Build the OWL file
        * */
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLDataFactory df = OWLManager.getOWLDataFactory();
        String base = "http://example.com/onto2owl/"+name_for_ontology+"/";
        OWLOntology new_ontology=manager.createOntology(IRI.create(base));
        ArrayList<OWLClass> addedClasses=new ArrayList<OWLClass>();
        for (Concepts temp_concept : ontodl_result.concept) {
            //Create the class
            output+="Creating Class=" + temp_concept.name+"\n";
            OWLClass classe=df.getOWLClass(IRI.create(base+"#"+temp_concept.name));
            addedClasses.add(classe);
            OWLAxiom AxiomDeclaration=df.getOWLDeclarationAxiom(classe);
            manager.addAxiom(new_ontology,AxiomDeclaration);
            //Hierarchies
            for(Hierarchies temp_hierarchies : ontodl_result.hierarchy){
                if(temp_hierarchies.class_name.equals(temp_concept.name)){
                    output+="   Creating SubClass=" + temp_hierarchies.subclass_name+"\n";
                    OWLClass owlsubClass=df.getOWLClass(IRI.create(base+"#"+temp_hierarchies.subclass_name));
                    addedClasses.add(owlsubClass);
                    OWLAxiom AxiomsubDeclaration=df.getOWLDeclarationAxiom(owlsubClass);
                    manager.addAxiom(new_ontology,AxiomsubDeclaration);
                    OWLSubClassOfAxiom subClassOfAxiom=df.getOWLSubClassOfAxiom(classe,owlsubClass);
                    manager.addAxiom(new_ontology,subClassOfAxiom);
                }
            }
            //DataProperties
            for(Attributes attributes : temp_concept.atribute){
                output+="   Creating DataProperty=" + attributes.name+"\n";
                OWLDataProperty dataProperty=df.getOWLDataProperty(IRI.create(base+"#"+attributes.name));
                OWLDataRange dataRange=df.getOWLDatatype(IRI.create(attributes.type));
                //Declaration
                OWLProperty property=df.getOWLDataProperty(IRI.create(base+"#"+attributes.name));
                OWLDeclarationAxiom declarationAxiom=df.getOWLDeclarationAxiom(property);
                manager.addAxiom(new_ontology,declarationAxiom);
                //Domain and ange
                OWLDataPropertyDomainAxiom dataPropertyDomainAxiom=df.getOWLDataPropertyDomainAxiom(dataProperty,classe);
                OWLDataPropertyRangeAxiom dataPropertyRangeAxiom=df.getOWLDataPropertyRangeAxiom(dataProperty,dataRange);
                manager.addAxiom(new_ontology,dataPropertyDomainAxiom);
                manager.addAxiom(new_ontology,dataPropertyRangeAxiom);
            }
        }
        //ObjectProperties
        for(Triples relations : ontodl_result.triple){
            output+="   Creating ObjectProperty=" + relations.class1+"<>"+ relations.class2+"\n";
            OWLClass domain = null,range=null;
            for(OWLClass owlClass:addedClasses){
                if(owlClass.toStringID().equals(base+"#"+relations.class1)){
                    domain=owlClass;
                }
                if(owlClass.toStringID().equals(base+"#"+relations.class2)){
                    range=owlClass;
                }
            }
            //Property Declaration
            OWLObjectProperty objectProperty=df.getOWLObjectProperty(IRI.create(base+"#"+relations.relation));
            OWLDeclarationAxiom declarationAxiom=df.getOWLDeclarationAxiom(objectProperty);
            manager.addAxiom(new_ontology,declarationAxiom);

            OWLObjectPropertyDomainAxiom objectPropertyDomainAxiom=df.getOWLObjectPropertyDomainAxiom(objectProperty,domain);
            manager.addAxiom(new_ontology,objectPropertyDomainAxiom);
            //Object property Range with cardinality
            OWLAnnotationValue domainval=IRI.create("#"+relations.class1);
            OWLAnnotation domainann =  df.getOWLAnnotation(df.getOWLBackwardCompatibleWith(),domainval);
            if(relations.min>0 || relations.max>0){
                OWLClassExpression minCardinality = null;
                if(relations.min>0){
                    minCardinality=df.getOWLObjectMinCardinality(relations.min, objectProperty, range.getNNF());
                }
                OWLClassExpression maxCardinality =null;
                if(relations.max>0){

                    maxCardinality=df.getOWLObjectMaxCardinality(relations.max, objectProperty,range.getNNF());
                }
                if(minCardinality!=null && maxCardinality==null){
                    OWLClassExpression expression=minCardinality;
                    OWLSubClassOfAxiom subClassOfAxiom=df.getOWLSubClassOfAxiom(df.getOWLClass(IRI.create(relations.class2)), expression);
                    OWLObjectPropertyRangeAxiom owlObjectPropertyRangeAxiom=df.getOWLObjectPropertyRangeAxiom(objectProperty,subClassOfAxiom.getSuperClass(),new HashSet<OWLAnnotation>(Arrays.asList(domainann)));
                    manager.addAxiom(new_ontology,owlObjectPropertyRangeAxiom);
                }
                if(minCardinality==null && maxCardinality!=null){
                    OWLClassExpression expression=maxCardinality;
                    OWLSubClassOfAxiom subClassOfAxiom=df.getOWLSubClassOfAxiom(df.getOWLClass(IRI.create(relations.class2)), expression);
                    OWLObjectPropertyRangeAxiom owlObjectPropertyRangeAxiom=df.getOWLObjectPropertyRangeAxiom(objectProperty,subClassOfAxiom.getSuperClass(),new HashSet<OWLAnnotation>(Arrays.asList(domainann)));
                    manager.addAxiom(new_ontology,owlObjectPropertyRangeAxiom);
                }
                if(minCardinality!=null && maxCardinality!=null){
                    ArrayList<OWLClassExpression> union=new ArrayList();
                    union.add(minCardinality);
                    union.add(maxCardinality);
                    OWLClassExpression expression=df.getOWLObjectUnionOf(new HashSet<OWLClassExpression>(union));
                    OWLSubClassOfAxiom subClassOfAxiom=df.getOWLSubClassOfAxiom(df.getOWLClass(IRI.create(relations.class2)), expression);
                    OWLObjectPropertyRangeAxiom owlObjectPropertyRangeAxiom=df.getOWLObjectPropertyRangeAxiom(objectProperty,subClassOfAxiom.getSuperClass(),new HashSet<OWLAnnotation>(Arrays.asList(domainann)));
                    manager.addAxiom(new_ontology,owlObjectPropertyRangeAxiom);
                }
            }else{
                OWLObjectPropertyRangeAxiom owlObjectPropertyRangeAxiom=df.getOWLObjectPropertyRangeAxiom(objectProperty, range,new HashSet<OWLAnnotation>(Arrays.asList(domainann)));
                manager.addAxiom(new_ontology, owlObjectPropertyRangeAxiom);
            }
        }
        output+="\n\nGenerating "+name_for_ontology+".owl file;";
        File file =new File(name_for_ontology+".owl");
        // By default ontologies are saved in the format from which they were
        // loaded. In this case the ontology was loaded from an rdf/xml file We
        // can get information about the format of an ontology from its manager
        OWLOntologyFormat format = manager.getOntologyFormat(new_ontology);
        // We can save the ontology in a different format Lets save the ontology
        // in owl/xml format
        OWLXMLOntologyFormat owlxmlFormat = new OWLXMLOntologyFormat();
        // Some ontology formats support prefix names and prefix IRIs. In our
        // case we loaded the pizza ontology from an rdf/xml format, which
        // supports prefixes. When we save the ontology in the new format we
        // will copy the prefixes over so that we have nicely abbreviated IRIs
        // in the new ontology document
        if (format.isPrefixOWLOntologyFormat()) {
            owlxmlFormat.copyPrefixesFrom(format.asPrefixOWLOntologyFormat());
        }
        try {
            manager.saveOntology(new_ontology, owlxmlFormat,IRI.create(file.toURI()));
        } catch (OWLOntologyStorageException e) {
            e.printStackTrace();
        }
        output+="\nDone generating the file!" +
                "\nStatistics::" +
                "\n\tClasses found::"+new_ontology.getClassesInSignature().size()+
                "\n\tObjectProperties found::"+new_ontology.getObjectPropertiesInSignature().size()+
                "\n\tDataProperties found::"+new_ontology.getDataPropertiesInSignature().size();

        return output;
    }
}
