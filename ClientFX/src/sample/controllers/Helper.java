package sample.controllers;

import clientBusiness.ServerMessage;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

/**
 * Created by User on 019 19.06.17.
 */
public class Helper {

    private ServerMessage serverMessage;

    public Helper(ServerMessage serverMessage) {
        this.serverMessage = serverMessage;
    }

    public String getServerMessage(){
        return serverMessage.getServerMessage();
    }
}
