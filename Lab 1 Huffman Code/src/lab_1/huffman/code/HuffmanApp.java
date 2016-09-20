/* 
* Bradley White
* Lab 1: Huffman Code
* CSCI 232
* February 7, 2016
*/

package lab_1.huffman.code;

import java.io.*;

public class HuffmanApp {

	public static void main(String[] args) throws IOException
	{
		Huffman huff = null;
		int value;
		String str;

		while(true)
		{
			System.out.print("Enter first letter of ");
			System.out.print("enter, show, code, or decode: ");
			int choice = getChar();
			switch(choice)
			{
			case 'e':
				System.out.println(
						"Enter text lines, terminate with $");
				str = getText();
				
                                huff = new Huffman(str);
				break;
			case 's':
				huff.displayTree();
				break;
			case 'c':
				huff.code();
				break;
			case 'd':
				huff.decode();
				break;
			default:
				System.out.print("Invalid entry\n");
			}  // end switch
		}  // end while
	}  // end main()
	// -------------------------------------------------------------
	public static String getString() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
	// -------------------------------------------------------------
	public static String getText() throws IOException
	{
		String outStr="", str = "";
		while(true)
		{
			str = getString();
			if( str.equals("$") )
				return outStr;
			outStr = outStr + str + "\n";
		}
	}  // end getText()
	// -------------------------------------------------------------
	public static char getChar() throws IOException
	{
		String s = getString();
		return s.charAt(0);
	}
	//-------------------------------------------------------------
	public static int getInt() throws IOException
	{
		String s = getString();
		return Integer.parseInt(s);
	}
}
