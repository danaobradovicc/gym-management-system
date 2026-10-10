package server.so.tiptreninga;

import common.domain.DomenskiObjekat;
import common.domain.TipTreninga;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.sql.SQLException;

/**
 * Tabela 5 red 11: ObrisiTipTreninga(TipTreninga). TipTreninga je ON DELETE
 * RESTRICT referenciran iz TerminTreninga (sql/schema.sql) - FK povreda se
 * prevodi u poruku (obrazac SK8/SK17).
 */
public class SOObrisiTipTreninga extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        TipTreninga tip = (TipTreninga) argument;
        if (tip.getId() == null) {
            throw new SystemOperationException("Id tipa treninga je obavezan za brisanje.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        TipTreninga tip = (TipTreninga) argument;
        try {
            Broker.getInstance().delete(tip, konekcija);
        } catch (SQLException e) {
            throw new SystemOperationException("Sistem ne moze da obrise tip treninga.");
        }
        result = null;
    }
}
