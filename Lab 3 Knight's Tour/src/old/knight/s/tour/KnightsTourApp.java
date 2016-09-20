/* 
* Bradley White
* Lab 3: Knight's Tour
* CSCI 232
* March 23, 2016
 */
// NetBeans package
package old.knight.s.tour;

import java.io.*;

public class KnightsTourApp {

    public static void main(String[] args) throws IOException {
        // Variables for user input
        int boardSize = 0, startingPos = 0;

        // Prompt the user and store the results
        System.out.print("Enter board size (8 for 8x8 board): ");
        boardSize = getInt();
        System.out.format("Enter the beginning square (1 to %d): ", (boardSize * boardSize));
        startingPos = getInt();
        // Create a new board based on user specifications
        Board board = new Board(boardSize, startingPos);
    }

    // Retreive the user input
    public static String getString() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }

    // Convert the user input into an int
    public static int getInt() throws IOException {
        String s = getString();
        return Integer.parseInt(s);
    }
}
