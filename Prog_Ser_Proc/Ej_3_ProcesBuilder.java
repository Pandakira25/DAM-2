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
            while ((c = is.read()) != -1) {
                System.out.print((char) c);
            }

            is.close();

            //Comprobación: Consultar si el proceso hijo a terminado correctamente o no
            //Para esto debemos esperar a que termine el proceso padre 'waitFor()'
            //Devuelve 0 si termina correctamente y -1 si no

            int exitVal;
            exitVal = p.waitFor();
            System.out.println("Valor de salida" + exitVal);

            if(exitVal != 0){
                while ((c = es.read()) != -1) {
                    System.out.print((char) c);
                }
            }

            es.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
