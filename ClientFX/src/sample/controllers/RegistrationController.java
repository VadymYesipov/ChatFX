package sample.controllers;

import clientBusiness.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomPasswordField;

import java.io.IOException;

/**
 * Created by User on 017 17.04.17.
 */
public class RegistrationController {

    private static Stage registrationStage;
    @FXML
    public Label signUpLabel;
    @FXML
    public TextField newLoginField;
    @FXML
    public CustomPasswordField newPasswordField;
    @FXML
    public Button newSignUpButton;

    public static void setStage(Stage stage) {
        registrationStage = stage;
    }

    public void register(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (newLoginField.getText().equals("") || newPasswordField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            if (newLoginField.getText().equals("") && newPasswordField.getText().equals("")) {
                alert.setContentText("Введите логин и пароль");
            } else if (newLoginField.getText().equals("")) {
                alert.setContentText("Введите логин");
            } else if (newPasswordField.getText().equals("")) {
                alert.setContentText("Введите пароль");
            }
            alert.setHeaderText("");
            alert.showAndWait();
        } else {
            Client client = new Client(newLoginField.getText(), newPasswordField.getText(), true);
            registrationStage.close();
        }
    }
}
