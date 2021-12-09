package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.Main;

import java.io.IOException;
import java.util.Objects;

public class IntroController extends Main {
    public void showLoginForm(ActionEvent actionEvent) throws IOException {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/loginForm.fxml")));
            stage.setTitle("Авторизация");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Stage stage = new Stage();

                    Parent root;
                    try {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/intro.fxml")));
                        stage.setTitle("Салон красоты");
                        stage.setScene(new Scene(root));
                        stage.setResizable(false);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла ошибка!");
            alert.setContentText("Не удалось выполнить операцию!\nПопробуйте повторить позже.");
            alert.showAndWait();
        }
    }

    public void callRegForm() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/registrationForm.fxml")));
            stage.setTitle("Регистрация");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Произошла ошибка!");
            alert.setContentText("Не удалось выполнить операцию!\nПопробуйте повторить позже.");
            alert.showAndWait();
        }
    }
}