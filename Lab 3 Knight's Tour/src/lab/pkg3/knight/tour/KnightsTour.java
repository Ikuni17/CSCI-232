/* 
* Bradley White
* Lab 3: Knight's Tour
* CSCI 232
* April 4, 2016
 */
// NetBeans package
//package lab.pkg3.knight.tour;

import java.io.*;
import java.util.*;

public class KnightsTour {

    // dimension represents one side of the board, boardSize represents all spaces (64 for 8x8)
    private static int dimension, boardSize, totalMoves = 0, startingPos;
    // Two dimensional array to represent the board
    private static int board[][];
    // Stack for keeping track of the path
    private static Deque<Integer> knightStack = new ArrayDeque<>(boardSize);
    // Used to print results in the correct format
    private static Boolean solutionFound = false;
    // All possible Knight moves (x, y), checked in anti-clockwise order
    private static final int[][] moves
            = {{+2, +1}, {+1, +2}, {-1, +2}, {-2, +1},
            {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};

    public static void main(String[] args) throws IOException {
        // Variables to calculate the starting position
        int startingRow = 0, startingColumn = 0;

        // Prompt the user and store the results
        System.out.print("Enter board size (8 for 8x8 board): ");
        dimension = getInt();
        boardSize = dimension * dimension;
        System.out.format("Enter the beginning square (1 to %d): ", boardSize);
        startingPos = getInt();

        // Initialize the stack, push the starting position, and update move counter        
        knightStack = new ArrayDeque<>(boardSize);
        knightStack.push(startingPos);
        totalMoves++;

        // Translate starting position to (x,y) coordinates, then call helper method
        startingRow = (startingPos - 1) % dimension;
        startingColumn = (startingPos - 1) / dimension;
        startTour(startingRow, startingColumn);
    }

    // Retrieve the user input
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

    // Helper method to determine if a destination move is on the board and unvisited
    private static boolean isValid(int row, int col) {
        return ((row >= 0 && row < dimension)
                && (col >= 0 && col < dimension)
                && (board[row][col] == -1));
    }

    // Display results
    private static void printResults() {
        // Determine if a solution was found
        if (solutionFound == true) {
            System.out.println("SUCCESS:");
        } else {
            System.out.println("FAILURE:");
        }
        System.out.println("Total Number of Moves=" + totalMoves);
        System.out.print("Moving Sequence:");
        // Print the path, poping from the bottom of the stack to produce the correct order
        while (knightStack.size() > 1) {
            System.out.print(" " + knightStack.removeLast());
        }
        System.out.print(" (" + knightStack.removeLast() + ")");
        System.out.println();
    }

    // Helper method to set every vertex on the board to unvisited
    public static void initializeBoard() {
        // Initialize the size of the board
        board = new int[dimension][dimension];
        // Set all vertices to unvisited (-1)
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                board[row][col] = -1;
            }
        }
    }

    // Helper method to start solving the tour from the starting position
    public static void startTour(int startingRow, int startingColumn) {
        initializeBoard();

        // Set the starting vertex to visited
        board[startingRow][startingColumn] = 1;
        
        // Call recursive method to solve the tour
        if (solveTour(startingRow, startingColumn)) {
            // If it returns true a path was found
            solutionFound = true;
            printResults();
        } 
        // Otherwise no solution
        else {
            printResults();
        }
    }

    // Recursive method to solve the tour by backtracking after hitting a dead end
    private static boolean solveTour(int row, int col) {
        // Base case, when the stack is full a path was found
        if (knightStack.size() == boardSize) {
            return true;
        }

        // Iterate through all possible moves a knight can take from a position
        for (int i = 0; i < 8; i++) {
            int nextRow = row + moves[i][0];
            int nextCol = col + moves[i][1];

            // Check if the move is valid
            if (isValid(nextRow, nextCol)) {
                // If so update the vertex as visited
                board[nextRow][nextCol] = 1;
                // Translate the destination into a position number
                int destination = nextRow + nextCol * dimension + 1;
                // Add to the path on the stack and update move counter
                knightStack.push(destination);
                totalMoves++;

                // Check if the next vertex has a move and start recursion
                if (solveTour(nextRow, nextCol)) {
                    return true;
                } 
                // Otherwise backtrack
                else {
                    // Set the vertex as unvisited
                    board[nextRow][nextCol] = -1;
                    // Remove the vertex from the path
                    knightStack.pop();
                    // Moving backwards also counts as a move
                    totalMoves++;
                }
            }
        }
        // Return false if all paths are exhausted
        return false;
    }
}
