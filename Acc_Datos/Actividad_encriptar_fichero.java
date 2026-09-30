import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class Actividad_encriptar_fichero {
    public static void main(String[] args) {
        int op;
        String orgl = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String cfr = "jklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi5678901234";

        HashMap<Character, Character> clave = new HashMap<>();

        for (int i = 0; i < orgl.length(); i++) {
            clave.put(orgl.charAt(i), cfr.charAt(i));
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
                        //Pedir una contraseña y el archivo para encriptarlo
                        System.out.println("Ingrese la ruta de su fichero:");
                        path = sc.nextLine();

                        File f = new File(path);

                        if (f.exists()) {
                            if (f.isFile()) {
                                System.out.println("Ingrese la contraseña para su fichero:");
                                contrasenia = sc.nextLine();

                                //TODO:Encriptar y formatear el fichero y crear su copia?

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

                                //TODO: Desencriptar el archivo

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
}
