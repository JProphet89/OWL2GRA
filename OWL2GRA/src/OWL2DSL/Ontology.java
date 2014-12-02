/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package OWL2DSL;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author jprophet89
 */

public class Ontology {
    
    public ArrayList <Concepts> concepts;
    public ArrayList <Hierarchies> hierarcy;
    public ArrayList <TripleDomain> tripledomain;
    public ArrayList <TripleRange> triplerange;
    
    public Ontology(){
        this.concepts=new ArrayList();
        this.hierarcy=new ArrayList();
        this.tripledomain=new ArrayList();
        this.triplerange=new ArrayList();
    }

    public void addConcept(String name) {
        Concepts temp=new Concepts();
        temp.name=name;
        this.concepts.add(temp);
    }

    public void addHierarchy(String class1, String class2) {
        boolean flag1=false;
        boolean flag2=false;
        Concepts classe=null,subclasse=null;
        for(Iterator i=this.concepts.iterator();i.hasNext();){
            Concepts temp=(Concepts) i.next();
            if(temp.name.equals(class1)){
                classe=temp;
                flag1=true;
            }
            if(temp.name.equals(class2)){
                subclasse=temp;
                flag2=true;
            }
        }
        if(flag1==true && flag2==true){
            Hierarchies htemp=new Hierarchies();
            htemp.class_name=classe;
            htemp.subclass_name=subclasse;
            this.hierarcy.add(htemp);
        }else{
            return;
        }
    }

    public void addPropertyRange(String range, String relation, int max, int min, String domain) {
        if(range!=null && relation!=null){
            for(int i=0;i<this.concepts.size();i++){
                if(domain.equals(this.concepts.get(i).name)){
                    this.concepts.get(i).triples.add(new TripleRange(range,relation,max,min));
                }
            }
        }
    }


    public void addAttribute(String classe, String attribute, String type) {
        for(int i=0;i<this.concepts.size();i++){
            Concepts ctemp=this.concepts.get(i);
            if(ctemp.name.equals(classe)){
                Attributes newattribute=new Attributes();
                newattribute.name=attribute;
                newattribute.type=type;
                ctemp.atribute.add(newattribute);
                this.concepts.set(i,ctemp);
                return;
            }

        }
    }

}
