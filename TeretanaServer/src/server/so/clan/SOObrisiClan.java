package server.so.clan;

import common.domain.Clan;
import common.domain.DomenskiObjekat;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.sql.SQLException;


public class SOObrisiClan extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        Clan clan = (Clan) argument;
        if (clan.getId() == null) {
            throw new SystemOperationException("Id clana je obavezan za brisanje.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        Clan clan = (Clan) argument;
        try {
            Broker.getInstance().delete(clan, konekcija);
        } catch (SQLException e) {
            throw new SystemOperationException("Sistem ne moze da obrise clana.");
        }
        result = null;
    }
}
