import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Actividad_08_10_26_FicherosBianarios_Ej1 {

    static final int LONG_DIRECCION = 150;                 // 150 chars * 2 bytes = 300 bytes
    static final int TAM_REGISTRO = 4 + 4 + 1 + 4 + (LONG_DIRECCION * 2) + 8; // 321 bytes

    public static void main(String[] args) {
        // Restaurante:
        // ID (int 4 bytes)
        // IdCadenaRestaurante (int 4 bytes)
        // Activo (boolean 1 byte)
        // Cantidad de empleados (int 4 bytes)
        // Dirección (String 150 caracteres = 300 bytes)
        // Facturación (long 8 bytes)

        File fichero = new File("restaurantes.dat");

        int[] ids = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] cadenas = {1, 1, 1, 2, 2, 2, 3, 3, 3};   // 3 restaurantes por cadena
        boolean[] activos = {true, true, false, true, true, true, false, true, true};
        int[] empleados = {12, 8, 15, 20, 9, 11, 7, 14, 18};
        String[] direcciones = {
            "Calle Mayor 1, Madrid",
            "Avenida de la Constitución 22, Sevilla",
            "Calle Larga 5, Valencia",
            "Plaza España 3, Barcelona",
            "Calle del Sol 10, Granada",
            "Paseo de Gracia 45, Barcelona",
            "Calle Alcalá 100, Madrid",
            "Gran Vía 8, Bilbao",
            "Calle Real 17, Zaragoza"
        };
        long[] facturacion = {150000L, 98000L, 120500L, 230000L, 87000L,
                              175000L, 64000L, 142000L, 199999L};

        try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
            raf.setLength(0); // por si el fichero ya existía

            for (int i = 0; i < ids.length; i++) {
                raf.writeInt(ids[i]);
                raf.writeInt(cadenas[i]);
                raf.writeBoolean(activos[i]);
                raf.writeInt(empleados[i]);
                raf.writeChars(ajustarDireccion(direcciones[i])); // siempre 300 bytes
                raf.writeLong(facturacion[i]);
            }

            System.out.println("Restaurantes guardados: " + ids.length);
            System.out.println("Tamaño del fichero: " + raf.length() + " bytes");
            System.out.println("Esperado: " + (ids.length * TAM_REGISTRO) + " bytes");

        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: " + e.getMessage());
        }
    }

    // Rellena con espacios o recorta para que tenga exactamente 150 caracteres
    private static String ajustarDireccion(String direccion) {
        StringBuilder sb = new StringBuilder(direccion);
        sb.setLength(LONG_DIRECCION);
        // setLength rellena con '\u0000'; los cambio por espacios
        for (int i = direccion.length(); i < LONG_DIRECCION; i++) {
            sb.setCharAt(i, ' ');
        }
        return sb.toString();
    }
}