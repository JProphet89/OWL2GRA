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
public class TripleDomain {
    public String domain;
    public String relation;

    public TripleDomain() {
        this.domain=null;
        this.relation=null;
    }

    public TripleDomain(String s, String ss) {
        this.domain=s;
        this.relation=ss;
    }
    
    
    
    
}
