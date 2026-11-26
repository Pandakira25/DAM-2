class Restaurante {

        int id;
        int idCadena;
        boolean activo;
        int empleados;
        String direccion;
        long facturacion;

        Restaurante() {
        }

        Restaurante(int id, int idCadena, boolean activo, int empleados, String direccion, long facturacion) {
            this.id = id;
            this.idCadena = idCadena;
            this.activo = activo;
            this.empleados = empleados;
            this.direccion = direccion;
            this.facturacion = facturacion;
        }

        public String toString() {
            return "Restaurante{id=" + id + ", idCadena=" + idCadena
                    + ", activo=" + activo + ", empleados=" + empleados
                    + ", direccion='" + direccion + "', facturacion=" + facturacion + "}";
        }
    }
