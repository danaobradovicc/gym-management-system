package server.so.programtreninga;

import common.domain.DomenskiObjekat;
import common.domain.ProgramTreninga;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;


public class SOPretraziProgramTreninga extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        ProgramTreninga program = (ProgramTreninga) argument;
        if (program.getId() == null) {
            throw new SystemOperationException("Id programa treninga je obavezan za pretragu.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        ProgramTreninga program = (ProgramTreninga) argument;
        ProgramTreninga pronadjen = (ProgramTreninga) Broker.getInstance()
                .selectById(new ProgramTreninga(), program.getId(), konekcija);
        if (pronadjen == null) {
            throw new SystemOperationException("Sistem ne moze da nadje program treninga.");
        }
        result = pronadjen;
    }
}
