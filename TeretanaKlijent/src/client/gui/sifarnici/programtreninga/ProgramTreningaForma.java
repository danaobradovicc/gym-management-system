package client.gui.sifarnici.programtreninga;

import client.controller.KontrolerProgramTreninga;
import client.gui.FormMode;
import client.tablemodel.ProgramTreningaTableModel;
import common.domain.ProgramTreninga;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class ProgramTreningaForma extends JFrame implements FormMode {

    private final KontrolerProgramTreninga kontrolerProgram = new KontrolerProgramTreninga();

    private final ProgramTreningaTableModel tableModel = new ProgramTreningaTableModel();
    private final JTable tabela = new JTable(tableModel);

    private final JTextField poljeNazivKriterijum = new JTextField(15);

    private final JTextField poljeNaziv = new JTextField(20);
    private final JTextField poljeOpis = new JTextField(25);
    private final JTextField poljeBrojNedelja = new JTextField(5);

    private final JButton dugmeNova = new JButton("Nova");
    private final JButton dugmeIzmeni = new JButton("Izmeni");
    private final JButton dugmeObrisi = new JButton("Obrisi");
    private final JButton dugmeSacuvaj = new JButton("Sacuvaj");
    private final JButton dugmeOtkazi = new JButton("Otkazi");

    private int rezim = FORM_VIEW_MODE;
    private ProgramTreninga trenutni;

    public ProgramTreningaForma() {
        super("Teretana - Program treninga");
        postaviKomponente();
        postaviAkcije();
        osveziTabelu(new ProgramTreninga());
        postaviRezim(FORM_VIEW_MODE);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
    }

    private void postaviKomponente() {
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    obradiIzborRedaTabele();
                }
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, napraviPanelPretrage(), napraviPanelDetalja());
        splitPane.setResizeWeight(0.5);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    private JPanel napraviPanelPretrage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Pretraga"));

        JPanel kriterijumi = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 3, 3, 3);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        kriterijumi.add(new JLabel("Naziv:"), c);
        c.gridx = 1;
        kriterijumi.add(poljeNazivKriterijum, c);

        JButton dugmePretrazi = new JButton("Pretrazi");
        JButton dugmeSve = new JButton("Prikazi sve");
        dugmePretrazi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pretrazi();
            }
        });
        dugmeSve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                osveziTabelu(new ProgramTreninga());
            }
        });
        c.gridx = 2;
        kriterijumi.add(dugmePretrazi, c);
        c.gridx = 3;
        kriterijumi.add(dugmeSve, c);

        panel.add(kriterijumi, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        return panel;
    }

    private JPanel napraviPanelDetalja() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Detalji programa treninga"));

        JPanel polja = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        polja.add(new JLabel("Naziv:"), c);
        c.gridx = 1;
        polja.add(poljeNaziv, c);

        c.gridx = 0;
        c.gridy = 1;
        polja.add(new JLabel("Opis:"), c);
        c.gridx = 1;
        polja.add(poljeOpis, c);

        c.gridx = 0;
        c.gridy = 2;
        polja.add(new JLabel("Broj nedelja:"), c);
        c.gridx = 1;
        polja.add(poljeBrojNedelja, c);

        JPanel dugmad = new JPanel();
        dugmad.add(dugmeNova);
        dugmad.add(dugmeIzmeni);
        dugmad.add(dugmeObrisi);
        dugmad.add(dugmeSacuvaj);
        dugmad.add(dugmeOtkazi);

        panel.add(polja, BorderLayout.CENTER);
        panel.add(dugmad, BorderLayout.SOUTH);
        return panel;
    }

    private void postaviAkcije() {
        dugmeNova.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nova();
            }
        });
        dugmeIzmeni.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                postaviRezim(FORM_EDIT_MODE);
            }
        });
        dugmeObrisi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrisi();
            }
        });
        dugmeSacuvaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sacuvaj();
            }
        });
        dugmeOtkazi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                otkazi();
            }
        });
    }

    private void pretrazi() {
        ProgramTreninga kriterijum = new ProgramTreninga();
        if (!poljeNazivKriterijum.getText().trim().isEmpty()) {
            kriterijum.setNaziv(poljeNazivKriterijum.getText().trim());
        }
        List<ProgramTreninga> rezultat = osveziTabelu(kriterijum);
        if (rezultat != null && !rezultat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sistem je nasao programe treninga po zadatim kriterijumima.",
                    "Pronadjeno", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private List<ProgramTreninga> osveziTabelu(ProgramTreninga kriterijum) {
        try {
            List<ProgramTreninga> rezultat = kontrolerProgram.vratiListu(kriterijum);
            tableModel.setProgrami(rezultat);
            return rezultat;
        } catch (Exception ex) {
            prikaziGresku(ex);
            return null;
        }
    }

    private void obradiIzborRedaTabele() {
        int red = tabela.getSelectedRow();
        if (red < 0) {
            return;
        }
        trenutni = tableModel.getProgramNaIndeksu(red);
        popuniDetalje(trenutni);
        postaviRezim(FORM_VIEW_MODE);
        JOptionPane.showMessageDialog(this, "Sistem je nasao program treninga.", "Pronadjeno", JOptionPane.INFORMATION_MESSAGE);
    }

    private void nova() {
        try {
            trenutni = kontrolerProgram.kreiraj();
            popuniDetalje(trenutni);
            postaviRezim(FORM_ADD_MODE);
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void sacuvaj() {
        try {
            procitajDetaljeUObjekat(trenutni);
            trenutni = kontrolerProgram.promeni(trenutni);
            JOptionPane.showMessageDialog(this, "Sistem je zapamtio program treninga.", "Sacuvano", JOptionPane.INFORMATION_MESSAGE);
            popuniDetalje(trenutni);
            postaviRezim(FORM_VIEW_MODE);
            osveziTabelu(new ProgramTreninga());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Broj nedelja mora biti ceo broj.", "Greska", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void obrisi() {
        if (trenutni == null || trenutni.getId() == null) {
            return;
        }
        int potvrda = JOptionPane.showConfirmDialog(this, "Obrisati izabrani program treninga?", "Potvrda", JOptionPane.YES_NO_OPTION);
        if (potvrda != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            kontrolerProgram.obrisi(trenutni);
            JOptionPane.showMessageDialog(this, "Sistem je obrisao program treninga.", "Obrisano", JOptionPane.INFORMATION_MESSAGE);
            trenutni = null;
            obrisiDetalje();
            postaviRezim(FORM_VIEW_MODE);
            osveziTabelu(new ProgramTreninga());
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void otkazi() {
        if (tabela.getSelectedRow() >= 0) {
            trenutni = tableModel.getProgramNaIndeksu(tabela.getSelectedRow());
            popuniDetalje(trenutni);
        } else {
            trenutni = null;
            obrisiDetalje();
        }
        postaviRezim(FORM_VIEW_MODE);
    }

    private void popuniDetalje(ProgramTreninga p) {
        poljeNaziv.setText(p.getNaziv() != null ? p.getNaziv() : "");
        poljeOpis.setText(p.getOpis() != null ? p.getOpis() : "");
        poljeBrojNedelja.setText(p.getBrojNedelja() != null ? String.valueOf(p.getBrojNedelja()) : "");
    }

    private void obrisiDetalje() {
        poljeNaziv.setText("");
        poljeOpis.setText("");
        poljeBrojNedelja.setText("");
    }

    private void procitajDetaljeUObjekat(ProgramTreninga p) {
        p.setNaziv(poljeNaziv.getText().trim());
        p.setOpis(poljeOpis.getText().trim());
        p.setBrojNedelja(parsirajBrojNedelja(poljeBrojNedelja.getText()));
    }

    private Integer parsirajBrojNedelja(String tekst) {
        if (tekst == null || tekst.trim().isEmpty()) {
            return null;
        }
        return Integer.valueOf(tekst.trim());
    }

    private void postaviRezim(int noviRezim) {
        this.rezim = noviRezim;
        boolean uredjivo = noviRezim == FORM_ADD_MODE || noviRezim == FORM_EDIT_MODE;

        postaviUredjivost(poljeNaziv, uredjivo);
        postaviUredjivost(poljeOpis, uredjivo);
        postaviUredjivost(poljeBrojNedelja, uredjivo);

        dugmeNova.setEnabled(noviRezim == FORM_VIEW_MODE);
        dugmeIzmeni.setEnabled(noviRezim == FORM_VIEW_MODE && trenutni != null && trenutni.getId() != null);
        dugmeObrisi.setEnabled(noviRezim == FORM_VIEW_MODE && trenutni != null && trenutni.getId() != null);
        dugmeSacuvaj.setEnabled(uredjivo);
        dugmeOtkazi.setEnabled(uredjivo);
    }

    private void postaviUredjivost(JComponent komponenta, boolean uredjivo) {
        komponenta.setEnabled(uredjivo);
    }

    private void prikaziGresku(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
    }
}
