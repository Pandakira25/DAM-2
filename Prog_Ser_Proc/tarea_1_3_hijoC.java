import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class tarea_1_3_hijoC {
    static final String vocales = "aiueoAIUEO";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String fich = sc.nextLine();
        int cons = 0;

        try {
            FileReader fr = new FileReader(new File(fich));

            int c;
            while ((c = fr.read()) != -1) { 
                if (Character.isLetter(c)) {
                    if(vocales.indexOf((char)c) < 0){
                        cons++;
                    }
                }
            }

            File f = new File("resultado_consonantes.txt");
            f.createNewFile();

            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("Nº de Consonantes: " + cons);
            bw.close();
            fr.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        sc.close();
    }
}
