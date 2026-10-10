package client.gui.dokumenti.clanarina;

import client.controller.KontrolerClan;
import client.controller.KontrolerClanarina;
import client.controller.KontrolerTipTreninga;
import client.controller.KontrolerTrener;
import client.gui.FormMode;
import client.tablemodel.ClanarinaTableModel;
import client.tablemodel.TerminTreningaTableModel;
import common.domain.Clan;
import common.domain.Clanarina;
import common.domain.TerminTreninga;
import common.domain.TipTreninga;
import common.domain.Trener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClanarinaForma extends JFrame implements FormMode {

    private static final SimpleDateFormat FORMAT_DATUMA = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat FORMAT_VREMENA = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final KontrolerClanarina kontrolerClanarina = new KontrolerClanarina();
    private final KontrolerTrener kontrolerTrener = new KontrolerTrener();
    private final KontrolerClan kontrolerClan = new KontrolerClan();
    private final KontrolerTipTreninga kontrolerTipTreninga = new KontrolerTipTreninga();

    private final ClanarinaTableModel tableModel = new ClanarinaTableModel();
    private final JTable tabela = new JTable(tableModel);

    private final TerminTreningaTableModel terminModel = new TerminTreningaTableModel();
    private final JTable tabelaTermina = new JTable(terminModel);

    
    private final JComboBox<Trener> comboTrenerKriterijum = new JComboBox<Trener>();
    private final JComboBox<Clan> comboClanKriterijum = new JComboBox<Clan>();
    private final JTextField poljeStatusKriterijum = new JTextField(10);

    
    private final JTextField poljeDatumPocetka = new JTextField(10);
    private final JTextField poljeDatumZavrsetka = new JTextField(10);
    private final JTextField poljeCena = new JTextField(10);
    private final JTextField poljeStatus = new JTextField(15);
    private final JComboBox<Trener> comboTrener = new JComboBox<Trener>();
    private final JComboBox<Clan> comboClan = new JComboBox<Clan>();

    private final JButton dugmeNova = new JButton("Nova");
    private final JButton dugmeIzmeni = new JButton("Izmeni");
    private final JButton dugmeObrisi = new JButton("Obrisi");
    private final JButton dugmeSacuvaj = new JButton("Sacuvaj");
    private final JButton dugmeOtkazi = new JButton("Otkazi");


    private final JTextField poljeSala = new JTextField(10);
    private final JTextField poljeVremePocetka = new JTextField(14);
    private final JTextField poljeVremeZavrsetka = new JTextField(14);
    private final JTextField poljeMaxUcesnika = new JTextField(5);
    private final JComboBox<TipTreninga> comboTipTreninga = new JComboBox<TipTreninga>();
    private final JButton dugmeDodajStavku = new JButton("Dodaj stavku");
    private final JButton dugmeUkloniStavku = new JButton("Ukloni stavku");

    private final List<Trener> sviTreneri = new ArrayList<Trener>();
    private final List<Clan> sviClanovi = new ArrayList<Clan>();
    private final List<TipTreninga> sviTipoviTreninga = new ArrayList<TipTreninga>();
    private final Map<Long, String> nazivTrenera = new HashMap<Long, String>();
    private final Map<Long, String> nazivClanova = new HashMap<Long, String>();
    private final Map<Long, String> nazivTipova = new HashMap<Long, String>();


    private final List<TerminTreninga> lokalnaListaTermina = new ArrayList<TerminTreninga>();
    private int indeksTerminaZaIzmenu = -1;

    private int rezim = FORM_VIEW_MODE;
    private Clanarina trenutna;

    public ClanarinaForma() {
        super("Teretana - Clanarina");
        postaviKomponente();
        postaviAkcije();
        ucitajTrenereIClanove();
        ucitajTipoveTreninga();
        osveziTabelu(new Clanarina());
        postaviRezim(FORM_VIEW_MODE);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(750, 600);
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
        tabelaTermina.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaTermina.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    obradiIzborRedaTermina();
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
        kriterijumi.add(new JLabel("Trener:"), c);
        c.gridx = 1;
        kriterijumi.add(comboTrenerKriterijum, c);
        c.gridx = 2;
        kriterijumi.add(new JLabel("Clan:"), c);
        c.gridx = 3;
        kriterijumi.add(comboClanKriterijum, c);
        c.gridx = 4;
        kriterijumi.add(new JLabel("Status:"), c);
        c.gridx = 5;
        kriterijumi.add(poljeStatusKriterijum, c);

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
                osveziTabelu(new Clanarina());
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
        panel.setBorder(BorderFactory.createTitledBorder("Detalji clanarine"));

        JPanel polja = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        polja.add(new JLabel("Datum pocetka (yyyy-MM-dd):"), c);
        c.gridx = 1;
        polja.add(poljeDatumPocetka, c);

        c.gridx = 0;
        c.gridy = 1;
        polja.add(new JLabel("Datum zavrsetka (yyyy-MM-dd):"), c);
        c.gridx = 1;
        polja.add(poljeDatumZavrsetka, c);

        c.gridx = 0;
        c.gridy = 2;
        polja.add(new JLabel("Cena:"), c);
        c.gridx = 1;
        polja.add(poljeCena, c);

        c.gridx = 0;
        c.gridy = 3;
        polja.add(new JLabel("Status:"), c);
        c.gridx = 1;
        polja.add(poljeStatus, c);

        c.gridx = 0;
        c.gridy = 4;
        polja.add(new JLabel("Trener:"), c);
        c.gridx = 1;
        polja.add(comboTrener, c);

        c.gridx = 0;
        c.gridy = 5;
        polja.add(new JLabel("Clan:"), c);
        c.gridx = 1;
        polja.add(comboClan, c);

        JPanel dugmad = new JPanel();
        dugmad.add(dugmeNova);
        dugmad.add(dugmeIzmeni);
        dugmad.add(dugmeObrisi);
        dugmad.add(dugmeSacuvaj);
        dugmad.add(dugmeOtkazi);

        JPanel centralniDeo = new JPanel(new BorderLayout());
        centralniDeo.add(polja, BorderLayout.NORTH);
        centralniDeo.add(napraviPanelTermina(), BorderLayout.CENTER);

        panel.add(centralniDeo, BorderLayout.CENTER);
        panel.add(dugmad, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel napraviPanelTermina() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Termini treninga"));

        JPanel unos = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 3, 3, 3);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        unos.add(new JLabel("Sala:"), c);
        c.gridx = 1;
        unos.add(poljeSala, c);
        c.gridx = 2;
        unos.add(new JLabel("Vreme pocetka (yyyy-MM-dd HH:mm):"), c);
        c.gridx = 3;
        unos.add(poljeVremePocetka, c);
        c.gridx = 4;
        unos.add(new JLabel("Vreme zavrsetka (yyyy-MM-dd HH:mm):"), c);
        c.gridx = 5;
        unos.add(poljeVremeZavrsetka, c);

        c.gridx = 0;
        c.gridy = 1;
        unos.add(new JLabel("Max ucesnika:"), c);
        c.gridx = 1;
        unos.add(poljeMaxUcesnika, c);
        c.gridx = 2;
        unos.add(new JLabel("Tip treninga:"), c);
        c.gridx = 3;
        unos.add(comboTipTreninga, c);
        c.gridx = 4;
        unos.add(dugmeDodajStavku, c);
        c.gridx = 5;
        unos.add(dugmeUkloniStavku, c);

        panel.add(unos, BorderLayout.NORTH);
        panel.add(new JScrollPane(tabelaTermina), BorderLayout.CENTER);
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
        dugmeDodajStavku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dodajStavku();
            }
        });
        dugmeUkloniStavku.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ukloniStavku();
            }
        });
    }

    private void ucitajTrenereIClanove() {
        try {
            sviTreneri.clear();
            sviTreneri.addAll(kontrolerTrener.vratiListuSvih());
            sviClanovi.clear();
            sviClanovi.addAll(kontrolerClan.vratiListuSvih());

            nazivTrenera.clear();
            nazivClanova.clear();
            for (Trener t : sviTreneri) {
                nazivTrenera.put(t.getId(), t.getIme() + " " + t.getPrezime());
            }
            for (Clan c : sviClanovi) {
                nazivClanova.put(c.getId(), c.getIme() + " " + c.getPrezime());
            }
            tableModel.setNazivTrenera(nazivTrenera);
            tableModel.setNazivClanova(nazivClanova);

            napuniComboTrenere();
            napuniComboClanove();
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void napuniComboTrenere() {
        comboTrenerKriterijum.removeAllItems();
        comboTrenerKriterijum.addItem(null);
        comboTrener.removeAllItems();
        for (Trener t : sviTreneri) {
            comboTrenerKriterijum.addItem(t);
            comboTrener.addItem(t);
        }
        ListCellRendererImePrezime<Trener> renderer = new ListCellRendererImePrezime<Trener>() {
            @Override
            protected String tekst(Trener t) {
                return t.getIme() + " " + t.getPrezime();
            }
        };
        comboTrenerKriterijum.setRenderer(renderer);
        comboTrener.setRenderer(renderer);
    }

    private void napuniComboClanove() {
        comboClanKriterijum.removeAllItems();
        comboClanKriterijum.addItem(null);
        comboClan.removeAllItems();
        for (Clan c : sviClanovi) {
            comboClanKriterijum.addItem(c);
            comboClan.addItem(c);
        }
        ListCellRendererImePrezime<Clan> renderer = new ListCellRendererImePrezime<Clan>() {
            @Override
            protected String tekst(Clan c) {
                return c.getIme() + " " + c.getPrezime();
            }
        };
        comboClanKriterijum.setRenderer(renderer);
        comboClan.setRenderer(renderer);
    }

    private void ucitajTipoveTreninga() {
        try {
            sviTipoviTreninga.clear();
            sviTipoviTreninga.addAll(kontrolerTipTreninga.vratiListuSvih());

            nazivTipova.clear();
            for (TipTreninga t : sviTipoviTreninga) {
                nazivTipova.put(t.getId(), t.getNaziv());
            }
            terminModel.setNazivTipova(nazivTipova);

            napuniComboTipove();
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void napuniComboTipove() {
        comboTipTreninga.removeAllItems();
        for (TipTreninga t : sviTipoviTreninga) {
            comboTipTreninga.addItem(t);
        }
        comboTipTreninga.setRenderer(new ListCellRendererImePrezime<TipTreninga>() {
            @Override
            protected String tekst(TipTreninga t) {
                return t.getNaziv();
            }
        });
    }

    private void pretrazi() {
        Clanarina kriterijum = new Clanarina();
        Trener trenerKriterijum = (Trener) comboTrenerKriterijum.getSelectedItem();
        Clan clanKriterijum = (Clan) comboClanKriterijum.getSelectedItem();
        if (trenerKriterijum != null) {
            kriterijum.setTrener(trenerKriterijum);
        }
        if (clanKriterijum != null) {
            kriterijum.setClan(clanKriterijum);
        }
        if (!poljeStatusKriterijum.getText().trim().isEmpty()) {
            kriterijum.setStatus(poljeStatusKriterijum.getText().trim());
        }
        List<Clanarina> rezultat = osveziTabelu(kriterijum);
        if (rezultat != null && !rezultat.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sistem je nasao clanarine po zadatim kriterijumima.",
                    "Pronadjeno", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private List<Clanarina> osveziTabelu(Clanarina kriterijum) {
        try {
            List<Clanarina> rezultat = kontrolerClanarina.vratiListu(kriterijum);
            tableModel.setClanarine(rezultat);
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
        Clanarina izabrana = tableModel.getClanarinaNaIndeksu(red);
        try {
            trenutna = kontrolerClanarina.pretrazi(izabrana);
            popuniDetalje(trenutna);
            postaviRezim(FORM_VIEW_MODE);
            JOptionPane.showMessageDialog(this, "Sistem je nasao clanarinu.", "Pronadjeno", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void obradiIzborRedaTermina() {
        int red = tabelaTermina.getSelectedRow();
        if (red < 0) {
            resetIzmenuTermina();
            return;
        }
        TerminTreninga t = terminModel.getTerminNaIndeksu(red);
        poljeSala.setText(t.getSala() != null ? t.getSala() : "");
        poljeVremePocetka.setText(formatirajVreme(t.getDatumVremePocetka()));
        poljeVremeZavrsetka.setText(formatirajVreme(t.getDatumVremeZavrsetka()));
        poljeMaxUcesnika.setText(t.getMaxBrojUcesnika() != null ? String.valueOf(t.getMaxBrojUcesnika()) : "");
        comboTipTreninga.setSelectedItem(t.getTipTreninga() != null ? pronadjiTip(t.getTipTreninga().getId()) : null);
        indeksTerminaZaIzmenu = red;
        dugmeDodajStavku.setText("Izmeni stavku");
    }

    private void resetIzmenuTermina() {
        indeksTerminaZaIzmenu = -1;
        dugmeDodajStavku.setText("Dodaj stavku");
    }

    private void nova() {
        try {
            trenutna = kontrolerClanarina.kreiraj();
            popuniDetalje(trenutna);
            postaviRezim(FORM_ADD_MODE);
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void sacuvaj() {
        try {
            procitajDetaljeUObjekat(trenutna);
            trenutna = kontrolerClanarina.promeni(trenutna);
            JOptionPane.showMessageDialog(this, "Sistem je zapamtio clanarinu.", "Sacuvano", JOptionPane.INFORMATION_MESSAGE);
            popuniDetalje(trenutna);
            postaviRezim(FORM_VIEW_MODE);
            osveziTabelu(new Clanarina());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cena mora biti broj.", "Greska", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void obrisi() {
        if (trenutna == null || trenutna.getId() == null) {
            return;
        }
        int potvrda = JOptionPane.showConfirmDialog(this, "Obrisati izabranu clanarinu?", "Potvrda", JOptionPane.YES_NO_OPTION);
        if (potvrda != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            kontrolerClanarina.obrisi(trenutna);
            JOptionPane.showMessageDialog(this, "Sistem je obrisao clanarinu.", "Obrisano", JOptionPane.INFORMATION_MESSAGE);
            trenutna = null;
            obrisiDetalje();
            postaviRezim(FORM_VIEW_MODE);
            osveziTabelu(new Clanarina());
        } catch (Exception ex) {
            prikaziGresku(ex);
        }
    }

    private void otkazi() {
        if (tabela.getSelectedRow() >= 0) {
            trenutna = tableModel.getClanarinaNaIndeksu(tabela.getSelectedRow());
            popuniDetalje(trenutna);
        } else {
            trenutna = null;
            obrisiDetalje();
        }
        postaviRezim(FORM_VIEW_MODE);
    }

    private void popuniDetalje(Clanarina c) {
        poljeDatumPocetka.setText(formatirajDatum(c.getDatumPocetka()));
        poljeDatumZavrsetka.setText(formatirajDatum(c.getDatumZavrsetka()));
        poljeCena.setText(c.getCena() != null ? String.valueOf(c.getCena()) : "");
        poljeStatus.setText(c.getStatus() != null ? c.getStatus() : "");
        comboTrener.setSelectedItem(c.getTrener() != null ? pronadjiTrenera(c.getTrener().getId()) : null);
        comboClan.setSelectedItem(c.getClan() != null ? pronadjiClana(c.getClan().getId()) : null);

        lokalnaListaTermina.clear();
        if (c.getTerminiTreninga() != null) {
            lokalnaListaTermina.addAll(c.getTerminiTreninga());
        }
        terminModel.setTermini(lokalnaListaTermina);
        ocistiUnosTermina();
    }

    private void obrisiDetalje() {
        poljeDatumPocetka.setText("");
        poljeDatumZavrsetka.setText("");
        poljeCena.setText("");
        poljeStatus.setText("");
        comboTrener.setSelectedItem(null);
        comboClan.setSelectedItem(null);

        lokalnaListaTermina.clear();
        terminModel.setTermini(lokalnaListaTermina);
        ocistiUnosTermina();
    }


    private void procitajDetaljeUObjekat(Clanarina c) throws ParseException {
        c.setDatumPocetka(parsirajDatum(poljeDatumPocetka.getText()));
        c.setDatumZavrsetka(parsirajDatum(poljeDatumZavrsetka.getText()));
        c.setCena(parsirajCenu(poljeCena.getText()));
        c.setStatus(poljeStatus.getText().trim());
        c.setTrener((Trener) comboTrener.getSelectedItem());
        c.setClan((Clan) comboClan.getSelectedItem());
        c.setTerminiTreninga(new ArrayList<TerminTreninga>(lokalnaListaTermina));
    }

    private void dodajStavku() {
        String sala = poljeSala.getText().trim();
        if (sala.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sala je obavezna.", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Timestamp pocetak;
        Timestamp zavrsetak;
        try {
            pocetak = parsirajVreme(poljeVremePocetka.getText());
            zavrsetak = parsirajVreme(poljeVremeZavrsetka.getText());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Vreme mora biti u formatu yyyy-MM-dd HH:mm.", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (pocetak == null || zavrsetak == null) {
            JOptionPane.showMessageDialog(this, "Vreme pocetka i vreme zavrsetka su obavezni.", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!zavrsetak.after(pocetak)) {
            JOptionPane.showMessageDialog(this, "Vreme zavrsetka mora biti posle vremena pocetka.", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Integer maxUcesnika;
        try {
            maxUcesnika = Integer.valueOf(poljeMaxUcesnika.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Max broj ucesnika mora biti ceo broj.", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (maxUcesnika <= 0) {
            JOptionPane.showMessageDialog(this, "Max broj ucesnika mora biti veci od 0.", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }
        TipTreninga tip = (TipTreninga) comboTipTreninga.getSelectedItem();
        if (tip == null) {
            JOptionPane.showMessageDialog(this, "Tip treninga je obavezan.", "Greska", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (indeksTerminaZaIzmenu >= 0 && indeksTerminaZaIzmenu < lokalnaListaTermina.size()) {
            TerminTreninga postojeci = lokalnaListaTermina.get(indeksTerminaZaIzmenu);
            postojeci.setSala(sala);
            postojeci.setDatumVremePocetka(pocetak);
            postojeci.setDatumVremeZavrsetka(zavrsetak);
            postojeci.setMaxBrojUcesnika(maxUcesnika);
            postojeci.setTipTreninga(tip);
        } else {
            TerminTreninga noviTermin = new TerminTreninga();
            noviTermin.setSala(sala);
            noviTermin.setDatumVremePocetka(pocetak);
            noviTermin.setDatumVremeZavrsetka(zavrsetak);
            noviTermin.setMaxBrojUcesnika(maxUcesnika);
            noviTermin.setTipTreninga(tip);
            lokalnaListaTermina.add(noviTermin);
        }

        terminModel.setTermini(lokalnaListaTermina);
        ocistiUnosTermina();
    }

    private void ukloniStavku() {
        int red = tabelaTermina.getSelectedRow();
        if (red < 0) {
            return;
        }
        lokalnaListaTermina.remove(red);
        terminModel.setTermini(lokalnaListaTermina);
        ocistiUnosTermina();
    }

    private void ocistiUnosTermina() {
        poljeSala.setText("");
        poljeVremePocetka.setText("");
        poljeVremeZavrsetka.setText("");
        poljeMaxUcesnika.setText("");
        comboTipTreninga.setSelectedItem(null);
        tabelaTermina.clearSelection();
        resetIzmenuTermina();
    }

    private Timestamp parsirajVreme(String tekst) throws ParseException {
        if (tekst == null || tekst.trim().isEmpty()) {
            return null;
        }
        return new Timestamp(FORMAT_VREMENA.parse(tekst.trim()).getTime());
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

    private String formatirajVreme(Timestamp vreme) {
        return vreme != null ? FORMAT_VREMENA.format(vreme) : "";
    }

    private Double parsirajCenu(String tekst) {
        if (tekst == null || tekst.trim().isEmpty()) {
            return null;
        }
        return Double.valueOf(tekst.trim().replace(',', '.'));
    }

    private Trener pronadjiTrenera(Long id) {
        for (Trener t : sviTreneri) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    private Clan pronadjiClana(Long id) {
        for (Clan c : sviClanovi) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    private TipTreninga pronadjiTip(Long id) {
        for (TipTreninga t : sviTipoviTreninga) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    
    private void postaviRezim(int noviRezim) {
        this.rezim = noviRezim;
        boolean uredjivo = noviRezim == FORM_ADD_MODE || noviRezim == FORM_EDIT_MODE;

        postaviUredjivost(poljeDatumPocetka, uredjivo);
        postaviUredjivost(poljeDatumZavrsetka, uredjivo);
        postaviUredjivost(poljeCena, uredjivo);
        postaviUredjivost(poljeStatus, uredjivo);
        comboTrener.setEnabled(uredjivo);
        comboClan.setEnabled(uredjivo);

        postaviUredjivost(poljeSala, uredjivo);
        postaviUredjivost(poljeVremePocetka, uredjivo);
        postaviUredjivost(poljeVremeZavrsetka, uredjivo);
        postaviUredjivost(poljeMaxUcesnika, uredjivo);
        comboTipTreninga.setEnabled(uredjivo);
        dugmeDodajStavku.setEnabled(uredjivo);
        dugmeUkloniStavku.setEnabled(uredjivo);

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

    private abstract static class ListCellRendererImePrezime<T> extends DefaultListCellRenderer {
        @Override
        @SuppressWarnings("unchecked")
        public Component getListCellRendererComponent(javax.swing.JList list, Object value, int index,
                                                        boolean isSelected, boolean cellHasFocus) {
            String tekst = value == null ? "-- bilo koji --" : tekst((T) value);
            return super.getListCellRendererComponent(list, tekst, index, isSelected, cellHasFocus);
        }

        protected abstract String tekst(T vrednost);
    }
}
