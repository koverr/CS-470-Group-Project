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
    SQLConnection sqlconn = new SQLConnection();
    Connection conn = sqlconn.connect();
    Statement stmt = sqlconn.getStatement();
    HashMap<String, Integer> stores = new HashMap<>();
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
        String price = Price.getText();             //UNREADABLE????????????????????
        
       
        
       
        try {
            search.clear();
            
            ResultSet rs = stmt.executeQuery("SELECT CarCondition, Style, Make, Model, Year, Color, Price"
                                            + " FROM AvailCars "
                                            + "WHERE StoreID = " + stores.get(location) + " && Year >= " + year + " && Price <= " + price + ";");            
        
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
        
        
        YearCol.setCellValueFactory(new PropertyValueFactory<Car, String>("Year"));
        MakeCol.setCellValueFactory(new PropertyValueFactory<Car, String>("Make"));
        ModelCol.setCellValueFactory(new PropertyValueFactory<Car, String>("Model"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<Car, String>("Price"));
        ColorCol.setCellValueFactory(new PropertyValueFactory<Car, String>("Color"));
        StyleCol.setCellValueFactory(new PropertyValueFactory<Car, String>("Style"));
        ConditionCol.setCellValueFactory(new PropertyValueFactory<Car, String>("Condition"));
        
        Results.setItems(search);
        
    }    
    
}
