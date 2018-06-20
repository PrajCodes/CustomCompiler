%{
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
%}

/* YACC Declarations */
%token DIVIDE ADD MINUS   MULTIPLY MOD CHECKEQUALS  GREATEREQ LESSEQ EQUAL GREATER  LESS PROGRAM  FUNCTION RETURN  IF THEN  ELSE WHILE DO OR AND PRINT TRUE %token FALSE IDENTIFIER INTEGER FLOAT STRING COMMENT_LINE
%left '-' '+'
%left '*' '/'
%left '%'
%left NEG /* negation--unary minus */
%right '^' /* exponentiation */

/* Grammar follows */
%%
input: /* empty string */
 | input line
 ;

line: '\n'
 | exp '\n' { System.out.println(" " + $1.dval + " "); }
 ;

exp: INTEGER { $$ = $1; }
 | '+' exp exp  { $$ = new HelpParser($1.dval + $3.dval); }
 | '-' exp exp  { $$ = new HelpParser($1.dval - $3.dval); }
 | '*' exp exp  { $$ = new HelpParser($1.dval * $3.dval); }
 | '/' exp exp  { $$ = new HelpParser($1.dval / $3.dval); }
 | '-' exp %prec NEG { $$ = new HelpParser(-$2.dval); }
 | '%' exp exp  { $$ = new HelpParser($1.dval / $3.dval); } 
 | exp '^' exp { $$ = new HelpParser(Math.pow($1.dval, $3.dval)); }
 | '(' exp ')' { $$ = $2; }
 ;
%%
public LexerFP lexe;

	/* interface to the lexer */
	private int yylex() {
		int retVal = -1;
		try {
			yylval = new HelpParser(0);
			lexe.yylex();
			System.out.println("Myval "+retVal);
		} catch (IOException e) {
			System.err.println("IO Error:" + e);
		}
		return retVal;
	}
	
	public void yyerror (String error) {
		System.err.println("Error : " + error + " at line " );
		System.err.println("String rejected");
	}
	
	/* constructor taking in File Input */
	public MyParser (Reader r) {
		lexe = new Lexer(r);
	}
	
	public static void main (String [] args) throws Exception {
		MyParser yyparser = new MyParser(new FileReader(args[0]));
		yyparser.yyparse();
		SymbolTable t=new SymbolTable(args[0]);
		System.out.println("String accepted");
	}

