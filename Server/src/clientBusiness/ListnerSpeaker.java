package clientBusiness;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by User on 025 25.02.17.
 */
public class ListnerSpeaker implements Runnable {
    volatile ArrayList<Socket> sockets;
    volatile Server server;

    public ListnerSpeaker(Server server) {
        this.server = server;
        sockets = server.getSockets();
    }

    @Override
    public void run() {
        try {
            listnerAndSpeaker();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.stopThread(Thread.currentThread());
    }

    private void listnerAndSpeaker() throws InterruptedException, ClassNotFoundException, IOException {
        while (true) {
            ServerMessage serverMessage = listner(Integer.parseInt(Thread.currentThread().getName().substring(7)));
            if (serverMessage == null) {
                break;
            } else {
                speaker(serverMessage);
            }
        }
    }

    private ServerMessage listner(int number) throws ClassNotFoundException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(sockets.get(number - 1).getInputStream());
            Message message = (Message) objectInputStream.readObject();
            message.printMessage();
            ServerMessage serverMessage = new ServerMessage(message, message.getNickname());
            //serverMessage.printServerMessage();
            return serverMessage;
        } catch (IOException e) {
            sockets.remove(number - 1);
            return null;
        }
    }

    private synchronized void speaker(ServerMessage serverMessage) {
        for (int j = 0; j < sockets.size(); j++) {
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(sockets.get(j).getOutputStream());
                objectOutputStream.writeObject(serverMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
