package client.gui;

import client.gui.dokumenti.clanarina.ClanarinaForma;
import client.gui.primalacusluge.clan.ClanForma;
import client.gui.pruzalacusluge.trener.TrenerForma;
import client.gui.sifarnici.kategorijaclanarine.KategorijaClanarineForma;
import client.gui.sifarnici.programtreninga.ProgramTreningaForma;
import client.gui.sifarnici.tiptreninga.TipTreningaForma;
import client.session.Sesija;
import common.domain.Trener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GlavnaForma extends JFrame {

    public GlavnaForma() {
        super("Teretana");

        Trener prijavljeniTrener = Sesija.getInstance().getPrijavljeniTrener();
        JLabel labela = new JLabel(
                "Prijavljeni ste kao: " + prijavljeniTrener.getIme() + " " + prijavljeniTrener.getPrezime(),
                SwingConstants.CENTER);
        labela.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        setJMenuBar(napraviMeni());
        getContentPane().add(labela, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private JMenuBar napraviMeni() {
        JMenuBar meni = new JMenuBar();

        JMenu dokumenti = new JMenu("Dokumenti");
        JMenuItem clanarinaStavka = new JMenuItem("Clanarina");
        clanarinaStavka.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClanarinaForma().setVisible(true);
            }
        });
        dokumenti.add(clanarinaStavka);
        meni.add(dokumenti);

        JMenu pruzalacUsluge = new JMenu("Pruzalac usluge");
        JMenuItem trenerStavka = new JMenuItem("Trener");
        trenerStavka.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TrenerForma().setVisible(true);
            }
        });
        pruzalacUsluge.add(trenerStavka);
        meni.add(pruzalacUsluge);

        JMenu primalacUsluge = new JMenu("Primalac usluge");
        JMenuItem clanStavka = new JMenuItem("Clan");
        clanStavka.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClanForma().setVisible(true);
            }
        });
        primalacUsluge.add(clanStavka);
        meni.add(primalacUsluge);

        JMenu sifarnici = new JMenu("Sifarnici");
        JMenuItem kategorijaClanarineStavka = new JMenuItem("Kategorija clanarine");
        kategorijaClanarineStavka.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new KategorijaClanarineForma().setVisible(true);
            }
        });
        sifarnici.add(kategorijaClanarineStavka);

        JMenuItem programTreningaStavka = new JMenuItem("Program treninga");
        programTreningaStavka.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProgramTreningaForma().setVisible(true);
            }
        });
        sifarnici.add(programTreningaStavka);

        JMenuItem tipTreningaStavka = new JMenuItem("Tip treninga");
        tipTreningaStavka.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TipTreningaForma().setVisible(true);
            }
        });
        sifarnici.add(tipTreningaStavka);

        meni.add(sifarnici);

        JMenuItem podesavanja = new JMenuItem("Podesavanja softverskog sistema");
        podesavanja.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GlavnaForma.this, "Podesavanja jos uvek nisu implementirana.",
                        "Podesavanja", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        meni.add(podesavanja);

        JMenuItem oProgramu = new JMenuItem("O programu");
        oProgramu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GlavnaForma.this,
                        "Teretana - sistem za upravljanje clanarinama\nSeminarski rad, Projektovanje softvera",
                        "O programu", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        meni.add(oProgramu);

        return meni;
    }
}
