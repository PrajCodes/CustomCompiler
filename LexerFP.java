/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilerpraj;

/*  prajn*/

import java.io.*;
import java.util.ArrayList;
import jdk.nashorn.internal.parser.Lexer;

public class LexerFP {

  public static final int EOF = -1;	// End of file
  private static final int BuffSize = 16384;
  public static final int Init = 0;

  
  private static final int LexState[] = { 
     0, 0
  };

  
  private static final String CmPacked = 
    "\11\0\1\4\1\2\1\0\1\4\1\1\22\0\1\3\1\26\3\0"+
    "\1\22\2\0\1\12\1\13\1\21\1\20\1\0\1\10\1\11\1\5"+
    "\12\6\2\0\1\24\1\23\1\25\2\0\5\7\1\15\11\7\1\27"+
    "\3\7\1\14\6\7\1\0\1\3\4\0\1\33\1\7\1\37\1\50"+
    "\1\42\1\43\1\32\1\44\1\41\2\7\1\45\1\34\1\36\1\31"+
    "\1\51\1\7\1\30\1\46\1\40\1\35\1\7\1\47\3\7\1\16"+
    "\1\0\1\17\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uff92\0";

  
  private static final char [] CMAP = UnPckMap(CmPacked);
  private static final int [] Action = UnpackAction();
  private static final String Action_PACKED_0 =
    "\1\0\2\1\1\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17"+
    "\1\20\1\21\1\0\12\4\1\0\1\1\2\0\1\22"+
    "\1\4\1\23\1\24\1\25\1\26\2\4\1\27\2\4"+
    "\1\30\2\4\1\31\1\4\2\1\1\32\3\4\1\33"+
    "\7\4\1\34\1\35\5\4\1\36\1\37\2\4\1\40"+
    "\1\4\1\41\1\42";

  private static int [] UnpackAction() {
    int [] result = new int[79];
    int offset = 0;
    offset = UnpackAction(Action_PACKED_0, offset, result);
    return result;
  }

  private static int UnpackAction(String packed, int offset, int [] result) {
    int i = 0;
    int j = offset;
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  private static final int [] RowMap = UnpackRowMap();

  private static final String RowMap_PACKED_0 =
    "\0\0\0\52\0\124\0\176\0\250\0\322\0\124\0\374"+
    "\0\u0126\0\322\0\u0150\0\u0126\0\u0126\0\u0126\0\u0126\0\u0126"+
    "\0\u017a\0\u01a4\0\u01ce\0\u01f8\0\u0222\0\u024c\0\u0276\0\u02a0"+
    "\0\u02ca\0\u02f4\0\u031e\0\u0348\0\u0372\0\u039c\0\124\0\u03c6"+
    "\0\u03f0\0\374\0\u0126\0\u041a\0\u0126\0\u0126\0\u0126\0\u0126"+
    "\0\u0444\0\u046e\0\322\0\u0498\0\u04c2\0\322\0\u04ec\0\u0516"+
    "\0\322\0\u0540\0\u056a\0\u0126\0\u03f0\0\u0594\0\u05be\0\u05e8"+
    "\0\322\0\u0612\0\u063c\0\u0666\0\u0690\0\u06ba\0\u06e4\0\u070e"+
    "\0\322\0\322\0\u0738\0\u0762\0\u078c\0\u07b6\0\u07e0\0\322"+
    "\0\322\0\u080a\0\u0834\0\322\0\u085e\0\322\0\322";

  private static int [] UnpackRowMap() {
    int [] result = new int[79];
    int offset = 0;
    offset = UnpackRowMap(RowMap_PACKED_0, offset, result);
    return result;
  }

  private static int UnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;
    int j = offset;
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  private static final int [] _TRANS = UnpackTrans();

  private static final String _TRANS_PACKED_0 =
    "\1\0\1\2\3\3\1\4\1\5\1\6\1\7\1\0"+
    "\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17"+
    "\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\1\6\1\30\4\6\1\31\1\32\1\33\4\6\1\34"+
    "\1\35\1\36\1\0\1\37\1\3\2\37\1\0\1\5"+
    "\44\0\4\37\1\0\1\5\50\0\1\40\52\0\1\5"+
    "\2\0\1\41\46\0\2\6\4\0\2\6\11\0\23\6"+
    "\3\0\1\42\2\0\2\42\3\0\1\43\2\42\11\0"+
    "\23\42\60\0\2\6\4\0\2\6\11\0\6\6\1\44"+
    "\14\6\23\0\1\45\51\0\1\46\51\0\1\47\51\0"+
    "\1\50\34\0\2\6\4\0\2\6\11\0\1\6\1\51"+
    "\21\6\6\0\2\6\4\0\2\6\11\0\13\6\1\52"+
    "\7\6\6\0\2\6\4\0\2\6\11\0\1\6\1\53"+
    "\21\6\6\0\2\6\4\0\2\6\11\0\7\6\1\54"+
    "\13\6\6\0\2\6\4\0\2\6\11\0\15\6\1\55"+
    "\5\6\6\0\2\6\4\0\2\6\11\0\14\6\1\56"+
    "\6\6\6\0\2\6\4\0\2\6\11\0\16\6\1\57"+
    "\4\6\6\0\2\6\4\0\2\6\11\0\15\6\1\60"+
    "\5\6\6\0\2\6\4\0\2\6\11\0\2\6\1\61"+
    "\20\6\6\0\2\6\4\0\2\6\11\0\1\6\1\62"+
    "\21\6\1\40\1\63\1\64\47\40\6\0\1\65\51\0"+
    "\2\6\4\0\2\6\11\0\7\6\1\66\13\6\6\0"+
    "\2\6\4\0\2\6\11\0\2\6\1\67\20\6\6\0"+
    "\2\6\4\0\2\6\11\0\11\6\1\70\11\6\6\0"+
    "\2\6\4\0\2\6\11\0\21\6\1\71\1\6\6\0"+
    "\2\6\4\0\2\6\11\0\13\6\1\72\7\6\6\0"+
    "\2\6\4\0\2\6\11\0\17\6\1\73\3\6\6\0"+
    "\2\6\4\0\2\6\11\0\12\6\1\74\10\6\6\0"+
    "\2\6\4\0\2\6\11\0\12\6\1\75\10\6\2\0"+
    "\1\64\55\0\2\6\4\0\2\6\11\0\10\6\1\76"+
    "\12\6\6\0\2\6\4\0\2\6\11\0\3\6\1\77"+
    "\17\6\6\0\2\6\4\0\2\6\11\0\6\6\1\100"+
    "\14\6\6\0\2\6\4\0\2\6\11\0\7\6\1\101"+
    "\13\6\6\0\2\6\4\0\2\6\11\0\13\6\1\102"+
    "\7\6\6\0\2\6\4\0\2\6\11\0\16\6\1\103"+
    "\4\6\6\0\2\6\4\0\2\6\11\0\7\6\1\104"+
    "\13\6\6\0\2\6\4\0\2\6\11\0\11\6\1\105"+
    "\11\6\6\0\2\6\4\0\2\6\11\0\1\6\1\106"+
    "\21\6\6\0\2\6\4\0\2\6\11\0\1\6\1\107"+
    "\21\6\6\0\2\6\4\0\2\6\11\0\13\6\1\110"+
    "\7\6\6\0\2\6\4\0\2\6\11\0\11\6\1\111"+
    "\11\6\6\0\2\6\4\0\2\6\11\0\12\6\1\112"+
    "\10\6\6\0\2\6\4\0\2\6\11\0\4\6\1\113"+
    "\16\6\6\0\2\6\4\0\2\6\11\0\7\6\1\114"+
    "\13\6\6\0\2\6\4\0\2\6\11\0\2\6\1\115"+
    "\20\6\6\0\2\6\4\0\2\6\11\0\5\6\1\116"+
    "\15\6\6\0\2\6\4\0\2\6\11\0\7\6\1\117"+
    "\13\6";

  private static int [] UnpackTrans() {
    int [] result = new int[2184];
    int offset = 0;
    offset = UnpackTrans(_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int UnpackTrans(String packed, int offset, int [] result) {
    
    int j = offset; 
    int l = packed.length();
    for (int i = 0; i < l;) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  private static final int _UNKNOWN_ERROR = 0;
  private static final int _NO_MATCH = 1;
  private static final int _PUSHBACK_2BIG = 2;

  private static final String _ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  private static final int [] _ATTRIBUTE = UnpackAttribute();

  private static final String _ATTRIBUTE_PACKED_0 =
    "\1\0\7\1\1\11\2\1\5\11\3\1\1\0\12\1"+
    "\1\0\1\1\2\0\1\11\1\1\4\11\13\1\1\11"+
    "\33\1";

  private static int [] UnpackAttribute() {
    int [] result = new int[79];
    int offset = 0;
    offset = UnpackAttribute(_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int UnpackAttribute(String packed, int offset, int [] result) {    
    int j = offset;
    int l = packed.length();
    for(int i = 0;i < l;) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  private java.io.Reader Reader;
  private int State;
  private int LexicalState = Init;
  private char Buffer[] = new char[BuffSize];
  private int MarkedPos;
  private int CurrentPos;
  private int StartRead;
  private int EndRead;
  private int yyline;
  private int yychar;
  private int yycolumn;
  private boolean AtBOL = true;
  private boolean AtEOF;
  private boolean EOFDone;
  private int FinalHighSurrogate = 0;
static ArrayList<String> ids=new ArrayList<String>();

public static void main(String[] args) throws Exception
{
	Reader reader=new FileReader(args[0]);
	LexerFP lexer=new LexerFP(reader);
	lexer.yylex();
	System.out.println("The symbol table is:");
	SymbolTable stable = new SymbolTable(args[0]);
}

  public LexerFP(java.io.Reader in) {
    this.Reader = in;
  }

  private static char [] UnPckMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;
    int j = 0;
    while (i < 158) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }

  private boolean Refill() throws java.io.IOException {
    if (StartRead > 0) {
      EndRead += FinalHighSurrogate;
      FinalHighSurrogate = 0;
      System.arraycopy(Buffer, StartRead,
                       Buffer, 0,
                       EndRead-StartRead);
     
      EndRead-= StartRead;
      CurrentPos-= StartRead;
      MarkedPos-= StartRead;
      StartRead = 0;
    }

    if (CurrentPos >= Buffer.length - FinalHighSurrogate) {
      char newBuffer[] = new char[Buffer.length*2];
      System.arraycopy(Buffer, 0, newBuffer, 0, Buffer.length);
      Buffer = newBuffer;
      EndRead += FinalHighSurrogate;
      FinalHighSurrogate = 0;
    }

    int requested = Buffer.length - EndRead;
    int numRead = Reader.read(Buffer, EndRead, requested);

    if (numRead == 0) {
      throw new java.io.IOException("Reader returned 0 characters. See JFlex examples for workaround.");
    }
    if (numRead > 0) {
      EndRead += numRead;
      if (numRead == requested) {
        if (Character.isHighSurrogate(Buffer[EndRead - 1])) {
          --EndRead;
          FinalHighSurrogate = 1;
        }
      }
      return false;
    }
    return true;
  }

  public final void yyclose() throws java.io.IOException {
    AtEOF = true;            /* indicate end of file */
    EndRead = StartRead;  /* invalidate buffer    */

    if (Reader != null)
      Reader.close();
  }

  public final void yyreset(java.io.Reader reader) {
    Reader = reader;
    AtBOL  = true;
    AtEOF  = false;
    EOFDone = false;
    EndRead = StartRead = 0;
    CurrentPos = MarkedPos = 0;
    FinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    LexicalState = Init;
    if (Buffer.length > BuffSize)
      Buffer = new char[BuffSize];
  }

  public final int yystate() {
    return LexicalState;
  }

  public final void yybegin(int newState) {
    LexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( Buffer, StartRead, MarkedPos-StartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return Buffer[StartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return MarkedPos-StartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void ScanError(int errorCode) {
    String message;
    try {
      message = _ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = _ERROR_MSG[_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      ScanError(_PUSHBACK_2BIG);

    MarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Object yylex() throws java.io.IOException {
    int Input;
    int Action;

    // cached fields:
    int CurrentPosL;
    int MarkedPosL;
    int EndReadL = EndRead;
    char [] BufferL = Buffer;
    char [] CMapL = CMAP;

    int [] TransL = _TRANS;
    int [] RowMapL = RowMap;
    int [] AttrL = _ATTRIBUTE;

    while (true) {
      MarkedPosL = MarkedPos;

      boolean R = false;
      int Ch;
      int CharCount;
      for (CurrentPosL = StartRead  ;
           CurrentPosL < MarkedPosL ;
           CurrentPosL += CharCount ) {
        Ch = Character.codePointAt(BufferL, CurrentPosL, MarkedPosL);
        CharCount = Character.charCount(Ch);
        switch (Ch) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          R = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          R = true;
          break;
        case '\n':
          if (R)
            R = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          R = false;
          yycolumn += CharCount;
        }
      }

      if (R) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean Peek;
        if (MarkedPosL < EndReadL)
          Peek = BufferL[MarkedPosL] == '\n';
        else if (AtEOF)
          Peek = false;
        else {
          boolean eof = Refill();
          EndReadL = EndRead;
          MarkedPosL = MarkedPos;
          BufferL = Buffer;
          if (eof) 
            Peek = false;
          else 
            Peek = BufferL[MarkedPosL] == '\n';
        }
        if (Peek) yyline--;
      }
      Action = -1;

      CurrentPosL = CurrentPos = StartRead = MarkedPosL;
  
      State = LexState[LexicalState];

      // set up Action for empty match case:
      int Attributes = AttrL[State];
      if ( (Attributes & 1) == 1 ) {
        Action = State;
      }


      ForAction: {
        while (true) {
    
          if (CurrentPosL < EndReadL) {
            Input = Character.codePointAt(BufferL, CurrentPosL, EndReadL);
            CurrentPosL += Character.charCount(Input);
          }
          else if (AtEOF) {
            Input = EOF;
            break ForAction;
          }
          else {
            // store back cached positions
            CurrentPos  = CurrentPosL;
            MarkedPos   = MarkedPosL;
            boolean eof = Refill();
            // get translated positions and possibly new buffer
            CurrentPosL  = CurrentPos;
            MarkedPosL   = MarkedPos;
            BufferL      = Buffer;
            EndReadL     = EndRead;
            if (eof) {
              Input = EOF;
              break ForAction;
            }
            else {
              Input = Character.codePointAt(BufferL, CurrentPosL, EndReadL);
              CurrentPosL += Character.charCount(Input);
            }
          }
          int Next = TransL[ RowMapL[State] + CMapL[Input] ];
          if (Next == -1) break ForAction;
          State = Next;

          Attributes = AttrL[State];
          if ( (Attributes & 1) == 1 ) {
            Action = State;
            MarkedPosL = CurrentPosL;
            if ( (Attributes & 8) == 8 ) break ForAction;
          }

        }
      }

      // store back cached position
      MarkedPos = MarkedPosL;

      if (Input == EOF && StartRead == CurrentPos) {
        AtEOF = true;
        return null;
      }
      else {
        switch (Action) {
          case 1: 
            { /* ignore */
            }
          case 35: break;
          case 2: 
            { System.out.println("/");
            }
          case 36: break;
          case 3: 
            { System.out.println("integer: "+yytext());
            }
          case 37: break;
          case 4: 
            { System.out.println("identifer: "+yytext()); ids.add(yytext()+" ");
            }
          case 38: break;
          case 5: 
            { System.out.println("-");
            }
          case 39: break;
          case 6: 
            { System.out.println("(");
            }
          case 40: break;
          case 7: 
            { System.out.println(")");
            }
          case 41: break;
          case 8: 
            { System.out.println("Boolean: True");
            }
          case 42: break;
          case 9: 
            { System.out.println("Boolean: False");
            }
          case 43: break;
          case 10: 
            { System.out.println("{"); ids.add("{");
            }
          case 44: break;
          case 11: 
            { System.out.println("}"); ids.add("}");
            }
          case 45: break;
          case 12: 
            { System.out.println("+");
            }
          case 46: break;
          case 13: 
            { System.out.println("*");
            }
          case 47: break;
          case 14: 
            { System.out.println("%");
            }
          case 48: break;
          case 15: 
            { System.out.println("=");
            }
          case 49: break;
          case 16: 
            { System.out.println("<");
            }
          case 50: break;
          case 17: 
            { System.out.println(">");
            }
          case 51: break;
          case 18: 
            { System.out.println("character-string: "+yytext());
            }
          case 52: break;
          case 19: 
            { System.out.println("==");
            }
          case 53: break;
          case 20: 
            { System.out.println("<=");
            }
          case 54: break;
          case 21: 
            { System.out.println(">=");
            }
          case 55: break;
          case 22: 
            { System.out.println("!=");
            }
          case 56: break;
          case 23: 
            { System.out.println("or");
            }
          case 57: break;
          case 24: 
            { System.out.println("if");
            }
          case 58: break;
          case 25: 
            { System.out.println("do");
            }
          case 59: break;
          case 26: 
            { System.out.println("float: "+yytext());
            }
          case 60: break;
          case 27: 
            { System.out.println("and");
            }
          case 61: break;
          case 28: 
            { System.out.println("then");
            }
          case 62: break;
          case 29: 
            { System.out.println("else");
            }
          case 63: break;
          case 30: 
            { System.out.println("while");
            }
          case 64: break;
          case 31: 
            { System.out.println("print");
            }
          case 65: break;
          case 32: 
            { System.out.println("return");
            }
          case 66: break;
          case 33: 
            { System.out.println("Program");
            }
          case 67: break;
          case 34: 
            { System.out.println("Function");
            }
          case 68: break;
          default:
            ScanError(_NO_MATCH);
        }
      }
    }
  }


}
