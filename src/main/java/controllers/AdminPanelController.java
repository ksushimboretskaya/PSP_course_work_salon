package controllers;

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
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Employee;
import models.Salon;
import models.Services;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {
    @FXML
    private TableView<Salon> salonTable;

//    @FXML
//    private TableColumn<Salon, String> idCol;

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

//    @FXML
//    private TableColumn<Services, String> servicesIdCol;

    @FXML
    private TableColumn<Services, String> servicesNameCol;

    @FXML
    private TableColumn<Services, String> servicesCostCol;

    @FXML
    private TableView<Employee> employeeTable;

//    @FXML
//    private TableColumn<Services, String> employeeIdCol;

    @FXML
    private TableColumn<Employee, String> employeeFirstNameCol;

    @FXML
    private TableColumn<Employee, String> employeeLastNameCol;

    @FXML
    private TableColumn<Employee, String> employeeServiceCol;

    @FXML
    private PieChart pieChart;
    @FXML
    public BarChart barChart;

    private final ObservableList<Salon> salonsData = FXCollections.observableArrayList();
    private final ObservableList<Services> servicesData = FXCollections.observableArrayList();
    private final ObservableList<Employee> employeeData = FXCollections.observableArrayList();
    private final DBWorker db = DBWorker.getInstance();

    public AdminPanelController() throws SQLException {
    }

    private void columnsImport() {
      //  idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        salonNameCol.setCellValueFactory(new PropertyValueFactory<>("salonName"));
        salonPhoneCol.setCellValueFactory(new PropertyValueFactory<>("salonPhone"));
        salonWebsiteCol.setCellValueFactory(new PropertyValueFactory<>("salonWebsite"));
        salonAddressCol.setCellValueFactory(new PropertyValueFactory<>("salonAddress"));

        salonTable.setItems(salonsData);

      //  servicesIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        servicesNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        servicesCostCol.setCellValueFactory(new PropertyValueFactory<>("serviceCost"));

        servicesTable.setItems(servicesData);

      //  employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeFirstNameCol.setCellValueFactory(new PropertyValueFactory<>("employee_firstName"));
        employeeLastNameCol.setCellValueFactory(new PropertyValueFactory<>("employee_lastName"));
        employeeServiceCol.setCellValueFactory(new PropertyValueFactory<>("employee_service"));

        employeeTable.setItems(employeeData);
    }

    public void loadData() {
        barChart.getData().clear();
        salonsData.clear();
        servicesData.clear();
        employeeData.clear();
        try {
            ResultSet salonRS = db.getConnection().createStatement().executeQuery("SELECT id, salonName,salonPhone,salonWebsite,salonAddress FROM salons");
            while (salonRS.next()) {
                salonsData.add(new Salon(salonRS.getInt(1), salonRS.getString(2), salonRS.getString(3), salonRS.getString(4), salonRS.getString(5)));
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
            ex.getMessage();
        }
        columnsImport();

        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Салоны", db.salonsCount()),
                new PieChart.Data("Услуги", db.servicesCount()),
                new PieChart.Data("Сотрудники", db.employeeCount())
        );
        pieChart.setData(list);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Сумма");
        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("Количество");

        dataSeries.getData().add(new XYChart.Data("Сотрудники",db.employeeCount()));
        dataSeries.getData().add(new XYChart.Data("Салоны", db.salonsCount()));
        dataSeries.getData().add(new XYChart.Data("Услуги", db.servicesCount()));

        barChart.getData().add(dataSeries);
    }

    public void exit(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/intro.fxml")));
            stage.setTitle("Салон");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serverControl(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/serverPanel.fxml")));
            stage.setTitle("Управление сервером");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadUsersList(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/userList.fxml")));
            stage.setTitle("Список пользователей");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void callInsertEmployeeForm() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/insertEmployeeForm.fxml")));
            stage.setTitle("Добавить сотрудника");
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

    public void callInsertSalonForm() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/insertSalonForm.fxml")));
            stage.setTitle("Добавить салон");
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

    public void callInsertServiceForm() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/insertServiceForm.fxml")));
            stage.setTitle("Добавить услугу");
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