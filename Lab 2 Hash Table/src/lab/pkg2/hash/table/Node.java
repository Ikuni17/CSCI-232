/* 
* Bradley White
* Lab 2: Hash Table
* CSCI 232
* February 22, 2016
*/

package lab.pkg2.hash.table;

class Node
{
	public int iData; // data item (key value)
	public Node leftChild; // this node's left child
	public Node rightChild; // this node's right child
	public void displayNode() // display ourself
	{
		System.out.print('{');
		System.out.print(iData);
		System.out.print(", ");
		System.out.print("} ");
	}
} // end class Node
