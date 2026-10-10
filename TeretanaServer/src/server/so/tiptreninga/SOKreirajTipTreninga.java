package server.so.tiptreninga;

import common.domain.DomenskiObjekat;
import common.domain.TipTreninga;
import server.so.AbstractSystemOperation;


public class SOKreirajTipTreninga extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema poslovnih provera ne dira se baza, argument se ni ne koristi.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        result = new TipTreninga();
    }
}
