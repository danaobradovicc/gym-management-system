package client.controller;

import common.communication.Operation;
import common.communication.Request;
import common.communication.Response;
import common.domain.DomenskiObjekat;
import common.domain.ProgramTreninga;

import java.util.List;


public class KontrolerProgramTreninga {

    public ProgramTreninga kreiraj() throws Exception {
        return (ProgramTreninga) posaljiIVratiRezultat(Operation.KREIRAJ_PROGRAM_TRENINGA, new ProgramTreninga());
    }

    public ProgramTreninga promeni(ProgramTreninga program) throws Exception {
        return (ProgramTreninga) posaljiIVratiRezultat(Operation.PROMENI_PROGRAM_TRENINGA, program);
    }

    public ProgramTreninga pretrazi(ProgramTreninga kriterijum) throws Exception {
        return (ProgramTreninga) posaljiIVratiRezultat(Operation.PRETRAZI_PROGRAM_TRENINGA, kriterijum);
    }

    public void obrisi(ProgramTreninga program) throws Exception {
        posaljiIVratiRezultat(Operation.OBRISI_PROGRAM_TRENINGA, program);
    }

    @SuppressWarnings("unchecked")
    public List<ProgramTreninga> vratiListu(ProgramTreninga kriterijum) throws Exception {
        return (List<ProgramTreninga>) posaljiIVratiRezultat(
                Operation.VRATI_LISTU_PROGRAM_TRENINGA_PO_KRITERIJUMU_PROGRAM_TRENINGA, kriterijum);
    }

    @SuppressWarnings("unchecked")
    public List<ProgramTreninga> vratiListuSvih() throws Exception {
        return (List<ProgramTreninga>) posaljiIVratiRezultat(Operation.VRATI_LISTU_SVI_PROGRAM_TRENINGA, new ProgramTreninga());
    }

    private Object posaljiIVratiRezultat(Operation operacija, DomenskiObjekat argument) throws Exception {
        Request zahtev = new Request(operacija, argument);
        Response odgovor = ClientConnection.getInstance().posaljiZahtev(zahtev);
        if (odgovor.getException() != null) {
            throw odgovor.getException();
        }
        return odgovor.getResult();
    }
}
