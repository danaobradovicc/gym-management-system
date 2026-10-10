package client.controller;

import common.communication.Operation;
import common.communication.Request;
import common.communication.Response;
import common.domain.Clan;
import common.domain.DomenskiObjekat;
import common.domain.KategorijaClanarine;

import java.util.List;


public class KontrolerClan {

    public Clan kreiraj() throws Exception {
        return (Clan) posaljiIVratiRezultat(Operation.KREIRAJ_CLAN, new Clan());
    }

    public Clan promeni(Clan clan) throws Exception {
        return (Clan) posaljiIVratiRezultat(Operation.PROMENI_CLAN, clan);
    }

    public Clan pretrazi(Clan kriterijum) throws Exception {
        return (Clan) posaljiIVratiRezultat(Operation.PRETRAZI_CLAN, kriterijum);
    }

    public void obrisi(Clan clan) throws Exception {
        posaljiIVratiRezultat(Operation.OBRISI_CLAN, clan);
    }

    @SuppressWarnings("unchecked")
    public List<Clan> vratiListu(Clan kriterijum) throws Exception {
        return (List<Clan>) posaljiIVratiRezultat(Operation.VRATI_LISTU_CLAN_PO_KRITERIJUMU_CLAN, kriterijum);
    }

    @SuppressWarnings("unchecked")
    public List<Clan> vratiListuPoKategoriji(KategorijaClanarine kriterijum) throws Exception {
        return (List<Clan>) posaljiIVratiRezultat(Operation.VRATI_LISTU_CLAN_PO_KRITERIJUMU_KATEGORIJA_CLANARINE, kriterijum);
    }

    @SuppressWarnings("unchecked")
    public List<Clan> vratiListuSvih() throws Exception {
        return (List<Clan>) posaljiIVratiRezultat(Operation.VRATI_LISTU_SVI_CLAN, new Clan());
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
