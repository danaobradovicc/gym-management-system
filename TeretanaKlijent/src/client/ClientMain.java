package client;

import client.gui.prijava.PrijavaFrame;

import javax.swing.SwingUtilities;


public class ClientMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PrijavaFrame().setVisible(true);
            }
        });
    }
}
