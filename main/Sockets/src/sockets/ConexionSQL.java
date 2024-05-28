package sockets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
/**
 *
 * @autho@author Jessica Castro
 * Farid Pozos
 * Andrés Montes
 */
public class ConexionSQL {
    //Ruta de la base de datos SQLITE
    private static final String DireccionDB = "jdbc:sqlite:dbSistema.db"; // C:\\Users\\Jessica Castro\\Desktop\\S-10\\SistemaDistribuido\\dbSistema.db
    
    //Metodo que establece la conexion
    public static Connection conectar(){
        Connection conexion = null;
        try {
            // Registrar el controlador JDBC de SQLite
            Class.forName("org.sqlite.JDBC");
            // Establecer la conexión
            conexion = DriverManager.getConnection(DireccionDB);
            System.out.println("Conexión establecida con la base de datos SQLite.");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar a la base de datos SQLite: " + e.getMessage());
        }
        return conexion;
    }
    
    // Método para cerrar la conexión
    public static void desconectar(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
  public static String getDireccionDB() {
        return DireccionDB;
    }
  public static void agregarUsuario() {
        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Nombre del usuario: ");
            String nombre = scanner.nextLine();

            System.out.print("Apellidos del usuario: ");
            String apellidos = scanner.nextLine();

            System.out.print("Correo electrónico del usuario: ");
            String correo = scanner.nextLine();

            System.out.print("Teléfono del usuario: ");
            String telefono = scanner.nextLine();

            System.out.print("Ubicación del usuario: ");
            String ubicacion = scanner.nextLine();

            String sql = "INSERT INTO USUARIOS (NOMBRE, APELLIDOS, CORREO_ELECTRONICO, TELEFONO, UBICACION) " +
                         "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setString(1, nombre);
                statement.setString(2, apellidos);
                statement.setString(3, correo);
                statement.setString(4, telefono);
                statement.setString(5, ubicacion);

                int filasInsertadas = statement.executeUpdate();
                if (filasInsertadas > 0) {
                    System.out.println("Usuario agregado correctamente.");
                } else {
                    System.out.println("No se pudo agregar el usuario.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
        }
  }
}
