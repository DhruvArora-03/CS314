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

import java.util.Iterator;

/**
 * Students are to complete this class. Students should implement as many methods as they can using
 * the Iterator from the iterator method and the other methods.
 *
 */
public abstract class AbstractSet<E> implements ISet<E> {
    /**
     * Make this set empty. <br>
     * pre: none <br>
     * post: size() = 0
     */
    public void clear() {
        Iterator<E> it = this.iterator();

        // iterate through the set and remove each element
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    /**
     * Determine if item is in this set. <br>
     * pre: item != null
     * 
     * @param item element whose presence is being tested. Item may not equal null.
     * @return true if this set contains the specified item, false otherwise.
     */
    public boolean contains(E item) {
        // check preconditions
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        Iterator<E> it = this.iterator();

        // iterate through the set and check if the item is in the set
        while (it.hasNext()) {
            if (it.next().equals(item)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determine if all of the elements of otherSet are in this set. <br>
     * pre: otherSet != null
     * 
     * @param otherSet != null
     * @return true if this set contains all of the elements in otherSet, false otherwise.
     */
    public boolean containsAll(ISet<E> otherSet) {
        // check preconditions
        if (otherSet == null) {
            throw new IllegalArgumentException("otherSet cannot be null");
        }

        Iterator<E> it = otherSet.iterator();

        // check if each item in the otherSet is in this set
        while (it.hasNext()) {
            if (!this.contains(it.next())) {
                return false;
            }
        }

        return true;
    }

    /**
     * Remove the specified item from this set if it is present. pre: item != null
     * 
     * @param item the item to remove from the set. item may not equal null.
     * @return true if this set changed as a result of this operation, false otherwise
     */
    public boolean remove(E item) {
        // check preconditions
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        Iterator<E> it = this.iterator();

        // if the item is in the set, remove it
        while (it.hasNext()) {
            if (it.next().equals(item)) {
                it.remove();
                return true;
            }
        }

        return false;
    }

    /**
     * Return the number of elements of this set. pre: none
     * 
     * @return the number of items in this set
     */
    public int size() {
        int count = 0;
        Iterator<E> it = this.iterator();

        // iterate through the set and count the number of elements
        while (it.hasNext()) {
            count++;
            it.next();
        }

        return count;
    }

    /**
     * Determine if this set is equal to other. Two sets are equal if they have exactly the same
     * elements. The order of the elements does not matter. <br>
     * pre: none
     * 
     * @param other the object to compare to this set
     * @return true if other is a Set and has the same elements as this set
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // can only be equal if it is a set
        if (other instanceof ISet<?>) {
            ISet<?> otherSet = (ISet<?>) other;

            // if otherSet is not the same size as this set, they are not equal
            if (otherSet.size() != this.size()) {
                return false;
            }

            // check if all elements in otherSet are in this set
            for (Object otherItem : otherSet) {
                Iterator<E> iterator = this.iterator();
                boolean found = false;

                while (iterator.hasNext() && !found) {
                    if (otherItem.equals(iterator.next())) {
                        found = true;
                    }
                }

                // reaches here if the element was not found or the iterator has reached the end
                if (!found) {
                    return false;
                }
            }

            // reaches here if all elements in otherSet are in this set
            return true;
        }

        // other is not a set, return false
        return false;
    }

    /**
     * Create a new set that is the union of this set and otherSet. <br>
     * pre: otherSet != null <br>
     * post: returns a set that is the union of this set and otherSet. Neither this set or otherSet
     * are altered as a result of this operation. <br>
     * pre: otherSet != null
     * 
     * @param otherSet != null
     * @return a set that is the union of this set and otherSet
     */
    public ISet<E> union(ISet<E> otherSet) {
        // check preconditions
        if (otherSet == null) {
            throw new IllegalArgumentException("otherSet cannot be null");
        }

        // union(A, B) = A + diff(B, A)
        // A = intersection(A, B) + diff(A, B)
        ISet<E> result = otherSet.difference(this);
        result.addAll(this);
        return result;
    }

    /**
     * Return a String version of this set. Format is (e1, e2, ... en)
     * 
     * @return A String version of this set.
     */
    public String toString() {
        if (this.size() == 0) {
            return "()";
        }

        StringBuilder result = new StringBuilder();
        Iterator<E> it = this.iterator();
        String seperator = ", ";

        // add the first element to the string to fix fencepost problem
        result.append("(");
        result.append(it.next());

        // add the rest of the elements to the string
        while (it.hasNext()) {
            result.append(seperator);
            result.append(it.next());
        }

        // add the closing parenthesis to the string
        result.append(")");
        return result.toString();
    }
}
