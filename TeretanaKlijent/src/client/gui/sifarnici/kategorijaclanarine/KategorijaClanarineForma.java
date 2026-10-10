package client.gui.sifarnici.kategorijaclanarine;

import client.controller.KontrolerKategorijaClanarine;
import client.gui.FormMode;
import client.tablemodel.KategorijaClanarineTableModel;
import common.domain.KategorijaClanarine;

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


public class KategorijaClanarineForma extends JFrame implements FormMode {

    private final KontrolerKategorijaClanarine kontrolerKategorija = new KontrolerKategorijaClanarine();

    private final KategorijaClanarineTableModel tableModel = new KategorijaClanarineTableModel();
    private final JTable tabela = new JTable(tableModel);

    private final JTextField poljeNazivKriterijum = new JTextField(15);
    private final JTextField poljeNaziv = new JTextField(20);

    private final JButton dugmeNova = new JButton("Nova");
    private final JButton dugmeIzmeni = new JButton("Izmeni");
    private final JButton dugmeObrisi = new JButton("Obrisi");
    private final JButton dugmeSacuvaj = new JButton("Sacuvaj");
    private final JButton dugmeOtkazi = new JButton("Otkazi");

    private int rezim = FORM_VIEW_MODE;
    private KategorijaClanarine trenutna;

    public KategorijaClanarineForma() {
        super("Teretana - Kategorija clanarine");
        postaviKomponente();
        postaviAkcije();
        osveziTabelu(new KategorijaClanarine());
        postaviRezim(FORM_VIEW_MODE);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 480);
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
        splitPane.setResizeWeight(0.6);

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
                osveziTabelu(new KategorijaClanarine());
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
        panel.setBorder(BorderFactory.createTitledBorder("Detalji kategorije clanarine"));

        JPanel polja = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        polja.add(new JLabel("Naziv:"), c);
        c.gridx = 1;
        polja.add(poljeNaziv, c);

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
        KategorijaClanarine kriterijum = new KategorijaClanarine();
        if (!poljeNazivKriterijum.getText().trim().isEmpty()) {
            kriterijum.setNaziv(poljeNazivKriterijum.getText().trim());
        }
        List<KategorijaClanarine> rezultat = osveziTabelu(kriterijum);
        if (rezultat != null && !rezultat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sistem je nasao kategorije clanarine po zadatim kriterijumima.",
                    "Pronadjeno", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private List<KategorijaClanarine> osveziTabelu(KategorijaClanarine kriterijum) {
        try {
            List<KategorijaClanarine> rezultat = kontrolerKategorija.vratiListu(kriterijum);
            tableModel.setKategorije(rezultat);
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
        trenutna = tableModel.getKategorijaNaIndeksu(red);
        popuniDetalje(trenutna);
        postaviRezim(FORM_VIEW_MODE);
        JOptionPane.showMessageDialog(this, "Sistem je nasao kategoriju clanarine.", "Pronadjeno", JOptionPane.INFORMATION_MESSAGE);
    }

    private void nova() {
        try {
            trenutna = kontrolerKategorija.kreiraj();
            popuniDetalje(trenutna);
            postaviRezim(FORM_ADD_MODE);
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void sacuvaj() {
        try {
            procitajDetaljeUObjekat(trenutna);
            trenutna = kontrolerKategorija.promeni(trenutna);
            JOptionPane.showMessageDialog(this, "Sistem je zapamtio kategoriju clanarine.", "Sacuvano", JOptionPane.INFORMATION_MESSAGE);
            popuniDetalje(trenutna);
            postaviRezim(FORM_VIEW_MODE);
            osveziTabelu(new KategorijaClanarine());
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void obrisi() {
        if (trenutna == null || trenutna.getId() == null) {
            return;
        }
        int potvrda = JOptionPane.showConfirmDialog(this, "Obrisati izabranu kategoriju clanarine?", "Potvrda", JOptionPane.YES_NO_OPTION);
        if (potvrda != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            kontrolerKategorija.obrisi(trenutna);
            JOptionPane.showMessageDialog(this, "Sistem je obrisao kategoriju clanarine.", "Obrisano", JOptionPane.INFORMATION_MESSAGE);
            trenutna = null;
            obrisiDetalje();
            postaviRezim(FORM_VIEW_MODE);
            osveziTabelu(new KategorijaClanarine());
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void otkazi() {
        if (tabela.getSelectedRow() >= 0) {
            trenutna = tableModel.getKategorijaNaIndeksu(tabela.getSelectedRow());
            popuniDetalje(trenutna);
        } else {
            trenutna = null;
            obrisiDetalje();
        }
        postaviRezim(FORM_VIEW_MODE);
    }

    private void popuniDetalje(KategorijaClanarine k) {
        poljeNaziv.setText(k.getNaziv() != null ? k.getNaziv() : "");
    }

    private void obrisiDetalje() {
        poljeNaziv.setText("");
    }

    private void procitajDetaljeUObjekat(KategorijaClanarine k) {
        k.setNaziv(poljeNaziv.getText().trim());
    }

    private void postaviRezim(int noviRezim) {
        this.rezim = noviRezim;
        boolean uredjivo = noviRezim == FORM_ADD_MODE || noviRezim == FORM_EDIT_MODE;

        postaviUredjivost(poljeNaziv, uredjivo);

        dugmeNova.setEnabled(noviRezim == FORM_VIEW_MODE);
        dugmeIzmeni.setEnabled(noviRezim == FORM_VIEW_MODE && trenutna != null && trenutna.getId() != null);
        dugmeObrisi.setEnabled(noviRezim == FORM_VIEW_MODE && trenutna != null && trenutna.getId() != null);
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
