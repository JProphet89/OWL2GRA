package OWL2DSL;

import java.util.ArrayList;

public class Concepts{
    public String name;
    public String description;
    public ArrayList<Attributes> atribute;
    public ArrayList<TripleRange> triples;

    public Concepts(){
        this.atribute=new ArrayList();
        this.triples=new ArrayList();
    }


}