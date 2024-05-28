package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jessica Castro
 * Farid Pozos
 * Andrés Montes
 */
public class NodoMaestroHilo extends Thread {
    
    private int prNM;
    private String ipNM;
    private String nombreNM;
    private ClienteServidorHilo hiloEscuchaNM;

    public NodoMaestroHilo(String ip, String nombre, int pr, ClienteServidorHilo hiloEscucha) {
        this.ipNM = ip;
        this.nombreNM = nombre;
        this.prNM = pr;
        this.hiloEscuchaNM = hiloEscucha;
    }
    
    @Override
    public void run(){
        Connection conexion = ConexionSQL.conectar();
        Scanner sn = new Scanner(System.in);
        sn.useDelimiter("\n");

        String mensaje;
        int opcion = 0;
        boolean salir = false;

        File f = new File("Conversaciones-" + nombreNM + ".txt");   // ALMACENAR CONVERSACIONES

        while(!salir){
            try {
                System.out.println("1. Ingeniero");
                System.out.println("2. Usuario");
                System.out.println("3. Mandar mensaje");
                System.out.println("4. Salir");

                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        ClienteHilo.MenuIngeniero(sn);
                        break;
                    case 2:
                        ClienteHilo.MenuUsuario(sn);
                        break;
                    case 3:
                        System.out.println("Nodos conectados:");
                        for(int i = 0; i < hiloEscuchaNM.getMoTotalUsr(); i++){
                            System.out.println("Presiona " + (i + 1) + " para " + hiloEscuchaNM.getMo().split("/")[i].split(",")[0]);
                        }
                        
                        System.out.println("\nMandar mensaje a nodo: ");
                        int usr = sn.nextInt() - 1;
                        
                        System.out.println("Escribe el mensaje:");   // Mensaje
                        mensaje = sn.next();                // 
                        String ipD = hiloEscuchaNM.getMo().split("/")[usr].split(",")[1];                         // ip
                        int puertoD = Integer.parseInt(hiloEscuchaNM.getMo().split("/")[usr].split(",")[2]);      // puerto

                        try(
                            Socket ss = new Socket(ipD, puertoD);
                            DataOutputStream outOut = new DataOutputStream(ss.getOutputStream());   // Envio de informacion al cliente destino
                            DataInputStream inIn = new DataInputStream(ss.getInputStream());
                        ){
                        
                            LocalDateTime locaDate = LocalDateTime.now();
                            int hours  = locaDate.getHour();
                            int minutes = locaDate.getMinute();
                            int seconds = locaDate.getSecond();

                            String conversacion = nombreNM + ": " + mensaje + 
                                    " (Hora: " + hours  + ":"+ minutes +":"+ seconds + ")";

                            outOut.writeUTF(conversacion);
                            String confirmacion = inIn.readUTF();

                            System.out.println(confirmacion);
                            
                            almacenarMensaje(f, conversacion + '\n' + confirmacion); // Se almacena la conversacion
                            System.out.println("Historial almacenado");

                            inIn.close();
                            outOut.close();
                            ss.close();
                        } catch (ConnectException ce){
                            System.out.println("Error al establecer una conexion: " + ce.getMessage());
                            System.out.println("El nodo al que desea enviar mensaje se encuentra desconectado");
                        }
                        
                        break;
                    case 4:
                        System.out.println("Desconectando... " + ipNM + "," + String.valueOf(prNM)); // "Desconectando";
                        
                        Socket sf = new Socket(ipNM, prNM);
                        DataOutputStream outF = new DataOutputStream(sf.getOutputStream());
                        
                        outF.writeUTF("Finaliza");
                        
                        System.out.println("El nodo " + nombreNM + " se desconecto");
                        
                        outF.close();
                        sf.close();
                        
                        try(
                            Socket sfs = new Socket(ipNM, 5000);
                            DataOutputStream outfs = new DataOutputStream(sfs.getOutputStream());   // Envio de informacion al cliente destino
                        ){
                            outfs.writeUTF("Finaliza");

                            outfs.close();
                            sfs.close();
                        } catch (ConnectException ce){
                            System.out.println("Error al establecer una conexion: " + ce.getMessage());
                            System.out.println("El nodo al que desea enviar mensaje se encuentra desconectado");
                        }
                        
                        salir = true;
                        break;
                    default:
                        mensaje = "Solo los numeros que estan en pantalla";
                        System.out.println(mensaje);
                        break;
                }

            } catch (IOException ex) {
                Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex){
            }

        }
            ConexionSQL.desconectar(conexion);
    }
    
    public void almacenarMensaje(File f, String mensaje) throws IOException{
        
        FileWriter fw = new FileWriter(f, true);
        fw.write(mensaje + "\r\n");
        fw.close();
        
    }
    
}
