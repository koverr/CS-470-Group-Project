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
    Statement stmt = sqlconn.getStatement();
    
    @FXML
    private Label label;
    
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
            ResultSet rs = stmt.executeQuery("SELECT Username FROM User WHERE Username = '" + user + "' AND Password = '" + pass + "';");
            if (!rs.next()){
                Status.setText("Incorrect Username or Password.");    
                Password.clear();
            }
            else{
                Node node=(Node) event.getSource();
                Stage stage=(Stage) node.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("UserPage.fxml"));/* Exception */
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
