package Hilos;
public class HilosEjemplo1_V2 extends Thread{
    private int hilo;

    public HilosEjemplo1_V2(int hilo) {
        this.hilo = hilo;
        System.out.println("Creando hilo: " + hilo);
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Hilo --> " + getName() + " c= " + i);
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                System.getLogger(HilosEjemplo1_V2.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }
}
