package common.domain;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Trener implements DomenskiObjekat {

    private Long idTrener;
    private String ime;
    private String prezime;
    private String korisnickoIme;
    private String sifra;
    private Date datumZaposlenja;

    public Trener() {
    }

    public Trener(Long idTrener, String ime, String prezime, String korisnickoIme, String sifra, Date datumZaposlenja) {
        this.idTrener = idTrener;
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
        this.datumZaposlenja = datumZaposlenja;
    }

    public Long getIdTrener() {
        return idTrener;
    }

    public void setIdTrener(Long idTrener) {
        this.idTrener = idTrener;
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

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public Date getDatumZaposlenja() {
        return datumZaposlenja;
    }

    public void setDatumZaposlenja(Date datumZaposlenja) {
        this.datumZaposlenja = datumZaposlenja;
    }

    @Override
    public String getTableName() {
        return "Trener";
    }

    @Override
    public String getInsertColumnNames() {
        return "ime, prezime, korisnickoIme, sifra, datumZaposlenja";
    }

    @Override
    public List<Object> getInsertValues() {
        List<Object> values = new ArrayList<Object>();
        values.add(ime);
        values.add(prezime);
        values.add(korisnickoIme);
        values.add(sifra);
        values.add(datumZaposlenja);
        return values;
    }

    @Override
    public String getUpdateColumnNames() {
        return "ime=?, prezime=?, korisnickoIme=?, sifra=?, datumZaposlenja=?";
    }

    @Override
    public List<Object> getUpdateValues() {
        return getInsertValues();
    }

    @Override
    public String getSearchCondition() {
        StringBuilder uslov = new StringBuilder();
        if (idTrener != null) {
            dodajUslov(uslov, "idTrener=?");
        }
        if (ime != null) {
            dodajUslov(uslov, "ime=?");
        }
        if (prezime != null) {
            dodajUslov(uslov, "prezime=?");
        }
        if (korisnickoIme != null) {
            dodajUslov(uslov, "korisnickoIme=?");
        }
        if (sifra != null) {
            dodajUslov(uslov, "sifra=?");
        }
        if (datumZaposlenja != null) {
            dodajUslov(uslov, "datumZaposlenja=?");
        }
        return uslov.toString();
    }

    @Override
    public List<Object> getSearchValues() {
        List<Object> values = new ArrayList<Object>();
        if (idTrener != null) {
            values.add(idTrener);
        }
        if (ime != null) {
            values.add(ime);
        }
        if (prezime != null) {
            values.add(prezime);
        }
        if (korisnickoIme != null) {
            values.add(korisnickoIme);
        }
        if (sifra != null) {
            values.add(sifra);
        }
        if (datumZaposlenja != null) {
            values.add(datumZaposlenja);
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
        idTrener = rs.getLong("idTrener");
        ime = rs.getString("ime");
        prezime = rs.getString("prezime");
        korisnickoIme = rs.getString("korisnickoIme");
        sifra = rs.getString("sifra");
        datumZaposlenja = rs.getDate("datumZaposlenja");
    }

    @Override
    public void setId(Long id) {
        this.idTrener = id;
    }

    @Override
    public Long getId() {
        return idTrener;
    }

    @Override
    public DomenskiObjekat newInstance() {
        return new Trener();
    }

    @Override
    public String toString() {
        return "Trener{" +
                "idTrener=" + idTrener +
                ", ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                ", datumZaposlenja=" + datumZaposlenja +
                '}';
    }
}
