package controllers;

import client.Client;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Employee;
import models.Services;

public class ServiceFormController {
    public TextField serviceName;
    public TextField serviceCost;

    private final Client connection = Client.getInstance();

    public void insertService(ActionEvent actionEvent) {
        Services newServ = new Services(serviceName.getText(), serviceCost.getText());
        connection.sendMessage("AddService");
        connection.sendObject(newServ);
        if(connection.readMessage().equals("Good")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Добавление услуги");
            alert.setHeaderText("Добавлено!");
            alert.setContentText("Теперь в нашем прайсе\nна одну услугу больше!");
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
