package server.so.clan;

import common.domain.Clan;
import common.domain.DomenskiObjekat;
import server.so.AbstractSystemOperation;


public class SOKreirajClan extends AbstractSystemOperation {
    //priprema prazan objekat clana za unos

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema poslovnih provera ne dira se baza, argument se ni ne koristi.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        result = new Clan();
    }
}
