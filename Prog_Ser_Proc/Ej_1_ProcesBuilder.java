//Ejemplo 1 con Proces Builder

import java.io.IOException;

public class Ej_1_ProcesBuilder {

    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder("NOTEPAD");

        //ProcessBuilder pb = new  ProcessBuilder("CMD", "/c", "DIR C:");
        //No se muestra por consola el resultado del comando, porque la salida
        //se dirige a nuestro programa Java, no a la pantalla
        
        try{
            Process p = pb.start();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
