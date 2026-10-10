package server.coordinator;

import server.net.ClientSessionHandler;

import java.util.ArrayList;
import java.util.List;


public class ServerCoordinator {

    private static ServerCoordinator instance;

    private final List<ClientSessionHandler> aktivneSesije = new ArrayList<ClientSessionHandler>();
//deljeni resurs
    private ServerCoordinator() {
    }

    public static synchronized ServerCoordinator getInstance() {
        if (instance == null) {
            instance = new ServerCoordinator();
        }
        return instance;
    }

    public synchronized void dodajSesiju(ClientSessionHandler sesija) {
        aktivneSesije.add(sesija); //klijent se poveze sa serverom
    }

    public synchronized void ukloniSesiju(ClientSessionHandler sesija) {
        aktivneSesije.remove(sesija);
    }

    public synchronized int brojAktivnihSesija() {
        return aktivneSesije.size();
    }

    public synchronized boolean sadrziSesiju(ClientSessionHandler sesija) {
        return aktivneSesije.contains(sesija);
    }
    
    //race condition
    //svaka mora biti synhronized
}
