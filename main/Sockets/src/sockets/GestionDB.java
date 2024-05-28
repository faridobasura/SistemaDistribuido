package sockets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Jessica Castro
 * Farid Pozos
 * Andrés Montes
 */
public class GestionDB {
        private static final String DireccionDB = "jdbc:sqlite:dbSistema.db"; //jdbc:sqlite:C:\\Users\\Usuario\\Desktop\\SistemaDistribuido\\SistemaDistribuido\\dbSistema.db
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
    // Método para levantar un ticket por usuario
    public static void levantarTicket() {
        System.out.println("Ingrese el ID del usuario:");
        int idUsuario = Integer.parseInt(scanner.nextLine());
        System.out.println("Ingrese el ID del dispositivo:");
        int idDispositivo = Integer.parseInt(scanner.nextLine());
        System.out.println("Ingrese la descripción del ticket:");
        String descripcion = scanner.nextLine();
        String estado = "activo";  // Estado inicial asignado automáticamente
        System.out.println("Ingrese la prioridad del ticket (número entero):");
        int prioridad = Integer.parseInt(scanner.nextLine());

        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            // Listar sucursales y permitir que el usuario seleccione una
            listarSucursales(conexion);
            System.out.println("Ingrese el ID de la sucursal:");
            int idSucursal = Integer.parseInt(scanner.nextLine());

            // Obtener el ID del ingeniero disponible
            int idIngeniero = obtenerIngenieroDisponible(conexion);
            if (idIngeniero == -1) {
                System.err.println("No se encontró un ingeniero disponible.");
                return;
            }

            // Obtener el nombre de la sucursal seleccionada
            String sucursal = obtenerNombreSucursal(conexion, idSucursal);
            if (sucursal == null) {
                System.err.println("No se encontró la sucursal con ID: " + idSucursal);
                return;
            }

            // Inserción del ticket
            String sqlInsert = "INSERT INTO TICKETS (ID_USUARIO, ID_INGENIERO, ID_DISPOSITIVO, DESCRIPCION, ESTADO, PRIORIDAD, ID_SUCURSAL) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement consultaInsert = conexion.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {
                consultaInsert.setInt(1, idUsuario);
                consultaInsert.setInt(2, idIngeniero);
                consultaInsert.setInt(3, idDispositivo);
                consultaInsert.setString(4, descripcion);
                consultaInsert.setString(5, estado);
                consultaInsert.setInt(6, prioridad);
                consultaInsert.setInt(7, idSucursal);
                consultaInsert.executeUpdate();
                
                // Obtener el ID_TICKET generado
                try (ResultSet generatedKeys = consultaInsert.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idTicket = generatedKeys.getInt(1);

                        // Generar el folio
                        String folio = idUsuario + "-" + idIngeniero + "-" + sucursal + "-" + idTicket;

                        // Actualizar el ticket con el folio generado
                        String sqlUpdate = "UPDATE TICKETS SET FOLIO = ? WHERE ID_TICKET = ?";
                        try (PreparedStatement consultaUpdate = conexion.prepareStatement(sqlUpdate)) {
                            consultaUpdate.setString(1, folio);
                            consultaUpdate.setInt(2, idTicket);
                            consultaUpdate.executeUpdate();
                        }
                        System.out.println("Levantando ticket...");
                        System.out.println("Ticket levantado correctamente con folio: " + folio);
                    } else {
                        System.err.println("Error al obtener el ID del nuevo ticket.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al levantar ticket: " + e.getMessage());
        }
    }

    // Método para listar las sucursales
    private static void listarSucursales(Connection conexion) throws SQLException {
        String sql = "SELECT ID_SUCURSAL, NOMBRE_SUCURSAL FROM SUCURSALES";
        try (PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {
            System.out.println("Sucursales disponibles:");
            while (resultado.next()) {
                int idSucursal = resultado.getInt("ID_SUCURSAL");
                String nombreSucursal = resultado.getString("NOMBRE_SUCURSAL");
                System.out.println(idSucursal + ": " + nombreSucursal);
            }
        }
    }

    // Método para obtener el nombre de la sucursal seleccionada
    private static String obtenerNombreSucursal(Connection conexion, int idSucursal) throws SQLException {
        String sql = "SELECT NOMBRE_SUCURSAL FROM SUCURSALES WHERE ID_SUCURSAL = ?";
        try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, idSucursal);
            try (ResultSet resultado = consulta.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getString("NOMBRE_SUCURSAL");
                } else {
                    return null;  // No se encontró la sucursal
                }
            }
        }
    }

    // Método para obtener un ingeniero disponible que no tenga tickets abiertos
    private static int obtenerIngenieroDisponible(Connection conexion) throws SQLException {
        String sql = "SELECT ID_INGENIERO FROM INGENIEROS WHERE ID_INGENIERO NOT IN (SELECT ID_INGENIERO FROM TICKETS WHERE ESTADO <> 'cerrado') LIMIT 1";
        try (PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet resultado = consulta.executeQuery()) {
            if (resultado.next()) {
                return resultado.getInt("ID_INGENIERO");
            } else {
                return -1;  // No se encontró ningún ingeniero disponible
            }
        }
    }

    // Método para actualizar el estado de un ticket
    public static void actualizarEstadoTicket(int idTicket, String nuevoEstado) {
        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            String sqlUpdate = "UPDATE TICKETS SET ESTADO = ? WHERE ID_TICKET = ?";
            try (PreparedStatement consultaUpdate = conexion.prepareStatement(sqlUpdate)) {
                consultaUpdate.setString(1, nuevoEstado);
                consultaUpdate.setInt(2, idTicket);
                consultaUpdate.executeUpdate();
                System.out.println("Estado del ticket actualizado correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado del ticket: " + e.getMessage());
        }
    }
    
    public static void cerrarTicket() {
    try {
        try (Connection conexion = DriverManager.getConnection(DireccionDB)) {
            System.out.println("Ingrese su ID de ingeniero:");
            int idIngeniero = Integer.parseInt(scanner.nextLine());

            String sql = "SELECT * FROM TICKETS WHERE ID_INGENIERO = ?";
            try (PreparedStatement consultaTickets = conexion.prepareStatement(sql)) {
                consultaTickets.setInt(1, idIngeniero);
                ResultSet tablaTickets = consultaTickets.executeQuery();
                if (!tablaTickets.isBeforeFirst()) {
                    System.out.println("No hay tickets asociados al ID del ingeniero.");
                    return;
                }

                System.out.println("Tickets asociados al ID del ingeniero:");
                while (tablaTickets.next()) {
                    int idTicket = tablaTickets.getInt("ID_TICKET");
                    String descripcion = tablaTickets.getString("DESCRIPCION");
                    System.out.println("ID Ticket: " + idTicket + ", Descripción: " + descripcion);
                }

                System.out.println("Ingrese el ID del ticket que desea cerrar:");
                int idTicket = Integer.parseInt(scanner.nextLine());

                System.out.println("¿Desea cerrar este ticket? (s/n):");
                String confirmacion = scanner.nextLine();
                if (confirmacion.equalsIgnoreCase("s")) {
                    sql = "UPDATE TICKETS SET ESTADO = 'CERRADO', FECHA_ACTUALIZACION = CURRENT_TIMESTAMP WHERE ID_TICKET = ?";
                    try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                        consulta.setInt(1, idTicket);
                        int filasActualizadas = consulta.executeUpdate();
                        if (filasActualizadas > 0) {
                            System.out.println("El ticket se ha cerrado correctamente.");
                        } else {
                            System.out.println("No se encontró el ticket especificado o no tiene permiso para cerrarlo.");
                        }
                    }
                } else {
                    System.out.println("Operación cancelada.");
                }
            }
        }
    } catch (NumberFormatException e) {
        System.err.println("Por favor, ingrese un número válido.");
    } catch (SQLException e) {
        System.err.println("Error al cerrar ticket: " + e.getMessage());
    }
    }

}
