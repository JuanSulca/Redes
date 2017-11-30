/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laboredes1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author Juan
 */
public class ServerUDP {

    /**
     * @param args the command line arguments
     */
    
    private static int PORT = 6000;//puerto al que esta ligado el servidor
    
    public static void main(String[] args) throws IOException{
        //Socket del servidor por donde se reciven las peticiones
        DatagramSocket serverSocket = new DatagramSocket(PORT);
        System.err.println("Server listening on port " + PORT + "using UDP connection\n");
        long initialTime = System.currentTimeMillis();//tiempo cuando se inicia el servidor
        System.out.println("Tiempo inicial: " + initialTime + "\n");
        try{
            //como debe estar esperando peticiones todo el tiempo se corre un lazo infinito
            while(true){
                //receive packet
                //array de bytes para leer el mensaje
                byte bufferReceive[] = new byte[256];
                //Datagrama para recepcion de peticion
                DatagramPacket receivePacket = new DatagramPacket(bufferReceive, bufferReceive.length);
                //recepcion del paquete
                serverSocket.receive(receivePacket);
                //se obtiene la direccion IP del cliente, del paquete recivido
                InetAddress clientAddres = receivePacket.getAddress();
                //se obtiene el puerto del cliente a partir del paquete recivido
                int clientPort = receivePacket.getPort();
                System.out.println("Client port: " + clientPort + "\n");
                
                //send packet
                String msg = "Hello there from Juan";
                //se obtiene un array de bytes del mensaje a enviar
                byte bufferSend[] = msg.getBytes();
                //empaquetado del mensaje con la direccion del cliente y el puerto del cliente
                DatagramPacket sendPacket = new DatagramPacket(bufferSend, bufferSend.length, clientAddres, clientPort);
                //envio del paquete
                serverSocket.send(sendPacket);
            }
        }finally{
            //cerrar socket
            serverSocket.close();
        }
    }
    
}
