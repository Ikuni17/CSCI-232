/* 
* Bradley White
* Lab 3: Knight's Tour
* CSCI 232
* April 4, 2016
 */
// NetBeans package
package old.knight.s.tour;

import java.util.*;

public class Knight {

    // Keeps track if this Knight has been used for the solution
    public Boolean visited = false;
    // Position of the knight on the board, with lower left being position 1
    int position = 0;
    // The dimension of the board used to calculate the next valid move
    int N = 0;
    // Keeps track of which moves have been checked
    int cycle = 0;
    int cycleCount = 0;
    // All possible Knight moves (x, y)
//    private static int[][] moves8
//            = {{+1, -2}, {+2, -1}, {+2, +1}, {+1, +2},
//            {-1, +2}, {-2, +1}, {-2, -1}, {-1, -2}};
    private static int[][] moves8
            = {{+2, +1}, {+1, +2}, {-1, +2}, {-2, +1},
            {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};
    //public Deque<Integer> adjacentStack = new ArrayDeque<>(8);

    // Constructor
    public Knight(int pos, int dimension) {
        position = pos;
        N = dimension;
    }

    public int getPos() {
        return this.position;
    }

    public Boolean getVisited() {
        return this.visited;
    }

    public void setVisited(Boolean change) {
        this.visited = change;
    }

    // Finds the next valid move
    public int getNextPos() {
        if (cycleCount > 1000) {
            return -1;
        } else {
            while (cycle < 8) {
                // Get the change in x and y for the next move
                int dx = moves8[cycle][0];
                int dy = moves8[cycle][1];
                // Translate to (x,y)
                int x = (position - 1) % N;
                int y = (position - 1) / N;
                // Add the change in coordinates
                x = x + dx;
                y = y + dy;
                // Used this move
                cycle++;
                // Make sure the move is on the board
                if (x >= 0 && x < N && y >= 0 && y < N) {
                    // Convert to a position for a Knight
                    int nextPos = x + y * N + 1;
                    // If the index is null or the Knight is unvisited, the move is valid
                    if (Board.boardArray[nextPos - 1] == null || Board.checkVertex(nextPos - 1) == false) {
                        return nextPos;
                    }
                }
            }
            // No valid move found, reset the cycle and return failure
            cycle = 0;
            cycleCount++;
            return -1;
        }
    }
}
