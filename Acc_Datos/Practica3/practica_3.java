package Practica3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class practica_3 {
    //Restaurante:
    //ID(Int 4 bytes)
    //IdCadenaRestaurante(Int 4 bytes) Estaría en otra tabla la información de la cadena de restaurantes
    //Activo(boolean 1 byte)
    //Cantidad de empleados(int 4 bytes)
    //Dirección(String 150 caracteres 300 bytes)
    //Facturación(long 8 bytes)
    //Tamaño de cada Restaurante: 321 bytes

    //Cadena de restaurante:
    //ID(Int 4 bytes)
    //Nombre(String 30 caracteres 60 bytes)
    //Tamaño de cada Cadena de restaurante: 64 bytes

    //Borrado lógico: al eliminar un restaurante se guarda su ID en negativo (5 -> -5)
    //El registro sigue ocupando su sitio y se ignora en todas las opciones
    //Característica por la que se filtra en la opción 5: la cadena

    // ---------- Rutas ----------
    static final String FICHERO_RESTAURANTES = "DATs/practica_3_restaurantes.dat";
    static final String FICHERO_CADENAS = "DATs/practica_3_cadenas.dat";

    // ---------- Restaurantes ----------
    // POSICION_X = byte dentro del registro donde empieza el campo X
    static final int LONG_DIRECCION = 150;
    static final int TAM_REGISTRO = 4 + 4 + 1 + 4 + (LONG_DIRECCION * 2) + 8; // 321 bytes

    static final int POSICION_ID = 0;
    static final int POSICION_ID_CADENA = 4;
    static final int POSICION_ACTIVO = 8;
    static final int POSICION_EMPLEADOS = 9;
    static final int POSICION_DIRECCION = 13;
    static final int POSICION_FACTURACION = 313; // 13 + 300

    // ---------- Cadenas ----------
    static final int LONG_NOMBRE_CADENA = 30;
    static final int TAM_REGISTRO_CADENA = 4 + (LONG_NOMBRE_CADENA * 2); // 64 bytes

    static final int POSICION_NOMBRE_CADENA = 4;

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        File fichero = new File(FICHERO_RESTAURANTES);

        try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw");
            RandomAccessFile rafCadenas = new RandomAccessFile(FICHERO_CADENAS, "rw")) {

            inicializarCadenas(rafCadenas);

            int opcion;
            do {
                System.out.println("\n===== GESTIÓN DE RESTAURANTES =====");
                System.out.println("1.- Añadir 15 restaurantes (desde arrays)");
                System.out.println("2.- Añadir un restaurante al final");
                System.out.println("3.- Visualizar todos los restaurantes");
                System.out.println("4.- Visualizar un restaurante por código");
                System.out.println("5.- Visualizar los restaurantes de una cadena");
                System.out.println("6.- Modificar un dato de un restaurante");
                System.out.println("7.- Eliminar un restaurante");
                System.out.println("8.- Salir");
                opcion = pedirInt("Opción: ");

                switch (opcion) {
                    case 1:
                        anadir15(raf);
                        break;
                    case 2:
                        anadirUno(raf, rafCadenas);
                        break;
                    case 3:
                        visualizarTodos(raf, rafCadenas);
                        break;
                    case 4:
                        visualizarPorCodigo(raf, rafCadenas);
                        break;
                    case 5:
                        visualizarPorCadena(raf, rafCadenas);
                        break;
                    case 6:
                        modificar(raf, rafCadenas);
                        break;
                    case 7:
                        eliminar(raf, rafCadenas);
                        break;
                    case 8:
                        System.out.println("Hasta luego.");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 8);

        } catch (IOException e) {
            System.out.println("Error con los ficheros: " + e.getMessage());
        }
    }

    // =====================================================================
    //  CADENAS
    // =====================================================================

    // Si el fichero de cadenas está vacío, se rellena con 3 cadenas
    private static void inicializarCadenas(RandomAccessFile rafCadenas) throws IOException {
        if (rafCadenas.length() > 0) {
            return;
        }
        int[] ids = {1, 2, 3};
        String[] nombres = {"La Parrilla Dorada", "Pasta Bella", "Sushi Express"};

        rafCadenas.seek(0);
        for (int i = 0; i < ids.length; i++) {
            rafCadenas.writeInt(ids[i]);
            escribirTexto(rafCadenas, nombres[i], LONG_NOMBRE_CADENA);
        }
    }

    // Devuelve la posición del registro de la cadena, o -1 si no existe
    private static long posCadena(RandomAccessFile rafCadenas, int id) throws IOException {
        int totalRegistros = (int) (rafCadenas.length() / TAM_REGISTRO_CADENA);
        if (id < 1 || id > totalRegistros) {
            return -1;
        }
        long base = (long) (id - 1) * TAM_REGISTRO_CADENA;
        rafCadenas.seek(base);
        return rafCadenas.readInt() == id ? base : -1;
    }

    private static String nombreCadena(RandomAccessFile rafCadenas, int idCadena) throws IOException {
        long base = posCadena(rafCadenas, idCadena);
        if (base == -1) {
            return "(cadena desconocida)";
        }
        rafCadenas.seek(base + POSICION_NOMBRE_CADENA);
        return leerTexto(rafCadenas, LONG_NOMBRE_CADENA);
    }

    private static void listarCadenas(RandomAccessFile rafCadenas) throws IOException {
        System.out.println("Cadenas disponibles:");
        int totalRegistros = (int) (rafCadenas.length() / TAM_REGISTRO_CADENA);
        for (int i = 0; i < totalRegistros; i++) {
            rafCadenas.seek((long) i * TAM_REGISTRO_CADENA);
            int id = rafCadenas.readInt();
            String nombre = leerTexto(rafCadenas, LONG_NOMBRE_CADENA);
            if (id > 0) {
                System.out.println("  " + id + " - " + nombre);
            }
        }
    }

    // =====================================================================
    //  RESTAURANTES
    // =====================================================================

    // Devuelve la posición del registro, o -1 si no existe o está borrado
    private static long posRestaurante(RandomAccessFile raf, int id) throws IOException {
        int totalRegistros = (int) (raf.length() / TAM_REGISTRO);
        if (id < 1 || id > totalRegistros) {
            return -1;
        }
        long base = (long) (id - 1) * TAM_REGISTRO;
        raf.seek(base);
        return raf.readInt() == id ? base : -1;
    }

    // Último código del fichero + 1 (cuenta también los borrados, usa el valor absoluto)
    private static int siguienteId(RandomAccessFile raf) throws IOException {
        int totalRegistros = (int) (raf.length() / TAM_REGISTRO);
        if (totalRegistros == 0) {
            return 1;
        }
        raf.seek((long) (totalRegistros - 1) * TAM_REGISTRO);
        return Math.abs(raf.readInt()) + 1;
    }

    private static void escribirRestaurante(RandomAccessFile raf, int id, int idCadena,
                                            boolean activo, int empleados, String direccion, long facturacion) throws IOException {
        raf.seek(raf.length());
        raf.writeInt(id);
        raf.writeInt(idCadena);
        raf.writeBoolean(activo);
        raf.writeInt(empleados);
        escribirTexto(raf, direccion, LONG_DIRECCION);
        raf.writeLong(facturacion);
    }

    private static void mostrarRestaurante(RandomAccessFile raf, RandomAccessFile rafCadenas, long base) throws IOException {
        raf.seek(base);
        int id = raf.readInt();
        int idCadena = raf.readInt();
        boolean activo = raf.readBoolean();
        int empleados = raf.readInt();
        String direccion = leerTexto(raf, LONG_DIRECCION);
        long facturacion = raf.readLong();

        System.out.println("-----------------------------------");
        System.out.println("Código: " + id);
        System.out.println("Cadena: " + idCadena + " - " + nombreCadena(rafCadenas, idCadena));
        System.out.println("Activo: " + (activo ? "Sí" : "No"));
        System.out.println("Empleados: " + empleados);
        System.out.println("Dirección: " + direccion);
        System.out.println("Facturación: " + facturacion);
    }

    // ---------- Opción 1 ----------
    private static void anadir15(RandomAccessFile raf) throws IOException {
        // 15 restaurantes de 3 cadenas (5 por cadena)
        int[] cadenas = {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};
        boolean[] activos = {true, true, false, true, true,
                            true, true, true, false, true,
                            true, false, true, true, true};
        int[] empleados = {12, 8, 15, 20, 9, 11, 7, 14, 18, 10, 16, 6, 13, 19, 22};
        String[] direcciones = {
            "Calle Mayor 1, Madrid",
            "Avenida de la Constitución 22, Sevilla",
            "Calle Larga 5, Valencia",
            "Plaza de España 3, Barcelona",
            "Calle del Sol 10, Granada",
            "Paseo de Gracia 45, Barcelona",
            "Calle Alcalá 100, Madrid",
            "Gran Vía 8, Bilbao",
            "Calle Real 17, Zaragoza",
            "Rambla de Catalunya 60, Barcelona",
            "Calle Serrano 34, Madrid",
            "Avenida del Puerto 12, Valencia",
            "Calle Toledo 76, Madrid",
            "Calle San Fernando 9, Sevilla",
            "Paseo Marítimo 2, Málaga"
        };
        long[] facturacion = {150000L, 98000L, 120500L, 230000L, 87000L,
                            175000L, 64000L, 142000L, 199999L, 110300L,
                            260000L, 54000L, 133700L, 188000L, 305000L};

        for (int i = 0; i < cadenas.length; i++) {
            escribirRestaurante(raf, siguienteId(raf), cadenas[i], activos[i],
                    empleados[i], direcciones[i], facturacion[i]);
        }
        System.out.println("Se han añadido 15 restaurantes.");
    }

    // ---------- Opción 2 ----------
    private static void anadirUno(RandomAccessFile raf, RandomAccessFile rafCadenas) throws IOException {
        listarCadenas(rafCadenas);
        int idCadena = pedirInt("ID de la cadena: ");
        if (posCadena(rafCadenas, idCadena) == -1) {
            System.out.println("Error: no existe la cadena " + idCadena + ".");
            return;
        }
        boolean activo = pedirBoolean("¿Está activo? (s/n): ");
        int empleados = pedirInt("Cantidad de empleados: ");
        String direccion = pedirTexto("Dirección (máx. " + LONG_DIRECCION + " caracteres): ");
        long facturacion = pedirLong("Facturación: ");

        int id = siguienteId(raf);
        escribirRestaurante(raf, id, idCadena, activo, empleados, direccion, facturacion);
        System.out.println("Restaurante añadido con código " + id + ".");
    }

    // ---------- Opción 3 ----------
    private static void visualizarTodos(RandomAccessFile raf, RandomAccessFile rafCadenas) throws IOException {
        int totalRegistros = (int) (raf.length() / TAM_REGISTRO);
        int mostrados = 0;
        for (int i = 0; i < totalRegistros; i++) {
            long base = (long) i * TAM_REGISTRO;
            raf.seek(base);
            if (raf.readInt() > 0) { // los borrados tienen código negativo
                mostrarRestaurante(raf, rafCadenas, base);
                mostrados++;
            }
        }
        if (mostrados == 0) {
            System.out.println("No hay restaurantes en el fichero.");
        } else {
            System.out.println("-----------------------------------");
            System.out.println("Total: " + mostrados + " restaurantes.");
        }
    }

    // ---------- Opción 4 ----------
    private static void visualizarPorCodigo(RandomAccessFile raf, RandomAccessFile rafCadenas) throws IOException {
        int id = pedirInt("Código del restaurante: ");
        long base = posRestaurante(raf, id);
        if (base == -1) {
            System.out.println("Error: no existe el restaurante " + id + ".");
        } else {
            mostrarRestaurante(raf, rafCadenas, base);
        }
    }

    // ---------- Opción 5 ----------
    private static void visualizarPorCadena(RandomAccessFile raf, RandomAccessFile rafCadenas) throws IOException {
        listarCadenas(rafCadenas);
        int idCadena = pedirInt("ID de la cadena: ");
        if (posCadena(rafCadenas, idCadena) == -1) {
            System.out.println("Error: no existe la cadena " + idCadena + ".");
            return;
        }

        int totalRegistros = (int) (raf.length() / TAM_REGISTRO);
        int encontrados = 0;
        for (int i = 0; i < totalRegistros; i++) {
            long base = (long) i * TAM_REGISTRO;
            raf.seek(base);
            int id = raf.readInt();
            int cadena = raf.readInt(); //Foreign key
            if (id > 0 && cadena == idCadena) {
                mostrarRestaurante(raf, rafCadenas, base);
                encontrados++;
            }
        }
        if (encontrados == 0) {
            System.out.println("No hay restaurantes de la cadena " + idCadena + ".");
        } else {
            System.out.println("-----------------------------------");
            System.out.println("Total: " + encontrados + " restaurantes de " + nombreCadena(rafCadenas, idCadena) + ".");
        }
    }

    // ---------- Opción 6 ----------
    private static void modificar(RandomAccessFile raf, RandomAccessFile rafCadenas) throws IOException {
        int id = pedirInt("Código del restaurante a modificar: ");
        long base = posRestaurante(raf, id);
        if (base == -1) {
            System.out.println("Error: no existe el restaurante " + id + ".");
            return;
        }
        mostrarRestaurante(raf, rafCadenas, base);

        System.out.println("\n¿Qué dato quieres modificar?");
        System.out.println("1.- Cadena");
        System.out.println("2.- Activo");
        System.out.println("3.- Cantidad de empleados");
        System.out.println("4.- Dirección");
        System.out.println("5.- Facturación");
        System.out.println("0.- Cancelar");
        int campo = pedirInt("Opción: ");

        switch (campo) {
            case 1:
                listarCadenas(rafCadenas);
                int idCadena = pedirInt("Nuevo ID de cadena: ");
                if (posCadena(rafCadenas, idCadena) == -1) {
                    System.out.println("Error: no existe la cadena " + idCadena + ". No se modifica nada.");
                    return;
                }
                raf.seek(base + POSICION_ID_CADENA);
                raf.writeInt(idCadena);
                break;
            case 2:
                raf.seek(base + POSICION_ACTIVO);
                raf.writeBoolean(pedirBoolean("¿Está activo? (s/n): "));
                break;
            case 3:
                raf.seek(base + POSICION_EMPLEADOS);
                raf.writeInt(pedirInt("Nueva cantidad de empleados: "));
                break;
            case 4:
                String dir = pedirTexto("Nueva dirección (máx. " + LONG_DIRECCION + " caracteres): ");
                raf.seek(base + POSICION_DIRECCION);
                escribirTexto(raf, dir, LONG_DIRECCION);
                break;
            case 5:
                raf.seek(base + POSICION_FACTURACION);
                raf.writeLong(pedirLong("Nueva facturación: "));
                break;
            case 0:
                System.out.println("Modificación cancelada.");
                return;
            default:
                System.out.println("Opción no válida.");
                return;
        }
        System.out.println("Dato modificado correctamente.");
    }

    // ---------- Opción 7 ----------
    private static void eliminar(RandomAccessFile raf, RandomAccessFile rafCadenas) throws IOException {
        int id = pedirInt("Código del restaurante a eliminar: ");
        long base = posRestaurante(raf, id);
        if (base == -1) {
            System.out.println("Error: no existe el restaurante " + id + ".");
            return;
        }
        mostrarRestaurante(raf, rafCadenas, base);

        if (pedirBoolean("¿Seguro que quieres eliminarlo? (s/n): ")) {
            raf.seek(base + POSICION_ID);
            raf.writeInt(-id); // borrado lógico
            System.out.println("Restaurante " + id + " eliminado.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    // =====================================================================
    //  AUXILIARES DE FICHERO
    // =====================================================================

    // Escribe exactamente 'longitud' caracteres (rellena con espacios o recorta)
    private static void escribirTexto(RandomAccessFile raf, String texto, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder(texto);
        if (sb.length() > longitud) {
            sb.setLength(longitud);
        }
        while (sb.length() < longitud) {
            sb.append(' ');
        }
        raf.writeChars(sb.toString());
    }

    // Lee 'longitud' caracteres y quita los espacios de relleno
    private static String leerTexto(RandomAccessFile raf, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }

    // =====================================================================
    //  AUXILIARES DE TECLADO
    // =====================================================================

    private static String pedirTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

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
