package main.java.applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class app extends Application {
    public static void main(String[] args) throws IOException {
        launch(args);
        }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(app.class.getResource("/BusinessesDataView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Edmonton Property Assessments");
        stage.setScene(scene);
        stage.show();
    }
}
