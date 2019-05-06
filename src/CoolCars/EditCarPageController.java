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
    
    //Establish connection to database
    SQLConnection sqlconn = new SQLConnection();
    Connection conn = sqlconn.connect();
    Statement normS = sqlconn.getStatement();
    
    //Get stored procedure
    CallableStatement editCar = sqlconn.procedure("edit_car(?,?,?,?,?)");
    
    //TODO: Remove? Fix?
    //Stores LocationID as the key and the Location Address as the value. Or at least it should
    HashMap<String, String> stores = new HashMap<>();
    String empID;
    
    //Reference FXML elements
    @FXML
    ChoiceBox Condition, StoreID;

    @FXML
    TextField VIN, Style, Make, Model, Year, Color, Price, Address;
    
    public void setEmployee(String id){
        this.empID = id;
    }
    
    //EditCar Button
    @FXML
    private void handleEditCar(ActionEvent event) throws IOException{
        
        //Grab input from JavaFX scene
        String stringVin = VIN.getText();
        String stringStoreID = (String) StoreID.getValue();
        int theVin = Integer.parseInt(stringVin);
        int theStoreID = Integer.parseInt(stringStoreID);
        String theColor = Color.getText();
        int thePrice = Integer.parseInt(Price.getText());
        String cond = (String) Condition.getValue();
        
        //Convert car condition back into an integer for the database
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
        
        //Alter Car value in the database
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
    
    //Exit button
    @FXML
    private void handleExit(ActionEvent event) throws IOException{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("EmployeePage.fxml"));
        try{
            Loader.load();
        } catch (IOException ex){
            System.err.println(ex);
        }
        EmployeePageController editPage = Loader.getController();
        editPage.setEmployee(empID);
 
        Parent p = Loader.getRoot();
        Node node=(Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(p));
        stage.show();
    }
    
    public void updateAddress(ActionEvent event) throws IOException{
        Address.setText(stores.get(StoreID.getValue()));
    }
    
    //Function used to transfer selected table data to the EditCar page
    public void setCar(String vin, String condition, String style, String make, String model, String year, String color, String price){
        
        try {
            ResultSet rs = normS.executeQuery("SELECT DISTINCT Cars.StoreID, Address FROM Cars JOIN Location ON Cars.StoreID = Location.StoreID;");
            while (rs.next()) {
                stores.put(rs.getString(1), rs.getString(2));
                StoreID.getItems().add(rs.getString(1));
            }
            //Address.textProperty().bind(StoreID.getSelectionModel().selectedItemProperty());
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
