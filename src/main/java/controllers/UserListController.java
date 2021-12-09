package controllers;

import client.Client;
import database.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserListController implements Initializable {
    @FXML
    public Button blockButton;

    @FXML
    public TextField updateFirstName;

    @FXML
    public TextField updateLastName;

    @FXML
    public TextField updateEmail;

    @FXML
    public TextField updatePassword;

    @FXML
    private TableView<User> usersList;

    @FXML
    private Button update;

    @FXML
    private TableColumn<User, String> firstNameCol;

    @FXML
    private TableColumn<User, String> lastNameCol;

    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, String> passwordCol;

    private final DBWorker db = DBWorker.getInstance();
    private final ObservableList<User> dataList = FXCollections.observableArrayList();
    public User userUpdateRecordSelected;
    private final Client connection = Client.getInstance();

    public UserListController() throws SQLException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataList.clear();
        try {
            ResultSet rs = db.getConnection().createStatement().executeQuery("SELECT firstName,lastName,email,password FROM users");
            while (rs.next()) {
                dataList.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (Exception e) {
            e.getMessage();
        }
        usersList.setItems(dataList);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
    }

    public void setSelectedData() {
        userUpdateRecordSelected = usersList.getSelectionModel().getSelectedItem();
        updateFirstName.setText(userUpdateRecordSelected.getFirstName());
        updateLastName.setText(userUpdateRecordSelected.getLastName());
        updateEmail.setText(userUpdateRecordSelected.getEmail());
        updatePassword.setText(userUpdateRecordSelected.getPassword());
    }

    public void updateUser(ActionEvent actionEvent) {
        User updateUser = new User(updateFirstName.getText(), updateLastName.getText(), updateEmail.getText(), updatePassword.getText());
        connection.sendMessage("UpdateUser");
        connection.sendObject(updateUser);
        if (connection.readMessage().equals("Good")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Изменение пользователя");
            alert.setHeaderText("Успешно!");
            alert.setContentText("Вы изменили данные\n о пользователе!");
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
//        try {
//            Stage stage = new Stage();
//            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/updateUserForm.fxml")));
//            stage.setTitle("Изменить данные о пользователе");
//            stage.setResizable(false);
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch (IOException  e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Ошибка");
//            alert.setHeaderText("Произошла ошибка!");
//            alert.setContentText("Не удалось выполнить операцию!\nПопробуйте повторить позже.");
//            alert.showAndWait();
//        }
//    }
}
