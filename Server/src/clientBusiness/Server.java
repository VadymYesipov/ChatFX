package clientBusiness;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by User on 003 03.02.17.
 */
public class Server {
    private final int PORT = 8887;
    ServerSocket serverSocket;
    private volatile ArrayList<Socket> sockets;
    private volatile ArrayList<Thread> threads;
    private ArrayList<Message> messages;

    public ArrayList<Socket> getSockets() {
        return sockets;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        sockets = new ArrayList<Socket>();
        messages = new ArrayList<Message>();
        threads = new ArrayList<Thread>();
    }

    protected void work() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //System.out.println(Thread.currentThread().getName());
                    try {
                        acceptSocket();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void acceptSocket() throws ClassNotFoundException {
        try {
            if (sockets.add(serverSocket.accept())) {
                if (getSendInfo(sockets.get(sockets.size() - 1))) {
                    Thread thread = new Thread(new ListnerSpeaker(this));
                    threads.add(thread);
                    thread.setName("Thread-" + threads.size());
                    thread.start();
                } else {
                    sockets.remove(sockets.size() - 1);
                    System.out.println("ggg");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopThread(Thread thread) {
        thread.interrupt();
        int index = Integer.parseInt(thread.currentThread().getName().substring(7)) - 1;
        threads.remove(index);
        if (index != threads.size()) {
            for (int i = index; i < threads.size(); i++) {
                threads.get(i).setName("Thread-" + (index + 1));
            }
        }
        System.out.println("threads.size = " + threads.size());
        System.out.println("sockets.size = " + sockets.size());
        System.out.println("messages.size = " + messages.size());
    }

    private boolean getSendInfo(Socket socket) throws ClassNotFoundException, IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object object = objectInputStream.readObject();
        Message message = null;
        if (object instanceof Message) {
            message = (Message) object;
            message.printRegistrationMessage();
            if (message.isBool()) {
                messages.add(message);
                return returnBooleanAndSendMessage(message);
            } else {
                boolean bool = false;
                for (int i = 0; i < messages.size(); i++) {
                    if (messages.get(i).getNickname().equals(message.getNickname()) && messages.get(i).getPassword().equals(message.getPassword())) {
                        System.out.println("her");
                        bool = true;
                    }
                }
                if (!bool && !message.isBool()) {
                    ServerMessage serverMessage = new ServerMessage(null, messages);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(sockets.get(sockets.size() - 1).getOutputStream());
                    objectOutputStream.writeObject(serverMessage);
                    return false;
                }
            }
        }
        return !returnBooleanAndSendMessage(message);
    }

    private boolean returnBooleanAndSendMessage(Message message) throws IOException {
        ServerMessage serverMessage = new ServerMessage(message, messages);
        serverMessage.printServerMessage();
        for (int j = 0; j < sockets.size(); j++) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(sockets.get(j).getOutputStream());
            objectOutputStream.writeObject(serverMessage);
        }
        return false;
    }
}