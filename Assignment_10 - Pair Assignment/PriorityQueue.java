import java.util.NoSuchElementException;

public class PriorityQueue<E extends Comparable<E>> {
    private final Node<E> HEADER; // data is NOT sorted
    private Node<E> last;

    public PriorityQueue () {
        HEADER = new Node<E>(null, null);
        last = HEADER;
    }
    
    /**
     * Adds a given element to the priority queue.
     * 
     * @param element E to be added, element != null
     */
    public void enqueue(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element cannot be null");
        }
        
        last.next = new Node<E>(element, null);
        last = last.next;
    }
    
    /**
     * Removes and returns the element with the lowest priority. Cannot be used on empty queue.
     * 
     * @return The element with the lowest priority.
     */
    public E dequeue() {
        if (last == HEADER) {
            throw new NoSuchElementException("Priority queue is empty");
        }

        // search for the min element, not updating if tied
        Node<E> min = HEADER.next;
        Node<E> prevMin = HEADER;
        Node<E> curr = HEADER.next;
        Node<E> prevCurr = HEADER;

        while(curr != null){
            E currVal = curr.value;
            
            if(min.value.compareTo(currVal) > 0) { // min > curr --> min = curr
                min = curr;
                prevMin = prevCurr;
            }

            prevCurr = curr;
            curr = curr.next;
        }
        prevMin.next = min.next; // remove
        
        if (min == last) {
            last = prevMin;
        }

        return min.value;
    }

    /**
     * Tells us if the queue has a size of one.
     */
    public boolean isSizeOne() {
        return last == HEADER.next;
    }

    private class Node<E extends Comparable<E>> {
        E value;
        Node next;

        Node(E value, Node next) {
            this.value = value;
            this.next = next;
        }
    }
}
