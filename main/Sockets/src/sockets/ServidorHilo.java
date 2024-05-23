/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jessica Castro
 * Farid Pozos
 * Andrés Montes
 */
public class ServidorHilo extends Thread {
    private int ID_hiloServidor;
    private String direccionIP_hiloServidor;
    private String nombreCliente_hiloServidor;

    public ServidorHilo(int ID_hiloServidor, String direccionIP_hiloServidor, String nombreCliente_hiloServidor) {
        this.ID_hiloServidor = ID_hiloServidor;
        this.direccionIP_hiloServidor = direccionIP_hiloServidor;
        this.nombreCliente_hiloServidor = nombreCliente_hiloServidor;
    }

    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(5000);
            Socket sc;

            while (true) {
                sc = servidor.accept();

                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                System.out.println("Nuevo cliente (" + ID_hiloServidor + ")");
                out.writeUTF("Ingrese su correo electrónico:");
                String correo = in.readUTF();

                if (nombreCliente_hiloServidor.equals(correo)) {
                    out.writeUTF("¡Bienvenido " + nombreCliente_hiloServidor + "!");
                } else {
                    out.writeUTF("No se encontró el correo electrónico en la base de datos.");
                }

                sc.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
  
