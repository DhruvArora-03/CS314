/**
 * CS 314 STUDENTS: FILL IN THIS HEADER.
 *
 * Student information for assignment:
 *
 * On my honor, Dhruv Arora, this programming assignment is my own work and I have not provided this
 * code to any other student.
 *
 * UTEID: da32895 <br>
 * email address: dhruvarora@utexas.edu <br>
 * TA name: Grace <br>
 * Number of slip days I am using: 2 <br>
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

/**
 * Some test cases for CS314 Binary Search Tree assignment.
 * 
 * Random insertion experiment results: <br>
 * ====================BINARY SEARCH TREE==================== <br>
 * | N_______| Average Time | Average Height | Average Size | <br>
 * | 1000____| 0.0001389456 | 19.0___________| 1000.0_______| <br>
 * | 2000____| 0.0002468786 | 22.0___________| 2000.0_______| <br>
 * | 4000____| 0.0005366562 | 25.0___________| 4000.0_______| <br>
 * | 8000____| 0.0010696989 | 27.0___________| 8000.0_______| <br>
 * | 16000___| 0.0023706173 | 31.0___________| 16000.0______| <br>
 * | 32000___| 0.0071199302 | 32.0___________| 32000.0______| <br>
 * | 64000___| 0.0140719343 | 35.0___________| 63999.0______| <br>
 * | 128000__| 0.0318959602 | 37.0___________| 127998.0_____| <br>
 * | 256000__| 0.0775779063 | 40.0___________| 255991.0_____| <br>
 * | 512000__| 0.2274391155 | 42.0___________| 511971.0_____| <br>
 * | 1024000 | 0.6503940896 | 46.0___________| 1023895.0____| <br>
 * ==============JAVA TREE SET============== <br>
 * | N_______| Average Time | Average Size | <br>
 * | 1000____| 0.0004866296 | 1000.0_______| <br>
 * | 2000____| 0.0005763055 | 2000.0_______| <br>
 * | 4000____| 0.0011156639 | 4000.0_______| <br>
 * | 8000____| 0.0022220080 | 8000.0_______| <br>
 * | 16000___| 0.0029696141 | 16000.0______| <br>
 * | 32000___| 0.0066700788 | 32000.0______| <br>
 * | 64000___| 0.0137773670 | 63999.0______| <br>
 * | 128000__| 0.0301365168 | 127998.0_____| <br>
 * | 256000__| 0.0807418826 | 255991.0_____| <br>
 * | 512000__| 0.2564813060 | 511971.0_____| <br>
 * | 1024000 | 0.6577213583 | 1023895.0____| <br>
 * 
 * Ascending order experiment results: <br>
 * ====================BINARY SEARCH TREE==================== <br>
 * | N_______| Average Time | Average Height | Average Size | <br>
 * | 1000____| 0.0025287633 | 999____________| 1000_________| <br>
 * | 2000____| 0.0041302911 | 1999___________| 2000_________| <br>
 * | 4000____| 0.0162255981 | 3999___________| 4000_________| <br>
 * | 8000____| 0.0663554451 | 7999___________| 8000_________| <br>
 * | 16000___| 0.2719840570 | 15999__________| 16000________| <br>
 * | 32000___| 1.1076665933 | 31999__________| 32000________| <br>
 * | 64000___| 4.4457104172 | 63999__________| 64000________| <br>
 * ==============JAVA TREE SET============== <br>
 * | N_______| Average Time | Average Size | <br>
 * | 1000____| 0.0006473262 | 1000_________| <br>
 * | 2000____| 0.0006578692 | 2000_________| <br>
 * | 4000____| 0.0009860021 | 4000_________| <br>
 * | 8000____| 0.0009748396 | 8000_________| <br>
 * | 16000___| 0.0017055246 | 16000________| <br>
 * | 32000___| 0.0036437656 | 32000________| <br>
 * | 64000___| 0.0064692245 | 64000________| <br>
 * 
 * Questions: <br>
 * For each value of N what is the minimum possible tree height, assuming N unique values are
 * inserted into the tree? <br>
 * 
 * The minimum possible height is given by floor(log base 2 of N). <br>
 * So for N = 1000, minHeight = 11. <br>
 * for N = 2000, minHeight = 12. <br>
 * for N = 4000, minHeight = 13. <br>
 * for N = 8000, minHeight = 14. <br>
 * for N = 16000, minHeight = 15. <br>
 * for N = 32000, minHeight = 16. <br>
 * for N = 64000, minHeight = 17. <br>
 * for N = 128000, minHeight = 18. <br>
 * for N = 256000, minHeight = 19. <br>
 * for N = 512000, minHeight = 20. <br>
 * for N = 1024000, minHeight = 21. <br>
 * 
 * What are the average times for TreeSet? How do they compare to your BinarySearchTree? <br>
 * 
 * (Average times are in the table). The average time for TreeSet is less than half the time for the
 * BinarySearchTree when N <= 8000, however, at all other times the average time for TreeSet is the
 * same as the BinarySearchTree. <br>
 * 
 * Predict how long it would take to add 128,000, 256,000, and 512,000 sorted integers into your
 * binary search tree. <br>
 * 
 * The time to add 128,000 integers would be around 17.6 seconds (4.4 x 4). <br>
 * The time to add 256,000 integers would be around 70.4 seconds (17.6 x 4). <br>
 * The time to add 512,000 integers would be around 281.6 seconds (70.4 x 4). <br>
 * 
 * How do these times compare to the times it took for you BinarySearchTree class when inserting
 * integers in sorted order? What do you think is the cause for these differences? <br>
 * 
 * These times are very significantly worse (for my BST). The BinarySearchTree class is much slower
 * when adding integers in sorted order. The reason for this is that the BinarySearchTree class is
 * not implementing a red-black tree. The BinarySearchTree class is using a naive algorithm which
 * leads to the add() method being really slow since it has to go through every node to add the
 * next.
 */
public class BSTTester {

    /**
     * The main method runs the tests.
     * 
     * @param args Not used
     */
    public static void main(String[] args) {
        runTests();
        // runExperiments();
    }

    private static void runTests() {
        int testNum = 1;

        // test 1 - constructor
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        showTestResults(bst.size() == 0, testNum);

        // test 2 - constructor
        testNum++;
        bst = new BinarySearchTree<>();
        showTestResults(bst.height() == -1, testNum);

        // test 3 - add
        testNum++;
        bst.add(2);
        showTestResults(bst.size() == 1, testNum);

        // test 4 - add
        testNum++;
        bst.add(1);
        showTestResults(bst.size() == 2, testNum);

        // test 5 - remove
        testNum++;
        bst.remove(1);
        showTestResults(bst.size() == 1, testNum);

        // test 6 - remove
        testNum++;
        bst.remove(2);
        showTestResults(bst.size() == 0, testNum);

        // test 7 - isPresent
        testNum++;
        bst.add(3);
        showTestResults(bst.isPresent(3), testNum);

        // test 8 - isPresent
        testNum++;
        showTestResults(!bst.isPresent(2), testNum);

        // test 9 - size
        testNum++;
        bst.add(1);
        showTestResults(bst.size() == 2, testNum);

        // test 10 - size
        testNum++;
        bst.add(2);
        showTestResults(bst.size() == 3, testNum);

        // test 11 - height
        testNum++;
        showTestResults(bst.height() == 2, testNum);

        // test 12 - height
        testNum++;
        bst.add(6);
        showTestResults(bst.height() == 2, testNum);

        // test 13 - getAll
        testNum++;
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(6);
        showTestResults(bst.getAll().equals(list), testNum);

        // test 14 - getAll
        testNum++;
        bst.add(4);
        list.add(3, 4);
        showTestResults(bst.getAll().equals(list), testNum);

        // test 15 - max
        testNum++;
        showTestResults(bst.max() == 6, testNum);

        // test 16 - max
        testNum++;
        bst.add(8);
        showTestResults(bst.max() == 8, testNum);

        // test 17 - min
        testNum++;
        showTestResults(bst.min() == 1, testNum);

        // test 18 - min
        testNum++;
        bst.add(-1);
        showTestResults(bst.min() == -1, testNum);

        // test 19 - iterativeAdd
        testNum++;
        bst.iterativeAdd(0);
        showTestResults(bst.size() == 8, testNum);

        // test 20 - iterativeAdd
        testNum++;
        bst.iterativeAdd(5);
        showTestResults(bst.size() == 9, testNum);

        // test 21 - get
        testNum++;
        showTestResults(bst.get(2) == 1, testNum);

        // test 22 - get
        testNum++;
        showTestResults(bst.get(6) == 5, testNum);

        // test 23 - getAllLessThan
        testNum++;
        list.clear();
        list.add(-1);
        list.add(0);
        list.add(1);
        list.add(2);
        showTestResults(bst.getAllLessThan(3).equals(list), testNum);

        // test 24 - getAllLessThan
        testNum++;
        list.add(3);
        list.add(4);
        list.add(5);
        showTestResults(bst.getAllLessThan(6).equals(list), testNum);

        // test 25 - getAllGreaterThan
        testNum++;
        list.clear();
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(8);
        showTestResults(bst.getAllGreaterThan(3).equals(list), testNum);

        // test 26 - getAllGreaterThan
        testNum++;
        list.clear();
        list.add(8);
        showTestResults(bst.getAllGreaterThan(6).equals(list), testNum);

        // test 27 - numNodesAtDepth
        testNum++;
        showTestResults(bst.numNodesAtDepth(2) == 4, testNum);

        // test 28 - numNodesAtDepth
        testNum++;
        bst.add(10);
        showTestResults(bst.numNodesAtDepth(3) == 3, testNum);

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
        System.out.println(" * ====================BINARY SEARCH TREE====================");
        System.out.println(" * | N_______| Average Time | Average Height | Average Size |");
        randomizedInsertionWithBST(s);
        System.out.println(" * ==============JAVA TREE SET==============");
        System.out.println(" * | N_______| Average Time | Average Size |");
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

            System.out.printf(" * | %-" + 7 + "s |", n);
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

            System.out.printf(" * | %-" + 7 + "s |", n);
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
        System.out.println(" * ====================BINARY SEARCH TREE====================");
        System.out.println(" * | N_______| Average Time | Average Height | Average Size |");
        ascendingInsertionWithBST(s);
        System.out.println(" * ==============JAVA TREE SET==============");
        System.out.println(" * | N_______| Average Time | Average Size |");
        ascendingInsertionWithTreeSet(s);
    }

    private static void ascendingInsertionWithBST(Stopwatch s) {
        BinarySearchTree<Integer> tree;
        int n = 500;
        double averageTime;

        for (int i = 0; i < 7; i++) {
            n *= 2;
            averageTime = 0.0;
            for (int j = 0; j < 10; j++) {
                tree = new BinarySearchTree<>();

                s.start();
                for (int k = 0; k < n; k++) {
                    tree.iterativeAdd(k);
                }
                s.stop();
                averageTime += s.time();
            }

            averageTime /= 10.0;

            System.out.printf(" * | %-" + 7 + "s |", n);
            System.out.printf(" %-" + 12 + "s |", String.format("%.10f", averageTime));
            System.out.printf(" %-" + 14 + "s |", n - 1);
            System.out.printf(" %-" + 12 + "s |\n", n);
        }
    }

    private static void ascendingInsertionWithTreeSet(Stopwatch s) {
        TreeSet<Integer> tree;
        int n = 500;
        double averageTime;

        for (int i = 0; i < 7; i++) {
            n *= 2;
            averageTime = 0.0;
            for (int j = 0; j < 10; j++) {
                tree = new TreeSet<>();

                s.start();
                for (int k = 0; k < n; k++) {
                    tree.add(k);
                }
                s.stop();
                averageTime += s.time();
            }

            averageTime /= 10.0;

            System.out.printf(" * | %-" + 7 + "s |", n);
            System.out.printf(" %-" + 12 + "s |", String.format("%.10f", averageTime));
            System.out.printf(" %-" + 12 + "s |\n", n);
        }
    }
}
