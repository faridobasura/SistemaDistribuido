/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Scanner sn = new Scanner(System.in);
            sn.useDelimiter("\n");
            String dir = "192.168.129.1";   // AQUI SE TIENE QUE SUSTITUIR LA IP POR LA DE LA MAQUINA DEL SERVIDOR
// AQUI SE TIENE QUE SUSTITUIR LA IP POR LA DE LA MAQUINA DEL SERVIDOR
            
            
            Socket sc = new Socket(dir, 5001);
            //  <-------------------
            
            
            DataInputStream in = new DataInputStream(sc.getInputStream());
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            
            

            
            Thread hiloEscucha = new Thread(new ClienteServidorHilo(5001));
            ClienteHilo hiloMenu = new ClienteHilo(in, out, sc, 5001);
            hiloEscucha.start();
            hiloMenu.start();
            hiloMenu.join();
            hiloEscucha.join();
            
            
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}


class PaqueteEnvio implements Serializable { // Serializable significa que las instancias de este
                                            // objeto se pueden convertir en bits para que puedan viajar por la red
    
    private String nick, ip, mensaje;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    
}

