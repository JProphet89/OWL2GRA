package OWL2DSL.GramConstructor;


import OWL2DSL.*;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import static org.apache.commons.io.FileUtils.readFileToString;

/**
 * Created by jprophet89 on 26/02/14.
 */
public class CodeGenerator {
    public Ontology onto;
    public String namepack;

    public String normalize(String nome){
        String temp=nome;
        temp=temp.replaceAll("[áàãâä]","a");
        temp=temp.replaceAll("[éèêë]","e");
        temp=temp.replaceAll("[íìïî]","i");
        temp=temp.replaceAll("[óòöôõ]","o");
        temp=temp.replaceAll("[úùüû]","u");
        return temp;
    }

    public CodeGenerator(Node root, String name,Ontology o){
        this.onto=o;
        if(root.subNodes.size()==0){
            return;
        }
        String classfile="";
        String aux="",aux2="",filename;
        String examplefile="//before you run this template on the OWL2GRA remove all comments that\n//are generated to help you understand the way that this work\n\nThing[\n";
        String aux1="";
        name=name.replaceAll(".onto","");
        name=name.replaceAll(".owl","");
        namepack=name;

        //create the package
        File f=new File(namepack);
        f.mkdir();
        create_class_helpers();

        //define the master class
        filename=""+name.charAt(0);
        filename=filename.toUpperCase();
        for(int i=1;i<name.length();i++){
            filename+=name.charAt(i);
        }
        classfile="package "+namepack+";\nimport java.util.ArrayList;\n\n";
        classfile+="public class Thing{\n";

        //define the cleangrammar

        String cleangrammar="grammar clean_"+name+";";

        //define the grammar start
        String grammar="grammar "+name+";\n\n@option{ \n" +
                "\tlanguage=Java;\n" +
                "}\n@header{\nimport java.util.ArrayList;import "+namepack+".*;\n}\n@parser::members{\n public ArrayList<Instances> instances=new ArrayList(); \n}";

        //Initialize the Thing Production
        cleangrammar+="\nthing:";
        grammar+="\nthing returns[Thing struct,ArrayList<Instances> insta]\n@init{\n$thing.struct=new Thing();\ninstances=new ArrayList();\n$thing.insta=new ArrayList();\n}\n@after{$thing.insta.addAll(instances);}:\n";
        aux1=normalize(root.subNodes.get(0).concep.name.toLowerCase());
        aux+="p00="+normalize(root.subNodes.get(0).concep.name.toLowerCase())+" {$thing.struct."+normalize(root.subNodes.get(0).concep.name.toLowerCase())+"s.add($p00.struct);}";
        classfile+="\tpublic ArrayList<"+normalize(root.subNodes.get(0).concep.name)+"> "+normalize(root.subNodes.get(0).concep.name.toLowerCase())+"s=new ArrayList();\n";
        for(int i=1;i<root.subNodes.size();i++){
            aux1+="|"+normalize(root.subNodes.get(i).concep.name.toLowerCase());
            aux+="\n\t| p0"+i+"="+normalize(root.subNodes.get(i).concep.name.toLowerCase())+" {$thing.struct."+normalize(root.subNodes.get(i).concep.name.toLowerCase())+"s.add($p0"+i+".struct);}";
            classfile+="\tpublic ArrayList<"+normalize(root.subNodes.get(i).concep.name)+"> "+normalize(root.subNodes.get(i).concep.name.toLowerCase())+"s=new ArrayList();\n";
        }
        cleangrammar+="\n\t'Thing['("+normalize(aux1)+")+ ']'\n;\n\n";
        grammar+="\n\t'Thing['("+normalize(aux)+")+ ']'\n;\n\n";
        classfile+="}";




        //Explore hierarchy nodes
        for(Iterator i=root.subNodes.iterator();i.hasNext();){
            Node temp=(Node) i.next();
            ArrayList<String> ptemp=subproduction(temp);
            grammar+=ptemp.get(0);
            cleangrammar+=ptemp.get(1);
            examplefile+=ptemp.get(2);
        }

        examplefile+="]";
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

        cleangrammar+="\n" +
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
            grammar = new String(grammar.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            cleangrammar = new String(cleangrammar.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            examplefile = new String(examplefile.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        try {
            try {
                out = new PrintStream(new FileOutputStream("clean_"+name+".g"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.print(cleangrammar);
        }
        finally {
            if (out != null) out.close();
        }
        try {
            try {
                out = new PrintStream(new FileOutputStream("inputexample_"+name+".ddesc"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.print(examplefile);
        }
        finally {
            if (out != null) out.close();
        }

        PrintStream out2 = null;
        try {
            try {
                out2 = new PrintStream(new FileOutputStream(namepack+"/Thing.java"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out2.print(classfile);
        }
        finally {
            if (out2 != null) out2.close();
        }
    }

    private void create_class_helpers() {
        String instance="",object="",data="";
        /**
         * Instance saving class
         */
        instance+="package "+namepack+";\nimport java.util.ArrayList;\n" +
                "public class Instances {\n" +
                "    public String name;\n" +
                "   public String type;" +
                "    public ArrayList<Dataproperties> dataproperties;\n" +
                "    public ArrayList<Objectproperties> objectproperties;\n" +
                "    public Instances(){\n" +
                "        name=\"\";\n" +
                "        dataproperties=new ArrayList();\n" +
                "        objectproperties=new ArrayList();\n" +
                "    }\n" +
                "}";
        PrintStream out = null;
        try {
            try {
                out = new PrintStream(new FileOutputStream(namepack+"/Instances.java"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.print(instance);
        }
        finally {
            if (out != null) out.close();
        }
        /**
         * Object property class
         */
        object+="package "+namepack+";\npublic class Objectproperties {\n" +
                "    public String object;\n" +
                "    public String property;\n" +
                "public Objectproperties(String d, String p){object=d;property=p;}\n" +
                "}\n";
        try {
            try {
                out = new PrintStream(new FileOutputStream(namepack+"/Objectproperties.java"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.print(object);
        }
        finally {
            if (out != null) out.close();
        }
        /**
         * Data Property class
         */
        data+="package "+namepack+";\npublic class Dataproperties {\n" +
                "    public String data;\n" +
                "    public String property;\n" +
                "\n" +
                "public Dataproperties(String d, String p){data=d;property=p;}\n" +
                "public Dataproperties(int d, String p){data=\"\"+d;property=p;}\n" +
                "public Dataproperties(float d, String p){data=\"\"+d;property=p;}\n" +
                "}\n";
        try {
            try {
                out = new PrintStream(new FileOutputStream(namepack+"/Dataproperties.java"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.print(data);
        }
        finally {
            if (out != null) out.close();
        }
    }


    private ArrayList<String> subproduction(Node root) {
        String classfile="",filetempname,production="",sub_attributes="",production1="",examplefile="";
        String sub_attributes1="";
        filetempname=""+root.concep.name.charAt(0);
        filetempname=filetempname.toUpperCase();
        for(int i=1;i<root.concep.name.length();i++){
            filetempname+=root.concep.name.charAt(i);
        }

        //Initialize the production
        production1="\n"+normalize(root.concep.name.toLowerCase())+":";
        production="\n"+normalize(root.concep.name.toLowerCase())+" returns["+normalize(filetempname)+" struct]\n" +
                "@init{\t$"+normalize(root.concep.name.toLowerCase())+".struct=new "+normalize(filetempname)+"();\nInstances i=new Instances();\ni.type=\""+root.concep.name+"\";\n}\n@after{\ninstances.add(i);\n}:\n";

        //initialize java class
        classfile="package "+namepack+";\nimport java.util.ArrayList;\n";
        classfile+="public class "+normalize(filetempname)+"{\n";
        classfile+="\tpublic String "+normalize(root.concep.name.toLowerCase())+"ID=null;\n";

        production1+="\t'"+filetempname+"{'"+normalize(root.concep.name.toLowerCase())+"ID ";
        production+="\t'"+filetempname+"{'"+normalize(root.concep.name.toLowerCase())+"ID{$"+normalize(root.concep.name.toLowerCase())+".struct."+normalize(root.concep.name.toLowerCase())+"ID=$"+normalize(root.concep.name.toLowerCase())+"ID.value;i.name=$"+normalize(root.concep.name.toLowerCase())+"ID.value.replaceAll(\" \",\"_\");}";
        examplefile+=filetempname+"{\t \"Intance of "+normalize(root.concep.name.toLowerCase())+" ID\" ";
        for(Iterator<Attributes> i=root.concep.atribute.iterator();i.hasNext();){
            Attributes att= i.next();
            if(att.type.substring(att.type.indexOf(":")+1).equals("string")){
                classfile+="\n\tpublic String "+normalize(att.name.toLowerCase())+"=\"\";";
                production1+="('"+normalize(att.name.toLowerCase())+"' "+normalize(att.name.toLowerCase())+")?";
                examplefile+="\t"+normalize(att.name.toLowerCase())+" \"some string value\"";
                production+="('"+normalize(att.name.toLowerCase())+"' "+normalize(att.name.toLowerCase())+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+normalize(att.name.toLowerCase())+"=$"+normalize(att.name.toLowerCase())+".value;i.dataproperties.add(new Dataproperties($"+normalize(att.name.toLowerCase())+".value,\""+normalize(att.name)+"\"));})?";
                sub_attributes+=normalize(att.name.toLowerCase())+" returns[String value]:\n\t STRING{$"+normalize(att.name.toLowerCase())+".value=$STRING.text;}\n;\n";
                sub_attributes1+=normalize(att.name.toLowerCase())+":\n\t STRING\n;\n";
            }
            if(att.type.substring(att.type.indexOf(":")+1).equals("int")){
                classfile+="\n\tpublic int "+normalize(att.name)+"=0;";
                production1+="('"+normalize(att.name.toLowerCase())+"' "+normalize(att.name.toLowerCase())+")?";
                examplefile+="\t"+normalize(att.name.toLowerCase())+" some_int_value )?";
                production+="('"+normalize(att.name.toLowerCase())+"' "+normalize(att.name.toLowerCase())+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+normalize(att.name.toLowerCase())+"=$"+normalize(att.name.toLowerCase())+".value;i.dataproperties.add(new Dataproperties($"+normalize(att.name.toLowerCase())+".value,\""+normalize(att.name)+"\"));})?";
                sub_attributes+=normalize(att.name.toLowerCase())+" returns[int value]:\n\tINT{$"+normalize(att.name.toLowerCase())+".value=$INT.int;\n};\n";
                sub_attributes1+=normalize(att.name.toLowerCase())+":\n\t INT\n;\n";
            }
            if(att.type.substring(att.type.indexOf(":")+1).equals("float")){
                classfile+="\n\tpublic float "+normalize(att.name)+"=0;";
                production1+="('"+normalize(att.name.toLowerCase())+"' "+normalize(att.name.toLowerCase())+")?";
                examplefile+="\t"+normalize(att.name.toLowerCase())+" some_int_value";
                production+="('"+normalize(att.name.toLowerCase())+"' "+normalize(att.name.toLowerCase())+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+normalize(att.name.toLowerCase())+"=$"+normalize(att.name.toLowerCase())+".value;i.dataproperties.add(new Dataproperties($"+normalize(att.name.toLowerCase())+".value,\""+normalize(att.name)+"\"));})?";
                sub_attributes+=normalize(att.name.toLowerCase())+" returns[float value]:\n\tFLOAT{$"+normalize(att.name.toLowerCase())+".value=Float.parseFloat($FLOAT.text);}\n;\n";
                sub_attributes1+=normalize(att.name.toLowerCase())+":\n\t FLOAT\n;\n";
            }
        }

        //Initialize HierarchySubnodes
        if(root.subNodes.size()>0){
            int j=0;
            String subhirarchi1="";
            String subhirarchi3="";
            classfile+="\n\tpublic ArrayList<"+normalize(root.subNodes.get(0).concep.name)+">"+normalize(root.subNodes.get(0).concep.name.toLowerCase())+"=new ArrayList();";
            subhirarchi3+=normalize(root.subNodes.get(0).concep.name.toLowerCase());
            subhirarchi1+="hi1_"+j+"="+normalize(root.subNodes.get(0).concep.name.toLowerCase())+" {$"+normalize(root.concep.name.toLowerCase())+".struct."+normalize(root.subNodes.get(0).concep.name.toLowerCase())+".add($hi1_"+j+".struct);}";
            j++;
            for(int i=1;i<root.subNodes.size();i++){
                classfile+="\n\tpublic ArrayList<"+normalize(root.subNodes.get(i).concep.name)+">"+normalize(root.subNodes.get(i).concep.name.toLowerCase())+"=new ArrayList();";
                subhirarchi1+="\n\t| hi1_"+j+"="+normalize(root.subNodes.get(i).concep.name.toLowerCase())+" {$"+normalize(root.concep.name.toLowerCase())+".struct."+normalize(root.subNodes.get(i).concep.name.toLowerCase())+".add($hi1_"+j+".struct);}";
                subhirarchi3+="\n\t| "+normalize(root.subNodes.get(i).concep.name.toLowerCase());
                j++;
            }
            production1+="\n\t(',' '[' ("+subhirarchi3+")+\n ']' )?";
            production+="\n\t(',' '[' ("+subhirarchi1+")+\n ']' )?";
        }

        /**
         * Objectproperty
         **/
        //Initialize the other triples connections
        ArrayList<TripleRange> triples=new ArrayList();
        triples.addAll(root.concep.triples);
        String subexample="";
        if(triples.size()>0){
            String subproduction="",subproduction1="";
            ArrayList<String> visited=new ArrayList();
            System.out.println("\n\nCODEGEN::TRIPES from "+root.concep.name);
            for(Iterator<TripleRange> i=triples.iterator();i.hasNext();){
                TripleRange temp=i.next();
                String relation,range,relation_alt;
                if(temp.relation.indexOf("#")>=0){
                    relation=normalize(temp.relation.toLowerCase().substring(temp.relation.indexOf("#") + 1));
                    relation_alt=normalize(temp.relation.substring(temp.relation.indexOf("#") + 1));
                }else{
                    relation=normalize(temp.relation.toLowerCase());
                    relation_alt=normalize(temp.relation);
                }
                if(triples.get(0).range.indexOf("#")>=0){
                    range=normalize(temp.range.toLowerCase().substring(temp.relation.indexOf("#") + 1));
                }else{
                    range=normalize(temp.range.toLowerCase());
                }
                if(!visited.contains(range)){
                    visited.add(range);
                    System.out.println("\tTriple::"+temp.range+"::MAX>"+temp.max+"::MIN>"+temp.min);
                    if(temp.max>0 && temp.min>0){
                        if(temp.max==1 && temp.min==1){
                            classfile+="\n\tpublic ArrayList<String> "+range+"_"+relation+"=new ArrayList();";
                            subproduction+="("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+range+"_"+relation+".add($"+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+".value);i.objectproperties.add(new Objectproperties($"+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+".value.replaceAll(\" \",\"_\"),\""+relation_alt+"\"));})";
                            subproduction1+="("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+")";
                            sub_attributes+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+" returns[String value]:\n\t'{' '"+relation+"' '"+range+"' "+range+"ID{$"+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+".value=$"+range+"ID.value;} '}';\n";
                            sub_attributes1+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+":\n\t'{' '"+relation+"' '"+range+"' "+range+"ID'}';\n";
                            subexample+="\t{ "+relation+" "+range+" \""+range+"ID_reference\" }  //  only one possible";
                        }
                        if(temp.max==2 && temp.min==1){
                            classfile+="\n\tpublic ArrayList<String> "+range+"_"+relation+"=new ArrayList();";
                            subproduction+="("+range+"1="+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+range+"_"+relation+".add($"+range+"1.value);i.objectproperties.add(new Objectproperties($"+range+"1.value.replaceAll(\" \",\"_\"),\""+relation_alt+"\"));})" +
                                    "("+range+"2="+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+range+"_"+relation+".add($"+range+"2.value);i.objectproperties.add(new Objectproperties($"+range+"2.value.replaceAll(\" \",\"_\"),\""+relation_alt+"\"));})?";
                            subproduction1+="("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+") ("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+")?";
                            sub_attributes+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+" returns[String value]:\n\t'{' '"+relation+"' '"+range+"' "+range+"ID{$"+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+".value=$"+range+"ID.value;} '}';\n";
                            sub_attributes1+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+":\n\t'{' '"+relation+"' '"+range+"' "+range+"ID'}';\n";
                            subexample+="\t{ "+relation+" "+range+" \""+range+"ID_reference\" }  //  at maximum 2, at least 1 required";
                        }
                        if(temp.max==2 && temp.min==2){
                            classfile+="\n\tpublic ArrayList<String> "+range+"_"+relation+"=new ArrayLis();";
                            subproduction+="("+range+"1="+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+range+"_"+relation+".add($"+range+"1.value);i.objectproperties.add(new Objectproperties($"+range+"1.value.replaceAll(\" \",\"_\"),\""+relation_alt+"\"));} " +
                                    " "+range+"2="+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+range+"_"+relation+".add($"+range+"2.value);i.objectproperties.add(new Objectproperties($"+range+"2.value.replaceAll(\" \",\"_\"),\""+relation_alt+"\"));})";
                            subproduction1+="("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+") ("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+")";
                            sub_attributes+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+" returns[String value]:\n\t'{' '"+relation+"' '"+range+"' "+range+"ID{$"+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+".value=$"+range+"ID.value;} '}';\n";
                            sub_attributes1+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+":\n\t'{' '"+relation+"' '"+range+"' "+range+"ID'}';\n";
                            subexample+="\t{ "+relation+" "+range+" \""+range+"ID_reference\" }  //  two required";
                        }
                    }else{
                        if(temp.max>0 && temp.min<0){
                            if(temp.max==1){
                                classfile+="\n\tpublic ArrayList<String> "+range+"_"+relation+"=new ArrayList();";
                                subproduction+="("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+range+"_"+relation+".add($"+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+".value);i.objectproperties.add(new Objectproperties($"+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+".value.replaceAll(\" \",\"_\"),\""+relation_alt+"\"));})?";
                                subproduction1+="("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+")?";
                                sub_attributes+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+" returns[String value]:\n\t'{' '"+relation+"' '"+range+"' "+range+"ID{$"+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+".value=$"+range+"ID.value;} '}';\n";
                                sub_attributes1+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+":\n\t'{' '"+relation+"' '"+range+"' "+range+"ID'}';\n";
                                subexample+="\t{ "+relation+" "+range+" \""+range+"ID_reference\" }  // only one possible";
                            }
                            if(temp.max==2){
                                classfile+="\n\tpublic ArrayList<String> "+range+"_"+relation+"=new ArrayList();";
                                subproduction+="("+range+"1="+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+range+"_"+relation+".add($"+range+"1.value);i.objectproperties.add(new Objectproperties($"+range+"1.value.replaceAll(\" \",\"_\"),\""+relation_alt+"\"));}" +
                                        " "+range+"2="+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+range+"_"+relation+".add($"+range+"2.value);i.objectproperties.add(new Objectproperties($"+range+"2.value.replaceAll(\" \",\"_\"),\""+relation_alt+"\"));})?";
                                subproduction1+="("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+")? ("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+")?";
                                sub_attributes+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+" returns[String value]:\n\t'{' '"+relation+"' '"+range+"' "+range+"ID{$"+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+".value=$"+range+"ID.value;} '}';\n";
                                sub_attributes1+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+":\n\t'{' '"+relation+"' '"+range+"' "+range+"ID'}';\n";
                                subexample+="\t{ "+relation+" "+range+" \""+range+"ID_reference\" }  //  at maximum two possible";
                            }
                        }else{
                            if(temp.min>0 && temp.max<0){
                                if(temp.min==1){
                                    classfile+="\n\tpublic ArrayList<String> "+range+"_"+relation+"=new ArrayList();";
                                    subproduction+="("+range+"2="+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+range+"_"+relation+".add($"+range+"2.value);i.objectproperties.add(new Objectproperties($"+range+"2.value.replaceAll(\" \",\"_\"),\""+relation_alt+"\"));})+";
                                    subproduction1+="("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+")+";
                                    sub_attributes+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+" returns[String value]:\n\t'{' '"+relation+"' '"+range+"' "+range+"ID{$"+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+".value=$"+range+"ID.value;} '}';\n";
                                    sub_attributes1+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+":\n\t'{' '"+relation+"' '"+range+"' "+range+"ID'}';\n";
                                    subexample+="\t{ "+relation+" "+range+" \""+range+"ID_reference\" }  //  at least one is required but more possible";
                                }
                                if(temp.min==2){
                                    classfile+="\n\tpublic ArrayList<String> "+range+"_"+relation+"=new ArrayList();";
                                    subproduction+="("+range+"1="+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+range+"_"+relation+".add($"+range+"1.value);i.objectproperties.add(new Objectproperties($"+range+"1.value.replaceAll(\" \",\"_\"),\""+relation_alt+"\"));})" +
                                            "("+range+"2="+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+range+"_"+relation+".add($"+range+"2.value);i.objectproperties.add(new Objectproperties($"+range+"2.value.replaceAll(\" \",\"_\"),\""+relation_alt+"\"));})+";
                                    subproduction1+="("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+") ("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+")+";
                                    sub_attributes+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+" returns[String value]:\n\t'{' '"+relation+"' '"+range+"' "+range+"ID{$"+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+".value=$"+range+"ID.value;} '}';\n";
                                    sub_attributes1+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+":\n\t'{' '"+relation+"' '"+range+"' "+range+"ID'}';\n";
                                    subexample+="\t{ "+relation+" "+range+" \""+range+"ID_reference\" }  //  at least two is required but more possible";
                                }
                            }else{
                                classfile+="\n\tpublic ArrayList<String> "+range+"_"+relation+"=new ArrayList();";
                                subproduction+="("+range+"1="+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+"{$"+normalize(root.concep.name.toLowerCase())+".struct."+range+"_"+relation+".add($"+range+"1.value);i.objectproperties.add(new Objectproperties($"+range+"1.value.replaceAll(\" \",\"_\"),\""+relation_alt+"\"));})*";
                                subproduction1+="("+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+")*";
                                sub_attributes+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+" returns[String value]:\n\t'{' '"+relation+"' '"+range+"' "+range+"ID{$"+normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+".value=$"+range+"ID.value;} '}';\n";
                                sub_attributes1+=normalize(root.concep.name.toLowerCase())+"_"+relation+"_"+range+":\n\t'{' '"+relation+"' '"+range+"' "+range+"ID'}';\n";
                                subexample+="\t{ "+relation+" "+range+" \""+range+"ID_reference\" } // many possible but need to be sequential";
                            }
                        }
                    }
                }
            }
            production+="\n\t(',' '[' "+subproduction+" ']' )?";
            production1+="\n\t(',' '[' "+subproduction1+" ']' )?";
        }

        production1+="'}'\n;\n\n";
        production+="'}'\n;\n\n";
        classfile+="\n}";


        production1+=normalize(root.concep.name.toLowerCase())+"ID :\n\t STRING\n;\n";
        production+=normalize(root.concep.name.toLowerCase())+"ID returns[String value]:\n\t STRING{$"+normalize(root.concep.name.toLowerCase())+"ID.value=$STRING.text;}\n;\n";
        production1+=sub_attributes1;
        production+=sub_attributes;

        PrintStream out = null;
        try {
            try {
                out = new PrintStream(new FileOutputStream(namepack+"/"+normalize(filetempname)+".java"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.print(classfile);
        }
        finally {
            if (out != null) out.close();
        }

        ArrayList<String> result=new ArrayList();
        String examplesubpro="";
        //Explore Hierarchy nodes
        for(Iterator j=root.subNodes.iterator();j.hasNext();){
            Node ptemp=(Node)j.next();
            ArrayList<String> temp=subproduction(ptemp);
            production+=temp.get(0);
            production1+=temp.get(1);
            examplesubpro+=temp.get(2);
        }
        if(!examplesubpro.equals("")){
            examplefile+="\n,[//this instances bellow are not necessary\n"+examplesubpro+"]";
        }
        if(!subexample.equals("")){
            examplefile+="\n,[ "+subexample+" ]\t";
        }
        examplefile+="\n}\n";

        //return the production

        result.add(production);
        result.add(production1);
        result.add(examplefile);
        return result;
    }

    private ArrayList<TripleRange> clean_extras(ArrayList<TripleRange> triples) {
        ArrayList<TripleRange> result=new ArrayList();
        boolean flag;
        for(Iterator i=triples.iterator();i.hasNext();){
            TripleRange temp=(TripleRange)i.next();
            flag=true;
            for(Iterator j=result.iterator();j.hasNext();){
                TripleRange ptemp=(TripleRange)i.next();
                if(temp.relation==ptemp.relation){
                    flag=false;
                }
            }
            if(flag==true){
                result.add(temp);
            }
        }
        return result;
    }


    public void generateMainclass(String name) throws IOException {
        //Main class for the DDesc2OWL
        /**
         *
         * Imports
         *
         * */
        String mainclasscode="" +
                "import "+name+".*;\n" +
                "import org.antlr.runtime.ANTLRFileStream;\n" +
                "import org.antlr.runtime.CommonTokenStream;\n" +
                "import org.antlr.runtime.RecognitionException;\n" +
                "import java.io.*;\n" +
                "import java.util.Iterator;\n" +
                "import java.util.ArrayList;\n" +
                "import org.json.simple.JSONArray;\n" +
                "import org.json.simple.JSONObject;\n";
        /**
         *
         * Main
         *
         * */
        mainclasscode+="" +
                "public class Main {\n" +
                "\tpublic static void main(String args[]) throws IOException {\n" +
                "   \t\tJSONObject metajson = new JSONObject();\n" +
                "   \t\tJSONArray instancias = new JSONArray();\n" +
                "      \t"+name+"Lexer lex = new "+name+"Lexer(new ANTLRFileStream(args[0], \"UTF8\"));\n" +
                "       \tCommonTokenStream tokens = new CommonTokenStream(lex);\n" +
                "       \t"+name+"Parser g = new "+name+"Parser(tokens,null);\n" +
                "      \ttry {\n" +
                "        \tfor(Iterator i=g.thing().insta.iterator();i.hasNext();){\n" +
                "        \t\tJSONObject obj = new JSONObject();\n" +
                "               \tInstances temp=(Instances)i.next();\n" +
                "               \tobj.put(\"name\",temp.name.replaceAll(\"\\\"\",\"\"));\n" +
                "               \tobj.put(\"type\",temp.type.replaceAll(\"\\\"\",\"\"));\n" +
                "               \tif(!temp.dataproperties.isEmpty()){\n" +
                "               \t\tJSONArray data = new JSONArray();\n" +
                "               \t\tfor(Iterator j=temp.dataproperties.iterator();j.hasNext();){\n" +
                "                \t\tDataproperties dtemp=(Dataproperties)j.next();\n" +
                "                \t\tJSONObject dataprop = new JSONObject();\n" +
                "                \t\tdataprop.put(\"prop\",dtemp.property);\n" +
                "                \t\tdataprop.put(\"data\",dtemp.data.replaceAll(\"\\\"\",\"\"));\n" +
                "                \t\tdata.add(dataprop);\n" +
                "               \t\t}\n" +
                "               \t\tobj.put(\"dataprop\",data);\n" +
                "               \t}\n" +
                "               \tif(!temp.objectproperties.isEmpty()){\n" +
                "               \t\tJSONArray objetp = new JSONArray();\n" +
                "               \t\tfor(Iterator j=temp.objectproperties.iterator();j.hasNext();){\n" +
                "                \t\tObjectproperties o=(Objectproperties)j.next();\n" +
                "                \t\tJSONObject objprop = new JSONObject();\n" +
                "                \t\tobjprop.put(\"prop\",o.property);\n" +
                "                \t\tobjprop.put(\"data\",o.object.replaceAll(\"\\\"\",\"\"));\n" +
                "                \t\tobjetp.add(objprop);\n" +
                "               \t\t}\n" +
                "               \t\tobj.put(\"objprop\",objetp);\n" +
                "               \t}\n" +
                "               \tinstancias.add(obj);\n" +
                "           \t}\n" +
                "           \tmetajson.put(\"instancias\",instancias);\n" +
                "       \t} catch (RecognitionException e) {\n" +
                "           e.printStackTrace();\n" +
                "       \t}\n" +
                "       \ttry {\n" +
                "       \t\tFileWriter file = new FileWriter(\"metadata.json\");\n" +
                "\t\t\tfile.write(metajson.toJSONString());\n" +
                "\t\t\tfile.flush();\n" +
                "\t\t\tfile.close();\n" +
                "\t\t} catch (IOException e) {\n" +
                "\t\t\te.printStackTrace();\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";


        PrintStream out = null;
        try {
            try {
                out = new PrintStream(new FileOutputStream("Main.java"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            out.print(mainclasscode);
        }
        finally {
            if (out != null) out.close();
        }
    }
}
