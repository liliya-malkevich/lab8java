package g9varB;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class MainFrame extends JFrame implements MessageListener {
    private InstantMessenger instMess;
    private static final String FRAME_TITLE = "Message in real time";
    private static final int FRAME_MINIMUM_WIDTH = 500;
    private static final int FRAME_MINIMUM_HEIGHT = 500;
    private static final int FROM_FIELD_DEFAULT_COLUMNS = 10;
    private static final int TO_FIELD_DEFAULT_COLUMNS = 20;
    private static final int OUTGOING_AREA_DEFAULT_ROWS = 5;
    private static final int SMALL_GAP = 5;
    private static final int MEDIUM_GAP = 10;
    private static final int LARGE_GAP = 15;
    private final JTextField textFieldFrom;
    private final JTextField textFieldTo;
    private final JEditorPane textAreaIncoming;
    private final JTextArea textAreaOutgoing;
    private StringBuffer incomingText;
    static HTMLDocument doc = null;
    static HTMLEditorKit htmlKit = null;

    public MainFrame() {
        super("Message in real time");
        this.setMinimumSize(new Dimension(500, 500));
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((kit.getScreenSize().width - this.getWidth()) / 2, (kit.getScreenSize().height - this.getHeight()) / 2);
        this.incomingText = new StringBuffer();
        this.textAreaIncoming = new JEditorPane();
        this.textAreaIncoming.setContentType("text/html");
        this.textAreaIncoming.setEditable(false);
        JScrollPane scrollPaneIncoming = new JScrollPane(this.textAreaIncoming);
        JLabel labelFrom = new JLabel("From");
        JLabel labelTo = new JLabel("To");
        this.textFieldFrom = new JTextField(10);
        this.textFieldTo = new JTextField(20);
        this.textAreaOutgoing = new JTextArea(5, 0);
        JScrollPane scrollPaneOutgoing = new JScrollPane(this.textAreaOutgoing);
        JPanel messagePanel = new JPanel();
        messagePanel.setBorder(BorderFactory.createTitledBorder("Type message"));
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.sendMessage();
            }
        });
        this.instMess = new InstantMessenger();
        this.instMess.addMessageListner(this);
        GroupLayout layout2 = new GroupLayout(messagePanel);
        messagePanel.setLayout(layout2);
        layout2.setHorizontalGroup(layout2.createSequentialGroup().addContainerGap().addGroup(layout2.createParallelGroup(Alignment.TRAILING).addGroup(layout2.createSequentialGroup().addComponent(labelFrom).addGap(5).addComponent(this.textFieldFrom).addGap(15).addComponent(labelTo).addGap(5).addComponent(this.textFieldTo)).addComponent(scrollPaneOutgoing).addComponent(sendButton)).addContainerGap());
        layout2.setVerticalGroup(layout2.createSequentialGroup().addContainerGap().addGroup(layout2.createParallelGroup(Alignment.BASELINE).addComponent(labelFrom).addComponent(this.textFieldFrom).addComponent(labelTo).addComponent(this.textFieldTo)).addGap(10).addComponent(scrollPaneOutgoing).addGap(10).addComponent(sendButton).addContainerGap());
        GroupLayout layout1 = new GroupLayout(this.getContentPane());
        this.setLayout(layout1);
        layout1.setHorizontalGroup(layout1.createSequentialGroup().addContainerGap().addGroup(layout1.createParallelGroup().addComponent(scrollPaneIncoming).addComponent(messagePanel)).addContainerGap());
        layout1.setVerticalGroup(layout1.createSequentialGroup().addContainerGap().addComponent(scrollPaneIncoming).addGap(10).addComponent(messagePanel).addContainerGap());
    }
//send message
    private void sendMessage() {
        String senderName = this.textFieldFrom.getText();
        String destinationAddress = this.textFieldTo.getText();
        String message = this.textAreaOutgoing.getText();
        if (senderName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Server error", "error", 0);
        } else if (destinationAddress.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Server error", "error", 0);
        } else if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Server error", "error", 0);
        } else {
            this.instMess.sendMessage(senderName, destinationAddress, message);
            this.appendMessage(senderName + " -> " + destinationAddress + ": " + message);
            //this.instMess.sendMessage(senderName, destinationAddress, message);
            this.textAreaOutgoing.setText("");
        }
    }

    public void messageReceived(Peer sender, String message) {
        String var10001 = sender.getName();
        this.appendMessage(var10001 + " (" + sender.getAddress().getHostName() + ": " + sender.getAddress().getPort() + "): " + message);
    }


    public synchronized void appendMessage(String message) {

        String smile = ":)";
        if (message.contains(smile)) {
            int pos = message.indexOf(smile);
            String var10000 = message.substring(0, pos);
            //message = var10000 + "<img src=\"file:\\C:\\Users\\kanis\\IdeaProjects\\lab7java\\angel.png\" width=30 height=30>" + message.substring(pos + 5);
            //message = var10000 + "Picture" + message.substring(pos + 3);
            message =  message.substring(0,pos) + "<img src=\"file:\\C:\\Users\\kanis\\IdeaProjects\\lab7java\\angel.png\" width=30 height=30>";
        }

        String html = "<span>" + message + "</span><br/>";
        this.incomingText.insert(0, html);
        String text = this.incomingText.toString();
        this.textAreaIncoming.setText(text);
    }
}

