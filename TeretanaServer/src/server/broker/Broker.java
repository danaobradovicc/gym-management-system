package server.broker;

import common.domain.DomenskiObjekat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Broker {

    private static Broker instance;

    private Broker() {
    }

    public static synchronized Broker getInstance() {
        if (instance == null) {
            instance = new Broker();
        }
        return instance;
    }

   //sta treba sacuvati i preko koje veze pristupamo bazi
    public void insert(DomenskiObjekat objekat, Connection konekcija) throws SQLException {
        String kolone = objekat.getInsertColumnNames();
        List<Object> vrednosti = objekat.getInsertValues();
        String sql = "INSERT INTO " + objekat.getTableName() + " (" + kolone + ") VALUES ("
                + napraviUpitnike(vrednosti.size()) + ")";

        try (PreparedStatement ps = konekcija.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            postaviVrednosti(ps, vrednosti, 1);
            ps.executeUpdate();//insert naredba bazi

            try (ResultSet generisaniKljucevi = ps.getGeneratedKeys()) {
                if (generisaniKljucevi.next()) {
                    objekat.setId(generisaniKljucevi.getLong(1));
                }
            }
        }
    }

   
    public void update(DomenskiObjekat objekat, Connection konekcija) throws SQLException {
        DomenskiObjekat kriterijumId = kriterijumPoId(objekat);
        String sql = "UPDATE " + objekat.getTableName() + " SET " + objekat.getUpdateColumnNames()
                + " WHERE " + kriterijumId.getSearchCondition();

        try (PreparedStatement ps = konekcija.prepareStatement(sql)) {
            int sledeciIndeks = postaviVrednosti(ps, objekat.getUpdateValues(), 1);
            postaviVrednosti(ps, kriterijumId.getSearchValues(), sledeciIndeks);
            ps.executeUpdate();
        }
    }
    //Прва линиja "заузме" позициje 1-5 (за 5 нових вредности) и каже "готова сам, следeħa слободна позициja je 6"; 
    //друга линиja настави тачно тaмо (позициja 6) да постави jeдину преосталу вредност (ID), 
    //тако да свих шест ? знакова у упиту добиjy тачну вредност, тачним редом, без преклапања.

    
    public void delete(DomenskiObjekat objekat, Connection konekcija) throws SQLException {
        DomenskiObjekat kriterijumId = kriterijumPoId(objekat);
        String sql = "DELETE FROM " + objekat.getTableName() + " WHERE " + kriterijumId.getSearchCondition();

        try (PreparedStatement ps = konekcija.prepareStatement(sql)) {
            postaviVrednosti(ps, kriterijumId.getSearchValues(), 1);
            ps.executeUpdate();
        }
    }

    
    public DomenskiObjekat selectById(DomenskiObjekat uzorak, Long id, Connection konekcija) throws SQLException {
        DomenskiObjekat kriterijumId = uzorak.newInstance();
        kriterijumId.setId(id);
        List<DomenskiObjekat> rezultat = filter(kriterijumId, konekcija);
        return rezultat.isEmpty() ? null : rezultat.get(0);
    }

    
    public List<DomenskiObjekat> selectAll(DomenskiObjekat uzorak, Connection konekcija) throws SQLException {
        return filter(uzorak.newInstance(), konekcija);
    }

    
    public List<DomenskiObjekat> filter(DomenskiObjekat kriterijum, Connection konekcija) throws SQLException {
        String uslov = kriterijum.getSearchCondition();
        String sql = "SELECT * FROM " + kriterijum.getTableName();
        if (uslov != null && uslov.length() > 0) {
            sql += " WHERE " + uslov;
        }

        List<DomenskiObjekat> rezultat = new ArrayList<DomenskiObjekat>();
        try (PreparedStatement ps = konekcija.prepareStatement(sql)) {
            postaviVrednosti(ps, kriterijum.getSearchValues(), 1);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DomenskiObjekat noviPrimerak = kriterijum.newInstance();
                    noviPrimerak.populateFromResultSet(rs);
                    rezultat.add(noviPrimerak);
                }
            }
        }
        return rezultat;
    }
 

    
    private DomenskiObjekat kriterijumPoId(DomenskiObjekat objekat) {
        DomenskiObjekat kriterijumId = objekat.newInstance();
        kriterijumId.setId(objekat.getId());
        return kriterijumId;
        //cist kriterijum
    }

    private String napraviUpitnike(int broj) {
        StringBuilder upitnici = new StringBuilder();
        for (int i = 0; i < broj; i++) {
            if (i > 0) {
                upitnici.append(", ");
            }
            upitnici.append("?");
        }
        return upitnici.toString();
    }

    
    private int postaviVrednosti(PreparedStatement ps, List<Object> vrednosti, int pocetniIndeks) throws SQLException {
        int indeks = pocetniIndeks;
        for (Object vrednost : vrednosti) {
            ps.setObject(indeks, vrednost);
            indeks++;
        }
        return indeks;
    }
    //sledeci slobodan indeks, prvo postavi nove vrendnosti za set deo upita, pa nastavi od tog vracenog indeksa da postavi ID vrednost 
}
