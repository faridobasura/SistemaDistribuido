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
public class ConsultaDispositivos extends Consulta{

    public ConsultaDispositivos(String DireccionDB) {
        super(DireccionDB);
    }

    @Override
    public void ejecutar() {
        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            String sql = "SELECT * FROM DISPOSITIVOS";
            try (Statement consulta = conexion.createStatement();
                 ResultSet tabla_resultado = consulta.executeQuery(sql)) {
                if (!tabla_resultado.isBeforeFirst()) {
                    System.out.println("No hay dispositivos en la base de datos.");
                } else {
                    while (tabla_resultado.next()) {
                        int idDispositivo = tabla_resultado.getInt("ID_DISPOSITIVO");
                        String tipoDispositivo = tabla_resultado.getString("TIPO_DISPOSITIVO");
                        String numeroSerie = tabla_resultado.getString("NUMERO_SERIE");
                        String modelo = tabla_resultado.getString("MODELO");
                        String marca = tabla_resultado.getString("MARCA");
                        String ubicacionActual = tabla_resultado.getString("UBICACION_ACTUAL");

                        // Procesar resultados para dispositivos
                        System.out.println("ID Dispositivo: " + idDispositivo);
                        System.out.println("Tipo de Dispositivo: " + tipoDispositivo);
                        System.out.println("Número de Serie: " + numeroSerie);
                        System.out.println("Modelo: " + modelo);
                        System.out.println("Marca: " + marca);
                        System.out.println("Ubicación Actual: " + ubicacionActual);
                        System.out.println("-------------------------------------");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar dispositivos: " + e.getMessage());
        }
    }
    
}
