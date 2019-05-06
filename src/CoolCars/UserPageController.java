/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoolCars;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;



/**
 * FXML Controller class
 *
 * @author mytar
 */
public class UserPageController implements Initializable {

    //setting up the connection to the SQL server
    SQLConnection sqlconn = new SQLConnection();
    Connection conn = sqlconn.connect();
    CallableStatement stmt = sqlconn.procedure("user_car_search(?,?,?)");//stored procedure for looking up cars, prevents SQL injection
    HashMap<String, Integer> stores = new HashMap<>();//HashMap for linking the storeID to the address of the location
    final ObservableList<Car> search = FXCollections.observableArrayList();
    
    @FXML
    ChoiceBox Location, Year;
    
    @FXML
    TextField Price;
    
    @FXML
    TableView Results;
    
    @FXML
    TableColumn YearCol, MakeCol, ModelCol, PriceCol, ColorCol, StyleCol, ConditionCol;
    
    @FXML
    private void handleSearch(ActionEvent event) throws IOException {
        String location = (String) Location.getValue();
        String year = (String) Year.getValue();
        String price = Price.getText();



       
        try {
            stmt.setInt(1, stores.get(location));
            stmt.setInt(2, Integer.parseInt(year));
            stmt.setInt(3, Integer.parseInt(price));

            stmt.execute();

            search.clear();
            
            ResultSet rs = stmt.getResultSet();
        
            while (rs.next()){
                search.add(new Car(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
            Results.setItems(search);
            
        } catch (SQLException e){
            System.err.println(e);
        }
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            ResultSet rs = stmt.executeQuery("SELECT Address FROM Location;");
            int i = 1;
            while(rs.next()){
                Location.getItems().add(rs.getString(1));
                stores.put(rs.getString(1), i++);
            }
            Location.setValue("422 Roberts St");
            
            rs = stmt.executeQuery("SELECT DISTINCT Year FROM Cars ORDER BY Year DESC;");
            while(rs.next()){
                Year.getItems().add(rs.getString(1));
            }
            Year.setValue("2020");
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        
        YearCol.setCellValueFactory(new PropertyValueFactory<>("Year"));
        MakeCol.setCellValueFactory(new PropertyValueFactory<>("Make"));
        ModelCol.setCellValueFactory(new PropertyValueFactory<>("Model"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ColorCol.setCellValueFactory(new PropertyValueFactory<>("Color"));
        StyleCol.setCellValueFactory(new PropertyValueFactory<>("Style"));
        ConditionCol.setCellValueFactory(new PropertyValueFactory<>("Condition"));
        
        Results.setItems(search);
        
    }    
    
}
