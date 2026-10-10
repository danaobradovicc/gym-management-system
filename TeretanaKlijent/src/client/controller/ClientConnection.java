package client.controller;

import common.communication.Receiver;
import common.communication.Request;
import common.communication.Response;
import common.communication.Sender;

import java.io.IOException;
import java.net.Socket;


public class ClientConnection {

    private static ClientConnection instance;

    private Socket soket;
    private Sender posiljalac;
    private Receiver primalac;

    private ClientConnection() {
    }

    public static synchronized ClientConnection getInstance() {
        if (instance == null) {
            instance = new ClientConnection();
        }
        return instance;
    }

    public synchronized void povezi(String host, int port) throws IOException {
        soket = new Socket(host, port);
        
        posiljalac = new Sender(soket);
        primalac = new Receiver(soket);
    }

    public synchronized Response posaljiZahtev(Request zahtev) throws IOException, ClassNotFoundException {
        posiljalac.send(zahtev);
        return (Response) primalac.receive();
    }

    public synchronized boolean jePovezan() {
        return soket != null && soket.isConnected() && !soket.isClosed();
    }

    public synchronized void prekiniVezu() {
        try {
            if (posiljalac != null) {
                posiljalac.close();
            }//zatvara sender
        } catch (IOException ignored) {
        }
        try {
            if (primalac != null) {
                primalac.close();
            }//zatvara receiver
        } catch (IOException ignored) {
        }
        try {
            if (soket != null) {
                soket.close();
            }//zatvara socket
        } catch (IOException ignored) {
        }
    }
}
