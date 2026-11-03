import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Practica_2 {

    static final String CARPETA = "ficheros";
    static final String F_BINARIO = CARPETA + File.separator + "restaurantes.dat";
    static final String F_TEXTO = CARPETA + File.separator + "restaurantes.txt";
    static final String F_OBJETOS = CARPETA + File.separator + "restaurantes.obj";

    static final int LONG_DIRECCION = 150; // 150 chars * 2 bytes = 300 bytes

    public static void main(String[] args) {
        new File(CARPETA).mkdirs();

        crearFicheroBinario();      // 1
        crearFicheroTexto();        // 2
        crearFicheroObjetos();      // 3
        consultarPorCadena();       // 4
    }

    // ---------------------------------------------------------------
    // 1.- Fichero binario a partir de arrays
    // ---------------------------------------------------------------
    private static void crearFicheroBinario() {
        int[] ids = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] cadenas = {1, 1, 1, 2, 2, 2, 3, 3, 3};
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

        try (RandomAccessFile raf = new RandomAccessFile(F_BINARIO, "rw")) {
            raf.setLength(0);

            for (int i = 0; i < ids.length; i++) {
                raf.writeInt(ids[i]);
                raf.writeInt(cadenas[i]);
                raf.writeBoolean(activos[i]);
                raf.writeInt(empleados[i]);
                raf.writeChars(ajustarDireccion(direcciones[i]));
                raf.writeLong(facturacion[i]);
            }
            System.out.println("Creando fichero binario con " + ids.length + " restaurantes...");
        } catch (IOException e) {
            System.out.println("Error al crear el fichero binario: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // 2.- Del binario al fichero de texto (campos separados por ";")
    // ---------------------------------------------------------------
    private static void crearFicheroTexto() {
        try (RandomAccessFile raf = new RandomAccessFile(F_BINARIO, "r");
             BufferedWriter bw = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream(F_TEXTO), StandardCharsets.UTF_8))) {

            int contador = 0;
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                int idCadena = raf.readInt();
                boolean activo = raf.readBoolean();
                int empleados = raf.readInt();
                String direccion = leerDireccion(raf).trim();
                long facturacion = raf.readLong();

                bw.write(id + ";" + idCadena + ";" + activo + ";" + empleados
                        + ";" + direccion + ";" + facturacion);
                bw.newLine();
                contador++;
            }
            System.out.println("Creando fichero de texto con " + contador + " líneas...");
        } catch (IOException e) {
            System.out.println("Error al crear el fichero de texto: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // 3.- Del texto al fichero de objetos
    // ---------------------------------------------------------------
    private static void crearFicheroObjetos() {
        try (BufferedReader br = new BufferedReader(
                     new InputStreamReader(new FileInputStream(F_TEXTO), StandardCharsets.UTF_8));
             ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(F_OBJETOS))) {

            String linea;
            int contador = 0;
            while ((linea = br.readLine()) != null) {
                String[] campos = linea.split(";");

                RestauranteInfo r = new RestauranteInfo(
                        Integer.parseInt(campos[0]),
                        Integer.parseInt(campos[1]),
                        Boolean.parseBoolean(campos[2]),
                        Integer.parseInt(campos[3]),
                        campos[4],
                        Long.parseLong(campos[5]));

                oos.writeObject(r);
                contador++;
            }
            System.out.println("Creando fichero de objetos con " + contador + " restaurantes...");
        } catch (IOException e) {
            System.out.println("Error al crear el fichero de objetos: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------
    // 4.- Consulta: pedir el id de una cadena y mostrar sus restaurantes
    // ---------------------------------------------------------------
    private static void consultarPorCadena() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nIntroduce el id de la cadena de restaurantes que deseas consultar: ");
        int idBuscado = sc.nextInt();

        boolean encontrado = false;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(F_OBJETOS))) {
            while (true) {
                RestauranteInfo r = (RestauranteInfo) ois.readObject();
                if (r.getIdCadena() == idBuscado) {
                    System.out.println(r);
                    encontrado = true;
                }
            }
        } catch (EOFException e) {
            // fin del fichero: es la forma normal de terminar de leer objetos
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer el fichero de objetos: " + e.getMessage());
        }

        if (!encontrado) {
            System.out.println("No hay restaurantes para la cadena " + idBuscado);
        }
    }

    // ---------------------------------------------------------------
    // Auxiliares
    // ---------------------------------------------------------------
    private static String ajustarDireccion(String direccion) {
        StringBuilder sb = new StringBuilder(direccion);
        sb.setLength(LONG_DIRECCION);
        for (int i = direccion.length(); i < LONG_DIRECCION; i++) {
            sb.setCharAt(i, ' ');
        }
        return sb.toString();
    }

    private static String leerDireccion(RandomAccessFile raf) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LONG_DIRECCION; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString();
    }
}