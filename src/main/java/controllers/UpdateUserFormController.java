//package controllers;
//
//import client.Client;
//import javafx.event.ActionEvent;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.control.Alert;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//import models.User;
//
//import java.net.URL;
//import java.sql.SQLException;
//import java.util.ResourceBundle;
//
//public class UpdateUserFormController implements Initializable {
//    public TextField firstName;
//    public TextField lastName;
//    public TextField email;
//    public TextField password;
//
//    private final Client connection = Client.getInstance();
//    private  UserListController userListController;
//    private  User newUser;
//
//    public UpdateUserFormController() throws SQLException {
//    }
//
//    public void updateUser(ActionEvent actionEvent) {
//        User updateUser = new User(newUser.getId(), firstName.getText(), lastName.getText(), email.getText(), password.getText());
//        connection.sendMessage("UpdateUser");
//        connection.sendObject(updateUser);
//        if (connection.readMessage().equals("Good")) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Изменение пользователя");
//            alert.setHeaderText("Успешно!");
//            alert.setContentText("Вы изменили данные\n о пользователе!");
//            alert.showAndWait();
//            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//            stage.hide();
//        } else {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setTitle("Ошибка!");
//            alert.setHeaderText("Заполните все поля");
//            alert.setContentText("Произошла ошибка!\nНаверное, Вы не заполнили некоторые поля");
//            alert.showAndWait();
//        }
//    }
//
//    public void loadData(User newUser){
//        firstName.setText(newUser.getFirstName());
//        lastName.setText(newUser.getLastName());
//        email.setText(newUser.getEmail());
//        password.setText(newUser.getPassword());
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//       loadData(userListController.userUpdateRecordSelected);
//    }
//}