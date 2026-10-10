package server.so.clanarina;

import common.domain.Clanarina;
import common.domain.DomenskiObjekat;
import server.so.AbstractSystemOperation;


public class SOKreirajClanarina extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema poslovnih provera - ne dira se baza, argument se ni ne koristi.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        result = new Clanarina();
    }
}
