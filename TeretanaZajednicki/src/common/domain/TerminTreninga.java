package common.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class TerminTreninga implements DomenskiObjekat {

    private Long idTerminTreninga;
    private String sala;
    private Timestamp datumVremePocetka;
    private Timestamp datumVremeZavrsetka;
    private Integer maxBrojUcesnika;
    private Clanarina clanarina;
    private TipTreninga tipTreninga;

    public TerminTreninga() {
    }

    public TerminTreninga(Long idTerminTreninga, String sala, Timestamp datumVremePocetka, Timestamp datumVremeZavrsetka,
                           Integer maxBrojUcesnika, Clanarina clanarina, TipTreninga tipTreninga) {
        this.idTerminTreninga = idTerminTreninga;
        this.sala = sala;
        this.datumVremePocetka = datumVremePocetka;
        this.datumVremeZavrsetka = datumVremeZavrsetka;
        this.maxBrojUcesnika = maxBrojUcesnika;
        this.clanarina = clanarina;
        this.tipTreninga = tipTreninga;
    }

    public Long getIdTerminTreninga() {
        return idTerminTreninga;
    }

    public void setIdTerminTreninga(Long idTerminTreninga) {
        this.idTerminTreninga = idTerminTreninga;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public Timestamp getDatumVremePocetka() {
        return datumVremePocetka;
    }

    public void setDatumVremePocetka(Timestamp datumVremePocetka) {
        this.datumVremePocetka = datumVremePocetka;
    }

    public Timestamp getDatumVremeZavrsetka() {
        return datumVremeZavrsetka;
    }

    public void setDatumVremeZavrsetka(Timestamp datumVremeZavrsetka) {
        this.datumVremeZavrsetka = datumVremeZavrsetka;
    }

    public Integer getMaxBrojUcesnika() {
        return maxBrojUcesnika;
    }

    public void setMaxBrojUcesnika(Integer maxBrojUcesnika) {
        this.maxBrojUcesnika = maxBrojUcesnika;
    }

    public Clanarina getClanarina() {
        return clanarina;
    }

    public void setClanarina(Clanarina clanarina) {
        this.clanarina = clanarina;
    }

    public TipTreninga getTipTreninga() {
        return tipTreninga;
    }

    public void setTipTreninga(TipTreninga tipTreninga) {
        this.tipTreninga = tipTreninga;
    }

    @Override
    public String getTableName() {
        return "TerminTreninga";
    }

    @Override
    public String getInsertColumnNames() {
        return "sala, datumVremePocetka, datumVremeZavrsetka, maxBrojUcesnika, idClanarina, idTipTreninga";
    }

    @Override
    public List<Object> getInsertValues() {
        List<Object> values = new ArrayList<Object>();
        values.add(sala);
        values.add(datumVremePocetka);
        values.add(datumVremeZavrsetka);
        values.add(maxBrojUcesnika);
        values.add(clanarina != null ? clanarina.getId() : null);
        values.add(tipTreninga != null ? tipTreninga.getId() : null);
        return values;
    }

    @Override
    public String getUpdateColumnNames() {
        return "sala=?, datumVremePocetka=?, datumVremeZavrsetka=?, maxBrojUcesnika=?, idClanarina=?, idTipTreninga=?";
    }

    @Override
    public List<Object> getUpdateValues() {
        return getInsertValues();
    }

    @Override
    public String getSearchCondition() {
        StringBuilder uslov = new StringBuilder();
        if (idTerminTreninga != null) {
            dodajUslov(uslov, "idTerminTreninga=?");
        }
        if (sala != null) {
            dodajUslov(uslov, "sala=?");
        }
        if (datumVremePocetka != null) {
            dodajUslov(uslov, "datumVremePocetka=?");
        }
        if (datumVremeZavrsetka != null) {
            dodajUslov(uslov, "datumVremeZavrsetka=?");
        }
        if (maxBrojUcesnika != null) {
            dodajUslov(uslov, "maxBrojUcesnika=?");
        }
        if (clanarina != null && clanarina.getId() != null) {
            dodajUslov(uslov, "idClanarina=?");
        }
        if (tipTreninga != null && tipTreninga.getId() != null) {
            dodajUslov(uslov, "idTipTreninga=?");
        }
        return uslov.toString();
    }

    @Override
    public List<Object> getSearchValues() {
        List<Object> values = new ArrayList<Object>();
        if (idTerminTreninga != null) {
            values.add(idTerminTreninga);
        }
        if (sala != null) {
            values.add(sala);
        }
        if (datumVremePocetka != null) {
            values.add(datumVremePocetka);
        }
        if (datumVremeZavrsetka != null) {
            values.add(datumVremeZavrsetka);
        }
        if (maxBrojUcesnika != null) {
            values.add(maxBrojUcesnika);
        }
        if (clanarina != null && clanarina.getId() != null) {
            values.add(clanarina.getId());
        }
        if (tipTreninga != null && tipTreninga.getId() != null) {
            values.add(tipTreninga.getId());
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
        idTerminTreninga = rs.getLong("idTerminTreninga");
        sala = rs.getString("sala");
        datumVremePocetka = rs.getTimestamp("datumVremePocetka");
        datumVremeZavrsetka = rs.getTimestamp("datumVremeZavrsetka");
        maxBrojUcesnika = rs.getInt("maxBrojUcesnika");

        long idClanarina = rs.getLong("idClanarina");
        if (!rs.wasNull()) {
            Clanarina c = new Clanarina();
            c.setId(idClanarina);
            clanarina = c;
        }

        long idTipTreninga = rs.getLong("idTipTreninga");
        if (!rs.wasNull()) {
            TipTreninga t = new TipTreninga();
            t.setId(idTipTreninga);
            tipTreninga = t;
        }
    }

    @Override
    public void setId(Long id) {
        this.idTerminTreninga = id;
    }

    @Override
    public Long getId() {
        return idTerminTreninga;
    }

    @Override
    public DomenskiObjekat newInstance() {
        return new TerminTreninga();
    }

    @Override
    public String toString() {
        return "TerminTreninga{" +
                "idTerminTreninga=" + idTerminTreninga +
                ", sala='" + sala + '\'' +
                ", datumVremePocetka=" + datumVremePocetka +
                ", datumVremeZavrsetka=" + datumVremeZavrsetka +
                ", maxBrojUcesnika=" + maxBrojUcesnika +
                ", clanarina=" + (clanarina != null ? clanarina.getId() : null) +
                ", tipTreninga=" + (tipTreninga != null ? tipTreninga.getId() : null) +
                '}';
    }
}
