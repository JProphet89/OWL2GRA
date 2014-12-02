/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Onto2OWL;
import java.util.Iterator;
import java.util.ArrayList;

/**
 *
 * @author jprophet89
 */
public class Triples {
    public String class1;
    public String relation;
    public int min;
    public int max;
    public String class2;
    
    public int CheckConcept1(ArrayList<Concepts> conceitos){
        Iterator i=conceitos.iterator();
        while(i.hasNext()){
            Concepts tmp=(Concepts)i.next();
            if(tmp.name.equals(this.class1)){
                return 0;
            }
        }
        return 1;
    }
    
    public int CheckConcept2(ArrayList<Concepts> conceitos){
        Iterator i=conceitos.iterator();
        while(i.hasNext()){
            Concepts tmp=(Concepts)i.next();
            if(tmp.name.equals(this.class2)){
                return 0;
            }
        }
        return 1;
    }
    
    public int CheckRelation(ArrayList<Relations> relacoes){
        Iterator i=relacoes.iterator();
        while(i.hasNext()){
            Relations tmp=(Relations)i.next();
            if(tmp.name.equals(this.relation)){
                return 0;
            }
        }
        return 1;
    }
}
