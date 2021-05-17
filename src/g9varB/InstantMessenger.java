package g9varB;

import java.awt.Component;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

public class InstantMessenger implements MessageListener {
    private String sender;
    private List<MessageListener> listeners = new LinkedList();
    private static final int SERVER_PORT = 5500;

    public InstantMessenger() {
        this.startServer();
    }

    public void addMessageListener(MessageListener listener) {
        synchronized(this.listeners) {
            this.listeners.add(listener);
        }
    }

    public void removeMessageListener(MessageListener listener) {
        synchronized(this.listeners) {
            this.listeners.remove(listener);
        }
    }

    public void sendMessage(String senderName, String address, String message) {
        try {
            Socket socket = new Socket(address, 5500);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(senderName);
            out.writeUTF(message);
            socket.close();
        } catch (UnknownHostException var7) {
            var7.printStackTrace();
            JOptionPane.showMessageDialog((Component)null, "Can't send the message:destined host not found", "Error", 0);
        } catch (IOException var8) {
            var8.printStackTrace();
            JOptionPane.showMessageDialog((Component)null, "Can't send the message", "Error", 0);
        }

    }
// поток обработчик запрслв
    private void startServer() {
        (new Thread(new Runnable() {
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(5500);

                    while(!Thread.interrupted()) {
                        Socket socket = serverSocket.accept();
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        String senderName = in.readUTF();
                        String message = in.readUTF();
                        socket.close();
                        InstantMessenger.this.notifyListeners(new Peer(senderName, (InetSocketAddress)socket.getRemoteSocketAddress()), message);
                    }
                } catch (IOException var6) {
                    var6.printStackTrace();
                    JOptionPane.showMessageDialog((Component)null, "Error in server functioning", "Mistake", 0);
                }

            }
        })).start();
    }

    public void messageReceived(Peer sender, String message) {
    }

    private void notifyListeners(Peer sender, String message) {
        synchronized(this.listeners) {
            Iterator var4 = this.listeners.iterator();

            while(var4.hasNext()) {
                MessageListener listener = (MessageListener)var4.next();
                listener.messageReceived(sender, message);
            }

        }
    }

    public void addMessageListner(MessageListener listener) {
        synchronized(this.listeners) {
            this.listeners.add(listener);
        }
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return this.sender;
    }
}
