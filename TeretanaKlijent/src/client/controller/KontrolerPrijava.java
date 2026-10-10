package client.controller;

import client.session.Sesija;
import common.communication.Operation;
import common.communication.Request;
import common.communication.Response;
import common.domain.Trener;


public class KontrolerPrijava {

    public Trener prijaviSe(String host, int port, String korisnickoIme, String sifra) throws Exception {
        ClientConnection.getInstance().povezi(host, port);//povezi klijenta sa serverom

        Trener kredencijali = new Trener();
        kredencijali.setKorisnickoIme(korisnickoIme);
        kredencijali.setSifra(sifra);

        Response odgovor;
        try {
            odgovor = ClientConnection.getInstance().posaljiZahtev(new Request(Operation.PRIJAVI_TRENER, kredencijali));
        } catch (Exception e) {
            ClientConnection.getInstance().prekiniVezu();
            throw e;
        }

        if (odgovor.getException() != null) {
            ClientConnection.getInstance().prekiniVezu();
            throw odgovor.getException();
        }
        //npr pogresno korisnicko ime ili sifra

        Trener prijavljeniTrener = (Trener) odgovor.getResult();
        Sesija.getInstance().setPrijavljeniTrener(prijavljeniTrener);
        return prijavljeniTrener;
    }
}
