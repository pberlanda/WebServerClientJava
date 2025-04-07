import java.io.*;
import java.net.*;

public class Server1 {

    public static void main(String[] args) {
        int port = 80;
        String line, richiesta;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);  // costruisce il socket di ascolto (listener)
            System.out.println("Server avviato sulla porta " + port + "...");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();  // Costruisce un socket per gestire il Client che ha chiesto la connessione
                System.out.println("Connessione accettata da " + clientSocket.getInetAddress()  + " porta: " + clientSocket.getPort());
                System.out.println();

                // Lettura della richiesta
                System.out.println("********** Inizio richiesta arrivata dal Client ***********");
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                richiesta="";
                while ((line = in.readLine()) != null && !line.isEmpty()) {  // Legge la richiesta fino alla riga VUOTA di fine HEADER
                  System.out.println(line);
                  richiesta=richiesta+line + "<BR>";
                }
                System.out.println("********** Fine richiesta arrivata dal Client ***********");
                System.out.println();

                // Apro il flusso di output (con autoflush!) per scrivere la risposta al client
                OutputStream outputStream = clientSocket.getOutputStream();
                PrintWriter dati_OUT = new PrintWriter(outputStream, true);

                 // Controllo se il metodo HTTP è GET
                 if (!richiesta.startsWith("GET")) {
                    dati_OUT.println("HTTP/1.1 400 Bad Request");
                    dati_OUT.println("Content-Type: text/html; charset=UTF-8");
                    dati_OUT.println("Connection: close");
                    dati_OUT.println();
                    dati_OUT.println("<html><body><h1>400 - Bad Request</h1></body></html>");
                    dati_OUT.flush();
                    clientSocket.close();
                    continue;
                  }

                // Invio la risposta HTTP al client
                dati_OUT.println("HTTP/1.1 200 OK");
                dati_OUT.println("Content-Type: text/html; charset=UTF-8");
                dati_OUT.println("Connection: close");
                dati_OUT.println();
                dati_OUT.println("<html><head><title>Server JAVA</title></head>");
                dati_OUT.println("<body style='background-color:blue; font-family:arial;'>");
                dati_OUT.println("<h1 style='color: orange; font-size:300%;'> Benvenuto sul server HTTP in JAVA<BR></h1>");
                dati_OUT.println("<h1 style='color: yellow; font-size:200%;'>Indirizzo IP Client: "+ clientSocket.getInetAddress().getHostAddress() + "<BR>");
                dati_OUT.println("Porta del Client: "+ clientSocket.getPort() + "<BR></h1>");
                dati_OUT.println("<BR>");
                dati_OUT.println("<h3 style='color: #33ff33;'>****** questa la RICHIESTA ricevuta *******<BR>");
                dati_OUT.println(richiesta + "</h3><BR>");
                dati_OUT.println("</body></html>");
                dati_OUT.println();
                dati_OUT.flush();

                clientSocket.close();  //chiude la connessione con il client
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
