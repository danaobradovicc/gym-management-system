package server.so;

import common.domain.DomenskiObjekat;
import common.exception.SystemOperationException;
import server.broker.DataSource;

import java.sql.Connection;
import java.sql.SQLException;


public abstract class AbstractSystemOperation {

    
    protected Connection konekcija;

    protected Object result;

    public final Object templateExecute(DomenskiObjekat argument) throws Exception {
        //poziva je servercontroller
        try {
            konekcija = DataSource.getInstance().getConnection();
        } catch (SQLException e) {
            
            throw new SystemOperationException("Sistem ne moze da uspostavi konekciju sa bazom podataka: " + e.getMessage());
        }

        try {
            konekcija.setAutoCommit(false);
            //promene se ne potvrdjuju automatski

            validate(argument);
            execute(argument);

            konekcija.commit();
            return result;
        } catch (Exception e) {
            if (konekcija != null) {
                try {
                    konekcija.rollback();
                } catch (SQLException rollbackException) {
                    
                    e.addSuppressed(new Exception("Rollback neuspesan: " + rollbackException.getMessage()));
                }
            }
            throw e;
        } finally {
            if (konekcija != null) {
                konekcija.close();
            }
        }
    }

    
    protected abstract void validate(DomenskiObjekat argument) throws Exception;

    protected abstract void execute(DomenskiObjekat argument) throws Exception; //broker koristi

    public Object getResult() {
        return result;
    }
}
