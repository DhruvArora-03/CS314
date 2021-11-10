/* CS 314 STUDENTS: FILL IN THIS HEADER.
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

import java.util.List;

/**
 * Shell for a binary search tree class.
 * 
 * @author scottm
 * @param <E> The data type of the elements of this BinarySearchTree. Must
 *            implement Comparable or inherit from a class that implements
 *            Comparable.
 *
 */
public class BinarySearchTree<E extends Comparable<? super E>> {

    private BSTNode<E> root;
    // CS314 students. Add any other instance variables you want here

    // CS314 students. Add a default constructor here if you feel it is necessary.

    /**
     * Add the specified item to this Binary Search Tree if it is not already
     * present. <br>
     * pre: <tt>value</tt> != null<br>
     * post: Add value to this tree if not already present. Return true if this tree
     * changed as a result of this method call, false otherwise.
     * 
     * @param value the value to add to the tree
     * @return false if an item equivalent to value is already present in the tree,
     *         return true if value is added to the tree and size() = old size() + 1
     */
    public boolean add(E value) {
        // check preconditions
        if (value == null) {
            throw new IllegalArgumentException("Cannot add null to a BinarySearchTree");
        }
        // tree empty --> create root
        else if (root == null) {
            root = new BSTNode<E>(value);
            return true;
        }

        // else --> recurse
        return add(root, value);
    }

    /**
     * Helper method for add()
     * 
     * @param node the current node we are attempting to add at
     * @param value the value which we wish to add
     * @return true/false if the value was added or not
     * 
     */
    private boolean add(BSTNode<E> node, E value) {
        // ensure no duplicates
        if (node.getData().compareTo(value) == 0) {
            return false;
        }
        // add to left if possible, otherwise go into left subtree
        else if (node.getData().compareTo(value) > 0) {
            if (node.getLeft() == null) {
                node.setLeft(new BSTNode<E>(value));
                return true;
            } else {
                return add(node.getLeft(), value);
            }
        }
        // add to right if possible, otherwise go into right subtree
        else {
            if (node.getRight() == null) {
                node.setRight(new BSTNode<E>(value));
                return true;
            } else {
                return add(node.getRight(), value);
            }
        }
    }

    /**
     * Remove a specified item from this Binary Search Tree if it is present. <br>
     * pre: <tt>value</tt> != null<br>
     * post: Remove value from the tree if present, return true if this tree changed
     * as a result of this method call, false otherwise.
     * 
     * @param value the value to remove from the tree if present
     * @return false if value was not present returns true if value was present and
     *         size() = old size() - 1
     */
    public boolean remove(E value) {
        // check preconditions
        if (value == null) {
            throw new IllegalArgumentException("Cannot remove null from a BinarySearchTree");
        }
        // tree empty --> nothing to remove
        else if (root == null) {
            return false;
        }

        // else --> recurse
        return remove(root, value);
    }

    private boolean remove(BinarySearchTree.BSTNode<E> root2, E value) {
        // if the value is less than the root, go left
        if (root2.getData().compareTo(value) > 0) {
            if (root2.getLeft() == null) {
                return false;
            } else {
                return remove(root2.getLeft(), value);
            }
        }
        // if the value is greater than the root, go right
        else if (root2.getData().compareTo(value) < 0) {
            if (root2.getRight() == null) {
                return false;
            } else {
                return remove(root2.getRight(), value);
            }
        }
        // if the value is the root, remove it
        else {
            // if the root has no children, remove it
            if (root2.getLeft() == null && root2.getRight() == null) {
                root2 = null;
                return true;
            }
            // if the root has one child, remove it
            else if (root2.getLeft() == null || root2.getRight() == null) {
                if (root2.getLeft() == null) {
                    root2 = root2.getRight();
                } else {
                    root2 = root2.getLeft();
                }
                return true;
            }
            // if the root has two children, find the smallest value in the right subtree
            // and replace the root with that value
            else {
                BinarySearchTree.BSTNode<E> smallest = findSmallest(root2.getRight());
                root2.setData(smallest.getData());
                remove(root2.getRight(), smallest.getData());
                return true;
            }
        }
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
        return true;
    }

    /**
     * Return how many elements are in this Binary Search Tree. <br>
     * pre: none<br>
     * post: return the number of items in this tree
     * 
     * @return the number of items in this Binary Search Tree
     */
    public int size() {
        return -1;
    }

    /**
     * return the height of this Binary Search Tree. <br>
     * pre: none<br>
     * post: return the height of this tree. If the tree is empty return -1,
     * otherwise return the height of the tree
     * 
     * @return the height of this tree or -1 if the tree is empty
     */
    public int height() {
        return -2;
    }

    /**
     * Return a list of all the elements in this Binary Search Tree. <br>
     * pre: none<br>
     * post: return a List object with all data from the tree in ascending order. If
     * the tree is empty return an empty List
     * 
     * @return a List object with all data from the tree in sorted order if the tree
     *         is empty return an empty List
     */
    public List<E> getAll() {
        return null;
    }

    /**
     * return the maximum value in this binary search tree. <br>
     * pre: <tt>size()</tt> > 0<br>
     * post: return the largest value in this Binary Search Tree
     * 
     * @return the maximum value in this tree
     */
    public E max() {
        return null;
    }

    /**
     * return the minimum value in this binary search tree. <br>
     * pre: <tt>size()</tt> > 0<br>
     * post: return the smallest value in this Binary Search Tree
     * 
     * @return the minimum value in this tree
     */
    public E min() {
        return null;
    }

    /**
     * An add method that implements the add algorithm iteratively instead of
     * recursively. <br>
     * pre: data != null <br>
     * post: if data is not present add it to the tree, otherwise do nothing.
     * 
     * @param data the item to be added to this tree
     * @return true if data was not present before this call to add, false
     *         otherwise.
     */
    public boolean iterativeAdd(E data) {
        return false;
    }

    /**
     * Return the "kth" element in this Binary Search Tree. If kth = 0 the smallest
     * value (minimum) is returned. If kth = 1 the second smallest value is
     * returned, and so forth. <br>
     * pre: 0 <= kth < size()
     * 
     * @param kth indicates the rank of the element to get
     * @return the kth value in this Binary Search Tree
     */
    public E get(int kth) {
        return null;
    }

    /**
     * Return a List with all values in this Binary Search Tree that are less than
     * the parameter <tt>value</tt>. <tt>value</tt> != null<br>
     * 
     * @param value the cutoff value
     * @return a List with all values in this tree that are less than the parameter
     *         value. If there are no values in this tree less than value return an
     *         empty list. The elements of the list are in ascending order.
     */
    public List<E> getAllLessThan(E value) {
        return null;
    }

    /**
     * Return a List with all values in this Binary Search Tree that are greater
     * than the parameter <tt>value</tt>. <tt>value</tt> != null<br>
     * 
     * @param value the cutoff value
     * @return a List with all values in this tree that are greater than the
     *         parameter value. If there are no values in this tree greater than
     *         value return an empty list. The elements of the list are in ascending
     *         order.
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
     * Prints a vertical representation of this tree. The tree has been rotated
     * counter clockwise 90 degrees. The root is on the left. Each node is printed
     * out on its own row. A node's children will not necessarily be at the rows
     * directly above and below a row. They will be indented three spaces from the
     * parent. Nodes indented the same amount are at the same depth. <br>
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
