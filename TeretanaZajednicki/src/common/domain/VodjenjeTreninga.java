package common.domain;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class VodjenjeTreninga implements DomenskiObjekat {

    private Trener trener;
    private ProgramTreninga programTreninga;
    private Date datumTreninga;

    public VodjenjeTreninga() {
    }

    public VodjenjeTreninga(Trener trener, ProgramTreninga programTreninga, Date datumTreninga) {
        this.trener = trener;
        this.programTreninga = programTreninga;
        this.datumTreninga = datumTreninga;
    }

    public Trener getTrener() {
        return trener;
    }

    public void setTrener(Trener trener) {
        this.trener = trener;
    }

    public ProgramTreninga getProgramTreninga() {
        return programTreninga;
    }

    public void setProgramTreninga(ProgramTreninga programTreninga) {
        this.programTreninga = programTreninga;
    }

    public Date getDatumTreninga() {
        return datumTreninga;
    }

    public void setDatumTreninga(Date datumTreninga) {
        this.datumTreninga = datumTreninga;
    }

    @Override
    public String getTableName() {
        return "VodjenjeTreninga";
    }

    @Override
    public String getInsertColumnNames() {
        return "idTrener, idProgramTreninga, datumTreninga";
    }

    @Override
    public List<Object> getInsertValues() {
        List<Object> values = new ArrayList<Object>();
        values.add(trener != null ? trener.getId() : null);
        values.add(programTreninga != null ? programTreninga.getId() : null);
        values.add(datumTreninga);
        return values;
    }

    @Override
    public String getUpdateColumnNames() {
        // idTrener i idProgramTreninga cine slozeni kljuc (identitet reda) i ne
        // menjaju se kroz UPDATE - jedino datumTreninga je azurirljiv atribut.
        return "datumTreninga=?";
    }

    @Override
    public List<Object> getUpdateValues() {
        List<Object> values = new ArrayList<Object>();
        values.add(datumTreninga);
        return values;
    }

    @Override
    public String getSearchCondition() {
        StringBuilder uslov = new StringBuilder();
        if (trener != null && trener.getId() != null) {
            dodajUslov(uslov, "idTrener=?");
        }
        if (programTreninga != null && programTreninga.getId() != null) {
            dodajUslov(uslov, "idProgramTreninga=?");
        }
        if (datumTreninga != null) {
            dodajUslov(uslov, "datumTreninga=?");
        }
        return uslov.toString();
    }

    @Override
    public List<Object> getSearchValues() {
        List<Object> values = new ArrayList<Object>();
        if (trener != null && trener.getId() != null) {
            values.add(trener.getId());
        }
        if (programTreninga != null && programTreninga.getId() != null) {
            values.add(programTreninga.getId());
        }
        if (datumTreninga != null) {
            values.add(datumTreninga);
        }
        return values;
    }

    private void dodajUslov(StringBuilder uslov, String deo) {
        if (uslov.length() > 0) {
            uslov.append(" AND ");
        }
        uslov.append(deo);
    }

    @Override
    public void populateFromResultSet(ResultSet rs) throws SQLException {
        long idTrener = rs.getLong("idTrener");
        if (!rs.wasNull()) {
            Trener t = new Trener();
            t.setId(idTrener);
            trener = t;
        }

        long idProgramTreninga = rs.getLong("idProgramTreninga");
        if (!rs.wasNull()) {
            ProgramTreninga p = new ProgramTreninga();
            p.setId(idProgramTreninga);
            programTreninga = p;
        }

        datumTreninga = rs.getDate("datumTreninga");
    }

    /**
     * VodjenjeTreninga nema sopstveni surogatni kljuc - koristi slozeni
     * identitet trener+programTreninga (videti FK polja). Ova metoda se ne
     * poziva jer trenutno ne postoji nijedna sistemska operacija za ovaj
     * entitet (zakljucana odluka 10.1); namerno baca izuzetak umesto da
     * vrati laznu vrednost.
     */
    @Override
    public Long getId() {
        throw new UnsupportedOperationException(
                "VodjenjeTreninga nema sopstveni surogatni kljuc — koristi kompozitni identitet "
                        + "trener+programTreninga; ova metoda se ne poziva jer trenutno ne postoji "
                        + "nijedna sistemska operacija za ovaj entitet, zakljucana odluka 10.1");
    }

    /**
     * Videti napomenu uz {@link #getId()}.
     */
    @Override
    public void setId(Long id) {
        throw new UnsupportedOperationException(
                "VodjenjeTreninga nema sopstveni surogatni kljuc — koristi kompozitni identitet "
                        + "trener+programTreninga; ova metoda se ne poziva jer trenutno ne postoji "
                        + "nijedna sistemska operacija za ovaj entitet, zakljucana odluka 10.1");
    }

    @Override
    public DomenskiObjekat newInstance() {
        return new VodjenjeTreninga();
    }

    @Override
    public String toString() {
        return "VodjenjeTreninga{" +
                "trener=" + (trener != null ? trener.getId() : null) +
                ", programTreninga=" + (programTreninga != null ? programTreninga.getId() : null) +
                ", datumTreninga=" + datumTreninga +
                '}';
    }
}
