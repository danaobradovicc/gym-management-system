package common.domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public interface DomenskiObjekat extends Serializable {

    
    String getTableName();

    
    String getInsertColumnNames();

    
    List<Object> getInsertValues();
    //broker ih ne lepi u sql tekst, opasno zbog sql injectiona, ide posebno preko
    //ps.setObject()

    
    String getUpdateColumnNames();

    
    List<Object> getUpdateValues();

    
    String getSearchCondition();
    //pogleda polja koja su postavljena, nisu null, i napravi WHERE npr ime=? AND prezime=?

    
    List<Object> getSearchValues();

    
    void populateFromResultSet(ResultSet rs) throws SQLException;

    void setId(Long id);

    Long getId();

    
    DomenskiObjekat newInstance();
}
