/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laboredes1;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan
 */
public class ClientUDP {

    /**
     * @param args the command line arguments
     */
    
    private static final int SERVER_PORT = 6000;
    
    public static void main(String[] args) throws IOException{
        String serverAddress = JOptionPane.showInputDialog("Enter IP Address of a machine that is\n" + "running the date service on port " + SERVER_PORT + ":");
        //send a packet(request)
        DatagramSocket clientSocket = new DatagramSocket();
        byte bufferSend[] = serverAddress.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(bufferSend, bufferSend.length, InetAddress.getByName(serverAddress), SERVER_PORT);
        clientSocket.send(sendPacket);
        //Recive packet
        byte bufferReceive[] = new byte[128];
        DatagramPacket receivePacket = new DatagramPacket(bufferSend, bufferSend.length);
        clientSocket.receive(receivePacket);
        
        //transform bytes into string
        InputStream myInputStream = new ByteArrayInputStream(receivePacket.getData());
        BufferedReader input = new BufferedReader(new InputStreamReader(myInputStream));     
        String answer = input.readLine();
        
        //Despliega mensaje
        JOptionPane.showMessageDialog(null, answer);
        clientSocket.close();
        System.exit(0);
        
    }
    
}
