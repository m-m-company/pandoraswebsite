package persistence;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DbAccess {

    private static Connection connection = null;
    private DbAccess(){}

    public static Connection getConnection(){
        if (connection == null){
            Context context = null;
            try {
                context = new InitialContext();
                DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/pandoras");
                connection = ds.getConnection();
            } catch (NamingException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

}