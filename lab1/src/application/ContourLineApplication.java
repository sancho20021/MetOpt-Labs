package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ContourLineApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation("/application.ContourLineApplication.fxml");
            Parent root = FXMLLoader.load(ContourLineApplication.class.getResource("ContourLineApplication.fxml"));
//            Scene scene  = new Scene(root);
//            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("exception during start: " + e.getMessage());
        }
    }
}
