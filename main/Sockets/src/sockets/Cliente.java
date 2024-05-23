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
            String dir = "192.168.177.1";   // AQUI SE TIENE QUE SUSTITUIR LA IP POR LA DE LA MAQUINA DEL SERVIDOR
// AQUI SE TIENE QUE SUSTITUIR LA IP POR LA DE LA MAQUINA DEL SERVIDOR
            
            
            Socket sc = new Socket(dir, 5001);
            //  <-------------------
            
            
            DataInputStream in = new DataInputStream(sc.getInputStream());
            DataOutputStream out = new DataOutputStream(sc.getOutputStream());
            
            

            
            int puertoRandom = generaPuertoAleatorio(5001, 5100);
            Thread hiloEscucha = new Thread(new ClienteServidorHilo(puertoRandom));
            //ClienteServidorHilo hiloEscucha = new ClienteServidorHilo();
            ClienteHilo hiloMenu = new ClienteHilo(in, out, sc, puertoRandom);
            hiloEscucha.start();
            hiloMenu.start();
            hiloMenu.join();
            hiloEscucha.join();
            
            //ClienteServidorHilo hiloEscucha = new ClienteServidorHilo();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static int generaPuertoAleatorio(int minimo, int maximo){
        return (int)Math.floor(Math.random() * (maximo-minimo+1) + (minimo));
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

