package client.gui.pruzalacusluge.trener;

import client.controller.KontrolerTrener;
import client.gui.FormMode;
import client.tablemodel.TrenerTableModel;
import common.domain.Trener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class TrenerForma extends JFrame implements FormMode {

    private static final SimpleDateFormat FORMAT_DATUMA = new SimpleDateFormat("yyyy-MM-dd");

    private final KontrolerTrener kontrolerTrener = new KontrolerTrener();

    private final TrenerTableModel tableModel = new TrenerTableModel();
    private final JTable tabela = new JTable(tableModel);

    
    private final JTextField poljeImeKriterijum = new JTextField(10);
    private final JTextField poljePrezimeKriterijum = new JTextField(10);
    private final JTextField poljeKorisnickoImeKriterijum = new JTextField(10);

    
    private final JTextField poljeIme = new JTextField(15);
    private final JTextField poljePrezime = new JTextField(15);
    private final JTextField poljeKorisnickoIme = new JTextField(15);
    private final JPasswordField poljeSifra = new JPasswordField(15);
    private final JTextField poljeDatumZaposlenja = new JTextField(10);

    private final JButton dugmeNova = new JButton("Nova");
    private final JButton dugmeIzmeni = new JButton("Izmeni");
    private final JButton dugmeObrisi = new JButton("Obrisi");
    private final JButton dugmeSacuvaj = new JButton("Sacuvaj");
    private final JButton dugmeOtkazi = new JButton("Otkazi");

    private int rezim = FORM_VIEW_MODE;
    private Trener trenutni;

    public TrenerForma() {
        super("Teretana - Trener");
        postaviKomponente();
        postaviAkcije();
        osveziTabelu(new Trener());
        postaviRezim(FORM_VIEW_MODE);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 550);
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
        kriterijumi.add(new JLabel("Ime:"), c);
        c.gridx = 1;
        kriterijumi.add(poljeImeKriterijum, c);
        c.gridx = 2;
        kriterijumi.add(new JLabel("Prezime:"), c);
        c.gridx = 3;
        kriterijumi.add(poljePrezimeKriterijum, c);
        c.gridx = 4;
        kriterijumi.add(new JLabel("Korisnicko ime:"), c);
        c.gridx = 5;
        kriterijumi.add(poljeKorisnickoImeKriterijum, c);

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
                osveziTabelu(new Trener());
            }
        });
        c.gridx = 6;
        kriterijumi.add(dugmePretrazi, c);
        c.gridx = 7;
        kriterijumi.add(dugmeSve, c);

        panel.add(kriterijumi, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        return panel;
    }

    private JPanel napraviPanelDetalja() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Detalji trenera"));

        JPanel polja = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        polja.add(new JLabel("Ime:"), c);
        c.gridx = 1;
        polja.add(poljeIme, c);

        c.gridx = 0;
        c.gridy = 1;
        polja.add(new JLabel("Prezime:"), c);
        c.gridx = 1;
        polja.add(poljePrezime, c);

        c.gridx = 0;
        c.gridy = 2;
        polja.add(new JLabel("Korisnicko ime:"), c);
        c.gridx = 1;
        polja.add(poljeKorisnickoIme, c);

        c.gridx = 0;
        c.gridy = 3;
        polja.add(new JLabel("Sifra:"), c);
        c.gridx = 1;
        polja.add(poljeSifra, c);

        c.gridx = 0;
        c.gridy = 4;
        polja.add(new JLabel("Datum zaposlenja (yyyy-MM-dd):"), c);
        c.gridx = 1;
        polja.add(poljeDatumZaposlenja, c);

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
        Trener kriterijum = new Trener();
        if (!poljeImeKriterijum.getText().trim().isEmpty()) {
            kriterijum.setIme(poljeImeKriterijum.getText().trim());
        }
        if (!poljePrezimeKriterijum.getText().trim().isEmpty()) {
            kriterijum.setPrezime(poljePrezimeKriterijum.getText().trim());
        }
        if (!poljeKorisnickoImeKriterijum.getText().trim().isEmpty()) {
            kriterijum.setKorisnickoIme(poljeKorisnickoImeKriterijum.getText().trim());
        }
        List<Trener> rezultat = osveziTabelu(kriterijum);
        if (rezultat != null && !rezultat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sistem je nasao trenere po zadatim kriterijumima.",
                    "Pronadjeno", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private List<Trener> osveziTabelu(Trener kriterijum) {
        try {
            List<Trener> rezultat = kontrolerTrener.vratiListu(kriterijum);
            tableModel.setTreneri(rezultat);
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
        trenutni = tableModel.getTrenerNaIndeksu(red);
        popuniDetalje(trenutni);
        postaviRezim(FORM_VIEW_MODE);
        JOptionPane.showMessageDialog(this, "Sistem je nasao trenera.", "Pronadjeno", JOptionPane.INFORMATION_MESSAGE);
    }

    private void nova() {
        try {
            trenutni = kontrolerTrener.kreiraj();
            popuniDetalje(trenutni);
            postaviRezim(FORM_ADD_MODE);
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void sacuvaj() {
        try {
            procitajDetaljeUObjekat(trenutni);
            trenutni = kontrolerTrener.promeni(trenutni);
            JOptionPane.showMessageDialog(this, "Sistem je zapamtio trenera.", "Sacuvano", JOptionPane.INFORMATION_MESSAGE);
            popuniDetalje(trenutni);
            postaviRezim(FORM_VIEW_MODE);
            osveziTabelu(new Trener());
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void obrisi() {
        if (trenutni == null || trenutni.getId() == null) {
            return;
        }
        int potvrda = JOptionPane.showConfirmDialog(this, "Obrisati izabranog trenera?", "Potvrda", JOptionPane.YES_NO_OPTION);
        if (potvrda != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            kontrolerTrener.obrisi(trenutni);
            JOptionPane.showMessageDialog(this, "Sistem je obrisao trenera.", "Obrisano", JOptionPane.INFORMATION_MESSAGE);
            trenutni = null;
            obrisiDetalje();
            postaviRezim(FORM_VIEW_MODE);
            osveziTabelu(new Trener());
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void otkazi() {
        if (tabela.getSelectedRow() >= 0) {
            trenutni = tableModel.getTrenerNaIndeksu(tabela.getSelectedRow());
            popuniDetalje(trenutni);
        } else {
            trenutni = null;
            obrisiDetalje();
        }
        postaviRezim(FORM_VIEW_MODE);
    }

    private void popuniDetalje(Trener t) {
        poljeIme.setText(t.getIme() != null ? t.getIme() : "");
        poljePrezime.setText(t.getPrezime() != null ? t.getPrezime() : "");
        poljeKorisnickoIme.setText(t.getKorisnickoIme() != null ? t.getKorisnickoIme() : "");
        poljeSifra.setText(t.getSifra() != null ? t.getSifra() : "");
        poljeDatumZaposlenja.setText(formatirajDatum(t.getDatumZaposlenja()));
    }

    private void obrisiDetalje() {
        poljeIme.setText("");
        poljePrezime.setText("");
        poljeKorisnickoIme.setText("");
        poljeSifra.setText("");
        poljeDatumZaposlenja.setText("");
    }

    private void procitajDetaljeUObjekat(Trener t) throws ParseException {
        t.setIme(poljeIme.getText().trim());
        t.setPrezime(poljePrezime.getText().trim());
        t.setKorisnickoIme(poljeKorisnickoIme.getText().trim());
        t.setSifra(new String(poljeSifra.getPassword()));
        t.setDatumZaposlenja(parsirajDatum(poljeDatumZaposlenja.getText()));
    }

    private Date parsirajDatum(String tekst) throws ParseException {
        if (tekst == null || tekst.trim().isEmpty()) {
            return null;
        }
        return new Date(FORMAT_DATUMA.parse(tekst.trim()).getTime());
    }

    private String formatirajDatum(Date datum) {
        return datum != null ? FORMAT_DATUMA.format(datum) : "";
    }

    private void postaviRezim(int noviRezim) {
        this.rezim = noviRezim;
        boolean uredjivo = noviRezim == FORM_ADD_MODE || noviRezim == FORM_EDIT_MODE;

        postaviUredjivost(poljeIme, uredjivo);
        postaviUredjivost(poljePrezime, uredjivo);
        postaviUredjivost(poljeKorisnickoIme, uredjivo);
        postaviUredjivost(poljeSifra, uredjivo);
        postaviUredjivost(poljeDatumZaposlenja, uredjivo);

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
