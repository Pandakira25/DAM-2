import java.io.Serializable;

public class Restaurante extends CadenaRestaurantes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String direccion;
    private int numeroEmpleados;

    public Restaurante() {
        super();
    }

    public Restaurante(String nombre, int id, String direccion, int numeroEmpleados) {
        super(nombre, id);
        this.direccion = direccion;
        this.numeroEmpleados = numeroEmpleados;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNumeroEmpleados() {
        return numeroEmpleados;
    }

    public void setNumeroEmpleados(int numeroEmpleados) {
        this.numeroEmpleados = numeroEmpleados;
    }

    @Override
    public String toString() {
        return "Restaurante [" + super.toString()
                + ", direccion=" + direccion
                + ", numeroEmpleados=" + numeroEmpleados + "]";
    }
}
