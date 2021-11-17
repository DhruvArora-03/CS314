/*
 * CS 314 STUDENTS: FILL IN THIS HEADER.
 *
 * Student information for assignment:
 *
 * On my honor, Dhruv Arora, this programming assignment is my own work and I have not provided this
 * code to any other student.
 *
 * UTEID: da32895 email address: dhruvarora@utexas.edu TA name: Grace Number of slip days I am
 * using: 0
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
        // tree empty --> nothing to remove
        else if (root == null) {
            return false;
        }
        // the value to remove is at root
        else if (root.data.equals(value)) {
            root = removeNode(root);
            // // no children --> remove root
            // if (root.left == null && root.right == null) {
            // root = null;
            // }
            // // one child --> set child as root
            // else if (root.left == null || root.right == null) {
            // root = root.left == null ? root.left : root.right;
            // }
            // // two children --> set smallest in the right tree as the new root
            // else {

            // BSTNode<E> parentOfSmallest = findParentOfSmallest(root.right);
            // // set root's left and right to the left and right of the new root
            // parentOfSmallest.left.left = root.left;
            // parentOfSmallest.left.right = root.right;
            // root = parentOfSmallest.left;
            // // delete the reference to the new root's old position
            // if (parentOfSmallest.left.right != null) {
            // parentOfSmallest.left = parentOfSmallest.left.right;
            // } else {
            // parentOfSmallest.left = null;
            // }
            // }
            size--;
            return true;
        }

        // else --> recurse
        return remove(root, value);
    }

    /**
     * Helper method for remove()
     * 
     * @param node the current node we are attempting to remove at, node != null
     * @param value the value which we wish to remove, value != null
     * @return true/false if the value was removed or not
     * 
     */
    private boolean remove(BSTNode<E> node, E value) {
        // value is on the left
        if (node.data.compareTo(value) > 0) {
            if (node.left == null) {
                return false;
            } else if (node.left.data.equals(value)) {
                node.left = removeNode(node.left);
                size--;
                return true;
            }

            return remove(node.left, value);
        }
        // value is on the right (value should never be in this node)
        else {
            if (node.right == null) {
                return false;
            } else if (node.right.data.equals(value)) {
                node.right = removeNode(node.right);
                size--;
                return true;
            }

            return remove(node.right, value);
        }
    }

    /**
     * Removes the given node and returns the node that should 'replace' it
     * 
     * @param node the BSTNode<E> to be removed
     * @return the new node that should replace the removed one
     */
    private BSTNode<E> removeNode(BSTNode<E> node) {
        // no children --> no need to replace
        if (node.left == null && node.right == null) {
            return null;
        }
        // one child --> gets replaced by child
        else if (node.left == null || node.right == null) {
            return node.left == null ? node.right : node.left;
        }
        // two children --> gets replaced by the smallest node in the right tree
        else {
            BSTNode<E> parentOfSmallest = findParentOfSmallest(node.right); //TODO: fix this shit
            // save the node so that reference isn't lost when removing smallest from tree
            BSTNode<E> newNode = new BSTNode<E>(node.left, parentOfSmallest.left.data, node.right);
            // delete the reference to the new node's old position
            parentOfSmallest.left = removeNode(parentOfSmallest.left);

            return newNode;
        }
    }

    /**
     * Find the smallest element in the tree rooted at the specified node.
     * 
     * @param node the node to start the search at
     * @return the smallest element in the
     */
    private BSTNode<E> findParentOfSmallest(BSTNode<E> node) {
        // if the left child doesn't have a child --> we are at parent of smallest
        if (node.left.left == null) {
            return node;
        }

        return findParentOfSmallest(node.left);
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

        // if empty tree --> false, else --> check
        return root == null ? false : isPresent(root, value);
    }

    /**
     * Helper method for isPresent()
     * 
     * @param node the node we are currently at
     * @param value the value we are looking for
     * @return true/false if the value is found or not
     */
    private boolean isPresent(BSTNode<E> node, E value) {
        int comparison = node.data.compareTo(value);
        // if the current node has the value
        if (comparison == 0) {
            return true;
        }
        // search left subtree if exists
        else if (comparison > 0) {
            return node.left == null ? false : isPresent(node.left, value);
        }
        // search right subtree if exists
        else {
            return node.right == null ? false : isPresent(node.right, value);
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
        return root == null ? -1 : height(root);
    }

    /**
     * Helper method for height()
     * 
     * @param node the node we are at
     * @return the height of this subtree
     */
    private int height(BSTNode<E> node) {
        if (node == null) {
            return 0;
        }
        // find max height in the child subtrees
        int maxChildHeight = Math.max(height(node.left), height(node.right));
        // we only 'include' this node if it has a child
        return node.left == null && node.right == null ? 0 : 1 + maxChildHeight;
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
        if (node != null) {
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
        return max(root);
    }

    /**
     * Helper method for max()
     * 
     * @param node the node we are currently at
     * @return the maximum value in this subtree
     */
    private E max(BSTNode<E> node) {
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
        return min(root);
    }

    /**
     * Helper method for max()
     * 
     * @param node the node we are currently at
     * @return the maximum value in this subtree
     */
    private E min(BSTNode<E> node) {
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
        BSTNode<E> prev = null;
        BSTNode<E> curr = root;

        while (curr != null) {
            prev = curr;

            int comparison = curr.data.compareTo(data);
            if (comparison > 0) {
                // data belongs in left subtree
                curr = curr.left;
            } else if (comparison < 0) {
                // data belongs in right subtree
                curr = curr.right;
            } else {
                // value already exists
                return false;
            }
        }

        curr = new BSTNode<>(data); // curr now represents the node to be added
        // the tree is empty
        if (prev == null) {
            root = curr;
        }
        // value belongs on left
        else if (prev.data.compareTo(data) > 0) {
            prev.left = curr;
        }
        // value belongs on right
        else {
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
        // special cases of min and max
        else if (kth == 0) {
            return min();
        } else if (kth == size) {
            return max();
        }

        return get(root, kth);
    }

    /**
     * 
     * 
     * @param node
     * @param k
     * @return
     */
    private E get(BSTNode<E> node, Integer k) {
        E result = null;
        if (node != null) {
            result = get(node.left, k);
            if (k <= 0) {
                return node.data;
            } else {
                k--;
            }
            // only call get(node.right) if result is null
            result = result != null ? result : get(node.right, k);
        }

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
        return null;
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
        return null;
    }

    /**
     * Find the number of nodes in this tree at the specified depth. <br>
     * pre: none
     * 
     * @param d The target depth.
     * @return The number of nodes in this tree at a depth equal to the parameter d.
     */
    public int numNodesAtDepth(int d) {
        return -1;
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
