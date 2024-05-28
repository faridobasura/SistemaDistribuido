package sockets;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sockets {
    
    private static ArrayList<String> maquinas = new ArrayList<String>();

    /**
     * @param args Indica el numero de maquina que se esta ejecutando
     */
    public static void main(String[] args) {    //public static void main(String[] args)
        
        maquinas.add("192.168.152.129");    // ID = 0 m1  
        maquinas.add("192.168.152.131");    // ID = 1 m2
        maquinas.add("192.168.152.130");    // ID = 2 m3  Si la primera maquina no esta conectada, la proxima sera el nodo maestro
        maquinas.add("192.168.152.133");    // ID = 3 m5  Maquina que sera el nodo maestro por tener el ID mayor
        
        int IDmax = maquinas.size() - 1;
        int IDmaquina = Integer.valueOf(args[0]);
        
        nodoNuevoBuscaNodoMaestro(maquinas, IDmax, IDmaquina - 1);

    }
    
    public static void nodoNuevoBuscaNodoMaestro(ArrayList<String> ips, int i, int ID){
        if(i > -1){
            try {
                Socket sc = new Socket(ips.get(i), 5000);
                System.out.println("Nodo no maestro");
                Cliente nodo = new Cliente();
                nodo.main(sc, ID);
            } catch (IOException ex) {
                i--;
                nodoNuevoBuscaNodoMaestro(ips, i, ID);
            }
        }
        else{
            System.out.println("Nodo maestro");
            Servidor nodoMaestro = new Servidor();
            nodoMaestro.main(ips, ID);
        }
        
        
    }
    
}
