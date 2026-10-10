package server.so.clanarina;

import common.domain.Clanarina;
import common.domain.DomenskiObjekat;
import common.domain.TerminTreninga;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SOPromeniClanarina extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        Clanarina clanarina = (Clanarina) argument;
        if (clanarina.getDatumPocetka() == null) {
            throw new SystemOperationException("Datum pocetka clanarine je obavezan.");
        }
        if (clanarina.getDatumZavrsetka() == null) {
            throw new SystemOperationException("Datum zavrsetka clanarine je obavezan.");
        }
        if (clanarina.getCena() == null || clanarina.getCena() <= 0) {
            throw new SystemOperationException("Cena clanarine mora biti veca od 0.");
        }
        if (clanarina.getStatus() == null || clanarina.getStatus().trim().isEmpty()) {
            throw new SystemOperationException("Status clanarine je obavezan.");
        }
        if (clanarina.getTrener() == null || clanarina.getTrener().getId() == null) {
            throw new SystemOperationException("Trener je obavezan za clanarinu.");
        }
        if (clanarina.getClan() == null || clanarina.getClan().getId() == null) {
            throw new SystemOperationException("Clan je obavezan za clanarinu.");
        }
        validateTermine(clanarina.getTerminiTreninga());
    }

    private void validateTermine(List<TerminTreninga> termini) throws SystemOperationException {
        if (termini == null) {
            return;
        }
        for (TerminTreninga termin : termini) {
            if (termin.getSala() == null || termin.getSala().trim().isEmpty()) {
                throw new SystemOperationException("Sala termina treninga je obavezna.");
            }
            if (termin.getDatumVremePocetka() == null) {
                throw new SystemOperationException("Vreme pocetka termina treninga je obavezno.");
            }
            if (termin.getDatumVremeZavrsetka() == null) {
                throw new SystemOperationException("Vreme zavrsetka termina treninga je obavezno.");
            }
            if (!termin.getDatumVremeZavrsetka().after(termin.getDatumVremePocetka())) {
                throw new SystemOperationException("Vreme zavrsetka termina treninga mora biti posle vremena pocetka.");
            }
            if (termin.getMaxBrojUcesnika() == null || termin.getMaxBrojUcesnika() <= 0) {
                throw new SystemOperationException("Max broj ucesnika termina treninga mora biti veci od 0.");
            }
            if (termin.getTipTreninga() == null || termin.getTipTreninga().getId() == null) {
                throw new SystemOperationException("Tip treninga je obavezan za termin treninga.");
            }
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        Clanarina clanarina = (Clanarina) argument;
        boolean nova = clanarina.getId() == null;
        try {
            if (nova) {
                Broker.getInstance().insert(clanarina, konekcija);
            } else {
                Broker.getInstance().update(clanarina, konekcija);
            }
            sinhronizujTermine(clanarina, nova);
        } catch (SQLException e) {
            throw new SystemOperationException("Sistem ne moze da zapamti clanarinu.");
        }
        result = clanarina;
    }


    private void sinhronizujTermine(Clanarina clanarina, boolean nova) throws SQLException {
        List<TerminTreninga> novaLista = clanarina.getTerminiTreninga();
        if (novaLista == null) {
            novaLista = new ArrayList<TerminTreninga>();
            clanarina.setTerminiTreninga(novaLista);
        }


        Map<Long, TerminTreninga> staraMapa = new HashMap<Long, TerminTreninga>();
        if (!nova) {
            TerminTreninga kriterijum = new TerminTreninga();
            kriterijum.setClanarina(clanarina);
            for (DomenskiObjekat d : Broker.getInstance().filter(kriterijum, konekcija)) {
                TerminTreninga stariTermin = (TerminTreninga) d;
                staraMapa.put(stariTermin.getId(), stariTermin);
            }
        }


        for (TerminTreninga termin : novaLista) {
            termin.setClanarina(clanarina);
            if (termin.getId() == null || !staraMapa.containsKey(termin.getId())) {
                termin.setId(null);
                Broker.getInstance().insert(termin, konekcija);
            } else {
                Broker.getInstance().update(termin, konekcija);
                staraMapa.remove(termin.getId());
            }
        }


        for (TerminTreninga zaBrisanje : staraMapa.values()) {
            Broker.getInstance().delete(zaBrisanje, konekcija);
        }
    }
}
