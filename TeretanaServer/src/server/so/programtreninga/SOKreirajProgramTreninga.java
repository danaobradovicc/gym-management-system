package server.so.programtreninga;

import common.domain.DomenskiObjekat;
import common.domain.ProgramTreninga;
import server.so.AbstractSystemOperation;


public class SOKreirajProgramTreninga extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema poslovnih provera - ne dira se baza, argument se ni ne koristi.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        result = new ProgramTreninga();
    }
}
