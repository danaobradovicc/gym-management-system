package server.so.kategorijaclanarine;

import common.domain.DomenskiObjekat;
import common.domain.KategorijaClanarine;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.sql.SQLException;


public class SOPromeniKategorijaClanarine extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        KategorijaClanarine kategorija = (KategorijaClanarine) argument;
        if (kategorija.getNaziv() == null || kategorija.getNaziv().trim().isEmpty()) {
            throw new SystemOperationException("Naziv kategorije clanarine je obavezan.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        KategorijaClanarine kategorija = (KategorijaClanarine) argument;
        try {
            if (kategorija.getId() == null) {
                Broker.getInstance().insert(kategorija, konekcija);
            } else {
                Broker.getInstance().update(kategorija, konekcija);
            }
        } catch (SQLException e) {
            throw new SystemOperationException("Sistem ne moze da zapamti kategoriju clanarine.");
        }
        result = kategorija;
    }
}
