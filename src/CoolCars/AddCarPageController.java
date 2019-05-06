/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoolCars;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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


/**
 * FXML Controller class
 *
 * @author mytar
 */
public class AddCarPageController implements Initializable {

    SQLConnection sqlconn = new SQLConnection();
    Connection conn = sqlconn.connect();
    Statement normS = sqlconn.getStatement();
    CallableStatement addCar = sqlconn.procedure("add_car(?,?,?,?,?,?,?,?,?)");
    HashMap<String, String> stores = new HashMap<>();
    String empID;

    @FXML
    TextField  Style, Make, Model, Year, Color, VIN, Price, Address;

    @FXML
    ChoiceBox Condition, StoreID;

    public void setEmployee(String id){
        this.empID = id;
    }
    
    @FXML
    private void handleAddCar(ActionEvent event) throws IOException {

        //sorry not sorry
        int theVin;
        int thePrice;
        String stringStoreID = (String) StoreID.getValue();
        String stringYear = (String) Year.getText();
        String theStyle = (String) Style.getText();
        String theMake = (String) Make.getText();
        String theModel = (String) Model.getText();
        String theColor = (String) Color.getText();
        String stringVin = VIN.getText();
        String stringPrice = Price.getText();
        theVin = Integer.parseInt(stringVin);
        thePrice = Integer.parseInt(stringPrice);
        int theStoreID = Integer.parseInt(stringStoreID);
        int theYear = Integer.parseInt(stringYear);
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
            addCar.setInt(1,theVin);
            addCar.setInt(2,theStoreID);
            addCar.setInt(3, theCondition);
            addCar.setString(4, theStyle);
            addCar.setString(5, theMake);
            addCar.setString(6, theModel);
            addCar.setInt(7, theYear);
            addCar.setString(8, theColor);
            addCar.setInt(9, thePrice);
            addCar.execute();
        }
        catch(SQLException e)
        {
            System.err.println(e);
        }
    }

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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ResultSet rs = normS.executeQuery("SELECT Distinct CarCondition FROM Cars;");
            int i = 1;
            while(rs.next()){
                String aCondition =rs.getString(1);
                int conditioni = Integer.parseInt(aCondition);
                switch (conditioni) {
                    case 1:
                        aCondition = "New";
                        break;
                    case 2:
                        aCondition = "Fair";
                        break;
                    case 3:
                        aCondition = "Used";
                        break;
                    default:
                        aCondition = "Unavailable";
                        break;
                }
                Condition.getItems().add(aCondition);
            }
            Condition.setValue("Used");

            rs = normS.executeQuery("SELECT DISTINCT Cars.StoreID, Address FROM Cars JOIN Location ON Cars.StoreID = Location.StoreID;");
            while (rs.next()) {
                stores.put(rs.getString(1), rs.getString(2));
                StoreID.getItems().add(rs.getString(1));
            }
            StoreID.setValue("1");

        }  catch (SQLException e) {
            System.out.println(e);
            System.out.println("2");
        }



    }

}
