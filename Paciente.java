public class Paciente implements Comparable<Paciente> {
    private String nombre;
    private String sintoma;
    private char codigo;

    public Paciente(String nombre, String sintoma, char codigo) {
        this.nombre = nombre;
        this.sintoma = sintoma;
        this.codigo = Character.toUpperCase(codigo);
    }

    @Override
    public int compareTo(Paciente otro) {
        return Integer.compare(this.getPrioridad(), otro.getPrioridad());
    }

    private int getPrioridad() {
        switch (codigo) {
            case 'A':
                return 1;
            case 'B':
                return 2;
            case 'C':
                return 3;
            case 'D':
                return 4;
            case 'E':
                return 5;
            default:
                throw new IllegalArgumentException("Código inválido: " + codigo);
        }
    }

    // Getters y toString
    @Override
    public String toString() {
        return nombre + ", " + sintoma + ", " + codigo;
    }

    public char getCodigo() {
        return codigo;
    }
}