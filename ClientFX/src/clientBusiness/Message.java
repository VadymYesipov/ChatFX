package clientBusiness;

import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by User on 012 12.05.17.
 */
public class Message implements Serializable {
    private String message;
    //private int localPort;
    private InetAddress inetAddress;
    private String nickname;
    private boolean bool;
    private String password;

    public boolean isBool() {
        return bool;
    }

    public String getMessage() {
        return message;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public Message(String nickname, String password, InetAddress inetAddress, boolean bool) {
        this.nickname = nickname;
        this.password = password;
        //this.localPort = localPort;
        this.inetAddress = inetAddress;
        this.bool = bool;
    }

    public Message(String message, String nickname) {
        this.message = message;
        this.nickname = nickname;
    }

    protected void printRegistrationMessage() {
        message = nickname + "  " + password + "  " + inetAddress;
        System.out.println(message);
    }

    public void printMessage() {
        System.out.println(nickname + ": " + message);
    }
}
