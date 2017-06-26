package sample.controllers;

import clientBusiness.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by User on 017 17.04.17.
 */
public class MainController implements Initializable {

    private static Stage mainStage;
    private Helper helper;
    private volatile Client client;
    private int y;
    @FXML
    public ScrollPane scrollPane;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextArea textArea;
    @FXML
    public Button sendButton;

    public void send(ActionEvent actionEvent) throws IOException {
        client.senderBusiness(textArea.getText());
        //senderBusiness(textArea.getText());
    }

    public void senderBusiness(String text) {
        if (!text.equals("")) {
            Label label = new Label(text);
            char[] chars = label.getText().toCharArray();
            if (y * 10 + 10 < anchorPane.getHeight() - 50) {
                sendSomeLengthMessage(chars);
            } else {
                anchorPane.setMinHeight(anchorPane.getHeight() + 200);
                sendSomeLengthMessage(chars);
            }
            y += 5;
        }
    }

    private void sendMessage(Label label) {
        label.setLayoutY(y * 10 + 10);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                anchorPane.getChildren().add(label);
                scrollPane.setContent(anchorPane);
            }
        });
    }

    private void sendShortMessage(char[] chars) {
        Label label = new Label(String.valueOf(chars));
        label.setLayoutX(10);
        sendMessage(label);
    }

    private void sendLongMessage(char[] chars) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (i % 54 == 0) {
                stringBuffer.insert(i, "\n");
            }
            stringBuffer.append(String.valueOf(chars[i]));
        }
        String string = new String(stringBuffer);
        String[] strings = string.split("\n");
        Label[] labels = new Label[strings.length];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label(strings[i]);
            labels[i].setLayoutX(10);
            sendMessage(labels[i]);
            y++;
        }
    }

    private void sendSomeLengthMessage(char[] chars) {
        if (chars.length < 54) {
            sendShortMessage(chars);
        } else {
            sendLongMessage(chars);
        }
    }

    private void method() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (client.getHelper() != null) {
                        if (!bool) {
                            bool=!bool;
                            helper = client.getHelper();
                            senderBusiness(client.getHelper().getServerMessage());
                        }else {
                            if(helper.equals(client.getHelper())){
                                continue;
                            }else {
                                helper = client.getHelper();
                                senderBusiness(client.getHelper().getServerMessage());
                            }
                        }
                    }
                }
            }
        }).start();
    }

    private boolean bool;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("D:\\Курсовая\\ClientFX\\src\\sample\\annotations\\client.dat"));
            client = (Client) objectInputStream.readObject();
            if (client.isConnection()) {
                client.getMessage();
                System.out.println("her");
            } else {
                System.out.println("ne her");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        method();
    }



    public static void setStage(Stage stage){
        mainStage = stage;
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }
}
