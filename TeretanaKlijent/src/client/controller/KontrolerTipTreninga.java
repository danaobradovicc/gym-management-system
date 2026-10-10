package client.controller;

import common.communication.Operation;
import common.communication.Request;
import common.communication.Response;
import common.domain.DomenskiObjekat;
import common.domain.TipTreninga;

import java.util.List;


public class KontrolerTipTreninga {

    public TipTreninga kreiraj() throws Exception {
        return (TipTreninga) posaljiIVratiRezultat(Operation.KREIRAJ_TIP_TRENINGA, new TipTreninga());
    }

    public TipTreninga promeni(TipTreninga tip) throws Exception {
        return (TipTreninga) posaljiIVratiRezultat(Operation.PROMENI_TIP_TRENINGA, tip);
    }

    public TipTreninga pretrazi(TipTreninga kriterijum) throws Exception {
        return (TipTreninga) posaljiIVratiRezultat(Operation.PRETRAZI_TIP_TRENINGA, kriterijum);
    }

    public void obrisi(TipTreninga tip) throws Exception {
        posaljiIVratiRezultat(Operation.OBRISI_TIP_TRENINGA, tip);
    }

    @SuppressWarnings("unchecked")
    public List<TipTreninga> vratiListu(TipTreninga kriterijum) throws Exception {
        return (List<TipTreninga>) posaljiIVratiRezultat(Operation.VRATI_LISTU_TIP_TRENINGA_PO_KRITERIJUMU_TIP_TRENINGA, kriterijum);
    }

    @SuppressWarnings("unchecked")
    public List<TipTreninga> vratiListuSvih() throws Exception {
        return (List<TipTreninga>) posaljiIVratiRezultat(Operation.VRATI_LISTU_SVI_TIP_TRENINGA, new TipTreninga());
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
