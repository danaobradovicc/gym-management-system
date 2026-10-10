package server.so.clan;

import common.domain.Clan;
import common.domain.DomenskiObjekat;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOVratiListuSviClan extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        // Nema provera - operacija ne prima poslovni kriterijum.
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        result = Broker.getInstance().selectAll(new Clan(), konekcija);
    }
}
