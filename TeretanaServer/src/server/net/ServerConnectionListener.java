package server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerConnectionListener implements Runnable {

    private final int port;

    public ServerConnectionListener(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSoket = new ServerSocket(port)) { //automatski se zatvara na kraju try bloka, bez close
            System.out.println("Server slusa na portu " + port + "...");
            while (true) {
                Socket soket = serverSoket.accept();
                ClientSessionHandler handler = new ClientSessionHandler(soket);
                Thread nit = new Thread(handler);
                nit.start();
            }
        } catch (IOException e) {
            System.out.println("Greska u ServerConnectionListener-u: " + e.getMessage());
            //obuhvata i while true petlju
        }
    }
}
