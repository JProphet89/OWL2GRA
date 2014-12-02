package Onto2OWL;

import java.util.ArrayList;

public class Concepts{
    public String name;
    public String description;
    public ArrayList<Attributes> atribute;

    public Concepts(){
        this.atribute=new ArrayList<Attributes>();
    }

    public Concepts(String thing) {
        this.name=thing;
    }
}