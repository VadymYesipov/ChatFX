package clientBusiness;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by User on 012 12.05.17.
 */
public class ServerMessage implements Serializable {

    private String serverMessage;
    private ArrayList<Message> messages;

    public String getServerMessage() {
        return serverMessage;
    }

    public ServerMessage(Message message, ArrayList<Message> messages) {
        serverMessage = "Пользователь " + message.getNickname() + " был добавлен. Пароль: " + message.getPassword();
        this.messages = messages;
    }

    public ServerMessage(Message message, String nickname){
        serverMessage = nickname + ": " + message.getMessage();
    }

    protected void printServerMessage(){
        System.out.println(serverMessage);
    }
}
