package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Estado del torneo + reglas de negocio + sorteo recursivo. */
public class Torneo implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Random RND = new Random();

    private final List<Equipo> equipos;
    private final List<Etapa> etapas = new ArrayList<>();
    private Equipo campeon;

    public Torneo(List<Equipo> equipos) {
        this.equipos = new ArrayList<>(equipos);
    }

    public List<Equipo> getEquipos() { return equipos; }
    public List<Etapa> getEtapas() { return etapas; }
    public Equipo getCampeon() { return campeon; }

    /** Sortea todas las etapas hasta tener campeon. Los ganadores se simulan al azar. */
    public void ejecutar() throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        etapas.clear();
        campeon = null;
        List<Equipo> actuales = new ArrayList<>(equipos);
        while (actuales.size() > 1) {
            Etapa etapa = sortearEtapa(actuales);
            etapas.add(etapa);
            List<Equipo> ganadores = new ArrayList<>();
            for (Partido p : etapa.getPartidos()) {
                Equipo g = RND.nextBoolean() ? p.getLocal() : p.getVisitante();
                p.setGanador(g);
                ganadores.add(g);
            }
            actuales = ganadores;
        }
        campeon = actuales.get(0);
    }

    public Etapa sortearEtapa(List<Equipo> participantes)
            throws NumeroEquiposInvalidoException, SorteoInvalidoException {
        int n = participantes.size();
        if (n < 2 || (n & (n - 1)) != 0) {
            throw new NumeroEquiposInvalidoException(
                "El numero de equipos (" + n + ") no es potencia de 2 (16, 8, 4, 2).");
        }
        List<Partido> partidos = emparejar(new ArrayList<>(participantes), new ArrayList<>());
        return new Etapa(nombreEtapa(n), partidos);
    }

    /**
     * Algoritmo recursivo: caso base = no quedan equipos pendientes.
     * Paso recursivo = saca dos equipos distintos al azar, crea el partido y repite con el resto.
     * Como cada equipo se retira de la lista al emparejarse, ninguno juega dos veces en la etapa.
     */
    private List<Partido> emparejar(List<Equipo> pendientes, List<Partido> acumulado)
            throws SorteoInvalidoException {
        if (pendientes.isEmpty()) {
            return acumulado;
        }
        if (pendientes.size() < 2) {
            throw new SorteoInvalidoException("Equipo sin oponente disponible: " + pendientes.get(0));
        }
        Equipo a = pendientes.remove(RND.nextInt(pendientes.size()));
        Equipo b = pendientes.remove(RND.nextInt(pendientes.size()));
        if (a.equals(b)) {
            throw new SorteoInvalidoException("Un equipo no puede ser competidor y oponente: " + a);
        }
        acumulado.add(new Partido(a, b));
        return emparejar(pendientes, acumulado);
    }

    private String nombreEtapa(int n) {
        return switch (n) {
            case 16 -> "Octavos de final";
            case 8 -> "Cuartos de final";
            case 4 -> "Semifinales";
            case 2 -> "Final";
            default -> "Ronda de " + n + " equipos";
        };
    }
}
