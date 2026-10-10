package server.so.kategorijaclanarine;

import common.domain.DomenskiObjekat;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOVratiListuKategorijaClanarine extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema obaveznih polja - kriterijum moze biti delimicno ili u
        // potpunosti nepopunjen.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        result = Broker.getInstance().filter(argument, konekcija);
    }
}
