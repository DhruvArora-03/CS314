import java.util.NoSuchElementException;
import javax.sound.sampled.SourceDataLine;

public class PQ<E extends Comparable<E>> {
    private final Node HEADER; // data is NOT sorted

    public PQ() {
        HEADER = new Node(null, null);
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

        // search for the target solution, not updating if tied
        Node curr = HEADER;
        boolean inserted = false;

        while (!inserted && curr.next != null) {
            // look for the closest element greater than the given
            if (curr.next.value.compareTo(element) > 0) {
                curr.next = new Node(element, curr.next);
                inserted = true;
            }
            
            curr = curr.next;
        }
        // insert at end
        if (!inserted) {
            curr.next = new Node(element);
        }
    }

    /**
     * Removes and returns the element with the lowest priority. Cannot be used on empty queue.
     * 
     * @return The element with the lowest priority.
     */
    public E dequeue() {
        if (HEADER.next.equals(null)) {
            throw new NoSuchElementException("Cannot call dequeue on an empty PQ");
        }

        E result = HEADER.next.value;
        HEADER.next = HEADER.next.next;
        return result;
    }

    /**
     * Tells us if the queue has a size of one.
     */
    public boolean isSizeOne() {
        // size is one iff HEADER.next.next is null, check if HEADER.next is !null to avoid errors
        return HEADER.next == null ? false : HEADER.next.next == null;
    }

    @Override
    public String toString() {
        if (HEADER.next == null) {
            return "[]";
        }
        
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        builder.append(HEADER.next.value);

        Node curr = HEADER.next.next;

        while (curr != null) {
            builder.append(", ");
            builder.append(curr.value);
            curr = curr.next;
        }

        builder.append("]");
        
        return builder.toString();
    }

    private class Node {
        E value;
        Node next;

        Node(E value) {
            this(value, null);
        }

        Node(E value, Node next) {
            this.value = value;
            this.next = next;
        }
    }
}
