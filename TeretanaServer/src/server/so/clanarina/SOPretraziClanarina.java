package server.so.clanarina;

import common.domain.Clanarina;
import common.domain.DomenskiObjekat;
import common.domain.TerminTreninga;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.util.ArrayList;
import java.util.List;


public class SOPretraziClanarina extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        Clanarina clanarina = (Clanarina) argument;
        if (clanarina.getId() == null) {
            throw new SystemOperationException("Id clanarine je obavezan za pretragu.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        Clanarina clanarina = (Clanarina) argument;
        Clanarina pronadjena = (Clanarina) Broker.getInstance().selectById(new Clanarina(), clanarina.getId(), konekcija);
        if (pronadjena == null) {
            throw new SystemOperationException("Sistem ne moze da nadje clanarinu.");
        }

        TerminTreninga kriterijum = new TerminTreninga();
        kriterijum.setClanarina(pronadjena);
        List<TerminTreninga> termini = new ArrayList<TerminTreninga>();
        for (DomenskiObjekat d : Broker.getInstance().filter(kriterijum, konekcija)) {
            termini.add((TerminTreninga) d);
        }
        pronadjena.setTerminiTreninga(termini);

        result = pronadjena;
    }
}
