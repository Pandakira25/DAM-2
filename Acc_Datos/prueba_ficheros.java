import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
public class prueba_ficheros {
    public static void main(String[] args) {
        //Fichero existente
        File f = new File("D:\\DAM-2\\Acc_Datos\\Fichero para pruebas.txt");
        try{
            FileReader fileR = new FileReader(f);

            
        }catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        
    }
     
}