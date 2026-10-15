import java.io.Serializable;

public class RestauranteInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int idCadena;
    private boolean activo;
    private int numeroEmpleados;
    private String direccion;
    private long facturacion;

    public RestauranteInfo() {
    }

    public RestauranteInfo(int id, int idCadena, boolean activo,
                           int numeroEmpleados, String direccion, long facturacion) {
        this.id = id;
        this.idCadena = idCadena;
        this.activo = activo;
        this.numeroEmpleados = numeroEmpleados;
        this.direccion = direccion;
        this.facturacion = facturacion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCadena() { return idCadena; }
    public void setIdCadena(int idCadena) { this.idCadena = idCadena; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public int getNumeroEmpleados() { return numeroEmpleados; }
    public void setNumeroEmpleados(int numeroEmpleados) { this.numeroEmpleados = numeroEmpleados; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public long getFacturacion() { return facturacion; }
    public void setFacturacion(long facturacion) { this.facturacion = facturacion; }

    @Override
    public String toString() {
        return "Restaurante [id=" + id
                + ", idCadena=" + idCadena
                + ", activo=" + activo
                + ", empleados=" + numeroEmpleados
                + ", direccion=" + direccion
                + ", facturacion=" + facturacion + "]";
    }
}