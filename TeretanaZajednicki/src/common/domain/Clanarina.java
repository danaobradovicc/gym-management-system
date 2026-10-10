package common.domain;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Clanarina implements DomenskiObjekat {

    private Long idClanarina;
    private Date datumPocetka;
    private Date datumZavrsetka;
    private Double cena;
    private String status;
    private Trener trener;
    private Clan clan;
    private List<TerminTreninga> terminiTreninga;

    public Clanarina() {
    }

    public Clanarina(Long idClanarina, Date datumPocetka, Date datumZavrsetka, Double cena, String status,
                      Trener trener, Clan clan) {
        this.idClanarina = idClanarina;
        this.datumPocetka = datumPocetka;
        this.datumZavrsetka = datumZavrsetka;
        this.cena = cena;
        this.status = status;
        this.trener = trener;
        this.clan = clan;
    }

    public Long getIdClanarina() {
        return idClanarina;
    }

    public void setIdClanarina(Long idClanarina) {
        this.idClanarina = idClanarina;
    }

    public Date getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(Date datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public Date getDatumZavrsetka() {
        return datumZavrsetka;
    }

    public void setDatumZavrsetka(Date datumZavrsetka) {
        this.datumZavrsetka = datumZavrsetka;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Trener getTrener() {
        return trener;
    }

    public void setTrener(Trener trener) {
        this.trener = trener;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public List<TerminTreninga> getTerminiTreninga() {
        return terminiTreninga;
    }

    public void setTerminiTreninga(List<TerminTreninga> terminiTreninga) {
        this.terminiTreninga = terminiTreninga;
    }

    @Override
    public String getTableName() {
        return "Clanarina";
    }

    @Override
    public String getInsertColumnNames() {
        return "datumPocetka, datumZavrsetka, cena, status, idTrener, idClan";
    }

    @Override
    public List<Object> getInsertValues() {
        List<Object> values = new ArrayList<Object>();
        values.add(datumPocetka);
        values.add(datumZavrsetka);
        values.add(cena);
        values.add(status);
        values.add(trener != null ? trener.getId() : null);
        values.add(clan != null ? clan.getId() : null);
        return values;
    }

    @Override
    public String getUpdateColumnNames() {
        return "datumPocetka=?, datumZavrsetka=?, cena=?, status=?, idTrener=?, idClan=?";
    }

    @Override
    public List<Object> getUpdateValues() {
        return getInsertValues();
    }

    @Override
    public String getSearchCondition() {
        StringBuilder uslov = new StringBuilder();
        if (idClanarina != null) {
            dodajUslov(uslov, "idClanarina=?");
        }
        if (datumPocetka != null) {
            dodajUslov(uslov, "datumPocetka=?");
        }
        if (datumZavrsetka != null) {
            dodajUslov(uslov, "datumZavrsetka=?");
        }
        if (cena != null) {
            dodajUslov(uslov, "cena=?");
        }
        if (status != null) {
            dodajUslov(uslov, "status=?");
        }
        if (trener != null && trener.getId() != null) {
            dodajUslov(uslov, "idTrener=?");
        }
        if (clan != null && clan.getId() != null) {
            dodajUslov(uslov, "idClan=?");
        }
        return uslov.toString();
    }

    @Override
    public List<Object> getSearchValues() {
        List<Object> values = new ArrayList<Object>();
        if (idClanarina != null) {
            values.add(idClanarina);
        }
        if (datumPocetka != null) {
            values.add(datumPocetka);
        }
        if (datumZavrsetka != null) {
            values.add(datumZavrsetka);
        }
        if (cena != null) {
            values.add(cena);
        }
        if (status != null) {
            values.add(status);
        }
        if (trener != null && trener.getId() != null) {
            values.add(trener.getId());
        }
        if (clan != null && clan.getId() != null) {
            values.add(clan.getId());
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
        idClanarina = rs.getLong("idClanarina");
        datumPocetka = rs.getDate("datumPocetka");
        datumZavrsetka = rs.getDate("datumZavrsetka");
        cena = rs.getDouble("cena");
        status = rs.getString("status");

        long idTrener = rs.getLong("idTrener");
        if (!rs.wasNull()) {
            Trener t = new Trener();
            t.setId(idTrener);
            trener = t;
        }

        long idClan = rs.getLong("idClan");
        if (!rs.wasNull()) {
            Clan c = new Clan();
            c.setId(idClan);
            clan = c;
        }
    }

    @Override
    public void setId(Long id) {
        this.idClanarina = id;
    }

    @Override
    public Long getId() {
        return idClanarina;
    }

    @Override
    public DomenskiObjekat newInstance() {
        return new Clanarina();
    }

    @Override
    public String toString() {
        return "Clanarina{" +
                "idClanarina=" + idClanarina +
                ", datumPocetka=" + datumPocetka +
                ", datumZavrsetka=" + datumZavrsetka +
                ", cena=" + cena +
                ", status='" + status + '\'' +
                ", trener=" + (trener != null ? trener.getId() : null) +
                ", clan=" + (clan != null ? clan.getId() : null) +
                '}';
    }
}
