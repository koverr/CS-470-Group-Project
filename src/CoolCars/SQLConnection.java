/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoolCars;
import java.sql.*;
import java.util.ResourceBundle;

public class SQLConnection {
    Connection conn;
    Statement stmt;
    private ResourceBundle reader = null;
    private static String FILENAME;
    
    public SQLConnection(){
        FILENAME = "resources/dbconfig";
    }

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


    
