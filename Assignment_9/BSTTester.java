/*
 * CS 314 STUDENTS: FILL IN THIS HEADER.
 *
 * Student information for assignment:
 *
 * On my honor, Dhruv Arora, this programming assignment is my own work and I have not provided this
 * code to any other student.
 *
 * UTEID: da32895 <br>
 * emaill address: dhruvarora@utexas.edu <br>
 * TA name: Grace <br>
 * Number of slip days I am using: 2
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

/**
 * Some test cases for CS314 Binary Search Tree assignment.
 * 
 * Experiment results:
 * 
 * Questions: <br>
 * For each value of N what is the minimum possible tree height, assuming N
 * unique values are inserted into the tree? <br>
 * 
 * The minimum possible height is given by floor(log base 2 of N).
 * So for N = 1000, minHeight = 109
 * 
 * 
 */
public class BSTTester {

    /**
     * The main method runs the tests.
     * 
     * @param args Not used
     */
    public static void main(String[] args) {
        runAllGivenTests();
        runExperiments();
    }

    private static void runAllGivenTests() {
        BinarySearchTree<String> t = new BinarySearchTree<>();

        // test 1
        System.out.println("Test 1: empty tree created.");
        showTestResults(t.size() == 0, 1);

        // test 2
        System.out.println("Test 2: height of empty tree must be -1.");
        showTestResults(t.height() == -1, 2);

        // test 3
        System.out.println("Test 3: empty tree must " + "not contain the String \"abyss\".");
        showTestResults(t.isPresent("abyss") == false, 3);

        t.add("abyss");
        // test 4
        System.out.println("Test 4: added \"abyss\" to the" + "tree. Size must be 1.");
        showTestResults(t.size() == 1, 4);

        // test 5
        System.out.println("Test 5: height of tree with 1" + "element must be 0.");
        showTestResults(t.height() == 0, 5);

        // test 6
        System.out.println("Test 6: \"abyss\" must be in the tree.");
        showTestResults(t.isPresent("abyss") == true, 6);

        // test 7
        System.out.println("Test 7: tree must " + "not contain the String \"beep\".");
        showTestResults(t.isPresent("beep") == false, 7);

        // test 8
        System.out.println("Test 8: min value must be" + "\"abyss\" at this point.");
        showTestResults(t.min().equals("abyss"), 8);

        // test 9
        System.out.println("Test 9: max value must be" + "\"abyss\" at this point.");
        showTestResults(t.max().equals("abyss"), 9);

        t.add("abyss");
        // test 10
        System.out.println("Test 10: attempt to add \"abyss\"" + "again. size must remain 1.");
        showTestResults(t.size() == 1, 10);

        // test 11
        System.out.println("Test 11: attempt to add \"abyss\"" + "again. height must remain 0.");
        showTestResults(t.height() == 0, 11);

        // test 12
        System.out.println("Test 12: \"abyss\" must still be" + "present.");
        showTestResults(t.isPresent("abyss") == true, 12);

        t.add("beep");
        // test 13
        System.out.println("Test 13: added \"beep\" to the" + "tree. Size must be 2.");
        showTestResults(t.size() == 2, 13);

        // test 14
        System.out.println("Test 14: height of tree with 2" + "elements must be 1.");
        showTestResults(t.height() == 1, 14);

        // test 15
        System.out.println("Test 15: Removing \"abyss\" from the tree.");
        showTestResults(t.remove("abyss") == true, 15);

        // test 16
        System.out.println("Test 16: Removing \"beep\" from the tree.");
        showTestResults(t.remove("beep") == true, 16);

        // test 17
        System.out.println("Test 17: Tree must be empty at this point.");
        showTestResults(t.size() == 0, 17);

        t.add("beep");
        t.add("abyss");
        t.add("calls");
        ArrayList<String> expected = new ArrayList<>();
        expected.add("abyss");
        expected.add("beep");
        expected.add("calls");

        // test 18
        System.out.println("Test 18: Added \"beep\", \"abyss\", and" + "\"calls\" to the tree in that order.\n"
                + "Testing getAll method.");

        showTestResults(expected.equals(t.getAll()) == true, 18);

        // test 19
        t.add("bit");
        t.add("dish");
        System.out.println("Test 19: Added \"bit\" and \"dish\" to" + "tree. Checking that \"yes\" is not present.");
        showTestResults(t.remove("yes") == false, 19);

        // test 20
        t.add("a");
        System.out.println("Test 20: Added \"a\" and then " + "removed it.");
        showTestResults(t.remove("a") == true, 20);

        // test 21
        System.out.println("Test 21: Checking that \"calls\" is still present.");
        showTestResults(t.remove("calls") == true, 21);

        // test 22
        t.remove("abyss");
        System.out.println("Test 22: Removing \"abyss\". " + "Checking that \"beep\" is still present.");
        showTestResults(t.remove("beep") == true, 22);

        // Test 23 - Adding unbalanced
        BinarySearchTree<Integer> actualTree = new BinarySearchTree<>();
        ArrayList<Integer> expectedValues = new ArrayList<>();
        actualTree.add(1);
        actualTree.iterativeAdd(2);
        actualTree.iterativeAdd(3);
        actualTree.add(-1);
        expectedValues.add(-1);
        expectedValues.add(1);
        expectedValues.add(2);
        expectedValues.add(3);
        System.out.println("Test 23: Adding unbalanced");
        showTestResults(expectedValues.equals(actualTree.getAll()) == true, 23);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 24 - Adding unbalanced
        actualTree.iterativeAdd(-2);
        expectedValues.add(0, -2);
        System.out.println("Test 24: Adding unbalanced");
        showTestResults(expectedValues.equals(actualTree.getAll()) == true, 24);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 25 - Removing root [-2, -1, 2, 3]
        actualTree.remove(1);
        expectedValues.remove(Integer.valueOf(1));
        System.out.println("Test 25: Removing root");
        showTestResults(expectedValues.equals(actualTree.getAll()) == true, 25);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 26 - Removing new root [-2, 2, 3]
        actualTree.remove(-1);
        expectedValues.remove(Integer.valueOf(-1));
        System.out.println("Test 26: Removing new root");
        showTestResults(expectedValues.equals(actualTree.getAll()) == true, 26);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 27 - IsPresent new root
        System.out.println("Test 27: IsPresent new root");
        showTestResults(actualTree.isPresent(-2) == true, 27);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 28 - IsPresent rightmost minimum
        System.out.println("Test 28: isPresent, root of tree");
        showTestResults(actualTree.isPresent(2) == true, 28);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 29 - Size
        System.out.println("Test 29: Size of tree");
        showTestResults(actualTree.size() == 3, 29);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 30 - Size removing rightmost minimum [-2, 3]
        actualTree.remove(2);
        expectedValues.remove(Integer.valueOf(2));
        System.out.println("Test 30: Size of tree");
        showTestResults(actualTree.size() == 2, 30);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 31 - Height
        System.out.println("Test 31: Height of tree");
        showTestResults(actualTree.height() == 1, 31);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 32 - Height removing root [3]
        actualTree.remove(-2);
        expectedValues.remove(Integer.valueOf(-2));
        System.out.println("Test 32: Height of tree");
        showTestResults(actualTree.height() == 0, 32);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 33 - Max of tree
        System.out.println("Test 33: Max of tree");
        showTestResults(actualTree.max().equals(Integer.valueOf(3)), 33);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 34 - Max of tree [-1, 3]
        actualTree.add(-1);
        System.out.println("Test 34: Max of tree");
        showTestResults(actualTree.max().equals(Integer.valueOf(3)), 34);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 35 - Min of tree
        System.out.println("Test 35: Min of tree");
        showTestResults(actualTree.min().equals(Integer.valueOf(-1)), 35);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 36 - Min of tree
        actualTree.add(4);
        System.out.println("Test 36: Min of tree");
        showTestResults(actualTree.min().equals(Integer.valueOf(-1)), 36);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 37 - Number of Nodes at Depth
        System.out.println("Test 37: Number of Nodes at depth of tree");
        showTestResults(actualTree.numNodesAtDepth(0) == 1, 37);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 38 - Number of Nodes at Depth
        System.out.println("Test 38: Number of Nodes at depth of tree");
        showTestResults(actualTree.numNodesAtDepth(1) == 2, 38);
        // System.out.println(actualTree.getAll());
        // actualTree.printTree();

        // Test 39 - height
        int[] values = { 50, 25, -10, 10, 5, 0, 23, 30, 35, 40, 100, 75, 200 };
        BinarySearchTree<Integer> t2 = new BinarySearchTree<>();
        for (int i : values) {
            t2.add(i);
        }
        System.out.println("Height again for non trivial tree");
        showTestResults(t2.height() == 5, 39);
        // System.out.println(t2.getAll());
        // t2.printTree();

        // Test 40 - 52: get kth
        System.out.println("getKth");
        Arrays.sort(values);
        for (int i = 0; i < values.length; i++) {
            showTestResults(t2.get(i).equals(Integer.valueOf(values[i])), 40 + i);
        }

        // Test 53: getAllLessThan
        System.out.println("get all less than -50");
        showTestResults(t2.getAllLessThan(-50).equals(new ArrayList<Integer>()), 53);
        // System.out.println(t2.getAll());
        // t2.printTree();

        // Test 54: getAllLessThan
        System.out.println("get all less than 25");
        ArrayList<Integer> expectedList = new ArrayList<>();
        int cutoff = 25;
        int index = 0;
        while (index < values.length && values[index] < cutoff) {
            expectedList.add(Integer.valueOf(values[index]));
            index++;
        }
        List<Integer> actual = t2.getAllLessThan(cutoff);
        showTestResults(actual.equals(expectedList), 54);
        // System.out.println(t2.getAll());
        // t2.printTree();

        // Test 55: getAllLessThan
        System.out.println("get all less than 1000");
        expectedList.clear();
        cutoff = 1000;
        index = 0;
        while (index < values.length && values[index] < cutoff) {
            expectedList.add(Integer.valueOf(values[index]));
            index++;
        }
        actual = t2.getAllLessThan(cutoff);
        showTestResults(actual.equals(expectedList), 55);
        System.out.println("expected list: " + expectedList);
        System.out.println("actual list:   " + actual);
        // System.out.println(expectedList);
        // System.out.println(t2.getAll());
        // t2.printTree();

        // Test 57: getAllGreaterThan
        System.out.println("get all greater than 1000");
        expectedList.clear();
        cutoff = 1000;
        index = values.length - 1;
        while (index >= 0 && values[index] > cutoff) {
            expectedList.add(Integer.valueOf(values[index]));
            index--;
        }
        Collections.reverse(expectedList);
        actual = t2.getAllGreaterThan(cutoff);
        showTestResults(actual.equals(expectedList), 57);
        // System.out.println(expectedList);
        // System.out.println(t2.getAll());
        // t2.printTree();

        // Test 58: getAllGreaterThan
        System.out.println("get all greater than 25");
        expectedList.clear();
        cutoff = 25;
        index = values.length - 1;
        while (index >= 0 && values[index] > cutoff) {
            expectedList.add(Integer.valueOf(values[index]));
            index--;
        }
        Collections.reverse(expectedList);
        actual = t2.getAllGreaterThan(cutoff);
        showTestResults(actual.equals(expectedList), 58);
        System.out.println("expected list: " + expectedList);
        System.out.println("actual list:   " + actual);
        // t2.printTree();

        // Test 59: getAllGreaterThan
        System.out.println("get all greater than -1000");
        expectedList.clear();
        cutoff = -1000;
        index = values.length - 1;
        while (index >= 0 && values[index] > cutoff) {
            expectedList.add(Integer.valueOf(values[index]));
            index--;
        }
        Collections.reverse(expectedList);
        actual = t2.getAllGreaterThan(cutoff);
        showTestResults(actual.equals(expectedList), 59);
        System.out.println("expected list: " + expectedList);
        System.out.println("actual list:   " + actual);
        // t2.printTree();

        // Test 60, stress test
        System.out.println("Stress test, comparing size to HashSet");
        BinarySearchTree<Integer> bst1 = new BinarySearchTree<>();
        HashSet<Integer> hs = new HashSet<>();
        Random r = new Random();
        int numValues = 500_000;
        for (int i = 0; i < numValues; i++) {
            int temp = r.nextInt();
            bst1.add(temp);
            hs.add(temp);
        }
        showTestResults(hs.size() == bst1.size(), 60);

        // Test 61, stress test
        System.out.println("Stress test, comparing size to HashSet");
        bst1 = new BinarySearchTree<>();
        hs = new HashSet<>();
        numValues = 1_000_000;
        for (int i = 0; i < numValues; i++) {
            int temp = r.nextInt();
            bst1.add(temp);
            hs.add(temp);
        }
        showTestResults(hs.size() == bst1.size(), 61);
    }

    private static void showTestResults(boolean passed, int testNum) {
        if (passed) {
            System.out.println("Test " + testNum + " passed.");
        } else {
            System.out.println("TEST " + testNum + " FAILED.");
        }
    }

    private static void runExperiments() {
        Stopwatch s = new Stopwatch();

        randomInsertionExperiments(s);
        ascendingInsertionExperiments(s);
    }

    private static void randomInsertionExperiments(Stopwatch s) {
        System.out.println("====================BINARY SEARCH TREE====================");
        System.out.println("| N       | Average Time | Average Height | Average Size |");
        randomizedInsertionWithBST(s);
        System.out.println("==============JAVA TREE SET==============");
        System.out.println("| N       | Average Time | Average Size |");
        randomizedInsertionWithTreeSet(s);
    }

    private static void randomizedInsertionWithBST(Stopwatch s) {
        BinarySearchTree<Integer> tree;
        int n = 500;
        double averageTime, averageHeight, averageSize;

        for (int i = 0; i < 11; i++) {
            n *= 2;
            averageTime = averageHeight = averageSize = 0.0;
            for (int j = 0; j < 10; j++) {
                tree = new BinarySearchTree<>();

                s.start();
                addNRandomIntsToBST(n, tree);
                s.stop();
                averageTime += s.time();
                averageHeight += tree.height();
                averageSize += tree.size();
            }

            averageTime /= 10.0;
            averageHeight /= 10.0;
            averageSize /= 10.0;

            System.out.printf("| %-" + 7 + "s |", n);
            System.out.printf(" %-" + 12 + "s |", String.format("%.10f", averageTime));
            System.out.printf(" %-" + 14 + "s |", averageHeight);
            System.out.printf(" %-" + 12 + "s |\n", averageSize);
        }
    }

    private static void randomizedInsertionWithTreeSet(Stopwatch s) {
        TreeSet<Integer> tree;
        int n = 500;
        double averageTime, averageSize;

        for (int i = 0; i < 11; i++) {
            n *= 2;
            averageTime = averageSize = 0.0;
            for (int j = 0; j < 10; j++) {
                tree = new TreeSet<>();

                s.start();
                addNRandomIntsToTreeSet(n, tree);
                s.stop();
                averageTime += s.time();
                averageSize += tree.size();
            }

            averageTime /= 10.0;
            averageSize /= 10.0;

            System.out.printf("| %-" + 7 + "s |", n);
            System.out.printf(" %-" + 12 + "s |", String.format("%.10f", averageTime));
            System.out.printf(" %-" + 12 + "s |\n", averageSize);
        }
    }

    private static void addNRandomIntsToBST(int n, BinarySearchTree<Integer> tree) {
        Random r = new Random(123);
        for (int i = 0; i < n; i++) {
            tree.add(Integer.valueOf(r.nextInt()));
        }
    }

    private static void addNRandomIntsToTreeSet(int n, TreeSet<Integer> tree) {
        Random r = new Random(123);
        for (int i = 0; i < n; i++) {
            tree.add(Integer.valueOf(r.nextInt()));
        }
    }

    private static void ascendingInsertionExperiments(Stopwatch s) {
        int[] numbers = new int[1000];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;
        }

        System.out.println("====================BINARY SEARCH TREE====================");
        System.out.println("| N       | Average Time | Average Height | Average Size |");
        // ascendingInsertionWithBST(s);
        System.out.println("==============JAVA TREE SET==============");
        System.out.println("| N       | Average Time | Average Size |");
        // ascendingInsertionWithTreeSet(s);
    }
}
