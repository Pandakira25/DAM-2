import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Actividad_08_10_26_FicherosBinarios_Ej1_Gestion {

    static final int LONG_DIRECCION = 150;
    static final int TAM_REGISTRO = 4 + 4 + 1 + 4 + (LONG_DIRECCION * 2) + 8; // 321 bytes

    // Offsets de cada campo dentro del registro
    static final int OFF_ID = 0;
    static final int OFF_CADENA = 4;
    static final int OFF_ACTIVO = 8;
    static final int OFF_EMPLEADOS = 9;
    static final int OFF_DIRECCION = 13;
    static final int OFF_FACTURACION = 313; // 13 + 300

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        File fichero = new File("restaurantes.dat");

        if (!fichero.exists()) {
            System.out.println("El fichero no existe. Ejecuta primero la clase que lo crea.");
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {

            int totalRegistros = (int) (raf.length() / TAM_REGISTRO);
            int id;

            do {
                id = pedirInt("\nNúmero del restaurante (0 para salir): ");

                if (id == 0) {
                    break;
                }

                if (id < 1 || id > totalRegistros) {
                    System.out.println("No existe el restaurante " + id
                            + " (hay del 1 al " + totalRegistros + ").");
                    continue;
                }

                menuRestaurante(raf, id);

            } while (true);

            System.out.println("Hasta luego.");

        } catch (IOException e) {
            System.out.println("Error con el fichero: " + e.getMessage());
        }
    }

    private static void menuRestaurante(RandomAccessFile raf, int id) throws IOException {
        long base = (long) (id - 1) * TAM_REGISTRO;
        int opcion;

        do {
            System.out.println("\n--- Restaurante " + id + " ---");
            System.out.println("1. Consultar toda la información");
            System.out.println("2. Modificar cadena");
            System.out.println("3. Modificar activo");
            System.out.println("4. Modificar cantidad de empleados");
            System.out.println("5. Modificar dirección");
            System.out.println("6. Modificar facturación");
            System.out.println("0. Volver");
            opcion = pedirInt("Opción: ");

            switch (opcion) {
                case 1:
                    consultar(raf, base);
                    break;
                case 2:
                    raf.seek(base + OFF_CADENA);
                    raf.writeInt(pedirInt("Nuevo ID de cadena: "));
                    System.out.println("Cadena modificada.");
                    break;
                case 3:
                    raf.seek(base + OFF_ACTIVO);
                    raf.writeBoolean(pedirBoolean("¿Está activo? (s/n): "));
                    System.out.println("Estado modificado.");
                    break;
                case 4:
                    raf.seek(base + OFF_EMPLEADOS);
                    raf.writeInt(pedirInt("Nueva cantidad de empleados: "));
                    System.out.println("Empleados modificados.");
                    break;
                case 5:
                    System.out.print("Nueva dirección (máx. " + LONG_DIRECCION + " caracteres): ");
                    String dir = sc.nextLine();
                    raf.seek(base + OFF_DIRECCION);
                    raf.writeChars(ajustarDireccion(dir));
                    System.out.println("Dirección modificada.");
                    break;
                case 6:
                    raf.seek(base + OFF_FACTURACION);
                    raf.writeLong(pedirLong("Nueva facturación: "));
                    System.out.println("Facturación modificada.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void consultar(RandomAccessFile raf, long base) throws IOException {
        raf.seek(base);

        int id = raf.readInt();
        int idCadena = raf.readInt();
        boolean activo = raf.readBoolean();
        int empleados = raf.readInt();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LONG_DIRECCION; i++) {
            sb.append(raf.readChar());
        }
        String direccion = sb.toString().trim();

        long facturacion = raf.readLong();

        System.out.println("\nID: " + id);
        System.out.println("Cadena: " + idCadena);
        System.out.println("Activo: " + activo);
        System.out.println("Empleados: " + empleados);
        System.out.println("Dirección: " + direccion);
        System.out.println("Facturación: " + facturacion);
    }

    // Rellena con espacios o recorta para que tenga exactamente 150 caracteres
    private static String ajustarDireccion(String direccion) {
        StringBuilder sb = new StringBuilder(direccion);
        if (sb.length() > LONG_DIRECCION) {
            sb.setLength(LONG_DIRECCION);
        }
        while (sb.length() < LONG_DIRECCION) {
            sb.append(' ');
        }
        return sb.toString();
    }

    // ---------- Métodos auxiliares para leer del teclado sin que pete ----------

    private static int pedirInt(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número entero válido.");
            }
        }
    }

    private static long pedirLong(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Long.parseLong(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido.");
            }
        }
    }

    private static boolean pedirBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String r = sc.nextLine().trim().toLowerCase();
            if (r.equals("s") || r.equals("si") || r.equals("sí")) return true;
            if (r.equals("n") || r.equals("no")) return false;
            System.out.println("Responde s o n.");
        }
    }
}