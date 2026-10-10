package server.gui;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;


public class ServerFrame extends JFrame {

    private static final String DB_PROPERTIES_PUTANJA = "src/server/config/db.properties";

    public ServerFrame(int port) {
        super("Teretana - Server");

        JLabel labelaStatus = new JLabel("Server slusa na portu " + port + ".", SwingConstants.CENTER);
        labelaStatus.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        setJMenuBar(napraviMeni());
        getContentPane().add(labelaStatus, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 220);
        setLocationRelativeTo(null);
    }

    private JMenuBar napraviMeni() {
        JMenuBar meni = new JMenuBar();

        JMenu podesavanja = new JMenu("Podesavanja");
        JMenuItem dbKredencijali = new JMenuItem("DB kredencijali");
        dbKredencijali.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                otvoriDijalogZaKredencijale();
            }
        });
        podesavanja.add(dbKredencijali);
        meni.add(podesavanja);

        return meni;
    }

    private void otvoriDijalogZaKredencijale() {
        Properties svojstva = ucitajSvojstva();

        JTextField poljeUrl = new JTextField(svojstva.getProperty("db.url", ""), 30);
        JTextField poljeKorisnik = new JTextField(svojstva.getProperty("db.user", ""), 20);
        JPasswordField poljeLozinka = new JPasswordField(svojstva.getProperty("db.password", ""), 20);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("URL:"), c);
        c.gridx = 1;
        panel.add(poljeUrl, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Korisnik:"), c);
        c.gridx = 1;
        panel.add(poljeKorisnik, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(new JLabel("Lozinka:"), c);
        c.gridx = 1;
        panel.add(poljeLozinka, c);

        int rezultat = JOptionPane.showConfirmDialog(this, panel, "DB kredencijali",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (rezultat != JOptionPane.OK_OPTION) {
            return;
        }

        svojstva.setProperty("db.url", poljeUrl.getText().trim());
        svojstva.setProperty("db.user", poljeKorisnik.getText().trim());
        svojstva.setProperty("db.password", new String(poljeLozinka.getPassword()));

        if (sacuvajSvojstva(svojstva)) {
            JOptionPane.showMessageDialog(this,
                    "Kredencijali sacuvani u " + DB_PROPERTIES_PUTANJA + ".\n"
                            + "Promene vaze ODMAH, za sledecu operaciju - DataSource cita ovaj fajl "
                            + "iznova pri svakom pristupu bazi (nema potrebe za restartom servera).",
                    "Sacuvano", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private Properties ucitajSvojstva() {
        Properties svojstva = new Properties();
        File fajl = new File(DB_PROPERTIES_PUTANJA);
        if (fajl.exists()) {
            try (FileInputStream ulazniTok = new FileInputStream(fajl)) {
                svojstva.load(ulazniTok);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Greska pri citanju " + DB_PROPERTIES_PUTANJA + ": " + e.getMessage(),
                        "Greska", JOptionPane.ERROR_MESSAGE);
            }
        }
        return svojstva;
    }

    private boolean sacuvajSvojstva(Properties svojstva) {
        File fajl = new File(DB_PROPERTIES_PUTANJA);
        try (FileOutputStream izlazniTok = new FileOutputStream(fajl)) {
            svojstva.store(izlazniTok, "Azurirano preko ServerFrame - Podesavanja - DB kredencijali");
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Greska pri upisu " + DB_PROPERTIES_PUTANJA + ": " + e.getMessage(),
                    "Greska", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    //db.properties se prepisuje
}
