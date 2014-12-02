/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package OWL2DSL;

/**
 *
 * @author jprophet89
 */
public class TripleRange {
    public String range;
    public int min;
    public int max;
    public String relation;

    public TripleRange() {
        this.range=null;
        this.relation=null;
    }
    
    public TripleRange(String s,String ss, int max,int min) {
        this.range=s;
        this.relation=ss;
        this.max=max;
        this.min=min;
    }


}
