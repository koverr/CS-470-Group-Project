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
    
    String employee;
    
    //Stores the location name, storeID
    HashMap<String, Integer> locationData = new HashMap<>();
    
    //Stores all the cars and their data at a given location
    ObservableList<Car> localCarData = FXCollections.observableArrayList();
    
    @FXML
    ChoiceBox Location;
    
    @FXML
    TableView<Car> CarTable;
    
    @FXML
    TableColumn VINCol, ConditionCol, StyleCol, MakeCol, ModelCol, YearCol, ColorCol, PriceCol;
 
    //Carries over the employee ID from the login page, allowing functionality of the RentCar page
    public void setEmployee(String EID){
        this.employee = EID;
    }
       
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
        
        //Load Add Car page
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("AddCarPage.fxml"));
        try{
            Loader.load();
        } catch (IOException ex){
            System.err.println(ex);
        }
        
        //Place Add Car controller in an object
        AddCarPageController addPage = Loader.getController();
        
        //Pass in employee information to the next page
        addPage.setEmployee(employee);//keep track of employee id during the whole login session
        
        //Display new page
        Parent p = Loader.getRoot();
        Node node=(Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.show();
    }

    @FXML
    private void handleEdit(ActionEvent event) throws IOException{
        
        //Selected keeps track of all the information in the selected row
        Car Selected = CarTable.getSelectionModel().getSelectedItem();
        
        //Load edit car page
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("EditCarPage.fxml"));
        try{
            Loader.load();
        } catch (IOException ex){
            System.err.println(ex);
        }
        
        //Grab EditCarPage Controller as object
        EditCarPageController editPage = Loader.getController();
        editPage.setCar(Selected.getVin(), Selected.getCondition(), Selected.getStyle(), Selected.getMake(), Selected.getModel(), Selected.getYear(), Selected.getColor(), Selected.getPrice());
        editPage.setEmployee(employee);//keep track of employee id during the whole login session
        
        Parent p = Loader.getRoot();
        Node node=(Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.show();
    }
    
    
    @FXML
    private void handleExit(ActionEvent event) throws IOException{
        Node node=(Node) event.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    //Similar to Edit and Add car pages
    @FXML
    private void handleRent(ActionEvent event) throws IOException{
        Car Selected = CarTable.getSelectionModel().getSelectedItem();
        
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("RentCarPage.fxml"));
        try{
            Loader.load();
        } catch (IOException ex){
            System.err.println(ex);
        }
        RentCarPageController rentPage = Loader.getController();
        rentPage.setCar(Selected.getVin(), Selected.getCondition(), Selected.getStyle(), Selected.getMake(), Selected.getModel(), Selected.getYear(), Selected.getColor(), Selected.getPrice());
        rentPage.setRentInfo(employee);
        
        
        Parent p = Loader.getRoot();
        Node node=(Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.show();
    }
    
    @FXML
    //deletes the car currently selected in the table view
    private void handleDeleteCar(ActionEvent event) throws IOException{
        Car Selected = CarTable.getSelectionModel().getSelectedItem();
        try {
            //stored procedure not needed since SQL injection not possible
            stmt.executeUpdate("DELETE FROM Cars WHERE VIN = " + Selected.getVin() + ";");
        } catch (SQLException e){
            System.err.println(e);
        }
        this.listByLocation(event);//update the table view so the deleted car is no longer shown
        
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
        VINCol.setCellValueFactory( new PropertyValueFactory<>("Vin"));
        ConditionCol.setCellValueFactory( new PropertyValueFactory<>("Condition"));
        StyleCol.setCellValueFactory( new PropertyValueFactory<>("Style"));
        MakeCol.setCellValueFactory( new PropertyValueFactory<>("Make"));
        ModelCol.setCellValueFactory( new PropertyValueFactory<>("Model"));
        YearCol.setCellValueFactory( new PropertyValueFactory<>("Year"));
        ColorCol.setCellValueFactory( new PropertyValueFactory<>("Color"));
        PriceCol.setCellValueFactory( new PropertyValueFactory<>("Price"));
        
        
        CarTable.setItems(localCarData);
    }    
    
}
