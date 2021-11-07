import java.util.Iterator;

public interface ISet<T> {
    public void add(T item);
    public void remove(T item);
    public boolean contains(T item);
    public int size();
    public boolean isEmpty();
    public void clear();
    public Iterator<T> iterator();
}