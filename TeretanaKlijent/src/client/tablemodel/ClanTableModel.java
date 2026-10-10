package client.tablemodel;

import common.domain.Clan;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClanTableModel extends AbstractTableModel {

    private static final String[] NAZIVI_KOLONA =
            {"Id", "Ime", "Prezime", "Email", "Datum uclanjenja", "Aktivan", "Kategorija clanarine"};

    private List<Clan> clanovi = new ArrayList<Clan>();
    private Map<Long, String> nazivKategorija = new HashMap<Long, String>();

    public void setClanovi(List<Clan> clanovi) {
        this.clanovi = clanovi != null ? clanovi : new ArrayList<Clan>();
        fireTableDataChanged();
    }

    public void setNazivKategorija(Map<Long, String> nazivKategorija) {
        this.nazivKategorija = nazivKategorija;
    }

    public Clan getClanNaIndeksu(int red) {
        return clanovi.get(red);
    }

    @Override
    public int getRowCount() {
        return clanovi.size();
    }

    @Override
    public int getColumnCount() {
        return NAZIVI_KOLONA.length;
    }

    @Override
    public String getColumnName(int kolona) {
        return NAZIVI_KOLONA[kolona];
    }

    @Override
    public Object getValueAt(int red, int kolona) {
        Clan c = clanovi.get(red);
        switch (kolona) {
            case 0:
                return c.getIdClan();
            case 1:
                return c.getIme();
            case 2:
                return c.getPrezime();
            case 3:
                return c.getEmail();
            case 4:
                return c.getDatumUclanjenja();
            case 5:
                return c.getAktivan();
            case 6:
                return nazivKategorije(c.getKategorijaClanarine() != null ? c.getKategorijaClanarine().getId() : null);
            default:
                return null;
        }
    }

    private String nazivKategorije(Long id) {
        if (id == null) {
            return "";
        }
        String naziv = nazivKategorija.get(id);
        return naziv != null ? naziv : ("#" + id);
    }
}
