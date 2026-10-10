package server.so.clanarina;

import common.domain.Clan;
import common.domain.Clanarina;
import common.domain.DomenskiObjekat;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOVratiListuClanarinaPoKriterijumuClan extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema obaveznih polja - kriterijum moze biti delimicno popunjen.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        Clan kriterijumClan = (Clan) argument;
        Clanarina clanarinaKriterijum = new Clanarina();
        clanarinaKriterijum.setClan(kriterijumClan);
        result = Broker.getInstance().filter(clanarinaKriterijum, konekcija);
    }
}
