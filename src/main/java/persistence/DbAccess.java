package persistence;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class DbAccess {

    static Connection connection = null;

    static{
        try{
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/pandoras");
            connection = ds.getConnection();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }

}