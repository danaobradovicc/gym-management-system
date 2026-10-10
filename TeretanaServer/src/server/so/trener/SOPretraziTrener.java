package server.so.trener;

import common.domain.DomenskiObjekat;
import common.domain.Trener;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOPretraziTrener extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        Trener trener = (Trener) argument;
        if (trener.getId() == null) {
            throw new SystemOperationException("Id trenera je obavezan za pretragu.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        Trener trener = (Trener) argument;
        Trener pronadjen = (Trener) Broker.getInstance().selectById(new Trener(), trener.getId(), konekcija);
        if (pronadjen == null) {
            throw new SystemOperationException("Sistem ne moze da nadje trenera.");
        }
        result = pronadjen;
    }
}
