package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent intro = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/intro.fxml")));
        primaryStage.setTitle("Салон");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(intro));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}