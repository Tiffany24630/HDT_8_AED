import java.util.PriorityQueue;

public class SistemaEmergenciasJCF {
    public static void main(String[] args) {
        PriorityQueue<Paciente> cola = new PriorityQueue<>();
        

        while (!cola.isEmpty()) {
            System.out.println(cola.poll());
        }
    }
}