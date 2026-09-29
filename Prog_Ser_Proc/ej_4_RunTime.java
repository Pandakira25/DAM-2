import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ej_4_RunTime {
    public static void main(String[] args) {
        //Lctura por teclado
        BufferedReader bre = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("\nTeclear la información deseada:");

        String cadena;

        try {
            cadena = bre.readLine();
            System.out.println("\nInformación tecleada: \"" + cadena + "\"\n\n\n");
            bre.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Runtime r = Runtime.getRuntime();

        String comando = "cmd /c dira c:";
        Process p = null;

        try {
            p = r.exec(comando);
            //Asociar stream a la salida del proceso, para poder visualizar el ocntenido,
            //con el método getImputStrem() de la clase Process

            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String linea;
            while((linea = br.readLine()) != null){
                System.out.println(linea);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int exitVal;

        InputStream es = p.getErrorStream();
        BufferedReader be = new BufferedReader(new InputStreamReader(es));

        try {
            exitVal = p.waitFor();
            System.out.println("Valor de salida: " + exitVal);

            String lineaE;

            while((lineaE = be.readLine()) != null){
                System.out.println(lineaE);
            }

            be.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
}
