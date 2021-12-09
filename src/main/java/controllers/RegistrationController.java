package controllers;

import client.Client;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import validators.EmailValidator;

import java.io.IOException;

public class RegistrationController {
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public PasswordField password;

    private final Client connection = Client.getInstance();
    private final EmailValidator emailValidator = new EmailValidator();

    public void MakeRegistration(ActionEvent actionEvent) throws IOException {
        User newUser = new User(firstName.getText(),lastName.getText(),email.getText(),password.getText());
        connection.sendMessage("SignIn");
        connection.sendObject(newUser);

        if(connection.readMessage().equals("Good") && emailValidator.validate(email.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Регистрация");
            alert.setHeaderText("Вы успешно прошли регистрацию");
            alert.setContentText(firstName.getText()+" "+ lastName.getText()+ ", теперь Вы можете войти,\nиспользуя свой логин и пароль");
            alert.showAndWait();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.hide();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Заполните все поля");
            alert.setContentText("Произошла ошибка!\nНаверное, Вы не заполнили некоторые поля\nили ввели некоректрый E-mail");
            alert.showAndWait();
        }
    }
}
