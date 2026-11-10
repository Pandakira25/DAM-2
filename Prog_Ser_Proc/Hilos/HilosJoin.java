package Hilos;

public class HilosJoin extends  Thread{
    int n;

    public static void main(String[] args) {
        //Creación de los hilos
        HilosJoin hj1 = new HilosJoin("hilo_1", 2);
        HilosJoin hj2 = new HilosJoin("hilo_2", 5);
        HilosJoin hj3 = new HilosJoin("hilo_3", 7);

        //Inicio de los hilos
        hj1.start();
        hj2.start();
        hj3.start();

        try {
            //Se aplica la función join a los hilos desde main
            //de esta forma espera hasta que termine la ejecución para finalizar el main
            hj1.join();
            hj2.join();
            hj3.join();
        } catch (InterruptedException ex) {
            System.getLogger(HilosJoin.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        

        System.out.println("Programa finalizado --> main()");
    }

    public HilosJoin(String nombre, int n) {
        super(nombre);
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < n; i++) {
            System.out.println("Hilo " + getName() + ": " + (i+1));
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                System.getLogger(HilosJoin.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        System.out.println("Bucle del hilo '" + getName() + "' finalizado");
    }

    
}
