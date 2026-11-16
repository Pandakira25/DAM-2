package Hilos;

import java.util.Random;

/*
Crear un proceso que lance dos hilos.
Cada uno de ellos hace pausas de duración aleatoria de entre
10 y 500 ms, utilizando el método sleep de la clase Thread.
El hilo principal tiene que esperar a que finalicen los dos hilos
que ha lanzado, por lo que siempre terminará en último lugar.
Dado que, se va a pausar la ejecución de los dos hilos, durante
ese periodo de tiempo se podrían interrumpir. Si esto llegase a
pasar se debería lanzar la interrupción que la capture y
mostrar un mensaje.
*/

public class Actividad_Hilos_1_1 extends Thread {
    private String n;
    private final Random rd = new Random();

    public Actividad_Hilos_1_1(String n) {
        this.n = n;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Hilo --> " + n + " c = " + i);
            try {
                int pausa = rd.nextInt(491) + 10; // Le sumo porque el random solo va de 0 a lo que le diga no le puedo especificar un rango.
                sleep(pausa);
            } catch (InterruptedException ex) {
                System.out.println("Hilo " + n + " interrumpido.");
            }
        }
    }

    public static void main(String[] args) {
        // Creación de los hilos
        Actividad_Hilos_1_1 h1 = new Actividad_Hilos_1_1("Hilo 1");
        Actividad_Hilos_1_1 h2 = new Actividad_Hilos_1_1("Hilo 2");

        // Se inician los hilos
        h1.start();
        h2.start();

        try {
            // Espera a que finalicen los hilos
            h1.join();
            h2.join();
        } catch (InterruptedException ex) {
            System.out.println("Hilo principal interrumpido.");
        }

        System.out.println("Fin main/hilo principal");
    }

}
