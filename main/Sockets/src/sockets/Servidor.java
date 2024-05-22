/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
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
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            ServerSocket server = new ServerSocket(5000);
            Socket sc;
            
            
            
            ArrayList<Socket> listaClientesS = new ArrayList<>();   // Lista de sockets
            ArrayList<String> listaClientesN = new ArrayList<>();   // Lista de nombres
            ArrayList<String> listaClientesIP = new ArrayList<>();   // Lista de IPs
            int nC = 1; //Numero de cliente
            
            System.out.println("Servidor iniciado");
            while(true){
                Connection conexion = ConexionSQL.conectar();
                sc = server.accept();
                listaClientesS.add(sc);
                
                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                
                System.out.println("Nuevo cliente (" + nC + ")");
                out.writeUTF("Indica tu nombre");
                String nombreCliente = in.readUTF();
                out.writeUTF("Indica tu IP");
                String ipCliente = in.readUTF();
                
                listaClientesN.add(nombreCliente);
                listaClientesIP.add(ipCliente);
                ServidorHilo hilo = new ServidorHilo(in, out, nombreCliente);
                hilo.start();
                
                
                System.out.println("Creada la conexion con el cliente " + nombreCliente +
                " con la direccion: " + ipCliente + 
                " con el puerto: " + listaClientesS.get(nC-1).getLocalPort());
                
                nC += 1;
                ConexionSQL.desconectar(conexion);
            }
        } catch (IOException ex){
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
}
