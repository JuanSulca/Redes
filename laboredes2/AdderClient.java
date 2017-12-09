/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sumador;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan
 */
public class AdderClient {
    
    private BufferedReader in;
    private PrintWriter out;

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws IOException {
        AdderClient s = new AdderClient();
        s.connectToServer();
        
    }
    
    // funcion para escribir en la consola
    private void log(String message) {
        System.out.println(message);
    }
    
    //funcion de coneccion con el servidor
    public void connectToServer() throws IOException {

        //se obtiene la direccion IP del servidor
        String serverAddress = JOptionPane.showInputDialog(
            null,
            "Enter IP Address of the Server:",
            "Adder Client",
            JOptionPane.QUESTION_MESSAGE);

        //Se crea un puerto con la ip y el socket del servidor, al igual que un buffer de entrada y buffer de salida
        Socket socket = new Socket(serverAddress, 9898);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        //se lee una linea con la respuesta del servidor a la coneccion
        log(in.readLine());
        // se leen los dos numero como string y se los envia al servidor; el servidor detecta son validos o invalidos
        String a = JOptionPane.showInputDialog(null, "Enter a number a:", "Adder Client", JOptionPane.QUESTION_MESSAGE);
        String b = JOptionPane.showInputDialog(null, "Enter a number b:", "Adder Client", JOptionPane.QUESTION_MESSAGE);
        out.println(a);
        out.println(b);
        // se obtiene la respues del servidor y si esta es valida, se la muestra en una ventana de dialogo
        String response;
        try {
            response = in.readLine();
            if (response == null || response.equals("")) {
                System.exit(0);
            }
        } catch (IOException ex) {
            response = "Error: " + ex;
        }
        JOptionPane.showMessageDialog(null, "a + b = " + response);
    }
}
