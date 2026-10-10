package server.net;

import common.communication.Operation;
import common.communication.Receiver;
import common.communication.Request;
import common.communication.Response;
import common.communication.Sender;
import common.domain.Trener;
import common.exception.SystemOperationException;
import server.controller.ServerController;
import server.coordinator.ServerCoordinator;

import java.io.IOException;
import java.net.Socket;


public class ClientSessionHandler implements Runnable {

    private final Socket socket;
    private Sender posiljalac;
    private Receiver primalac;
    private Trener prijavljeniTrener;

    public ClientSessionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            
            posiljalac = new Sender(socket);
            primalac = new Receiver(socket);

            ServerCoordinator.getInstance().dodajSesiju(this);

            while (true) {
                Object primljeno = primalac.receive();
                if (!(primljeno instanceof Request)) {
                    continue;
                }
                Request zahtev = (Request) primljeno;
                Response odgovor = obradiZahtev(zahtev);
                posiljalac.send(odgovor);
            }
        } catch (IOException | ClassNotFoundException e) {
            
        } finally {
            ServerCoordinator.getInstance().ukloniSesiju(this);
            zatvoriTiho();
        }
    }

    private Response obradiZahtev(Request zahtev) {
        try {
            if (zahtev.getOperation() != Operation.PRIJAVI_TRENER && prijavljeniTrener == null) {
                throw new SystemOperationException("Niste prijavljeni.");
            }

            Object rezultat = ServerController.getInstance().izvrsiOperaciju(zahtev.getOperation(), zahtev.getArgument());

            if (zahtev.getOperation() == Operation.PRIJAVI_TRENER && rezultat instanceof Trener) {
                prijavljeniTrener = (Trener) rezultat;
            }

            return new Response(rezultat, null);
        } catch (Exception e) {
            return new Response(null, e);
        }
    }

    private void zatvoriTiho() {
        try {
            if (posiljalac != null) {
                posiljalac.close();
            }
        } catch (IOException ignored) {
        }
        try {
            if (primalac != null) {
                primalac.close();
            }
        } catch (IOException ignored) {
        }
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
