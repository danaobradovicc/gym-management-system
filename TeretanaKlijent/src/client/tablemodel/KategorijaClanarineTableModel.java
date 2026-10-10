package client.tablemodel;

import common.domain.KategorijaClanarine;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class KategorijaClanarineTableModel extends AbstractTableModel {

    private static final String[] NAZIVI_KOLONA = {"Id", "Naziv"};

    private List<KategorijaClanarine> kategorije = new ArrayList<KategorijaClanarine>();

    public void setKategorije(List<KategorijaClanarine> kategorije) {
        this.kategorije = kategorije != null ? kategorije : new ArrayList<KategorijaClanarine>();
        fireTableDataChanged();
    }

    public KategorijaClanarine getKategorijaNaIndeksu(int red) {
        return kategorije.get(red);
        //korisnik klikne na red i dobija se ceo objekat
    }

    @Override
    public int getRowCount() {
        return kategorije.size();
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
        KategorijaClanarine k = kategorije.get(red);
        switch (kolona) {
            case 0:
                return k.getIdKategorijaClanarine();
            case 1:
                return k.getNaziv();
            default:
                return null;
        }
    }
}
