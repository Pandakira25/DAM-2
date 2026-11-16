import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        // Configuración de la conexión (cambia 'sakila' por la base de datos que quieras, o 'mysql')
        // Si tu MySQL está en local y usa el puerto por defecto 3306:
        String servidor = "localhost";
        String puerto = "3306";
        String baseDatos = "sakila"; // Puedes cambiar a otra BBDD o dejar vacía si solo pruebas el servidor
        String usuario = "root";
        String password = "alumno.uem"; // <-- Pon aquí la contraseña de root de tu MySQL

        // Construcción de la URL de conexión JDBC
        String url = "jdbc:mysql://" + servidor + ":" + puerto + "/" + baseDatos + ""; //           + "?useSSL=false&serverTimezone=UTC";

        System.out.println("Intentando conectar a MySQL...");

        // Bloque try-with-resources para asegurar que la conexión se cierra correctamente
        try (Connection conexion = DriverManager.getConnection(url, usuario, password)) {
            
            if (conexion != null && !conexion.isClosed()) {
                System.out.println("\n¡CONEXIÓN EXITOSA! La base de datos está funcionando correctamente.\n");
                
                // ----------------------------------------------------
                // Extracción y visualización de parámetros de la BD
                // ----------------------------------------------------
                DatabaseMetaData metaData = conexion.getMetaData();
                
                /*
                System.out.println("========== PARÁMETROS DE LA BASE DE DATOS ==========");
                System.out.println("• Nombre del SGBD: " + metaData.getDatabaseProductName());
                System.out.println("• Versión del SGBD: " + metaData.getDatabaseProductVersion());
                System.out.println("• Nombre del Driver JDBC: " + metaData.getDriverName());
                System.out.println("• Versión del Driver JDBC: " + metaData.getDriverVersion());
                System.out.println("• Usuario conectado: " + metaData.getUserName());
                System.out.println("• URL de conexión: " + metaData.getURL());
                System.out.println("• ¿Permite transacciones?: " + metaData.supportsTransactions());
                System.out.println("====================================================");
                */
                
                

                //ResultSet rs = metaData.getTables();


                conexion.close();
            }

        } catch (SQLException e) {
            System.err.println("\nERROR: No se pudo establecer la conexión con la base de datos.");
            System.err.println("Código de error (SQLState): " + e.getSQLState());
            System.err.println("Mensaje: " + e.getMessage());
            
            // Diagnóstico rápido según el error
            if (e.getMessage().contains("Access denied")) {
                System.err.println("-> Causa probable: La contraseña del usuario 'root' es incorrecta.");
            } else if (e.getMessage().contains("Communications link failure")) {
                System.err.println("-> Causa probable: El servidor MySQL está apagado o el puerto 3306 es inaccesible.");
            }
        }
    }
}
