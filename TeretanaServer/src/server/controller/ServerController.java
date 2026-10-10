package server.controller;

import common.communication.Operation;
import common.domain.DomenskiObjekat;
import server.so.AbstractSystemOperation;
import server.so.clan.SOKreirajClan;
import server.so.clan.SOObrisiClan;
import server.so.clan.SOPretraziClan;
import server.so.clan.SOPromeniClan;
import server.so.clan.SOVratiListuClanPoKriterijumuClan;
import server.so.clan.SOVratiListuClanPoKriterijumuKategorijaClanarine;
import server.so.clan.SOVratiListuSviClan;
import server.so.clanarina.SOKreirajClanarina;
import server.so.clanarina.SOObrisiClanarina;
import server.so.clanarina.SOPretraziClanarina;
import server.so.clanarina.SOPromeniClanarina;
import server.so.clanarina.SOVratiListuClanarinaPoKriterijumuClan;
import server.so.clanarina.SOVratiListuClanarinaPoKriterijumuClanarina;
import server.so.clanarina.SOVratiListuClanarinaPoKriterijumuTrener;
import server.so.kategorijaclanarine.SOKreirajKategorijaClanarine;
import server.so.kategorijaclanarine.SOObrisiKategorijaClanarine;
import server.so.kategorijaclanarine.SOPretraziKategorijaClanarine;
import server.so.kategorijaclanarine.SOPromeniKategorijaClanarine;
import server.so.kategorijaclanarine.SOVratiListuKategorijaClanarine;
import server.so.kategorijaclanarine.SOVratiListuSviKategorijaClanarine;
import server.so.prijava.SOPrijaviTrener;
import server.so.programtreninga.SOKreirajProgramTreninga;
import server.so.programtreninga.SOObrisiProgramTreninga;
import server.so.programtreninga.SOPretraziProgramTreninga;
import server.so.programtreninga.SOPromeniProgramTreninga;
import server.so.programtreninga.SOVratiListuProgramTreningaPoKriterijumuProgramTreninga;
import server.so.programtreninga.SOVratiListuSviProgramTreninga;
import server.so.tiptreninga.SOKreirajTipTreninga;
import server.so.tiptreninga.SOObrisiTipTreninga;
import server.so.tiptreninga.SOPretraziTipTreninga;
import server.so.tiptreninga.SOPromeniTipTreninga;
import server.so.tiptreninga.SOVratiListuSviTipTreninga;
import server.so.tiptreninga.SOVratiListuTipTreningaPoKriterijumuTipTreninga;
import server.so.trener.SOKreirajTrener;
import server.so.trener.SOObrisiTrener;
import server.so.trener.SOPretraziTrener;
import server.so.trener.SOPromeniTrener;
import server.so.trener.SOVratiListuSviTrener;
import server.so.trener.SOVratiListuTrenerPoKriterijumuTrener;


public class ServerController {

    private static ServerController instance;

    private ServerController() {
    }

    public static synchronized ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }

    public Object izvrsiOperaciju(Operation operacija, Object argument) throws Exception {
        AbstractSystemOperation sistemskaOperacija = napraviSistemskuOperaciju(operacija);
        return sistemskaOperacija.templateExecute((DomenskiObjekat) argument);
        //opsti object-domenskiObjekat
    }

   
    private AbstractSystemOperation napraviSistemskuOperaciju(Operation operacija) {
        switch (operacija) {
            //sve SO klase nasleđuju AbstractSystemOperation
            case PRIJAVI_TRENER:
                return new SOPrijaviTrener();

            case KREIRAJ_KATEGORIJA_CLANARINE:
                return new SOKreirajKategorijaClanarine();
            case PROMENI_KATEGORIJA_CLANARINE:
                return new SOPromeniKategorijaClanarine();
            case PRETRAZI_KATEGORIJA_CLANARINE:
                return new SOPretraziKategorijaClanarine();
            case OBRISI_KATEGORIJA_CLANARINE:
                return new SOObrisiKategorijaClanarine();
            case VRATI_LISTU_KATEGORIJA_CLANARINE_PO_KRITERIJUMU_KATEGORIJA_CLANARINE:
                return new SOVratiListuKategorijaClanarine();
            case VRATI_LISTU_SVI_KATEGORIJA_CLANARINE:
                return new SOVratiListuSviKategorijaClanarine();

            case KREIRAJ_TRENER:
                return new SOKreirajTrener();
            case PROMENI_TRENER:
                return new SOPromeniTrener();
            case PRETRAZI_TRENER:
                return new SOPretraziTrener();
            case OBRISI_TRENER:
                return new SOObrisiTrener();
            case VRATI_LISTU_TRENER_PO_KRITERIJUMU_TRENER:
                return new SOVratiListuTrenerPoKriterijumuTrener();
            case VRATI_LISTU_SVI_TRENER:
                return new SOVratiListuSviTrener();

            case KREIRAJ_CLAN:
                return new SOKreirajClan();
            case PROMENI_CLAN:
                return new SOPromeniClan();
            case PRETRAZI_CLAN:
                return new SOPretraziClan();
            case OBRISI_CLAN:
                return new SOObrisiClan();
            case VRATI_LISTU_CLAN_PO_KRITERIJUMU_CLAN:
                return new SOVratiListuClanPoKriterijumuClan();
            case VRATI_LISTU_CLAN_PO_KRITERIJUMU_KATEGORIJA_CLANARINE:
                return new SOVratiListuClanPoKriterijumuKategorijaClanarine();
            case VRATI_LISTU_SVI_CLAN:
                return new SOVratiListuSviClan();

            case KREIRAJ_CLANARINA:
                return new SOKreirajClanarina();
            case PROMENI_CLANARINA:
                return new SOPromeniClanarina();
            case PRETRAZI_CLANARINA:
                return new SOPretraziClanarina();
            case OBRISI_CLANARINA:
                return new SOObrisiClanarina();
            case VRATI_LISTU_CLANARINA_PO_KRITERIJUMU_CLAN:
                return new SOVratiListuClanarinaPoKriterijumuClan();
            case VRATI_LISTU_CLANARINA_PO_KRITERIJUMU_CLANARINA:
                return new SOVratiListuClanarinaPoKriterijumuClanarina();
            case VRATI_LISTU_CLANARINA_PO_KRITERIJUMU_TRENER:
                return new SOVratiListuClanarinaPoKriterijumuTrener();

            case KREIRAJ_TIP_TRENINGA:
                return new SOKreirajTipTreninga();
            case PROMENI_TIP_TRENINGA:
                return new SOPromeniTipTreninga();
            case PRETRAZI_TIP_TRENINGA:
                return new SOPretraziTipTreninga();
            case OBRISI_TIP_TRENINGA:
                return new SOObrisiTipTreninga();
            case VRATI_LISTU_TIP_TRENINGA_PO_KRITERIJUMU_TIP_TRENINGA:
                return new SOVratiListuTipTreningaPoKriterijumuTipTreninga();
            case VRATI_LISTU_SVI_TIP_TRENINGA:
                return new SOVratiListuSviTipTreninga();

            case KREIRAJ_PROGRAM_TRENINGA:
                return new SOKreirajProgramTreninga();
            case PROMENI_PROGRAM_TRENINGA:
                return new SOPromeniProgramTreninga();
            case PRETRAZI_PROGRAM_TRENINGA:
                return new SOPretraziProgramTreninga();
            case OBRISI_PROGRAM_TRENINGA:
                return new SOObrisiProgramTreninga();
            case VRATI_LISTU_PROGRAM_TRENINGA_PO_KRITERIJUMU_PROGRAM_TRENINGA:
                return new SOVratiListuProgramTreningaPoKriterijumuProgramTreninga();
            case VRATI_LISTU_SVI_PROGRAM_TRENINGA:
                return new SOVratiListuSviProgramTreninga();

            default:
                throw new UnsupportedOperationException(
                        "Sistemska operacija " + operacija + " jos uvek nije implementirana na serveru.");
        }
    }
}
