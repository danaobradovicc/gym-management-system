package client.gui.primalacusluge.clan;

import client.controller.KontrolerClan;
import client.controller.KontrolerKategorijaClanarine;
import client.gui.FormMode;
import client.tablemodel.ClanTableModel;
import common.domain.Clan;
import common.domain.KategorijaClanarine;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClanForma extends JFrame implements FormMode {

    private static final SimpleDateFormat FORMAT_DATUMA = new SimpleDateFormat("yyyy-MM-dd");

    private final KontrolerClan kontrolerClan = new KontrolerClan();
    private final KontrolerKategorijaClanarine kontrolerKategorija = new KontrolerKategorijaClanarine();

    private final ClanTableModel tableModel = new ClanTableModel();
    private final JTable tabela = new JTable(tableModel);

    
    private final JTextField poljeImeKriterijum = new JTextField(10);
    private final JTextField poljePrezimeKriterijum = new JTextField(10);
    private final JComboBox<KategorijaClanarine> comboKategorijaKriterijum = new JComboBox<KategorijaClanarine>();

    
    private final JTextField poljeIme = new JTextField(15);
    private final JTextField poljePrezime = new JTextField(15);
    private final JTextField poljeEmail = new JTextField(15);
    private final JTextField poljeDatumUclanjenja = new JTextField(10);
    private final JCheckBox poljeAktivan = new JCheckBox("Aktivan");
    private final JComboBox<KategorijaClanarine> comboKategorija = new JComboBox<KategorijaClanarine>();

    private final JButton dugmeNova = new JButton("Nova");
    private final JButton dugmeIzmeni = new JButton("Izmeni");
    private final JButton dugmeObrisi = new JButton("Obrisi");
    private final JButton dugmeSacuvaj = new JButton("Sacuvaj");
    private final JButton dugmeOtkazi = new JButton("Otkazi");

    private final List<KategorijaClanarine> sveKategorije = new ArrayList<KategorijaClanarine>();
    private final Map<Long, String> nazivKategorija = new HashMap<Long, String>();

    private int rezim = FORM_VIEW_MODE;
    private Clan trenutni;

    public ClanForma() {
        super("Teretana - Clan");
        postaviKomponente();
        postaviAkcije();
        ucitajKategorije();
        osveziTabelu(new Clan());
        postaviRezim(FORM_VIEW_MODE);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 580);
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
        kriterijumi.add(new JLabel("Kategorija:"), c);
        c.gridx = 5;
        kriterijumi.add(comboKategorijaKriterijum, c);

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
                osveziTabelu(new Clan());
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
        panel.setBorder(BorderFactory.createTitledBorder("Detalji clana"));

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
        polja.add(new JLabel("Email:"), c);
        c.gridx = 1;
        polja.add(poljeEmail, c);

        c.gridx = 0;
        c.gridy = 3;
        polja.add(new JLabel("Datum uclanjenja (yyyy-MM-dd):"), c);
        c.gridx = 1;
        polja.add(poljeDatumUclanjenja, c);

        c.gridx = 0;
        c.gridy = 4;
        polja.add(new JLabel("Kategorija clanarine:"), c);
        c.gridx = 1;
        polja.add(comboKategorija, c);

        c.gridx = 1;
        c.gridy = 5;
        polja.add(poljeAktivan, c);

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

    private void ucitajKategorije() {
        try {
            sveKategorije.clear();
            sveKategorije.addAll(kontrolerKategorija.vratiListuSvih());

            nazivKategorija.clear();
            for (KategorijaClanarine k : sveKategorije) {
                nazivKategorija.put(k.getId(), k.getNaziv());
            }
            tableModel.setNazivKategorija(nazivKategorija);

            comboKategorijaKriterijum.removeAllItems();
            comboKategorijaKriterijum.addItem(null);
            comboKategorija.removeAllItems();
            for (KategorijaClanarine k : sveKategorije) {
                comboKategorijaKriterijum.addItem(k);
                comboKategorija.addItem(k);
            }
            DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(javax.swing.JList list, Object value, int index,
                                                                boolean isSelected, boolean cellHasFocus) {
                    String tekst = value == null ? "-- bilo koja --" : ((KategorijaClanarine) value).getNaziv();
                    return super.getListCellRendererComponent(list, tekst, index, isSelected, cellHasFocus);
                }
            };
            comboKategorijaKriterijum.setRenderer(renderer);
            comboKategorija.setRenderer(renderer);
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void pretrazi() {
        Clan kriterijum = new Clan();
        if (!poljeImeKriterijum.getText().trim().isEmpty()) {
            kriterijum.setIme(poljeImeKriterijum.getText().trim());
        }
        if (!poljePrezimeKriterijum.getText().trim().isEmpty()) {
            kriterijum.setPrezime(poljePrezimeKriterijum.getText().trim());
        }
        KategorijaClanarine kategorijaKriterijum = (KategorijaClanarine) comboKategorijaKriterijum.getSelectedItem();
        if (kategorijaKriterijum != null) {
            kriterijum.setKategorijaClanarine(kategorijaKriterijum);
        }
        List<Clan> rezultat = osveziTabelu(kriterijum);
        if (rezultat != null && !rezultat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sistem je nasao clanove po zadatim kriterijumima.",
                    "Pronadjeno", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private List<Clan> osveziTabelu(Clan kriterijum) {
        try {
            List<Clan> rezultat = kontrolerClan.vratiListu(kriterijum);
            tableModel.setClanovi(rezultat);
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
        trenutni = tableModel.getClanNaIndeksu(red);
        popuniDetalje(trenutni);
        postaviRezim(FORM_VIEW_MODE);
        JOptionPane.showMessageDialog(this, "Sistem je nasao clana.", "Pronadjeno", JOptionPane.INFORMATION_MESSAGE);
    }

    private void nova() {
        try {
            trenutni = kontrolerClan.kreiraj();
            popuniDetalje(trenutni);
            postaviRezim(FORM_ADD_MODE);
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void sacuvaj() {
        try {
            procitajDetaljeUObjekat(trenutni);
            trenutni = kontrolerClan.promeni(trenutni);
            JOptionPane.showMessageDialog(this, "Sistem je zapamtio clana.", "Sacuvano", JOptionPane.INFORMATION_MESSAGE);
            popuniDetalje(trenutni);
            postaviRezim(FORM_VIEW_MODE);
            osveziTabelu(new Clan());
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void obrisi() {
        if (trenutni == null || trenutni.getId() == null) {
            return;
        }
        int potvrda = JOptionPane.showConfirmDialog(this, "Obrisati izabranog clana?", "Potvrda", JOptionPane.YES_NO_OPTION);
        if (potvrda != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            kontrolerClan.obrisi(trenutni);
            JOptionPane.showMessageDialog(this, "Sistem je obrisao clana.", "Obrisano", JOptionPane.INFORMATION_MESSAGE);
            trenutni = null;
            obrisiDetalje();
            postaviRezim(FORM_VIEW_MODE);
            osveziTabelu(new Clan());
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void otkazi() {
        if (tabela.getSelectedRow() >= 0) {
            trenutni = tableModel.getClanNaIndeksu(tabela.getSelectedRow());
            popuniDetalje(trenutni);
        } else {
            trenutni = null;
            obrisiDetalje();
        }
        postaviRezim(FORM_VIEW_MODE);
    }

    private void popuniDetalje(Clan c) {
        poljeIme.setText(c.getIme() != null ? c.getIme() : "");
        poljePrezime.setText(c.getPrezime() != null ? c.getPrezime() : "");
        poljeEmail.setText(c.getEmail() != null ? c.getEmail() : "");
        poljeDatumUclanjenja.setText(formatirajDatum(c.getDatumUclanjenja()));
        poljeAktivan.setSelected(c.getAktivan() != null && c.getAktivan());
        comboKategorija.setSelectedItem(c.getKategorijaClanarine() != null ? pronadjiKategoriju(c.getKategorijaClanarine().getId()) : null);
    }

    private void obrisiDetalje() {
        poljeIme.setText("");
        poljePrezime.setText("");
        poljeEmail.setText("");
        poljeDatumUclanjenja.setText("");
        poljeAktivan.setSelected(false);
        comboKategorija.setSelectedItem(null);
    }

    private void procitajDetaljeUObjekat(Clan c) throws ParseException {
        c.setIme(poljeIme.getText().trim());
        c.setPrezime(poljePrezime.getText().trim());
        c.setEmail(poljeEmail.getText().trim());
        c.setDatumUclanjenja(parsirajDatum(poljeDatumUclanjenja.getText()));
        c.setAktivan(poljeAktivan.isSelected());
        c.setKategorijaClanarine((KategorijaClanarine) comboKategorija.getSelectedItem());
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

    private KategorijaClanarine pronadjiKategoriju(Long id) {
        for (KategorijaClanarine k : sveKategorije) {
            if (k.getId().equals(id)) {
                return k;
            }
        }
        return null;
    }

    private void postaviRezim(int noviRezim) {
        this.rezim = noviRezim;
        boolean uredjivo = noviRezim == FORM_ADD_MODE || noviRezim == FORM_EDIT_MODE;

        postaviUredjivost(poljeIme, uredjivo);
        postaviUredjivost(poljePrezime, uredjivo);
        postaviUredjivost(poljeEmail, uredjivo);
        postaviUredjivost(poljeDatumUclanjenja, uredjivo);
        poljeAktivan.setEnabled(uredjivo);
        comboKategorija.setEnabled(uredjivo);

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
