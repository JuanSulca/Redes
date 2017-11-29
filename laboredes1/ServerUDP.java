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
    
    private static int PORT = 6000;
    
    public static void main(String[] args) throws IOException{
        DatagramSocket serverSocket = new DatagramSocket(PORT);
        System.err.println("Server listening on port " + PORT + "using UDP connection\n");
        long initialTime = System.currentTimeMillis();
        System.out.println("Tiempo inicial: " + initialTime + "\n");
        try{
            while(true){
                //receive packet
                byte bufferReceive[] = new byte[128];
                DatagramPacket receivePacket = new DatagramPacket(bufferReceive, bufferReceive.length);
                serverSocket.receive(receivePacket);
                InetAddress clientAddres = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                System.out.println("Client port: " + clientPort + "\n");
                
                //send packet
                String msg = "Message from Server";
                byte bufferSend[] = msg.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(bufferSend, bufferSend.length, clientAddres, clientPort);
                serverSocket.send(sendPacket);
            }
        }finally{
            serverSocket.close();
        }
    }
    
}
