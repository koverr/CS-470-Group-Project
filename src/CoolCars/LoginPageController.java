/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoolCars;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author koryo
 */
public class LoginPageController implements Initializable {
    SQLConnection sqlconn = new SQLConnection();
    Connection conn = sqlconn.connect();
    Statement stmt = sqlconn.getStatement();
    
    @FXML
    private Label label;
    
    @FXML
    TextField Username;
    
    @FXML
    PasswordField Password;
    
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        
        if (Username.getText().equals("koverbay") && Password.getText().equals("pass")){
            System.out.println("Logged in!");
            /////////////////////////////////////////////////////////////////////
            try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM AvailCars;");
            
            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            for (int i = 1; i <= columns; i++){
                System.out.print(rsmd.getColumnName(i) + "      ");
            }
            System.out.println();
            while (rs.next()){

                for (int i = 1; i <= columns; i++){
                    //if (i > 1)
                    //    System.out.print(", ");
                    String columnData = rs.getString(i);
                    System.out.print(columnData + "         ");
                }
                System.out.println();
            }
            
            } catch (SQLException e){
             System.err.print(e);
            }
            /////////////////////////////////////////////////////////////////////
            Node node=(Node) event.getSource();
            Stage stage=(Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("UserPage.fxml"));/* Exception */
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
            System.out.println("Wrong credentials");
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
