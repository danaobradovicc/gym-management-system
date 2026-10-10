package server;

import server.gui.ServerFrame;
import server.net.ServerConnectionListener;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ServerMain {

    private static final String SERVER_PROPERTIES_RESOURCE = "/server/config/server.properties";
    private static final int PODRAZUMEVANI_PORT = 5555;

    public static void main(String[] args) {
        final int port = ucitajPort();

        ServerConnectionListener listener = new ServerConnectionListener(port);
        new Thread(listener).start();
        //deo programa stalno ceka klijente, a ostatak programa moze normalno da nastavi da radi
        //nit 1 ceka klijente
        //nit 2 prikazuje prozor i reaguje na klikove

        SwingUtilities.invokeLater(new Runnable() { //da pravilno reaguje na klikove
            @Override
            public void run() {
                new ServerFrame(port).setVisible(true);
            }
        });
        //otvara server prozor
        //tekst se prikazuje
    }

    private static int ucitajPort() {
        Properties svojstva = new Properties();
        try (InputStream ulazniTok = ServerMain.class.getResourceAsStream(SERVER_PROPERTIES_RESOURCE)) {
            if (ulazniTok == null) {
                return PODRAZUMEVANI_PORT; //'rezervni'
            }
            svojstva.load(ulazniTok);
            return Integer.parseInt(svojstva.getProperty("server.port", String.valueOf(PODRAZUMEVANI_PORT)));
        } catch (IOException e) {
            return PODRAZUMEVANI_PORT; //5555
        }
    }
}
