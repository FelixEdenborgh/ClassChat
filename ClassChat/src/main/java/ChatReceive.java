import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ChatReceive extends Thread {
        public String group = "234.235.236.237";
     // public int port = 12540;
     MulticastSocket socket;
    JTextArea textArea;

    ChatReceive(MulticastSocket socket, JTextArea textArea) throws IOException {
        this.socket = socket;
        this.textArea = textArea;

        //MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(InetAddress.getByName(group));

    }

    public void run(){

        while(true){

            byte[] data = new byte[256];
            DatagramPacket pack = new DatagramPacket(data, data.length);
            try {

                System.out.println("in receive");
                socket.receive(pack);
                String message = new String(pack.getData(),0 , pack.getLength());
                textArea.append(message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Received data from: " + pack.getAddress().toString());
           // String message = new String(pack.getData(),0 , pack.getLength());
           // System.out.println("Message: " + message);
        }
    }



}
