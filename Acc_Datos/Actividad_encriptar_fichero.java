import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Actividad_encriptar_fichero {

    public static void main(String[] args) {
        int op;
        String orgl = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String cfr = "jklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi5678901234";

        HashMap<Character, Character> clave = new HashMap<>();
        HashMap<Character, Character> claveInversa = new HashMap<>();

        for (int i = 0; i < orgl.length(); i++) {
            clave.put(orgl.charAt(i), cfr.charAt(i));
            claveInversa.put(cfr.charAt(i), orgl.charAt(i));
        }

        String contrasenia;
        String path;

        Scanner sc = new Scanner(System.in);

        try {
            do {
                System.out.println("1- Encriptar un fichero\n2-Desencriptar un fichero\n3-Salir");
                op = Integer.parseInt(sc.nextLine());

                if (op != 1 && op != 2 && op != 3) {
                    System.out.println("Debe ingresar una de las opciones del menú");
                } else {
                    if (op == 1) {
                        System.out.println("Ingrese la ruta de su fichero:");
                        path = sc.nextLine();

                        File f = new File(path);

                        if (f.exists()) {
                            if (f.isFile()) {
                                System.out.println("Ingrese la contraseña para su fichero:");
                                contrasenia = sc.nextLine();

                                encriptar(f, contrasenia, clave);

                            } else {
                                System.out.println("Es un directorio");
                            }
                        } else {
                            System.out.println("El fichero no existe");
                        }

                    } else if (op == 2) {
                        System.out.println("Ingrese la ruta de su fichero");
                        path = sc.nextLine();

                        File f = new File(path);

                        if (f.exists()) {
                            if (f.isFile()) {
                                System.out.println("Ingrese la contraseña para desencriptar:");
                                contrasenia = sc.nextLine();

                                desencriptar(f, contrasenia, claveInversa);

                            } else {
                                System.out.println("Es un directorio");
                            }
                        } else {
                            System.out.println("El fichero no existe");
                        }
                    }

                }
            } while (op != 3);
        } catch (Exception e) {
            System.out.println("Debe ingresar un número");
        }

        sc.close();
    }

    // Sustituye cada carácter según el mapa; los que no están (espacios, símbolos...) se quedan igual
    private static String transformar(String linea, HashMap<Character, Character> mapa) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < linea.length(); i++) {
            char c = linea.charAt(i);
            sb.append(mapa.getOrDefault(c, c));
        }
        return sb.toString();
    }

    private static void encriptar(File origen, String contrasenia, HashMap<Character, Character> clave) {
        File destino = new File(origen.getAbsoluteFile().getParentFile(), "enc_" + origen.getName());

        try (BufferedReader br = new BufferedReader(new FileReader(origen));
             BufferedWriter bw = new BufferedWriter(new FileWriter(destino))) {

            // Primera línea: la contraseña sin encriptar
            bw.write("Contrasenia: " + contrasenia);
            bw.newLine();

            String linea;
            while ((linea = br.readLine()) != null) {
                bw.write(transformar(linea, clave));
                bw.newLine();
            }

            System.out.println("Fichero encriptado: " + destino.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error al encriptar el fichero: " + e.getMessage());
        }
    }

    private static void desencriptar(File origen, String contrasenia, HashMap<Character, Character> claveInversa) {
        try (BufferedReader br = new BufferedReader(new FileReader(origen))) {
    
            String primera = br.readLine();
            String prefijo = "Contrasenia: ";
    
            if (primera == null || !primera.startsWith(prefijo)) {
                System.out.println("El fichero no tiene el formato esperado");
                return;
            }
    
            String contraseniaFichero = primera.substring(prefijo.length());
    
            if (!contraseniaFichero.equals(contrasenia)) {
                System.out.println("Contraseña incorrecta");
                return;
            }
    
            System.out.println("--- Contenido desencriptado ---");
    
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(transformar(linea, claveInversa));
            }
    
            System.out.println("-------------------------------");
    
        } catch (IOException e) {
            System.out.println("Error al desencriptar el fichero: " + e.getMessage());
        }
    }
}