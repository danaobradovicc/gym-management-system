package server.so.kategorijaclanarine;

import common.domain.DomenskiObjekat;
import common.domain.KategorijaClanarine;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOPretraziKategorijaClanarine extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        KategorijaClanarine kategorija = (KategorijaClanarine) argument;
        if (kategorija.getId() == null) {
            throw new SystemOperationException("Id kategorije clanarine je obavezan za pretragu.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        KategorijaClanarine kategorija = (KategorijaClanarine) argument;
        KategorijaClanarine pronadjena = (KategorijaClanarine) Broker.getInstance()
                .selectById(new KategorijaClanarine(), kategorija.getId(), konekcija);
        if (pronadjena == null) {
            throw new SystemOperationException("Sistem ne moze da nadje kategoriju clanarine.");
        }
        result = pronadjena;
    }
}
