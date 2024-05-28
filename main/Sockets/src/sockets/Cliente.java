package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;

/**
 *
 * @author Jessica Castro
 * Farid Pozos
 * Andrés Montes
 */
public class Cliente {

    /**
     * @param sc es el socket de conexion con un nodo no maestro
     * @param ID es el id de la maquina que esta conectando
     */
    public static void main(Socket sc, int ID) {
        try {
            Scanner sn = new Scanner(System.in);
            sn.useDelimiter("\n");
            
            DataInputStream in = new DataInputStream(sc.getInputStream());
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            
            
            String mensaje = in.readUTF();  // Nombre
            System.out.println(mensaje);
            
            int puertoRandom = 5011;
            
            //String nombre = sn.next();
            String nombre = "Sucursal " + (ID + 1);
            out.writeUTF(nombre + "," + puertoRandom);

            
            String ip = in.readUTF();
            
            ClienteServidorHilo hiloEscucha = new ClienteServidorHilo(puertoRandom, ip, nombre);
            
            ClienteHilo hiloMenu = new ClienteHilo(in, out, sc, puertoRandom, ip, nombre, hiloEscucha);
            
            hiloEscucha.start();
            hiloMenu.start();
            hiloMenu.join();
            hiloEscucha.join();

            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
