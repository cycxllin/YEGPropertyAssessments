package main.java.applications.YegPropertyAssessments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("/PropertyAsessmentsDataView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Edmonton Property Assessments");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
