import org.junit.Test;
import static org.junit.Assert.*;
import java.util.NoSuchElementException;

public class VectorHeapTest {
    @Test
    public void testAddAndRemove() {
        VectorHeap<Paciente> heap = new VectorHeap<>();
        heap.add(new Paciente("A", "Síntoma", 'C'));
        heap.add(new Paciente("B", "Síntoma", 'A'));
        assertEquals('A', heap.remove().getCodigo());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveEmpty() {
        VectorHeap<Paciente> heap = new VectorHeap<>();
        heap.remove();
    }
}