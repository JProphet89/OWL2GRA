// $ANTLR 3.5.1 /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g 2014-06-30 21:29:16

	package Onto2OWL;
	import java.util.ArrayList;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class OntoDLParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "CHAR", "COMMENT", "ESC_SEQ", 
		"HEX_DIGIT", "ID", "INT", "OCTAL_ESC", "STRING", "UNICODE_ESC", "WS", 
		"')'", "','", "',Description('", "'>'", "'Attributes['", "'Concepts['", 
		"'Description('", "'Hierarchies['", "'Links['", "'Ontology{'", "'Relations['", 
		"']'", "'boolean'", "'float'", "'int'", "'max'", "'min'", "'string'", 
		"'{'", "'}'"
	};
	public static final int EOF=-1;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int T__32=32;
	public static final int T__33=33;
	public static final int CHAR=4;
	public static final int COMMENT=5;
	public static final int ESC_SEQ=6;
	public static final int HEX_DIGIT=7;
	public static final int ID=8;
	public static final int INT=9;
	public static final int OCTAL_ESC=10;
	public static final int STRING=11;
	public static final int UNICODE_ESC=12;
	public static final int WS=13;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public OntoDLParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public OntoDLParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return OntoDLParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g"; }



	// $ANTLR start "ontology"
	// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:18:1: ontology returns [Ontologies ontology] : 'Ontology{' concepts hierarchies relations links[$concepts.list_concepts,$relations.list_relations] '}' ;
	public final Ontologies ontology() throws RecognitionException {
		Ontologies ontology = null;


		ArrayList <Concepts> concepts1 =null;
		ArrayList <Relations> relations2 =null;
		ParserRuleReturnScope links3 =null;
		ArrayList <Hierarchies> hierarchies4 =null;


			ontology =new Ontologies();

		try {
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:22:2: ( 'Ontology{' concepts hierarchies relations links[$concepts.list_concepts,$relations.list_relations] '}' )
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:23:2: 'Ontology{' concepts hierarchies relations links[$concepts.list_concepts,$relations.list_relations] '}'
			{
			match(input,23,FOLLOW_23_in_ontology29); 
			pushFollow(FOLLOW_concepts_in_ontology36);
			concepts1=concepts();
			state._fsp--;

			pushFollow(FOLLOW_hierarchies_in_ontology43);
			hierarchies4=hierarchies();
			state._fsp--;

			pushFollow(FOLLOW_relations_in_ontology50);
			relations2=relations();
			state._fsp--;

			pushFollow(FOLLOW_links_in_ontology57);
			links3=links(concepts1, relations2);
			state._fsp--;


						int cnum=concepts1.size();
						if(cnum-concepts1.size()>0){
							System.out.println("Warning::New Concepts have been found and added to the list;");
						}
						int rnum=concepts1.size();
						if(rnum-concepts1.size()>0){
							System.out.println("Warning::New Relations have been found and added to the list;");
						}
						ontology.concept=(links3!=null?((OntoDLParser.links_return)links3).list_concepts_out:null);
						ontology.hierarchy=hierarchies4;
						ontology.relation=(links3!=null?((OntoDLParser.links_return)links3).list_relations_out:null);
						ontology.triple=(links3!=null?((OntoDLParser.links_return)links3).list_links:null);
					
			match(input,33,FOLLOW_33_in_ontology62); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return ontology;
	}
	// $ANTLR end "ontology"



	// $ANTLR start "concepts"
	// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:48:1: concepts returns [ArrayList <Concepts> list_concepts] : 'Concepts[' (cp1= concept ( ',' cp2= concept )* )? ']' ;
	public final ArrayList <Concepts> concepts() throws RecognitionException {
		ArrayList <Concepts> list_concepts = null;


		Concepts cp1 =null;
		Concepts cp2 =null;


			list_concepts =new ArrayList<Concepts>();

		try {
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:52:2: ( 'Concepts[' (cp1= concept ( ',' cp2= concept )* )? ']' )
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:53:2: 'Concepts[' (cp1= concept ( ',' cp2= concept )* )? ']'
			{
			match(input,19,FOLLOW_19_in_concepts81); 
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:54:3: (cp1= concept ( ',' cp2= concept )* )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==32) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:54:4: cp1= concept ( ',' cp2= concept )*
					{
					pushFollow(FOLLOW_concept_in_concepts89);
					cp1=concept();
					state._fsp--;

					list_concepts.add(cp1);
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:54:59: ( ',' cp2= concept )*
					loop1:
					while (true) {
						int alt1=2;
						int LA1_0 = input.LA(1);
						if ( (LA1_0==15) ) {
							alt1=1;
						}

						switch (alt1) {
						case 1 :
							// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:54:60: ',' cp2= concept
							{
							match(input,15,FOLLOW_15_in_concepts93); 
							pushFollow(FOLLOW_concept_in_concepts97);
							cp2=concept();
							state._fsp--;

							list_concepts.add(cp2);
							}
							break;

						default :
							break loop1;
						}
					}

					}
					break;

			}

			match(input,25,FOLLOW_25_in_concepts105); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return list_concepts;
	}
	// $ANTLR end "concepts"



	// $ANTLR start "concept"
	// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:58:1: concept returns [Concepts concep] : '{' c1= ID ( ',' 'Description(' description= STRING ')' )? ( ',' 'Attributes[' a1= atribute ( ',' a2= atribute )* ']' )? '}' ;
	public final Concepts concept() throws RecognitionException {
		Concepts concep = null;


		Token c1=null;
		Token description=null;
		Attributes a1 =null;
		Attributes a2 =null;


			concep =new Concepts();
			concep.atribute=new ArrayList<Attributes>();

		try {
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:63:2: ( '{' c1= ID ( ',' 'Description(' description= STRING ')' )? ( ',' 'Attributes[' a1= atribute ( ',' a2= atribute )* ']' )? '}' )
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:64:2: '{' c1= ID ( ',' 'Description(' description= STRING ')' )? ( ',' 'Attributes[' a1= atribute ( ',' a2= atribute )* ']' )? '}'
			{
			match(input,32,FOLLOW_32_in_concept125); 
			c1=(Token)match(input,ID,FOLLOW_ID_in_concept131); 
			concep.name=(c1!=null?c1.getText():null);
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:65:41: ( ',' 'Description(' description= STRING ')' )?
			int alt3=2;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==15) ) {
				int LA3_1 = input.LA(2);
				if ( (LA3_1==20) ) {
					alt3=1;
				}
			}
			switch (alt3) {
				case 1 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:65:42: ',' 'Description(' description= STRING ')'
					{
					match(input,15,FOLLOW_15_in_concept135); 
					match(input,20,FOLLOW_20_in_concept136); 
					description=(Token)match(input,STRING,FOLLOW_STRING_in_concept139); 

								String descrip=(description!=null?description.getText():null);
								concep.description=descrip.replaceAll("\"","");
							
					match(input,14,FOLLOW_14_in_concept141); 
					}
					break;

			}

			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:69:3: ( ',' 'Attributes[' a1= atribute ( ',' a2= atribute )* ']' )?
			int alt5=2;
			int LA5_0 = input.LA(1);
			if ( (LA5_0==15) ) {
				alt5=1;
			}
			switch (alt5) {
				case 1 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:69:4: ',' 'Attributes[' a1= atribute ( ',' a2= atribute )* ']'
					{
					match(input,15,FOLLOW_15_in_concept148); 
					match(input,18,FOLLOW_18_in_concept149); 
					pushFollow(FOLLOW_atribute_in_concept152);
					a1=atribute();
					state._fsp--;

					concep.atribute.add(a1);
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:69:71: ( ',' a2= atribute )*
					loop4:
					while (true) {
						int alt4=2;
						int LA4_0 = input.LA(1);
						if ( (LA4_0==15) ) {
							alt4=1;
						}

						switch (alt4) {
						case 1 :
							// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:69:72: ',' a2= atribute
							{
							match(input,15,FOLLOW_15_in_concept155); 
							pushFollow(FOLLOW_atribute_in_concept159);
							a2=atribute();
							state._fsp--;

							concep.atribute.add(a2);
							}
							break;

						default :
							break loop4;
						}
					}

					match(input,25,FOLLOW_25_in_concept163); 
					}
					break;

			}

			match(input,33,FOLLOW_33_in_concept168); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return concep;
	}
	// $ANTLR end "concept"



	// $ANTLR start "atribute"
	// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:73:1: atribute returns [Attributes atr] : '{' a1= ID ( 'float' | 'string' | 'int' | 'boolean' ) '}' ;
	public final Attributes atribute() throws RecognitionException {
		Attributes atr = null;


		Token a1=null;


			atr =new Attributes();

		try {
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:77:2: ( '{' a1= ID ( 'float' | 'string' | 'int' | 'boolean' ) '}' )
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:78:2: '{' a1= ID ( 'float' | 'string' | 'int' | 'boolean' ) '}'
			{
			match(input,32,FOLLOW_32_in_atribute188); 
			a1=(Token)match(input,ID,FOLLOW_ID_in_atribute194); 

						atr.name=(a1!=null?a1.getText():null);
					
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:82:3: ( 'float' | 'string' | 'int' | 'boolean' )
			int alt6=4;
			switch ( input.LA(1) ) {
			case 27:
				{
				alt6=1;
				}
				break;
			case 31:
				{
				alt6=2;
				}
				break;
			case 28:
				{
				alt6=3;
				}
				break;
			case 26:
				{
				alt6=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 6, 0, input);
				throw nvae;
			}
			switch (alt6) {
				case 1 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:82:4: 'float'
					{
					match(input,27,FOLLOW_27_in_atribute201); 
					atr.type="float";
					}
					break;
				case 2 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:82:41: 'string'
					{
					match(input,31,FOLLOW_31_in_atribute204); 
					atr.type="string";
					}
					break;
				case 3 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:82:80: 'int'
					{
					match(input,28,FOLLOW_28_in_atribute207); 
					atr.type="int";
					}
					break;
				case 4 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:82:113: 'boolean'
					{
					match(input,26,FOLLOW_26_in_atribute210); 
					atr.type="boolean";
					}
					break;

			}

			match(input,33,FOLLOW_33_in_atribute216); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return atr;
	}
	// $ANTLR end "atribute"



	// $ANTLR start "hierarchies"
	// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:86:1: hierarchies returns [ArrayList <Hierarchies> list_hierarchies] : 'Hierarchies[' (h1= hierarchy ( ',' h2= hierarchy )* )? ']' ;
	public final ArrayList <Hierarchies> hierarchies() throws RecognitionException {
		ArrayList <Hierarchies> list_hierarchies = null;


		Hierarchies h1 =null;
		Hierarchies h2 =null;


			list_hierarchies =new ArrayList<Hierarchies>();

		try {
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:90:2: ( 'Hierarchies[' (h1= hierarchy ( ',' h2= hierarchy )* )? ']' )
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:91:2: 'Hierarchies[' (h1= hierarchy ( ',' h2= hierarchy )* )? ']'
			{
			match(input,21,FOLLOW_21_in_hierarchies235); 
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:92:3: (h1= hierarchy ( ',' h2= hierarchy )* )?
			int alt8=2;
			int LA8_0 = input.LA(1);
			if ( (LA8_0==32) ) {
				alt8=1;
			}
			switch (alt8) {
				case 1 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:92:4: h1= hierarchy ( ',' h2= hierarchy )*
					{
					pushFollow(FOLLOW_hierarchy_in_hierarchies242);
					h1=hierarchy();
					state._fsp--;


							    list_hierarchies.add(h1);
							
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:94:5: ( ',' h2= hierarchy )*
					loop7:
					while (true) {
						int alt7=2;
						int LA7_0 = input.LA(1);
						if ( (LA7_0==15) ) {
							alt7=1;
						}

						switch (alt7) {
						case 1 :
							// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:94:6: ',' h2= hierarchy
							{
							match(input,15,FOLLOW_15_in_hierarchies246); 
							pushFollow(FOLLOW_hierarchy_in_hierarchies250);
							h2=hierarchy();
							state._fsp--;


									    list_hierarchies.add(h2);
									
							}
							break;

						default :
							break loop7;
						}
					}

					}
					break;

			}

			match(input,25,FOLLOW_25_in_hierarchies258); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return list_hierarchies;
	}
	// $ANTLR end "hierarchies"



	// $ANTLR start "hierarchy"
	// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:100:1: hierarchy returns [Hierarchies hierar] : '{' hi1= ID '>' hi2= ID '}' ;
	public final Hierarchies hierarchy() throws RecognitionException {
		Hierarchies hierar = null;


		Token hi1=null;
		Token hi2=null;


			hierar =new Hierarchies();

		try {
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:104:2: ( '{' hi1= ID '>' hi2= ID '}' )
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:105:2: '{' hi1= ID '>' hi2= ID '}'
			{
			match(input,32,FOLLOW_32_in_hierarchy279); 
			hi1=(Token)match(input,ID,FOLLOW_ID_in_hierarchy282); 
			match(input,17,FOLLOW_17_in_hierarchy284); 
			hi2=(Token)match(input,ID,FOLLOW_ID_in_hierarchy288); 

					hierar.class_name=(hi1!=null?hi1.getText():null);
					hierar.subclass_name=(hi2!=null?hi2.getText():null);
				
			match(input,33,FOLLOW_33_in_hierarchy290); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return hierar;
	}
	// $ANTLR end "hierarchy"



	// $ANTLR start "relations"
	// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:111:1: relations returns [ArrayList <Relations> list_relations] : 'Relations[' (re1= relation ( ',' re2= relation )* )? ']' ;
	public final ArrayList <Relations> relations() throws RecognitionException {
		ArrayList <Relations> list_relations = null;


		Relations re1 =null;
		Relations re2 =null;


			list_relations =new ArrayList<Relations>();

		try {
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:115:2: ( 'Relations[' (re1= relation ( ',' re2= relation )* )? ']' )
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:116:2: 'Relations[' (re1= relation ( ',' re2= relation )* )? ']'
			{
			match(input,24,FOLLOW_24_in_relations310); 
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:117:3: (re1= relation ( ',' re2= relation )* )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==32) ) {
				alt10=1;
			}
			switch (alt10) {
				case 1 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:117:4: re1= relation ( ',' re2= relation )*
					{
					pushFollow(FOLLOW_relation_in_relations317);
					re1=relation();
					state._fsp--;

					list_relations.add(re1);
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:117:59: ( ',' re2= relation )*
					loop9:
					while (true) {
						int alt9=2;
						int LA9_0 = input.LA(1);
						if ( (LA9_0==15) ) {
							alt9=1;
						}

						switch (alt9) {
						case 1 :
							// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:117:60: ',' re2= relation
							{
							match(input,15,FOLLOW_15_in_relations321); 
							pushFollow(FOLLOW_relation_in_relations325);
							re2=relation();
							state._fsp--;

							list_relations.add(re2);
							}
							break;

						default :
							break loop9;
						}
					}

					}
					break;

			}

			match(input,25,FOLLOW_25_in_relations333); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return list_relations;
	}
	// $ANTLR end "relations"



	// $ANTLR start "relation"
	// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:121:1: relation returns [Relations rel] : '{' r1= ID ( ',Description(' r2= STRING ')' )? '}' ;
	public final Relations relation() throws RecognitionException {
		Relations rel = null;


		Token r1=null;
		Token r2=null;


			rel =new Relations();

		try {
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:125:2: ( '{' r1= ID ( ',Description(' r2= STRING ')' )? '}' )
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:126:2: '{' r1= ID ( ',Description(' r2= STRING ')' )? '}'
			{
			match(input,32,FOLLOW_32_in_relation353); 
			r1=(Token)match(input,ID,FOLLOW_ID_in_relation356); 

					rel.name=(r1!=null?r1.getText():null);
				
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:128:3: ( ',Description(' r2= STRING ')' )?
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( (LA11_0==16) ) {
				alt11=1;
			}
			switch (alt11) {
				case 1 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:128:4: ',Description(' r2= STRING ')'
					{
					match(input,16,FOLLOW_16_in_relation359); 
					r2=(Token)match(input,STRING,FOLLOW_STRING_in_relation362); 

							String descrip=(r2!=null?r2.getText():null);
							rel.description=descrip.replaceAll("\"","");
						
					match(input,14,FOLLOW_14_in_relation364); 
					}
					break;

			}

			match(input,33,FOLLOW_33_in_relation367); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return rel;
	}
	// $ANTLR end "relation"


	public static class links_return extends ParserRuleReturnScope {
		public ArrayList<Triples> list_links;
		public ArrayList<Concepts> list_concepts_out;
		public ArrayList<Relations> list_relations_out;
	};


	// $ANTLR start "links"
	// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:134:1: links[ArrayList<Concepts> list_concepts,ArrayList<Relations> list_relations] returns [ArrayList<Triples> list_links,ArrayList<Concepts> list_concepts_out,ArrayList<Relations> list_relations_out] : 'Links[' (l1= link ( ',' l2= link )* )? ']' ;
	public final OntoDLParser.links_return links(ArrayList<Concepts> list_concepts, ArrayList<Relations> list_relations) throws RecognitionException {
		OntoDLParser.links_return retval = new OntoDLParser.links_return();
		retval.start = input.LT(1);

		Triples l1 =null;
		Triples l2 =null;


			retval.list_links =new ArrayList<Triples>();

		try {
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:142:2: ( 'Links[' (l1= link ( ',' l2= link )* )? ']' )
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:143:2: 'Links[' (l1= link ( ',' l2= link )* )? ']'
			{
			match(input,22,FOLLOW_22_in_links393); 
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:144:3: (l1= link ( ',' l2= link )* )?
			int alt13=2;
			int LA13_0 = input.LA(1);
			if ( (LA13_0==32) ) {
				alt13=1;
			}
			switch (alt13) {
				case 1 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:144:4: l1= link ( ',' l2= link )*
					{
					pushFollow(FOLLOW_link_in_links400);
					l1=link();
					state._fsp--;


								Triples li1=l1;
								if(li1.CheckConcept1(list_concepts)==1){
									System.out.println("Erro::Concept "+li1.class1+" not found on specification.\n");
									li1=null;
								}
								if(li1.CheckConcept2(list_concepts)==1){
									System.out.println("Erro::Concept "+li1.class2+" not found on specification.\n");
									li1=null;
								}
								if(li1.CheckRelation(list_relations)==1){
									System.out.println("Erro::Relations "+li1.relation+" not found on specification.\n");
									li1=null;
								}
								if(li1!=null){
									retval.list_links.add(li1);
								}
							
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:161:4: ( ',' l2= link )*
					loop12:
					while (true) {
						int alt12=2;
						int LA12_0 = input.LA(1);
						if ( (LA12_0==15) ) {
							alt12=1;
						}

						switch (alt12) {
						case 1 :
							// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:161:5: ',' l2= link
							{
							match(input,15,FOLLOW_15_in_links403); 
							pushFollow(FOLLOW_link_in_links407);
							l2=link();
							state._fsp--;


										Triples li2=l2;
										if(li2.CheckConcept1(list_concepts)==1){
											System.out.println("Erro::Concept "+li2.class1+" not found on specification.\n");
											li2=null;
										}
										if(li2.CheckConcept2(list_concepts)==1){
											System.out.println("Erro::Concept "+li2.class2+" not found on specification.\n");
											li2=null;
										}
										if(li2.CheckRelation(list_relations)==1){
											System.out.println("Erro::Relations "+li2.relation+" not found on specification.\n");
											li2=null;
										}
										if(li2!=null){
											retval.list_links.add(li2);
										}
									
							}
							break;

						default :
							break loop12;
						}
					}

					}
					break;

			}

			match(input,25,FOLLOW_25_in_links415); 
			}

			retval.stop = input.LT(-1);


				retval.list_concepts_out =list_concepts;
				retval.list_relations_out =list_relations;

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "links"



	// $ANTLR start "link"
	// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:182:1: link returns [Triples lin] : '{' c1= ID r= ID ( 'min' min= INT )? ( 'max' max= INT )? c2= ID '}' ;
	public final Triples link() throws RecognitionException {
		Triples lin = null;


		Token c1=null;
		Token r=null;
		Token min=null;
		Token max=null;
		Token c2=null;


			lin =new Triples();

		try {
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:186:2: ( '{' c1= ID r= ID ( 'min' min= INT )? ( 'max' max= INT )? c2= ID '}' )
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:187:2: '{' c1= ID r= ID ( 'min' min= INT )? ( 'max' max= INT )? c2= ID '}'
			{
			match(input,32,FOLLOW_32_in_link435); 
			c1=(Token)match(input,ID,FOLLOW_ID_in_link438); 
			r=(Token)match(input,ID,FOLLOW_ID_in_link442); 
			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:187:16: ( 'min' min= INT )?
			int alt14=2;
			int LA14_0 = input.LA(1);
			if ( (LA14_0==30) ) {
				alt14=1;
			}
			switch (alt14) {
				case 1 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:187:17: 'min' min= INT
					{
					match(input,30,FOLLOW_30_in_link445); 
					min=(Token)match(input,INT,FOLLOW_INT_in_link449); 
					}
					break;

			}

			// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:187:33: ( 'max' max= INT )?
			int alt15=2;
			int LA15_0 = input.LA(1);
			if ( (LA15_0==29) ) {
				alt15=1;
			}
			switch (alt15) {
				case 1 :
					// /Users/jprophet89/Desktop/OWL2GRA/src/Onto2OWL/OntoDL.g:187:34: 'max' max= INT
					{
					match(input,29,FOLLOW_29_in_link454); 
					max=(Token)match(input,INT,FOLLOW_INT_in_link458); 
					}
					break;

			}

			c2=(Token)match(input,ID,FOLLOW_ID_in_link464); 

					lin.class1=(c1!=null?c1.getText():null);
					lin.relation=(r!=null?r.getText():null);
					if((min!=null?min.getText():null)!=null){
						lin.min=Integer.parseInt((min!=null?min.getText():null));
					}else{
						lin.min=0;
					}
					if((max!=null?max.getText():null)!=null){
						lin.max=Integer.parseInt((max!=null?max.getText():null));
					}else{
						lin.max=0;
					}
					lin.class2=(c2!=null?c2.getText():null);
				
			match(input,33,FOLLOW_33_in_link466); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return lin;
	}
	// $ANTLR end "link"

	// Delegated rules



	public static final BitSet FOLLOW_23_in_ontology29 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_concepts_in_ontology36 = new BitSet(new long[]{0x0000000000200000L});
	public static final BitSet FOLLOW_hierarchies_in_ontology43 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_relations_in_ontology50 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_links_in_ontology57 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_33_in_ontology62 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_19_in_concepts81 = new BitSet(new long[]{0x0000000102000000L});
	public static final BitSet FOLLOW_concept_in_concepts89 = new BitSet(new long[]{0x0000000002008000L});
	public static final BitSet FOLLOW_15_in_concepts93 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_concept_in_concepts97 = new BitSet(new long[]{0x0000000002008000L});
	public static final BitSet FOLLOW_25_in_concepts105 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_32_in_concept125 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_ID_in_concept131 = new BitSet(new long[]{0x0000000200008000L});
	public static final BitSet FOLLOW_15_in_concept135 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_20_in_concept136 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_STRING_in_concept139 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_14_in_concept141 = new BitSet(new long[]{0x0000000200008000L});
	public static final BitSet FOLLOW_15_in_concept148 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_18_in_concept149 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_atribute_in_concept152 = new BitSet(new long[]{0x0000000002008000L});
	public static final BitSet FOLLOW_15_in_concept155 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_atribute_in_concept159 = new BitSet(new long[]{0x0000000002008000L});
	public static final BitSet FOLLOW_25_in_concept163 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_33_in_concept168 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_32_in_atribute188 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_ID_in_atribute194 = new BitSet(new long[]{0x000000009C000000L});
	public static final BitSet FOLLOW_27_in_atribute201 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_31_in_atribute204 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_28_in_atribute207 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_26_in_atribute210 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_33_in_atribute216 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_21_in_hierarchies235 = new BitSet(new long[]{0x0000000102000000L});
	public static final BitSet FOLLOW_hierarchy_in_hierarchies242 = new BitSet(new long[]{0x0000000002008000L});
	public static final BitSet FOLLOW_15_in_hierarchies246 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_hierarchy_in_hierarchies250 = new BitSet(new long[]{0x0000000002008000L});
	public static final BitSet FOLLOW_25_in_hierarchies258 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_32_in_hierarchy279 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_ID_in_hierarchy282 = new BitSet(new long[]{0x0000000000020000L});
	public static final BitSet FOLLOW_17_in_hierarchy284 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_ID_in_hierarchy288 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_33_in_hierarchy290 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_24_in_relations310 = new BitSet(new long[]{0x0000000102000000L});
	public static final BitSet FOLLOW_relation_in_relations317 = new BitSet(new long[]{0x0000000002008000L});
	public static final BitSet FOLLOW_15_in_relations321 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_relation_in_relations325 = new BitSet(new long[]{0x0000000002008000L});
	public static final BitSet FOLLOW_25_in_relations333 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_32_in_relation353 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_ID_in_relation356 = new BitSet(new long[]{0x0000000200010000L});
	public static final BitSet FOLLOW_16_in_relation359 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_STRING_in_relation362 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_14_in_relation364 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_33_in_relation367 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_22_in_links393 = new BitSet(new long[]{0x0000000102000000L});
	public static final BitSet FOLLOW_link_in_links400 = new BitSet(new long[]{0x0000000002008000L});
	public static final BitSet FOLLOW_15_in_links403 = new BitSet(new long[]{0x0000000100000000L});
	public static final BitSet FOLLOW_link_in_links407 = new BitSet(new long[]{0x0000000002008000L});
	public static final BitSet FOLLOW_25_in_links415 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_32_in_link435 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_ID_in_link438 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_ID_in_link442 = new BitSet(new long[]{0x0000000060000100L});
	public static final BitSet FOLLOW_30_in_link445 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_INT_in_link449 = new BitSet(new long[]{0x0000000020000100L});
	public static final BitSet FOLLOW_29_in_link454 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_INT_in_link458 = new BitSet(new long[]{0x0000000000000100L});
	public static final BitSet FOLLOW_ID_in_link464 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_33_in_link466 = new BitSet(new long[]{0x0000000000000002L});
}
