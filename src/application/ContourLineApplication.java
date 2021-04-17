package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class ContourLineApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(ContourLineApplication.class.getResource("ContourLineApplication.fxml"));
            stage.show();
        } catch (Exception e) {
            System.err.println("exception during start: " + e.getMessage());
        }
    }
}
