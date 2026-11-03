package Hilos;

public class PrimerHiloR implements Runnable{

    @Override
    public void run() {
        System.out.println("Estamos en el hilo: " + Thread.currentThread().getId());
    }

}
