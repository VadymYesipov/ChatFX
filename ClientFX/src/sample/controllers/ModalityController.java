package sample.controllers;

import clientBusiness.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.controlsfx.control.textfield.CustomPasswordField;
import sample.Main;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by User on 017 17.04.17.
 */
public class ModalityController {

    private static Stage modalityStage;
    private Client client;
    @FXML
    private Label babbleLable;
    @FXML
    private TextField loginField;
    @FXML
    private CustomPasswordField passwordField;
    @FXML
    private Button signInButton;
    @FXML
    private Label tipLabel;
    @FXML
    private Button signUpButton;

    public static void setStage(Stage stage) {
        modalityStage = stage;
    }

    public void signIn(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (loginField.getText().equals("") || passwordField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информация");
            if (loginField.getText().equals("") && passwordField.getText().equals("")) {
                alert.setContentText("Введите логин и пароль");
            } else if (loginField.getText().equals("")) {
                alert.setContentText("Введите логин");
            } else if (passwordField.getText().equals("")) {
                alert.setContentText("Введите пароль");
            }
            alert.setHeaderText("");
            alert.showAndWait();
        } else {
            client = new Client(loginField.getText(), passwordField.getText(), false);
            if(client.isConnection()){
                serializeClient(client);
                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("../annotations/main.fxml"));
                primaryStage.setResizable(false);
                primaryStage.setTitle("Hello World");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
                MainController.setStage(primaryStage);
                if (primaryStage.isShowing()) {
                    modalityStage.close();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setContentText("Такого пользователя не существует. Пройдите регистрацию или попытайтесь ещё.");
                alert.setHeaderText("");
                alert.showAndWait();
            }

        }
    }

    private void serializeClient(Client client) throws IOException {
        String path = "D:\\Курсовая\\ClientFX\\src\\sample\\annotations\\client.dat";
        File file = new File(path);
        file.createNewFile();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(path));
        try {
            objectOutputStream.writeObject(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signUp(ActionEvent actionEvent) throws IOException {
        Stage registrationStage = new Stage();
        Parent parent1 = FXMLLoader.load(getClass().getResource("../annotations/registration.fxml"));
        parent1.setVisible(true);
        registrationStage.setResizable(false);
        registrationStage.setTitle("Hello World");
        registrationStage.setScene(new Scene(parent1));
        registrationStage.initModality(Modality.WINDOW_MODAL);
        registrationStage.initOwner(modalityStage.getScene().getWindow());
        registrationStage.show();
        RegistrationController.setStage(registrationStage);
    }
}
