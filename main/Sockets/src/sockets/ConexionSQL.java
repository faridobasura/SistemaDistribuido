/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sockets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Usuario
 */
public class ConexionSQL {
    //Ruta de la base de datos SQLITE
    private static final String DireccionDB = "jdbc:sqlite:C:\\Users\\Usuario\\Desktop\\SistemaDistribuido\\SistemaDistribuido\\dbSistema.db"; 
    
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
}
