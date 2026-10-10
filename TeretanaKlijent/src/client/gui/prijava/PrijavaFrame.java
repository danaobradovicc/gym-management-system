package client.gui.prijava;

import client.controller.KontrolerPrijava;
import client.gui.GlavnaForma;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PrijavaFrame extends JFrame {

    private static final String HOST = "localhost";
    private static final int PORT = 5555;

    private final JTextField poljeKorisnickoIme = new JTextField(20);
    private final JPasswordField poljeSifra = new JPasswordField(20);
    private final JButton dugmePrijava = new JButton("Prijavi se");

    private final KontrolerPrijava kontrolerPrijava = new KontrolerPrijava();

    public PrijavaFrame() {
        super("Teretana - prijava");
        postaviKomponente();

        dugmePrijava.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prijaviSe();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void postaviKomponente() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Korisnicko ime:"), c);
        c.gridx = 1;
        panel.add(poljeKorisnickoIme, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Sifra:"), c);
        c.gridx = 1;
        panel.add(poljeSifra, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        panel.add(dugmePrijava, c);

        getContentPane().add(panel);
    }

   
    private void prijaviSe() {
        String korisnickoIme = poljeKorisnickoIme.getText();
        String sifra = new String(poljeSifra.getPassword());

        try {
            kontrolerPrijava.prijaviSe(HOST, PORT, korisnickoIme, sifra);//prosledjuje kontroleru
            JOptionPane.showMessageDialog(this, "Korisnicko ime i sifra su ispravni.",
                    "Prijava uspesna", JOptionPane.INFORMATION_MESSAGE);
            dispose();//unistava se prozor
            new GlavnaForma().setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Greska pri prijavi", JOptionPane.ERROR_MESSAGE);
        }
    }
}
