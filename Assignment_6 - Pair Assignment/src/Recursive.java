/*  Student information for assignment:
 *
 *  On OUR honor, Dhruv Arora and Rohit Anantha,
 *  this programming assignment is OUR own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 0
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID: da32895
 *  email address: dhruvarora@utexas.edu
 *  Grader name: Grace
 *  Section number: 52660
 *
 *  Student 2
 *  UTEID: ra38355
 *  email address: anantha.rohit.11@gmail.com
 *
 */


//imports

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Various recursive methods to be implemented.
 * Given shell file for CS314 assignment.
 */
public class Recursive {
    // x and y offsets to move in north, east, south, and west directions for prob #6 and prob #8
    private final static int[][] NESW_DIRECTIONS = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
    private static final List<String> LETTERS_FOR_NUMBER;
    
    /* Static code blocks are run once when this class is loaded.
     * Here we create an unmodifiable list to use with the phone
     * mnemonics method.
     */
    static {
        String[] letters = { "0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ" };
        ArrayList<String> lettersAsList = new ArrayList<>(Arrays.asList(letters));
        LETTERS_FOR_NUMBER = Collections.unmodifiableList(lettersAsList);
        
    }
    // used by method digitLetters
    
    /**
     * Problem 1: convert a base 10 int to binary recursively.
     * <br>pre: n != Integer.MIN_VALUE<br>
     * <br>post: Returns a String that represents N in binary.
     * All chars in returned String are '1's or '0's.
     * Most significant digit is at position 0
     *
     * @param n the base 10 int to convert to base 2
     * @return a String that is a binary representation of the parameter n
     */
    public static String getBinary(int n) {
        if (n == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "getBinary. n cannot equal "
                    + "Integer.MIN_VALUE. n: " + n);
        }
        
        if (n < 0) {
            // fix negative cases by 'converting' to positive
            return "-" + getBinary(n * -1);
        } else if (n < 2) { // base case: n == 0 or n == 1
            return String.valueOf(n);
        }
        
        // recursion step
        return getBinary(n / 2) + (n % 2);
    }
    
    /**
     * Problem 2: reverse a String recursively.<br>
     * pre: stringToRev != null<br>
     * post: returns a String that is the reverse of stringToRev
     *
     * @param stringToRev the String to reverse, pre: stringToRev != null
     * @return a String with the characters in stringToRev
     * in reverse order.
     */
    public static String revString(String stringToRev) {
        if (stringToRev == null) {
            throw new IllegalArgumentException("Failed precondition: revString. parameter may not" +
                    " be null.");
        }
        
        // base case - just one character left or an empty string
        if (stringToRev.length() < 2) {
            return stringToRev;
        }
        
        // recursive step - last char + call revString() on the rest of the string
        return stringToRev.charAt(stringToRev.length() - 1)
                + revString(stringToRev.substring(0, stringToRev.length() - 1));
    }
    
    /**
     * Problem 3: Returns the number of elements in data
     * that are followed directly by value that is
     * double that element.
     * pre: data != null
     * post: return the number of elements in data
     * that are followed immediately by double the value
     *
     * @param data The array to search.
     * @return The number of elements in data that
     * are followed immediately by a value that is double the element.
     */
    public static int nextIsDouble(int[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Failed precondition: data. parameter may " +
                    "not be null.");
        }
        
        // kick off recursion
        return nextIsDouble(data, 0);
    }
    
    /**
     * Returns the number of elements in data that are followed directly by value that is double
     * that element, starting searching at pos.
     *
     * @param data int[] containing all data, pre: data != null
     * @param pos  int index to start checking at, always starts at 0
     * @return # of values where the next value is double (starting at pos)
     */
    private static int nextIsDouble(int[] data, int pos) {
        // precondition checked by calling method: public nextIsDouble()
        
        // base case - when 0 or 1 numbers left
        if (pos >= data.length - 1) {
            return 0;
        }
        
        // recursive step - if 2 * curr == next --> add 1 to total and call nextIsDouble w/ pos + 1
        return (data[pos] * 2 == data[pos + 1] ? 1 : 0) + nextIsDouble(data, pos + 1);
    }
    
    /**
     * Problem 4: Find all combinations of mnemonics
     * for the given number.<br>
     * pre: number != null, number.length() > 0,
     * all characters in number are digits<br>
     * post: see tips section of assignment handout
     *
     * @param number The number to find mnemonics for, pre: number != null, number.length() > 0,
     *               allDigits(number) == true
     * @return An ArrayList<String> with all possible mnemonics
     * for the given number
     */
    public static ArrayList<String> listMnemonics(String number) {
        if (number == null || number.length() == 0 || !allDigits(number)) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "listMnemonics");
        }
        
        // kick off recursion
        ArrayList<String> results = new ArrayList<>(); // to store the mnemonics
        recursiveMnemonics(results, "", number);
        return results;
    }
    
    /**
     * Helper method for listMnemonics
     *
     * @param mnemonics     completed mnemonics
     *                      pre: mnemonics is not null
     * @param mnemonicSoFar is a partial (possibly complete) mnemonic
     *                      pre: none
     * @param digitsLeft    are the digits that have not been used
     *                      from the original number.
     *                      pre: none
     */
    private static void recursiveMnemonics(ArrayList<String> mnemonics, String mnemonicSoFar,
                                           String digitsLeft) {
        if (mnemonics == null) {
            throw new IllegalArgumentException("Invalid Arguments: mnemonics != null");
        }
        
        // base case - when all digits have been used
        if (digitsLeft.length() == 0) {
            mnemonics.add(mnemonicSoFar); // save current mnemonic to result ArrayList
        } else {
            // recursive step(s)
            String newDigitsLeft = digitsLeft.substring(1); // avoid creating a new string each time
            for (char letter : digitLetters(digitsLeft.charAt(0)).toCharArray()) {
                // try appending every possible letter
                recursiveMnemonics(mnemonics, mnemonicSoFar + letter, newDigitsLeft);
            }
        }
    }
    
    /**
     * helper method for recursiveMnemonics
     * pre: ch is a digit '0' through '9'
     * post: return the characters associated with
     * this digit on a phone keypad
     */
    private static String digitLetters(char ch) {
        if (ch < '0' || ch > '9') {
            throw new IllegalArgumentException("parameter "
                    + "ch must be a digit, 0 to 9. Given value = " + ch);
        }
        int index = ch - '0';
        return LETTERS_FOR_NUMBER.get(index);
    }
    
    /**
     * helper method for listMnemonics
     * pre: s != null
     * post: return true if every character in s is
     * a digit ('0' through '9')
     */
    private static boolean allDigits(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "allDigits. String s cannot be null.");
        }
        boolean allDigits = true;
        int i = 0;
        while (i < s.length() && allDigits) {
            allDigits = s.charAt(i) >= '0' && s.charAt(i) <= '9';
            i++;
        }
        return allDigits;
    }
    
    /**
     * Problem 5: Draw a Sierpinski Carpet.
     *
     * @param size  the size in pixels of the window, size > 0
     * @param limit the smallest size of a square in the carpet, limit > 0
     */
    public static void drawCarpet(int size, int limit) {
        if (size <= 0 || limit <= 0) {
            throw new IllegalArgumentException("Invalid Arguments: size and limit must be > 0");
        }
        DrawingPanel p = new DrawingPanel(size, size);
        Graphics g = p.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, size, size);
        g.setColor(Color.WHITE);
        drawSquares(g, size, limit, 0, 0);
    }
    
    /**
     * Helper method for drawCarpet
     * Draw the individual squares of the carpet.
     *
     * @param g     The Graphics object to use to fill rectangles, pre: g != null
     * @param size  the size of the current square, pre: size > 0
     * @param limit the smallest allowable size of squares, pre: limit > 0
     * @param x     the x coordinate of the upper left of the current square, pre: x in bounds
     * @param y     the y coordinate of the upper left of the current square, pre: y in bounds
     */
    private static void drawSquares(Graphics g, int size, int limit, double x, double y) {
        if (g == null || size <= 0 || limit <= 0) {
            throw new IllegalArgumentException("Invalid Arguments: g != null, size > 0, limit > " +
                    "0, x within bounds, y within bounds");
        }
        
        // base case - size <= limit --> do nothing
        
        // recursive step - size > limit --> fill the middle square and recursively draw on outer 8
        if (size > limit) {
            // go through all 9 squares
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (r == 1 && c == 1) { // 'cut out' middle
                        g.fillRect((int) (x + (size / 3)), (int) (y + (size / 3)),
                                size / 3, size / 3);
                    } else { // recursively create carpet
                        drawSquares(g, size / 3, limit, x + (size / 3.0 * r), y + (size / 3.0 * c));
                    }
                }
            }
        }
    }
    
    /**
     * Problem 6: Determine if water at a given point
     * on a map can flow off the map.
     * <br>pre: map != null, map.length > 0,
     * map is a rectangular matrix, 0 <= row < map.length,
     * 0 <= col < map[0].length
     * <br>post: return true if a drop of water starting at the location
     * specified by row, column can reach the edge of the map,
     * false otherwise.
     *
     * @param map The elevations of a section of a map.
     * @param row The starting row of a drop of water.
     * @param col The starting column of a drop of water.
     * @return return true if a drop of water starting at the location
     * specified by row, column can reach the edge of the map, false otherwise.
     */
    public static boolean canFlowOffMap(int[][] map, int row, int col) {
        if (map == null || map.length == 0 || !isRectangular(map) || !inbounds(row, col, map)) {
            throw new IllegalArgumentException("Failed precondition: canFlowOffMap");
        }
        
        // base case - is on the 'border/edge' of the map --> therefore can flow off
        if (row == 0 || row == map.length - 1 || col == 0 || col == map[0].length - 1) {
            return true;
        }
        
        // recursive step(s) - try going in each direction (only if new spot is 'lower')
        for (int[] direction : NESW_DIRECTIONS) {
            // if new direction is lower elevation --> and recursive check from new spot
            if (map[row][col] > map[row + direction[0]][col + direction[1]]
                    && canFlowOffMap(map, row + direction[0], col + direction[1])) {
                return true;
            }
        }
        
        // if loop doesn't terminate --> there is no viable path to go off map
        return false;
    }
    
    /* helper method for canFlowOfMap - CS314 students you should not have to
     * call this method,
     * pre: mat != null,
     */
    private static boolean inbounds(int r, int c, int[][] mat) {
        if (mat == null) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "inbounds. The 2d array mat may not be null.");
        }
        
        return r >= 0 && r < mat.length && mat[r] != null
                && c >= 0 && c < mat[r].length;
    }
    
    /*
     * helper method for canFlowOfMap - CS314 students you should not have to
     * call this method,
     * pre: mat != null, mat.length > 0
     * post: return true if mat is rectangular
     */
    private static boolean isRectangular(int[][] mat) {
        if (mat == null || mat.length == 0) {
            throw new IllegalArgumentException("Failed precondition: "
                    + "inbounds. The 2d array mat may not be null "
                    + "and must have at least 1 row.");
        }
        
        boolean correct = true;
        final int numCols = mat[0].length;
        int row = 0;
        while (correct && row < mat.length) {
            correct = (mat[row] != null) && (mat[row].length == numCols);
            row++;
        }
        
        return correct;
    }
    
    /**
     * Problem 7: Find the minimum difference possible between teams
     * based on ability scores. The number of teams may be greater than 2.
     * The goal is to minimize the difference between the team with the
     * maximum total ability and the team with the minimum total ability.
     * <br> pre: numTeams >= 2, abilities != null, abilities.length >= numTeams
     * <br> post: return the minimum possible difference between the team
     * with the maximum total ability and the team with the minimum total
     * ability.
     *
     * @param numTeams  the number of teams to form.
     *                  Every team must have at least one member
     * @param abilities the ability scores of the people to distribute
     * @return return the minimum possible difference between the team
     * with the maximum total ability and the team with the minimum total
     * ability. The return value will be greater than or equal to 0.
     */
    public static int minDifference(int numTeams, int[] abilities) {
        // if there are more teams than people, at least one team will always be empty
        if (abilities == null || numTeams > abilities.length || numTeams < 2) {
            throw new IllegalArgumentException("Invalid Arguments: abilities != null, numTeams <=" +
                    " abilities.length, numTeams >= 2");
        }
        
        // kick off recursion
        return minDifference(abilities, new int[numTeams], new boolean[numTeams], 0);
    }
    
    /**
     * Find the minimum difference possible between teams
     * based on ability scores. The number of teams may be greater than 2.
     * The goal is to minimize the difference between the team with the
     * maximum total ability and the team with the minimum total ability.
     *
     * @param abilities     the list of abilities for all users
     * @param teamAbilities stores sum of team's (index) abilities
     * @param hasAtLeastOne stores whether each team (index) has at least one player added
     * @param abilityIndex  next index of abilities to place into a team
     * @return the smallest possible difference between the max and min team
     */
    private static int minDifference(int[] abilities, int[] teamAbilities, boolean[] hasAtLeastOne,
                                     int abilityIndex) {
        // preconditions checked by calling method: minDifference()
        
        // base case - all players have been added to a team
        if (abilityIndex >= abilities.length) {
            // if bool arr contains a false --> there is a team without a player --> invalid
            for (boolean currTeamHasAtLeastOne : hasAtLeastOne) {
                if (!currTeamHasAtLeastOne) {
                    // is invalid
                    return Integer.MAX_VALUE;
                }
            }
            
            // since loop didn't terminate --> must be valid --> find the diff between low and high
            return rangeOfArr(teamAbilities);
        }
        
        // recursive step(s) - search for min possible after adding to each possible team
        int min = Integer.MAX_VALUE;
        // add abilities[index] into every possible team and compare new min with cumulative
        for (int teamIndex = 0; teamIndex < teamAbilities.length; teamIndex++) {
            // increment vars before calling minDifference()
            teamAbilities[teamIndex] += abilities[abilityIndex];
            // track which teams have a player for validity verification
            boolean hadOneBefore = hasAtLeastOne[teamIndex];
            hasAtLeastOne[teamIndex] = true;
            
            // find curr min and compare with cumulative min
            int currMin = minDifference(abilities, teamAbilities, hasAtLeastOne, abilityIndex + 1);
            if (currMin < min) {
                min = currMin;
            }
            
            // backtrack to original values to reset for next possible team
            teamAbilities[teamIndex] -= abilities[abilityIndex];
            hasAtLeastOne[teamIndex] = hadOneBefore;
        }
        
        return min;
    }
    
    /**
     * Finds the difference between the smallest and greatest value in the array.
     *
     * @param arr the array to find the range of
     * @return the biggest value minus the smallest one in the array
     */
    private static int rangeOfArr(int[] arr) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        
        // search for min and max vals in the array
        for (int val : arr) {
            if (val < min) {
                min = val;
            }
            if (val > max) {
                max = val;
            }
        }
        
        // return range
        return max - min;
    }
    
    /**
     * Problem 8: Maze solver.
     * <br>pre: board != null
     * <br>pre: board is a rectangular matrix
     * <br>pre: board only contains characters 'S', 'E', '$', 'G', 'Y', and '*'
     * <br>pre: there is a single 'S' character present
     * <br>post: rawMaze is not altered as a result of this method.
     * Return 2 if it is possible to escape the maze after
     * collecting all the coins.
     * Return 1 if it is possible to escape the maze
     * but without collecting all the coins.
     * Return 0 if it is not possible
     * to escape the maze. More details in the assignment handout.
     *
     * @param rawMaze represents the maze we want to escape.
     *                rawMaze is not altered as a result of this method.
     * @return per the post condition
     */
    public static int canEscapeMaze(char[][] rawMaze) {
        // precondition checked Maze constructor
        Maze maze = new Maze(rawMaze);
        
        // account for case with no exit
        if (!maze.hasExit) {
            return 0;
        }
        
        // kick off recursion with starting coords
        return mazeExplorer(maze, maze.startRow, maze.startCol, maze.goldCoins);
    }
    
    /**
     * Recursive method for the maze solver method, looks for valid exits as well as valid but
     * not all gold collected exits. Keeps track of the current amounts of gold as well as the
     * status of the maze.
     *
     * @param row the current row
     * @param col the current col
     * @return 2 if there exists a path where all gold was collected, 1 if there exists a path to
     * an exit, and 0 if there is no path to the exit.
     */
    private static int mazeExplorer(Maze maze, int row, int col, int goldCoins) {
        // base case - coords are on the exit
        if (maze.rawMaze[row][col] == Maze.EXIT_CELL) {
            return goldCoins == 0 ? 2 : 1; // check if 'this path' found all the gold
        }
        
        // check if found a coin
        if (maze.rawMaze[row][col] == Maze.MONEY_CELL) {
            goldCoins--;
        }
        
        // recursive step(s) - try moving in each direction to find greatest path score possible
        int bestPathScore = 0;
        for (int[] direction : NESW_DIRECTIONS) {
            // get new location
            int newRow = row + direction[0], newCol = col + direction[1];
            
            // if the new location is in bounds and passable --> check new location
            if (maze.isTraversable(newRow, newCol)) {
                // decay the current location and travel to new location
                char oldCellValue = maze.decay(row, col);
                
                // compare new location's score to current best score
                int currentPathScore = mazeExplorer(maze, newRow, newCol, goldCoins);
                if (currentPathScore > bestPathScore) {
                    bestPathScore = currentPathScore;
                    // exit recursion early if a 2 is found: pop, pop, pop
                    if (bestPathScore == 2) {
                        return bestPathScore;
                    }
                }
                
                // restore the current location and backtrack.
                maze.rawMaze[row][col] = oldCellValue;
            }
        }
        
        return bestPathScore;
    }
    
    /**
     * Helper class for problem #8, containing constant identifiers for the types of Cells and
     * the list of cells.
     */
    private static final class Maze {
        // Cell identifiers for prob #8
        private final static char STARTING_CELL = 'S';
        private final static char IMPASSABLE_CELL = '*';
        private final static char GREEN_CELL = 'G';
        private final static char YELLOW_CELL = 'Y';
        private final static char MONEY_CELL = '$';
        private final static char EXIT_CELL = 'E';
        private final static Set<Character> VALID_CELLS = Set.of(STARTING_CELL, IMPASSABLE_CELL,
                GREEN_CELL, YELLOW_CELL, MONEY_CELL, EXIT_CELL);
        private final char[][] rawMaze;
        // instance vars
        private int startRow, startCol; // starting coords - calc during construction
        private int goldCoins; // # coins in entire maze
        private boolean hasExit; // there is at least one exit
        
        /**
         * Create a Maze Object based on the rawMaze given. Checks validity: Rectangular,
         * Containing only valid characters, and only containing one start Sets starting
         * position, number of gold coins, and whether or not there is at least one exit.
         *
         * @param rawMaze 2D char array used to represent the maze, rawMaze != null &&
         *                isRectangular && only has valid characters && exactly one starting pos
         */
        public Maze(char[][] rawMaze) {
            String exceptionText = "Invalid Argument: rawMaze must satisfy preconditions";
            if (rawMaze == null) {
                throw new IllegalArgumentException(exceptionText);
            }
            
            this.rawMaze = rawMaze;
            
            // set to negative to verify if start location has been found
            startRow = -1;
            startCol = -1;
            
            // check if all cells valid, find start pos, # of gold coins, and if there is exit
            goldCoins = 0;
            final int rowLength = rawMaze[0].length;
            for (int row = 0, rawMazeLength = rawMaze.length; row < rawMazeLength; row++) {
                // check rectangularity
                if (rawMaze[row].length != rowLength) {
                    throw new IllegalArgumentException(exceptionText);
                }
                
                for (int col = 0; col < rawMaze[row].length; col++) {
                    // check for 'cell' validity
                    if (!Maze.VALID_CELLS.contains(rawMaze[row][col])) {
                        throw new IllegalArgumentException(exceptionText);
                    } else { // if the cell is valid:
                        // check for starting position
                        if (rawMaze[row][col] == STARTING_CELL) {
                            // if this is the first starting position we find, save it
                            if (startRow + startCol < 0) {
                                startRow = row;
                                startCol = col;
                            } else {
                                // cannot have more than one starting position
                                throw new IllegalArgumentException(exceptionText);
                            }
                        }
                        // keep count of total gold coins in the maze
                        else if (rawMaze[row][col] == MONEY_CELL) {
                            goldCoins++;
                        }
                        // maze must have at least one exit
                        hasExit = hasExit || rawMaze[row][col] == EXIT_CELL;
                    }
                }
            }
        }
        
        /**
         * Return whether or not it is safe (in bounds and not impassable) at the given location
         * in a maze.
         *
         * @param row row value of coordinates
         * @param col col value of coordinates
         * @return true/false if coordinates is in bounds and the location of a passable cell
         */
        public boolean isTraversable(int row, int col) {
            return row >= 0 && row < rawMaze.length && rawMaze[row] != null
                    && col >= 0 && col < rawMaze[row].length && rawMaze[row][col] != IMPASSABLE_CELL;
        }
        
        /**
         * Decays the value at row, col in the maze to its specified next stage as stated in the
         * problem description.
         *
         * @param row the row of the value to decay, inBounds(row, col, rawMaze)
         * @param col the col of the value to decay, inBounds(row, col, rawMaze)
         * @return the value prior to being decayed at row, col
         */
        private char decay(int row, int col) {
            // preconditions are already checked by calling method: canEscapeMaze()
            
            // save old value and then apply appropriate decay
            char cell = rawMaze[row][col];
            if (cell == Maze.STARTING_CELL) {
                rawMaze[row][col] = Maze.GREEN_CELL;
            } else if (cell == Maze.GREEN_CELL || cell == Maze.MONEY_CELL) {
                rawMaze[row][col] = Maze.YELLOW_CELL;
            } else if (cell == Maze.YELLOW_CELL) {
                rawMaze[row][col] = Maze.IMPASSABLE_CELL;
            }
            
            return cell; // return value prior to decay
        }
    }
}