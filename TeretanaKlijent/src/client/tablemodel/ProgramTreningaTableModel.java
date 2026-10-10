package client.tablemodel;

import common.domain.ProgramTreninga;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class ProgramTreningaTableModel extends AbstractTableModel {

    private static final String[] NAZIVI_KOLONA = {"Id", "Naziv", "Opis", "Broj nedelja"};

    private List<ProgramTreninga> programi = new ArrayList<ProgramTreninga>();

    public void setProgrami(List<ProgramTreninga> programi) {
        this.programi = programi != null ? programi : new ArrayList<ProgramTreninga>();
        fireTableDataChanged();
    }

    public ProgramTreninga getProgramNaIndeksu(int red) {
        return programi.get(red);
    }

    @Override
    public int getRowCount() {
        return programi.size();
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
        ProgramTreninga p = programi.get(red);
        switch (kolona) {
            case 0:
                return p.getIdProgramTreninga();
            case 1:
                return p.getNaziv();
            case 2:
                return p.getOpis();
            case 3:
                return p.getBrojNedelja();
            default:
                return null;
        }
    }
}
