package common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipTreninga implements DomenskiObjekat {

    private Long idTipTreninga;
    private String naziv;
    private String nivoTezine;

    public TipTreninga() {
    }

    public TipTreninga(Long idTipTreninga, String naziv, String nivoTezine) {
        this.idTipTreninga = idTipTreninga;
        this.naziv = naziv;
        this.nivoTezine = nivoTezine;
    }

    public Long getIdTipTreninga() {
        return idTipTreninga;
    }

    public void setIdTipTreninga(Long idTipTreninga) {
        this.idTipTreninga = idTipTreninga;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getNivoTezine() {
        return nivoTezine;
    }

    public void setNivoTezine(String nivoTezine) {
        this.nivoTezine = nivoTezine;
    }

    @Override
    public String getTableName() {
        return "TipTreninga";
    }

    @Override
    public String getInsertColumnNames() {
        return "naziv, nivoTezine";
    }

    @Override
    public List<Object> getInsertValues() {
        List<Object> values = new ArrayList<Object>();
        values.add(naziv);
        values.add(nivoTezine);
        return values;
    }

    @Override
    public String getUpdateColumnNames() {
        return "naziv=?, nivoTezine=?";
    }

    @Override
    public List<Object> getUpdateValues() {
        return getInsertValues();
    }

    @Override
    public String getSearchCondition() {
        StringBuilder uslov = new StringBuilder();
        if (idTipTreninga != null) {
            dodajUslov(uslov, "idTipTreninga=?");
        }
        if (naziv != null) {
            dodajUslov(uslov, "naziv=?");
        }
        if (nivoTezine != null) {
            dodajUslov(uslov, "nivoTezine=?");
        }
        return uslov.toString();
    }

    @Override
    public List<Object> getSearchValues() {
        List<Object> values = new ArrayList<Object>();
        if (idTipTreninga != null) {
            values.add(idTipTreninga);
        }
        if (naziv != null) {
            values.add(naziv);
        }
        if (nivoTezine != null) {
            values.add(nivoTezine);
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
        idTipTreninga = rs.getLong("idTipTreninga");
        naziv = rs.getString("naziv");
        nivoTezine = rs.getString("nivoTezine");
    }

    @Override
    public void setId(Long id) {
        this.idTipTreninga = id;
    }

    @Override
    public Long getId() {
        return idTipTreninga;
    }

    @Override
    public DomenskiObjekat newInstance() {
        return new TipTreninga();
    }

    @Override
    public String toString() {
        return "TipTreninga{" +
                "idTipTreninga=" + idTipTreninga +
                ", naziv='" + naziv + '\'' +
                ", nivoTezine='" + nivoTezine + '\'' +
                '}';
    }
}
