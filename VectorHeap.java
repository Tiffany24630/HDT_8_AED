import java.util.ArrayList;
import java.util.NoSuchElementException;

public class VectorHeap<E extends Comparable<E>> implements PriorityQueue<E> {
    private final ArrayList<E> data;

    public VectorHeap() {
        data = new ArrayList<>();
    }

    @Override
    public void add(E element) {
        data.add(element);
        int index = data.size() - 1;
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (data.get(index).compareTo(data.get(parent)) >= 0) break;
            swap(index, parent);
            index = parent;
        }
    }

    @Override
    public E remove() {
        if (isEmpty()) throw new NoSuchElementException("Cola vacía");
        E min = data.get(0);
        E last = data.remove(data.size() - 1);
        if (!data.isEmpty()) {
            data.set(0, last);
            heapifyDown(0);
        }
        return min;
    }

    private void heapifyDown(int index) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int smallest = index;

        if (left < data.size() && data.get(left).compareTo(data.get(smallest)) < 0) {
            smallest = left;
        }
        if (right < data.size() && data.get(right).compareTo(data.get(smallest)) < 0) {
            smallest = right;
        }
        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    private void swap(int i, int j) {
        E temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
}