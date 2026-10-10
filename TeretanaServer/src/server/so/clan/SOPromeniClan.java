package server.so.clan;

import common.domain.Clan;
import common.domain.DomenskiObjekat;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.sql.SQLException;


public class SOPromeniClan extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        Clan clan = (Clan) argument;
        if (clan.getIme() == null || clan.getIme().trim().isEmpty()) {
            throw new SystemOperationException("Ime clana je obavezno.");
        }
        if (clan.getPrezime() == null || clan.getPrezime().trim().isEmpty()) {
            throw new SystemOperationException("Prezime clana je obavezno.");
        }
        if (clan.getEmail() == null || clan.getEmail().trim().isEmpty()) {
            throw new SystemOperationException("Email clana je obavezan.");
        }
        if (clan.getEmail().length() > 50) {
            throw new SystemOperationException("Email clana ne sme biti duzi od 50 karaktera.");
        }
        if (clan.getDatumUclanjenja() == null) {
            throw new SystemOperationException("Datum uclanjenja clana je obavezan.");
        }
        if (clan.getAktivan() == null) {
            throw new SystemOperationException("Polje 'aktivan' za clana je obavezno.");
        }
        if (clan.getKategorijaClanarine() == null || clan.getKategorijaClanarine().getId() == null) {
            throw new SystemOperationException("Kategorija clanarine je obavezna za clana.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        Clan clan = (Clan) argument;
        try {
            if (clan.getId() == null) {
                Broker.getInstance().insert(clan, konekcija);
            } else {
                Broker.getInstance().update(clan, konekcija);
            }
        } catch (SQLException e) {
            throw new SystemOperationException("Sistem ne moze da zapamti clana.");
        }
        result = clan;
    }
    
    //ako član nema ID dodaje novog člana u bazu (INSERT)
    //ako član ima ID menja postojećeg člana (UPDATE)
}
