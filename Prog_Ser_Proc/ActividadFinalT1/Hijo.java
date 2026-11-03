package ActividadFinalT1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Hijo {
    public static void main(String[] args) {
        try {
            int lineasError=0;
            //int lineasTotales=0;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String nombreFichero=in.readLine();
            in.close();
            BufferedReader inFichero=new BufferedReader(new java.io.FileReader(nombreFichero));
            String linea=inFichero.readLine();
            while(linea!=null){
                //lineasTotales++;
                if(linea.contains("ERROR")){
                    lineasError++;
                }
                linea=inFichero.readLine();
            }
            inFichero.close();
            System.out.println(lineasError);

        } catch (Exception e) {
            System.err.println(e);
            System.exit(-1);
        }
        System.exit(0);
    }
}