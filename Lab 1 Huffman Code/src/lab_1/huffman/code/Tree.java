/* 
* Bradley White
* Lab 1: Huffman Code
* CSCI 232
* February 7, 2016
*/

package lab_1.huffman.code;

import java.util.Stack;

public class Tree {

    public Node root; // first node of tree
    // -------------------------------------------------------------

    public Tree() // constructor
    {
        root = null;
    } // no nodes in tree yet
    //-------------------------------------------------------------

    public Node find(int key) // find node with given key
    { // (assumes non-empty tree)
        Node current = root; // start at root
        while (current.freq != key) // while no match,
        {
            if (key < current.freq) // go left?
            {
                current = current.leftChild;
            } else // or go right?
            {
                current = current.rightChild;
            }
            if (current == null) // if no child,
            {
                return null; // didn't find it
            }
        }
        return current; // found it
    } // end find()
    // -------------------------------------------------------------

    public void insert(char id, int dd) {
        Node newNode = new Node(); // make new node
        newNode.character = id; // insert data
        newNode.freq = dd;
        if (root == null) // no node in root
        {
            root = newNode;
        } else // root occupied
        {
            Node current = root; // start at root
            Node parent;
            while (true) // (exits internally)
            {
                parent = current;
                if (id < current.freq) // go left?
                {
                    current = current.leftChild;
                    if (current == null) // if end of the line,
                    { // insert on left
                        parent.leftChild = newNode;
                        return;
                    }
                } // end if go left
                else // or go right?
                {
                    current = current.rightChild;
                    if (current == null) // if end of the line
                    { // insert on right
                        parent.rightChild = newNode;
                        return;
                    }
                } // end else go right
            } // end while
        } // end else not root
    } // end insert()
    // -------------------------------------------------------------

    public boolean delete(int key) // delete node with given key
    { // (assumes non-empty list)
        Node current = root;
        Node parent = root;
        boolean isLeftChild = true;
        while (current.freq != key) // search for node
        {
            parent = current;
            if (key < current.freq) // go left?
            {
                isLeftChild = true;
                current = current.leftChild;
            } else // or go right?
            {
                isLeftChild = false;
                current = current.rightChild;
            }
            if (current == null) // end of the line,
            {
                return false; // didn't find it
            }
        } // end while
        // found node to delete
        // if no children, simply delete it
        if (current.leftChild == null
                && current.rightChild == null) {
            if (current == root) // if root,
            {
                root = null; // tree is empty
            } else if (isLeftChild) {
                parent.leftChild = null; // disconnect
            } else // from parent
            {
                parent.rightChild = null;
            }
        } // if no right child, replace with left subtree
        else if (current.rightChild == null) {
            if (current == root) {
                root = current.leftChild;
            } else if (isLeftChild) {
                parent.leftChild = current.leftChild;
            } else {
                parent.rightChild = current.leftChild;
            }
        } // if no left child, replace with right subtree
        else if (current.leftChild == null) {
            if (current == root) {
                root = current.rightChild;
            } else if (isLeftChild) {
                parent.leftChild = current.rightChild;
            } else {
                parent.rightChild = current.rightChild;
            }
        } else // two children, so replace with inorder successor
        {
            // get successor of node to delete (current)
            Node successor = getSuccessor(current);
            // connect parent of current to successor instead
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.leftChild = successor;
            } else {
                parent.rightChild = successor;
            }
            // connect successor to current's left child
            successor.leftChild = current.leftChild;
        } // end else two children
        // (successor cannot have a left child)
        return true; // success
    } // end delete()
    // -------------------------------------------------------------
    // returns node with next-highest value after delNode
    // goes to right child, then right child's left descendents

    private Node getSuccessor(Node delNode) {
        Node successorParent = delNode;
        Node successor = delNode;
        Node current = delNode.rightChild; // go to right child
        while (current != null) // until no more
        { // left children,
            successorParent = successor;
            successor = current;
            current = current.leftChild; // go to left child
        }
        // if successor not
        if (successor != delNode.rightChild) // right child,
        { // make connections
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = delNode.rightChild;
        }
        return successor;
    }

    // -------------------------------------------------------------

    public void traverse(int traverseType) {
        switch (traverseType) {
            case 1:
                System.out.print("\nPreorder traversal: ");
                preOrder(root);
                break;
            case 2:
                System.out.print("\nInorder traversal: ");
                inOrder(root);
                break;
            case 3:
                System.out.print("\nPostorder traversal: ");
                postOrder(root);
                break;
        }
        System.out.println();
    }

    // -------------------------------------------------------------

    public void preOrder(Node localRoot) {
        if (localRoot != null) {
            System.out.println(localRoot.character + "," + localRoot.freq + "," + localRoot.codeTable);
            preOrder(localRoot.leftChild);
            preOrder(localRoot.rightChild);
        }
    }
	// -------------------------------------------------------------

    public void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.leftChild);
            System.out.println(localRoot.character + "," + localRoot.freq + "," + localRoot.codeTable);
            inOrder(localRoot.rightChild);
        }
    }

    // -------------------------------------------------------------

    public void postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.leftChild);
            postOrder(localRoot.rightChild);
            System.out.println(localRoot.character + "," + localRoot.freq + "," + localRoot.codeTable);
        }
    }

    // -------------------------------------------------------------

    public void displayTree() {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 32;
        boolean isRowEmpty = false;
        System.out.println(
                "......................................................");
        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int j = 0; j < nBlanks; j++) {
                System.out.print(' ');
            }
            while (globalStack.isEmpty() == false) {
                Node temp = (Node) globalStack.pop();
                if (temp != null) {
                    System.out.print(temp.character);
                    System.out.print(temp.freq);
                    localStack.push(temp.leftChild);
                    localStack.push(temp.rightChild);
                    if (temp.leftChild != null
                            || temp.rightChild != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    System.out.print(' ');
                }
            } // end while globalStack not empty
            System.out.println();
            nBlanks /= 2;
            while (localStack.isEmpty() == false) {
                globalStack.push(localStack.pop());
            }
        } // end while isRowEmpty is false
        System.out.println(
                "......................................................");
    } // end displayTree()
    // -------------------------------------------------------------

    public int getFreq() {
        return root.freq;
    }
} // end class Tree
