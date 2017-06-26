package sample;

import clientBusiness.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.ModalityController;

public class Main extends Application {

    @Override
    public void start(Stage modalityStage) throws Exception{
        Parent parent = FXMLLoader.load(getClass().getResource("annotations/modality.fxml"));
        modalityStage.setResizable(false);
        modalityStage.setTitle("Hello World");
        modalityStage.setScene(new Scene(parent));
        modalityStage.show();
        ModalityController.setStage(modalityStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
