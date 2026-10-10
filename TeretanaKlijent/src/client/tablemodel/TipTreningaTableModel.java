package client.tablemodel;

import common.domain.TipTreninga;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class TipTreningaTableModel extends AbstractTableModel {

    private static final String[] NAZIVI_KOLONA = {"Id", "Naziv", "Nivo tezine"};

    private List<TipTreninga> tipovi = new ArrayList<TipTreninga>();

    public void setTipovi(List<TipTreninga> tipovi) {
        this.tipovi = tipovi != null ? tipovi : new ArrayList<TipTreninga>();
        fireTableDataChanged();
    }

    public TipTreninga getTipNaIndeksu(int red) {
        return tipovi.get(red);
    }

    @Override
    public int getRowCount() {
        return tipovi.size();
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
        TipTreninga t = tipovi.get(red);
        switch (kolona) {
            case 0:
                return t.getIdTipTreninga();
            case 1:
                return t.getNaziv();
            case 2:
                return t.getNivoTezine();
            default:
                return null;
        }
    }
}
