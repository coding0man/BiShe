package Utils;

import java.sql.*;

/**
 * Created by fandexian on 16/4/9.
 */
public class DbDao {
    private DbDao dbDao;
    private Connection connection;
    private Statement statement;
    private ResultSet set;


   public DbDao getDbDao(){
       if(dbDao == null){

       }
       return dbDao;
   }
    private DbDao(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(Constants.URL,Constants.USER,Constants.PASSWORD);
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ResultSet query(String sql) throws SQLException {
        statement.executeQuery(sql);
        return set;
    }
}
