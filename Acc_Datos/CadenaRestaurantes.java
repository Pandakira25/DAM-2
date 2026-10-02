import java.io.Serializable;

public class CadenaRestaurantes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private int id;

    public CadenaRestaurantes() {
    }

    public CadenaRestaurantes(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CadenaRestaurantes [nombre=" + nombre + ", id=" + id + "]";
    }
}
