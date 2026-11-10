package Hilos;

public class HiloInter extends Thread {
    public void run(){
        try {
            while (!isInterrupted()) { 
                System.out.println("Estamos en el hilo");
                sleep(1000);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Se ha producido una exepción");
        }
    }
    
    public static void main(String[] args) {
        HiloInter h1 = new HiloInter();
        h1.start();

        try {
            sleep(5000);
        } catch (InterruptedException ex) {
            System.getLogger(HiloInter.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        //Se envía la petición de interrupción al hilo
        //si está bloqueado, en este caso por spleep(), se
        //lanza la interrupción 'InterrupredExpetion'
        h1.interrupt();
    }
}
