package Hilos;

/*
Se pretende simular la gestión de pedidos de una tienda en la
que tres personas preparan pedidos al mismo tiempo. Utilizar
tres nombres ficticios, por ejemplo, Susana, Pedro y Juan. El
encargado será el hilo principal del programa y tendrá que
esperar a que los tres acaben antes de cerrar. Es importante el
orden de preparación de los pedidos, por lo que, cada uno de
ellos debe tener asignada una prioridad diferente.
*/

public class Actividad_Hilos_1_2 extends Thread {

    //3 hilos susana pedro y juan por prioridades
    private String n;

    public Actividad_Hilos_1_2(String n) {
        this.n = n;
    }
    
    @Override
    public void run() {
        System.out.println(n + " está preparando un pedido");
        for (int i = 0; i < 5; i++) {
            try { 
                sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Hilo " + n + " interrumpido.");
            }
        }
    }
    
    public static void main(String[] args) {
        Actividad_Hilos_1_2 susana = new Actividad_Hilos_1_2("Susana");
        Actividad_Hilos_1_2 pedro = new Actividad_Hilos_1_2("pedro");
        Actividad_Hilos_1_2 juan = new Actividad_Hilos_1_2("juan");

        susana.setPriority(Thread.MIN_PRIORITY);
        pedro.setPriority(Thread.NORM_PRIORITY);
        juan.setPriority(Thread.MAX_PRIORITY);

        susana.start();
        pedro.start();
        juan.start();

        try {
            susana.join();
            pedro.join();
            juan.join();
        } catch (InterruptedException e) {
            System.out.println("Hilo principal interrumpido.");
        }

        System.out.println("Fin main/hilo principal");
    }
}
