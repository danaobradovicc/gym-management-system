package common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KategorijaClanarine implements DomenskiObjekat {

    private Long idKategorijaClanarine;
    private String naziv;

    public KategorijaClanarine() {
    }

    public KategorijaClanarine(Long idKategorijaClanarine, String naziv) {
        this.idKategorijaClanarine = idKategorijaClanarine;
        this.naziv = naziv;
    }

    public Long getIdKategorijaClanarine() {
        return idKategorijaClanarine;
    }

    public void setIdKategorijaClanarine(Long idKategorijaClanarine) {
        this.idKategorijaClanarine = idKategorijaClanarine;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String getTableName() {
        return "KategorijaClanarine";
    }

    @Override
    public String getInsertColumnNames() {
        return "naziv";
    }

    @Override
    public List<Object> getInsertValues() {
        List<Object> values = new ArrayList<Object>();
        values.add(naziv);
        return values;
    }

    @Override
    public String getUpdateColumnNames() {
        return "naziv=?";
    }

    @Override
    public List<Object> getUpdateValues() {
        return getInsertValues();
    }

    @Override
    public String getSearchCondition() {
        StringBuilder uslov = new StringBuilder();
        if (idKategorijaClanarine != null) {
            dodajUslov(uslov, "idKategorijaClanarine=?");
        }
        if (naziv != null) {
            dodajUslov(uslov, "naziv=?");
        }
        return uslov.toString();
    }

    @Override
    public List<Object> getSearchValues() {
        List<Object> values = new ArrayList<Object>();
        if (idKategorijaClanarine != null) {
            values.add(idKategorijaClanarine);
        }
        if (naziv != null) {
            values.add(naziv);
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
        idKategorijaClanarine = rs.getLong("idKategorijaClanarine");
        naziv = rs.getString("naziv");
    }

    @Override
    public void setId(Long id) {
        this.idKategorijaClanarine = id;
    }

    @Override
    public Long getId() {
        return idKategorijaClanarine;
    }

    @Override
    public DomenskiObjekat newInstance() {
        return new KategorijaClanarine();
    }

    @Override
    public String toString() {
        return "KategorijaClanarine{" +
                "idKategorijaClanarine=" + idKategorijaClanarine +
                ", naziv='" + naziv + '\'' +
                '}';
    }
}
