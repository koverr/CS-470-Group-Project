/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoolCars;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import java.sql.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author koryo
 */
public class LoginPageController implements Initializable {
    
    //Establishes connection to the database and defines stored procedures
    SQLConnection sqlconn = new SQLConnection();
    Connection conn = sqlconn.connect();
    CallableStatement stmt = sqlconn.procedure("user_login(?,?)");
    CallableStatement stmt2 = sqlconn.procedure("employee_login(?,?)");
    
    @FXML
    TextField Username;
    
    @FXML
    PasswordField Password;
    
    @FXML
    Label Status;
    
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        //Stores Username and Password fields
        String user = Username.getText();
        String pass = Password.getText();
        
        try {
            //Sets up variables for stored procedure queries
            stmt.setString(1, user);
            stmt.setString(2,pass);

            int inEID;
            try {
                inEID = Integer.parseInt(user);
            }
            catch (Exception e){
                //System.err.println(e);
                inEID = -1;
            }
            stmt2.setInt(1,inEID);

            stmt2.setString(2,pass);

            boolean s1Results = stmt.execute();
            boolean s2Results = stmt2.execute();

            ResultSet r1 = stmt.getResultSet();
            ResultSet r2 = stmt2.getResultSet();

            
            //Checks that login matches a user
            if ((!r1.next()) && (!r2.next())){
                Status.setText("Incorrect Username or Password.");    
                Password.clear();
            }
            //Checks if login was a user and if so moves to user page
            r1.beforeFirst();r2.beforeFirst();
            if (r1.next()){
                Node node=(Node) event.getSource();
                Stage stage=(Stage) node.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("UserPage.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            //Checks if login was a Employee
            else if (r2.next()){
                //Takes the employeeID if matched into the employee page
                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("EmployeePage.fxml"));
                try{
                    Loader.load();
                } catch (IOException ex){
                    System.err.println(ex);
                }
                EmployeePageController editPage = Loader.getController();
                editPage.setEmployee(user);

                Parent p = Loader.getRoot();
                Node node=(Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setScene(new Scene(p));
                stage.show();
            }
        } catch (SQLException e){
            System.err.println(e);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}
