/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerpraj;
import java.lang.Math;
import java.io.*;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import compilerpraj.LexerFP;

/**
 *
 * @author prajn
 */
public class MyParser {

boolean debug;        //do I want debug output?
int nerrs;            //number of errors so far
int errflag;          //was there an error?
int vchar;             //the current working character

void debug(String msg)
{
  if (debug)
    System.out.println(msg);
}

final static int STACKSIZE = 500;  //maximum stack size
int statestk[] = new int[STACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;

final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}

final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}

void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}

String   text;//user iable to return contextual strings
HelpParser val; //used to return semantic vals from action routines
HelpParser lval;//the 'lval' (result) I got from lex()
HelpParser valstk[];
int valptr;

void val_init()
{
  valstk=new HelpParser[STACKSIZE];
  val=new HelpParser();
  lval=new HelpParser();
  valptr=-1;
}
void val_push(HelpParser val)
{
  if (valptr>=STACKSIZE)
    return;
  valstk[++valptr]=val;
}
HelpParser val_pop()
{
  if (valptr<0)
    return new HelpParser();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
HelpParser val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new HelpParser();
  return valstk[ptr];
}
final HelpParser dup_val(HelpParser val)
{
  HelpParser dup = new HelpParser();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short DIVIDE=257;
public final static short ADD=258;
public final static short MINUS=259;
public final static short MULTIPLY=260;
public final static short MOD=261;
public final static short CHECKEQUALS=262;
public final static short GREATEREQ=263;
public final static short LESSEQ=264;
public final static short EQUAL=265;
public final static short GREATER=266;
public final static short LESS=267;
public final static short PROGRAM=268;
public final static short FUNCTION=269;
public final static short RETURN=270;
public final static short IF=271;
public final static short THEN=272;
public final static short ELSE=273;
public final static short WHILE=274;
public final static short DO=275;
public final static short OR=276;
public final static short AND=277;
public final static short PRINT=278;
public final static short TRUE=279;
public final static short FALSE=280;
public final static short IDENTIFIER=281;
public final static short INTEGER=282;
public final static short FLOAT=283;
public final static short STRING=284;
public final static short COMMENT_LINE=285;
public final static short NEG=286;
public final static short ERRCODE=256;
final static short lhs[] = {                           -1,
    0,    0,    1,    1,    2,    2,    2,    2,    2,    2,
    2,    2,    2,
};
final static short len[] = {                            2,
    0,    2,    1,    2,    1,    3,    3,    3,    3,    2,
    3,    3,    3,
};
final static short defred[] = {                         1,
    0,    5,    0,    0,    0,    0,    0,    3,    0,    2,
    0,    0,    0,    0,    0,    0,    0,    0,    4,    0,
    0,    0,    0,    0,   13,    0,
};
final static short dgoto[] = {                          1,
   10,   11,
};
final static short sindex[] = {                         0,
   48,    0,  -35,  -35,  -35,  -35,  -35,    0,  -35,    0,
    4,  -37,   57,   57,   57,   57,  -40,  -35,    0,  -90,
  -90,  -90,  -90,  -90,    0,  -90,
};
final static short rindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   66,    0,    0,    0,    0,    0,    0,    0,  -10,
   -1,    8,   19,   28,    0,   37,
};
final static short gindex[] = {                         0,
    0,  111,
};
final static int TABLESIZE=339;
static short table[];
static { table();}
static void table(){
table = new short[]{                          7,
   25,    7,    9,   18,    9,    0,    5,    4,    6,    3,
    0,    6,    0,   19,    0,    0,    0,    8,    0,    0,
    0,    0,    0,    0,    0,    0,    7,    0,    9,    7,
    7,    7,    7,    0,    7,    6,    7,   11,    6,    6,
    6,    6,    0,    6,    8,    6,   12,    8,    8,    8,
    8,    0,    8,   18,    8,    9,   18,    8,    9,    9,
    9,    9,    0,    9,   11,    9,    0,   11,   11,   11,
   11,    0,   11,   12,   11,   10,   12,   12,   12,   12,
    0,   12,    0,   12,    7,    0,    0,    9,    0,    5,
    4,    0,    3,    7,    6,    0,    9,   18,    5,    4,
    0,    3,   10,    6,    0,    0,   10,   10,   10,    0,
   10,    0,   10,   12,   13,   14,   15,   16,    0,   17,
    0,    0,   20,   21,   22,   23,   24,    0,   26,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   18,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    2,    0,    2,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    7,    0,    0,    0,    0,    0,    0,    0,    0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    8,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    9,    0,    0,    0,    0,    0,    0,    0,    0,   11,
    0,    0,    0,    0,    0,    0,    0,    0,   12,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    2,
    0,    0,    0,    0,    0,    0,    0,    0,    2,
};
}
static short check[];
static { check(); }
static void check() {
check = new short[] {                         10,
   41,   37,   40,   94,   40,   -1,   42,   43,   10,   45,
   -1,   47,   -1,   10,   -1,   -1,   -1,   10,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   37,   -1,   10,   40,
   41,   42,   43,   -1,   45,   37,   47,   10,   40,   41,
   42,   43,   -1,   45,   37,   47,   10,   40,   41,   42,
   43,   -1,   45,   94,   47,   37,   94,   10,   40,   41,
   42,   43,   -1,   45,   37,   47,   -1,   40,   41,   42,
   43,   -1,   45,   37,   47,   10,   40,   41,   42,   43,
   -1,   45,   -1,   47,   37,   -1,   -1,   40,   -1,   42,
   43,   -1,   45,   37,   47,   -1,   40,   94,   42,   43,
   -1,   45,   37,   47,   -1,   -1,   41,   42,   43,   -1,
   45,   -1,   47,    3,    4,    5,    6,    7,   -1,    9,
   -1,   -1,   12,   13,   14,   15,   16,   -1,   18,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   94,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  282,   -1,  282,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  282,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  282,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  282,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  282,
};
}
final static short FINAL=1;
final static short MAXTOKEN=286;
final static String name[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,"'\\n'",null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
null,"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'^'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"DIVIDE","ADD","MINUS","MULTIPLY","MOD",
"CHECKEQUALS","GREATEREQ","LESSEQ","EQUAL","GREATER","LESS","PROGRAM",
"FUNCTION","RETURN","IF","THEN","ELSE","WHILE","DO","OR","AND","PRINT","TRUE",
"FALSE","IDENTIFIER","INTEGER","FLOAT","STRING","COMMENT_LINE","NEG",
};
final static String rule[] = {
"$accept : input",
"input :",
"input : input line",
"line : '\\n'",
"line : exp '\\n'",
"exp : INTEGER",
"exp : '+' exp exp",
"exp : '-' exp exp",
"exp : '*' exp exp",
"exp : '/' exp exp",
"exp : '-' exp",
"exp : '%' exp exp",
"exp : exp '^' exp",
"exp : '(' exp ')'",
};

//#line 36 "FP.y"
public LexerFP lexe;

	/* interface to the lexer */
	private int lex() {
		int retVal = -1;
		try {
			lval = new HelpParser(0);
			lexe.yylex();
			System.out.println("Myval "+retVal);
		} catch (IOException e) {
			System.err.println("IO Error:" + e);
		}
		return retVal;
	}
	
	public void error (String error) {
		System.err.println("Error : " + error + " at line " );
		System.err.println("String rejected");
	}
	
	/* constructor taking in File Input */
	public MyParser (Reader r) {
		lexe = new LexerFP(r);
	}
	
	public static void main (String [] args) throws Exception {
		MyParser MyParser = new MyParser(new FileReader(args[0]));
		MyParser.parse();
		SymbolTable t=new SymbolTable(args[0]);
		System.out.println("String accepted");
	}

void lexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= MAXTOKEN) //check index bounds
     s = name[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}


int n;       //next next thing to do
int m;       //
int state;   //current parsing state from state table
String s;    //current token string

int parse()
{
boolean doaction;
  init_stacks();
  nerrs = 0;
  errflag = 0;
  vchar = -1;          //impossible char forces a read
  state=0;            //initial state
  state_push(state);  //save it
  val_push(lval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (debug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (n=defred[state];n==0;n=defred[state])
      {
      if (debug) debug("n:"+n+"  state:"+state+"  vchar:"+vchar);
      if (vchar < 0)      //we want a char?
        {
        vchar = lex();  //get next token
        if (debug) debug(" next vchar:"+vchar);
        //#### ERROR CHECK ####
        if (vchar < 0)    //it it didn't work/error
          {
          vchar = 0;      //change it to default string (no -1!)
          if (debug)
            lexdebug(state,vchar);
          }
        }//char<0
      n = sindex[state];  //get amount to shift by (shift index)
      if ((n != 0) && (n += vchar) >= 0 &&
          n <= TABLESIZE && check[n] == vchar)
        {
        if (debug)
          debug("state "+state+", shifting to state "+table[n]);
        //#### NEXT STATE ####
        state = table[n];//we are in a new state
        state_push(state);   //save it
        val_push(lval);      //push our lval as the input for next rule
        vchar = -1;           //since we have 'eaten' a token, say we need another
        if (errflag > 0)     //have we recovered an error?
           --errflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the n=0 loop
        }

    n = rindex[state];  //reduce
    if ((n !=0 ) && (n += vchar) >= 0 &&
            n <= TABLESIZE && check[n] == vchar)
      {   //we reduced!
      if (debug) debug("reduce");
      n = table[n];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (errflag==0)
        {
        error("syntax error");
        nerrs++;
        }
      if (errflag < 3) //low error count?
        {
        errflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            error("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          n = sindex[state_peek(0)];
          if ((n != 0) && (n += ERRCODE) >= 0 &&
                    n <= TABLESIZE && check[n] == ERRCODE)
            {
            if (debug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+table[n]+" ");
            state = table[n];
            state_push(state);
            val_push(lval);
            doaction=false;
            break;
            }
          else
            {
            if (debug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              error("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (vchar == 0)
          return 1; //abort
        if (debug)
          {
          s = null;
          if (vchar <= MAXTOKEN) s = name[vchar];
          if (s == null) s = "illegal-symbol";
          debug("state "+state+", error recovery discards token "+vchar+" ("+s+")");
          }
        vchar = -1;  //read another
        }
      }//end error recovery
    }//n=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    m = len[n];          //get count of terminals on rhs
    if (debug)
      debug("state "+state+", reducing "+m+" by rule "+n+" ("+rule[n]+")");
    if (m>0)                 //if count of rhs not 'nil'
      val = val_peek(m-1); //get current semantic value
    val = dup_val(val); //duplicate val if HelpMyParser is used as semantic value
    switch(n)
      {
case 4:
{ System.out.println(" " + val_peek(1).dval + " "); }
break;
case 5:
{ val = val_peek(0); }
break;
case 6:
{ val = new HelpParser(val_peek(2).dval + val_peek(0).dval); }
break;
case 7:
{ val = new HelpParser(val_peek(2).dval - val_peek(0).dval); }
break;
case 8:
{ val = new HelpParser(val_peek(2).dval * val_peek(0).dval); }
break;
case 9:
{ val = new HelpParser(val_peek(2).dval / val_peek(0).dval); }
break;
case 10:
{ val = new HelpParser(-val_peek(0).dval); }
break;
case 11:
{ val = new HelpParser(val_peek(2).dval / val_peek(0).dval); }
break;
case 12:
{ val = new HelpParser(Math.pow(val_peek(2).dval, val_peek(0).dval)); }
break;
case 13:
{ val = val_peek(1); }
break;
    }
    if (debug) debug("reduce");
    state_drop(m);             //we just reduced len states
    state = state_peek(0);     //get new state
    val_drop(m);               //corresponding value drop
    m = lhs[n];            //select next TERMINAL(on lhs)
    if (state == 0 && m == 0)//done? 'rest' state and at first TERMINAL
      {
      if (debug) debug("After reduction, shifting from state 0 to state "+FINAL+"");
      state = FINAL;         //explicitly say we're done
      state_push(FINAL);       //and save it
      val_push(val);           //also save the semantic value of parsing
      if (vchar < 0)            //we want another character?
        {
        vchar = lex();        //get next character
        if (vchar<0) vchar=0;  //clean, if necessary
        }
      if (vchar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if state
    else                        //else not done yet
      {                         //get next state and push, for next defred[]
      n = gindex[m];      //find out where to go
      if ((n != 0) && (n += state) >= 0 &&
            n <= TABLESIZE && check[n] == state)
        state = table[n]; //get new state
      else
        state = dgoto[m]; //else go to new defred
      if (debug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+state+"");
      state_push(state);     //going again, so push state & val...
      val_push(val);         //for next action
      }
    }//main loop
  return 0;//accept!!
}

    public void run()
{
  parse();
}

public MyParser()
{
  //nothing to do
}
public MyParser(boolean debugMe)
{
  debug=debugMe;
}
}
    
