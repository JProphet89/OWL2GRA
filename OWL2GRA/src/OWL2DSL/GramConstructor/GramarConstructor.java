package OWL2DSL.GramConstructor;

import OWL2DSL.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by jprophet89 on 21/02/14.
 */

public class GramarConstructor {

    public Node root;
    public ArrayList<String> visitados=new ArrayList();
    public Ontology onto;

    public GramarConstructor(){
        this.root=new Node();
    }

    private Node explore(Node rootnode) {
        if(visitados.contains( rootnode.concep.name)){
            return rootnode;
        }
        for(Iterator i=this.onto.hierarcy.iterator();i.hasNext();){
            Hierarchies temp=(Hierarchies) i.next();
            if(temp.class_name.name.equals(rootnode.concep.name)){
                if(!visitados.contains(temp.subclass_name.name)){
                    Node n=new Node(temp.subclass_name);
                    n.rootnode=rootnode;
                    n.subNodes=new ArrayList();
                    rootnode.subNodes.add(explore(n));
                }
            }
        }
        visitados.add(rootnode.concep.name);
        return rootnode;
    }

    private ArrayList<Node> Connected2Root() {
        ArrayList<Node> result=new ArrayList();
        for(Iterator i=this.onto.concepts.iterator();i.hasNext();){
            Concepts ctemp=(Concepts) i.next();
            boolean flag1=true;
            for(Iterator j=this.onto.hierarcy.iterator();j.hasNext();){
                Hierarchies htemp=(Hierarchies) j.next();
                if(ctemp.name.equals(htemp.subclass_name.name)){
                    flag1=false;
                }
            }
            if(flag1==true && ctemp!=null){
                result.add(new Node(ctemp,this.root));
            }
        }
        return result;
    }


    public void buildgrammartree(Ontology onto, String name_for_grammar, String path_to_ontology) throws IOException {
        this.onto=onto;
        this.root.subNodes=Connected2Root();
        ArrayList<Node> newlist=new ArrayList();
        for(Iterator i=this.root.subNodes.iterator();i.hasNext();){
            Node n=(Node)i.next();
            newlist.add(explore(n));
        }
        this.root.subNodes=newlist;
        CodeGenerator code=new CodeGenerator(this.root,name_for_grammar,this.onto);
        code.generateMainclass(name_for_grammar);
    }
}
