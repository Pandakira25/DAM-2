import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Actividad_1_fich_bin_write {

    public static void main(String[] args) {
        String fichero = "restaurantes.dat";

        // Datos de ejemplo de la cadena de restaurantes
        String[] nombres = {"La Taberna del Puerto", "Casa Pepe", "El Rincon Gourmet", "Pizzeria Napoli", "Sushi Zen"};
        int[] capacidades = {80, 45, 60, 55, 40};
        long[] ganancias = {1250000L, 640000L, 2100000L, 890000L, 1530000L};
        float[] metros = {210.5f, 95.0f, 150.75f, 120.3f, 85.5f};
        // Categoria: E = economico, M = medio, L = lujo
        char[] categorias = {'M', 'E', 'L', 'M', 'L'};

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fichero))) {

            for (int i = 0; i < nombres.length; i++) {
                dos.writeUTF(nombres[i]);
                dos.writeInt(capacidades[i]);
                dos.writeLong(ganancias[i]);
                dos.writeFloat(metros[i]);
                dos.writeChar(categorias[i]);
            }

            System.out.println("Fichero '" + fichero + "' creado con " + nombres.length + " restaurantes.");

        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: " + e.getMessage());
        }
    }
}
