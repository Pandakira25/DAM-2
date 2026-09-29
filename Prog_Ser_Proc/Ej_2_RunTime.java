import java.io.IOException;

public class Ej_2_RunTime {
    public static void main(String[] args) {
        //Capturar el entorno de ejecución actual, la ejecución de la aplicación Java
        Runtime r = Runtime.getRuntime();

        String comando = "notepad";
        //String comando = "cmd /c dir";

        try {
            Process p = r.exec(comando);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
