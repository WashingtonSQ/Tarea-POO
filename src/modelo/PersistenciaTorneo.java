package modelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/** Serializacion / deserializacion del torneo (parte del Modelo). */
public class PersistenciaTorneo {

    public static void guardar(Torneo torneo, String ruta) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ruta))) {
            out.writeObject(torneo);
        }
    }

    public static Torneo cargar(String ruta) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ruta))) {
            return (Torneo) in.readObject();
        }
    }
}
