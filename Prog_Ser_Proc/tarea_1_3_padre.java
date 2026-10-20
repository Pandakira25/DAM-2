import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;


/**
 * Proceso padre: lanza un hijo para contar vocales y otro para contar consonantes
 * de un fichero cuya ruta recibe como argumento del main.
 */
public class tarea_1_3_padre {

    private static final String FICH_VOCALES = "resultado_vocales.txt";
    private static final String FICH_CONSONANTES = "resultado_consonantes.txt";

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("No se ingresó la ruta de ningún fichero");
        }else{
            String rutaFichero = args[0];
            if (!new File(rutaFichero).isFile()) {
                System.err.println("El fichero no existe: " + rutaFichero);
            }else{
                try {
                    //Redirigimos la std.out y std.err para poder ver los errores de los hijitos
                    ProcessBuilder pbV = new ProcessBuilder("java", "-jar", "tarea_1_3_hijoV.jar");
                    pbV.redirectError(ProcessBuilder.Redirect.INHERIT);
                    pbV.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    Process hijoV = pbV.start();
                    ProcessBuilder pbC = new ProcessBuilder("java", "-jar", "tarea_1_3_hijoC.jar");
                    pbV.redirectError(ProcessBuilder.Redirect.INHERIT);
                    pbV.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    Process hijoC = pbC.start();
                    
                    BufferedWriter bwV = new BufferedWriter(new OutputStreamWriter(hijoV.getOutputStream()));
                    BufferedWriter bwC = new BufferedWriter(new OutputStreamWriter(hijoC.getOutputStream()));

                    bwV.write(rutaFichero);
                    bwV.newLine();
                    bwV.flush();

                    bwC.write(rutaFichero);
                    bwC.newLine();
                    bwC.flush();
        
                    hijoV.waitFor();
                    hijoC.waitFor();
        
                    //LEER el resultado y mostrarlo

                    File fV = new File(FICH_VOCALES);
                    FileReader frV = new FileReader(fV);
                    int c;
                    while ((c = frV.read()) != -1) { 
                        System.out.print((char) c);
                    }
                    
                    frV.close();

                    System.out.println();

                    FileReader frC = new FileReader(new File(FICH_CONSONANTES));
                    while ((c = frC.read()) != -1) { 
                        System.out.print((char) c);
                    }
                    frC.close();
                    bwV.close();
                    bwC.close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                } 
            }
    
        }
    }
}