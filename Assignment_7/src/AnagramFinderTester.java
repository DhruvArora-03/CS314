/* CS 314 STUDENTS: FILL IN THIS HEADER AND THEN COPY AND PASTE IT TO YOUR
 * LetterInventory.java AND AnagramSolver.java CLASSES.
 *
 * Student information for assignment:
 *
 *  On my honor, Dhruv Arora, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: da32895
 *  email address: dhruvarora@utexas.edu
 *  TA name: Grace
 *  Number of slip days I am using: 0
 */

public class AnagramFinderTester {
    /**
     * main method that executes tests.
     *
     * @param args Not used.
     */
    public static void main(String[] args) {
        cs314StudentTestsForLetterInventory();
    }
    
    /**
     * Run student tests for the LetterInventory.java class
     */
    private static void cs314StudentTestsForLetterInventory() {
        // test 1 - constructor
        int caseNum = 1;
        LetterInventory inventory1 = new LetterInventory("dcbaa");
        if (inventory1.toString().equals("aabcd")) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 2 - constructor
        caseNum++;
        LetterInventory inventory2 = new LetterInventory("@@@dhRUV aR0r@!!! :) *&^%#!");
        if (inventory2.toString().equals("adhrrruv")) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 3 - get
        caseNum++;
        if (inventory1.get('a') == 2) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 4 - get
        caseNum++;
        if (inventory2.get('e') == 0) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 5 - size
        caseNum++;
        if (inventory1.size() == 5) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 6 - size
        caseNum++;
        if (inventory2.size() == 8) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 7 - isEmpty
        caseNum++;
        if (!inventory1.isEmpty()) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 8 - isEmpty
        caseNum++;
        LetterInventory emptyInventory = new LetterInventory("");
        if (emptyInventory.isEmpty()) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 9 - toString
        caseNum++;
        LetterInventory inventory3 = new LetterInventory("thisisalowercasestringwithnoextras");
        if (inventory3.toString().equals("aaaceeeghhiiiilnnoorrrsssssttttwwx")) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 10 - toString
        caseNum++;
        LetterInventory inventory4 = new LetterInventory(
                "tHiS @#$ is?!?!?! qUITe!!!! THE SPicy--- stRING?!??!?!?!?@**^#%"
        );
        if (inventory4.toString().equals("ceeghhiiiiinpqrssssttttuy")) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 11 - add
        caseNum++;
        if (inventory1.add(inventory2).toString().equals("aaabcddhrrruv")) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 12 - add
        caseNum++;
        if (inventory3.add(inventory4).toString().equals(
                "aaacceeeeegghhhhiiiiiiiiilnnnoopqrrrrsssssssssttttttttuwwxy")) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 13 - subtract
        caseNum++;
        if (inventory1.subtract(new LetterInventory("aDcB")).toString().equals("a")) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
    
        // test 14 - subtract
        caseNum++;
        if (inventory1.subtract(new LetterInventory("aaa")) == null) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 15 - equals
        caseNum++;
        LetterInventory inventory1Clone = new LetterInventory("aabcd");
        if (inventory1.equals(inventory1Clone)) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
        
        // test 16 - equals
        caseNum++;
        if (!inventory2.equals(inventory1Clone)) {
            System.out.println("Test " + caseNum + " passed!");
        } else {
            System.out.println("Test " + caseNum + " FAILED!");
        }
    }
}
