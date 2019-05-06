package CoolCars;

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


//Loads in the window 
public class CoolCars extends Application {
    Stage window;
    Scene scene;
    
    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setTitle("Cool Cars LLC");
        Image image = new Image("CoolCars/CoolCarsLogo.png");
        window.getIcons().add(image);
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
