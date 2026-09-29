import java.util.Map;

public class Ej_5_ProcessBuilder {
    public static void main(String[] args) {
        ProcessBuilder test = new ProcessBuilder();

        //Capturar las variables de entorno de windows y escribirlas por consola
        Map entorno = test.environment();

        System.err.println("Variables de entorno: ");
        System.err.println(entorno);

        
    }
}
