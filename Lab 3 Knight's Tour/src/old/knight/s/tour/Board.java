/* 
* Bradley White
* Lab 3: Knight's Tour
* CSCI 232
* April 4, 2016
 */
// NetBeans package
package old.knight.s.tour;

import java.util.*;
import java.lang.StackOverflowError;

public class Board {

    public static int boardSize, startPos, dimension;
    // Counter for total moves
    public static int totalMoves = 0;
    // Array of Knights for each position in the board, where index = position - 1
    public static Knight[] boardArray;
    // Stack for keeping track of the path
    public static Deque<Knight> knightStack = new ArrayDeque<>(boardSize);

    public static Boolean solutionFound = false;

    // Constructor
    public Board(int size, int position) {
        dimension = size;
        startPos = position;
        initializeBoard();
    }

    // Helper method to initialize variables then start finding a solution
    public static void initializeBoard() {
        boardSize = dimension * dimension;
        boardArray = new Knight[boardSize];
        knightStack = new ArrayDeque<>(boardSize);
        Knight startingKnight = createKnight(startPos);
        makeMoves(startingKnight);
    }

    // Determine of a certain Knight has been visited yet
    public static boolean checkVertex(int index) {
        return boardArray[index].getVisited();
    }

    // Helper method to handle repetitive tasks during movements
    public static Knight createKnight(int pos) {
        // Create a new Knight
        Knight tempKnight = new Knight(pos, dimension);
        // Insert into the Array at the correct index
        boardArray[pos - 1] = tempKnight;
        // Push the Knight onto the stack
        knightStack.push(tempKnight);
        // Update counter and boolean
        totalMoves++;
        tempKnight.visited = true;
        // Return the new Knight
        return tempKnight;
    }

    // Finds valid moves until a dead end is reached or a solution is found
    public static void makeMoves(Knight startingKnight) {
        // Position of next available move
        int destination = 0;
        // Loop until no valid move or solution is found
        while (destination != -1 && knightStack.size() != boardSize) {
            // Call helper method to calculate next valid position
            destination = startingKnight.getNextPos();
            //System.out.println("Stack size: " + knightStack.size() + ", Starting Point: " + startingKnight.position + ", Dest: " + destination);
            // If a valid move is found enter logic statements
            if (destination != -1) {
                // If the index is null, call helper method
                if (boardArray[destination - 1] == null) {
                    Knight nextKnight = createKnight(destination);
                    //nextKnight.adjacentStack.push(destination);
                    // Start the loop again from the new Knight
                    startingKnight = nextKnight;
                } else {
                    // If the Knight exists, update variables
                    Knight nextKnight = boardArray[destination - 1];
                    knightStack.push(nextKnight);
                    totalMoves++;
                    nextKnight.visited = true;
                    //nextKnight.adjacentStack.push(destination);
                    // Start the loop again from this Knight
                    startingKnight = nextKnight;
                }
                //System.out.println("Making moves: " + knightStack.size());
            }
        }
        // If the loop ended without a full stack, it has to backtrack
        if (knightStack.size() != boardSize) {
            backTrack();
        } // Otherwise a solution was found, display the results
        else {
            solutionFound = true;
            printResults();
        }
    }

    // Helper method to try and find a new path
    public static void backTrack() {
        // Pop the last knight on the stack because it didn't find a valid move
        Knight succKnight = knightStack.pop();
        succKnight.visited = false;
        totalMoves++;
        int destination = -1;

        // Keep backtracking until a valid move is found or the stack is empty
        //while (knightStack.size() != boardSize && knightStack.size() > 0) {
        while (destination == -1 && knightStack.size() > 1) {
            // Get the top knight from the stack, but don't remove it yet
            Knight currentKnight = knightStack.peek();
            // Find its next valid move
            destination = currentKnight.getNextPos();
            //System.out.println("Stack size: " + knightStack.size() + ", Starting Point: " + currentKnight.position + ", Dest: " + destination);

            // If no valid move was found pop the Knight from the stack
            if (destination == -1) {
                currentKnight.visited = false;
                knightStack.pop();
                totalMoves++;
                succKnight = currentKnight;
            } // If it happens to be the same destination as before pop the Knight
            else if (destination == succKnight.position) {
                currentKnight.visited = false;
                knightStack.pop();
                totalMoves++;
                succKnight = currentKnight;
                destination = -1;
            }
        }
        //else if (!previousKnight.adjacentStack.contains(destination)){
        // If a valid move is found, try the path
        if (destination != -1) {
            // If the destination does not have a Knight, create one
            if (boardArray[destination - 1] == null) {
                Knight nextKnight = new Knight(destination, dimension);
                boardArray[destination - 1] = nextKnight;
            }
            // Retrieve the destination Knight and update variables
            Knight nextKnight = boardArray[destination - 1];
            knightStack.push(nextKnight);
            totalMoves++;
            nextKnight.visited = true;
            //nextKnight.adjacentStack.push(destination);

            if (knightStack.size() == 1) {
                printResults();
            } else if (knightStack.size() == boardSize) {
                solutionFound = true;
                printResults();
            } else {
                makeMoves(nextKnight);
            }
        } // Otherwise the stack is empty and there is no solution
        else {
            printResults();
        }
        //}

    }

    // Display results
    public static void printResults() {
        if (solutionFound == true) {
            System.out.println("SUCCESS:");
        } else {
            System.out.println("FAILURE:");
        }
        System.out.println("Total Number of Moves=" + totalMoves);
        System.out.print("Moving Sequence:");
        // Print the path, poping from the bottom of the stack
        while (knightStack.size() > 1) {
            Knight currentKnight = knightStack.removeLast();
            System.out.print(" " + currentKnight.position);
        }
        Knight currentKnight = knightStack.removeLast();
        System.out.print(" (" + currentKnight.position + ")");
        System.out.println();
    }

}
