package controllers;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {
    @FXML
    private Label firstName;

    @FXML
    private Label lastName;

    @FXML
    private Label email;

    @FXML
    private Label password;

    Client connection = Client.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection.sendMessage("MakeProfile");
        ArrayList<User> user = (ArrayList<User>) connection.readObject();
        for (User users : user) {
            firstName.setText(users.getFirstName());
            lastName.setText(users.getLastName());
            email.setText(users.getEmail());
            password.setText(users.getPassword());
        }
    }
}