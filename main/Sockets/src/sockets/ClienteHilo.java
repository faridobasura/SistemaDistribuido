package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private String ip;
    private String nombre;
    private ClienteServidorHilo hiloEscucha;

    public ClienteHilo(DataInputStream in, DataOutputStream out, Socket sc, int pr, 
            String ip, String nombre, ClienteServidorHilo hiloEscucha) {
        this.in = in;
        this.out = out;
        this.sc = sc;
        this.pr = pr;
        this.ip = ip;
        this.nombre = nombre;
        this.hiloEscucha = hiloEscucha;
    }
    
    public ClienteHilo(){
        
    }
    
    @Override
    public void run(){
        Connection conexion = ConexionSQL.conectar();
        Scanner sn = new Scanner(System.in);
        sn.useDelimiter("\n");

        String mensaje;
        int opcion = 0;
        boolean salir = false;

        File f = new File("Conversaciones-" + nombre + ".txt");   // ALMACENAR CONVERSACIONES

        while(!salir){
            try {
                System.out.println("1. Ingeniero");
                System.out.println("2. Usuario");
                System.out.println("3. Mandar mensaje");
                System.out.println("4. Salir");

                opcion = sn.nextInt();
                out.writeInt(opcion);

                switch (opcion) {
                    case 1:
                        ClienteHilo.MenuIngeniero(sn);
                        break;
                    case 2:
                        ClienteHilo.MenuUsuario(sn);
                        break;
                    case 3:
                        System.out.println("Nodos conectados:");
                        for(int i = 0; i < hiloEscucha.getMoTotalUsr(); i++){
                            System.out.println("Presiona " + (i + 1) + " para " + hiloEscucha.getMo().split("/")[i].split(",")[0]);
                        }
                        
                        System.out.println("\nMandar mensaje a nodo: ");
                        int usr = sn.nextInt() - 1;
                        
                        System.out.println(in.readUTF());   // Mensaje
                        mensaje = sn.next();                // 
                        out.writeUTF(mensaje + "/" + 
                                hiloEscucha.getMo().split("/")[usr].split(",")[1] + "/" +   // ip
                                hiloEscucha.getMo().split("/")[usr].split(",")[2]);         // puerto
                        
                        System.out.println(in.readUTF());   // Confirmacion de recibido
                        
                        out.writeUTF("Historial almacenado");
                        almacenarMensaje(f, in.readUTF());
                        
                        break;
                    case 4:
                        mensaje = "Desconectando...," + ip + "," + String.valueOf(pr); // "Desconectando";
                        out.writeUTF(mensaje);
                        in.close();
                        out.close();
                        sc.close();
                        salir = true;
                        break;
                    default:
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                        break;
                }

            } catch (NumberFormatException ex){
                
            } catch (IOException ex) {
                Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
            } 

        }
            ConexionSQL.desconectar(conexion);
    }
    
    public void almacenarMensaje(File f, String mensaje) throws IOException{
        
        FileWriter fw = new FileWriter(f, true);
        fw.write(mensaje + "\r\n");
        fw.close();
        
    }
    
    public static void MenuIngeniero(Scanner scanner){
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
    
    public static void MenuIngenierio_Ingeniero(Scanner scanner) {
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
    public static void MenuIngeniero_ActualizacionIngenieros(Scanner scanner) {
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
    }
    public static void MenuIngenierio_Usuario(Scanner scanner) {
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
      public static void MenuIngeniero_ActualizacionUsuarios(Scanner scanner) {
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
     public static void MenuIngeniero_Ticket(Scanner scanner) {
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
      public static void MenuIngeniero_Dispositivo(Scanner scanner) {
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
       public static void MenuIngeniero_ActualizacionDispositivo(Scanner scanner) {
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
     public static void MenuUsuario(Scanner scanner) {
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
     public static void MenuUsuario_Ticket(Scanner scanner) {
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
    

