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
    
    private static final int SERVER_PORT = 6000; //Define el puerto del servido para la comunicacion UDP
    
    public static void main(String[] args) throws IOException{
        //Crea una pantalla para ingresar la direccion IP y almacenarlo como un string
        String serverAddress = JOptionPane.showInputDialog("Enter IP Address of a machine that is\n" + "running the date service on port " + SERVER_PORT + ":");
        //send a packet(request)
        DatagramSocket clientSocket = new DatagramSocket();
        //combierte la direccion ip del servidor en un array de bytes
        byte bufferSend[] = serverAddress.getBytes();
        //se empaqueta la direccion del servidor para ser enviada como un datagrama
        DatagramPacket sendPacket = new DatagramPacket(bufferSend, bufferSend.length, InetAddress.getByName(serverAddress), SERVER_PORT);
        //envio del datagrama
        clientSocket.send(sendPacket);
        //Recive packet
        //se crea un array de bytes para el mensaje a recivir
        byte bufferReceive[] = new byte[256];
        //se crea un datagrama para almacenar los datos de recepcion
        DatagramPacket receivePacket = new DatagramPacket(bufferReceive, bufferReceive.length);
        //Se recive la respuesta del servidor
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
