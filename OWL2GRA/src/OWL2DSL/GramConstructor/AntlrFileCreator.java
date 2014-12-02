package OWL2DSL.GramConstructor;

import OWL2DSL.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;


/**
 * Created by jprophet89 on 21/02/14.
 */
public class AntlrFileCreator {

    public void GramarAntlrgenerator(Node root , String name) {
        if(root.subNodes.size()==0){
            return;
        }
        name=name.replaceAll(".onto","");
        name=name.replaceAll(".owl","");
        String aux="";
        String grammar="grammar "+name+";\n\n@option{ \n" +
                "\tlanguage=Java;\n" +
                "}\n";

        //Initialize the Thing Production
        grammar+="\nthing:\n";
        aux=root.subNodes.get(0).concep.name.toLowerCase();
        for(int i=1;i<root.subNodes.size();i++){
            aux+="|"+root.subNodes.get(i).concep.name.toLowerCase();
        }
        grammar+="("+aux+")("+aux+")*\n;\n";

        //Get other productions from hierarchy
        for(Iterator i=root.subNodes.iterator();i.hasNext();){
            Node temp=(Node) i.next();
            aux=temp.concep.name.toLowerCase()+" returns[String idvalue";
            if(temp.concep.atribute.size()>0){
                for(int j=0;j<temp.concep.atribute.size();j++){
                    if(temp.concep.atribute.get(j).type.equals("string")){aux+=", String "+temp.concep.atribute.get(j).name.toLowerCase();}
                    if(temp.concep.atribute.get(j).type.equals("float")){aux+=", float "+temp.concep.atribute.get(j).name.toLowerCase();}
                    if(temp.concep.atribute.get(j).type.equals("int")){aux+=", int "+temp.concep.atribute.get(j).name.toLowerCase();}
                }
            }
            aux+="]:\n";
            aux+="\t"+temp.concep.name.toLowerCase()+"ID{$"+temp.concep.name.toLowerCase()+".idvalue=$"+temp.concep.name.toLowerCase()+"ID.value;}";
            /*
            *
            * Explore attributes
            *
            * */
            if(temp.concep.atribute.size()>0){
                for(int j=0;j<temp.concep.atribute.size();j++){
                    aux+=" ("+temp.concep.atribute.get(j).name.toLowerCase()+"{$"+temp.concep.name.toLowerCase()+"."+temp.concep.atribute.get(j).name.toLowerCase()+"=$"+temp.concep.atribute.get(j).name.toLowerCase()+".value;})? ";
                }
            }
            /*
            *
            * list all subnodes
            *
            * */

            for(Iterator k=temp.subNodes.iterator();k.hasNext();){
                Node ptemp=(Node) k.next();
                aux+=" ("+ptemp.concep.name.toLowerCase()+")? ";
            }

            //End of production
            aux+="\n;\n\n";
            /*
            * Mapping all the Concepts attributes from the production
            * */
            aux+=temp.concep.name.toLowerCase()+"ID returns[String value]:\n\tSTRING{$"+temp.concep.name.toLowerCase()+"ID.value=$STRING.text();}\n;\n";
            if(temp.concep.atribute.size()>0){
                for(int j=0;j<temp.concep.atribute.size();j++){
                    aux+=temp.concep.atribute.get(j).name.toLowerCase()+" returns [";
                    if(temp.concep.atribute.get(j).type.equals("string")){
                        aux+="String value]:\n\tSTRING{$"+temp.concep.atribute.get(j).name+".value=$STRING.text();}\n;\n";
                    }
                    if(temp.concep.atribute.get(j).type.equals("float")){
                        aux+="float value]:\n\tFLOAT{$"+temp.concep.atribute.get(j).name+".value=$FLOAT.value();}\n;\n";
                    }
                    if(temp.concep.atribute.get(j).type.equals("int")){
                        aux+="int value]:\n\tINT{$"+temp.concep.atribute.get(j).name+".value=$INT.value();}\n;\n";
                    }
                }
            }


            grammar+="\n"+aux;


            /*
            *
            * Explore sub productions
            *
            * */
            for(Iterator k=temp.subNodes.iterator();k.hasNext();){
                Node ptemp=(Node) k.next();
                aux="";
                aux+=subproduction(ptemp);
                grammar+=aux;
            }


        }
        /*
        *
        * Grammar Types
        *
        * */

        grammar+="\n" +
                "\n" +
                "ID  :\t('a'..'z'|'A'..'Z'|'_'|'-') ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'-')*\n" +
                "    ;\n" +
                "\n" +
                "INT :\t'0'..'9'+\n" +
                "    ;\n" +
                "\n" +
                "COMMENT\n" +
                "    :   '//' ~('\\n'|'\\r')* '\\r'? '\\n' {$channel=HIDDEN;}\n" +
                "    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}\n" +
                "    ;\n" +
                "\n" +
                "WS  :   ( ' '\n" +
                "        | '\\t'\n" +
                "        | '\\r'\n" +
                "        | '\\n'\n" +
                "        ) {$channel=HIDDEN;}\n" +
                "    ;\n" +
                "\nFLOAT\n" +
                "    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT?\n" +
                "    |   '.' ('0'..'9')+ EXPONENT?\n" +
                "    |   ('0'..'9')+ EXPONENT\n" +
                "    ;\n" +
                "\n" +
                "fragment\n" +
                "EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;\n" +
                "STRING\n" +
                "    :  '\"' ( ESC_SEQ | ~('\\\\'|'\"') )* '\"'\n" +
                "    ;\n" +
                "\n" +
                "CHAR:  '\\'' ( ESC_SEQ | ~('\\''|'\\\\') ) '\\''\n" +
                "    ;\n" +
                "\n" +
                "fragment\n" +
                "HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;\n" +
                "\n" +
                "fragment\n" +
                "ESC_SEQ\n" +
                "    :   '\\\\' ('b'|'t'|'n'|'f'|'r'|'\\\"'|'\\''|'\\\\')\n" +
                "    |   UNICODE_ESC\n" +
                "    |   OCTAL_ESC\n" +
                "    ;\n" +
                "\n" +
                "fragment\n" +
                "OCTAL_ESC\n" +
                "    :   '\\\\' ('0'..'3') ('0'..'7') ('0'..'7')\n" +
                "    |   '\\\\' ('0'..'7') ('0'..'7')\n" +
                "    |   '\\\\' ('0'..'7')\n" +
                "    ;\n" +
                "\n" +
                "fragment\n" +
                "UNICODE_ESC\n" +
                "    :   '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT\n" +
                "    ;";


        PrintStream out = null;
        try {
            try {
                out = new PrintStream(new FileOutputStream(name+".g"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.print(grammar);
        }
        finally {
            if (out != null) out.close();
        }

    }

    private String subproduction(Node root) {
        String result=root.concep.name.toLowerCase();
        result+=" returns[String idvalue";
        if(root.concep.atribute.size()>0){
            for(int j=0;j<root.concep.atribute.size();j++){
                if(root.concep.atribute.get(j).type.equals("string")){result+=",String "+root.concep.atribute.get(j).name.toLowerCase();}
                if(root.concep.atribute.get(j).type.equals("float")){result+=",float "+root.concep.atribute.get(j).name.toLowerCase();}
                if(root.concep.atribute.get(j).type.equals("int")){result+=",int "+root.concep.atribute.get(j).name.toLowerCase();}
            }

        }
        result+="]:\n";
        result+="\t"+root.concep.name.toLowerCase()+"ID{$"+root.concep.name.toLowerCase()+".idvalue=$"+root.concep.name.toLowerCase()+"ID.value;}";
            /*
            *
            * Explore attributes
            *
            * */
        if(root.concep.atribute.size()>0){
            for(int j=0;j<root.concep.atribute.size();j++){
                result+=" ("+root.concep.atribute.get(j).name.toLowerCase()+"{$"+root.concep.name.toLowerCase()+"."+root.concep.atribute.get(j).name.toLowerCase()+"=$"+root.concep.atribute.get(j).name.toLowerCase()+".value;})? ";
            }
        }
            /*
            *
            * list all subnodes
            *
            * */

        for(Iterator k=root.subNodes.iterator();k.hasNext();){
            Node ptemp=(Node) k.next();
            result+=" ("+ptemp.concep.name.toLowerCase()+")? ";
        }

        //End of production
        result+="\n;\n\n";
            /*
            * Mapping all the Concepts attributes from the production
            * */
        result+=root.concep.name.toLowerCase()+"ID returns[String value]:\n\tSTRING{$"+root.concep.name.toLowerCase()+"ID.value=$STRING.text();}\n;\n";
        if(root.concep.atribute.size()>0){
            for(int j=0;j<root.concep.atribute.size();j++){
                result+=root.concep.atribute.get(j).name.toLowerCase()+" returns [";
                if(root.concep.atribute.get(j).type.equals("string")){
                    result+="String value]:\n\tSTRING{$"+root.concep.atribute.get(j).name+".value=$STRING.text();}\n;\n";
                }
                if(root.concep.atribute.get(j).type.equals("float")){
                    result+="float value]:\n\tFLOAT{$"+root.concep.atribute.get(j).name+".value=$FLOAT.value();}\n;\n";
                }
                if(root.concep.atribute.get(j).type.equals("int")){
                    result+="int value]:\n\tINT{$"+root.concep.atribute.get(j).name+".value=$INT.value();}\n;\n";
                }
            }
        }

            /*
            *
            * Explore sub productions
            *
            * */

        result+="\n";

        for(Iterator k=root.subNodes.iterator();k.hasNext();){
            String aux="";
            Node ptemp=(Node) k.next();
            aux=subproduction(ptemp);
            result+=aux;
        }


        return result;
    }

}
