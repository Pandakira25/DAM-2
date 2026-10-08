import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Actividad_08_10_26_FicherosBinarios_Ej1_Lectura {

    static final int LONG_DIRECCION = 150;                 // 150 chars * 2 bytes = 300 bytes
    static final int TAM_REGISTRO = 4 + 4 + 1 + 4 + (LONG_DIRECCION * 2) + 8; // 321 bytes

    public static void main(String[] args) {
        File fichero = new File("restaurantes.dat");

        if (!fichero.exists()) {
            System.out.println("El fichero no existe. Ejecuta primero la clase que lo crea.");
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {

            long totalRegistros = raf.length() / TAM_REGISTRO;
            System.out.println("Registros en el fichero: " + totalRegistros);
            System.out.println("-----------------------------------");

            // Lectura secuencial de todos los registros
            while (raf.getFilePointer() < raf.length()) {
                leerRegistro(raf);
            }

            // Ejemplo de acceso directo: leer solo el restaurante con ID 5
            System.out.println("\nAcceso directo al restaurante 5:");
            leerRestaurantePorId(raf, 5);

        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    // Lee el registro que empieza en la posición actual del puntero
    private static void leerRegistro(RandomAccessFile raf) throws IOException {
        int id = raf.readInt();
        int idCadena = raf.readInt();
        boolean activo = raf.readBoolean();
        int empleados = raf.readInt();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LONG_DIRECCION; i++) {
            sb.append(raf.readChar());
        }
        String direccion = sb.toString().trim(); // quita los espacios de relleno

        long facturacion = raf.readLong();

        System.out.println("ID: " + id);
        System.out.println("Cadena: " + idCadena);
        System.out.println("Activo: " + activo);
        System.out.println("Empleados: " + empleados);
        System.out.println("Dirección: " + direccion);
        System.out.println("Facturación: " + facturacion);
        System.out.println("-----------------------------------");
    }

    // Salta directamente al registro del ID indicado (los IDs empiezan en 1)
    private static void leerRestaurantePorId(RandomAccessFile raf, int id) throws IOException {
        long posicion = (long) (id - 1) * TAM_REGISTRO;
        if (posicion < 0 || posicion >= raf.length()) {
            System.out.println("No existe el restaurante con ID " + id);
            return;
        }
        raf.seek(posicion);
        leerRegistro(raf);
    }
}