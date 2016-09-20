/* 
* Bradley White
* Lab 1: Huffman Code
* CSCI 232
* February 7, 2016
*/

package lab_1.huffman.code;

public class Huffman {

    int maxSize = 29;       // maximum size for various parameters, equal to the amount of ASCII characters
    int whiteSpace = 2;     // constant for white space between inputs
    public String inputString, upperCase, encodeString = "", decodeString = "";
    public int[] freqTable = new int[maxSize];           // array to hold the frequency of each character
    public String[] codeTable = new String[maxSize];     // array to hold the code for each character
    public Tree huffTree;                                // final tree based on frequency of each character or parent node
    public PriorityQ theQ = new PriorityQ(maxSize);      // priority queue sorted by frequency

    // constructor
    public Huffman(String str) {
        inputString = str;      // user input string from HuffmanApp

        // initialize te frequency table with zeros
        for (int i = 0; i < maxSize; i++) {
            freqTable[i] = 0;
        }

        // call helper methods
        upperCase = convertString(inputString);
        makeFreqTable();
        queueTree();
        makeHuffmanTree();
    }

    // replaces special characters, then converts the user input to upper case and returns a new string
    public String convertString(String input) {
        String answer;

        answer = input.replace(' ', '[');
        answer = answer.replace('\n', '\\');
        answer = answer.replace('\r', ']');
        answer = answer.toUpperCase();
        System.out.println(answer);

        return answer;
    }

    // counts the frequency of each letter and populates the frequency table with the value
    public void makeFreqTable() {
        char c, alpha;
        int index;
        
        // iterate through the upper case string and increase count for each character
        for (int i = 0; i < upperCase.length(); i++) {
            c = upperCase.charAt(i);
            index = (int) c;             // convert character to ASCII index
            freqTable[index - 65]++;     // increase count for relevant index, A=0, B=1,...
        }

        // print the alphabet and special characters
        for (alpha = 'A'; alpha <= 'Z'; alpha++) {
            System.out.print(alpha + " ");
        }
        System.out.print("[ \\ ]\n");

        // print the frequency of each character underneath its position in output
        for (int i = 0; i < maxSize; i++) {
            System.out.print(freqTable[i] + " ");
        }

        // add whitespace before next input
        for (int i = 0; i < whiteSpace; i++) {
            System.out.println();
        }
    }

    // call display tree within Tree class to print the tree structure including character and frequency
    public void displayTree() {
        huffTree.displayTree();

        // add whitepsace before next input
        for (int i = 0; i < whiteSpace; i++) {
            System.out.println();
        }
    }

    // lookup code in code table and insert into encode string then print
    public void code() {
        // call helper methods
        makeCodes(huffTree.root);
        makeCodeTable(huffTree.root);
        printCodeTable();
        System.out.println("Coded message:");

        // character arithmatic to find the index relative to each character in the code table and build string
        for (int i = 0; i < upperCase.length(); i++) {
            char c;
            int index;
            c = upperCase.charAt(i);
            index = (int) c;
            encodeString = encodeString + codeTable[index - 65];
        }

        System.out.println(encodeString);

        // add whitespace before next input
        for (int i = 0; i < whiteSpace; i++) {
            System.out.println();
        }
    }

    // traverse tree based on code in encode string and append letter then print string
    public void decode() {
        System.out.println("Decoded message:");
        Node currentNode = huffTree.root;

        // iterate through each character in the encode string
        for (int i = 0; i < encodeString.length(); i++) {
            char c;
            c = encodeString.charAt(i);

            // if read a zero traverse left in hufftree
            if (c == '0') {
                currentNode = currentNode.leftChild;
            } 
            // if read a one traverse right in huffTree
            else if (c == '1') {
                currentNode = currentNode.rightChild;
            }

            // check if at a leaf node and append character if so then return to the root
            if (currentNode.leftChild == null && currentNode.rightChild == null) {;
                decodeString = decodeString + currentNode.character;
                currentNode = huffTree.root;
            }

        }

        System.out.println(decodeString);

        // add whitespace before next input
        for (int i = 0; i < whiteSpace; i++) {
            System.out.println();
        }
    }

    // Make a tree for each item in freqTable and insert into priorityQ
    public void queueTree() {

        for (int i = 0; i < maxSize; i++) {
            // check if the character has a frequency
            if (freqTable[i] != 0) {
                Tree queTree = new Tree();
                queTree.insert(((char) (i + 65)), freqTable[i]);
                theQ.insert(queTree);
            }
        }
    }

    // Combine trees and insert back into priorityQ
    public void makeHuffmanTree() {

        // loop until only one tree is in the priority queue
        while (theQ.size() > 1) {
            Tree firstTree, secondTree, parentTree = new Tree();
            
            // remove the first two trees with lowest frequency
            firstTree = theQ.remove();
            secondTree = theQ.remove();

            // create a new tree with frequency of the first two trees
            parentTree.insert('+', (firstTree.root.freq + secondTree.root.freq));
            
            // place the first two trees as the leaves
            parentTree.root.leftChild = firstTree.root;
            parentTree.root.rightChild = secondTree.root;
            
            // insert back into the priority queue
            theQ.insert(parentTree);
        }
        
        // remove the complete tree
        huffTree = theQ.remove();
    }

    // Use recursion to traverse the tree to get the code for each letter
    public void makeCodes(Node currentNode) {
        
        // if not at a leaf node keep traversing and adding codes to children
        if (currentNode.leftChild != null) {
            currentNode.leftChild.codeTable = currentNode.codeTable + "0";
            makeCodes(currentNode.leftChild);

            currentNode.rightChild.codeTable = currentNode.codeTable + "1";
            makeCodes(currentNode.rightChild);
        }
    }

    // In order traversal of the tree reading codes from nodes and initializing the code table
    public void makeCodeTable(Node localRoot) {
        if (localRoot != null) {
            makeCodeTable(localRoot.leftChild);
            
            // if the node is not a parent node
            if (localRoot.character != '+') {
                // ASCII table index to populate relevant index in the code table, A=0, B=1...
                codeTable[(int) localRoot.character - 65] = localRoot.codeTable;
            }
            makeCodeTable(localRoot.rightChild);
        }
    }

    // prints each character that has a frequency code and it's code
    public void printCodeTable() {
        for (int i = 0; i < maxSize; i++) {
            // check that character has a code
            if (codeTable[i] != null) {
                // character arithmatic for ASCII character and relevant code, A=65,B=66...
                System.out.println(((char) (i + 65)) + " " + codeTable[i]);
            }
        }
    }
}
