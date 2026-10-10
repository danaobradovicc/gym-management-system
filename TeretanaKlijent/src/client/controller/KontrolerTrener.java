package client.controller;

import common.communication.Operation;
import common.communication.Request;
import common.communication.Response;
import common.domain.DomenskiObjekat;
import common.domain.Trener;

import java.util.List;


public class KontrolerTrener {

    public Trener kreiraj() throws Exception {
        return (Trener) posaljiIVratiRezultat(Operation.KREIRAJ_TRENER, new Trener());
    }

    public Trener promeni(Trener trener) throws Exception {
        return (Trener) posaljiIVratiRezultat(Operation.PROMENI_TRENER, trener);
    }

    public Trener pretrazi(Trener kriterijum) throws Exception {
        return (Trener) posaljiIVratiRezultat(Operation.PRETRAZI_TRENER, kriterijum);
    }

    public void obrisi(Trener trener) throws Exception {
        posaljiIVratiRezultat(Operation.OBRISI_TRENER, trener);
    }

    @SuppressWarnings("unchecked")
    public List<Trener> vratiListu(Trener kriterijum) throws Exception {
        return (List<Trener>) posaljiIVratiRezultat(Operation.VRATI_LISTU_TRENER_PO_KRITERIJUMU_TRENER, kriterijum);
    }

    @SuppressWarnings("unchecked")
    public List<Trener> vratiListuSvih() throws Exception {
        return (List<Trener>) posaljiIVratiRezultat(Operation.VRATI_LISTU_SVI_TRENER, new Trener());
    }

    private Object posaljiIVratiRezultat(Operation operacija, DomenskiObjekat argument) throws Exception {
        Request zahtev = new Request(operacija, argument);
        Response odgovor = ClientConnection.getInstance().posaljiZahtev(zahtev);
        if (odgovor.getException() != null) {
            throw odgovor.getException();
        }
        return odgovor.getResult();
    }
}
