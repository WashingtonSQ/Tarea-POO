package modelo;

/** Se lanza cuando el sorteo recursivo encuentra una condicion logica imposible. */
public class SorteoInvalidoException extends Exception {
    private static final long serialVersionUID = 1L;

    public SorteoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
