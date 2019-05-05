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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mytar
 */
public class EmployeePageController implements Initializable {
    
    //Database connection
    SQLConnection sqlconn = new SQLConnection();
    Connection conn = sqlconn.connect();
    Statement stmt = sqlconn.getStatement();
    
    //Stores the location name, storeID
    HashMap<String, Integer> locationData = new HashMap<>();
    
    //Stores all the cars and their data at a given location
    final ObservableList<Car> localCarData = FXCollections.observableArrayList();
    
    @FXML
    ChoiceBox Location;
    
    @FXML
    TableView CarTable;
    
    @FXML
    TableColumn VINCol, ConditionCol, StyleCol, MakeCol, ModelCol, YearCol, ColorCol, PriceCol;
 
       
    @FXML
    private void listByLocation(ActionEvent event) throws IOException{
        
        //Grabbing the information from the ChoiceBox
        String location = (String) Location.getValue();
        
        
        try{
            
            localCarData.clear();
            
            ResultSet rs = stmt.executeQuery("SELECT Vin, CarCondition, Style, Make, Model, Year, Color, Price"
            + " FROM Cars WHERE StoreID = " + locationData.get(location) + ";");
            
            //Pass in query results to ObservableList
            while(rs.next()){
                localCarData.add(new Car(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            
            //Update the table with the new data
            CarTable.setItems(localCarData);
            
         
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void handleAddCar(ActionEvent event) throws IOException{
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("AddCarPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleExit(ActionEvent event) throws IOException{
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            
            //Populate ChoiceBox with locations
            ResultSet rs = stmt.executeQuery("SELECT Address FROM Location;");
            int i = 1;
            while(rs.next()){
                Location.getItems().add(rs.getString(1));
                locationData.put(rs.getString(1), i++);
            }
           
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        
        //Map Car Model Class to TableView columns
        VINCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Vin"));
        ConditionCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Condition"));
        StyleCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Style"));
        MakeCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Make"));
        ModelCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Model"));
        YearCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Year"));
        ColorCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Color"));
        PriceCol.setCellValueFactory( new PropertyValueFactory<Car,String>("Price"));
        
        
        CarTable.setItems(localCarData);
    }    
    
}
