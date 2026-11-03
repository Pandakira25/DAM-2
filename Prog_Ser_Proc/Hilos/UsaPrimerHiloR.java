package Hilos;

public class UsaPrimerHiloR {
    public static void main(String[] args) {
        //Primer hilo
        PrimerHiloR hilo1 = new PrimerHiloR();
        new Thread(hilo1).start();

        //Segundo hilo
        PrimerHiloR hilo2 = new PrimerHiloR();
        Thread hilo = new Thread(hilo2);
        hilo.start();

        //Tercer hilo
        new Thread(new PrimerHiloR()).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.getLogger(UsaPrimerHiloR.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        System.out.println("Termina la ejecución de main()");
    }
}
