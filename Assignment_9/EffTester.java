// Efficiency tester for BST
// Author: Jeriah Yu
// The tests in here for remove, add, get, less than and greater than should happen instantaneously
// or near-instantaneously, even though the graph has 100000 elements. If they take too long or
// you get a stackoverflow error, something is probably wrong.
import java.util.Arrays;
import java.util.List;

public class EffTester {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Stopwatch s = new Stopwatch();
        int size = 100000;

        System.out.println("Generating...");
        //Test 1: Get efficiency
        for (int i = 0; i < size; i++) {
            bst.iterativeAdd(i);
        }
        System.out.print("Test 1...");
        //These should be near instantaneous
        s.start();
        int a = bst.size();
        int b = bst.get(3);
        s.stop();
        if (s.time() < 0.1 && a == size && b == 3) {
            System.out.print("PASSED ");
        } else {
            System.out.print("FAILED ");
        }
        System.out.println(s);

        System.out.print("Test 2...");
        s.start();
        boolean c = bst.remove(0);
        boolean d = bst.add(0);
        List<Integer> e = bst.getAllLessThan(1);
        boolean f = bst.remove(2);
        List<Integer> g = bst.getAllLessThan(2);
        a = bst.numNodesAtDepth(1);
        s.stop();
        if (s.time() < 0.1 && c && d && f && a == 2 && e.equals(Arrays.asList(new Integer[]{0})) && g.equals(Arrays.asList(new Integer[]{0, 1}))) {
            System.out.print("PASSED ");
        } else {
            System.out.print("FAILED ");
        }
        System.out.println(s);

        System.out.println("Generating...");
        bst = new BinarySearchTree<Integer>();
        for (int i = 0; i > size * -1; i--) {
            bst.iterativeAdd(i);
        }
        System.out.print("Test 3...");
        s.start();
        c = bst.remove(0);
        d = bst.add(0);
        e = bst.getAllGreaterThan(-1);
        f = bst.remove(-2);
        g = bst.getAllGreaterThan(-2);
        a = bst.numNodesAtDepth(1);
        s.stop();
        if (s.time() < 0.1 && c && d && f && a == 2 && e.equals(Arrays.asList(new Integer[]{0})) && g.equals(Arrays.asList(new Integer[]{-1, 0}))) {
            System.out.print("PASSED ");
        } else {
            System.out.print("FAILED ");
        }
        System.out.println(s);
    }
}
