import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JFileChooser;
import javax.swing.UIManager;

/**
 * Student information for assignment:
 *
 * On OUR honor, Rohit Anantha and Dhruv Arora, this programming assignment is OUR own work and WE
 * have not provided this code to any other student.
 *
 * Number of slip days used: 2
 *
 * Student 1 (Student whose Canvas account is being used) <br>
 * UTEID: ra38355 <br>
 * email address: anantha.rohit.11@gmail.com <br>
 * TA name: Grace <br>
 * 
 * Student 2 UTEID: da32895 <br>
 * email address: dhruvarora@utexas.edu
 */

/**
 * CS 314 Students, put your results to the experiments and answers to questions here: <br>
 * CS314 Students, why is it unwise to implement all three of the intersection, union, and
 * difference methods in the AbstractSet class: <br>
 * <br>
 * Since the AbstractSet class is an abstract class, it cannot be instantiated, so it is only useful
 * to implement methods that will not be changed in the implementation of other classes. However,
 * since by the definition of Union, Intersection, and Difference, we can create Union by adding the
 * original set and the difference of A - B. However, since this is an abstract class, we can simply
 * refer to the method headers of difference(). This allows us to reduce the number of
 * implementations in the future, since we just improve efficency in the called methods, we
 * automatically improve efficency in union.
 *
 * Experiment: <br>
 * 
 * <br>
 * UnsortedSet: <br>
 * Title__________| Size __| Factor | Total Words | Factor | Distinct | Factor | Time | Factor| <br>
 * oscar.txt______| 140kb__| 1x_____| 23791_______| 1x_____| 4914_____|_1x_____| 0.176|_1x____| <br>
 * fairytales.txt_| 420kb__| 3x_____| 74530_______| 3.13x__| 11771____|_2.39x__| 0.919|_5.22x_| <br>
 * dracula.txt____| 860kb__| 2x_____| 164410______|_2.21x__| 19012____|_1.61x__| 2.153|_2.34x_| <br>
 * mobydick.txt___| 1.21mb | 1.4x___| 215864______|_1.31x__| 33568____|_1.77x__| 11.83|_5.49x_| <br>
 * warpeace.txt___| 3.20mb | 2.6x___| 566334______|_2.62x__| 41970____|_1.25x__| 19.09|_1.61x_| <br>
 * 
 * SortedSet: <br>
 * Title__________| Size __| Factor | Total Words | Factor | Distinct | Factor | Time | Factor| <br>
 * oscar.txt______| 140kb__| 1x_____| 23791_______| 1x_____| 4914_____|_1x_____| 0.040|_1x____| <br>
 * fairytales.txt_| 420kb__| 3x_____| 74530_______| 3.13x__| 11771____|_2.39x__| 0.103|_2.57x_| <br>
 * dracula.txt____| 860kb__| 2x_____| 164410______|_2.21x__| 19012____|_1.61x__| 0.215|_2.09x_| <br>
 * mobydick.txt___| 1.21mb | 1.4x___| 215864______|_1.31x__| 33568____|_1.77x__| 0.272|_1.27x_| <br>
 * warpeace.txt___| 3.20mb | 2.6x___| 566334______|_2.62x__| 41970____|_1.25x__| 0.652|_2.40x_| <br>
 * 
 * HashSet: <br>
 * Title__________| Size __| Factor | Total Words | Factor | Distinct | Factor | Time | Factor| <br>
 * oscar.txt______| 140kb__| 1x_____| 23791_______| 1x_____| 4914_____|_1x_____| 0.020|_1x____| <br>
 * fairytales.txt_| 420kb__| 3x_____| 74530_______| 3.13x__| 11771____|_2.39x__| 0.057|_2.85x_| <br>
 * dracula.txt____| 860kb__| 2x_____| 164410______|_2.21x__| 19012____|_1.61x__| 0.088|_1.54x_| <br>
 * mobydick.txt___| 1.21mb | 1.4x___| 215864______|_1.31x__| 33568____|_1.77x__| 0.114|_1.29x_| <br>
 * warpeace.txt___| 3.20mb | 2.6x___| 566334______|_2.62x__| 41970____|_1.25x__| 0.275|_2.41x_| <br>
 * 
 * TreeSet: <br>
 * Title__________| Size __| Factor | Total Words | Factor | Distinct | Factor | Time | Factor| <br>
 * oscar.txt______| 140kb__| 1x_____| 23791_______| 1x_____| 4914_____|_1x_____| 0.023|_1x____| <br>
 * fairytales.txt_| 420kb__| 3x_____| 74530_______| 3.13x__| 11771____|_2.39x__| 0.071|_3.09x_| <br>
 * dracula.txt____| 860kb__| 2x_____| 164410______|_2.21x__| 19012____|_1.61x__| 0.113|_1.59x_| <br>
 * mobydick.txt___| 1.21mb | 1.4x___| 215864______|_1.31x__| 33568____|_1.77x__| 0.165|_1.46x_| <br>
 * warpeace.txt___| 3.20mb | 2.6x___| 566334______|_2.62x__| 41970____|_1.25x__| 0.371|_2.25x_| <br>
 * 
 * 
 * What do you think the order (Big O) of the two processText methods are for each kind of Set?
 * Assume N = total number of words in a file and M = number of distinct words in the file. M = the
 * size of the set when finished. <br>
 * 
 * UnsortedSet: performs N adds, O(M^2), O(N * M ^ 2) for process text<br>
 * SortedSet: performs N adds, O(M), O(N * M) for process text<br>
 * HashSet: performs N adds O(1), O(N) for process text<br>
 * TreeSet: performs N adds O(log M), O(N * log M) for process text<br>
 * 
 * What are the orders (Big O) of your add methods? What do you think the Big O of the HashSet and
 * TreeSet add methods are? <br>
 * 
 * Unsorted Set: add O(N) <br>
 * Sorted Set: add O(N) <br>
 * Hash Set: add O(1) <br>
 * Tree Set: add O(log N) <br>
 * 
 * <br>
 * What are the differences between HashSet and TreeSet when printing out the contents of the Set?
 * 
 * Hash Set is in a random order, TreeSet in sorted order. <br>
 * 
 */

public class SetTester {

    public static void main(String[] args) throws Exception {

        // test 1
        int testNum = 1;
        // test each method in UnsortedSet
        ISet<Integer> unsorted = new UnsortedSet<>();
        // test add
        unsorted.add(1);
        unsorted.add(2);
        unsorted.add(3);
        showTestResults(unsorted.toString().equals("(1, 2, 3)"), testNum);
        testNum++;
        // test 2
        // test addAll
        UnsortedSet<Integer> unsorted2 = new UnsortedSet<>();
        unsorted2.add(4);
        unsorted2.add(5);
        unsorted2.add(6);
        unsorted.addAll(unsorted2);
        showTestResults(unsorted.toString().equals("(1, 2, 3, 4, 5, 6)"), testNum);
        testNum++;
        // test 3
        // test remove
        unsorted.remove(2);
        showTestResults(unsorted.toString().equals("(1, 3, 4, 5, 6)"), testNum);
        testNum++;
        // test 4
        // test contains
        showTestResults(unsorted.contains(1), testNum);
        testNum++;
        // test 5
        // test size
        showTestResults(unsorted.size() == 5, testNum);
        testNum++;
        // test 6
        // test clear
        unsorted.clear();
        showTestResults(unsorted.size() == 0, testNum);
        testNum++;
        // test 7
        // test containsAll
        showTestResults(!unsorted.containsAll(unsorted2), testNum);
        testNum++;
        // test 8
        // test equals
        showTestResults(!unsorted.equals(unsorted2), testNum);
        testNum++;
        // test 9
        // test difference
        UnsortedSet<Integer> unsorted3 = new UnsortedSet<>();
        unsorted3.add(1);
        unsorted3.add(2);
        unsorted3.add(3);
        unsorted = unsorted2.difference(unsorted3);
        showTestResults(unsorted.toString().equals("(4, 5, 6)"), testNum);
        testNum++;
        // test 10
        // test intersection
        unsorted2.add(1);
        unsorted2.add(2);
        unsorted2.add(3);
        unsorted = unsorted2.intersection(unsorted3);
        showTestResults(unsorted.toString().equals("(1, 2, 3)"), testNum);
        testNum++;
        // test 11
        // test union
        unsorted = unsorted3.union(unsorted2);
        showTestResults(unsorted.toString().equals("(4, 5, 6, 1, 2, 3)"), testNum);
        testNum++;
        // test 12
        // test iterator
        Iterator<Integer> it = unsorted.iterator();
        showTestResults(it.next() == 4, testNum);
        testNum++;

        ISet<Integer> sorted = new SortedSet<>();
        // test 13
        // test add
        sorted.add(3);
        sorted.add(1);
        sorted.add(2);
        showTestResults(sorted.toString().equals("(1, 2, 3)"), testNum);
        testNum++;

        // test 14
        // test addAll
        ISet<Integer> sorted2 = new SortedSet<>();
        sorted2.add(5);
        sorted2.add(4);
        sorted2.add(6);
        sorted.addAll(sorted2);
        showTestResults(sorted.toString().equals("(1, 2, 3, 4, 5, 6)"), testNum);
        testNum++;

        // test 15
        // test contains
        showTestResults(sorted.contains(1), testNum);
        testNum++;

        // test 16
        // test containsAll
        showTestResults(sorted.containsAll(sorted2), testNum);
        testNum++;

        // test 17
        // test clear
        sorted.clear();
        showTestResults(sorted.size() == 0, testNum);
        testNum++;

        // test 18
        // test remove
        sorted.add(1);
        sorted.add(2);
        sorted.add(3);
        sorted.remove(2);
        showTestResults(sorted.toString().equals("(1, 3)"), testNum);
        testNum++;

        // test 19
        // test min
        showTestResults(((SortedSet<Integer>) sorted).min() == 1, testNum);
        testNum++;

        // test 20
        // test max
        showTestResults(((SortedSet<Integer>) sorted).max() == 3, testNum);
        testNum++;

        // test 21
        // test difference
        ISet<Integer> sorted3 = new SortedSet<>();
        sorted3.add(1);
        sorted3.add(2);
        sorted3.add(3);
        sorted = sorted2.difference(sorted3);
        showTestResults(sorted.toString().equals("(4, 5, 6)"), testNum);
        testNum++;

        // test 22
        // test equals
        showTestResults(sorted.equals(sorted2), testNum);
        testNum++;

        // test 23
        // test intersection
        sorted = sorted2.intersection(sorted3);
        showTestResults(sorted.toString().equals("()"), testNum);
        testNum++;

        // test 24
        // test iterator
        sorted.add(1);
        Iterator<Integer> it2 = sorted.iterator();
        showTestResults(it2.next() == 1, testNum);
        testNum++;

        // test 25
        // test size
        sorted.add(2);
        sorted.add(3);
        showTestResults(sorted.size() == 3, testNum);
        testNum++;

        // test 26
        // test union
        sorted = sorted2.union(sorted3);
        showTestResults(sorted.toString().equals("(1, 2, 3, 4, 5, 6)"), testNum);

        // CS314 Students. Uncomment this section when ready to
        // run your experiments
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Unable to change look and feel");
        }
        Scanner sc = new Scanner(System.in);
        String response = "";
        do {
            largeTest();
            System.out.print("Another file? Enter y to do another file: ");
            response = sc.next();
        } while (response != null && response.length() > 0
                && response.substring(0, 1).equalsIgnoreCase("y"));

        sc.close();
    }

    // print out results of test
    private static <E> void showTestResults(boolean passed, int testNumber) {
        if (passed) {
            System.out.println("Passed test " + testNumber);
        } else {
            System.out.print("Failed test " + testNumber);
        }

    }

    /*
     * Method asks user for file and compares run times to add words from file to various sets,
     * including CS314 UnsortedSet and SortedSet and Java's TreeSet and HashSet.
     */
    private static void largeTest() {
        System.out.println();
        System.out.println(
                "Opening Window to select file. " + "You may have to minimize other windows.");
        String text = convertFileToString();
        Scanner keyboard = new Scanner(System.in);
        System.out.println();
        System.out.println("***** CS314 SortedSet: *****");
        processTextCS314Sets(new SortedSet<String>(), text, keyboard);
        System.out.println("****** CS314 UnsortedSet: *****");
        processTextCS314Sets(new UnsortedSet<String>(), text, keyboard);
        System.out.println("***** Java HashSet ******");
        processTextJavaSets(new HashSet<String>(), text, keyboard);
        System.out.println("***** Java TreeSet ******");
        processTextJavaSets(new TreeSet<String>(), text, keyboard);
        keyboard.close();
    }

    /*
     * pre: set != null, text != null Method to add all words in text to the given set. Words are
     * delimited by white space. This version for CS314 sets.
     */
    private static void processTextCS314Sets(ISet<String> set, String text, Scanner keyboard) {
        Stopwatch s = new Stopwatch();
        Scanner sc = new Scanner(text);
        int totalWords = 0;
        s.start();
        while (sc.hasNext()) {
            totalWords++;
            set.add(sc.next());
        }
        s.stop();
        sc.close();

        showResultsAndWords(set, s, totalWords, set.size(), keyboard);
    }

    /*
     * pre: set != null, text != null Method to add all words in text to the given set. Words are
     * delimited by white space. This version for Java Sets.
     */
    private static void processTextJavaSets(Set<String> set, String text, Scanner keyboard) {
        Stopwatch s = new Stopwatch();
        Scanner sc = new Scanner(text);
        int totalWords = 0;
        s.start();
        while (sc.hasNext()) {
            totalWords++;
            set.add(sc.next());
        }
        s.stop();
        sc.close();

        showResultsAndWords(set, s, totalWords, set.size(), keyboard);
    }

    /*
     * Show results of add words to given set.
     */
    private static <E> void showResultsAndWords(Iterable<E> set, Stopwatch s, int totalWords,
            int setSize, Scanner keyboard) {

        System.out.println("Time to add the elements in the text to this set: " + s.toString());
        System.out.println("Total number of words in text including duplicates: " + totalWords);
        System.out.println("Number of distinct words in this text " + setSize);

        System.out.print("Enter y to see the contents of this set: ");
        String response = keyboard.next();

        if (response != null && response.length() > 0
                && response.substring(0, 1).equalsIgnoreCase("y")) {
            for (Object o : set) {
                System.out.println(o);
            }
        }
        System.out.println();
    }

    /*
     * Ask user to pick a file via a file choosing window and convert that file to a String. Since
     * we are evaluating the file with many sets convert to string once instead of reading through
     * file multiple times.
     */
    private static String convertFileToString() {
        // create a GUI window to pick the text to evaluate
        JFileChooser chooser = new JFileChooser(".");
        StringBuilder text = new StringBuilder();
        int retval = chooser.showOpenDialog(null);

        chooser.grabFocus();

        // read in the file
        if (retval == JFileChooser.APPROVE_OPTION) {
            File source = chooser.getSelectedFile();
            Scanner s = null;
            try {
                s = new Scanner(new FileReader(source));

                while (s.hasNextLine()) {
                    text.append(s.nextLine());
                    text.append(" ");
                }

                s.close();
            } catch (IOException e) {
                System.out.println("An error occured while trying to read from the file: " + e);
            } finally {
                if (s != null) {
                    s.close();
                }
            }
        }

        return text.toString();
    }
}
