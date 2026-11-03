package Hilos;
public class UsaHilosEjemplo1_V2 {
    public static void main(String[] args) {
        HilosEjemplo1_V2 h = null;

        for (int i = 0; i < 3; i++) {
            h = new HilosEjemplo1_V2(i);
            h.start();
        }

        System.out.println("\n3 hilos iniciados");
        System.out.println("Finaliza la ejecución de main()");
    }
}
