//import jdk.jfr.StackTrace;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import static java.awt.BorderLayout.*;

public class ChatSend extends JFrame implements ActionListener {


    JTextArea textArea = new JTextArea(10, 16);
    JButton kopplaNedButton = new JButton("Koppla ned");
    JTextField textField = new JTextField();
    public int port = 12540;
    public MulticastSocket socket = new MulticastSocket(port);
    InetAddress Iadr = InetAddress.getByName("234.235.236.237");
    ChatReceive C1 = new ChatReceive(socket, textArea);




    ChatSend() throws IOException {

        JFrame frame = new JFrame("Chat Felix");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);
        textField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String m = "F: " + textField.getText();
                textField.setText(" ");
               // textArea.setText(m);
                byte [] data = m.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, Iadr, port);
                try {
                    socket.send(packet);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println(m);
            }
        });


        //textField.addActionListener(this);
        kopplaNedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket.leaveGroup(Iadr);
                    System.out.print("Disconnecting from group");
                    textArea.setText("Disconecting from group");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(kopplaNedButton, NORTH);
        panel.add(textArea, CENTER);
        panel.add(textField, SOUTH);
        C1.start();
        frame.pack();
        frame.setLocation(800, 400);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
    }
}
