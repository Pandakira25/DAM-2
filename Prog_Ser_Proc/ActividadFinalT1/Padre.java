package ActividadFinalT1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Padre {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese las rutas de los ficheros a analizar separadas por un punto y coma(;)");
        String r = sc.nextLine().trim();
        String rutas[] = null;

        if (!r.isBlank()) {
            rutas = r.split(";");

            if (rutas == null) {
                System.out.println("No se ingresaron rutas");
            } else {
                try {
                    Process procesos[] = new Process[rutas.length];
                    ProcessBuilder pbH = new ProcessBuilder("java", "-jar", "ActividadFinalT1/Hijo.jar");
                    pbH.redirectError(ProcessBuilder.Redirect.INHERIT);
                    for (int i = 0; i < rutas.length; i++) {
                        procesos[i] = pbH.start();

                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(procesos[i].getOutputStream()));
                        bw.write(rutas[i]);
                        bw.newLine();
                        bw.flush();
                        bw.close();
                    }

                    int totalErrores = 0;

                    for (int i = rutas.length - 1; i >= 0; i--) {
                        if (procesos[i].waitFor() == 0) {
                            BufferedReader br = new BufferedReader(new InputStreamReader(procesos[i].getInputStream()));
                            String errores = br.readLine();
                            totalErrores += Integer.parseInt(errores);
                            System.out.println("El numero de errores encontrados en el fichero " + rutas[i] + " es: " + errores + ".");
                            br.close();
                        } else {
                            System.out.println("Algo salió mal al analizar el archivo " + rutas[i]);
                        }
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

            sc.close();

        }else{
            System.out.println("No se ingresaron rutas");
        }
    }
}
