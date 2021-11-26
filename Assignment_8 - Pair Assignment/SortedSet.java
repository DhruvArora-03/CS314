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
 * In this implementation of the ISet interface the elements in the Set are maintained in ascending
 * order.
 * 
 * The data type for E must be a type that implements Comparable.
 * 
 * Implement methods that were not implemented in AbstractSet and override methods that can be done
 * more efficiently. An ArrayList must be used as the internal storage container. For methods
 * involving two set, if that method can be done more efficiently if the other set is also a
 * SortedSet, then do so.
 */
public class SortedSet<E extends Comparable<? super E>> extends AbstractSet<E> {
    private ArrayList<E> myCon;

    /**
     * create an empty SortedSet
     * 
     * Big O: O(1)
     */
    public SortedSet() {
        myCon = new ArrayList<>();
    }

    /**
     * create a SortedSet out of a set. <br>
     * 
     * Big O: O(NlogN)
     * 
     * @param other != null
     */
    public SortedSet(ISet<E> other) {
        // check preconditions
        if (other == null) {
            throw new IllegalArgumentException("other can not be null");
        }
        myCon = new ArrayList<>();
        for (E e : other) {
            myCon.add(e);
        }
        sort();
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
    @Override
    public boolean add(E item) {
        // check preconditions
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        int index = locationToAdd(item);
        // index pos --> element found already in set (duplicate)
        if (index >= 0) {
            return false;
        }

        // undo the 'negative transformation' done at the end of locationToAdd()
        myCon.add((-1 * index) - 1, item);
        return true;
    }

    /**
     * A union operation. Add all items of otherSet that are not already present in this set to this
     * set.
     * 
     * Big O: O(N) if otherSet is a SortedSet, O(NlogN) if otherSet is a Set
     * 
     * @param otherSet != null
     * @return true if this set changed as a result of this operation, false otherwise.
     */
    @Override
    public boolean addAll(ISet<E> otherSet) {
        // check preconditions
        if (otherSet == null) {
            throw new IllegalArgumentException("otherSet cannot be null");
        }

        int oldSize = myCon.size();

        // if otherSet is a SortedSet use the optimized method
        if (otherSet instanceof SortedSet<?>) {
            // merge the two sets
            myCon = mergeWithList(((SortedSet<E>) otherSet).myCon);

        } else {
            // if otherSet is not a SortedSet, use add method on each element
            for (E item : otherSet) {
                add(item);
            }
        }

        return oldSize < myCon.size();
    }

    /**
     * Combine two sorted ArrayList's into one sorted ArrayList.
     * 
     * Big O: O(N)
     * 
     * @param other != null
     */
    private ArrayList<E> mergeWithList(ArrayList<E> otherList) {
        // check preconditions
        if (otherList == null) {
            throw new IllegalArgumentException("otherList cannot be null");
        }
        ArrayList<E> newList = new ArrayList<>();
        int otherIndex = 0;
        int thisIndex = 0;
        // while both sets have elements remaining
        while (otherIndex < otherList.size() && thisIndex < size()) {
            E thisItem = myCon.get(thisIndex);
            E otherItem = otherList.get(otherIndex);
            int comparison = thisItem.compareTo(otherItem);

            // add thisItem to newList
            if (comparison < 0) {
                newList.add(thisItem);
                thisIndex++;
            }
            // add otherItem to newList
            else if (comparison > 0) {
                newList.add(otherItem);
                otherIndex++;
            }
            // both are equal, so add one and increment both to take c
            else {
                newList.add(thisItem);
                thisIndex++;
                otherIndex++;
            }
        }

        int remainderIndex = thisIndex == myCon.size() ? otherIndex : thisIndex;
        ArrayList<E> remainderList = thisIndex == myCon.size() ? otherList : myCon;
        // add the remaining elements of other to newList
        for (int i = remainderIndex; i < remainderList.size(); i++) {
            newList.add(remainderList.get(i));
        }

        return newList;
    }

    /**
     * Determine if item is in this set. <br>
     * pre: item != null
     * 
     * Big O: O(logN)
     * 
     * @param item element whose presence is being tested. Item may not equal null.
     * @return true if this set contains the specified item, false otherwise.
     */
    @Override
    public boolean contains(E item) {
        // check preconditions
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        return locationToAdd(item) >= 0;
    }

    /**
     * Determine if this set contains all items of otherSet.
     * 
     * Big O: O(N) if otherSet is a SortedSet, O(N^2) if otherSet is a Set
     * 
     * @param otherSet != null
     * @return true if this set contains all items of otherSet, false otherwise.
     */
    @Override
    public boolean containsAll(ISet<E> otherSet) {
        // check preconditions
        if (otherSet == null) {
            throw new IllegalArgumentException("otherSet cannot be null");
        }

        if (this.size() < otherSet.size()) {
            return false;
        }

        // if otherSet is a SortedSet use the optimized containsAll method, two pointers
        if (otherSet instanceof SortedSet<?>) {
            int thisIndex = 0;
            int otherIndex = 0;
            SortedSet<E> other = (SortedSet<E>) otherSet;
            while (thisIndex < size() && otherIndex < other.size()) {
                E thisItem = myCon.get(thisIndex);
                E otherItem = other.myCon.get(otherIndex);
                int comparison = thisItem.compareTo(otherItem);

                // if thisItem is less than otherItem, increment thisIndex
                if (comparison < 0) {
                    thisIndex++;
                }
                // if thisItem is greater than otherItem, otherItem cannot exist in the set
                else if (comparison > 0) {
                    return false;
                }
                // if items are equal --> found the item, increment both
                else {
                    thisIndex++;
                    otherIndex++;
                }
            }

            return otherIndex == other.size();
        }

        return super.containsAll(otherSet);
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
     * Find the index of where the element is or where it should be added
     * 
     * Big O: O(logN)
     * 
     * @param item the item to find a location for
     * @return if item exists --> it's index, else --> -1 * (where it should be plus 1)
     */
    private int locationToAdd(E item) {
        int left = 0;
        int right = size();
        int mid = right / 2;

        // loop until left and right pass each other
        while (left < right) {
            int comparison = myCon.get(mid).compareTo(item);
            if (comparison < 0) {
                left = mid + 1;
            } else if (comparison > 0) {
                right = mid;
            } else {
                // element found
                return mid;
            }
            mid = left + ((right - left) / 2);
        }

        // negative of index of where elem should be added, but one lower to account for mid = 0
        return -1 * (mid + 1);
    }

    /**
     * Implement mergesort to sort myCon
     * 
     * Big O: O(NlogN)
     */
    private void sort() {
        sort(0, size(), new Object[size()]);
    }

    /**
     * Implement mergesort to sort a subarry of myCon from start to end
     *
     * Big O: O(1) if at base case, O(NlogN) from top
     * 
     * @param start beginning of subarray to sort, inclusive
     * @param end end of subarry to sort, exclusive
     */
    private void sort(int start, int end, Object[] sorted) {
        // base case is less than 2 elements --> do nothing
        if (end - start > 1) {
            // sort left and right, then merge
            int mid = start + ((end - start) / 2);
            sort(start, mid, sorted);
            sort(mid, end, sorted);

            // pointers for left and right subarrays
            int left = start;
            int right = mid;
            int sortedIndex = start;

            // loop while both pointers are in range of their subarrays
            while (left < mid && right < end) {
                // left subarray contains smallest remaining
                if (myCon.get(left).compareTo(myCon.get(right)) < 0) {
                    sorted[sortedIndex] = myCon.get(left);
                    left++;
                } else {
                    sorted[sortedIndex] = myCon.get(right);
                    right++;
                }

                sortedIndex++;
            }

            // take care of leftover
            int pointer = left == mid ? right : left;
            int limit = pointer == left ? mid : end;
            while (pointer < limit) {
                sorted[sortedIndex] = myCon.get(pointer);
                pointer++;
                sortedIndex++;
            }
        }
    }

    /**
     * Remove an item from this set. <br>
     * pre: item != null <br>
     * post: size() = old.size() - 1
     * 
     * Big O: O(N)
     * 
     * @param item the item to be removed from this set. item may not equal null.
     * @return true if this set changed as a result of this operation, false otherwise.
     */
    @Override
    public boolean remove(E item) {
        // check preconditions
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        int index = locationToAdd(item);
        // positive index --> element is in the set
        if (index >= 0) {
            myCon.remove(index);
            return true;
        }

        return false;
    }

    /**
     * Return the smallest element in this SortedSet. <br>
     * pre: size() != 0
     * 
     * Big O: O(1)
     * 
     * @return the smallest element in this SortedSet.
     */
    public E min() {
        if (size() <= 0) {
            throw new IllegalStateException("Cannot call min() on an empty set");
        }
        return myCon.get(0);
    }

    /**
     * Return the largest element in this SortedSet. <br>
     * pre: size() != 0
     * 
     * Big O: O(1)
     * 
     * @return the largest element in this SortedSet.
     */
    public E max() {
        if (size() <= 0) {
            throw new IllegalStateException("Cannot call max() on an empty set");
        }
        return myCon.get(myCon.size() - 1);
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
     * Big O: O(N)
     * 
     * @param otherSet != null
     * @return a set that is the difference of this set and otherSet
     */
    @Override
    public ISet<E> difference(ISet<E> otherSet) {
        if (otherSet == null) {
            throw new IllegalArgumentException("otherSet cannot be null");
        }
        SortedSet<E> result = new SortedSet<>();
        // if otherSet is an instance of SortedSet, use the optimized version
        if (otherSet instanceof SortedSet<?>) {
            SortedSet<E> other = (SortedSet<E>) otherSet;
            int indexA = 0;
            int indexB = 0;

            while (indexA < size() && indexB < other.size()) {
                int comparison = myCon.get(indexA).compareTo(other.myCon.get(indexB));
                // if item at indexA is smaller --> add to result and increment
                if (comparison < 0) {
                    result.myCon.add(myCon.get(indexA)); // adding to list since we checked already
                    indexA++;
                }
                // if item at indexB is smaller --> increment until it isn't
                else if (comparison > 0) {
                    indexB++;
                }
                // if items at both are the same --> increment both
                else {
                    indexA++;
                    indexB++;
                }
            }
            // add the rest of the items in this set
            for (int i = indexA; i < size(); i++) {
                result.myCon.add(myCon.get(i));
            }
        } else { // if otherSet is not an instance of SortedSet, use the generic version
            for (E item : this) {
                if (!otherSet.contains(item)) {
                    // adding to list since we are going through this set in order
                    result.myCon.add(item);
                }
            }
        }

        return result;
    }

    /**
     * Determine if this set is equal to other. Two sets are equal if they have exactly the same
     * elements. The order of the elements does not matter. <br>
     * pre: none
     * 
     * Big O: O(N) best case, O(N^2) worst cases (not a sorted set)
     * 
     * @param other the object to compare to this set
     * @return true if other is a Set and has the same elements as this set
     */
    @Override
    public boolean equals(Object otherSet) {
        // if other is SortedSet, use optimized version
        if (otherSet instanceof SortedSet<?>) {
            SortedSet<?> other = (SortedSet<?>) otherSet;

            // check sizes
            if (other.size() != size()) {
                return false;
            }

            // if this and other are equal SortedSets, their internal storage containers are equal
            return myCon.equals(other.myCon);
        }

        // use AbstractSet equals if not the special case of SortedSet
        return super.equals(otherSet);
    }

    /**
     * create a new set that is the intersection of this set and otherSet. <br>
     * pre: otherSet != null<br>
     * <br>
     * post: returns a set that is the intersection of this set and otherSet. Neither this set or
     * otherSet are altered as a result of this operation. <br>
     * pre: otherSet != null
     * 
     * Big O: O(N) best case, O(N^2) worst case (not a sorted set)
     * 
     * @param otherSet != null
     * @return a set that is the intersection of this set and otherSet
     */
    @Override
    public ISet<E> intersection(ISet<E> otherSet) {
        if (otherSet == null) {
            throw new IllegalArgumentException("otherSet cannot be null");
        }
        SortedSet<E> result = new SortedSet<>();
        // if otherSet is a SortedSet, use the optimized version
        if (otherSet instanceof SortedSet<?>) {
            SortedSet<E> other = (SortedSet<E>) otherSet;
            int indexA = 0;
            int indexB = 0;

            while (indexA < size() && indexB < other.size()) {
                int comparison = myCon.get(indexA).compareTo(other.myCon.get(indexB));
                // if item at indexA is smaller --> increment until it isn't
                if (comparison < 0) {
                    indexA++;
                }
                // if item at indexB is smaller --> increment until it isn't
                else if (comparison > 0) {
                    indexB++;
                }
                // if items at both index are equivalent --> add once and increment both
                else {
                    result.myCon.add(myCon.get(indexA));
                    indexA++;
                    indexB++;
                }
            }
        } else { // if otherSet is not an instance of SortedSet, use the generic version
            for (E item : this) {
                if (otherSet.contains(item)) {
                    // adding to list since we are going through this set in order
                    result.myCon.add(item);
                }
            }
        }

        return result;
    }

    /**
     * Create a new set that is the union of this set and otherSet. <br>
     * pre: otherSet != null <br>
     * post: returns a set that is the union of this set and otherSet. Neither this set or otherSet
     * are altered as a result of this operation. <br>
     * pre: otherSet != null
     * 
     * Big O: O(N) in the optimal case, O(N^2) in the worst case
     * 
     * @param otherSet != null
     * @return a set that is the union of this set and otherSet
     * 
     * 
     * @param otherSet the other set to union this to
     */
    @Override
    public ISet<E> union(ISet<E> otherSet) {
        if (otherSet == null) {
            throw new IllegalArgumentException("otherSet cannot be null");
        }
        // if otherSet is a SortedSet, use the optimized version
        if (otherSet instanceof SortedSet<?>) {
            SortedSet<E> result = new SortedSet<>();
            SortedSet<E> other = (SortedSet<E>) otherSet;
            int indexA = 0;
            int indexB = 0;

            while (indexA < size() && indexB < other.size()) {
                int comparison = myCon.get(indexA).compareTo(other.myCon.get(indexB));
                // if item at indexA is smaller --> add it to result
                if (comparison < 0) {
                    result.myCon.add(myCon.get(indexA)); // adding to list since we checked already
                    indexA++;
                }
                // if item at indexB is smaller --> add it to result
                else if (comparison > 0) {
                    result.myCon.add(other.myCon.get(indexB));
                    indexB++;
                }
                // if items at both index are equivalent --> add once and increment both
                else {
                    result.myCon.add(myCon.get(indexA));
                    indexA++;
                    indexB++;
                }
            }

            // add the rest of the items in this set or otherSet whichever is not empty
            SortedSet<E> remainderSet = indexA < size() ? this : other;
            for (int i = indexA < size() ? indexA : indexB; i < remainderSet.size(); i++) {
                result.myCon.add(remainderSet.myCon.get(i));
            }

            return result;
        }

        return super.union(otherSet);
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
