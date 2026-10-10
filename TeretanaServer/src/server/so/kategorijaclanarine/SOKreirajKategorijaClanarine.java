package server.so.kategorijaclanarine;

import common.domain.DomenskiObjekat;
import common.domain.KategorijaClanarine;
import server.so.AbstractSystemOperation;


public class SOKreirajKategorijaClanarine extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema poslovnih provera - ne dira se baza, argument se ni ne koristi.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        result = new KategorijaClanarine();
    }
}
