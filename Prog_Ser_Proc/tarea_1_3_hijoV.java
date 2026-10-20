import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class tarea_1_3_hijoV {
    static final String vocales = "aiueoAIUEO";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String fich = sc.nextLine();
        int voc = 0;
        System.err.println("hola");
        System.err.flush();
        try {
            FileReader fr = new FileReader(new File(fich));

            int c;
            while ((c = fr.read()) != -1) { 
                if (Character.isLetter(c)) {
                    if(vocales.indexOf((char)c) >= 0){
                        voc++;
                    }
                }
            }

            File f = new File("resultado_vocales.txt");
            f.createNewFile();
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Nº de Consonantes: " + voc);
            bw.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        sc.close();
    }
}
