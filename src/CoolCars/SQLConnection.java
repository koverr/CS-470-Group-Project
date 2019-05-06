package CoolCars;
import java.sql.*;
import java.util.ResourceBundle;

public class SQLConnection {
    Connection conn;
    Statement stmt;
    private ResourceBundle reader = null;
    private static String FILENAME;
    
    //Points to a config file outside of code
    public SQLConnection(){
        FILENAME = "resources/dbconfig";
    }

    //Connects to the database without having the plaintext user/pass/connection string in the code
    public Connection connect(){
        try {
            reader = ResourceBundle.getBundle(FILENAME);
            conn=DriverManager.getConnection(reader.getString("db.url"),reader.getString("db.username"),reader.getString("db.password"));
        } catch (SQLException e){
            System.err.print(e);

        }
        return conn;
    }


    
    public Statement getStatement(){
        try {
            stmt = conn.createStatement();
        } catch (SQLException e){
            System.err.print(e);
        }
        return stmt;
    }

    public CallableStatement procedure(String s){
        try{
            CallableStatement c = conn.prepareCall("{call " + s + "}");
            return c;
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
        return null;
    }
}