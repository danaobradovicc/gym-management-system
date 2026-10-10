package server.so.trener;

import common.domain.DomenskiObjekat;
import common.domain.Trener;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.sql.SQLException;


public class SOPromeniTrener extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        Trener trener = (Trener) argument;
        if (trener.getIme() == null || trener.getIme().trim().isEmpty()) {
            throw new SystemOperationException("Ime trenera je obavezno.");
        }
        if (trener.getPrezime() == null || trener.getPrezime().trim().isEmpty()) {
            throw new SystemOperationException("Prezime trenera je obavezno.");
        }
        if (trener.getKorisnickoIme() == null || trener.getKorisnickoIme().trim().isEmpty()) {
            throw new SystemOperationException("Korisnicko ime trenera je obavezno.");
        }
        if (trener.getSifra() == null || trener.getSifra().trim().isEmpty()) {
            throw new SystemOperationException("Sifra trenera je obavezna.");
        }
        if (trener.getDatumZaposlenja() == null) {
            throw new SystemOperationException("Datum zaposlenja trenera je obavezan.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        Trener trener = (Trener) argument;
        try {
            if (trener.getId() == null) {
                Broker.getInstance().insert(trener, konekcija);
            } else {
                Broker.getInstance().update(trener, konekcija);
            }
        } catch (SQLException e) {
            throw new SystemOperationException("Sistem ne moze da zapamti trenera.");
        }
        result = trener;
    }
}
