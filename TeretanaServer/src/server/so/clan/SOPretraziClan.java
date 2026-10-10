package server.so.clan;

import common.domain.Clan;
import common.domain.DomenskiObjekat;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOPretraziClan extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        Clan clan = (Clan) argument;
        if (clan.getId() == null) {
            throw new SystemOperationException("Id clana je obavezan za pretragu.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        Clan clan = (Clan) argument;
        Clan pronadjen = (Clan) Broker.getInstance().selectById(new Clan(), clan.getId(), konekcija);
        if (pronadjen == null) {
            throw new SystemOperationException("Sistem ne moze da nadje clana.");
        }
        result = pronadjen;
    }
}
