package client.tablemodel;

import common.domain.TerminTreninga;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TerminTreningaTableModel extends AbstractTableModel {

    private static final String[] NAZIVI_KOLONA =
            {"Sala", "Vreme pocetka", "Vreme zavrsetka", "Max ucesnika", "Tip treninga"};

    private List<TerminTreninga> termini = new ArrayList<TerminTreninga>();
    private Map<Long, String> nazivTipova = new HashMap<Long, String>();

    public void setTermini(List<TerminTreninga> termini) {
        this.termini = termini != null ? termini : new ArrayList<TerminTreninga>();
        fireTableDataChanged();
    }

    public void setNazivTipova(Map<Long, String> nazivTipova) {
        this.nazivTipova = nazivTipova;
    }

    public TerminTreninga getTerminNaIndeksu(int red) {
        return termini.get(red);
    }

    @Override
    public int getRowCount() {
        return termini.size();
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
        TerminTreninga t = termini.get(red);
        switch (kolona) {
            case 0:
                return t.getSala();
            case 1:
                return t.getDatumVremePocetka();
            case 2:
                return t.getDatumVremeZavrsetka();
            case 3:
                return t.getMaxBrojUcesnika();
            case 4:
                return imeIliId(t.getTipTreninga() != null ? t.getTipTreninga().getId() : null);
            default:
                return null;
        }
    }

    private String imeIliId(Long id) {
        if (id == null) {
            return "";
        }
        String naziv = nazivTipova.get(id);
        return naziv != null ? naziv : ("#" + id);
    }
}
