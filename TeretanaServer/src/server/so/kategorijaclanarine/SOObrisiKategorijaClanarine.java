package server.so.kategorijaclanarine;

import common.domain.DomenskiObjekat;
import common.domain.KategorijaClanarine;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.sql.SQLException;


public class SOObrisiKategorijaClanarine extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        KategorijaClanarine kategorija = (KategorijaClanarine) argument;
        if (kategorija.getId() == null) {
            throw new SystemOperationException("Id kategorije clanarine je obavezan za brisanje.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        KategorijaClanarine kategorija = (KategorijaClanarine) argument;
        try {
            Broker.getInstance().delete(kategorija, konekcija);
        } catch (SQLException e) {
            throw new SystemOperationException("Sistem ne moze da obrise kategoriju clanarine.");
        }
        result = null;
    }
}
