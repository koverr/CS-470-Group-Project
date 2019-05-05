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
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
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
    Car current;

    @FXML
    ChoiceBox Condition, StoreID;

    @FXML
    TextField VIN, Style, Make, Model, Year, Color, Price, Address;
    
    
    
    @FXML
    private void handleEditCar(ActionEvent event) throws IOException{
        int theVin = Integer.parseInt(VIN.getText());
        int theStoreID = (int) StoreID.getValue();
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
    
    public void setCar(Car car){
        current = car;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
