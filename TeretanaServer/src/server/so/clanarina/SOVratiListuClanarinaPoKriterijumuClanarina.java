package server.so.clanarina;

import common.domain.DomenskiObjekat;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOVratiListuClanarinaPoKriterijumuClanarina extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema obaveznih polja - kriterijum moze biti delimicno popunjen.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        result = Broker.getInstance().filter(argument, konekcija);
    }
}
