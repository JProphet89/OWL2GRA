package OWL2DSL.GramConstructor;

import OWL2DSL.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jprophet89 on 21/02/14.
 */
public class Node {

    public Concepts concep;

    public Node rootnode;

    public ArrayList<Node> subNodes;

    public Node(){
        this.concep=null;
        this.rootnode=null;
        this.subNodes=new ArrayList(1000);
    }

    public Node (Concepts c){
        concep=c;
        subNodes=new ArrayList<Node>(1000);
        rootnode=null;
    }

    public Node (Concepts c, Node up){
        concep=c;
        subNodes=new ArrayList<Node>(1000);
        rootnode=up;
    }

    public String printsubnodes() {
        String result="";
        for(Iterator i=this.subNodes.iterator();i.hasNext();){
            Node n=(Node) i.next();
            result+="::"+n.concep.name;
        }
        return result;
    }
}
