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

/**
 * FXML Controller class
 *
 * @author mytar
 */
public class AddCarPageController implements Initializable {

    SQLConnection sqlconn = new SQLConnection();
    Connection conn = sqlconn.connect();
    Statement normS = sqlconn.getStatement();

    @FXML
    ChoiceBox Condition, Style, Make, Model, Year, Color,StoreID;

    @FXML
    TextField VIN, Price;

    @FXML
    private void handleAddCar(ActionEvent event) throws IOException {
        int theVin =  Integer.parseInt(VIN.getText());
        int thePrice = Integer.parseInt(Price.getText());



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

            rs = normS.executeQuery("SELECT DISTINCT Year FROM Cars ORDER BY Year DESC;");
            while (rs.next()) {
                Year.getItems().add(rs.getString(1));
            }
            Year.setValue("2020");

            rs = normS.executeQuery("SELECT DISTINCT Style FROM Cars;");
            while (rs.next()) {
                Style.getItems().add(rs.getString(1));
            }

            rs = normS.executeQuery("SELECT DISTINCT Make FROM Cars;");
            while (rs.next()) {
                Make.getItems().add(rs.getString(1));
            }

            rs = normS.executeQuery("SELECT DISTINCT Model FROM Cars;");
            while (rs.next()) {
                Model.getItems().add(rs.getString(1));
            }

            rs = normS.executeQuery("SELECT DISTINCT Color FROM Cars;");
            while (rs.next()) {
                Color.getItems().add(rs.getString(1));
            }

            rs = normS.executeQuery("SELECT DISTINCT StoreID FROM Cars;");
            while (rs.next()) {
                StoreID.getItems().add(rs.getString(1));
            }
        }  catch (SQLException e) {
            System.out.println(e);
            System.out.println("2");
        }



    }

}
