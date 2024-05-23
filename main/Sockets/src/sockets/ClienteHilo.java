
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jessica Castro
 * Farid Pozos
 * Andrés Montes
 */
public class ClienteHilo extends Thread{
    
    private DataInputStream in;
    private DataOutputStream out;
    private Socket sc;
    private int pr;

    public ClienteHilo(DataInputStream in, DataOutputStream out, Socket sc, int pr) {
        this.in = in;
        this.out = out;
        this.sc = sc;
        this.pr = pr;
    }
    
    @Override
    public void run(){
        Connection conexion = ConexionSQL.conectar();
        Scanner sn = new Scanner(System.in);

        String mensaje;
        int opcion;
        boolean salir = false;


        while(!salir){
            try {
                System.out.println("1. Ingeniero");
                System.out.println("2. Usuario");
                System.out.println("3. Salir");

                opcion = sn.nextInt();
                out.writeInt(opcion);

                switch (opcion) {
                    case 1:
                        ClienteHilo.MenuIngeniero(sn);
                    case 2:
                        ClienteHilo.MenuUsuario(sn);
                        /* Enio de mensajes
                        System.out.println(in.readUTF());
                        mensaje = sn.next();
                        out.writeUTF(mensaje);
                        System.out.println(in.readUTF());
                        mensaje = sn.next();
                        out.writeUTF(mensaje);
                        System.out.println(in.readUTF());
                        out.writeInt(sn.nextInt());
                        break;*/
                    case 3:
                        mensaje = "Desconectando";
                        out.writeUTF(mensaje);
                        sc.close();
                        salir = true;
                        break;
                    default:
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                }

            } catch (IOException ex) {
                Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
            ConexionSQL.desconectar(conexion);
    }
    
    private static void MenuIngeniero(Scanner scanner){
        boolean salir = false;
        while(!salir){
        System.out.println("\n\tMenú de Ingeniero: ");
        System.out.println("\n1. Ingenieros");
        System.out.println("\n2. Usuarios");
        System.out.println("\n3. Tickets");
        System.out.println("\n4. Dispositivos");
        System.out.println("\n5. Regresar al menu anterior");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        switch (opcion) {
                case 1:
                    ClienteHilo.MenuIngenierio_Ingeniero(scanner);
                    break;
                case 2:
                    ClienteHilo.MenuIngenierio_Usuario(scanner);
                    break;
                case 3:
                    ClienteHilo.MenuIngeniero_Ticket(scanner);
                    break;
                case 4:
                    ClienteHilo.MenuIngeniero_Dispositivo(scanner);
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
    
    private static void MenuIngenierio_Ingeniero(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nIngenieros:");
            System.out.println("1. Consultar");
            System.out.println("2. Actualizar");
            System.out.println("3. Volver al menú anterior");
            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    System.out.println("Consultando ingenieros...");
                    ConsultaIngenieros consulta = new ConsultaIngenieros(ConexionSQL.getDireccionDB());
                    consulta.ejecutar();
                    break;
                case 2:
                    MenuIngeniero_ActualizacionIngenieros(scanner);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
    private static void MenuIngeniero_ActualizacionIngenieros(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("2. Eliminar");
            System.out.println("3. Volver al menú anterior");
            int opcion = scanner.nextInt();            System.out.println("\nActualizar Ingenieros:");
            System.out.println("1. Agregar");

            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    GestionDB.agregarIngeniero();
                    break;
                case 2:
                    GestionDB.eliminarIngeniero();
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }private static void MenuIngenierio_Usuario(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nUsuarios:");
            System.out.println("1. Consultar");
            System.out.println("2. Actualizar");
            System.out.println("3. Volver al menú anterior");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    ConsultaIngenieros consulta = new ConsultaIngenieros(ConexionSQL.getDireccionDB());
                    consulta.ejecutar();
                    break;
                case 2:
                    MenuIngeniero_ActualizacionUsuarios(scanner);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
      private static void MenuIngeniero_ActualizacionUsuarios(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\nActualizar Usuarios:");
            System.out.println("1. Agregar");
            System.out.println("2. Eliminar");
            System.out.println("3. Volver al menú anterior");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    GestionDB.agregarUsuario();                    
                    break;
                case 2:
                    GestionDB.eliminarUsuario();
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
     private static void MenuIngeniero_Ticket(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\nMenú de Tickets (Ingeniero):");
            System.out.println("1. Consultar");
            System.out.println("2. Cerrar");
            System.out.println("3. Volver al menú anterior");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    ConsultaTickets consulta = new ConsultaTickets(ConexionSQL.getDireccionDB());
                    consulta.ejecutar();
                    break;
                case 2:
                    System.out.println("Cerrando ticket...");
                    GestionDB.cerrarTicket();                    
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
      private static void MenuIngeniero_Dispositivo(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\nMenú de Dispositivos (Ingeniero):");
            System.out.println("1. Consultar");
            System.out.println("2. Actualizar");
            System.out.println("3. Volver al menú anterior");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Consultando dispositivos...");
                    ConsultaDispositivos consulta = new ConsultaDispositivos(ConexionSQL.getDireccionDB()) {};
                    consulta.ejecutar();
                    break;
                case 2:
                    MenuIngeniero_ActualizacionDispositivo(scanner);
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
       private static void MenuIngeniero_ActualizacionDispositivo(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\nActualizar Dispositivos (Ingeniero):");
            System.out.println("1. Agregar");
            System.out.println("2. Eliminar");
            System.out.println("3. Volver al menú anterior");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    GestionDB.agregarDispositivo();                    
                    break;
                case 2:
                    GestionDB.eliminarDispositivo();                    
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
     private static void MenuUsuario(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\nMenú de Usuario:");
            System.out.println("1. Tickets");
            System.out.println("2. Consultar Dispositivos");
            System.out.println("3. Volver al menú principal");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    MenuUsuario_Ticket(scanner);
                    break;
                case 2:
                    System.out.println("Consultando dispositivos...");
                    ConsultaDispositivos consulta = new ConsultaDispositivos(ConexionSQL.getDireccionDB());
                    consulta.ejecutar();
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
     private static void MenuUsuario_Ticket(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\nTickets (Usuario):");
            System.out.println("1. Consultar");
            System.out.println("2. Levantar Ticket");
            System.out.println("3. Volver al menú anterior");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Consultando tickets...");
                    ConsultaTickets consulta = new ConsultaTickets(ConexionSQL.getDireccionDB());
                    consulta.ejecutar();
                    break;
                case 2:
                    GestionDB.levantarTicket();                    
                    break;
                case 3:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        }
    }
     
}
    

