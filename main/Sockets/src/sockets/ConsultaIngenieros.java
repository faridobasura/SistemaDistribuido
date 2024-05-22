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
 * @author Jessica Castro
 * Farid Pozos
 * Andrés Montes
 */
public class ConsultaIngenieros extends Consulta{

    public ConsultaIngenieros(String DireccionDB) {
        super(DireccionDB);
    }

    @Override
    public void ejecutar() {
        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            String sql = "SELECT * FROM INGENIEROS";
            try (Statement consulta = conexion.createStatement();
                 ResultSet tabla_resultado = consulta.executeQuery(sql)) {
                if (!tabla_resultado.isBeforeFirst()) {
                    System.out.println("No hay ingenieros en la base de datos.");
                } else {
                    while (tabla_resultado.next()) {
                        int idIngeniero = tabla_resultado.getInt("ID_INGENIERO");
                        String nombre = tabla_resultado.getString("NOMBRE");
                        String especialidad = tabla_resultado.getString("ESPECIALIDAD");
                        String ubicacion = tabla_resultado.getString("UBICACION");

                        // Procesar resultados para ingenieros
                        System.out.println("ID Ingeniero: " + idIngeniero);
                        System.out.println("Nombre: " + nombre);
                        System.out.println("Especialidad: " + especialidad);
                        System.out.println("Ubicación: " + ubicacion);
                        System.out.println("-------------------------------------");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar ingenieros: " + e.getMessage());
        }
    }
    
}
