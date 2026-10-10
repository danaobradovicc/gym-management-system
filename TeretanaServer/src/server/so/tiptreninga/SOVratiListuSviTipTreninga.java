package server.so.tiptreninga;

import common.domain.DomenskiObjekat;
import common.domain.TipTreninga;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOVratiListuSviTipTreninga extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema provera - operacija ne prima poslovni kriterijum.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        result = Broker.getInstance().selectAll(new TipTreninga(), konekcija);
    }
}
