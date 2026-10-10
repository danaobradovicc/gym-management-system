package client.controller;

import common.communication.Operation;
import common.communication.Request;
import common.communication.Response;
import common.domain.Clan;
import common.domain.Clanarina;
import common.domain.DomenskiObjekat;
import common.domain.Trener;

import java.util.List;


public class KontrolerClanarina {

    public Clanarina kreiraj() throws Exception {
        return (Clanarina) posaljiIVratiRezultat(Operation.KREIRAJ_CLANARINA, new Clanarina());
    }

    public Clanarina promeni(Clanarina clanarina) throws Exception {
        return (Clanarina) posaljiIVratiRezultat(Operation.PROMENI_CLANARINA, clanarina);
    }
 //salje popunjenu clanarinu

    public Clanarina pretrazi(Clanarina kriterijum) throws Exception {
        return (Clanarina) posaljiIVratiRezultat(Operation.PRETRAZI_CLANARINA, kriterijum);
    }

    public void obrisi(Clanarina clanarina) throws Exception {
        posaljiIVratiRezultat(Operation.OBRISI_CLANARINA, clanarina);
    }
    //brisanje samo uspeh neuspeh

    @SuppressWarnings("unchecked")
    public List<Clanarina> vratiListu(Clanarina kriterijum) throws Exception {
        return (List<Clanarina>) posaljiIVratiRezultat(Operation.VRATI_LISTU_CLANARINA_PO_KRITERIJUMU_CLANARINA, kriterijum);
    }
//pretraga po kriterijumu same clanarine npr status
    @SuppressWarnings("unchecked")
    public List<Clanarina> vratiListuPoClanu(Clan kriterijum) throws Exception {
        return (List<Clanarina>) posaljiIVratiRezultat(Operation.VRATI_LISTU_CLANARINA_PO_KRITERIJUMU_CLAN, kriterijum);
    }
    //sve clanarine jednog clana

    @SuppressWarnings("unchecked")
    public List<Clanarina> vratiListuPoTreneru(Trener kriterijum) throws Exception {
        return (List<Clanarina>) posaljiIVratiRezultat(Operation.VRATI_LISTU_CLANARINA_PO_KRITERIJUMU_TRENER, kriterijum);
    }
    //sve clanarine jednog trenera

    private Object posaljiIVratiRezultat(Operation operacija, DomenskiObjekat argument) throws Exception {
        Request zahtev = new Request(operacija, argument);
        Response odgovor = ClientConnection.getInstance().posaljiZahtev(zahtev);
        if (odgovor.getException() != null) {
            throw odgovor.getException();
        }
        return odgovor.getResult();
    }
}
