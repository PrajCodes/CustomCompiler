/* JFlex Lexical Analysis for our FP Language */
import java.io.*;
import java.util.ArrayList;

%%
%public
%class FP
%byaccj
%unicode
%line
%column

%{
static ArrayList<String> ids=new ArrayList<String>();
public int getLine() {
		return yyline;
}
private MyParser yyparser;
public LexerFP (java.io.Reader r, MyParser yyparser) {
		this(r);
		this.yyparser=yyparser;
		System.out.println("Called me");
	}
	
%}

lineTerminator = \r|\n|\r\n|\
inputChar = [^\r\n]
whiteSpace = {lineTerminator}|[ \t\f]
comment = "//"({inputChar}|[ ])*{lineTerminator}?
digit=[0-9]
letter=[a-zA-Z]
chars=[a-zA-Z0-9]|\
identifier={letter}({letter}|{digit}){7}
integer=[-]?{whiteSpace}*{digit}+
float={integer}"."{digit}+
string="("({chars}|[ ])*")"
boolean=T|F
%%
[0-9]+					   {yyparser.yylval = new HelpParser(Integer.parseInt(yytext()));return MyParser.INTEGER;}
"("                        return "{";   								
")"                        return "}";      							
"{"                        ids.add("{");    							
"}"                        ids.add("}"};    							
"/"                        return MyParser.DIVIDE;   							
"+"                        return MyParser.ADD;      							
"-"                        return MyParser.MINUS;    							
"*"                        return MyParser.MULTIPLY; 							
"%"						   return MyParser.MOD;	    							
"=="                       return MyParser.CHECKEQUALS;      					
"<="                       return MyParser.LESSEQ;   							
">="                       return MyParser.GREATEREQ;							
"!="                       return MyParser.NOTEQ;    							
"="                        return MyParser.EQUAL;    							
">"                        return MyParser.GREATER;  							
"<"                        return MyParser.LESS;     							
"Program"				   return MyParser.PROGRAM;  							
"Function"				   return MyParser.FUNCTION;	    						
"return"				   return MyParser.RETURN;	   							
"if"					   return MyParser.IF;	   								
"then"					   return MyParser.THEN;	    							
"else"					   return MyParser.ELSE;    								
"while"					   return MyParser.WHILE;    						
"do"					   return MyParser.DO;    							
"or"					   return MyParser.OR;		   						
"and"					   return MyParser.AND;		    					
"print"					   return MyParser.PRINT;		    					
"T"						   return MyParser.TRUE;		   							
"F"						   return MyParser.FALSE;	  							
{identifier}               { ids.add(yytext()+" ");return MyParser.IDENTIFIER;}     
{integer}				   return MyParser.INTEGER;	   							
{float}                    return MyParser.FLOAT;	    						
{string}                   return MyParser.STRING;    							
[\r]+  {/*Do Nothing*/}
[\t]+    {/*Do Nothing*/}
[\n]+   {/*Do Nothing*/}
{comment}				   return MyParser.COMMENT_LINE; 

\n       {/*Do Nothing*/}
.        {/*Do Nothing*/}  							