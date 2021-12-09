package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import server.StartServer;

public class ServerController {
    private final StartServer server=new StartServer();
    @FXML
    private Label serverStatus;

    @FXML
    void startServer(ActionEvent event) {
        server.startServer();
        serverStatus.setText("ON");
    }

    @FXML
    void stopServer(ActionEvent event) {
        server.stopServer();
        serverStatus.setText("OFF");

    }
}
