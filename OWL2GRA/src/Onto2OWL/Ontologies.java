package Onto2OWL;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JTextArea;

public class Ontologies{
    public ArrayList<Concepts> concept;
    public ArrayList<Hierarchies> hierarchy;
    public ArrayList<Relations> relation;
    public ArrayList<Triples> triple;

    public Ontologies(){
        this.concept=new ArrayList<Concepts>();
        this.hierarchy=new ArrayList<Hierarchies>();
        this.relation=new ArrayList<Relations>();
        this.triple=new ArrayList<Triples>();
    }
}