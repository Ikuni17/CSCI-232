/* 
* Bradley White
* Lab 1: Huffman Code
* CSCI 232
* February 7, 2016
*/

package lab_1.huffman.code;

public class Node
{
        public char character;          // character for this node
        public int freq;                // frequency from table or summation of children
	public Node leftChild;          // this node's left child
	public Node rightChild;         // this node's right child
        public String codeTable = "";   // this node's code
	public void displayNode()       // display fields of this node
	{
		System.out.print('{');
		System.out.print(character);
		System.out.print(", ");
		System.out.print(freq);
		System.out.print("} ");
	}
} // end class Node
