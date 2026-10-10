package server.so.tiptreninga;

import common.domain.DomenskiObjekat;
import common.domain.TipTreninga;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.sql.SQLException;

/**
 * Tabela 5 red 24: PromeniTipTreninga(TipTreninga). Insert-ili-update po
 * id-u. SK14/SK16 nemaju detaljan opis - poruke sastavljene po obrascu
 * SK1/SK3/SK5/SK7.
 */
public class SOPromeniTipTreninga extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        TipTreninga tip = (TipTreninga) argument;
        if (tip.getNaziv() == null || tip.getNaziv().trim().isEmpty()) {
            throw new SystemOperationException("Naziv tipa treninga je obavezan.");
        }
        if (tip.getNivoTezine() == null || tip.getNivoTezine().trim().isEmpty()) {
            throw new SystemOperationException("Nivo tezine tipa treninga je obavezan.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        TipTreninga tip = (TipTreninga) argument;
        try {
            if (tip.getId() == null) {
                Broker.getInstance().insert(tip, konekcija);
            } else {
                Broker.getInstance().update(tip, konekcija);
            }
        } catch (SQLException e) {
            throw new SystemOperationException("Sistem ne moze da zapamti tip treninga.");
        }
        result = tip;
    }
}
