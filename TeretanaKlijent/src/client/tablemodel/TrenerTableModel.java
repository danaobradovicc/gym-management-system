package client.tablemodel;

import common.domain.Trener;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TrenerTableModel extends AbstractTableModel {

    private static final String[] NAZIVI_KOLONA = {"Id", "Ime", "Prezime", "Korisnicko ime", "Datum zaposlenja"};

    private List<Trener> treneri = new ArrayList<Trener>();

    public void setTreneri(List<Trener> treneri) {
        this.treneri = treneri != null ? treneri : new ArrayList<Trener>();
        fireTableDataChanged();
    }

    public Trener getTrenerNaIndeksu(int red) {
        return treneri.get(red);
    }

    @Override
    public int getRowCount() {
        return treneri.size();
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
        Trener t = treneri.get(red);
        switch (kolona) {
            case 0:
                return t.getIdTrener();
            case 1:
                return t.getIme();
            case 2:
                return t.getPrezime();
            case 3:
                return t.getKorisnickoIme();
            case 4:
                return t.getDatumZaposlenja();
            default:
                return null;
        }
    }
}
