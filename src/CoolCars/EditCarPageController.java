/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoolCars;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class EditCarPageController implements Initializable {
    
    SQLConnection sqlconn = new SQLConnection();
    Connection conn = sqlconn.connect();
    Statement normS = sqlconn.getStatement();
    CallableStatement editCar = sqlconn.procedure("edit_car(?,?,?,?,?)");
    HashMap<String, String> stores = new HashMap<>();

    @FXML
    ChoiceBox Condition, StoreID;

    @FXML
    TextField VIN, Style, Make, Model, Year, Color, Price, Address;
    
    
    
    @FXML
    private void handleEditCar(ActionEvent event) throws IOException{
        String stringVin = VIN.getText();
        String stringStoreID = (String) StoreID.getValue();
        int theVin = Integer.parseInt(stringVin);
        int theStoreID = Integer.parseInt(stringStoreID);
        String theColor = Color.getText();
        int thePrice = Integer.parseInt(Price.getText());
        String cond = (String) Condition.getValue();
        int theCondition;
        switch (cond) {
            case "New":
                theCondition = 1;
                break;
            case "Fair":
                theCondition = 2;
                break;
            case "Used":
                theCondition = 3;
                break;
            default:
                theCondition = -1;
                break;
        }

        try{
            editCar.setInt(1, theVin);
            editCar.setInt(2, theStoreID);
            editCar.setInt(3,theCondition);
            editCar.setString(4,theColor);
            editCar.setInt(5,thePrice);
            editCar.execute();

        }
        catch(Exception e)
        {
            System.err.println(e);
        }
        
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("EmployeePage.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    @FXML
    private void handleExit(ActionEvent event) throws IOException{
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("EmployeePage.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void setCar(String vin, String condition, String style, String make, String model, String year, String color, String price){
        
        try {
            ResultSet rs = normS.executeQuery("SELECT DISTINCT Cars.StoreID, Address FROM Cars JOIN Location ON Cars.StoreID = Location.StoreID;");
            while (rs.next()) {
                stores.put(rs.getString(1), rs.getString(2));
                StoreID.getItems().add(rs.getString(1));
            }
            Address.textProperty().bind(StoreID.getSelectionModel().selectedItemProperty());
            StoreID.setValue("1");
            
            this.VIN.setText(vin);
            this.Condition.getItems().addAll("New", "Fair", "Used", "Unavailable");
            this.Condition.setValue(condition);
            this.Style.setText(style);
            this.Make.setText(make);
            this.Model.setText(model);
            this.Year.setText(year);
            this.Color.setText(color);
            this.Price.setText(price);
            
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        
            
    }    
    
}
