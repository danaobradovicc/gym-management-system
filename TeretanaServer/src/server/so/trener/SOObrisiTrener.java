package server.so.trener;

import common.domain.DomenskiObjekat;
import common.domain.Trener;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.sql.SQLException;


public class SOObrisiTrener extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        Trener trener = (Trener) argument;
        if (trener.getId() == null) {
            throw new SystemOperationException("Id trenera je obavezan za brisanje.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        Trener trener = (Trener) argument;
        try {
            Broker.getInstance().delete(trener, konekcija);
        } catch (SQLException e) {
            throw new SystemOperationException("Sistem ne moze da obrise trenera.");
        }
        result = null;
    }
}
