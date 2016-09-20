/* 
 * Bradley White
 * Lab 2: Hash Table
 * CSCI 232
 * February 22, 2016
 */
package lab.pkg2.hash.table;

public class HashTable {

    // Array of trees for seperate chaining the hash table to avoid collisions
    public Tree[] array;
    // User defined hash table size
    public int arraySize;

    // Constructor to initialize variables
    public HashTable(int size) {
        arraySize = size;
        array = new Tree[arraySize];
    }

    // Display all key values within the hash table and their corresponding index
    public void display() {
        for (int i = 0; i < array.length; i++) {
            // Check that the index is not null
            if (array[i] != null) {
                System.out.print(i + ". ");
                // Call in order traversal from Tree class for each tree
                array[i].inOrder(array[i].root);
                System.out.println();
            }
        }
    }

    // Insert a new key value into the hash table
    public void insert(int key) {
        // Call hash function to get correct index
        int index;
        index = hashFunction(key);

        // Create a new tree if the index is null, otherwise insert into the tree
        if (array[index] == null) {
            Tree tempTree = new Tree();
            tempTree.insert(key);
            array[index] = tempTree;
        } else {
            array[index].insert(key);
        }
    }

    // Search the hash table for a specific key value and return true if found
    public boolean find(int key) {
        boolean check = false;
        // Call hash function to get correct index
        int index;
        index = hashFunction(key);

        // Check if the index is null first, a key value cannot exist if the index is null
        if (array[index] != null) {
            // Use the tree class find method and return true if the key value is found
            Node found = array[index].find(key);
            if (found != null) {
                check = true;
            }
        }
        return check;
    }

    // Remove a specific key value from the hash table
    public void delete(int key) {
        // Call hash function to get correct index
        int index;
        index = hashFunction(key);

        // Call delete method from the Tree class to handle deleting nodes
        array[index].delete(key);
        // If the tree is empty, make the index null to avoid null pointer exceptions
        if (array[index].root == null) {
            array[index] = null;
        }
    }

    // Helper method to calculate the correct index for a specific key
    public int hashFunction(int key) {
        int index;
        index = (key % arraySize);
        return index;
    }
}
