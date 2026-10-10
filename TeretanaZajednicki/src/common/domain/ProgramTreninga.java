package common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramTreninga implements DomenskiObjekat {

    private Long idProgramTreninga;
    private String naziv;
    private String opis;
    private Integer brojNedelja;

    public ProgramTreninga() {
    }

    public ProgramTreninga(Long idProgramTreninga, String naziv, String opis, Integer brojNedelja) {
        this.idProgramTreninga = idProgramTreninga;
        this.naziv = naziv;
        this.opis = opis;
        this.brojNedelja = brojNedelja;
    }

    public Long getIdProgramTreninga() {
        return idProgramTreninga;
    }

    public void setIdProgramTreninga(Long idProgramTreninga) {
        this.idProgramTreninga = idProgramTreninga;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getBrojNedelja() {
        return brojNedelja;
    }

    public void setBrojNedelja(Integer brojNedelja) {
        this.brojNedelja = brojNedelja;
    }

    @Override
    public String getTableName() {
        return "ProgramTreninga";
    }

    @Override
    public String getInsertColumnNames() {
        return "naziv, opis, brojNedelja";
    }

    @Override
    public List<Object> getInsertValues() {
        List<Object> values = new ArrayList<Object>();
        values.add(naziv);
        values.add(opis);
        values.add(brojNedelja);
        return values;
    }

    @Override
    public String getUpdateColumnNames() {
        return "naziv=?, opis=?, brojNedelja=?";
    }

    @Override
    public List<Object> getUpdateValues() {
        return getInsertValues();
    }

    @Override
    public String getSearchCondition() {
        StringBuilder uslov = new StringBuilder();
        if (idProgramTreninga != null) {
            dodajUslov(uslov, "idProgramTreninga=?");
        }
        if (naziv != null) {
            dodajUslov(uslov, "naziv=?");
        }
        if (opis != null) {
            dodajUslov(uslov, "opis=?");
        }
        if (brojNedelja != null) {
            dodajUslov(uslov, "brojNedelja=?");
        }
        return uslov.toString();
    }

    @Override
    public List<Object> getSearchValues() {
        List<Object> values = new ArrayList<Object>();
        if (idProgramTreninga != null) {
            values.add(idProgramTreninga);
        }
        if (naziv != null) {
            values.add(naziv);
        }
        if (opis != null) {
            values.add(opis);
        }
        if (brojNedelja != null) {
            values.add(brojNedelja);
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
        idProgramTreninga = rs.getLong("idProgramTreninga");
        naziv = rs.getString("naziv");
        opis = rs.getString("opis");
        brojNedelja = rs.getInt("brojNedelja");
    }

    @Override
    public void setId(Long id) {
        this.idProgramTreninga = id;
    }

    @Override
    public Long getId() {
        return idProgramTreninga;
    }

    @Override
    public DomenskiObjekat newInstance() {
        return new ProgramTreninga();
    }

    @Override
    public String toString() {
        return "ProgramTreninga{" +
                "idProgramTreninga=" + idProgramTreninga +
                ", naziv='" + naziv + '\'' +
                ", opis='" + opis + '\'' +
                ", brojNedelja=" + brojNedelja +
                '}';
    }
}
