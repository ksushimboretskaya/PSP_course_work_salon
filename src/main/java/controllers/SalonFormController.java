package controllers;

import client.Client;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Salon;

public class SalonFormController {
    public TextField salonName;
    public TextField salonPhone;
    public TextField salonWebsite;
    public TextField salonAddress;

    private final Client connection = Client.getInstance();

    public void insertSalon(ActionEvent actionEvent) {
        Salon newSalon = new Salon(salonName.getText(), salonPhone.getText(), salonWebsite.getText(), salonAddress.getText());
        connection.sendMessage("AddSalon");
        connection.sendObject(newSalon);
        if(connection.readMessage().equals("Good")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Добавление салона");
            alert.setHeaderText("Добавлено!");
            alert.setContentText("Теперь в Вашей сети\nна один салон больше!");
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
