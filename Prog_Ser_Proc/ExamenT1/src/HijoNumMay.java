
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class HijoNumMay {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String rfich = sc.nextLine();

        try {
            FileReader fr = new FileReader(new File(rfich));

            int nMayor = 0;
            int c;
            String s = "";
            while ((c = fr.read()) != -1) { 
                s += (char) c;
            }

            for(int i = 0; i < (s.length()); i++){
                if (nMayor < s.charAt(i)) {
                    nMayor = Integer.parseInt(s);
                }
            }

            File f = new File("FicherosSalida/resultadoNumeroMayor.txt");
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("El mayor número encontrado fué: " + nMayor);
            bw.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}
