package server.so.trener;

import common.domain.DomenskiObjekat;
import common.domain.Trener;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOVratiListuSviTrener extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema provera operacija ne prima poslovni kriterijum.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        result = Broker.getInstance().selectAll(new Trener(), konekcija);
    }
}
