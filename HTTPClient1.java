import java.io.*;
import java.net.*;

public class HTTPClient1 {

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; //o altro indirizzo pubblico
        int serverPort = 80; // o porta 80 se mi connetto a server reali pubblici

        try {
            // Creo una connessione TCP al server
            Socket socket = new Socket(serverAddress, serverPort);

            // Ottengo il flusso di output per scrivere la richiesta al server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Scrivo la richiesta HTTP GET
            
            out.println("GET / HTTP/1.1");
            out.println("Host: " + serverAddress);
            out.println("Connection: close"); // chiuda la connessione
            out.println(); // Fine dell'intestazione (riga vuota)
            out.flush(); // svuoto il buffer di uscita

            // Ottengo il flusso di input per leggere la risposta dal server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            

            // Leggo e stampo la risposta dal server
            System.out.println("Risposta dal server:");
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            socket.close();// Chiudo la connessione con il server
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
