
package compilerpraj;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class CompilerPraj {


    public static void main(String[] args) {

    HashMap<Integer,String> symbolTable=new HashMap<Integer,String>();
    Scanner sc = new Scanner(System.in);
    String input=sc.nextLine();
    // now using just multi space as delimiter 
    String[] starray = input.split("\\s");
    // later we can use this piece of code 
    /*
     String delim = " \n\r\t,.;"; //insert here all delimitators
 StringTokenizer st = new StringTokenizer(message,delim);
 while (st.hasMoreTokens()) {
     System.out.println(st.nextToken());
 }
    */
     display(starray);
    
    }
    
    public static boolean insert(String identifier){
     
        
        return true;
    }
    
    
  public static void display(String[] starray){
      System.out.println("Display function");
      System.out.println(Arrays.toString(starray));  
       
    }
    
    public static boolean find_in_current_scope_1(String identifier){
    
        return true;
    }
    
    public static boolean find_in_all_scopes_2(String identifier){
    
        return true;
    }
    
}
