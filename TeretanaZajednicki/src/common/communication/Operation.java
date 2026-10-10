package common.communication;


public enum Operation {

    // 1. signal KreirajClan(Clan)
    KREIRAJ_CLAN,
    // 2. signal KreirajClanarina(Clanarina)
    KREIRAJ_CLANARINA,
    // 3. signal KreirajKategorijaClanarine(KategorijaClanarine)
    KREIRAJ_KATEGORIJA_CLANARINE,
    // 4. signal KreirajProgramTreninga(ProgramTreninga) - videti napomenu klase (SK22/SK23)
    KREIRAJ_PROGRAM_TRENINGA,
    // 5. signal KreirajTipTreninga(TipTreninga)
    KREIRAJ_TIP_TRENINGA,
    // 6. signal KreirajTrener(Trener)
    KREIRAJ_TRENER,

    // 7. signal ObrisiClan(Clan)
    OBRISI_CLAN,
    // 8. signal ObrisiClanarina(Clanarina)
    OBRISI_CLANARINA,
    // 9. signal ObrisiKategorijaClanarine(KategorijaClanarine)
    OBRISI_KATEGORIJA_CLANARINE,
    // 10. signal ObrisiProgramTreninga(ProgramTreninga)
    OBRISI_PROGRAM_TRENINGA,
    // 11. signal ObrisiTipTreninga(TipTreninga)
    OBRISI_TIP_TRENINGA,
    // 12. signal ObrisiTrener(Trener)
    OBRISI_TRENER,

    // 13. signal PretraziClan(Clan)
    PRETRAZI_CLAN,
    // 14. signal PretraziClanarina(Clanarina)
    PRETRAZI_CLANARINA,
    // 15. signal PretraziKategorijaClanarine(KategorijaClanarine)
    PRETRAZI_KATEGORIJA_CLANARINE,
    // 16. signal PretraziProgramTreninga(ProgramTreninga)
    PRETRAZI_PROGRAM_TRENINGA,
    // 17. signal PretraziTipTreninga(TipTreninga)
    PRETRAZI_TIP_TRENINGA,
    // 18. signal PretraziTrener(Trener)
    PRETRAZI_TRENER,

    // 19. signal PrijaviTrener(korisnickoIme, sifra)
    PRIJAVI_TRENER,

    // 20. signal PromeniClan(Clan)
    PROMENI_CLAN,
    // 21. signal PromeniClanarina(Clanarina)
    PROMENI_CLANARINA,
    // 22. signal PromeniKategorijaClanarine(KategorijaClanarine)
    PROMENI_KATEGORIJA_CLANARINE,
    // 23. signal PromeniProgramTreninga(ProgramTreninga)
    PROMENI_PROGRAM_TRENINGA,
    // 24. signal PromeniTipTreninga(TipTreninga)
    PROMENI_TIP_TRENINGA,
    // 25. signal PromeniTrener(Trener)
    PROMENI_TRENER,

    // 26. signal UbaciProgramTreninga(ProgramTreninga) - videti napomenu klase (SK22/SK23)
    UBACI_PROGRAM_TRENINGA,

    // 27. signal vratiListuClan(kriterijumClan, Lista<Clan>)
    VRATI_LISTU_CLAN_PO_KRITERIJUMU_CLAN,
    // 28. signal vratiListuClan(kriterijumKategorijaClanarine, Lista<Clan>)
    VRATI_LISTU_CLAN_PO_KRITERIJUMU_KATEGORIJA_CLANARINE,
    // 29. signal vratiListuClanarina(kriterijumClan, Lista<Clanarina>)
    VRATI_LISTU_CLANARINA_PO_KRITERIJUMU_CLAN,
    // 30. signal vratiListuClanarina(kriterijumClanarina, Lista<Clanarina>)
    VRATI_LISTU_CLANARINA_PO_KRITERIJUMU_CLANARINA,
    // 31. signal vratiListuClanarina(kriterijumTipTreninga, Lista<Clanarina>)
    VRATI_LISTU_CLANARINA_PO_KRITERIJUMU_TIP_TRENINGA,
    // 32. signal vratiListuClanarina(kriterijumTrener, Lista<Clanarina>)
    VRATI_LISTU_CLANARINA_PO_KRITERIJUMU_TRENER,
    // 33. signal vratiListuKategorijaClanarine(kriterijumKategorijaClanarine, Lista<KategorijaClanarine>)
    VRATI_LISTU_KATEGORIJA_CLANARINE_PO_KRITERIJUMU_KATEGORIJA_CLANARINE,
    // 34. signal vratiListuProgramTreninga(kriterijumProgramTreninga, Lista<ProgramTreninga>)
    VRATI_LISTU_PROGRAM_TRENINGA_PO_KRITERIJUMU_PROGRAM_TRENINGA,

    // 35. signal vratiListuSviClan(Lista<Clan>)
    VRATI_LISTU_SVI_CLAN,
    // 36. signal vratiListuSviKategorijaClanarine(Lista<KategorijaClanarine>)
    VRATI_LISTU_SVI_KATEGORIJA_CLANARINE,
    // 37. signal vratiListuSviProgramTreninga(Lista<ProgramTreninga>)
    VRATI_LISTU_SVI_PROGRAM_TRENINGA,
    // 38. signal vratiListuSviTipTreninga(Lista<TipTreninga>)
    VRATI_LISTU_SVI_TIP_TRENINGA,
    // 39. signal vratiListuSviTrener(Lista<Trener>)
    VRATI_LISTU_SVI_TRENER,

    // 40. signal vratiListuTipTreninga(kriterijumTipTreninga, Lista<TipTreninga>)
    VRATI_LISTU_TIP_TRENINGA_PO_KRITERIJUMU_TIP_TRENINGA,
    // 41. signal vratiListuTrener(kriterijumProgramTreninga, Lista<Trener>) - videti napomenu klase (VodjenjeTreninga)
    VRATI_LISTU_TRENER_PO_KRITERIJUMU_PROGRAM_TRENINGA,
    // 42. signal vratiListuTrener(kriterijumTrener, Lista<Trener>)
    VRATI_LISTU_TRENER_PO_KRITERIJUMU_TRENER
}
