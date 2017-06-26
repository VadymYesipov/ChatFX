package clientBusiness;

import sample.controllers.Helper;
import sample.controllers.MainController;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by User on 003 03.02.17.
 */
public class Client implements Serializable {

    private static final int PORT = 8887;
    private static final String IP = "85.90.209.113";//192.168.0.101
    private static Socket socket;
    InetAddress ipAddress;
    private static String nickname;
    private String password;
    private boolean connection;

    private Helper helper;


    public Helper getHelper() {
        return helper;
    }

    public static String getNickname() {
        return nickname;
    }

    public boolean isConnection() {
        return connection;
    }

    public Client(String nickname, String password, boolean bool) throws IOException, ClassNotFoundException {
        ipAddress = InetAddress.getByName(IP);
        socket = new Socket(ipAddress, PORT);
        System.out.println(socket.getLocalPort());
        this.nickname = nickname;
        this.password = password;
        sendInfo(nickname, password, bool);
        connection = getInfo();
    }

    private void sendInfo(String nickname, String password, boolean bool) throws IOException {
        Message message = new Message(nickname, password, socket.getInetAddress(), bool);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(message);
    }

    private boolean getInfo() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ServerMessage message = (ServerMessage) objectInputStream.readObject();
        message.printServerMessage();
        if (message.getServerMessage() == null) {
            return false;
        } else {
            return true;
        }
    }

    public void getMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        getterBusiness();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void senderBusiness(String text) throws IOException {
        Message message = new Message(text, nickname);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(message);
    }

    private void getterBusiness() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ServerMessage message = (ServerMessage) objectInputStream.readObject();
        message.printServerMessage();
        helper = new Helper(message);
    }
}