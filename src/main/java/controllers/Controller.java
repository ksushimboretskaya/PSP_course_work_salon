package controllers;

import client.Client;
import database.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Employee;
import models.Records;
import models.Salon;
import models.Services;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Button b2;

    @FXML
    public Button b1;

    @FXML
    private TableView<Salon> salonTable;

    @FXML
    private TableColumn<Salon, String> idCol;

    @FXML
    private TableColumn<Salon, String> salonNameCol;

    @FXML
    private TableColumn<Salon, String> salonPhoneCol;

    @FXML
    private TableColumn<Salon, String> salonWebsiteCol;

    @FXML
    private TableColumn<Salon, String> salonAddressCol;

    @FXML
    private TableView<Services> servicesTable;

    @FXML
    private TableColumn<Services, String> servicesIdCol;

    @FXML
    private TableColumn<Services, String> servicesNameCol;

    @FXML
    private TableColumn<Services, String> servicesCostCol;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Services, String> employeeIdCol;

    @FXML
    private TableColumn<Employee, String> employeeFirstNameCol;

    @FXML
    private TableColumn<Employee, String> employeeLastNameCol;

    @FXML
    private TableColumn<Employee, String> employeeServiceCol;

    DBWorker db = DBWorker.getInstance();
    private final ObservableList<Salon> salonsData = FXCollections.observableArrayList();
    private final ObservableList<Services> servicesData = FXCollections.observableArrayList();
    private final ObservableList<Employee> employeeData = FXCollections.observableArrayList();

    public Controller() throws SQLException {
    }

    private void columnsImport() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        salonNameCol.setCellValueFactory(new PropertyValueFactory<>("salonName"));
        salonPhoneCol.setCellValueFactory(new PropertyValueFactory<>("salonPhone"));
        salonWebsiteCol.setCellValueFactory(new PropertyValueFactory<>("salonWebsite"));
        salonAddressCol.setCellValueFactory(new PropertyValueFactory<>("salonAddress"));

        salonTable.setItems(salonsData);

        servicesIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        servicesNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        servicesCostCol.setCellValueFactory(new PropertyValueFactory<>("serviceCost"));

        servicesTable.setItems(servicesData);

        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("employee_firstName"));
        employeeLastNameCol.setCellValueFactory(new PropertyValueFactory<>("employee_lastName"));
        employeeServiceCol.setCellValueFactory(new PropertyValueFactory<>("employee_service"));

        employeeTable.setItems(employeeData);
    }

    public void loadData() {
        salonsData.clear();
        servicesData.clear();
        employeeData.clear();

        try {
            ResultSet salonRS = db.getConnection().createStatement().executeQuery("SELECT id, salonName,salonPhone,salonWebsite,salonAddress FROM salons");
            while (salonRS.next()) {
                salonsData.add(new Salon(salonRS.getInt(1), salonRS.getString(2),
                        salonRS.getString(3), salonRS.getString(4), salonRS.getString(5)));
            }
            ResultSet servicesRS = db.getConnection().createStatement().executeQuery("SELECT id, serviceName,serviceCost FROM services");
            while (servicesRS.next()) {
                servicesData.add(new Services(servicesRS.getInt(1), servicesRS.getString(2), servicesRS.getString(3)));
            }
            ResultSet employeeRS = db.getConnection().createStatement().executeQuery("SELECT id, employee_firstName,employee_lastName,employee_service FROM employee");
            while (employeeRS.next()) {
                employeeData.add(new Employee(employeeRS.getInt(1), employeeRS.getString(2), employeeRS.getString(3), employeeRS.getString(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        columnsImport();
    }

    public void showUserProfile(ActionEvent mouseEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/userProfile.fxml")));
            stage.setTitle("Профиль пользователя");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void insertRecords(ActionEvent actionEvent) {
//        Client connection = new Client();
//        Records newRec = new Records(salonName.getAccessibleText(), serviceName.getAccessibleText(), datePicker.getAccessibleText());
//        connection.sendMessage("AddRecords");
//        connection.sendObject(newRec);
//        if (connection.readMessage().equals("Good")) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Запись к мастеру");
//            alert.setHeaderText("Успешно!");
//            alert.setContentText("Теперь в нашем прайсе\nна одну услугу больше!");
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

    public void callAddRecordsForm() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/addRecord.fxml")));
            stage.setTitle("Записаться");
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

    public void callSearchForm() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/searchMain.fxml")));
            stage.setTitle("Дополнительные функции");
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
    }
}