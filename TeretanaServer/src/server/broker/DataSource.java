package server.broker;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DataSource { 
    
    //veza izmedju servera i baze

    private static final String DB_PROPERTIES_PUTANJA = "src/server/config/db.properties";
    //cita izmenjeni db.properties
    private static DataSource instance;

    private DataSource() {
    }

    public static synchronized DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    
    public Connection getConnection() throws SQLException { //konekcija sa bazom
        Properties svojstva = ucitajSvojstva();
        //datasource ne pamti stare podatke
        String drajver = svojstva.getProperty("db.driver");
        try {
            Class.forName(drajver);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC drajver nije pronadjen na classpath-u: " + drajver, e);
        }

        String url = svojstva.getProperty("db.url");
        String korisnickoIme = svojstva.getProperty("db.user");
        String lozinka = svojstva.getProperty("db.password");

        return DriverManager.getConnection(url, korisnickoIme, lozinka);
    }

    private Properties ucitajSvojstva() throws SQLException {
        Properties svojstva = new Properties();
        File fajl = new File(DB_PROPERTIES_PUTANJA);
        try (FileInputStream ulazniTok = new FileInputStream(fajl)) {
            svojstva.load(ulazniTok);
        } catch (IOException e) {
            throw new SQLException("Nije moguce procitati " + DB_PROPERTIES_PUTANJA
                    + " (napravljen po uzoru na db.properties.example).", e);
        }
        return svojstva;
    }
}
