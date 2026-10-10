package server.so.prijava;

import common.domain.DomenskiObjekat;
import common.domain.Trener;
import common.exception.SystemOperationException;
import server.broker.Broker;
import server.so.AbstractSystemOperation;

import java.util.List;


 
public class SOPrijaviTrener extends AbstractSystemOperation {

    @Override
    protected void validate(DomenskiObjekat argument) throws Exception {
        Trener kredencijali = (Trener) argument;
        if (kredencijali.getKorisnickoIme() == null || kredencijali.getKorisnickoIme().trim().isEmpty()
                || kredencijali.getSifra() == null || kredencijali.getSifra().trim().isEmpty()) {
            throw new SystemOperationException("Korisnicko ime i sifra su obavezni.");
        }
    }

    @Override
    protected void execute(DomenskiObjekat argument) throws Exception {
        Trener kredencijali = (Trener) argument;

        Trener kriterijum = new Trener();
        kriterijum.setKorisnickoIme(kredencijali.getKorisnickoIme());
        List<DomenskiObjekat> pronadjeni = Broker.getInstance().filter(kriterijum, konekcija);

        Trener pronadjeniTrener = pronadjeni.isEmpty() ? null : (Trener) pronadjeni.get(0);
        if (pronadjeniTrener == null || !pronadjeniTrener.getSifra().equals(kredencijali.getSifra())) {
            throw new SystemOperationException("Korisnicko ime i sifra nisu ispravni");
        }

        result = pronadjeniTrener;
    }
}
