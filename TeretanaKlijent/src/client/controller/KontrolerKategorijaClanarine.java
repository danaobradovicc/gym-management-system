package client.controller;

import common.communication.Operation;
import common.communication.Request;
import common.communication.Response;
import common.domain.DomenskiObjekat;
import common.domain.KategorijaClanarine;

import java.util.List;


public class KontrolerKategorijaClanarine {

    public KategorijaClanarine kreiraj() throws Exception {
        return (KategorijaClanarine) posaljiIVratiRezultat(Operation.KREIRAJ_KATEGORIJA_CLANARINE, new KategorijaClanarine());
    }

    public KategorijaClanarine promeni(KategorijaClanarine kategorija) throws Exception {
        return (KategorijaClanarine) posaljiIVratiRezultat(Operation.PROMENI_KATEGORIJA_CLANARINE, kategorija);
    }

    public KategorijaClanarine pretrazi(KategorijaClanarine kriterijum) throws Exception {
        return (KategorijaClanarine) posaljiIVratiRezultat(Operation.PRETRAZI_KATEGORIJA_CLANARINE, kriterijum);
    }

    public void obrisi(KategorijaClanarine kategorija) throws Exception {
        posaljiIVratiRezultat(Operation.OBRISI_KATEGORIJA_CLANARINE, kategorija);
    }

    @SuppressWarnings("unchecked")
    public List<KategorijaClanarine> vratiListu(KategorijaClanarine kriterijum) throws Exception {
        return (List<KategorijaClanarine>) posaljiIVratiRezultat(
                Operation.VRATI_LISTU_KATEGORIJA_CLANARINE_PO_KRITERIJUMU_KATEGORIJA_CLANARINE, kriterijum);
    }

    @SuppressWarnings("unchecked")
    public List<KategorijaClanarine> vratiListuSvih() throws Exception {
        return (List<KategorijaClanarine>) posaljiIVratiRezultat(
                Operation.VRATI_LISTU_SVI_KATEGORIJA_CLANARINE, new KategorijaClanarine());
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
