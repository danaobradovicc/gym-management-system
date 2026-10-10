package client.tablemodel;

import common.domain.Clanarina;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClanarinaTableModel extends AbstractTableModel {

    private static final String[] NAZIVI_KOLONA =
            {"Id", "Datum pocetka", "Datum zavrsetka", "Cena", "Status", "Trener", "Clan"};

    private List<Clanarina> clanarine = new ArrayList<Clanarina>();
    private Map<Long, String> nazivTrenera = new HashMap<Long, String>();
    private Map<Long, String> nazivClanova = new HashMap<Long, String>();
    //id sa imenom, povezivanje

    public void setClanarine(List<Clanarina> clanarine) {
        this.clanarine = clanarine != null ? clanarine : new ArrayList<Clanarina>();
        fireTableDataChanged();
        //podaci su se promenili osvezi prikaz
    }

    public void setNazivTrenera(Map<Long, String> nazivTrenera) {
        this.nazivTrenera = nazivTrenera;
    }

    public void setNazivClanova(Map<Long, String> nazivClanova) {
        this.nazivClanova = nazivClanova;
    }

    public Clanarina getClanarinaNaIndeksu(int red) {
        return clanarine.get(red);
    }

    @Override
    public int getRowCount() {
        return clanarine.size();
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
        Clanarina c = clanarine.get(red);
        switch (kolona) {
            case 0:
                return c.getIdClanarina();
            case 1:
                return c.getDatumPocetka();
            case 2:
                return c.getDatumZavrsetka();
            case 3:
                return c.getCena();
            case 4:
                return c.getStatus();
            case 5:
                return imeIliId(nazivTrenera, c.getTrener() != null ? c.getTrener().getId() : null);
            case 6:
                return imeIliId(nazivClanova, c.getClan() != null ? c.getClan().getId() : null);
            default:
                return null;
        }
    }

    private String imeIliId(Map<Long, String> nazivi, Long id) {
        if (id == null) {
            return "";
        }
        String naziv = nazivi.get(id);
        return naziv != null ? naziv : ("#" + id);
    }
}
