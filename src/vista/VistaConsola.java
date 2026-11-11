package vista;

import java.util.Scanner;
import modelo.Etapa;
import modelo.Partido;
import modelo.Torneo;

/** Solo entrada/salida. Sin logica de negocio. */
public class VistaConsola {
    private final Scanner sc = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n=== SORTEO DE LA LIGA ===");
        System.out.println("1. Realizar nuevo sorteo");
        System.out.println("2. Guardar torneo (torneo.ser)");
        System.out.println("3. Cargar torneo (torneo.ser)");
        System.out.println("4. Mostrar torneo actual");
        System.out.println("0. Salir");
        System.out.print("Opcion: ");
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje) {
        System.out.println("[ERROR] " + mensaje);
    }

    public void mostrarTorneo(Torneo torneo) {
        for (Etapa etapa : torneo.getEtapas()) {
            System.out.println("\n--- " + etapa.getNombre() + " ---");
            int i = 1;
            for (Partido p : etapa.getPartidos()) {
                System.out.println(i++ + ". " + p + "  ->  Avanza: " + p.getGanador());
            }
        }
        System.out.println("\nCAMPEON: " + torneo.getCampeon());
    }
}
