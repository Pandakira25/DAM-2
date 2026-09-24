import  java.io.File;
import java.io.FileNotFoundException;
import  java.io.FileReader;
import java.io.FileWriter;

public class Ejercicio2_FileReader {
    public static void main(String[] args) {
        File f = new File("D:\\DAM-2\\Acc_Datos\\Fichero para pruebas.txt");
        
        FileReader fr;
        FileWriter fw;

        File nf = new File("D:\\DAM-2\\Acc_Datos\\Prueba crear Fichero");

        try {
            fr = new FileReader(f);
            fw = new FileWriter(f,true);

            int v;

            fw.write("\nPrueba para escribir una cadena de caracteres en un fichero");

            fw.close();

            while((v = fr.read()) != -1){
                System.out.print((char)v);
            }

            fr.close();

            nf.createNewFile();

            fw = new FileWriter(nf, true);
            fr = new FileReader(nf);

            fw.write("Prueba para escribir una cadena de caracteres en un fichero");

            fw.close();
            

            while((v = fr.read()) != -1){
                System.out.print((char)v);
            }

            fr.close();
           

            //Tarea que borre una palabra o que borre todos los que sean un mismo caracter ej la "a"

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
