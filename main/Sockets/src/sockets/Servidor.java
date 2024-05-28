package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jessica Castro
 * Farid Pozos
 * Andrés Montes
 */
public class Servidor {
            
    /**
     * @param ips Lista de ips de las maquinas. Su orden en la lista corresponde a su ID.
     * El ID mayor sera considerado el nodo maestro.
     * @param ID es el id de la maquina que esta siendo el servidor
     */
    public static void main(ArrayList<String> ips, int ID) {
        
        String nombreMaestro;
        String ipMaestro;
        String puertoMaestro = "5011";
        
        try {
            ServerSocket server = new ServerSocket(5000);
            Socket sc;
            
            nombreMaestro = "Sucursal " + (ID + 1);
            ipMaestro = ips.get(ID);
            
            int nC = 1; //Numero de cliente
            ArrayList<String> mo = new ArrayList<>();   // maquinas online = <nombre>,<ip>,<puerto>
            
            System.out.println("Nodo maestro iniciado");
            
            ClienteServidorHilo hiloEscucha = new ClienteServidorHilo(5011, ipMaestro, nombreMaestro);
            NodoMaestroHilo hiloMaestro = new NodoMaestroHilo(ipMaestro, nombreMaestro, 5011, hiloEscucha);
            
            hiloEscucha.start();
            hiloMaestro.start();
            
            maquinasOnline(mo, nombreMaestro, ipMaestro, puertoMaestro);
            
            boolean escucha = true;
            
            while(escucha){
                
                sc = server.accept();
                
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                
                out.writeUTF("Identificando ...");
                String mr = in.readUTF();
                
                if(mr.contains("Finaliza")){
                    in.close();
                    out.close();
                    sc.close();
                    escucha = false;
                    System.out.println("---- Finaliza servidor ----");
                }
                else{
                    //----------- DETECCION ONLINE --------- 
                    InetAddress ipInet = sc.getInetAddress();   // Obtiene direccion del cliente en formato inet
                    String ipCliente = ipInet.getHostAddress();  // Obtenemos la ip del cliente que se acaba de conectar

                    InetAddress ipLocal = sc.getLocalAddress();
                    String ipServidor = ipLocal.getHostAddress();
                    //----------- ---------------- --------- 

                    System.out.println("ip nodo: " + ipCliente + " ------- ip servidor: " + ipServidor);

                    System.out.println("Nuevo nodo (" + nC + ")");
                    String[] mensaje = mr.split(",");
                    String nombreCliente = mensaje[0];
                    String puertoCliente = mensaje[1];
                    out.writeUTF(ipCliente);

                    ServidorHilo hilo = new ServidorHilo(in, out, nombreCliente);
                    hilo.start();

                    System.out.println("Creada la conexion con el nodo " + nombreCliente + " con ip: " + ipCliente);
                    nC += 1;

                    maquinasOnline(mo, nombreCliente, ipCliente, puertoCliente);
                }
                
                
                
            }
        } catch (IOException ex){
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void maquinasOnline(ArrayList<String> mo, String nombreCliente, String ipCliente, String puertoCliente){
        String moStr = "";
        
        if(!mo.contains(nombreCliente + "," + ipCliente + "," + puertoCliente)){
            mo.add(nombreCliente + "," + ipCliente + "," + puertoCliente);
        }

        System.out.println("\n----------------- Maquinas conectadas -----------------");
        for(int i = 0; i < mo.size(); i++){
            System.out.println(mo.get(i));
            moStr = moStr + mo.get(i) + "/";
        }
        System.out.println("-------------------------------------------------------\n");

        for(int i = 0; i < mo.size(); i++){
            System.out.println("----------> " + mo.get(i).split(",")[1]);
            try (Socket so = new Socket(mo.get(i).split(",")[1], 5011); 
                    DataOutputStream outOnline = new DataOutputStream(so.getOutputStream())) {
                outOnline.writeUTF(moStr);
                outOnline.close();
                so.close();
            } catch (ConnectException ce){
                System.out.println("Error al establecer una conexion:" + ce.getMessage());
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
