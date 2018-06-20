package compilerpraj;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Stack;



public class HashTablewithLinkedList {

	LinkedListNode[] hashtab;
	int hashtabsize;

	public HashTablewithLinkedList(int hashindex) {
		hashtab = new LinkedListNode[hashindex];
		hashtabsize = 0;
	}

	public void insert(int charsum, String str, int blocknum){
		
		hashtabsize++;
		int index = hashindexcal(charsum);
		/* used earlier for checking the current scope but now called the function itself in main before inserting
		LinkedListNode first = hashtab[index];
		while (first != null)
		{
			if (first.strpart.equals(str) & first.inblocknum == blocknum) {
				System.out.println("Identifier "+first.strpart+" already exist for this block");
				return;
			}
			first = first.next;
		}
		 */
		LinkedListNode newlink = new LinkedListNode(str, blocknum);

		if (hashtab[index] == null)
		{
			
			hashtab[index] = newlink;
		} 
		else
		{
			// if collision adding the new element at the start and pointing others accordingly
			newlink.next = hashtab[index];
			hashtab[index] = newlink;

		}

	}

	private int hashindexcal(int charsum) {
		int hashindexval = charsum % 5; 
		return hashindexval;

	}

	public void DisplayHashTable() {
		System.out.println("Hash Table is");
		for (int i = 0; i < 5; i++)
		{
			System.out.print("Index " + i + ":");
			LinkedListNode first = hashtab[i];
			while (first != null)
			{
				System.out.print("[" + first.strpart + "," + first.inblocknum + "] ");
				first = first.next;
			}
			System.out.println();
		}

	}
	public void SymbolTableDump(int block ) throws FileNotFoundException{
		System.out.println("SYMBOL TABLE DUMP");
                //PrintStream o = new PrintStream(new File("C:\\Users\\prajn\\CSUEB\\Compiler Design\\Project\\fptokens.txt"));
		for(int j=1;j<=block;j++)
		{
			//System.out.print("Scope "+j+": " );
		for(int i = 0; i < 5; i++)
		{
			LinkedListNode first = hashtab[i];
			while (first != null)
			{
				
				//System.out.print("Block numbers are "+block+","+ first.inblocknum);
				if(j ==first.inblocknum )
				{
                                //System.setOut(System.out);
				//System.out.print(first.strpart+" " );
                                //System.setOut(o);
                                System.out.println(first.strpart+ " in Scope "+j);
                                
				}
				first = first.next;
			}
			
		}
		System.out.println();
		}
	}
	public void Find_In_All_Scopes(String str)
	{
		Stack<Integer> stackforfind = new Stack<Integer>();
		boolean foundNotFound = false;
			
		for(int i = 0; i < 5; i++)
		{
			LinkedListNode first = hashtab[i];
			while (first != null)
			{
				if(first.strpart.equals(str) )
				{
					foundNotFound = true;
					stackforfind.push(first.inblocknum);
				
				}
				first = first.next;
			}
			
		}
		
		System.out.println();
		if(foundNotFound)
		{
			System.out.print("Identifier "+str+" is found in blocks : ");
			while(!stackforfind.isEmpty())
			   {
			      System.out.print(stackforfind.peek()+" ");
			      stackforfind.pop();
			   }
			System.out.println();

		}
		else
			
			System.out.println("Identifier "+str+" not found in any scope");
		
		
	}
	public boolean Find_In_Current_Scope(String str, int blknum)
	{
		//boolean foundNotFoundCurr = false;
			
		for(int i = 0; i < 5; i++)
		{
			LinkedListNode first = hashtab[i];
			while (first != null)
			{
				if(first.strpart.equals(str) & first.inblocknum == blknum )
				{
					//foundNotFoundCurr = true;
					System.out.println("Identifier "+str+" is found in block : "+blknum);
					return true;
				
				}
				first = first.next;
			}
			
		}
		//System.out.println("String "+str+" is not found in Scope "+blknum);
		
		return false;
	}
}
