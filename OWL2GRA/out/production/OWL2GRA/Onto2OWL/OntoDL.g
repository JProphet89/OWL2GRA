grammar OntoDL;

@option{ 
	language=Java;
}

@header{
	package Onto2OWL;
	import java.util.ArrayList;
	import Concepts;
	import Hierarchies;
	import Relations;
	import Attributes;
	import Triples;
	import Ontologies;
}

ontology returns[Ontologies ontology]
@init{
	$ontology.ontology=new Ontologies();
}
	:
	'Ontology{'
		//Concepts from a ontology and it's attributes
		concepts
		//Hierarchies between Concepts;
		hierarchies
		//Relations specification for links
		relations
		//Links between Concepts;
		links[$concepts.list_concepts,$relations.list_relations]{
			int cnum=$concepts.list_concepts.size();
			if(cnum-$concepts.list_concepts.size()>0){
				System.out.println("Warning::New Concepts have been found and added to the list;");
			}
			int rnum=$concepts.list_concepts.size();
			if(rnum-$concepts.list_concepts.size()>0){
				System.out.println("Warning::New Relations have been found and added to the list;");
			}
			$ontology.ontology.concept=$links.list_concepts_out;
			$ontology.ontology.hierarchy=$hierarchies.list_hierarchies;
			$ontology.ontology.relation=$links.list_relations_out;
			$ontology.ontology.triple=$links.list_links;
		}
	'}'
	;

concepts returns[ArrayList <Concepts> list_concepts]
@init{
	$concepts.list_concepts=new ArrayList<Concepts>();
}
	:
	'Concepts[' 
		(cp1=concept{$concepts.list_concepts.add($cp1.concep);} (',' cp2=concept{$concepts.list_concepts.add($cp2.concep);})*)?
	']'
	;
	
concept returns[Concepts concep]
@init{
	$concept.concep=new Concepts();
	$concept.concep.atribute=new ArrayList<Attributes>();
}
	:
	'{'
		c1=ID{$concept.concep.name=$c1.text;} (',''Description('description=STRING{
			String descrip=$description.text;
			$concept.concep.description=descrip.replaceAll("\"","");
		}')')?
		(',''Attributes['a1=atribute{$concept.concep.atribute.add($a1.atr);}(',' a2=atribute{$concept.concep.atribute.add($a2.atr);})*']')?
	'}'
	;
	
atribute returns[Attributes atr]
@init{
	$atribute.atr=new Attributes();
}
	:
	'{'
		a1=ID {
			$atribute.atr.name=$a1.text;
		}
		('float'{$atribute.atr.type="float";}|'string'{$atribute.atr.type="string";}|'int'{$atribute.atr.type="int";}|'boolean'{$atribute.atr.type="boolean";}) 
	'}'
	;

hierarchies returns[ArrayList <Hierarchies> list_hierarchies]
@init{
	$hierarchies.list_hierarchies=new ArrayList<Hierarchies>();
}
	:
	'Hierarchies['
		(h1=hierarchy{
		    $hierarchies.list_hierarchies.add($h1.hierar);
		} (',' h2=hierarchy{
		    $hierarchies.list_hierarchies.add($h2.hierar);
		})*)?
	']'	
	;
	
hierarchy returns[Hierarchies hierar]
@init{
	$hierarchy.hierar=new Hierarchies();
}
	:
	'{'hi1=ID '>' hi2=ID{
		$hierarchy.hierar.class_name=$hi1.text;
		$hierarchy.hierar.subclass_name=$hi2.text;
	}'}'
	;
	
relations returns[ArrayList <Relations> list_relations]
@init{
	$relations.list_relations=new ArrayList<Relations>();
}
	:
	'Relations['
		(re1=relation{$relations.list_relations.add($re1.rel);} (',' re2=relation{$relations.list_relations.add($re2.rel);})*)?
	']'
	;
	
relation returns[Relations rel]
@init{
	$relation.rel=new Relations();
}
	:
	'{'r1=ID{
		$relation.rel.name=$r1.text;
	}(',Description('r2=STRING{
		String descrip=$r2.text;
		$relation.rel.description=descrip.replaceAll("\"","");
	}')')?'}'
	;
	
links[ArrayList<Concepts> list_concepts,ArrayList<Relations> list_relations] returns [ArrayList<Triples> list_links,ArrayList<Concepts> list_concepts_out,ArrayList<Relations> list_relations_out]
@init{
	$links.list_links=new ArrayList<Triples>();
}
@after{
	$links.list_concepts_out=$links.list_concepts;
	$links.list_relations_out=$links.list_relations;
}
	:
	'Links['
		(l1=link{
			Triples li1=$l1.lin;
			if(li1.CheckConcept1($links.list_concepts)==1){
				System.out.println("Erro::Concept "+li1.class1+" not found on specification.\n");
				li1=null;
			}
			if(li1.CheckConcept2($links.list_concepts)==1){
				System.out.println("Erro::Concept "+li1.class2+" not found on specification.\n");
				li1=null;
			}
			if(li1.CheckRelation($links.list_relations)==1){
				System.out.println("Erro::Relations "+li1.relation+" not found on specification.\n");
				li1=null;
			}
			if(li1!=null){
				$links.list_links.add(li1);
			}
		}(',' l2=link{
			Triples li2=$l2.lin;
			if(li2.CheckConcept1($links.list_concepts)==1){
				System.out.println("Erro::Concept "+li2.class1+" not found on specification.\n");
				li2=null;
			}
			if(li2.CheckConcept2($links.list_concepts)==1){
				System.out.println("Erro::Concept "+li2.class2+" not found on specification.\n");
				li2=null;
			}
			if(li2.CheckRelation($links.list_relations)==1){
				System.out.println("Erro::Relations "+li2.relation+" not found on specification.\n");
				li2=null;
			}
			if(li2!=null){
				$links.list_links.add(li2);
			}
		})*)?
	']'
	;
	
link returns[Triples lin]
@init{
	$link.lin=new Triples();
}
	:
	'{'c1=ID r=ID ('min' min=INT)? ('max' max=INT)? c2=ID{
		$link.lin.class1=$c1.text;
		$link.lin.relation=$r.text;
		if($min.text!=null){
			$link.lin.min=Integer.parseInt($min.text);
		}else{
			$link.lin.min=0;
		}
		if($max.text!=null){
			$link.lin.max=Integer.parseInt($max.text);
		}else{
			$link.lin.max=0;
		}
		$link.lin.class2=$c2.text;
	}'}'
	;

ID  :	('a'..'z'|'A'..'Z'|'_'|'-') ('a'..'z'|'A'..'Z'|'0'..'9'|'_'|'-')*
    ;

INT :	'0'..'9'+
    ;

COMMENT
    :   '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    |   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;

STRING
    :  '"' ( ESC_SEQ | ~('\\'|'"') )* '"'
    ;

CHAR:  '\'' ( ESC_SEQ | ~('\''|'\\') ) '\''
    ;

fragment
HEX_DIGIT : ('0'..'9'|'a'..'f'|'A'..'F') ;

fragment
ESC_SEQ
    :   '\\' ('b'|'t'|'n'|'f'|'r'|'\"'|'\''|'\\')
    |   UNICODE_ESC
    |   OCTAL_ESC
    ;

fragment
OCTAL_ESC
    :   '\\' ('0'..'3') ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7') ('0'..'7')
    |   '\\' ('0'..'7')
    ;

fragment
UNICODE_ESC
    :   '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
    ;