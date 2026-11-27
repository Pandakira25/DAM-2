
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class App {
    private static final String RUTA_FICH_SUM = "FicherosSalida/resultadoSumaTotal.txt";
    private static final String RUTA_FICH_MAY_NUM = "FicherosSalida/resultadoNumeroMayor.txt";

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("No ingresó ninguna ruta");
        } else {
            String rutaFich = args[0];

            if (!new File(rutaFich).isFile()) {
                System.out.println("El fichero no existe");
            } else {
                try {
                    ProcessBuilder pbHs = new ProcessBuilder("java", "-jar", "HijoSum.jar");
                    pbHs.redirectError(ProcessBuilder.Redirect.INHERIT);
                    pbHs.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    Process hs = pbHs.start();

                    ProcessBuilder pbHNM = new ProcessBuilder("java", "-jar", "HijoNumMay.jar");
                    pbHNM.redirectError(ProcessBuilder.Redirect.INHERIT);
                    pbHNM.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    Process hnm = pbHNM.start();

                    BufferedWriter bwHS = new BufferedWriter(new OutputStreamWriter(hs.getOutputStream()));
                    bwHS.write(rutaFich);
                    bwHS.newLine();
                    bwHS.flush();
                    bwHS.close();

                    BufferedWriter bwHNM = new BufferedWriter(new OutputStreamWriter(hnm.getOutputStream()));
                    bwHNM.write(rutaFich);
                    bwHNM.newLine();
                    bwHNM.flush();
                    bwHNM.close();

                    hnm.waitFor();
                    hs.waitFor();

                    System.out.println();

                    File fSum = new File(RUTA_FICH_SUM);
                    FileReader frSum = new FileReader(fSum);
                    int c;
                    while ((c = frSum.read()) != -1) { 
                        System.out.print((char) c);
                    }
                    frSum.close();

                    System.out.println();
                    System.out.println("Segundo fich");

                    File fNumMay = new File(RUTA_FICH_MAY_NUM);
                    FileReader frNM = new FileReader(fNumMay);
                    while ((c = frNM.read()) != -1) { 
                        System.out.print((char) c);
                    }
                    frNM.close();

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }

    }
}
