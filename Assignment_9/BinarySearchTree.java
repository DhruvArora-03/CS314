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
import java.util.List;

/**
 * Shell for a binary search tree class.
 * 
 * @author scottm
 * @param <E> The data type of the elements of this BinarySearchTree. Must implement Comparable or
 *        inherit from a class that implements Comparable.
 *
 */
public class BinarySearchTree<E extends Comparable<? super E>> {
    private BSTNode<E> root;
    private int size;

    /**
     * Constructs an empty BinarySearchTree.
     */
    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    /**
     * Add the specified item to this Binary Search Tree if it is not already present. <br>
     * pre: <tt>value</tt> != null<br>
     * post: Add value to this tree if not already present. Return true if this tree changed as a
     * result of this method call, false otherwise.
     * 
     * @param value the value to add to the tree
     * @return false if an item equivalent to value is already present in the tree, return true if
     *         value is added to the tree and size() = old size() + 1
     */
    public boolean add(E value) {
        // check preconditions
        if (value == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        int oldSize = size;
        root = addHelper(root, value);
        return size > oldSize; // if size increase --> something was added
    }

    /**
     * Helper method for add(). Go until 'fall off' tree or we find the val already exists
     * 
     * @param node the current node we are attempting to add at
     * @param value the value which we wish to add
     * @return the node after the add is complete
     * 
     */
    private BSTNode<E> addHelper(BSTNode<E> node, E value) {
        // base case
        if (node == null) {
            size++;
            return new BSTNode<>(value);
        }

        int comparison = value.compareTo(node.data);
        if (comparison < 0) { // value is less --> go left
            node.left = addHelper(node.left, value);
        } else if (comparison > 0) { // value is greater --> go right
            node.right = addHelper(node.right, value);
        }
        // else value is already in the tree

        return node;
    }

    /**
     * Remove a specified item from this Binary Search Tree if it is present. <br>
     * pre: <tt>value</tt> != null<br>
     * post: Remove value from the tree if present, return true if this tree changed as a result of
     * this method call, false otherwise.
     * 
     * @param value the value to remove from the tree if present
     * @return false if value was not present returns true if value was present and size() = old
     *         size() - 1
     */
    public boolean remove(E value) {
        // check preconditions
        if (value == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        int oldSize = size;
        root = removeHelper(root, value);
        return size < oldSize; // if size decrease --> something was removed
    }

    /**
     * Helper method for remove(). Go until 'fall off' tree or we find the val to remove
     * 
     * @param node the current node we are attempting to remove at
     * @param value the value which we wish to remove
     * @return the node after the remove is complete
     * 
     */
    private BSTNode<E> removeHelper(BSTNode<E> node, E value) {
        // base case
        if (node == null) {
            return null;
        }

        int comparison = value.compareTo(node.data);
        if (comparison < 0) { // value is less --> go left
            node.left = removeHelper(node.left, value);
        } else if (comparison > 0) { // value is greater --> go right
            node.right = removeHelper(node.right, value);
        } else { // value is equal to node.data --> remove it
            if (node.left == null) { // only right child
                size--;
                return node.right;
            } else if (node.right == null) { // only left child
                size--;
                return node.left;
            } else { // two children --> replace with smallest value in right subtree
                node.data = min(node.right);
                // now go remove the duplicate
                node.right = removeHelper(node.right, node.data);
            }
        }
        return node;
    }

    /**
     * Check to see if the specified element is in this Binary Search Tree. <br>
     * pre: <tt>value</tt> != null<br>
     * post: return true if value is present in tree, false otherwise
     * 
     * @param value the value to look for in the tree
     * @return true if value is present in this tree, false otherwise
     */
    public boolean isPresent(E value) {
        // check preconditions
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        return isPresent(root, value);
    }

    /**
     * Helper method for isPresent()
     * 
     * @param node the node we are currently at
     * @param value the value we are looking for
     * @return true/false if the value is found or not
     */
    private boolean isPresent(BSTNode<E> node, E value) {
        if (node == null) {
            return false;
        }

        int comparison = node.data.compareTo(value);
        // the value is here
        if (comparison == 0) {
            return true;
        }
        // search left subtree
        else if (comparison > 0) {
            return isPresent(node.left, value);
        }
        // search right subtree
        else {
            return isPresent(node.right, value);
        }
    }

    /**
     * Return how many elements are in this Binary Search Tree. <br>
     * pre: none<br>
     * post: return the number of items in this tree
     * 
     * @return the number of items in this Binary Search Tree
     */
    public int size() {
        return size;
    }

    /**
     * return the height of this Binary Search Tree. <br>
     * pre: none<br>
     * post: return the height of this tree. If the tree is empty return -1, otherwise return the
     * height of the tree
     * 
     * @return the height of this tree or -1 if the tree is empty
     */
    public int height() {
        return height(root);
    }

    /**
     * Helper method for height()
     * 
     * @param node the node we are at
     * @return the height of this subtree
     */
    private int height(BSTNode<E> node) {
        // base case
        if (node == null) {
            return -1;
        }

        // recurse to find max height of the two child subtrees and return 1 + that
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Return a list of all the elements in this Binary Search Tree. <br>
     * pre: none<br>
     * post: return a List object with all data from the tree in ascending order. If the tree is
     * empty return an empty List
     * 
     * @return a List object with all data from the tree in sorted order if the tree is empty return
     *         an empty List
     */
    public List<E> getAll() {
        List<E> result = new ArrayList<>();
        getAll(result, root);
        return result;
    }

    /**
     * Helper method for getAll()
     * 
     * @return a List<E> of all
     */
    private void getAll(List<E> result, BSTNode<E> node) {
        if (node != null) { // if !(base case)
            // in order traversal: left, root, right
            getAll(result, node.left);
            result.add(node.data);
            getAll(result, node.right);
        }
    }

    /**
     * return the maximum value in this binary search tree. <br>
     * pre: <tt>size()</tt> > 0<br>
     * post: return the largest value in this Binary Search Tree
     * 
     * @return the maximum value in this tree
     */
    public E max() {
        // check preconditions
        if (size == 0) {
            throw new IllegalStateException("cannot find max of an empty tree");
        }

        return max(root);
    }

    /**
     * Helper method for max()
     * 
     * @param node the node we are currently at
     * @return the maximum value in this subtree
     */
    private E max(BSTNode<E> node) {
        // go right until we hit a null, and then return the data
        if (node.right != null) {
            return max(node.right);
        }

        return node.data;
    }

    /**
     * return the minimum value in this binary search tree. <br>
     * pre: <tt>size()</tt> > 0<br>
     * post: return the smallest value in this Binary Search Tree
     * 
     * @return the minimum value in this tree
     */
    public E min() {
        // check preconditions
        if (size == 0) {
            throw new IllegalStateException("cannot find min of an empty tree");
        }

        return min(root);
    }

    /**
     * Helper method for max()
     * 
     * @param node the node we are currently at
     * @return the maximum value in this subtree
     */
    private E min(BSTNode<E> node) {
        // go left until we hit a null, and then return the data
        if (node.left != null) {
            return min(node.left);
        }

        return node.data;
    }

    /**
     * An add method that implements the add algorithm iteratively instead of recursively. <br>
     * pre: data != null <br>
     * post: if data is not present add it to the tree, otherwise do nothing.
     * 
     * @param data the item to be added to this tree
     * @return true if data was not present before this call to add, false otherwise.
     */
    public boolean iterativeAdd(E data) {
        // check preconditions
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        BSTNode<E> prev = null;
        BSTNode<E> curr = root;

        while (curr != null) {
            prev = curr; // keep track of the previous node

            int comparison = curr.data.compareTo(data);
            if (comparison > 0) { // data belongs in left subtree
                curr = curr.left;
            } else if (comparison < 0) { // data belongs in right subtree
                curr = curr.right;
            } else {
                // value already exists
                return false;
            }
        }

        curr = new BSTNode<>(data); // curr now represents the node to be added
        if (prev == null) { // prev == null --> tree empty --> init root
            root = curr;
        } else if (prev.data.compareTo(data) > 0) { // value belongs on left
            prev.left = curr;
        } else { // value belongs on right
            prev.right = curr;
        }
        size++;
        return true;
    }

    /**
     * Return the "kth" element in this Binary Search Tree. If kth = 0 the smallest value (minimum)
     * is returned. If kth = 1 the second smallest value is returned, and so forth. <br>
     * pre: 0 <= kth < size()
     * 
     * @param kth indicates the rank of the element to get
     * @return the kth value in this Binary Search Tree
     */
    public E get(int kth) {
        // check preconditions
        if (kth < 0 || kth >= size) {
            throw new IllegalArgumentException("kth cannot be out of bounds");
        }

        int[] k = {kth}; // so that we can pass by reference
        return get(root, k);
    }

    /**
     * Helper method for get(). Get the kth element in the subtree of node. <br>
     * 
     * @param node the root of the subtree we are currently at
     * @param k singleton array containing the value of k (used to pass by reference)
     * @return the kth element in the subtree of node
     */
    private E get(BSTNode<E> node, int[] k) {
        if (node == null) {
            return null;
        }

        // check left subtree
        E result = get(node.left, k);

        // if not found in left subtree, check this node and decrement k (if we are not at kth elem)
        if (result == null && k[0] <= 0) {
            result = node.data; // k == 0 --> we are at the kth element
        } else {
            k[0]--;
        }

        // check right subtree if we have not found kth in left subtree or current node
        result = result == null ? get(node.right, k) : result;

        return result;
    }

    /**
     * Return a List with all values in this Binary Search Tree that are less than the parameter
     * <tt>value</tt>. <tt>value</tt> != null<br>
     * 
     * @param value the cutoff value
     * @return a List with all values in this tree that are less than the parameter value. If there
     *         are no values in this tree less than value return an empty list. The elements of the
     *         list are in ascending order.
     */
    public List<E> getAllLessThan(E value) {
        // check preconditions
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        List<E> result = new ArrayList<>();
        getAllLessThan(result, root, value);
        return result;
    }

    /**
     * Helper method for getAllLessThan()
     * 
     * @param result list to store results
     * @param node the subtree we are currently at
     * @param value the value we want less than
     */
    private void getAllLessThan(List<E> result, BSTNode<E> node, E value) {
        if (node != null) {
            // check left subtree always
            getAllLessThan(result, node.left, value);

            if (node.data.compareTo(value) < 0) {
                result.add(node.data); // if this node's data < value --> add it to list
                getAllLessThan(result, node.right, value); // only have to check if node is valid
            }
        }
    }

    /**
     * Return a List with all values in this Binary Search Tree that are greater than the parameter
     * <tt>value</tt>. <tt>value</tt> != null<br>
     * 
     * @param value the cutoff value
     * @return a List with all values in this tree that are greater than the parameter value. If
     *         there are no values in this tree greater than value return an empty list. The
     *         elements of the list are in ascending order.
     */
    public List<E> getAllGreaterThan(E value) {
        // check preconditions
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        List<E> result = new ArrayList<>();
        getAllGreaterThan(result, root, value);
        return result;
    }

    private void getAllGreaterThan(List<E> result, BSTNode<E> node, E value) {
        if (node != null) {
            if (node.data.compareTo(value) > 0) {
                getAllGreaterThan(result, node.left, value); // only have to check if node is valid
                result.add(node.data); // if this node's data > value --> add it to list
            }

            // check right subtree always
            getAllGreaterThan(result, node.right, value);
        }
    }

    /**
     * Find the number of nodes in this tree at the specified depth. <br>
     * pre: none
     * 
     * @param d The target depth.
     * @return The number of nodes in this tree at a depth equal to the parameter d.
     */
    public int numNodesAtDepth(int d) {
        return numNodesAtDepth(root, d);
    }

    private int numNodesAtDepth(BSTNode<E> node, int d) {
        if (node == null) { // fell off tree before reaching depth d
            return 0;
        } else if (d <= 0) { // found depth d --> + 1
            return 1;
        } else { // search deeper
            return numNodesAtDepth(node.left, d - 1) + numNodesAtDepth(node.right, d - 1);
        }
    }

    /**
     * Prints a vertical representation of this tree. The tree has been rotated counter clockwise 90
     * degrees. The root is on the left. Each node is printed out on its own row. A node's children
     * will not necessarily be at the rows directly above and below a row. They will be indented
     * three spaces from the parent. Nodes indented the same amount are at the same depth. <br>
     * pre: none
     */
    public void printTree() {
        printTree(root, "");
    }

    private void printTree(BSTNode<E> n, String spaces) {
        if (n != null) {
            printTree(n.getRight(), spaces + "  ");
            System.out.println(spaces + n.getData());
            printTree(n.getLeft(), spaces + "  ");
        }
    }

    private static class BSTNode<E extends Comparable<? super E>> {
        private E data;
        private BSTNode<E> left;
        private BSTNode<E> right;

        public BSTNode() {
            this(null);
        }

        public BSTNode(E initValue) {
            this(null, initValue, null);
        }

        public BSTNode(BSTNode<E> initLeft, E initValue, BSTNode<E> initRight) {
            data = initValue;
            left = initLeft;
            right = initRight;
        }

        public E getData() {
            return data;
        }

        public BSTNode<E> getLeft() {
            return left;
        }

        public BSTNode<E> getRight() {
            return right;
        }

        public void setData(E theNewValue) {
            data = theNewValue;
        }

        public void setLeft(BSTNode<E> theNewLeft) {
            left = theNewLeft;
        }

        public void setRight(BSTNode<E> theNewRight) {
            right = theNewRight;
        }
    }
}
