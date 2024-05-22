/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sockets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Usuario
 */
public class ConsultaUsuarios extends Consulta {

    public ConsultaUsuarios(String DireccionDB) {
        super(DireccionDB);
    }

    @Override
    public void ejecutar() {
         // Función para consultar los usuarios en la base de datos
        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            // Crear una sentencia SQL para realizar la consulta
            String sql = "SELECT * FROM USUARIOS";

            // Crear un objeto Statement para ejecutar la consulta
            try (Statement consulta = conexion.createStatement()) {
                // Ejecutar la consulta y obtener el conjunto de resultados
                try (ResultSet tabla_resultado = consulta.executeQuery(sql)) {
                    // Iterar sobre los resultados y mostrarlos en la consola
                    while (tabla_resultado.next()) {
                        int id = tabla_resultado.getInt("ID_USUARIO");
                        String nombre = tabla_resultado.getString("NOMBRE");
                        String apellidos = tabla_resultado.getString("APELLIDOS");
                        String correoElectronico = tabla_resultado.getString("CORREO_ELECTRONICO");
                        String telefono = tabla_resultado.getString("TELEFONO");
                        String ubicacion = tabla_resultado.getString("UBICACION");

                        System.out.println("ID: " + id + ", Nombre: " + nombre + ", Apellidos: " + apellidos +
                                ", Correo Electrónico: " + correoElectronico + ", Teléfono: " + telefono +
                                ", Ubicación: " + ubicacion);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar la base de datos: " + e.getMessage());
        }
    }   
}
