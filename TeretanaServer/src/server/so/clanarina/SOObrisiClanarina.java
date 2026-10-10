package server.so.clanarina;

import common.domain.Clanarina;
import common.domain.DomenskiObjekat;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.sql.SQLException;


public class SOObrisiClanarina extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        Clanarina clanarina = (Clanarina) argument;
        if (clanarina.getId() == null) {
            throw new SystemOperationException("Id clanarine je obavezan za brisanje.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        Clanarina clanarina = (Clanarina) argument;
        try {
            Broker.getInstance().delete(clanarina, konekcija);
        } catch (SQLException e) {
            throw new SystemOperationException("Sistem ne moze da obrise clanarinu.");
        }
        result = null;
    }
}
