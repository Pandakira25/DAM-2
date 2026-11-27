
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class HijoSuma {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String rfich = sc.nextLine();

        int suma = 0;

        try {
            FileReader fr = new FileReader(new File(rfich));
            
            //Ni idea profe intenté de todo :(
            String s = "";
            int c;
            while ((c = fr.read()) != -1) { 
                s += (char) c;
            }
            
            for(int i = 0; i < (s.length()); i++){
                suma += Integer.parseInt(s);
            }
            
            
            //System.out.println(Integer.parseInt(s) + "  --  " + s.charAt(1));

            File f = new File("FicherosSalida/resultadoSumaTotal.txt");
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("La suma total de los numeros de su fichero es: " + suma);
            bw.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}
