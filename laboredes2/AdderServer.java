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
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Juan
 */
public class AdderServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        System.out.println("Running adder server");
        int clientNumber = 0; // inicializaccion del contador de clientes
        ServerSocket listener = new ServerSocket(9898); //inicializacion del socket para atender las peticiones del cliente
        // se crea un thread por cada cliente que se conecta al servidor
        try {
            while (true) {
                new Adder(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }
    
    //clase adder que implementa las funciones de thread
    private static class Adder extends Thread {
        private Socket socket;
        private int clientNumber;

        //constructor que recibe el socket y el numero de cliente
        public Adder(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        @Override
        public void run() {
            try {
                // Declaracion de los buffers de entrada y salida para el thread
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                
                //envia un mensaje al cliente confirmando la coneccion
                out.println("Hello, you are client #" + clientNumber + ".");
                //suma de los numeros y contador para contar el numero de iteraciones
                int sum = 0;
                int cont = 0;
                while (true) {
                    //el bucle termina cuando han llegado dos valores
                    if(cont == 2)
                        break;
                    String input = in.readLine();//se lee la entrada y se la valida
                    log(input); // se imprime en consola la etrada para depuracion
                    try{
                        sum += Integer.parseInt(input); // se hace un cambio de string a entero para realizar la suma
                        cont++; // se aumenta el contador de iteraciones
                    }catch(Exception e){
                        out.println("NaN"); // Si el string recibido no es un numero, se envia not a number como respuesta
                        break;
                    }                    
                }
                out.println(sum); // se envia la suma de los dos numeros 
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e); //si existe algun problema se muestra un error en la consola
            } finally {
                try {
                    socket.close(); //  se cierra el socket
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?"); //si no se puede cerrar el socket, se envia un mensaje de error.
                }
                log("Connection with client# " + clientNumber + " closed");/* si todo termina como es esperado se envia un mensaje 
                diciendo que se ha cerrado la coneccion*/
            }
        }
        
        //Funcion para escribir en la consola.
        private void log(String message) {
            System.out.println(message);
        }
    }
    
}
