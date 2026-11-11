package modelo;

import java.io.Serializable;
import java.util.List;

public class Etapa implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String nombre;
    private final List<Partido> partidos;

    public Etapa(String nombre, List<Partido> partidos) {
        this.nombre = nombre;
        this.partidos = partidos;
    }

    public String getNombre() { return nombre; }
    public List<Partido> getPartidos() { return partidos; }
}
