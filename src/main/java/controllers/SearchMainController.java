package controllers;

import client.Client;
import database.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Employee;
import models.Salon;
import models.Services;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class SearchMainController {

   @FXML
   public Button b1;
    @FXML
    public Button b2;
    @FXML
    public Button exit;
    @FXML
    private TableView<Services> servicesTableView;

    @FXML
    private TableColumn<Services, String> serviceNameCol;

    @FXML
    private TableColumn<Services, String> serviceCostCol;

    DBWorker db = DBWorker.getInstance();
    private final ObservableList<Services> servicesObservableList = FXCollections.observableArrayList();
    private final Client connection = Client.getInstance();

    public SearchMainController() throws SQLException {
    }

    private void tableCreator(){

        serviceNameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceCostCol.setCellValueFactory(new PropertyValueFactory<>("serviceCost"));
        servicesTableView.setItems(servicesObservableList);
    }

    @FXML
    private void changeColor(ActionEvent event) {
        Button btn = (Button) event.getSource();

        switch(btn.getText()) {
            case "Найти услугу": {
                TextInputDialog dialog = new TextInputDialog("");
                dialog.setTitle("Поиск салонных услуг");
                dialog.setHeaderText("Поиск услуги");
                dialog.setContentText("Пожалуйста, введите тип услуги");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()) {
                    connection.sendMessage("FindServices");
                    connection.sendObject(result.get());
                    servicesObservableList.clear();
                    try {
                        ResultSet rs = db.getConnection().createStatement().executeQuery("select serviceName, serviceCost from services  where serviceName = ('"+result.get()+"')");
                        while (rs.next()) {
                            servicesObservableList.add(new Services(rs.getString(1), rs.getString(2)));
                        }
                        tableCreator();
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка");
                        alert.setHeaderText("Произошла ошибка!");
                        alert.setContentText("Не удалось выполнить операцию!\nПопробуйте повторить позже.");
                        alert.showAndWait();
                    }
//                    try {
//                        Stage stage = new Stage();
//                        //  stage.getIcons().add(new Image("Icons/System-icon.png"));
//                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/search.fxml")));
//                        stage.setTitle("Результат поиска: ");
//                        stage.setResizable(false);
//                        stage.setScene(new Scene(root));
//                        stage.setOnShown(e1 -> tableCreator());
//                        stage.show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
            }
            break;
//            case "Просмотреть всю историю":
//            {
//
//                connection.sendMessage("ShowHistory");
//                ArrayList<Taxes> dataList = (ArrayList<Taxes>) connection.readObject();
//                ObservableList<Taxes> data = FXCollections.observableArrayList(dataList);
//                for(Taxes taxes : dataList){
//
//                }
//                System.out.println("dataList: " +dataList);
//
//                try {
//                    Stage stage = new Stage();
//                    stage.getIcons().add(new Image("Icons/System-icon.png"));
//                    Parent root = FXMLLoader.load(getClass().getResource("/fxml/taxTable.fxml"));
//                    stage.setTitle("Регистрация");
//                    stage.setResizable(false);
//                    stage.setScene(new Scene(root));
//                    stage.show();
//                    stage.setOnShown(new EventHandler<WindowEvent>() {
//                        @Override
//                        public void handle(WindowEvent event) {
//
//                            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//                            surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
//                            typeCol.setCellValueFactory(new PropertyValueFactory<>("taxType"));
//                            summaryCol.setCellValueFactory(new PropertyValueFactory<>("summary"));
//                            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
//                            taxList.setItems(data);
//                        }
//                    });
//
//
//                }catch (Exception e){
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Ошибка");
//                    alert.setHeaderText("Произошла ошибка!");
//                    alert.setContentText("Неудалось выполнить операцию!\nПопробуйте повторить позже.");
//                    alert.showAndWait();
//                }
//            }
//            break;
            case "Удалить учетную запись":
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Удаление записи");
                alert.setHeaderText("Удаление учетной записи пользователя");
                alert.setContentText("Вы уверены, что хотите удалить\nВашу учетную запись?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    connection.sendMessage("DeleteRecords");
                    if(connection.readMessage().equals("Good")){
                        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                        infoAlert.setTitle("Уведомление");
                        infoAlert.setHeaderText(null);
                        infoAlert.setContentText("Операция прошла успешно! Ваши данные удалены из системы.\nПосле закрытия окна, приложение будет закрыто.");
                        infoAlert.showAndWait();
                    }
                    System.exit(0);

                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + btn.getText());
        }
    }

    @FXML
    private void exit(ActionEvent event) {
        //connection.sendMessage("Shutdown");
        System.exit(0);
    }
}
