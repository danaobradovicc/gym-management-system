package server.so.programtreninga;

import common.domain.DomenskiObjekat;
import common.domain.ProgramTreninga;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOVratiListuSviProgramTreninga extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema provera operacija ne prima poslovni kriterijum.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        result = Broker.getInstance().selectAll(new ProgramTreninga(), konekcija);
    }
}
