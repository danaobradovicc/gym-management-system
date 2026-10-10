package server.so.clanarina;

import common.domain.Clanarina;
import common.domain.DomenskiObjekat;
import common.domain.Trener;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOVratiListuClanarinaPoKriterijumuTrener extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema obaveznih polja - kriterijum moze biti delimicno popunjen.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        Trener kriterijumTrener = (Trener) argument;
        Clanarina clanarinaKriterijum = new Clanarina();
        clanarinaKriterijum.setTrener(kriterijumTrener);
        result = Broker.getInstance().filter(clanarinaKriterijum, konekcija);
    }
}
