import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GuardarRestaurantes {

    public static final String FICHERO = "restaurantes02_10_26.dat";

    public static void main(String[] args) {
        ArrayList<Restaurante> restaurantes = new ArrayList<>();
        restaurantes.add(new Restaurante("La Parrilla", 1, "Calle Mayor 12, Madrid", 15));
        restaurantes.add(new Restaurante("El Rincón", 2, "Av. de España 45, Alcobendas", 8));
        restaurantes.add(new Restaurante("Mar y Tierra", 3, "Plaza del Sol 3, Getafe", 20));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO))) {
            oos.writeObject(restaurantes);
            System.out.println("Se han guardado " + restaurantes.size() + " restaurantes en " + FICHERO);
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: " + e.getMessage());
        }
    }
}
