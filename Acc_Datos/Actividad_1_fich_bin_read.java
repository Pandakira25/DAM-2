import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

public class Actividad_1_fich_bin_read {

    public static void main(String[] args) {
        String fichero = "restaurantes.dat";
        int contador = 0;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(fichero))) {

            System.out.println("=== RESTAURANTES ===");

            // Se lee en el mismo orden en que se escribio hasta llegar al final del fichero
            while (true) {
                try {
                    String nombre = dis.readUTF();
                    int capacidad = dis.readInt();
                    long ganancias = dis.readLong();
                    float metros = dis.readFloat();
                    char categoria = dis.readChar();

                    contador++;
                    System.out.println("\nRestaurante " + contador);
                    System.out.println("  Nombre: " + nombre);
                    System.out.println("  Capacidad: " + capacidad + " personas");
                    System.out.println("  Ganancias anuales: " + ganancias + " euros");
                    System.out.println("  Metros cuadrados: " + metros + " m2");
                    System.out.println("  Categoria: " + categoria + " (E = economico, M = medio, L = lujo)");

                } catch (EOFException e) {
                    break; // fin del fichero
                }
            }

            System.out.println("\nTotal de restaurantes leidos: " + contador);

        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }
}
