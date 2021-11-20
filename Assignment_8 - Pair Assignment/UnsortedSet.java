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
import java.util.ArrayList;

/**
 * A simple implementation of an ISet. Elements are not in any particular order. Students are to
 * implement methods that were not implemented in AbstractSet and override methods that can be done
 * more efficiently. An ArrayList must be used as the internal storage container.
 *
 */
public class UnsortedSet<E> extends AbstractSet<E> {
    private ArrayList<E> myCon;

    /**
     * Constructs an empty set.
     * 
     * Big O: O(1)
     * 
     * @post size() = 0
     */
    public UnsortedSet() {
        myCon = new ArrayList<>();
    }

    /**
     * Add an item to this set. <br>
     * item != null
     * 
     * Big O: O(N)
     * 
     * @param item the item to be added to this set. item may not equal null.
     * @return true if this set changed as a result of this operation, false otherwise.
     */
    public boolean add(E item) {
        // check precondition
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        // ensure property of set
        else if (myCon.contains(item)) {
            return false;
        }
        myCon.add(item);

        return true;
    }

    /**
     * A union operation. Add all items of otherSet that are not already present in this set to this
     * set.
     * 
     * Big O: O(N^2)
     * 
     * @param otherSet != null
     * @return true if this set changed as a result of this operation, false otherwise.
     */
    public boolean addAll(ISet<E> otherSet) {
        // check preconditions
        if (otherSet == null) {
            throw new IllegalArgumentException("otherSet cannot be null");
        }

        // use add method to add all items (add handles duplicates)
        boolean changed = false;
        for (E item : otherSet) {
            // changed will remain true after first successful add
            changed = add(item) || changed;
        }

        return changed;
    }

    /**
     * Make this set empty. <br>
     * 
     * Big O: O(N)
     * 
     * pre: none <br>
     * post: size() = 0
     */
    @Override
    public void clear() {
        myCon.clear();
    }

    /**
     * Create a new set that is the difference of this set and otherSet. Return an ISet of elements
     * that are in this Set but not in otherSet. Also called the relative complement. <br>
     * Example: If ISet A contains [X, Y, Z] and ISet B contains [W, Z] then A.difference(B) would
     * return an ISet with elements [X, Y] while B.difference(A) would return an ISet with elements
     * [W]. <br>
     * pre: otherSet != null <br>
     * post: returns a set that is the difference of this set and otherSet. Neither this set or
     * otherSet are altered as a result of this operation. <br>
     * pre: otherSet != null
     * 
     * Big O: O(N^2)
     * 
     * @param otherSet != null
     * @return a set that is the difference of this set and otherSet
     */
    @Override
    public ISet<E> difference(ISet<E> otherSet) {
        // check preconditions
        if (otherSet == null) {
            throw new IllegalArgumentException("otherSet cannot be null");
        }

        UnsortedSet<E> newSet = new UnsortedSet<>();

        // add all items from this set that are not in otherSet
        for (E item : this) {
            if (!otherSet.contains(item)) {
                newSet.myCon.add(item); // add direct since we know it isn't a duplicate
            }
        }

        return newSet;
    }

    /**
     * create a new set that is the intersection of this set and otherSet. <br>
     * pre: otherSet != null<br>
     * <br>
     * post: returns a set that is the intersection of this set and otherSet. Neither this set or
     * otherSet are altered as a result of this operation. <br>
     * pre: otherSet != null
     * 
     * Big O: O(N^2)
     * 
     * @param otherSet != null
     * @return a set that is the intersection of this set and otherSet
     */
    @Override
    public ISet<E> intersection(ISet<E> otherSet) {
        // check preconditions
        if (otherSet == null) {
            throw new IllegalArgumentException("otherSet cannot be null");
        }

        UnsortedSet<E> newSet = new UnsortedSet<>();

        // add all items from this set that are in otherSet
        for (E item : this) {
            if (otherSet.contains(item)) {
                newSet.myCon.add(item); // add direct since we know it isn't a duplicate
            }
        }

        return newSet;
    }

    /**
     * Return an Iterator object for the elements of this set. pre: none
     * 
     * Big O: O(1)
     * 
     * @return an Iterator object for the elements of this set
     */
    @Override
    public Iterator<E> iterator() {
        return myCon.iterator();
    }

    /**
     * Return the number of elements of this set. pre: none
     * 
     * Big O: O(1)
     * 
     * @return the number of items in this set
     */
    @Override
    public int size() {
        return myCon.size();
    }

}
