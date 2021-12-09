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
import javafx.stage.Stage;
import models.Records;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddRecordPanelController implements Initializable {
    @FXML
    private ComboBox<String> salonName;

    @FXML
    private ComboBox<String> serviceName;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField userName;

    @FXML
    private Label serviceCost;

    private final ObservableList<Records> recordsObservableList = FXCollections.observableArrayList();
    private final ObservableList<String> salonObservableList = FXCollections.observableArrayList();
    private final ObservableList<String> servicesObservableList = FXCollections.observableArrayList();
    private final ObservableList<String> servicesCostList = FXCollections.observableArrayList();
    DBWorker db = DBWorker.getInstance();
    private final Client connection = Client.getInstance();

    public AddRecordPanelController() throws SQLException {
    }

    public void loadData() {
        recordsObservableList.clear();
        salonObservableList.clear();
        servicesObservableList.clear();
        try {
            ResultSet salonRS = db.getConnection().createStatement().executeQuery("SELECT salonName FROM salons");
            while (salonRS.next()) {
                salonObservableList.add(salonRS.getString("salonName"));
            }
            ResultSet servicesRS = db.getConnection().createStatement().executeQuery("SELECT serviceName FROM services");
            while (servicesRS.next()) {
                servicesObservableList.add(servicesRS.getString("serviceName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataImport();
    }

    public void dataImport() {
        salonName.setItems(salonObservableList);
        serviceName.setItems(servicesObservableList);
        datePicker.setValue(LocalDate.now());
        datePicker.setShowWeekNumbers(true);
        serviceName.setOnAction(event ->
                serviceCost.setText(getServicesCost()));
    }

    public String getServicesCost() {
        servicesCostList.clear();
        try {
            ResultSet rs = db.getConnection().createStatement().executeQuery("select serviceCost from services where serviceName = ('" + serviceName.getValue() + "')");
            while (rs.next()) {
                servicesCostList.add(rs.getString("serviceCost"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return servicesCostList.toString();
    }

    public void addRecord(ActionEvent actionEvent) {
        Records newRecords = new Records(salonName.getValue(), userName.getText(), serviceName.getValue(), serviceCost.getText(), datePicker.getValue().toString());
        connection.sendMessage("AddRecord");
        connection.sendObject(newRecords);
        if (connection.readMessage().equals("Good")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Запись к мастеру");
            alert.setHeaderText("Успешно!");
            alert.setContentText("Будем ждать Вас\nснова!");
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

    public void generateReport() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        // spreadsheet object
        Sheet sheet = workbook.createSheet("Чек");

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.PINK.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell =  header.createCell(0);
        headerCell.setCellValue("Название салона");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Фамилия клиента");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Название услги");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("Стоимость услуги");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("Дата");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        Row row = sheet.createRow(2);

        Cell cell = row.createCell(0);
        cell.setCellValue(salonName.getValue());
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue(userName.getText());
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue(serviceName.getValue());
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue(serviceCost.getText());
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue(datePicker.getValue().toString());
        cell.setCellStyle(style);

        FileOutputStream outputStream = new FileOutputStream("rep.xlsx");
        workbook.write(outputStream);
        workbook.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }
}
