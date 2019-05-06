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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mytar
 */
public class RentCarPageController implements Initializable {
    SQLConnection sqlconn = new SQLConnection();
    Connection conn = sqlconn.connect();
    Statement stmt = sqlconn.getStatement();
    
    @FXML
    TextField CustomerIDForm, DurationForm;
    
    @FXML
    Button CancelButton, ConfirmButton;
    
    @FXML
    Text VINField, MakeField, ModelField, YearField;
    
    
    @FXML
    private void handleCancel(ActionEvent event) throws IOException{
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("EmployeePage.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void handleConfirm(ActionEvent event) throws IOException{
        //TODO
    }
    
    
    public void setCar(String vin, String condition, String style, String make, String model, String year, String color, String price){
        
        try {
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT Cars.StoreID, Address FROM Cars JOIN Location ON Cars.StoreID = Location.StoreID;");
            while (rs.next()) {
                //stores.put(rs.getString(1), rs.getString(2));
                //StoreID.getItems().add(rs.getString(1));
            }
            //Address.textProperty().bind(StoreID.getSelectionModel().selectedItemProperty());
            //StoreID.setValue("1");
            
            VINField.setText(vin);
            MakeField.setText(make);
            ModelField.setText(model);
            YearField.setText(year);
            
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
