package server.so.programtreninga;

import common.domain.DomenskiObjekat;
import common.domain.ProgramTreninga;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.sql.SQLException;


public class SOObrisiProgramTreninga extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        ProgramTreninga program = (ProgramTreninga) argument;
        if (program.getId() == null) {
            throw new SystemOperationException("Id programa treninga je obavezan za brisanje.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        ProgramTreninga program = (ProgramTreninga) argument;
        try {
            Broker.getInstance().delete(program, konekcija);
        } catch (SQLException e) {
            throw new SystemOperationException("Sistem ne moze da obrise program treninga.");
        }
        result = null;
    }
}
