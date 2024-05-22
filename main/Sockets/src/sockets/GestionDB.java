/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sockets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class GestionDB {
        private static final String DireccionDB = "jdbc:sqlite:C:\\Users\\Usuario\\Desktop\\SistemaDistribuido\\SistemaDistribuido\\dbSistema.db"; 
        private static final Scanner scanner = new Scanner(System.in);

        // Método para agregar un usuario a la base de datos
    public static void agregarUsuario() {
        System.out.println("Ingrese el nombre:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese los apellidos:");
        String apellidos = scanner.nextLine();
        System.out.println("Ingrese el correo electrónico:");
        String correo = scanner.nextLine();
        System.out.println("Ingrese el teléfono:");
        String telefono = scanner.nextLine();
        System.out.println("Ingrese la ubicación:");
        String ubicacion = scanner.nextLine();

        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            String sql = "INSERT INTO USUARIOS (NOMBRE, APELLIDOS, CORREO_ELECTRONICO, TELEFONO, UBICACION) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setString(1, nombre);
                statement.setString(2, apellidos);
                statement.setString(3, correo);
                statement.setString(4, telefono);
                statement.setString(5, ubicacion);
                statement.executeUpdate();
                System.out.println("Agregando Usuario...");
                System.out.println("Usuario agregado correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
        }
    }

    // Método para eliminar un usuario de la base de datos
    public static void eliminarUsuario() {
        System.out.println("Ingrese el ID del usuario a eliminar:");
        int idUsuario = Integer.parseInt(scanner.nextLine());

        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            String sql = "DELETE FROM USUARIOS WHERE ID_USUARIO = ?";
            try (PreparedStatement statement = conexion.prepareStatement(sql)) {
                statement.setInt(1, idUsuario);
                statement.executeUpdate();
                System.out.println("Eliminando usuario...");
                System.out.println("Usuario eliminado correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    // Método para agregar un ingeniero a la base de datos
    public static void agregarIngeniero() {
        System.out.println("Ingrese el ID del ingeniero:");
        int idIngeniero = Integer.parseInt(scanner.nextLine());
        System.out.println("Ingrese el nombre:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese la especialidad:");
        String especialidad = scanner.nextLine();
        System.out.println("Ingrese la ubicación:");
        String ubicacion = scanner.nextLine();

        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            String sql = "INSERT INTO INGENIEROS (ID_INGENIERO, NOMBRE, ESPECIALIDAD, UBICACION) VALUES (?, ?, ?, ?)";
            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, idIngeniero);
                consulta.setString(2, nombre);
                consulta.setString(3, especialidad);
                consulta.setString(4, ubicacion);
                consulta.executeUpdate();
                System.out.println("Agregando Ingeniero...");
                System.out.println("Ingeniero agregado correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar ingeniero: " + e.getMessage());
        }
    }

    // Método para eliminar un ingeniero de la base de datos
    public static void eliminarIngeniero() {
        System.out.println("Ingrese el ID del ingeniero a eliminar:");
        int idIngeniero = Integer.parseInt(scanner.nextLine());

        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            String sql = "DELETE FROM INGENIEROS WHERE ID_INGENIERO = ?";
            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, idIngeniero);
                consulta.executeUpdate();
                System.out.println("Eliminando Ingeniero...");
                System.out.println("Ingeniero eliminado correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar ingeniero: " + e.getMessage());
        }
    }

    // Método para agregar un dispositivo a la base de datos
    public static void agregarDispositivo() {
        System.out.println("Ingrese el tipo de dispositivo:");
        String tipoDispositivo = scanner.nextLine();
        System.out.println("Ingrese el número de serie:");
        String numeroSerie = scanner.nextLine();
        System.out.println("Ingrese el modelo:");
        String modelo = scanner.nextLine();
        System.out.println("Ingrese la marca:");
        String marca = scanner.nextLine();
        System.out.println("Ingrese la ubicación actual:");
        String ubicacionActual = scanner.nextLine();

        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            String sql = "INSERT INTO DISPOSITIVOS (TIPO_DISPOSITIVO, NUMERO_SERIE, MODELO, MARCA, UBICACION_ACTUAL) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setString(1, tipoDispositivo);
                consulta.setString(2, numeroSerie);
                consulta.setString(3, modelo);
                consulta.setString(4, marca);
                consulta.setString(5, ubicacionActual);
                consulta.executeUpdate();
                System.out.println("Agregando Dispositivo...");
                System.out.println("Dispositivo agregado correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al agregar dispositivo: " + e.getMessage());
        }
    }

    // Método para eliminar un dispositivo de la base de datos
    public static void eliminarDispositivo() {
        System.out.println("Ingrese el ID del dispositivo a eliminar:");
        int idDispositivo = Integer.parseInt(scanner.nextLine());

        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            String sql = "DELETE FROM DISPOSITIVOS WHERE ID_DISPOSITIVO = ?";
            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setInt(1, idDispositivo);
                consulta.executeUpdate();
                System.out.println("Eliminando Dispositivo...");
                System.out.println("Dispositivo eliminado correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar dispositivo: " + e.getMessage());
        }
    }
    
}
