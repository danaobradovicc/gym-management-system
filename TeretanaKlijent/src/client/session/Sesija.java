package client.session;

import common.domain.Trener;


public class Sesija {

    private static Sesija instance;

    private Trener prijavljeniTrener;

    private Sesija() {
    }

    public static synchronized Sesija getInstance() {
        if (instance == null) {
            instance = new Sesija();
        }
        return instance;
    }

    public Trener getPrijavljeniTrener() {
        return prijavljeniTrener;
    }

    public void setPrijavljeniTrener(Trener prijavljeniTrener) {
        this.prijavljeniTrener = prijavljeniTrener;
    }

    public boolean jePrijavljen() {
        return prijavljeniTrener != null;
    }
}
