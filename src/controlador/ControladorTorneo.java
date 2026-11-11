package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Equipo;
import modelo.NumeroEquiposInvalidoException;
import modelo.PersistenciaTorneo;
import modelo.SorteoInvalidoException;
import modelo.Torneo;
import vista.VistaConsola;

public class ControladorTorneo {
    private static final String ARCHIVO = "torneo.ser";
    private final VistaConsola vista;
    private Torneo torneo;

    public ControladorTorneo(VistaConsola vista) {
        this.vista = vista;
    }

    public void iniciar() {
        int opcion;
        do {
            opcion = vista.mostrarMenu();
            switch (opcion) {
                case 1 -> nuevoSorteo();
                case 2 -> guardar();
                case 3 -> cargar();
                case 4 -> mostrar();
                case 0 -> vista.mostrarMensaje("Hasta luego.");
                default -> vista.mostrarError("Opcion no valida.");
            }
        } while (opcion != 0);
    }

    private void nuevoSorteo() {
        try {
            torneo = new Torneo(equiposIniciales());
            torneo.ejecutar();
            vista.mostrarTorneo(torneo);
        } catch (NumeroEquiposInvalidoException | SorteoInvalidoException e) {
            vista.mostrarError(e.getMessage());
        }
    }

    private void guardar() {
        if (torneo == null) {
            vista.mostrarError("No hay torneo para guardar.");
            return;
        }
        try {
            PersistenciaTorneo.guardar(torneo, ARCHIVO);
            vista.mostrarMensaje("Torneo guardado en " + ARCHIVO);
        } catch (IOException e) {
            vista.mostrarError("No se pudo guardar: " + e.getMessage());
        }
    }

    private void cargar() {
        try {
            torneo = PersistenciaTorneo.cargar(ARCHIVO);
            vista.mostrarMensaje("Torneo cargado.");
            vista.mostrarTorneo(torneo);
        } catch (IOException | ClassNotFoundException e) {
            vista.mostrarError("No se pudo cargar: " + e.getMessage());
        }
    }

    private void mostrar() {
        if (torneo == null) {
            vista.mostrarError("Aun no hay torneo. Realiza un sorteo o carga uno.");
        } else {
            vista.mostrarTorneo(torneo);
        }
    }

    private List<Equipo> equiposIniciales() {
        String[] nombres = {"Liga de Quito", "Barcelona SC", "Emelec", "Independiente del Valle",
            "Aucas", "El Nacional", "Delfin", "U. Catolica", "Deportivo Cuenca", "Orense",
            "Tecnico Universitario", "Mushuc Runa", "Macara", "Libertad", "Imbabura", "Guayaquil City"};
        List<Equipo> lista = new ArrayList<>();
        for (String n : nombres) lista.add(new Equipo(n));
        return lista;
    }
}
