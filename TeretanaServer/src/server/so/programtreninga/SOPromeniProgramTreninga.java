package server.so.programtreninga;

import common.domain.DomenskiObjekat;
import common.domain.ProgramTreninga;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.sql.SQLException;


public class SOPromeniProgramTreninga extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        ProgramTreninga program = (ProgramTreninga) argument;
        if (program.getNaziv() == null || program.getNaziv().trim().isEmpty()) {
            throw new SystemOperationException("Naziv programa treninga je obavezan.");
        }
        if (program.getOpis() == null || program.getOpis().trim().isEmpty()) {
            throw new SystemOperationException("Opis programa treninga je obavezan.");
        }
        if (program.getBrojNedelja() == null || program.getBrojNedelja() <= 0) {
            throw new SystemOperationException("Broj nedelja programa treninga mora biti veci od 0.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        ProgramTreninga program = (ProgramTreninga) argument;
        try {
            if (program.getId() == null) {
                Broker.getInstance().insert(program, konekcija);
            } else {
                Broker.getInstance().update(program, konekcija);
            }
        } catch (SQLException e) {
            throw new SystemOperationException("Sistem ne moze da zapamti program treninga.");
        }
        result = program;
    }
}
