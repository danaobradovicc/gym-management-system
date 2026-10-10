package common.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;


public class Receiver {

    private final ObjectInputStream ulazniTok;

    public Receiver(Socket socket) throws IOException {
        this.ulazniTok = new ObjectInputStream(socket.getInputStream());
    }

    public Object receive() throws IOException, ClassNotFoundException {
        return ulazniTok.readObject();
    }

    public void close() throws IOException {
        ulazniTok.close();
    }
}
