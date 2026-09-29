import java.io.IOException;
import java.io.InputStream;

public class Ej_3_ProcesBuilder {
    public static void main(String[] args) {
        //Vamos a capturar la salida del proceso (InputStream)
        //para poder visualizarlo por consola
        //getImputStram(), getOutputStream()

        try {
            Process p = new ProcessBuilder("cmd", "/c", "dir").start();

            //La clase process proporciona el método getImputStream, para leer
            //el stream de salida del proceso y poder escribirlo por consola
            //Capturar la salida del proceso hijo, para visualizarla posteriormente
            InputStream is = p.getInputStream();
            //Capturar la salida de errores del proceso hijo, para visualizarlo después
            InputStream es = p.getErrorStream();

            //Mostrar por pantalla, caracter a caracter, la salida del proceso
            int c;
            while ((c = is.read()) != 1) {
                System.out.println((char) c);
            }

            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
