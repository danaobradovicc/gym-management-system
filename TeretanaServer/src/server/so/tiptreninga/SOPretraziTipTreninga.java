package server.so.tiptreninga;

import common.domain.DomenskiObjekat;
import common.domain.TipTreninga;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOPretraziTipTreninga extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        TipTreninga tip = (TipTreninga) argument;
        if (tip.getId() == null) {
            throw new SystemOperationException("Id tipa treninga je obavezan za pretragu.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        TipTreninga tip = (TipTreninga) argument;
        TipTreninga pronadjen = (TipTreninga) Broker.getInstance().selectById(new TipTreninga(), tip.getId(), konekcija);
        if (pronadjen == null) {
            throw new SystemOperationException("Sistem ne moze da nadje tip treninga.");
        }
        result = pronadjen;
    }
}
