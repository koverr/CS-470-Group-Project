/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoolCars;

import java.io.IOException;
import java.sql.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author mytar
 */
public class EmployeePageController implements Initializable {
    SQLConnection sqlconn = new SQLConnection();
    Connection conn = sqlconn.connect();
    Statement stmt = sqlconn.getStatement();
    HashMap<String, Integer> stores = new HashMap<>();

    
    @FXML
    ChoiceBox Location;
    
    @FXML
    TableView CarTable;
    
    @FXML
    TableColumn VINCol, ConditionCol, StyleCol, MakeCol, ModelCol, YearCol, ColorCol, PriceCol;
 
       
    @FXML
    private void listByLocation(ActionEvent event) throws IOException{
        String location = (String) Location.getValue();
        System.out.println(location);
        CarSearch query;
        
        try{
            ResultSet rs = stmt.executeQuery("SELECT Vin, CarCondition, Style, Make, Model Price"
            + " FROM Cars "
            + " Where StoreID = " + stores.get(location) + ";");
            //System.out.println("I was clicked");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            ResultSet rs = stmt.executeQuery("SELECT Address FROM Location;");
            int i = 1;
            while(rs.next()){
                Location.getItems().add(rs.getString(1));
                stores.put(rs.getString(1), i++);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        final ObservableList<Car> data = FXCollections.observableArrayList(
                new Car("1234", "-1", "SUV", "Nissan", "Maxima", "2008", "Maroon", "50")
        );
        
        VINCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Vin"));
        ConditionCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Condition"));
        StyleCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Style"));
        MakeCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Make"));
        ModelCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Model"));
        YearCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Year"));
        ColorCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Color"));
        PriceCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Price"));
        
        CarTable.setItems(data);
    }    
    
}
