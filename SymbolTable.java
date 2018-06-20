/* Author Prajna */
package compilerpraj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;

public class SymbolTable {

    public static int num_st[] = new int[100];// Symbol Table Dynamic Arrays 
    public static int Count;
	public SymbolTable(String filename)throws Exception{
	HashMap<Integer, LinkedList<String>> tMap = new HashMap<Integer, LinkedList<String>>();
        LinkedList linked_lists_created[] = new LinkedList[5];
        for (int i = 0; i < 5; i++) {
            linked_lists_created[i] = new LinkedList();
        }
        for (int i = 0; i < 100; i++) {
            num_st[i] = 1;
        }
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String cur_string;
        while ((cur_string = br.readLine()) != null) {
		
			cur_string=cur_string.replaceAll("\\{","{ ");
			cur_string=cur_string.replaceAll("\\}"," }");
            String split_found[] = ((cur_string.replaceAll("(?://.*)|(/\\*(?:.|[\\n\\r])*?\\*/)", "")).trim()).split(" ");
            for (String t : split_found) {
                int Count = find_current_scope(t);
                if (t.equals("{") || t.equals("OPEN") || t.equals("}") || t.equals("CLOSE")) {

                } else {
					
                    char cur_stringn[] = t.toCharArray();
                    int te = 0;
                    for (char c : cur_stringn) {
                        te += (int) c;
                    }
                    te = te % 5;
					String test="[-]*[0-9]*";
					if(t.matches(test)){
					}
                   else if (find_all_scope(t, Count, te, linked_lists_created, tMap)) {
                        insert(t, Count, te, linked_lists_created, tMap);
                    }
                }
            }
        }
        display(tMap);
    
		
	}
    public static void insert(String InString, int Count, int te, LinkedList ll[], HashMap tMap) {
        ll[te].add(InString + ":" + Count);
        tMap.put(te, ll[te]);
    }

	public static void display(HashMap hm) {
        for (int i = 0; i < 5; i++) {
            LinkedList ll = new LinkedList();
            ll = (LinkedList) hm.get(i);
            if(ll==null){
                System.out.println("BLOCK"+i+"::"+"0");
                break;
            }
            System.out.print("BLOCK" + i + ":: ");
            for (Object ii : ll) {
               
                System.out.print(" --> " + (ii.toString()).replaceAll("[{}()]",""));
            }
            System.out.println();
        }
    }
	
    public static boolean find_all_scope(String InString, int Count, int te, LinkedList ll[], HashMap tMap) {
        int ctr = 1;
        for (int i = 0; i < 5; i++) {
            LinkedList<String> lis = new LinkedList();
            lis = (LinkedList) tMap.get(i);
            if (lis == null) {
                return true;
            }
			InString.replaceAll("[{}()]","");
            for (int j = 0; j < lis.size(); j++) {
                String cur_string = lis.get(j).toString();
                if (cur_string.equals(InString + ":" + Count) ||(InString==" ")) {
                    ctr = 0;
                    break;
                } else {
                    ctr = 1;
                }
            }
            if (ctr == 0) {
                return false;
            }
        }
        if (ctr == 1) {
            return true;
        }
        return false;

    }

    public static int find_current_scope(String t) {
        if (t.contains("{") || t.contains("OPEN")) {
            Count++;
            while ((num_st[Count] == 0)) {
                Count++;
            }
            if (num_st[Count] == 0) {
                Count++;
            }
        } else if (t.contains("}") || t.contains("CLOSE")) {
            num_st[Count] = 0;
            Count--;
			
            while (num_st[Count] == 0) {
                if (Count < 0) {
                    System.out.println("the code input doesn't have a balanced Brackets");
                } else {
                    Count--;
                }
            }		
        }
        return (Count);
    }   
}
