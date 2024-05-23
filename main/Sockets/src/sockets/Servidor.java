/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jessica Castro
 * Farid Pozos
 * Andrés Montes
 */
public class Servidor {
    private static final String CATALOGO_FILE = "C:\\Users\\Usuario\\Desktop\\SistemaDistribuido\\SistemaDistribuido\\catalogoClientes.dat";
    private static final Map<String, String> catalogoClientes = new HashMap<>();

   public static void main(String[] args) {
        int contadorClientes = 0;
        try {
            ServerSocket server = new ServerSocket(5000);

            System.out.println("Servidor iniciado");
            cargarCatalogo();
            agregarEntradaCatalogo("CDMX", "192.168.129.130");
            agregarEntradaCatalogo("OAXACA", "192.168.129.131");
            agregarEntradaCatalogo("GUANAJUATO", "192.168.129.132");
            agregarEntradaCatalogo("CHIAPAS", "192.168.129.133");

            while (true) {
                Socket sc = server.accept();
                contadorClientes++;
                String direccionIP = obtenerDireccionIP();
                String nombreSucursal = catalogoClientes.get(direccionIP);

                if (nombreSucursal != null) {
                    ServidorHilo hilo = new ServidorHilo(contadorClientes, direccionIP, nombreSucursal);
                    hilo.start();
                } else {
                    System.out.println("Dirección IP no encontrada en el catálogo: " + direccionIP);
                    sc.close();
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Map<String, String> cargarCatalogoClientes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CATALOGO_FILE))) {
            return (Map<String, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
            return new HashMap<>();
        }
    }
    public static String obtenerDireccionIP() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLoopbackAddress() && !addr.getHostAddress().contains(":")) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "No se ha podido obtener la direccion IP"; // Retorna null si no se puede obtener la dirección IP
    }
    public static void cargarCatalogo() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CATALOGO_FILE))) {
            catalogoClientes.putAll((Map<String, String>) ois.readObject());
        } catch (FileNotFoundException e) {
            System.out.println("El archivo de catálogo no existe. Se creará uno nuevo.");
        } catch (IOException | ClassNotFoundException e) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void agregarEntradaCatalogo(String sucursal, String direccionIP) {
        catalogoClientes.put(direccionIP, sucursal);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CATALOGO_FILE))) {
            oos.writeObject(catalogoClientes);
            System.out.println("Entrada agregada al catálogo y guardada en el archivo.");
        } catch (IOException e) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}