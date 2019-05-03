/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoolCars;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


/**
 * FXML Controller class
 *
 * @author mytar
 */
public class UserPageController implements Initializable {
    SQLConnection sqlconn = new SQLConnection();
    Connection conn = sqlconn.connect();
    Statement stmt = sqlconn.getStatement();
    
    @FXML
    ChoiceBox Location;
    
    @FXML 
    ChoiceBox Year;
    
    @FXML
    TextField Price;
    
    @FXML
    TextField Results;
    
    @FXML
    private void handleSelection() throws IOException{
  

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT Address FROM Location;");
            while(rs.next()){
                Location.getItems().add(rs.getString(1));
            }
            Location.setValue("422 Roberts St");
            
            rs = stmt.executeQuery("SELECT DISTINCT Year FROM Cars;");
            while(rs.next()){
                Year.getItems().add(rs.getString(1));
            }
            Year.setValue("2019");
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }    
    
}
