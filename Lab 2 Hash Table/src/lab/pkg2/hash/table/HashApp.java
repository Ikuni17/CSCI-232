/* 
* Bradley White
* Lab 2: Hash Table
* CSCI 232
* February 22, 2016
 */
package lab.pkg2.hash.table;

import java.io.*;
import java.util.*;

public class HashApp {

    public static void main(String[] args) throws IOException {

        // Various user input values
        int value, arraySize, numItems;
        // Check for null pointers
        boolean check;
        // Make sure the key values get initialized
        boolean haveKeys = false;
        // API to generate random numbers if needed
        Random numGen = new Random();
        // Store the random or user input key values
        int[] numbers;

        // Initialize the size of the hash table, and create object
        System.out.print("Enter size of hash table: ");
        arraySize = getInt();
        HashTable theHash = new HashTable(arraySize);

        // Initialize the amount of key values to store in the hash table
        System.out.print("Enter initial number of items: ");
        numItems = getInt();
        numbers = new int[numItems];

        // Loop until key values are initialized in the numbers array
        while (!haveKeys) {
            // Ask the user if they want to randomly generate key values or input them
            System.out.print("How to initialize the key values? Enter first letter of random or input: ");
            int initialize = getChar();

            switch (initialize) {
                // Randomly initialize the numbers array
                case 'r':
                    // Populate the entire array
                    for (int i : numbers) {
                        // Use an upper bound of 100 for generating random numbers into the array
                        numbers[i] = numGen.nextInt(100);
                        // Check that the key value is not a duplicate before inserting into the hash table
                        check = theHash.find(numbers[i]);
                        if (check == false) {
                            theHash.insert(numbers[i]);
                        }
                    }
                    // Break the switch and exit the while loop
                    haveKeys = true;
                    break;
                // Allow the user to enter space seperated integers
                case 'i':
                    // Prompt the user and store the input
                    System.out.print("Enter " + numItems + " integers seperated by spaces: ");
                    String input = getString();
                    // Tokenize the string, seperated by spaces
                    StringTokenizer strToken = new StringTokenizer(input);

                    // Populate the entire array
                    for (int i : numbers) {
                        // Convert each token to an integer than insert into the array
                        numbers[i] = Integer.parseInt((String) strToken.nextElement());
                        // Check that the key value is not a duplicate before inserting into the hash table
                        check = theHash.find(numbers[i]);
                        if (check == false) {
                            theHash.insert(numbers[i]);
                        }
                    }
                    // Break the switch and exit the while loop
                    haveKeys = true;
                    break;
                default:
                    System.out.print("Invalid entry\n");
            }
        }
        
        // Infinite loop
        while (true) {
            // Prompt the user to choose a function, then call helper in HashTable class
            System.out.print("Enter first letter of show, ");
            System.out.print("insert, find, or delete: ");
            int choice = getChar();
            
            switch (choice) {
                // Display all key values in the hash table
                case 's':
                    theHash.display();
                    break;
                // Insert a new key value into the hash table
                case 'i':
                    System.out.print("Enter key value to insert: ");
                    value = getInt();
                    // Check that the key value is not a duplicate before inserting into the hash table
                    check = theHash.find(value);
                    if (check == true) {
                        System.out.println(value + " is a duplicate key value, try again");
                    } else {
                        theHash.insert(value);
                    }
                    break;
                // Search the hash table for a specific key value
                case 'f':
                    System.out.print("Enter key value to find: ");
                    value = getInt();
                    
                    check = theHash.find(value);
                    if (check == true) {
                        System.out.println("Found " + value);
                    } else {
                        System.out.println("Could not find " + value);
                    }
                    break;
                // Delete a specifc key value from the hash table
                case 'd':
                    System.out.print("Enter key value to delete: ");
                    value = getInt();
                    // Check that the key value exists in the hash table before attempting deletion
                    check = theHash.find(value);
                    if (check == false) {
                        System.out.println(value + " is not in the hash table");
                    } else {
                        theHash.delete(value);
                    }
                    break;
                default:
                    System.out.print("Invalid entry\n");
            } // end switch
        } // end while
    } // end main()
    // -------------------------------------------------------------

    public static String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }
    // -------------------------------------------------------------

    public static char getChar() throws IOException {
        String s = getString();
        return s.charAt(0);
    }
    //-------------------------------------------------------------

    public static int getInt() throws IOException {
        String s = getString();
        return Integer.parseInt(s);
    }
    // -------------------------------------------------------------
}
