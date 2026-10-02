import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LeerRestaurantes {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(GuardarRestaurantes.FICHERO))) {
            ArrayList<Restaurante> restaurantes = (ArrayList<Restaurante>) ois.readObject();

            System.out.println("Restaurantes leídos del fichero:");
            for (Restaurante r : restaurantes) {
                System.out.println(r);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada: " + e.getMessage());
        }
    }
}
