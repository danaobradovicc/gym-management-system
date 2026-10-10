package common.domain;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Clan implements DomenskiObjekat {

    private Long idClan;
    private String ime;
    private String prezime;
    private String email;
    private Date datumUclanjenja;
    private Boolean aktivan;
    private KategorijaClanarine kategorijaClanarine;

    public Clan() {
    }

    public Clan(Long idClan, String ime, String prezime, String email, Date datumUclanjenja, Boolean aktivan,
                KategorijaClanarine kategorijaClanarine) {
        this.idClan = idClan;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.datumUclanjenja = datumUclanjenja;
        this.aktivan = aktivan;
        this.kategorijaClanarine = kategorijaClanarine;
    }

    public Long getIdClan() {
        return idClan;
    }

    public void setIdClan(Long idClan) {
        this.idClan = idClan;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDatumUclanjenja() {
        return datumUclanjenja;
    }

    public void setDatumUclanjenja(Date datumUclanjenja) {
        this.datumUclanjenja = datumUclanjenja;
    }

    public Boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }

    public KategorijaClanarine getKategorijaClanarine() {
        return kategorijaClanarine;
    }

    public void setKategorijaClanarine(KategorijaClanarine kategorijaClanarine) {
        this.kategorijaClanarine = kategorijaClanarine;
    }

    @Override
    public String getTableName() {
        return "Clan";
    }

    @Override
    public String getInsertColumnNames() {
        return "ime, prezime, email, datumUclanjenja, aktivan, idKategorijaClanarine";
    }

    @Override
    public List<Object> getInsertValues() {
        List<Object> values = new ArrayList<Object>();
        values.add(ime);
        values.add(prezime);
        values.add(email);
        values.add(datumUclanjenja);
        values.add(aktivan);
        values.add(kategorijaClanarine != null ? kategorijaClanarine.getId() : null);
        return values;
    }

    @Override
    public String getUpdateColumnNames() {
        return "ime=?, prezime=?, email=?, datumUclanjenja=?, aktivan=?, idKategorijaClanarine=?";
    }

    @Override
    public List<Object> getUpdateValues() {
        return getInsertValues();
    }

    @Override
    public String getSearchCondition() {
        StringBuilder uslov = new StringBuilder();
        if (idClan != null) {
            dodajUslov(uslov, "idClan=?");
        }
        if (ime != null) {
            dodajUslov(uslov, "ime=?");
        }
        if (prezime != null) {
            dodajUslov(uslov, "prezime=?");
        }
        if (email != null) {
            dodajUslov(uslov, "email=?");
        }
        if (datumUclanjenja != null) {
            dodajUslov(uslov, "datumUclanjenja=?");
        }
        if (aktivan != null) {
            dodajUslov(uslov, "aktivan=?");
        }
        if (kategorijaClanarine != null && kategorijaClanarine.getId() != null) {
            dodajUslov(uslov, "idKategorijaClanarine=?");
        }
        return uslov.toString();
    }

    @Override
    public List<Object> getSearchValues() {
        List<Object> values = new ArrayList<Object>();
        if (idClan != null) {
            values.add(idClan);
        }
        if (ime != null) {
            values.add(ime);
        }
        if (prezime != null) {
            values.add(prezime);
        }
        if (email != null) {
            values.add(email);
        }
        if (datumUclanjenja != null) {
            values.add(datumUclanjenja);
        }
        if (aktivan != null) {
            values.add(aktivan);
        }
        if (kategorijaClanarine != null && kategorijaClanarine.getId() != null) {
            values.add(kategorijaClanarine.getId());
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
        idClan = rs.getLong("idClan");
        ime = rs.getString("ime");
        prezime = rs.getString("prezime");
        email = rs.getString("email");
        datumUclanjenja = rs.getDate("datumUclanjenja");
        aktivan = rs.getBoolean("aktivan");

        long idKategorijaClanarine = rs.getLong("idKategorijaClanarine");
        if (!rs.wasNull()) {
            KategorijaClanarine kat = new KategorijaClanarine();
            kat.setId(idKategorijaClanarine);
            kategorijaClanarine = kat;
        }
    }

    @Override
    public void setId(Long id) {
        this.idClan = id;
    }

    @Override
    public Long getId() {
        return idClan;
    }

    @Override
    public DomenskiObjekat newInstance() {
        return new Clan();
    }

    @Override
    public String toString() {
        return "Clan{" +
                "idClan=" + idClan +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", email='" + email + '\'' +
                ", datumUclanjenja=" + datumUclanjenja +
                ", aktivan=" + aktivan +
                ", kategorijaClanarine=" + (kategorijaClanarine != null ? kategorijaClanarine.getId() : null) +
                '}';
    }
}
