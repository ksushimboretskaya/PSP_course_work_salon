package controllers;

import client.Client;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Employee;

public class EmployeeFormController {
    public TextField firstName;
    public TextField lastName;
    public TextField service;

    private final Client connection = Client.getInstance();

    public void insertEmployee(ActionEvent actionEvent) {
        Employee newEmp = new Employee(firstName.getText(), lastName.getText(), service.getText());
        connection.sendMessage("AddEmp");
        connection.sendObject(newEmp);
        if(connection.readMessage().equals("Good")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Добавление сотрудника");
            alert.setHeaderText("Добавлено!");
            alert.setContentText("Теперь в команде\nна одного сотрудника больше!");
            alert.showAndWait();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.hide();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Заполните все поля");
            alert.setContentText("Произошла ошибка!\nНаверное, Вы не заполнили некоторые поля");
            alert.showAndWait();
        }
    }
}