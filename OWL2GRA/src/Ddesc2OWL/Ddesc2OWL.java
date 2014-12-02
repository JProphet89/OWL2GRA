package Ddesc2OWL;

import antlr.RecognitionException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;

import java.io.*;
import java.util.Iterator;

/**
 * Created by jprophet89 on 01/07/14.
 */
public class Ddesc2OWL {

    public String RunModule(String path_to_ontology, String path_to_grammar, String path_to_ddesc, String path) throws IOException, InterruptedException, ParseException, OWLOntologyCreationException, OWLOntologyStorageException {
        String output="";
        output+="\n\njava -jar "+path+"/antlr-3.5.1-complete.jar -Dfile.encoding=utf-8 "+path_to_grammar;
        Process p=Runtime.getRuntime().exec("java -jar "+path+"/antlr-3.5.1-complete.jar -Dfile.encoding=utf-8 "+path_to_grammar);
        p.waitFor();
        if(p.exitValue()==0){
            DataInputStream dis = new DataInputStream(p.getErrorStream());
            String line = "";
            while ( (line = dis.readLine()) != null)
            {
                output+="\nError::"+line;
            }
            dis.close();
        }else{
            DataInputStream dis = new DataInputStream(p.getErrorStream());
            String line = "";
            while ( (line = dis.readLine()) != null)
            {
                output+="\nError::"+line;
            }
            dis.close();
        }
        output+="\n\njavac -cp "+path+"/antlr-3.5.1-complete.jar:"+path+"/commons-io-2.4.jar:$CLASSPATH:"+path+"/dom4j-2.0.0-ALPHA-2.jar:"+path+"/json-simple-1.1.1.jar:. -encoding utf8 Main.java";
        if(System.getProperties().getProperty("os.name").toLowerCase().contains("windows")){
            p=Runtime.getRuntime().exec("javac -cp "+path+"/antlr-3.5.1-complete.jar;"+path+"/commons-io-2.4.jar;"+path+"/dom4j-2.0.0-ALPHA-2.jar;"+path+"/json-simple-1.1.1.jar;. Main.java");
        }else{
            p=Runtime.getRuntime().exec("javac -cp "+path+"/antlr-3.5.1-complete.jar:"+path+"/commons-io-2.4.jar:$CLASSPATH:"+path+"/dom4j-2.0.0-ALPHA-2.jar:"+path+"/json-simple-1.1.1.jar:. Main.java");
        }
        p.waitFor();
        if(p.exitValue()==0){
            DataInputStream dis = new DataInputStream(p.getErrorStream());
            String line = "";
            while ( (line = dis.readLine()) != null)
            {
                output+="\nError::"+line;
            }
            dis.close();
        }else{
            DataInputStream dis = new DataInputStream(p.getErrorStream());
            String line = "";
            while ( (line = dis.readLine()) != null)
            {
                output+="\nError::"+line;
            }
            dis.close();
        }
        output+="\n\njava -cp "+path+"/antlr-3.5.1-complete.jar:"+path+"/commons-io-2.4.jar:$CLASSPATH:"+path+"/json-simple-1.1.1.jar:.  Main "+path_to_ddesc;
        if(System.getProperties().getProperty("os.name").toLowerCase().contains("windows")){
            p=Runtime.getRuntime().exec("java -cp "+path+"/antlr-3.5.1-complete.jar;"+path+"/commons-io-2.4.jar;"+path+"/json-simple-1.1.1.jar;. Main "+path_to_ddesc);
        }else{
            p=Runtime.getRuntime().exec("java -cp "+path+"/antlr-3.5.1-complete.jar:"+path+"/commons-io-2.4.jar:$CLASSPATH:"+path+"/json-simple-1.1.1.jar:. Main "+path_to_ddesc);
        }
        p.waitFor();
        if(p.exitValue()==0){
            DataInputStream dis = new DataInputStream(p.getErrorStream());
            String line = "";
            while ( (line = dis.readLine()) != null)
            {
                output+="\nError::"+line;
            }
            dis.close();
        }else{
            DataInputStream dis = new DataInputStream(p.getErrorStream());
            String line = "";
            while ( (line = dis.readLine()) != null)
            {
                output+="Error::"+line;
            }
            dis.close();
        }
        output+=Individualsgeneration(path_to_ontology);
        return output;
    }


    public String Individualsgeneration(String path_to_ontology) throws IOException, ParseException, OWLOntologyCreationException, OWLOntologyStorageException {
        String output="";

        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        File ontology_file=new File(path_to_ontology);
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(ontology_file);
        OWLDataFactory df=OWLManager.getOWLDataFactory();

        System.out.println("Individuals generation");

        System.out.println(ontology.getOntologyID());

        //Generate all the individuals for the ontology
        JSONParser parser = new JSONParser();
        File metajson=new File("metadata.json");
        if(!metajson.exists()){
            output+="\nERROR::Metadata.json was not found the process was not completed!!";
            return output;
        }
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("metadata.json"));
        JSONArray msg = (JSONArray) jsonObject.get("instancias");
        for(Iterator<JSONObject> i = msg.iterator();i.hasNext();){
            JSONObject obj=i.next();//get the object that will be processed
            //Individual and Class type generation
            OWLNamedIndividual name =df.getOWLNamedIndividual(IRI.create(obj.get("name").toString()));
            OWLClass type = df.getOWLClass(IRI.create(ontology.getOntologyID().getOntologyIRI()+"#"+obj.get("type").toString()));
            OWLClassAssertionAxiom classAssertion = df.getOWLClassAssertionAxiom(type, name);
            manager.addAxiom(ontology,classAssertion);
            //DataProperties generation
            if(obj.containsKey("dataprop")){
                JSONArray object=(JSONArray)obj.get("dataprop");
                for(Iterator<JSONObject> o = object.iterator();o.hasNext();){
                    JSONObject otemp=o.next();
                    OWLDataProperty prop = df.getOWLDataProperty(IRI.create(ontology.getOntologyID().getOntologyIRI() + "#" + otemp.get("prop").toString()));
                    OWLDataPropertyAssertionAxiom dataPropertyAssertionAxiom = df.getOWLDataPropertyAssertionAxiom(prop, name, otemp.get("data").toString());
                    manager.addAxiom(ontology,dataPropertyAssertionAxiom);
                }
            }
            //ObjectProperties generation
            if(obj.containsKey("objprop")){
                JSONArray object=(JSONArray)obj.get("objprop");
                for(Iterator<JSONObject> o = object.iterator();o.hasNext();){
                    JSONObject otemp=o.next();
                    OWLObjectProperty prop = df.getOWLObjectProperty(IRI.create(ontology.getOntologyID().getOntologyIRI()+"#"+otemp.get("prop").toString()));
                    OWLNamedIndividual connected_indiv =df.getOWLNamedIndividual(IRI.create(otemp.get("data").toString()));
                    OWLObjectPropertyAssertionAxiom propertyAssertion = df.getOWLObjectPropertyAssertionAxiom(prop,name,connected_indiv);
                    manager.addAxiom(ontology,propertyAssertion);
                }
            }
        }
        manager.saveOntology(ontology);
        output+="\n\nThe process was complete without accidents!!";
        return output;
    }
}
