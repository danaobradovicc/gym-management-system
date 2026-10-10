package server.so.clan;

import common.domain.Clan;
import common.domain.DomenskiObjekat;
import common.domain.KategorijaClanarine;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOVratiListuClanPoKriterijumuKategorijaClanarine extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema obaveznih polja - kriterijum moze biti delimicno popunjen.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        KategorijaClanarine kriterijumKategorija = (KategorijaClanarine) argument;
        Clan clanKriterijum = new Clan();
        clanKriterijum.setKategorijaClanarine(kriterijumKategorija);
        result = Broker.getInstance().filter(clanKriterijum, konekcija);
    }
}
