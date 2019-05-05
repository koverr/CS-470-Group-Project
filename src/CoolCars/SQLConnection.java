/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoolCars;
import java.sql.*;

public class SQLConnection {
    private final String USERNAME;
    private final String PASSWORD;
    private final String CONN_STRING;
    Connection conn;
    Statement stmt;
    
    public SQLConnection(){
        USERNAME = "root";
        PASSWORD = "";
        CONN_STRING = "jdbc:mysql://localhost:3306/coolcars";
    }

    public Connection connect(){
        try {
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
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


    
