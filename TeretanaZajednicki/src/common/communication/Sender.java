package common.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Sender {

    private final ObjectOutputStream izlazniTok;

    public Sender(Socket socket) throws IOException {
        this.izlazniTok = new ObjectOutputStream(socket.getOutputStream());
        this.izlazniTok.flush();
    }

    
    public void send(Object object) throws IOException {
        izlazniTok.writeObject(object);
        izlazniTok.flush();
        izlazniTok.reset();
    }

    public void close() throws IOException {
        izlazniTok.close();
    }
}
