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
        String user = Username.getText();
        String pass = Password.getText();
        
        try {
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

            if ((!r1.next()) && (!r2.next())){
                Status.setText("Incorrect Username or Password.");    
                Password.clear();
            }
            r1.beforeFirst();r2.beforeFirst();
            if (r1.next()){
                Node node=(Node) event.getSource();
                Stage stage=(Stage) node.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("UserPage.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else if (r2.next()){
                System.out.println("Made it here");
                Node node=(Node) event.getSource();
                Stage stage=(Stage) node.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("EmployeePage.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
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
