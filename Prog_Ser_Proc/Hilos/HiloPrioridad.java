package Hilos;

public class HiloPrioridad  extends Thread{
    private  int contador = 0;
    private boolean stopHilo = false;
    String nombre;

    public HiloPrioridad(String nombre) {
        super(nombre);
        this.nombre = nombre;
    }

    public int getContador() {
        return contador;
    }

    public void paraHilo(){
        stopHilo = true;
    }

    @Override
    public void run() {
        while (!stopHilo) { 
            contador ++;
        }
        System.out.println("Parado hilo con Nombre: " + nombre);
    }
    
}
