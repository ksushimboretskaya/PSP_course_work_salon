package controllers;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;

import java.io.IOException;
import java.util.Objects;

public class LoginController<JFXTextField, JFXPasswordField> {
    @FXML
    private TextField email;
    @FXML
    private PasswordField pass;

    public void makeLogin(ActionEvent actionEvent) throws Exception {
        User user = new User(email.getText(), pass.getText());

        if (email.getText().equals("admin") && pass.getText().equals("admin")) {
            try {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//                stage.getIcons().add(new Image("Icons/System-icon.png"));
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/adminPanel.fxml")));
                stage.setTitle("Панель администратора");
                stage.setResizable(true);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException ex) {
                ex.getMessage();
            }
        } else {
            Client connection = new Client();
            connection.sendMessage("LogIn");
            connection.sendObject(user);
            if (connection.readMessage().equals("Good")) {
                try {
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/MainWindow.fxml")));
                    stage.setTitle("Салон");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Произошла ошибка!");
                    alert.setContentText("Не удалось выполнить операцию!\nПопробуйте повторить позже.");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Авторизация");
                alert.setHeaderText("Пользователь не найден!");
                alert.setContentText("Пользователь с именем " + email.getText() + " ещё не зарегистрирован!");
                alert.showAndWait();
            }
        }
    }
}