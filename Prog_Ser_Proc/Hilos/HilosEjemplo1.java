package Hilos;
public class HilosEjemplo1 extends Thread{
    
    //Pasa como parámetro el nombre del hilo
    //Si no se pasa el nombre, Java crea un nombre automáticamente,
    //de la forma: Thread-0, Thread-1, ... , Tread-n
    public HilosEjemplo1(String nombre) {
        super(nombre);
        System.out.println("Creando hilo: " + getName());
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Hilo --> " + getName() + " c = " + i);
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                System.getLogger(HilosEjemplo1.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }

    public static void main(String[] args){
        //Creación de los hilos
        HilosEjemplo1 h1 = new HilosEjemplo1("Hilo 1");
        HilosEjemplo1 h2 = new HilosEjemplo1("Hilo 2");
        HilosEjemplo1 h3 = new HilosEjemplo1("Hilo 3");

        //Se inician los hilos
        h1.start();
        h2.start();
        h3.start();

        System.out.println("\n3 Hilos iniciados");
        System.out.println("Finaliza la ejecución de la función 'main()'");
    } 
}
