package Hilos;

public class HilosEjemplo2 extends Thread{

    @Override
    public void run() {
        //Mostrar información del hilo que se está ejecutando,
        //utilizando los métodos de la clase thread
        System.out.println("Dentro del hilo: " + getName());
        System.out.println("Prioridad: " + getPriority());
        System.out.println("Identificador: " + getId());
        System.out.println("Hilos activos: " + activeCount());
    }

    public static void main(String[] args) {
        currentThread().setName("Principal");
        System.out.println("Nombre: " + currentThread().getName());
        System.out.println("Nombre: " + currentThread().toString());

        HilosEjemplo2 h = null;

        for (int i = 0; i < 3; i++) {
            //Creación del hilo
            h= new HilosEjemplo2();
            //Asignar nombre
            h.setName("Hilo " + i);
            //Asignar prioridad
            h.setPriority(i+1);
            //iniciar hilo
            h.start();

            System.out.println("\nInformación del hilo: " + h.getName() + ": " + h.toString() + "\n");
        }
    }
}
