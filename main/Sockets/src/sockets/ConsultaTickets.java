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
public class ConsultaTickets extends Consulta{

    public ConsultaTickets(String DireccionDB) {
        super(DireccionDB);
    }

    @Override
    public void ejecutar() {
        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            String sql = "SELECT * FROM TICKETS";
            try (Statement consulta = conexion.createStatement();
                 ResultSet tabla_resultado = consulta.executeQuery(sql)) {
                if (!tabla_resultado.isBeforeFirst()) {
                    System.out.println("No hay tickets en la base de datos.");
                } else {
                    while (tabla_resultado.next()) {
                        int idTicket = tabla_resultado.getInt("ID_TICKET");
                        int idUsuario = tabla_resultado.getInt("ID_USUARIO");
                        int idIngeniero = tabla_resultado.getInt("ID_INGENIERO");
                        int idDispositivo = tabla_resultado.getInt("ID_DISPOSITIVO");
                        String fechaCreacion = tabla_resultado.getString("FECHA_CREACION");
                        String fechaActualizacion = tabla_resultado.getString("FECHA_ACTUALIZACION");
                        String descripcion = tabla_resultado.getString("DESCRIPCION");
                        String estado = tabla_resultado.getString("ESTADO");
                        int prioridad = tabla_resultado.getInt("PRIORIDAD");

                        // Procesar resultados para tickets
                        System.out.println("ID Ticket: " + idTicket);
                        System.out.println("ID Usuario: " + idUsuario);
                        System.out.println("ID Ingeniero: " + idIngeniero);
                        System.out.println("ID Dispositivo: " + idDispositivo);
                        System.out.println("Fecha Creación: " + fechaCreacion);
                        System.out.println("Fecha Actualización: " + fechaActualizacion);
                        System.out.println("Descripción: " + descripcion);
                        System.out.println("Estado: " + estado);
                        System.out.println("Prioridad: " + prioridad);
                        System.out.println("-------------------------------------");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar tickets: " + e.getMessage());
        }
    }
    
}
