/*  Student information for assignment:
 *
 *  On OUR honor, Dhruv and Rohit, this programming assignment is OUR own work
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

/**
 * Tester class for the methods in Recursive.java
 *
 * @author scottm
 */
public class RecursiveTester {
    
    // run the tests
    public static void main(String[] args) {
        studentTests();
    }
    
    // pre: r != null
    // post: run student test
    private static void studentTests() {
        // CS314 students put your tests here
        int testNum = 1;
        
        // test get binary
        String expected = "11";
        if (Recursive.getBinary(3).equals(expected)) {
            System.out.println("Passed test " + testNum + " : getBinary");
        } else {
            System.out.println("Failed test " + testNum + " : getBinary");
        }
        testNum++;
        expected = "100";
        if (Recursive.getBinary(4).equals(expected)) {
            System.out.println("Passed test " + testNum + " : getBinary");
        } else {
            System.out.println("Failed test " + testNum + " : getBinary");
        }
        
        // test reverse String
        expected = "olleh";
        testNum++;
        if (Recursive.revString("hello").equals(expected)) {
            System.out.println("Passed test " + testNum + " : revString");
        } else {
            System.out.println("Failed test " + testNum + " : revString");
        }
        expected = "ssap";
        testNum++;
        if (Recursive.revString("pass").equals(expected)) {
            System.out.println("Passed test " + testNum + " : revString");
        } else {
            System.out.println("Failed test " + testNum + " : revString");
        }
        
        // test next is double
        int expectedDoubles = 9;
        int[] testArray = new int[10];
        testNum++;
        if (Recursive.nextIsDouble(testArray) == (expectedDoubles)) {
            System.out.println("Passed test " + testNum + " : nextIsDouble");
        } else {
            System.out.println("Failed test " + testNum + " : nextIsDouble");
        }
        expectedDoubles = 2;
        testArray = new int[] {
                1, 2, 4
        };
        testNum++;
        if (Recursive.nextIsDouble(testArray) == (expectedDoubles)) {
            System.out.println("Passed test " + testNum + " : nextIsDouble");
        } else {
            System.out.println("Failed test " + testNum + " : nextIsDouble");
        }
        
        // test list Mnemonics
        expected = "[PG, PH, PI, QG, QH, QI, RG, RH, RI, SG, SH, SI]";
        testNum++;
        if (Recursive.listMnemonics("74").toString().equals(expected)) {
            System.out.println("Passed test " + testNum + " : listMnemonics");
        } else {
            System.out.println("Failed test " + testNum + " : listMnemonics");
        }
        expected = "[JA, JB, JC, KA, KB, KC, LA, LB, LC]";
        testNum++;
        if (Recursive.listMnemonics("52").toString().equals(expected)) {
            System.out.println("Passed test " + testNum + " : listMnemonics");
        } else {
            System.out.println("Failed test " + testNum + " : listMnemonics");
        }
        
        // test can flow off map
        int[][] world = new int[][]{
                { 5, 5, 5, 5, 5, 5 },
                { 5, 5, 4, 5, 5, 5 },
                { 5, 4, 3, 2, 5, 5 },
                { 5, 5, 2, 1, 0, 5 },
                { 5, 5, 5, 0, -1, -2 },
                { 5, 5, 5, 5, -2, -3 },
                { 5, 5, 5, 5, 5, 5 },
                { 5, 5, 5, 5, 5, 5 }
        };
        boolean expectedBool = true;
        testNum++;
        if (Recursive.canFlowOffMap(world, 1, 1) == expectedBool) {
            System.out.println("Passed test " + testNum + " : canFlowOffMap");
        } else {
            System.out.println("Failed test " + testNum + " : canFlowOffMap");
        }
        world = new int[][]{
                { 5, 5, 5, 5, 5, 5 },
                { 5, 5, 4, 5, 5, 5 },
                { 5, -8, 3, 2, 5, 5 },
                { 5, -7, 2, 1, 0, 5 },
                { 5, -6, 5, 0, -1, 5 },
                { 5, -5, -4, -3, -2, 5 },
                { 5, 5, 5, 5, 5, 5 },
                { 5, 5, 5, 5, 5, 5 }
        };
        expectedBool = false;
        testNum++;
        if (Recursive.canFlowOffMap(world, 1, 1) == expectedBool) {
            System.out.println("Passed test " + testNum + " : canFlowOffMap");
        } else {
            System.out.println("Failed test " + testNum + " : canFlowOffMap");
        }
        
        // test min difference
        int expectedNum = 1;
        testNum++;
        if (Recursive.minDifference(2, new int[] { 1, 2 }) == expectedNum) {
            System.out.println("Passed test " + testNum + " : minDifference");
        } else {
            System.out.println("Failed test " + testNum + " : minDifference");
        }
        expectedNum = 2;
        testNum++;
        if (Recursive.minDifference(3, new int[] { 1, 2, 3 }) == expectedNum) {
            System.out.println("Passed test " + testNum + " : minDifference");
        } else {
            System.out.println("Failed test " + testNum + " : minDifference");
        }
        // test maze explorer
        runMazeTest("$GSGE$$$$GG*G$$$", 2, 1, ++testNum);
        runMazeTest("Y$SGE", 1, 2, ++testNum);
    }
    
    private static void runMazeTest(String rawMaze, int rows, int expected, int num) {
        char[][] maze = makeMaze(rawMaze, rows);
        System.out.println("Can escape maze test number " + num);
        printMaze(maze);
        int actual = Recursive.canEscapeMaze(maze);
        System.out.println("Expected result: " + expected);
        System.out.println("Actual result:   " + actual);
        if (expected == actual) {
            System.out.println("passed test " + num);
        } else {
            System.out.println("FAILED TEST " + num);
        }
        System.out.println();
    }
    
    // print the given maze
    // pre: none
    private static void printMaze(char[][] maze) {
        if (maze == null) {
            System.out.println("NO MAZE GIVEN");
        } else {
            for (char[] row : maze) {
                for (char c : row) {
                    System.out.print(c);
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
    }
    
    // generate a char[][] given the raw string
    // pre: rawMaze != null, rawMaze.length() % rows == 0
    private static char[][] makeMaze(String rawMaze, int rows) {
        if (rawMaze == null || rawMaze.length() % rows != 0) {
            throw new IllegalArgumentException("Violation of precondition in makeMaze."
                    + "Either raw data is null or left over values: ");
        }
        int cols = rawMaze.length() / rows;
        char[][] result = new char[rows][cols];
        int rawIndex = 0;
        for (int r = 0; r < result.length; r++) {
            for (int c = 0; c < result[0].length; c++) {
                result[r][c] = rawMaze.charAt(rawIndex);
                rawIndex++;
            }
        }
        return result;
    }
}
