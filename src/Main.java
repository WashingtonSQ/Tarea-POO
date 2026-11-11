import controlador.ControladorTorneo;
import vista.VistaConsola;

public class Main {
    public static void main(String[] args) {
        new ControladorTorneo(new VistaConsola()).iniciar();
    }
}
